/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.daa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;

import com.tibco.xpd.daa.internal.util.CompositeUtil;
import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.openspacegwtgadget.integration.core.OpenspaceGadgetNature;
import com.tibco.xpd.openspacegwtgadget.integration.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * Project Selection page for Export Openspace gadget Development project DAA.
 * 
 * @author aallway
 * @since 24 Jan 2013
 */
class OpenspaceExportDAAProjectSelectionPage extends
        AbstractInputOutputSelectionWizardPage {

    /**
     * @param selection
     */
    public OpenspaceExportDAAProjectSelectionPage(IStructuredSelection selection) {
        super(selection);

    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#createViewerFilters()
     * 
     * @return
     */
    @Override
    protected ViewerFilter[] createViewerFilters() {
        /*
         * Use the default filters (restrict to the objects supported by
         * underlying class to start with).
         */
        ViewerFilter[] defaultFilters = super.createViewerFilters();

        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

        if (defaultFilters != null && defaultFilters.length > 0) {
            filters.addAll(Arrays.asList(defaultFilters));
        }

        /*
         * Then also filter all but Openspace gadget Dev enabled projects.
         */
        ViewerFilter openspaceGadgetProjectFilter = new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                if (element instanceof IProject
                        && OpenspaceGadgetNature
                                .isOpenspaceGadgetDevProject((IProject) element)) {
                    return true;
                }
                return false;
            }
        };

        filters.add(openspaceGadgetProjectFilter);

        return filters.toArray(new ViewerFilter[0]);
    }

    /**
     * @return List of selected projects
     */
    protected List<IProject> getSelectedProjects() {
        List<IProject> projects = new ArrayList<IProject>();
        for (Object o : getSelectedObjects()) {
            if (o instanceof IProject) {
                projects.add((IProject) o);
            }
        }
        return projects;
    }

    /**
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * 
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        validatePageCompletion();
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#validatePageCompletion()
     * 
     */
    @Override
    protected void validatePageCompletion() {
        if (!isInitialized()) {
            setPageComplete(false);
            return;
        }

        List<Object> selectedObjects = getSelectedObjects();
        if (selectedObjects.isEmpty()) {
            setErrorMessage(Messages.OpenspaceExportDAAProjectSelectionPage_SelectProjects_message);

            setPageComplete(false);
            return;
        }

        for (Object selectedObject : selectedObjects) {
            if (selectedObject instanceof IProject
                    && OpenspaceGadgetNature
                            .isOpenspaceGadgetDevProject((IProject) selectedObject)) {
                try {
                    if (CompositeUtil
                            .hasErrorLevelProblemMarkers((IProject) selectedObject)) {
                        setErrorMessage(String
                                .format(Messages.OpenspaceExportDAAProjectSelectionPage_ProjectHasProblems_message,
                                        ((IProject) selectedObject).getName()));
                        setPageComplete(false);
                        return;

                    }
                } catch (CoreException e) {
                    OpenspaceGadgetPlugin.getDefault().getLogger().error(e);
                    setPageComplete(false);
                    return;
                }

            } else {
                /* Should not be possible! */
                OpenspaceGadgetPlugin
                        .getDefault()
                        .getLogger()
                        .error(new Exception(
                                "Unspected selection in export DAA dialog: " //$NON-NLS-1$
                                        + selectedObject));
                setPageComplete(false);
                return;
            }
        }

        /*
         * Finally, ensure that there are no dirty editors (it's safer to ensure
         * that things are saved and built prior to exporting DAA.
         */
        if (workspaceHasDirtyResources()) {
            setErrorMessage(Messages.OpenspaceExportDAAProjectSelectionPage_MustSaveResources_message);
            setPageComplete(false);
            return;
        }

        /* Everything ok, Wipe error message and allow Finish. */
        setErrorMessage(null);
        setPageComplete(true);
    }

    /**
     * @return <code>true</code> if there are unsaved dirty resource in
     *         workspace.
     */
    private boolean workspaceHasDirtyResources() {
        IWorkbenchWindow windows[] =
                PlatformUI.getWorkbench().getWorkbenchWindows();

        for (int i = 0; i < windows.length; i++) {
            IWorkbenchPage pages[] = windows[i].getPages();
            for (int j = 0; j < pages.length; j++) {
                WorkbenchPage page = (WorkbenchPage) pages[j];

                ISaveablePart[] parts = page.getDirtyParts();

                if (parts != null && parts.length > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     * 
     * @return
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return IPath.SEPARATOR
                + Messages.OpenspaceExportDAAProjectSelectionPage_ExportsFolder_resource_label
                + IPath.SEPARATOR
                + Messages.OpenspaceExportDAAProjectSelectionPage_DistributedAppArchiveFolder_resource_label;
    }

}
