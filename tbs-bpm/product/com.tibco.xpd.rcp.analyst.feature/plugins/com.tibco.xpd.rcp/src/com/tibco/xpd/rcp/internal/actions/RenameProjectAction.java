/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ltk.ui.refactoring.resource.RenameResourceWizard;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;

/**
 * Action to rename a XPD project.
 * 
 * @author mtorres
 * 
 */
public class RenameProjectAction extends RegisterCommandAction implements
        ICancellableAction {

    private boolean isCancelled;

    private IProject selectedProject;

    public RenameProjectAction(IWorkbenchWindow window) {
        super(window, Messages.RenameProjectAction_title);
        setId("renameProject"); //$NON-NLS-1$
        setToolTipText(Messages.RenameProjectAction_tooltip);
        setImageDescriptor(RCPActivator.getImageDescriptor(RCPImages.RENAME
                .getPath()));
        // Register for key-binding
        //        registerCommand("com.tibco.xpd.rcp.command.newProject"); //$NON-NLS-1$
    }

    @Override
    public void run() {
        isCancelled = true;
        BusyIndicator.showWhile(getWindow().getShell().getDisplay(),
                new Runnable() {
                    @Override
                    public void run() {
                        RenameResourceWizard wizard =
                                new RenameResourceWizard(selectedProject);
                        RefactoringWizardOpenOperation op =
                                new RefactoringWizardOpenOperation(wizard);
                        try {
                            op.run(getWindow().getShell(),
                                    Messages.RenameEditorAction_renameResource_dialog_title);
                        } catch (InterruptedException e1) {
                            // do nothing
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

    /**
     * @param selectedProject
     *            the selectedProject to set
     */
    public void setSelectedProject(IProject selectedProject) {
        this.selectedProject = selectedProject;
    }

}
