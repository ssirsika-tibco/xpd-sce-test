/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.ui.importexport.maa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage;

/**
 * @author bharge
 * 
 */
public class MAAInputOutputSelectionWizardPage extends
        AbstractInputOutputSelectionWizardPage {

    protected static final String MAA_EXT = "maa"; //$NON-NLS-1$

    /**
     * @param selection
     */
    public MAAInputOutputSelectionWizardPage(IStructuredSelection selection) {
        super(selection);
        setIsExternalLocationToFile(true);
        setShouldExternalFolderExist(false);
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getFileDialog(org.eclipse.swt.widgets.Shell)
     * 
     * @param shell
     * @return
     */
    @Override
    protected FileDialog getFileDialog(Shell shell) {
        FileDialog dlg = new FileDialog(shell, SWT.SAVE);
        dlg.setText(Messages.MAAInputOutputSelectionWizardPage_newMAA_fileDialog_title);
        dlg.setFilterExtensions(new String[] { "*." + MAA_EXT }); //$NON-NLS-1$

        String path = getLocationPath();
        if (path != null && !path.isEmpty()) {
            dlg.setFilterPath(path);
        }

        return dlg;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage_Old#updatePageCompletion()
     * 
     */
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
            enableLocalPath(true);
            return;
        } else {
            if (selectedDAVResources.size() > 1) {
                // Multiple projects selected - so only allow export to
                // external path
                enableLocalPath(false);
            } else {
                enableLocalPath(true);
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#updateTextPathOnFocusLost(java.lang.String)
     * 
     * @param path
     * @return
     */
    @Override
    protected String updateTextPath(String filePath) {
        /*
         * Location path has to be an MAA file.
         */
        if (filePath != null && !filePath.isEmpty()) {
            IPath path = new Path(filePath);
            if (path.getFileExtension() == null
                    || !path.getFileExtension().equals(MAA_EXT)) {
                filePath =
                        path.removeFileExtension().addFileExtension(MAA_EXT)
                                .toOSString();
            }
        }
        return filePath;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.exportwizard.pages.AbstractInputOutputSelectionWizardPage#getWorkspaceExportFolder()
     * 
     * @return
     */
    @Override
    protected String getWorkspaceExportFolder() {
        return "/" + Messages.ExportFolder_title + "/" + Messages.MAAFolder_title; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ViewerFilter[] createViewerFilters() {
        List<ViewerFilter> filters =
                new ArrayList<ViewerFilter>(Arrays.asList(super
                        .createViewerFilters()));

        /*
         * Only show Studio projects.
         */
        filters.add(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof IProject) {
                    IProject p = (IProject) element;
                    if (p.isAccessible() && ProjectUtil.isStudioProject(p))
                        return true;
                }
                return false;
            }
        });
        return filters.toArray(new ViewerFilter[filters.size()]);
    }

}
