/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.n2.brm.internal.wizards;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.brm.internal.Messages;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * Wizard page used to select project to export.
 * 
 * @see WorkDataModelExportWizard
 * @author jarciuch
 */
public class WorkDataModelProjectSelectionPage extends
        AbstractInputOutputSelectionWizardPage {

    /**
     * Create an sinstance of the page.
     * 
     * @param selection
     *            initial selection.
     */
    public WorkDataModelProjectSelectionPage(IStructuredSelection selection) {
        super(selection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.
     * AbstractInputOutputSelectionWizardPage#validatePageCompletion()
     */
    @Override
    protected void validatePageCompletion() {
        // Check that we have proper selection in the tree
        List<Object> selectedResources = getSelectedObjects();
        if (selectedResources.isEmpty()) {
            // No selection
            if (isInitialized()) {
                setErrorMessage(String
                        .format(Messages.WorkDataModelProjectSelectionPage_EmptySelection_message));
            }
            setPageComplete(false);
            return;
        } else {
            for (Object object : selectedResources) {
                if (object instanceof IProject) {
                    IProject eachSelectedProject = (IProject) object;
                    try {
                        boolean hasProjectErrorLevelProblemMarkers =
                                CompositeUtil
                                        .hasErrorLevelProblemMarkers(eachSelectedProject);
                        if (hasProjectErrorLevelProblemMarkers) {
                            setErrorMessage(Messages.WorkDataModelProjectSelectionPage_SelectionWithErrors_message);
                            setPageComplete(false);
                            return;
                        }
                    } catch (CoreException e) {
                        Activator.getDefault().getLogger().error(e);
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.
     * AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return "/" + Messages.ExportsFolder_title + "/" + Messages.WorkDataModelFolder_title; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
