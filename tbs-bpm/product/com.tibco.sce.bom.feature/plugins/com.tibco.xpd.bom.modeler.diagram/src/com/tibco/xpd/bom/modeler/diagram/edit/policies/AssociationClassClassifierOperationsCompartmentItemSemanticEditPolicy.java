/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.AssociationClassOperationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class AssociationClassClassifierOperationsCompartmentItemSemanticEditPolicy
        extends UMLBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        if (UMLElementTypes.Operation_2005 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getClass_OwnedOperation());
            }
            return getGEFWrapper(new AssociationClassOperationCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

}
