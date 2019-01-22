/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * @generated
 */
public class ServiceTaskWsdlCreationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    /**
     * @generated NOT
     */
    private String fileExtension;

    private final String defaultFileName2;

    /**
     * @generated
     */
    public ServiceTaskWsdlCreationWizardPage(String pageName,
            IStructuredSelection selection, String fileExtension,
            String defaultFileName) {
        super(pageName, selection);
        this.fileExtension = fileExtension;
        defaultFileName2 = defaultFileName;
        setTitle(Messages.ServiceTaskWsdlCreationWizardPage_WindowTitle_label);
        setDescription(Messages.ServiceTaskWsdlCreationWizardPage_WindowDescription_shortdesc);
        // Set the new file extension
        if (fileExtension != null) {
            setFileExtension(fileExtension);
        }
        setOverwriteSeverity(IStatus.WARNING);

    }

    /**
     * Override to create files with this extension.
     * 
     * @generated
     */
    protected String getExtension() {
        return fileExtension;
    }

    public void setExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * @generated
     */
    public URI getURI() {
        return URI.createPlatformResourceURI(getFilePath().toString(), true);
    }

    /**
     * @generated
     */
    protected IPath getFilePath() {
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

    /**
     * @generated
     */
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(defaultFileName2);
        setPageComplete(validatePage());
        // Show default message when page first shown
        setErrorMessage(null);
        setMessage(null);
    }

}
