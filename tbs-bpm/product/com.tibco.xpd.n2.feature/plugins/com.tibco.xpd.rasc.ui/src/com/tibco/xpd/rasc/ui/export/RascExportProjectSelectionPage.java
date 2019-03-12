/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.rasc.ui.Messages;
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
     * Constructor.
     * 
     * @param selection
     *            The initial project selection.
     */
    public RascExportProjectSelectionPage(IStructuredSelection selection) {
        super(selection);
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#validatePageCompletion()
     */
    @Override
    protected void validatePageCompletion() {
        List<Object> selected = getSelectedObjects();
        if (selected != null && !selected.isEmpty()) {
            setPageComplete(true);
            setMessage(null);
        } else {
            setPageComplete(false);
            setMessage(
                    Messages.RascExportProjectSelectionPage_NoProjectSelectedError,
                    ERROR);
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return "Export"; //$NON-NLS-1$
    }

    /**
     * @return The list of selected projects.
     */
    public List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<>();
        for (Object item : getSelectedObjects().toArray()) {
            if (item instanceof IProject) {
                projects.add((IProject) item);
            }
        }
        return projects;
    }

}
