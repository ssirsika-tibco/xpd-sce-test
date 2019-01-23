/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class ProfileApplicationCreateCommand extends CreateElementCommand {

    /**
     * @generated
     */
    public ProfileApplicationCreateCommand(CreateElementRequest req) {
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

}
