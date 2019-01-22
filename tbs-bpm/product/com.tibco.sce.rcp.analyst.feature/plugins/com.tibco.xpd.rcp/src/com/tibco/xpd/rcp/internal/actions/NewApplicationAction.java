/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.utils.SplashDialog;

/**
 * Action to create a new application.
 * 
 * @author njpatel
 * 
 */
public class NewApplicationAction extends RegisterCommandAction {

    public NewApplicationAction(IWorkbenchWindow window) {
        super(window, Messages.NewApplicationAction_title);
        setId("newMaa"); //$NON-NLS-1$
        setToolTipText(Messages.NewApplicationAction_tooltip);
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
        // Register for key-binding
        //        registerCommand("com.tibco.xpd.rcp.command.newProject"); //$NON-NLS-1$
    }

    @Override
    public void run() {
        SplashDialog dlg = new SplashDialog(getWindow().getShell());
        dlg.open();
    }

}
