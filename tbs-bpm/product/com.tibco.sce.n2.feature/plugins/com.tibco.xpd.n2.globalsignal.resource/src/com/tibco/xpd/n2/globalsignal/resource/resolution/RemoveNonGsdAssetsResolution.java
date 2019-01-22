/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
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
import com.tibco.xpd.resources.builder.XpdProjectNature;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to remove non GSD assets from the project config.
 * 
 * @author sajain
 * @since Apr 24, 2015
 */
public class RemoveNonGsdAssetsResolution implements IResolution {

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
             * Open the dialog to get a confirmation from the user that the
             * non-GSD assets/special folders will be removed.
             */
            MessageDialog messageDialog =
                    new MessageDialog(
                            Display.getDefault().getActiveShell(),
                            Messages.GsdProjecrtValidator_RemoveNonGSDAsset_warningDialogTitle,
                            null,
                            Messages.GsdProjecrtValidator_RemoveNonGSDAsset_warningDialogText,
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
                                 * Remove Non GSD assets and special folders
                                 * from the specified project.
                                 */
                                removeNonGSDOrXPDNaturesAndBuilders(monitor,
                                        project);

                                /*
                                 * Remove Non GSD/XPD natures and builders from
                                 * the specified project.
                                 */
                                removeNonGSDAssetsAndSpecialFolders(project);
                            }

                            /**
                             * Remove Non GSD assets and special folders from
                             * the specified project.
                             * 
                             * @param project
                             */
                            private void removeNonGSDAssetsAndSpecialFolders(
                                    IProject project) {
                                /*
                                 * Get project config.
                                 */
                                ProjectConfig config =
                                        XpdResourcesPlugin.getDefault()
                                                .getProjectConfig(project);

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
                                 * Traverse through the available asset types
                                 * and add the assets to be deleted in the list
                                 * we have specifically defined for that purpose
                                 * (i.e., the non-GSD assets).
                                 */
                                for (AssetType assetType : assetTypes) {

                                    if (!(GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_ASSET_ID
                                            .equals(assetType.getId()) || GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_HIDDEN_ASSET_ID
                                            .equals(assetType.getId()))) {

                                        assetTypesToBeDeleted.add(assetType);
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
                                 * Form a list of special folders to be deleted.
                                 */
                                List<SpecialFolder> specialFoldersToBeDeleted =
                                        new ArrayList<SpecialFolder>();

                                /*
                                 * Traverse through all the special folders
                                 * available in the project config and add the
                                 * non-GSD special folder(s) to the list of
                                 * folders to be deleted.
                                 */
                                for (SpecialFolder eachSpecialFolder : allSpecialFolders
                                        .getFolders()) {

                                    if (eachSpecialFolder != null) {

                                        if (!GsdConstants.GSD_SPECIAL_FOLDER_KIND
                                                .equals(SpecialFolderUtil
                                                        .getSpecialFolderKind(eachSpecialFolder
                                                                .getFolder()))) {

                                            specialFoldersToBeDeleted
                                                    .add(eachSpecialFolder);
                                        }
                                    }
                                }

                                /*
                                 * Delete all the non-GSD special folders one by
                                 * one.
                                 */
                                for (SpecialFolder eachSpecialFolderToBeDeleted : specialFoldersToBeDeleted) {

                                    try {
                                        IFolder fileFolder =
                                                eachSpecialFolderToBeDeleted
                                                        .getFolder();

                                        allSpecialFolders
                                                .removeFolder(eachSpecialFolderToBeDeleted);

                                        if (fileFolder.exists()) {
                                            fileFolder.delete(true,
                                                    new NullProgressMonitor());
                                        }

                                    } catch (CoreException e) {

                                        GsdResourcePlugin.getDefault()
                                                .getLogger().error(e);
                                    }
                                }
                            }

                            /**
                             * Remove Non GSD/XPD natures and builders from the
                             * specified project.
                             * 
                             * @param monitor
                             * @param project
                             */
                            private void removeNonGSDOrXPDNaturesAndBuilders(
                                    IProgressMonitor monitor, IProject project) {
                                try {

                                    /*
                                     * Delete all natures except GSD and XPD and
                                     * all builders except validation builder.
                                     */

                                    /*
                                     * Get project description.
                                     */
                                    IProjectDescription projectDescription =
                                            project.getDescription();

                                    /*
                                     * Get all nature IDs present in the project
                                     * description.
                                     */
                                    String[] allNatureIDs =
                                            projectDescription.getNatureIds();

                                    /*
                                     * Form a list to contain all the nature IDs
                                     * that we want to spare.
                                     */
                                    List<String> newNatureIDsList =
                                            new ArrayList<String>();

                                    /*
                                     * Go through all the nature IDs present in
                                     * the project description and remove every
                                     * nature ID except XPD and GSD nature.
                                     */
                                    for (String eachNatureID : allNatureIDs) {

                                        if (XpdConsts.PROJECT_NATURE_ID
                                                .equals(eachNatureID)
                                                || GsdProjectValidator.GLOBAL_SIGNAL_DEFINITION_NATURE_ID
                                                        .equals(eachNatureID)) {

                                            newNatureIDsList.add(eachNatureID);

                                        }
                                    }

                                    /*
                                     * Get the project's build spec.
                                     */
                                    ICommand[] buildSpec =
                                            projectDescription.getBuildSpec();

                                    /*
                                     * Form a new build spec which will be set
                                     * to the project description later on.
                                     */
                                    List<ICommand> newBuildSpecList =
                                            new ArrayList<ICommand>();

                                    /*
                                     * Go through all the build commands in the
                                     * build spec and only add the validation
                                     * builder to the new list IF IT IS ALREADY
                                     * THERE.
                                     */
                                    for (ICommand eachBuildCommand : buildSpec) {

                                        if (XpdProjectNature.VALIDATION_BUILDER_ID
                                                .equals(eachBuildCommand
                                                        .getBuilderName())) {

                                            newBuildSpecList
                                                    .add(eachBuildCommand);
                                        }
                                    }

                                    /*
                                     * Convert ICommand list to array.
                                     */
                                    ICommand[] newBuildSpec =
                                            new ICommand[newBuildSpecList
                                                    .size()];

                                    newBuildSpecList.toArray(newBuildSpec);

                                    /*
                                     * Set new build spec.
                                     */
                                    projectDescription
                                            .setBuildSpec(newBuildSpec);

                                    /*
                                     * Convert Nature IDs list to array.
                                     */
                                    String[] newNatureIDs =
                                            new String[newNatureIDsList.size()];

                                    newNatureIDsList.toArray(newNatureIDs);

                                    /*
                                     * Set new nature IDs.
                                     */
                                    projectDescription
                                            .setNatureIds(newNatureIDs);

                                    /*
                                     * Set project description to project.
                                     */
                                    project.setDescription(projectDescription,
                                            monitor);

                                } catch (CoreException e1) {

                                    GsdResourcePlugin.getDefault().getLogger()
                                            .error(e1);
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