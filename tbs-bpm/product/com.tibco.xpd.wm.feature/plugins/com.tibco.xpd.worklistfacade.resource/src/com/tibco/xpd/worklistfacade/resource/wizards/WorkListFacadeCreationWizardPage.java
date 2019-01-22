/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.resource.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.editor.util.WorkListFacadeEditorUtil;
import com.tibco.xpd.worklistfacade.resource.util.Messages;

/**
 * The Wizard page to create a Work List Facade File.
 * 
 */
public class WorkListFacadeCreationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    public WorkListFacadeCreationWizardPage(String pageName,
            IStructuredSelection selection) {
        super(pageName, selection);
        // removing Wizard page image as none o the process/BOM/OM assets have
        // img for Wizard Page.
        // Add selection validator to warn user if file not created in Work List
        // Facade special folder
        setSpecialFolderSelectionValidator(WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND,
                new Status(
                        IStatus.ERROR,
                        WorkListFacadeResourcePlugin.PLUGIN_ID,
                        Messages.WorkListFacadeCreationWizardPage_WrongFolder_Msg));

        // Only show Work List Facade special folders in the tree viewer
        addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {
                if (element instanceof SpecialFolder) {
                    return WorkListFacadeResourcePlugin.WLF_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());
                }
                return true;
            }
        });
    }

    /**
     * Returns the File extension for WorkListFacade.
     * 
     * @return
     */
    protected String getExtension() {
        return WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION;
    }

    public URI getURI() {
        return URI.createPlatformResourceURI(getFilePath().toString(), true);
    }

    /**
     * Returns the File path, with the WorkListFacade file extension '.wlf'
     * appended , if does not already end with the same.
     * 
     * @return
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

        // If the path does not have the correct file extension then append the
        // correct file extension
        if (path != null
                && (path.getFileExtension() == null || !path.getFileExtension()
                        .equalsIgnoreCase(getExtension()))) {
            path = path.addFileExtension(getExtension());
        }

        return path;
    }

    /**
     * Sets the default unique file name derived from the Project name/ existing
     * WorkListFacade files under the parent Special Folder.
     */
    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);
        setFileName(WorkListFacadeEditorUtil
                .getUniqueFileName(getContainerFullPath(),
                        getFileName(),
                        getExtension()));
        setPageComplete(validatePage());
    }
    // XPD-5819: Removing override of method getFileName(), NOT REQUIRED
}
