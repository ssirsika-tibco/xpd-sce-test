/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * Action to create a new XPD project.
 * 
 * @author njpatel
 * 
 */
public class NewProjectAction extends RegisterCommandAction implements
        ICancellableAction {

    private boolean isCancelled;

    private final boolean startNewApplication;

    /**
     * Add a new project to the existing application.
     * 
     * @param window
     */
    public NewProjectAction(IWorkbenchWindow window) {
        this(window, false);
    }

    /**
     * Create a new application and add a project to it, or add a new project to
     * an existing application.
     * 
     * @param window
     * @param startNewApplication
     *            <code>true</code> if a new application should be created and
     *            the project added to it, <code>false</code> if the project
     *            should be added to the existing application
     */
    public NewProjectAction(IWorkbenchWindow window, boolean startNewApplication) {
        super(window, Messages.NewProjectAction_title);
        setId("newProject"); //$NON-NLS-1$
        setToolTipText(Messages.NewProjectAction_tooltip);
        setImageDescriptor(RCPActivator
                .getImageDescriptor(RCPImages.NEW_PROJECT.getPath()));
        // Register for key-binding
        registerCommand("com.tibco.xpd.rcp.command.newProject"); //$NON-NLS-1$
        this.startNewApplication = startNewApplication;
    }

    @Override
    public void run() {
        isCancelled = true;
        BusyIndicator.showWhile(getWindow().getShell().getDisplay(),
                new Runnable() {
                    @Override
                    public void run() {
                        NewProjectWizard wizard =
                                new NewProjectWizard(RCPResourceManager
                                        .getResource(), startNewApplication);
                        WizardDialog dlg =
                                new WizardDialog(getWindow().getShell(), wizard);
                        // Make the dialog as small as possible
                        dlg.setPageSize(0, 130);
                        if (dlg.open() == WizardDialog.OK) {
                            isCancelled = false;
                        }
                    }
                });
    }

    /**
     * @see com.tibco.xpd.rcp.internal.actions.ICancellableAction#isCancelled()
     * 
     * @return
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

}
