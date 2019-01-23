/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.StructuredViewer;

import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.ui.properties.CommandProvider;

/**
 * Generic action to invoke the command provider.
 * 
 * @author nwilson
 * @since 11 Feb 2015
 */
class CommandViewerAction extends ViewerAction {
    /**
     * The command provider.
     */
    private CommandProvider provider;

    /**
     * @param viewer
     *            The viewer associated with the action.
     * @param provider
     *            The command provider.
     */
    public CommandViewerAction(StructuredViewer viewer, CommandProvider provider) {
        super(viewer);
        this.provider = provider;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        Command cmd = provider.getCommand(this);
        if (cmd != null && cmd.canExecute()) {
            EditingDomain ed = provider.getEditingDomain();
            ed.getCommandStack().execute(cmd);
        }
    }
}