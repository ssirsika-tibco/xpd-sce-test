/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * @generated
 */
public class AssociationClassCreateCommand extends CreateElementCommand {

    private Stereotype stereo;

    /**
     * @generated
     */
    public AssociationClassCreateCommand(CreateElementRequest req) {
        super(req);
    }

    /**
     * @generated
     */
    protected EObject getElementToEdit() {
        EObject container = ((CreateElementRequest) getRequest())
                .getContainer();
        if (container instanceof View) {
            container = ((View) container).getElement();
        }
        return container;
    }

    /**
     * @generated
     */
    protected EClass getEClassToEdit() {
        return UMLPackage.eINSTANCE.getPackage();
    }

    /**
     * @generated NOT
     * 
     *            Overide the superclass method so that we can set the name of
     *            the class
     * 
     * @return the new model element that has been created
     */
    protected EObject doDefaultElementCreation() {
        EReference containment = getContainmentFeature();
        EClass eClass = getElementType().getEClass();
        EObject classCreated = null;

        if (containment != null) {
            EObject element = getElementToEdit();

            if (element != null) {

                classCreated = EMFCoreUtil.create(element, containment, eClass);

                Class cl = (Class) classCreated;
                Package pkgParent = (Package) element;

                String defaultName = null;

                // If stereotype is already applied then it is a mandatory
                // stereotype
                List<Stereotype> lstStereos = cl.getAppliedStereotypes();
                if (!lstStereos.isEmpty()) {
                    String name = lstStereos.get(0).getLabel();
                    defaultName = UML2ModelUtil.createUniqueElementName(
                            pkgParent, name);
                } else if (stereo != null) {
                    // Or if we have come from a dynamic palette tool...
                    UML2ModelUtil.safeApplyStereotype(cl, stereo);
                    defaultName = UML2ModelUtil.createUniqueElementName(
                            pkgParent, stereo.getLabel());
                } else if (getRequest().getParameter(
                        BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME) != null) {
                    // If the default name parameter has been set then use it,
                    // otherwise use default name
                    defaultName = (String) getRequest()
                            .getParameter(
                                    BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME);
                } else {
                    defaultName = UML2ModelUtil
                            .createUniqueClassName(pkgParent);
                }

                cl.setName(defaultName);
            }
        }

        return classCreated;

    }

}
