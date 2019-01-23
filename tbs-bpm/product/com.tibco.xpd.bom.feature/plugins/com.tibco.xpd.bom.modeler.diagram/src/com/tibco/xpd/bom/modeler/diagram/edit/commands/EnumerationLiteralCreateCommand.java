/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.resources.utils.UniquenessStrategy;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * @generated
 */
public class EnumerationLiteralCreateCommand extends CreateElementCommand {

    /**
     * @generated NOT
     */
    private Stereotype stereo;

    /**
     * @generated NOT
     */
    public EnumerationLiteralCreateCommand(CreateElementRequest req) {
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
        return UMLPackage.eINSTANCE.getEnumeration();
    }

    @Override
    protected EObject doDefaultElementCreation() {
        EnumerationLiteral enumLit =
                (EnumerationLiteral) super.doDefaultElementCreation();

        String defaultName = null;

        defaultName =
                UniquenessStrategy.getInstance()
                        .genUniqueEnumLitName((Enumeration) getElementToEdit());

        if (stereo != null) {
            UML2ModelUtil.safeApplyStereotype(enumLit, stereo);
            defaultName =
                    UML2ModelUtil
                            .createUniqueEnumLitName((Enumeration) getElementToEdit(),
                                    stereo.getLabel());
        }

        enumLit.setName(defaultName);
        // Set the display label
        PrimitivesUtil.setDisplayLabel(enumLit, defaultName, false);
        return enumLit;
    }
}
