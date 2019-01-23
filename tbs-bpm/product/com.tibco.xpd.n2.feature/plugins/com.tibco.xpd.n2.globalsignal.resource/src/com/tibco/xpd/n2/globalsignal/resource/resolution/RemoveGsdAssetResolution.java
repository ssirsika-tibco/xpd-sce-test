/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.globalSignalDefinition.util.GsdConstants;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.validation.GsdProjectValidator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to remove GSD asset from the project config.
 * 
 * @author sajain
 * @since Apr 24, 2015
 */
public class RemoveGsdAssetResolution implements IResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#run(org.eclipse.core.resources.IMarker)
     * 
     * @param marker
     * @throws ResolutionException
     */
    @Override
    public void run(final IMarker marker) throws ResolutionException {

        /*
         * Get marker resource.
         */
        final IResource resource = marker.getResource();

        /*
         * Proceed if the user has said 'Yes' and the concerned resource is an
         * IProject instance.
         */
        if (resource instanceof IProject) {

            /*
             * Open the dialog to get a confirmation from the user that the GSD
             * assets/special folders will be removed.
             */
            MessageDialog messageDialog =
                    new MessageDialog(
                            Display.getDefault().getActiveShell(),
                            Messages.GsdProjecrtValidator_RemoveGSDAsset_warningDialogTitle,
                            null,
                            Messages.GsdProjecrtValidator_RemoveGSDAsset_warningDialogText,
                            MessageDialog.QUESTION, new String[] {
                                    Messages.Yes_Label, Messages.No_Label }, 1);

            int userResponse = messageDialog.open();

            if (userResponse == 0) {

                IWorkspaceRunnable configTestRunnable =
                        new IWorkspaceRunnable() {
                            @Override
                            public void run(IProgressMonitor monitor)
                                    throws CoreException {

                                IProject project = (IProject) resource;

                                /*
                                 * Remove GSD asset and special folder.
                                 */
                                removeGSDAssetAndSpecialFolder(project);
                            }

                            /**
                             * Remove GSD asset and special folder.
                             * 
                             * @param project
                             */
                            private void removeGSDAssetAndSpecialFolder(
                                    IProject project) {
                                /*
                                 * Get project config.
                                 */
                                ProjectConfig config =
                                        XpdResourcesPlugin.getDefault()
                                                .getProjectConfig(project);

                                /*
                                 * Check if the config has GSD assets.
                                 */
                                if (config
                                        .hasAssetType(GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_ASSET_ID)
                                        || config
                                                .hasAssetType(GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID)) {

                                    /*
                                     * Get all assets present in the config.
                                     */
                                    EList<AssetType> assetTypes =
                                            config.getAssetTypes();

                                    /*
                                     * Form a list of assets to be deleted.
                                     */
                                    List<AssetType> assetTypesToBeDeleted =
                                            new ArrayList<AssetType>();

                                    /*
                                     * Traverse through the available asset
                                     * types and add the assets to be deleted in
                                     * the list we have specifically defined for
                                     * that purpose (i.e., the GSD assets)
                                     */
                                    for (AssetType assetType : assetTypes) {

                                        if (GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_ASSET_ID
                                                .equals(assetType.getId())
                                                || GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID
                                                        .equals(assetType
                                                                .getId())) {

                                            assetTypesToBeDeleted
                                                    .add(assetType);
                                        }
                                    }

                                    /*
                                     * Remove those assets from the config.
                                     */
                                    config.getAssetTypes()
                                            .removeAll(assetTypesToBeDeleted);

                                    /*
                                     * Fetch all the special folders from the
                                     * project config.
                                     */
                                    SpecialFolders allSpecialFolders =
                                            config.getSpecialFolders();

                                    /*
                                     * Form a list of special folders to be
                                     * deleted.
                                     */
                                    List<SpecialFolder> specialFoldersToBeDeleted =
                                            new ArrayList<SpecialFolder>();

                                    /*
                                     * Traverse through all the special folders
                                     * available in the project config and add
                                     * the GSD special folder(s) to the list of
                                     * folders to be deleted.
                                     */
                                    for (SpecialFolder eachSpecialFolder : allSpecialFolders
                                            .getFolders()) {

                                        if (eachSpecialFolder != null) {

                                            if (GsdConstants.GSD_SPECIAL_FOLDER_KIND
                                                    .equals(SpecialFolderUtil
                                                            .getSpecialFolderKind(eachSpecialFolder
                                                                    .getFolder()))) {

                                                specialFoldersToBeDeleted
                                                        .add(eachSpecialFolder);
                                            }
                                        }
                                    }

                                    /*
                                     * Delete all the GSD special folders one by
                                     * one.
                                     */
                                    for (SpecialFolder eachSpecialFolderToBeDeleted : specialFoldersToBeDeleted) {

                                        try {

                                            eachSpecialFolderToBeDeleted
                                                    .getFolder()
                                                    .delete(true,
                                                            new NullProgressMonitor());

                                        } catch (CoreException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };

                try {

                    /*
                     * Run as a single workspace operation.
                     */

                    ResourcesPlugin.getWorkspace().run(configTestRunnable,
                            new NullProgressMonitor());

                } catch (CoreException e) {

                    GsdResourcePlugin.getDefault().getLogger().error(e);
                }
            }

        }
    }
}