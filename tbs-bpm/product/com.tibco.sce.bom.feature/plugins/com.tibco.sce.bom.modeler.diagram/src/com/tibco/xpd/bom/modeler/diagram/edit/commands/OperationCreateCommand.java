/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.resources.utils.UniquenessStrategy;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * @generated
 */
public class OperationCreateCommand extends CreateElementCommand {

    private Stereotype stereo;

    /**
     * @generated NOT
     */
    public OperationCreateCommand(CreateElementRequest req) {
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
        return UMLPackage.eINSTANCE.getClass_();
    }

    @Override
    protected EObject doDefaultElementCreation() {
        Operation operation = (Operation) super.doDefaultElementCreation();

        String defaultName = null;

        if (getRequest()
                .getParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME) != null) {
            defaultName = (String) getRequest().getParameter(
            // If the default name parameter has been set then use it, otherwise
            // use default name
            BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.NAME);
        } else {
            defaultName =
                    UniquenessStrategy.getInstance()
                            .getUniqueOperationName((Class) getElementToEdit());
        }

        if (stereo != null) {
            UML2ModelUtil.safeApplyStereotype(operation, stereo);
            defaultName =
                    UML2ModelUtil.createUniqueOperationName(operation, stereo
                            .getLabel());
        }

        operation.setName(defaultName);
        // Set the display label
        PrimitivesUtil.setDisplayLabel(operation, defaultName, false);

        return operation;
    }
}
