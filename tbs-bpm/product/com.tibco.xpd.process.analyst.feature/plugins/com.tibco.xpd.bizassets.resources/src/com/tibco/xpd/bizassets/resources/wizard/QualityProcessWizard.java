/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bizassets.resources.wizard;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.bizassets.resources.internal.Messages;

/**
 * @author nwilson
 */
public class QualityProcessWizard extends Wizard implements INewWizard {

    private FolderSelectionPage folderSelectionPage;

    private QualityArchiveWizardPage qualityArchivePage;

    public QualityProcessWizard() {
        setWindowTitle(Messages.QualityProcessWizard_WizardTitle);
    }

    /**
     * @param workbench
     * @param selection
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        folderSelectionPage = new FolderSelectionPage(selection);
        qualityArchivePage = new QualityArchiveWizardPage();
    }

    @Override
    public void addPages() {
        addPage(folderSelectionPage);
        addPage(qualityArchivePage);
    }

    /**
     * @return
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        IFolder folder = folderSelectionPage.getFolder();
        IProject project = qualityArchivePage.getQualityProject();
        if (project != null && folder != null) {
            try {
                QualityArchiveUtil.copyQualityProject(folder, project);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
