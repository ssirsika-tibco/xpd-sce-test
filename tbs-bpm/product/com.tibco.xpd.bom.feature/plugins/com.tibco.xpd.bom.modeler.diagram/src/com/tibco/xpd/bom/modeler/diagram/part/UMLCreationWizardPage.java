/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.part;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * @generated
 */
public class UMLCreationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    /**
     * @generated NOT
     */
    private String fileExtension;

    /**
     * @generated
     */
    public UMLCreationWizardPage(String pageName,
            IStructuredSelection selection, String fileExtension) {
        super(pageName, selection);
        this.fileExtension = fileExtension;
        // Set the new file extension
        if (fileExtension != null) {
            setFileExtension(fileExtension);
        }

        // Add selection validator to warn user if file not created in BOM
        // special folder
        setSpecialFolderSelectionValidator(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND,
                new Status(IStatus.ERROR, BOMDiagramEditorPlugin.ID,
                        Messages.UMLCreationWizardPage_NotSpecialFolder));

        // XPD-4448: Only show BOM special folders in the tree viewer
        addFilter(new ViewerFilter() {

            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof SpecialFolder) {
                    return BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());
                }
                return true;
            }
        });
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
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(UMLDiagramEditorUtil
                .getUniqueFileName(getContainerFullPath(),
                        getFileName(),
                        getExtension()));
        setPageComplete(validatePage());
        // Show default message when page first shown
        setErrorMessage(null);
        setMessage(null);
    }

}
