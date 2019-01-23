/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.wizard;

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
import com.tibco.xpd.rest.schema.JsonSchemaWorkingCopy;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rest.schema.ui.internal.Messages;
import com.tibco.xpd.rest.schema.ui.internal.RestSchemaUiPlugin;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * JSON Schema wizard page for specifying the target folder and file name of the
 * JSON schema file.
 * 
 * @author nwilson
 * @since 9 Jan 2015
 */
public class JsonSchemaFilePage extends
        WizardNewFileInSpecialFolderCreationPage {

    public JsonSchemaFilePage(IStructuredSelection selection) {
        this(Messages.JsonSchemaFilePage_Title, selection);
    }

    /**
     * @param selection
     *            The current navigator selection.
     */
    public JsonSchemaFilePage(String title, IStructuredSelection selection) {
        super(title, selection);
        setSpecialFolderSelectionValidator(RestConstants.REST_SPECIAL_FOLDER_KIND,
                new Status(IStatus.ERROR, RestSchemaUiPlugin.PLUGIN_ID,
                        Messages.JsonSchemaFilePage_Description));

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
                && (path.getFileExtension() == null || !path
                        .getFileExtension()
                        .equalsIgnoreCase(JsonSchemaWorkingCopy.JSD_FILE_EXTENSION))) {
            path =
                    path.addFileExtension(JsonSchemaWorkingCopy.JSD_FILE_EXTENSION);
        }
        return path;
    }

    /**
     * Sets the default unique file name derived from the Project name/ existing
     * JSON Schema files under the parent Special Folder.
     */
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(getUniqueFileName(getContainerFullPath(),
                getFileName(),
                JsonSchemaWorkingCopy.JSD_FILE_EXTENSION));
        setPageComplete(validatePage());
    }

    /**
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
            fileName = Messages.JsonSchemaFilePage_defaultJsonSchemaFileName;
        }
        // Check and derive the next unique filename.
        return SpecialFolderUtil.getUniqueFileName(containerFullPath,
                fileName,
                extension);
    }
}
