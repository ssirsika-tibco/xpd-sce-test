/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.editor.util.GsdEditorUtil;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage;

/**
 * Wizard page to create a .gsd file.
 * 
 * @author sajain
 * @since Jan 30, 2015
 */
public class GsdCreationWizardPage extends
        WizardNewFileInSpecialFolderCreationPage {

    /**
     * @param pageName
     * @param selection
     */
    public GsdCreationWizardPage(String pageName,
            IStructuredSelection selection) {

        super(pageName, selection);

        /*
         * Add selection validator to warn user if file not created in GSD
         * special folder
         */
        setSpecialFolderSelectionValidator(GsdResourcePlugin.GSD_SPECIAL_FOLDER_KIND,
                new Status(
                        IStatus.ERROR,
                        GsdResourcePlugin.PLUGIN_ID,
                        Messages.GlobalSignalDefinitionCreationWizardPage_WrongFolder_Msg));

        /*
         * Only show Global Signal Definition special folders in the tree viewer
         */
        addFilter(new ViewerFilter() {
            @Override
            public boolean select(Viewer viewer, Object parentElement,
                    Object element) {

                if (element instanceof SpecialFolder) {

                    return GsdResourcePlugin.GSD_SPECIAL_FOLDER_KIND
                            .equals(((SpecialFolder) element).getKind());

                }
                return true;
            }
        });
    }

    /**
     * Returns the File extension for Global Signal Definition.
     * 
     * @return
     */
    protected String getExtension() {

        return GsdResourcePlugin.GSD_SPECIAL_FOLDER_KIND;
    }

    public URI getURI() {

        return URI.createPlatformResourceURI(getFilePath().toString(), true);
    }

    /**
     * Returns the File path, with the GlobalSignalDefinition file extension
     * '.gsd' appended , if does not already end with the same.
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

        /*
         * If the path does not have the correct file extension then append the
         * correct file extension
         */
        if (path != null
                && (path.getFileExtension() == null || !path.getFileExtension()
                        .equalsIgnoreCase(getExtension()))) {

            path = path.addFileExtension(getExtension());
        }

        return path;
    }

    /**
     * Sets the default unique file name derived from the Project name/ existing
     * Global Signal Definition files under the parent Special Folder.
     */
    @Override
    public void createControl(Composite parent) {

        super.createControl(parent);

        setFileName(GsdEditorUtil
                .getUniqueFileName(getContainerFullPath(),
                        getFileName(),
                        getExtension()));

        setPageComplete(validatePage());
    }
}
