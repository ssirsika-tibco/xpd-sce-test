/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Project asset configuration object to set up a special folder. This should be
 * used by an asset type that needs to be associated with a special folder.
 * <p>
 * To get the current special folder name use
 * <code>{@link #getSpecialFolderName()}</code>. Note that if the selection is
 * set (when an asset is being configured for an existing project) then
 * <code>{@link #getSelection()}</code> should be used to determine the name of
 * the selected folder.
 * </p>
 * <p>
 * This configuration class should be used in conjunction with
 * <code>{@link SpecialFolderAssetConfigurator}</code> and
 * <code>{@link SpecialFolderAssetWizardPageFactory}</code>
 * </p>
 * <p>
 * This class can be extended.
 * </p>
 * 
 * @see SpecialFolderAssetConfigurator
 * @see SpecialFolderAssetWizardPageFactory
 * 
 * @author njpatel
 * 
 */
public class SpecialFolderAssetConfiguration {

    private String folderName;

    private IStructuredSelection selection;

    /**
     * Set the name of the folder that will be marked as a special folder.
     * 
     * @param folderName
     */
    public void setSpecialFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * Get the name of the folder that will be marked as a special folder.
     * 
     * @return
     */
    public String getSpecialFolderName() {
        return folderName != null ? folderName : ""; //$NON-NLS-1$
    }

    /**
     * Set the current selection.
     * 
     * @param selection
     */
    public void setSelection(IStructuredSelection selection) {
        this.selection = selection;

        // Update the special folder name from the selection
        if (selection != null && selection.getFirstElement() instanceof IFolder) {
            setSpecialFolderName(((IFolder) selection.getFirstElement())
                    .getProjectRelativePath().toString());
        }
    }

    /**
     * Get the current selection.
     * 
     * @return
     */
    public IStructuredSelection getSelection() {
        return selection;
    }
}
