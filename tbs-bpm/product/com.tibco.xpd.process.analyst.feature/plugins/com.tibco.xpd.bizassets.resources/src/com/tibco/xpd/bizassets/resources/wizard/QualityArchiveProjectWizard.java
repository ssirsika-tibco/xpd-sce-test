/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.bizassets.resources.internal.Messages;
import com.tibco.xpd.bizassets.resources.quality.QualityArchiveNature;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.wizards.newproject.ProjectSelectionPage;

/**
 * @author nwilson
 */
public class QualityArchiveProjectWizard extends Wizard implements INewWizard {

    private static final String TEMPLATE_NATURE_ID =
            "com.tibco.xpd.processeditor.xpdl2.templateNature"; //$NON-NLS-1$

    private ProjectSelectionPage projectSelectionPage;

    private QualityArchiveProjectContentsPage projectContentsPage;

    /**
     * 
     */
    public QualityArchiveProjectWizard() {
        setWindowTitle(Messages.QualityArchiveProjectWizard_WizardTitle);
    }

    /**
     * @param workbench
     * @param selection
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        projectSelectionPage =
                new ProjectSelectionPage(
                        Messages.QualityArchiveProjectWizard_SelectProjectPageName);
        projectSelectionPage
                .setTitle(Messages.QualityArchiveProjectWizard_SelectProjectTitle);
        projectSelectionPage
                .setDescription(Messages.QualityArchiveProjectWizard_SelectProjectMessage);
        projectSelectionPage.hideProjectLifecycle();

        projectContentsPage = new QualityArchiveProjectContentsPage();
    }

    @Override
    public void addPages() {
        addPage(projectSelectionPage);
        addPage(projectContentsPage);
    }

    /**
     * @return
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        boolean finish = false;
        final IProject project = projectSelectionPage.getProjectHandle();
        final String filename = projectContentsPage.getSelectedFile();
        if (!project.exists()) {
            WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {
                    project.create(monitor);
                    project.open(monitor);
                    ProjectUtil.addNature(project, TEMPLATE_NATURE_ID);
                    ProjectUtil.addNature(project,
                            QualityArchiveNature.NATURE_ID);
                    if (filename != null) {
                        QualityArchiveUtil.extractArchive(project, filename);
                    }
                }

            };
            try {
                getContainer().run(false, true, op);
                finish = true;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return finish;
    }

}
