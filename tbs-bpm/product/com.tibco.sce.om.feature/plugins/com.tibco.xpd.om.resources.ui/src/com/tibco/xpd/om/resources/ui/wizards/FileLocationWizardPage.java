/*
 * Copyright (c) TIBCO Software Inc 2008. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.wizards;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

public class FileLocationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    private static final String DEFAULT_OM_BASE_FILE_NAME = Messages.FileLocationWizardPage_defaultOM_filename;
    private final String fileExtension;

    public FileLocationWizardPage(String pageName,
            IStructuredSelection selection, String fileExtension) {
        super(pageName, selection);
        this.fileExtension = fileExtension;

        // Set the new file extension
        if (fileExtension != null) {
            setFileExtension(fileExtension);
        }

    }

    protected String getExtension() {
        return fileExtension;
    }

    public URI getURI() {
        return URI.createPlatformResourceURI(getFilePath().toString(), false);
    }

    public IPath getFilePath() {
        IPath path = getContainerFullPath();
        if (path == null) {
            path = new Path(""); //$NON-NLS-1$
        }
        String fileName = getFileName();
        if (fileName != null) {
            path = path.append(fileName);
        }
        return path;
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(getUniqueFileName(getContainerFullPath(), getFileName(),
                getExtension()));
        setPageComplete(validatePage());
    }

    public static String getUniqueFileName(IPath containerFullPath,
            String fileName, String extension) {
        if (containerFullPath == null) {
            containerFullPath = new Path(""); //$NON-NLS-1$
        }
        if (fileName == null || fileName.trim().length() == 0) {
            fileName = DEFAULT_OM_BASE_FILE_NAME;
        }
        IPath filePath = containerFullPath.append(fileName);
        if (extension != null && !filePath.lastSegment().endsWith(extension)) {
            filePath = filePath.addFileExtension(extension);
        }
        fileName = filePath.lastSegment();
        if (extension != null && extension.trim().length() != 0) {
            String extensionWithDot = '.' + extension.trim();
            fileName = fileName.substring(0, fileName
                    .lastIndexOf(extensionWithDot));
        }
        int i = 0;
        while (ResourcesPlugin.getWorkspace().getRoot().exists(filePath)) {
            i++;
            filePath = containerFullPath.append(fileName + i);
            if (extension != null) {
                filePath = filePath.addFileExtension(extension);
            }
        }
        return filePath.lastSegment();
    }
}
