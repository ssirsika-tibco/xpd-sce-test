/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;

/**
 * @generated
 */
public class AssociationClassDanglingNodeCreateCommand extends
        CreateElementCommand {

    private Property sourceEndFromExtData;

    private Property targetEndFromExtData;

    private String nameFromExtData;

    /**
     * @generated NOT
     */
    public AssociationClassDanglingNodeCreateCommand(CreateElementRequest req) {
        super(req);

        Object paramSrc = req.getParameter("sourceEnd"); //$NON-NLS-1$
        Object paramTrg = req.getParameter("targetEnd"); //$NON-NLS-1$
        Object paramName = req.getParameter("name"); //$NON-NLS-1$

        if (paramSrc != null && paramSrc instanceof Property) {
            sourceEndFromExtData = (Property) paramSrc;
        }

        if (paramTrg != null && paramTrg instanceof Property) {
            targetEndFromExtData = (Property) paramTrg;
        }

        if (paramName != null && paramName instanceof String) {
            nameFromExtData = (String) paramName;
        }

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

    @Override
    protected EObject doDefaultElementCreation() {
        EReference containment = getContainmentFeature();
        EClass eClass = getElementType().getEClass();
        EObject eoCreated = null;
        AssociationClass assocClassCreated = null;

        if (containment != null) {
            EObject element = getElementToEdit();

            if (element != null) {

                eoCreated = EMFCoreUtil.create(element, containment, eClass);

                Package pkgParent = (Package) element;

                if (eoCreated != null && eoCreated instanceof AssociationClass) {

                    assocClassCreated = (AssociationClass) eoCreated;

                    if (nameFromExtData == null || nameFromExtData.equals("")) {
                        assocClassCreated.setName(UML2ModelUtil
                                .createUniqueAssociationClassName(pkgParent));
                    } else {
                        assocClassCreated.setName(nameFromExtData);
                    }

                    Property assocEnd1 = UMLFactory.eINSTANCE.createProperty();
                    assocEnd1.setName(sourceEndFromExtData.getName());
                    assocClassCreated.getOwnedEnds().add(assocEnd1);
                    assocEnd1.setType(sourceEndFromExtData.getType());

                    Property assocEnd2 = UMLFactory.eINSTANCE.createProperty();
                    assocEnd2.setName(targetEndFromExtData.getName());
                    assocClassCreated.getOwnedEnds().add(assocEnd2);
                    assocEnd2.setType(targetEndFromExtData.getType());

                }
            }
        }

        return assocClassCreated;
    }
}
