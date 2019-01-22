/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.IHandlerService;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * Action to show the About dialog.
 * 
 * @author njpatel
 * 
 */
public class AboutAction extends Action {
    private final IWorkbenchWindow window;

    public AboutAction(IWorkbenchWindow window) {
        this.window = window;
        setId("about"); //$NON-NLS-1$
        setText(Messages.AboutAction_title);
    }

    @Override
    public void run() {
        IHandlerService handlerService =
                (IHandlerService) window.getService(IHandlerService.class);
        try {
            handlerService.executeCommand("org.eclipse.ui.help.aboutAction", //$NON-NLS-1$
                    null);
        } catch (Exception ex) {
            throw new RuntimeException("About not found"); //$NON-NLS-1$
        }
    }
}