/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.helpers;

import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;

import com.tibco.xpd.bom.modeler.diagram.edit.commands.custom.BOMMoveElementsCommand;

/**
 * @generated
 */
public class UMLBaseEditHelper extends AbstractEditHelper {

    /**
     * @generated
     */
    public static final String EDIT_POLICY_COMMAND = "edit policy command"; //$NON-NLS-1$

    /**
     * @generated NOT
     */
    @Override
    protected ICommand getInsteadCommand(IEditCommandRequest req) {
    	/*
    	 * XPD-8355: (Nick) The new GMF, when executing a GeneralizationCreateCommand, also adds a second ConfigureRequest
    	 * command to set the source and target. This method was seeing the ConfigureRequest and replacing that second
    	 * command with the first GeneralizationCreateCommand. This resulted in a further ConfigureRequest command being
    	 * created and it went through that loop again eventually causing a StackOverflowException.
    	 * 
    	 * This fix prevents ConfigureRequest commands being replaced.
    	 */
    	if (req instanceof ConfigureRequest) {
    		return null;
    	}
    	
        ICommand epCommand = (ICommand) req.getParameter(EDIT_POLICY_COMMAND);
        req.setParameter(EDIT_POLICY_COMMAND, null);
        ICommand ehCommand = super.getInsteadCommand(req);
        if (epCommand == null) {
            return ehCommand;
        }
        if (ehCommand == null) {
            return epCommand;
        }
        CompositeCommand command = new CompositeCommand(null);
        /*
         * XPD-4906: Need to swap the commands around - add the destroy children
         * command before destroy parent commands. Otherwise the parent gets
         * destroyed before the children. This causes the destruction of the
         * children to fail because they are no longer contained in a resource
         * (become proxies).
         */
        command.add(ehCommand);
        command.add(epCommand);
        return command;
    }

    /**
     * @generated
     */
    @Override
    protected ICommand getCreateCommand(CreateElementRequest req) {
        return null;
    }

    /**
     * @generated
     */
    @Override
    protected ICommand getCreateRelationshipCommand(
            CreateRelationshipRequest req) {
        return null;
    }

    /**
     * Changed that it will call super class. The super will route the request
     * to all dependent objects and give them a chance to destroy.
     * 
     * @generated NOT
     */
    @Override
    protected ICommand getDestroyElementCommand(DestroyElementRequest req) {
        return super.getDestroyElementCommand(req);
    }

    /**
     * @generated
     */
    @Override
    protected ICommand getDestroyReferenceCommand(DestroyReferenceRequest req) {
        return null;
    }

    @Override
    protected ICommand getMoveCommand(MoveRequest req) {
        // Use a custom move element command so that additional linked items can
        // be automatically moved
        return new BOMMoveElementsCommand(req);
    }

}
