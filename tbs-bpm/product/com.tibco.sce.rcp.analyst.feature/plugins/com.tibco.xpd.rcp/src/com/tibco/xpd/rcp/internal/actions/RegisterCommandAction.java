/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.IHandlerService;

/**
 * To be implemented by any action that wishes to register with the command
 * handler service.
 * 
 * @author njpatel
 * 
 */
public abstract class RegisterCommandAction extends Action {

    private final IWorkbenchWindow window;

    public RegisterCommandAction(IWorkbenchWindow window, String title) {
        super(title);
        this.window = window;
    }

    /**
     * Get the workbench window.
     * 
     * @return
     */
    protected IWorkbenchWindow getWindow() {
        return window;
    }

    /**
     * Register the given command id with this action.
     * 
     * @param window
     * @param commandId
     */
    protected void registerCommand(String commandId) {
        if (commandId != null) {
            // Register for key-binding
            IHandlerService service =
                    (IHandlerService) window.getService(IHandlerService.class);
            service.activateHandler(commandId, new ActionHandler(this));
        }
    }
}
