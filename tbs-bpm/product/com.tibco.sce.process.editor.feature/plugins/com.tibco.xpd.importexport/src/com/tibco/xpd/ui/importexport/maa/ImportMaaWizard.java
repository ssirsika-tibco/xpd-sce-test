/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.importexport.maa;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.tibco.xpd.importexport.internal.Messages;

/**
 * Imports Modelled Application Archive (MAA) file into workspace
 * 
 * @author bharge
 * 
 */
public class ImportMaaWizard extends Wizard implements IImportWizard {

    private MAAImportWizardPage maaImportWizardPage = null;

    public ImportMaaWizard() {
        super();
        setWindowTitle(Messages.MaaFileImportWizard_title);
        this.maaImportWizardPage = new MAAImportWizardPage();
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        addPage(maaImportWizardPage);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        return maaImportWizardPage.createProjects();
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setDefaultPageImageDescriptor(IDEWorkbenchPlugin
                .getIDEImageDescriptor("wizban/importproj_wiz.png")); //$NON-NLS-1$

    }

}
