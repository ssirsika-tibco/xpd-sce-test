/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.ProfileApplicationCreateCommand;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;

/**
 * @generated
 */
public class PackageBadgeProfilesCompartmentItemSemanticEditPolicy extends
        UMLBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        if (UMLElementTypes.ProfileApplication_2006 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(UMLPackage.eINSTANCE
                        .getPackage_ProfileApplication());
            }
            return getGEFWrapper(new ProfileApplicationCreateCommand(req));
        }
        return super.getCreateCommand(req);
    }

}
