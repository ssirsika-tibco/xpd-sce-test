/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rsd.ui.RsdUIPlugin;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * REST Service Descriptor wizard page for specifying the target folder and file
 * name of the RSD file.
 * 
 * @author jarciuch
 */
public class RsdFilePage extends WizardNewFileInSpecialFolderCreationPage {

    public RsdFilePage(IStructuredSelection selection) {
        this(Messages.RsdFilePage_NewRsd_message, selection);
    }

    /**
     * @param selection
     *            The current navigator selection.
     */
    public RsdFilePage(String title, IStructuredSelection selection) {
        super(title, selection);
        setSpecialFolderSelectionValidator(RestConstants.REST_SPECIAL_FOLDER_KIND,
                new Status(IStatus.ERROR, RsdUIPlugin.PLUGIN_ID,
                        Messages.RsdFilePage_InvalidSelection_message));

        // Only show REST special folders in the tree viewer
        addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof SpecialFolder) {
                    return RestConstants.REST_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());
                }
                return true;
            }
        });
    }

    /**
     * @return The full path of the chosen file.
     */
    public IPath getFilePath() {
        IPath path = getContainerFullPath();
        if (path == null) {
            path = new Path(""); //$NON-NLS-1$
        }
        String fileName = getFileName();
        if (fileName != null) {
            path = path.append(fileName);
        }
        // If the path does not have the correct file extension then append the
        // correct file extension
        if (path != null
                && (path.getFileExtension() == null || !path.getFileExtension()
                        .equalsIgnoreCase(RsdUIPlugin.RSD_EXTENSION))) {
            path = path.addFileExtension(RsdUIPlugin.RSD_EXTENSION);
        }
        return path;
    }

    /**
     * Sets the default unique file name derived from the Project name/ existing
     * RSD files under the parent Special Folder.
     */
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(getUniqueFileName(getContainerFullPath(),
                getFileName(),
                RsdUIPlugin.RSD_EXTENSION));
        setPageComplete(validatePage());
    }

    /**
     * Gets a unique name for the file.
     * 
     * @param containerFullPath
     *            container path.
     * @param fileName
     *            Current file name.
     * @param extension
     *            File extension.
     * @return A unique file name.
     */
    private String getUniqueFileName(IPath containerFullPath, String fileName,
            String extension) {
        if (containerFullPath == null) {
            containerFullPath = new Path(""); //$NON-NLS-1$
        }

        if (fileName == null || fileName.trim().length() == 0) {
            String projectName = getProjectName(containerFullPath);
            fileName =
                    projectName != null ? projectName
                            : Messages.RsdFilePage_DefaultRsdFileName;
        }
        // Check and derive the next unique filename.
        return SpecialFolderUtil.getUniqueFileName(containerFullPath,
                fileName,
                extension);
    }

    /**
     * Returns the project name for the provided path and <code>null</code> if
     * the project name can't be determined.
     */
    private String getProjectName(IPath path) {
        if (path.segmentCount() > 0) {
            return path.segment(0);
        }
        return null;
    }
}
