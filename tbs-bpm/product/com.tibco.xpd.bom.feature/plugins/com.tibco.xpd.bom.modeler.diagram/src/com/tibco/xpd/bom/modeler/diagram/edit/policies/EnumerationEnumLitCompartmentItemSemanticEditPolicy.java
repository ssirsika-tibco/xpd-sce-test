/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.EnumerationLiteralCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class EnumerationEnumLitCompartmentItemSemanticEditPolicy extends
        UMLBaseItemSemanticEditPolicy {

    /**
     * 
     * @generated NOT
     * 
     * Mark the request with the property that the edit helper will not create
     * the another create command. 
     */

    protected Command getCreateCommand(CreateElementRequest req) {
        req.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND,
                Boolean.TRUE);
        return getCreateCommandGen(req);
    }

    /**
     * @generated
     */
    protected Command getCreateCommandGen(CreateElementRequest req) {
        if (UMLElementTypes.EnumerationLiteral_2003 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getEnumeration_OwnedLiteral());
            }
            return getGEFWrapper(new EnumerationLiteralCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

}
