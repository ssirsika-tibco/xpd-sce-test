/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.destination.ProjectVersionRefactorWizard;

/**
 * Refactor action that will kick off the project version refactoring.
 * 
 * @author njpatel
 * 
 */
public class ProjectVersionRefactorAction implements IObjectActionDelegate {

    private IProject project;
    private Shell shell;
    private ProjectDetails details;

    /**
     * Refactor action that will kick off the project version refactoring.
     */
    public ProjectVersionRefactorAction() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
     * action.IAction, org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        shell = targetPart.getSite().getShell();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (project != null && details != null) {
            ProjectVersionRefactorWizard wizard = new ProjectVersionRefactorWizard(
                    Messages.ProjectVersionRefactorAction_refactor_title,
                    project, details);
            RefactoringWizardOpenOperation op = new RefactoringWizardOpenOperation(
                    wizard);
            try {
                op.run(shell,
                        Messages.ProjectVersionRefactorAction_refactor_title);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        boolean enabled = false;
        project = null;
        details = null;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sSel = (IStructuredSelection) selection;
            if (sSel.size() == 1 && sSel.getFirstElement() instanceof IProject) {
                project = (IProject) sSel.getFirstElement();

                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);
                if (config != null && config.getProjectDetails() != null) {
                    details = config.getProjectDetails();
                    enabled = true;
                }
            }
        }
        action.setEnabled(enabled);
    }
}
