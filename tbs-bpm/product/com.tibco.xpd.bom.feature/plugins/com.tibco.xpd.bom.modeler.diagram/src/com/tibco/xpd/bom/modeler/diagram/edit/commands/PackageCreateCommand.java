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
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * @generated
 */
public class PackageCreateCommand extends CreateElementCommand {

    private Stereotype stereo;

    /**
     * @generated NOT
     */
    public PackageCreateCommand(CreateElementRequest req) {
        super(req);
        stereo =
                (Stereotype) req
                        .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE);
    }

    /**
     * @generated
     */
    protected EObject getElementToEdit() {
        EObject container =
                ((CreateElementRequest) getRequest()).getContainer();
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
     *            the package
     * 
     * @return the new model element that has been created
     */
    protected EObject doDefaultElementCreation() {

        EReference containment = getContainmentFeature();
        EClass eClass = getElementType().getEClass();
        EObject pkgCreated = null;

        if (containment != null) {

            // TODO : If we are creating this from a wizard we may need to add
            // more code to getElementToEdit
            // as we won't be originating from a view container
            EObject element = getElementToEdit();

            if (element != null) {

                pkgCreated = EMFCoreUtil.create(element, containment, eClass);

                Package pkg = (Package) pkgCreated;
                Package parentPkg = (Package) element;

                String defaultName = null;

                // If stereotype is already applied then it is a mandatory
                // stereotype
                List<Stereotype> lstStereos = pkg.getAppliedStereotypes();
                if (!lstStereos.isEmpty()) {

                    // TODO: FirstProfiles should have configurable default
                    // names
                    // FOund when externalizing the Concepts3 profile. A package
                    // would
                    // pick up "Package" rather than "mypackage"

                    // String name = lstStereos.get(0).getLabel();
                    // defaultName = UML2ModelUtil.createUniqueElementName(
                    // parentPkg, name);

                    defaultName =
                            UML2ModelUtil.createUniquePackageName(parentPkg);

                } else if (stereo != null) {
                    // Or if we have come from a dynamic palette tool...
                    UML2ModelUtil.safeApplyStereotype(pkg, stereo);
                    defaultName =
                            UML2ModelUtil.createUniqueElementName(parentPkg,
                                    stereo.getLabel());
                } else if (getRequest()
                        .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME) != null) {
                    // If the default name parameter has been set then use it,
                    // otherwise use default name
                    defaultName =
                            (String) getRequest()
                                    .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME);
                } else {
                    defaultName =
                            UML2ModelUtil.createUniquePackageName(parentPkg);
                }

                pkg.setName(defaultName);
                // Set the display label
                PrimitivesUtil.setDisplayLabel(pkg, defaultName, false);
            }
        }

        return pkgCreated;

    }

}
