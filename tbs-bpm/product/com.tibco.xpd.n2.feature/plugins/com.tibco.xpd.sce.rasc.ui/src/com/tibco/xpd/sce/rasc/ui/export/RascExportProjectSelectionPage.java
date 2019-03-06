/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.rasc.ui.export;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * Initial project and export location selection page for the RASC export
 * wizard.
 *
 * @author nwilson
 * @since 5 Mar 2019
 */
public class RascExportProjectSelectionPage
        extends AbstractInputOutputSelectionWizardPage {

    /**
     * @param selection
     */
    public RascExportProjectSelectionPage(IStructuredSelection selection) {
        super(selection);
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#validatePageCompletion()
     *
     */
    @Override
    protected void validatePageCompletion() {
        List<Object> selected = getSelectedObjects();
        setPageComplete(selected != null && !selected.isEmpty());
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     *
     * @return
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return "Export";
    }

}
