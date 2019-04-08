/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Post import task to for importing any XPD related projects and converting the
 * project configuration to be appropriate for the CE run-time destination
 * 
 * <li>Replaces all destination environments with the "CE" destination</i>
 * <li>Remove all AMX-BPM build folders: .bpm, .bom2Xsd, .bomJars, .processOut,
 * .deModulesOutput</li>
 * <li>TODO: Watch this space for more to come later...</li>
 * 
 * @author aallway
 * @since 04 April 2019
 */
public class Bpm2CeProjectConfigPostImportTask
        implements IPostImportProjectTask {

    /**
     * @see com.tibco.xpd.resources.ui.imports.IPostImportProjectTask#runPostImportTask(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    @Override
    public void runPostImportTask(IProject project,
            IProgressMonitor aProgressMonitor) throws CoreException {
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor, "", 4); //$NON-NLS-1$

        try {

            if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                ProjectConfig projectConfig = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);

                if (projectConfig != null) {
                    ProjectDetails projectDetails =
                            projectConfig.getProjectDetails();

                    /*
                     * We are only interested in projects not already converted
                     * to CE destination or created from SCE in the first place.
                     * 
                     * Don't want to touch already converted or new projects
                     * that would be a waste of time
                     */
                    if (projectDetails != null
                            && !projectDetails.isGlobalDestinationEnabled(
                                    XpdConsts.ACE_DESTINATION_NAME)) {

                        resetDestinationEnvironments(projectDetails, monitor);

                        removeSpecialBuildFolders(projectConfig, monitor);

                        moveGeneratedBOMs(projectConfig, monitor);

                        removeUnwantedSpecialUserFolders(projectConfig,
                                monitor);

                        /* Save it. */
                        ProjectConfigWorkingCopy wc =
                                (ProjectConfigWorkingCopy) WorkingCopyUtil
                                        .getWorkingCopyFor(projectConfig);
                        wc.setDetails(projectDetails);
                    }
                }
            }

        } catch (IOException e) {
            BundleActivator.getDefault().getLogger().error(e,
                    "Failed to save Project details working copy for project: " //$NON-NLS-1$
                            + project.getName());
        } finally {
            monitor.done();
        }
    }



    /**
     * Replace all existing destinations with CE destination.
     * 
     * @param projectDetails
     * @param monitor
     */
    private void resetDestinationEnvironments(ProjectDetails projectDetails,
            IProgressMonitor monitor) {
        monitor.subTask(Messages.Bpm2CeProjectConfigPostImportTask_RestDestinationEnv_status);

        Destinations destinations =
                ProjectConfigFactory.eINSTANCE.createDestinations();

        Destination ceDestination =
                ProjectConfigFactory.eINSTANCE.createDestination();
        ceDestination.setType(XpdConsts.ACE_DESTINATION_NAME);
        destinations.getDestination().add(ceDestination);

        projectDetails.setGlobalDestinations(destinations);

        monitor.subTask(""); //$NON-NLS-1$
        monitor.worked(1);
    }

    /**
     * Remove all AMX-BPM build folders: .bpm, .bom2Xsd, .bomJars, .processOut,
     * .deModulesOutput
     * 
     * Ensure that any special folder configuration is removed for these from
     * the project's .config file (such as
     * <config:folder id="_Mwh60LrdEeSqPrkc-C7L0Q" kind="bom2xsd" location=
     * ".bom2Xsd"/>)
     * 
     * @param projectDetails
     */
    private void removeSpecialBuildFolders(ProjectConfig projectConfig,
            IProgressMonitor monitor) {
        monitor.subTask(Messages.Bpm2CeProjectConfigPostImportTask_RemoveBuildFolders_status);

        IProject project = projectConfig.getProject();

        try {

            /*
             * First deal with those that are hidden SpecialFolders
             */
            List<String> unwantedTypes = Arrays.asList("compositeModulesOutput", //$NON-NLS-1$
                    "bom2xsd", //$NON-NLS-1$
                    "deModulesOutput", //$NON-NLS-1$
                    "daaBinFolder"//$NON-NLS-1$
            );

            SpecialFolders specialFolders = projectConfig.getSpecialFolders();

            if (specialFolders != null) {
                for (Iterator iterator =
                        specialFolders.getFolders().iterator(); iterator
                                .hasNext();) {
                    SpecialFolder specialFolder =
                            (SpecialFolder) iterator.next();

                    if (unwantedTypes.contains(specialFolder.getKind())) {
                        /*
                         * Remove the special folder from project config
                         */
                        iterator.remove();

                        /*
                         * Remove the physical folder itself if it exists.
                         */
                        String sfLocation = specialFolder.getLocation();

                        IFolder actualFolder = project.getFolder(sfLocation);

                        if (actualFolder.exists()) {
                            actualFolder.delete(true,
                                    new NullProgressMonitor());
                        }
                    }
                }
            }

            /*
             * Then deal with hidden folders that are not special folders.
             */
            IFolder bomJarsFolder = project.getFolder(".bomjars"); // $NON-NLS-1$ //$NON-NLS-1$
            if (bomJarsFolder.exists()) {
                bomJarsFolder.delete(true, new NullProgressMonitor());
            }

            IFolder processOutFolder = project.getFolder(".processOut"); // $NON-NLS-1$ //$NON-NLS-1$
            if (processOutFolder.exists()) {
                processOutFolder.delete(true, new NullProgressMonitor());
            }

        } catch (CoreException e) {
            BundleActivator.getDefault().getLogger().error(
                    "During project import conversion Could not remove folder: " //$NON-NLS-1$
                            + e.getMessage());
        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.worked(1);
        }
    }

    /**
     * Move generated BOM files to user defined "Business Objects" BOM folder
     * 
     * @param projectConfig
     * @param monitor
     */
    private void moveGeneratedBOMs(ProjectConfig projectConfig,
            SubMonitor monitor) {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_MovingGenBOMs_status);

        try {
            /* 
             * Find generated BOM folders...
             */
            SpecialFolder userBomSpecialFolder = null;

            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            
            if (specialFolders != null) {
                /*
                 * Copy the list as we may add a new special folder during
                 * processing.
                 */
                Collection<SpecialFolder> copySpecialFolders =
                        new ArrayList<>();
                copySpecialFolders.addAll(specialFolders.getFolders());

                for (SpecialFolder specialFolder : copySpecialFolders) {
                    if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND.equals(specialFolder.getKind()) &&
                            BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                                    .equals(specialFolder.getGenerated())
                            && specialFolder.getFolder() != null
                            && specialFolder.getFolder().exists()) {

                        /*
                         * Find or create the a defined BOM folder in the given
                         * project if we haven't already.
                         */
                        if (userBomSpecialFolder == null) {
                            userBomSpecialFolder =
                                    findOrCreateUserBomFolder(projectConfig,
                                            specialFolders);
                        }

                        /*
                         * Move all the generated BOM content to user defined
                         * BOM folder.
                         */
                        moveBOMsToUserFolder(projectConfig,
                                specialFolder,
                                userBomSpecialFolder);

                        /*
                         * Note: We don't remove the generated BOM folder here,
                         * that'll be done later in the migration process.
                         */
                    }
                }
            }
            


        } catch (CoreException e) {
            BundleActivator.getDefault().getLogger().error(
                    "During project import conversion caught error moving generated BOMs to user defined BOM folders: " //$NON-NLS-1$
                            + e.getMessage());
        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.worked(1);
        }

    }

    /**
     * Remove unwanted user-visible folders (Service Descriptors, Generated
     * Business Objects, Generated Services)
     * 
     * @param projectConfig
     * @param monitor
     */
    private void removeUnwantedSpecialUserFolders(ProjectConfig projectConfig,
            SubMonitor monitor) {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RemovingUnwantedUserFolders_status);

        IProject project = projectConfig.getProject();

        try {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();

            if (specialFolders != null) {
                for (Iterator iterator =
                        specialFolders.getFolders().iterator(); iterator
                                .hasNext();) {
                    SpecialFolder specialFolder =
                            (SpecialFolder) iterator.next();

                    if (isUnwantedUserSpecialFolder(specialFolder)) {
                        /*
                         * Remove the special folder from project config
                         */
                        iterator.remove();

                        /*
                         * Remove the physical folder itself if it exists.
                         */
                        String sfLocation = specialFolder.getLocation();

                        IFolder actualFolder = project.getFolder(sfLocation);

                        if (actualFolder.exists()) {
                            actualFolder.delete(true,
                                    new NullProgressMonitor());
                        }

                    }
                }
            }

        } catch (CoreException e) {
            BundleActivator.getDefault().getLogger().error(
                    "During project import conversion could not remove folder: " //$NON-NLS-1$
                            + e.getMessage());
        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.worked(1);
        }
    }


    /**
     * Move all the generated BOM content to user defined BOM folder.
     * 
     * @param projectConfig
     * @param srcSpecialFolder
     * @param tgtSpecialFolder
     * @throws CoreException
     */
    private void moveBOMsToUserFolder(ProjectConfig projectConfig,
            SpecialFolder srcSpecialFolder, SpecialFolder tgtSpecialFolder)
            throws CoreException {

        IFolder srcFolder = srcSpecialFolder.getFolder();
        IPath srcFolderPath = srcFolder.getFullPath();
        IFolder tgtFolder = tgtSpecialFolder.getFolder();
        IPath tgtFolderPath = tgtFolder.getFullPath();

        /* Get all BOM files from source folder. */
        Collection<IResource> bomFiles =
                SpecialFolderUtil.getAllDeepResourcesInContainer(srcFolder,
                        BOMResourcesPlugin.BOM_FILE_EXTENSION,
                        false);

        /* Move them to target folder, creating target folder as we go. */
        for (IResource bomFile : bomFiles) {
            IContainer bomSrcFolder = bomFile.getParent();

            IPath bomSrcParentPath = bomSrcFolder.getFullPath();
            IPath bomParentRelativePath =
                    bomSrcParentPath.makeRelativeTo(srcFolderPath);

            IPath bomTgtParentPath =
                    tgtFolderPath.append(bomParentRelativePath);

            IFolder bomTgtFolder = ResourcesPlugin.getWorkspace().getRoot()
                    .getFolder(bomTgtParentPath);
            if (!bomTgtFolder.exists()) {
                createFolder(bomTgtFolder);
            }
            
            IPath bomTgtFilePath = bomTgtParentPath.append(bomFile.getName());
            bomFile.move(bomTgtFilePath, true, null);
            
        }

    }

    /**
     * Find or create the a defined BOM folder in the given project
     * 
     * @param projectConfig
     * @param specialFolders
     * 
     * @return the folder
     * @throws CoreException
     */
    private SpecialFolder findOrCreateUserBomFolder(ProjectConfig projectConfig,
            SpecialFolders specialFolders) throws CoreException {
        SpecialFolder userBomSpecialFolder = null;
        IFolder userBomFolder = null;

        for (SpecialFolder specialFolder : specialFolders.getFolders()) {
            if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                    .equals(specialFolder.getKind())
                    && !BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                            .equals(specialFolder.getGenerated())) {
                userBomSpecialFolder = specialFolder;
                break;
            }
        }

        if (userBomSpecialFolder == null) {
            userBomFolder = projectConfig.getProject().getFolder(
                    Messages.Bpm2CeProjectConfigPostImportTask_BusinessObjectsFolderName_label);

            if (!userBomFolder.exists()) {
                createFolder(userBomFolder);
            }

            userBomSpecialFolder = specialFolders.addFolder(userBomFolder,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        } else {
            userBomFolder = userBomSpecialFolder.getFolder();

            if (!userBomFolder.exists()) {
                createFolder(userBomFolder);
            }
        }

        return userBomSpecialFolder;
    }

    /**
     * Create the given folder. All non-existing parent folders will also be
     * created.
     * 
     * @param folder
     * @throws CoreException
     */
    private void createFolder(IFolder folder) throws CoreException {
        if (folder != null) {
            IContainer parent = folder.getParent();

            if (!parent.exists()) {
                if (parent instanceof IFolder) {
                    // Create the parent
                    createFolder((IFolder) parent);
                }
            }
            folder.create(false, true, null);
        }
    }

    /**
     * @param specialFolder
     * 
     * @return <code>true</code> if the given special folder is one that we no
     *         longer need or want.
     */
    private boolean isUnwantedUserSpecialFolder(SpecialFolder specialFolder) {
        /* Generated BOMs folder. */
        if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                .equals(specialFolder.getKind())
                && BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                        .equals(specialFolder.getGenerated())) {
            return true;

        }
        /* Generated and user defined Services folders. */
        else if ("wsdl".equals(specialFolder.getKind())) { //$NON-NLS-1$
            return true;

        }

        return false;
    }

}
