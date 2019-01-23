/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * Action to exit the application.
 * 
 * @author njpatel
 * 
 */
public class ExitAction extends RegisterCommandAction {
    private final IWorkbenchWindow window;

    public ExitAction(IWorkbenchWindow window) {
        super(window, Messages.ExitAction_title);
        this.window = window;
        setId("exit"); //$NON-NLS-1$
        registerCommand("com.tibco.xpd.rcp.command.exit"); //$NON-NLS-1$
    }

    @Override
    public void run() {
        /*
         * If possible close the shell (same action as user clicking on the x on
         * the title bar) - this will ensure that this goes through the
         * pre-close in the workbench window advisor where a close handler is
         * defined to save any changes.
         */
        if (window.getWorkbench() != null) {
            Display display = window.getWorkbench().getDisplay();
            if (display != null && display.getActiveShell() != null) {
                display.getActiveShell().close();
                return;
            }
        }
        window.close();
    }
}