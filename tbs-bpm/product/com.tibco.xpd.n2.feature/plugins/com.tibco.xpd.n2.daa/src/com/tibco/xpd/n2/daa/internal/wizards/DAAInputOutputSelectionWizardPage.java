/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.internal.wizards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.internal.Messages;
import com.tibco.xpd.n2.daa.propertytester.DeployableBPMAssetsTester;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * @author bharge
 * 
 */
public class DAAInputOutputSelectionWizardPage extends
        AbstractInputOutputSelectionWizardPage {

    public DAAInputOutputSelectionWizardPage(IStructuredSelection selection) {
        super(selection, /* expandAllonInit = */true);
    }

    @Override
    protected String getWorkspaceExportFolder() {
        return "/" + Messages.ExportsFolder_title + "/" //$NON-NLS-1$ //$NON-NLS-2$
                + Messages.DAAFolder_title;
    }

    @SuppressWarnings("restriction")
    @Override
    protected void validatePageCompletion() {
        // Check that we have proper selection in the tree
        List<Object> selectedDAVResources = getSelectedObjects();
        if (selectedDAVResources.isEmpty()) {
            // No selection
            if (isInitialized()) {
                setErrorMessage(String
                        .format(Messages.InputOutputSelectionWizardPage_NoSelectionError_message));
            }
            setPageComplete(false);
            return;
        } else {
            for (Object object : selectedDAVResources) {
                if (object instanceof IProject) {
                    IProject eachSelectedProject = (IProject) object;
                    try {
                        boolean hasProjectErrorLevelProblemMarkers =
                                CompositeUtil
                                        .hasErrorLevelProblemMarkers(eachSelectedProject);
                        if (hasProjectErrorLevelProblemMarkers) {
                            setErrorMessage(Messages.InputOutputSelectionWizardPage_projects_contain_errors_label);
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

    @Override
    protected ViewerFilter[] createViewerFilters() {
        List<ViewerFilter> filters =
                new ArrayList<ViewerFilter>(Arrays.asList(super
                        .createViewerFilters()));

        filters.add(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                DeployableBPMAssetsTester tester =
                        new DeployableBPMAssetsTester();
                return tester
                        .test(element,
                                DeployableBPMAssetsTester.PROP_HAS_DEPLOYABLE_BPM_ASSETS,
                                new Object[] {},
                                null);
            }
        });
        return filters.toArray(new ViewerFilter[filters.size()]);
    }

}
