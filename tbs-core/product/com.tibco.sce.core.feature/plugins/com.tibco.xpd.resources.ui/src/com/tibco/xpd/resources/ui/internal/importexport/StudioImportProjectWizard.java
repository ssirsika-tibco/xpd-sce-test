/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.importexport;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Studio project import wizard. This will import projects using Eclipse's
 * import mechanism and then ensure all derived resources (marked in Team ignore
 * resources) are removed.
 * 
 * @author njpatel
 * @since 3.5.2
 */
public class StudioImportProjectWizard extends Wizard implements IImportWizard {

    private static final String EXTERNAL_PROJECT_SECTION =
            "ExternalProjectImportWizard";//$NON-NLS-1$

    private ProjectsImportPage importPage;

    /**
     * Studio project import wizard.
     */
    public StudioImportProjectWizard() {
        setNeedsProgressMonitor(true);
        IDialogSettings workbenchSettings =
                XpdResourcesUIActivator.getDefault().getDialogSettings();

        IDialogSettings wizardSettings =
                workbenchSettings.getSection(EXTERNAL_PROJECT_SECTION);
        if (wizardSettings == null) {
            wizardSettings =
                    workbenchSettings.addNewSection(EXTERNAL_PROJECT_SECTION);
        }
        setDialogSettings(wizardSettings);
        setWindowTitle(Messages.StudioImportProjectWizard_title);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        importPage = new ProjectsImportPage();
        addPage(importPage);
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        // Nothing to do here
    }

    @Override
    public boolean performFinish() {
        XpdResourcesPlugin.getDefault().setIsProjectsImportInProgress(true);
        try {
            return importPage.createProjects();
        } finally {
            XpdResourcesPlugin.getDefault()
                    .setIsProjectsImportInProgress(false);
        }
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     * 
     * @return
     */
    @Override
    public boolean performCancel() {
        importPage.performCancel();
        return true;
    }
}
