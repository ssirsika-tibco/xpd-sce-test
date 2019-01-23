/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.modeler.diagram.edit.policies.custom;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelBaseItemSemanticEditPolicy;

/**
 * 
 * Semantic Editpolicy for group items.
 * 
 * @author rgreen
 * 
 */
public class OrgModelGroupItemSemanticEditPolicy extends
        OrganizationModelBaseItemSemanticEditPolicy {

    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        CompoundCommand cc = getDestroyEdgesCommand();
        addDestroyShortcutsCommand(cc);
        cc.add(getGEFWrapper(new DestroyElementCommand(req)));
        return cc.unwrap();
    }

}
