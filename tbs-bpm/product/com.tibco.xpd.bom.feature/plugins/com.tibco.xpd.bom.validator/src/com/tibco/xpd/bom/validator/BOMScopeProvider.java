/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralSpecification;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IValidationItem;

/**
 * BOM Validation scope provider.
 * 
 * @author wzurek
 * 
 */
public class BOMScopeProvider implements IScopeProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.provider.IScopeProvider#getAffectedObjects(com
     * .tibco.xpd.validation.destinations.Destination, java.lang.String,
     * com.tibco.xpd.validation.provider.IValidationItem)
     */
    @Override
    public Collection<EObject> getAffectedObjects(Destination destination,
            String providerId, IValidationItem item) {
        Collection<EObject> objects = item.getObjects();
        Set<EObject> affectedObjs = new HashSet<EObject>();
        WorkingCopy wc = item.getWorkingCopy();
        IResource resource = wc.getEclipseResources().get(0);
        IProject project = null;
        if (resource != null) {
            project = resource.getProject();
        }
        if (project != null && isValidationEnabled(project, destination)) {
            if (doValidate(objects)) {
                // Get the model from the working copy
                if (wc != null && wc.getRootElement() != null) {
                    addItemsToValidate(wc, affectedObjs);
                }
            }
        }

        return affectedObjs;
    }

    /**
     * Check if the given destination is enabled.
     * 
     * @param project
     * @param destination
     * @return
     */
    protected boolean isValidationEnabled(IProject project,
            Destination destination) {
        return internalIsValidationEnabled(project, destination);
    }

    /**
     * Sid XPD-1605: Provide a static method that builders can use so they use
     * exactly the same method to decide whether validaiton is enabled (so that
     * builder is only enabled if valdiation is enabled)
     * 
     * @param project
     * @param destinaionId
     * 
     * @return
     */
    public static boolean isValidationDestinationEnabled(IProject project,
            String destinationId) {
        Destination destination =
                ValidationActivator.getDefault().getDestination(destinationId);
        if (destination != null) {
            return internalIsValidationEnabled(project, destination);
        }
        return false;
    }

    /**
     * * Sid XPD-1605: Provide a static method that builders can use so they use
     * exactly the same method to decide whether validaiton is enabled (so that
     * builder is only enabled if valdiation is enabled)
     * 
     * @param project
     * @param destination
     * @return
     */
    private static boolean internalIsValidationEnabled(IProject project,
            Destination destination) {
        /* Unselectable destinations validations are always run */
        if (!destination.isSelectable()
                /*
                 * Else destination has to be enabled in one of the project's
                 * selected destinations
                 */
                || BOMValidatorActivator.getDefault()
                        .isValidationDestinationEnabled(project,
                                destination.getId())) {
            return true;
        }
        return false;
    }

    /**
     * Check if the validation item should be revalidated.
     * 
     * @param eObjects
     *            collection of affected objects
     * @return <code>true</code> if the validation item should be revalidated,
     *         <code>false</code> otherwise.
     */
    protected boolean doValidate(Collection<EObject> eObjects) {
        boolean validate = false;

        if (eObjects != null) {
            for (EObject eo : eObjects) {
                if (validate = include(eo)) {
                    // Validate whole model
                    validate = true;
                    break;
                }
            }
        }

        return validate;
    }

    /**
     * Add the affected objects in the working copy to the list of objs to
     * validate.
     * 
     * @param wc
     *            <code>WorkingCopy</code>.
     * @param objs
     *            Set to add the validation items to.
     */
    protected void addItemsToValidate(WorkingCopy wc, Set<EObject> objs) {
        if (wc != null && objs != null) {
            EObject root = wc.getRootElement();

            if (root != null) {
                // Add root object
                objs.add(root);
                // Add root's content
                TreeIterator<Object> iter =
                        EcoreUtil.getAllProperContents(root, false);

                if (iter != null) {
                    while (iter.hasNext()) {
                        Object next = iter.next();

                        if (next instanceof EObject && include((EObject) next)) {
                            objs.add((EObject) next);
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the given EObject should be included in the validation.
     * 
     * @param eo
     *            <code>EObject</code>.
     * @return <code>true</code> if object of interest, <code>false</code>
     *         otherwise.
     */
    protected boolean include(EObject eo) {

        /*
         * If this is the domain value annotation on an enumeration literal then
         * include in the validation.
         */
        if (eo instanceof EAnnotation) {
            if (BOMResourcesPlugin.ENUM_LIT_DOMAIN_ANNOTATION_SOURCE
                    .equals(((EAnnotation) eo).getSource())) {
                return true;
            }
        }

        return eo != null
                && !(eo instanceof Profile)
                && (eo instanceof DynamicEObjectImpl || eo instanceof Model
                        || eo instanceof Package || eo instanceof Operation
                        || eo instanceof Parameter
                        || eo instanceof PrimitiveType || eo instanceof Class
                        || eo instanceof Enumeration
                        || eo instanceof EnumerationLiteral
                        || eo instanceof Property || eo instanceof Association
                        || eo instanceof Generalization || eo instanceof LiteralSpecification);
    }
}
