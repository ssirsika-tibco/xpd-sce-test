/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.dbimport.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.tibco.xpd.bom.dbimport.DBImageConstants;
import com.tibco.xpd.bom.dbimport.DBImportPlugin;
import com.tibco.xpd.bom.dbimport.ImportFromDbAction;
import com.tibco.xpd.bom.dbimport.internal.Messages;

/**
 * Import Database to BOM.
 * 
 * @author rsomayaj
 * 
 */
public class ImportDb2BomWizard extends Wizard implements IWorkbenchWizard {

    protected ImportFromDbAction action;

    private Object selectedObject;

    /**
     * Import UML Wizard.
     */
    public ImportDb2BomWizard() {
        setWindowTitle(Messages.ImportDb2BomWizard_WindowTitle_title);
        setDefaultPageImageDescriptor(DBImportPlugin.getDefault()
                .getImageRegistry()
                .getDescriptor(DBImageConstants.IMG_BOM_IMPORT_WIZARD));
        setForcePreviousAndNextButtons(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     * 
     * @return
     */
    @Override
    public boolean performFinish() {
        return true;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     * 
     */
    @Override
    public void addPages() {
        addPage(new ProfileSelectionPage(
                Messages.ImportDb2BomWizard_SelectProfilePage_label,
                selectedObject));
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param workbench
     * @param selection
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        selectedObject = selection.getFirstElement();
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     * 
     * @param page
     * @return
     */
    @Override
    public IWizardPage getNextPage(IWizardPage page) {
        return super.getNextPage(page);
    }

}
