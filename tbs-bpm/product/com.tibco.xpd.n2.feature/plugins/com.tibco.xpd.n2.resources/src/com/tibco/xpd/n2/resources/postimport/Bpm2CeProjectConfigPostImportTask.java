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

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.n2.resources.internal.Messages;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
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
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor, "", 6); //$NON-NLS-1$

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

                        resetVersion(projectDetails, monitor);

                        resetDestinationEnvironments(projectDetails, monitor);

                        removeSpecialBuildFolders(projectConfig, monitor);

                        moveGeneratedBOMs(projectConfig, monitor);

                        removeUnwantedSpecialUserFolders(projectConfig,
                                monitor);

                        removeUnwantedProjectAssets(projectConfig, monitor);

                        removeUnwantedProjectNaturesAndBuilders(project,
                                monitor);

                        /* Save it. */
                        ProjectConfigWorkingCopy wc =
                                (ProjectConfigWorkingCopy) WorkingCopyUtil
                                        .getWorkingCopyFor(projectConfig);
                        wc.setDetails(projectDetails);

                        removeUnsupportedPresentationChannels(project);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

            String message =
                    "Failed to save Project details working copy for project: " //$NON-NLS-1$
                            + project.getName();
            BundleActivator.getDefault().getLogger().error(e, message);

            throw new CoreException(new Status(IStatus.ERROR,
                    BundleActivator.PLUGIN_ID, message));
        } finally {
            monitor.done();
        }
    }

    /**
     * Reset the version of all projects to 1.0.0.qualifier (in SCE we take full
     * control of all versions and hence for these new-product projects it makes
     * sense to reset the version number)
     * 
     * @param projectDetails
     * @param monitor
     */
    private void resetVersion(ProjectDetails projectDetails,
            IProgressMonitor monitor) {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_ResetVersion_status);

        projectDetails.setVersion("1.0.0.qualifier"); //$NON-NLS-1$

        monitor.subTask(""); //$NON-NLS-1$
        monitor.worked(1);
    }

    /**
     * Replace all existing destinations with CE destination.
     * 
     * @param projectDetails
     * @param monitor
     */
    private void resetDestinationEnvironments(ProjectDetails projectDetails,
            IProgressMonitor monitor) {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RestDestinationEnv_status);

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
     * @throws CoreException
     */
    private void removeSpecialBuildFolders(ProjectConfig projectConfig,
            IProgressMonitor monitor) throws CoreException {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RemoveBuildFolders_status);

        IProject project = projectConfig.getProject();

        try {

            /*
             * First deal with those that are hidden SpecialFolders
             */
            List<String> unwantedTypes = Arrays.asList("compositeModulesOutput", //$NON-NLS-1$
                    "bom2xsd", //$NON-NLS-1$
                    "deModulesOutput", //$NON-NLS-1$
                    "daaBinFolder" //$NON-NLS-1$
            );

            SpecialFolders specialFolders = projectConfig.getSpecialFolders();

            if (specialFolders != null) {
                for (Iterator<?> iterator =
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
            e.printStackTrace();

            String message =
                    "During project import conversion Could not remove folder: " //$NON-NLS-1$
                            + e.getMessage();
            BundleActivator.getDefault().getLogger().error(message);

            throw new CoreException(new Status(IStatus.ERROR,
                    BundleActivator.PLUGIN_ID, message));

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
     * @throws CoreException
     */
    private void moveGeneratedBOMs(ProjectConfig projectConfig,
            SubMonitor monitor) throws CoreException {
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
                    if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                            .equals(specialFolder.getKind())
                            && BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
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
            e.printStackTrace();

            String message =
                    "During project import conversion caught error moving generated BOMs to user defined BOM folders: " //$NON-NLS-1$
                            + e.getMessage();
            BundleActivator.getDefault().getLogger().error(message);

            throw new CoreException(new Status(IStatus.ERROR,
                    BundleActivator.PLUGIN_ID, message));
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
     * @throws CoreException
     */
    private void removeUnwantedSpecialUserFolders(ProjectConfig projectConfig,
            SubMonitor monitor) throws CoreException {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RemovingUnwantedUserFolders_status);

        IProject project = projectConfig.getProject();

        try {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();

            if (specialFolders != null) {
                for (Iterator<?> iterator =
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
                         * Unless it's the decisions files as the user may not
                         * realise what tehy're doing on import and might commit
                         * back to svn without thinking (Jan requested in code
                         * review)
                         * 
                         */
                        if (!"com.tibco.xpd.asset.decisions" //$NON-NLS-1$
                                .equals(specialFolder.getKind())) {
                            String sfLocation = specialFolder.getLocation();

                            IFolder actualFolder =
                                    project.getFolder(sfLocation);

                            if (actualFolder.exists()) {
                                actualFolder.delete(true,
                                        new NullProgressMonitor());
                            }
                        }

                    }
                }
            }

        } catch (CoreException e) {
            e.printStackTrace();

            String message =
                    "During project import conversion could not remove folder: " //$NON-NLS-1$
                            + e.getMessage();
            BundleActivator.getDefault().getLogger().error(message);

            throw new CoreException(new Status(IStatus.ERROR,
                    BundleActivator.PLUGIN_ID, message));
        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.worked(1);
        }
    }

    /**
     * Remove unwanted asset definitions from project configuration
     * 
     * Currently this is just the WSDL asset.
     * 
     * @param projectConfig
     * @param monitor
     */
    private void removeUnwantedProjectAssets(ProjectConfig projectConfig,
            SubMonitor monitor) {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RemoveUnwantedAssetConfig_status);

        try {
            for (Iterator<AssetType> iterator =
                    projectConfig.getAssetTypes().iterator(); iterator
                            .hasNext();) {
                AssetType asset = iterator.next();

                if ("com.tibco.xpd.asset.wsdl".equals(asset.getId())) { //$NON-NLS-1$
                    iterator.remove();
                } else if ("com.tibco.xpd.asset.decisions" //$NON-NLS-1$
                        .equals(asset.getId())) {
                    iterator.remove();
                }
            }

        } finally {
            monitor.subTask(""); //$NON-NLS-1$
            monitor.worked(1);
        }
    }

    /**
     * Remove unwanted nature definitions from project configuration
     * <p>
     * Natures...
     * <li>com.tibco.xpd.wsdltobom.wsdlBomNature - WSDL to BOM nature</li>
     * <li>com.tibco.xpd.bom.xsdtransform.xsdNature - BOM to XSD nature</li>
     * <li>com.tibco.xpd.wsdlgen.wsdlGenNature - XPDL to WSDL nature</li>
     * <li>com.tibco.xpd.n2.daa.cleanBpmFolderNature - .bpm DAA build cleanup
     * nature</li>
     * <p>
     * Builders...
     * <li>com.tibco.xpd.wsdltobom.wsdlToBomBuilder</li>
     * <li>com.tibco.xpd.bom.xsdtransform.xsdBuilder</li>
     * <li>com.tibco.xpd.n2.daa.cleanBpmFolderBuilder</li>
     * <li>com.tibco.xpd.wsdlgen.wsdlGen</li>
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    private void removeUnwantedProjectNaturesAndBuilders(IProject project,
            SubMonitor monitor) throws CoreException {
        monitor.subTask(
                Messages.Bpm2CeProjectConfigPostImportTask_RemoveUnwantedNatures_status);

        try {
            /* Remove unwanted natures */
            IProjectDescription description = project.getDescription();
            ArrayList<String> naturesList = new ArrayList<String>(
                    Arrays.asList(description.getNatureIds()));

            naturesList.remove("com.tibco.xpd.wsdltobom.wsdlBomNature"); //$NON-NLS-1$
            naturesList.remove("com.tibco.xpd.bom.xsdtransform.xsdNature"); //$NON-NLS-1$
            naturesList.remove("com.tibco.xpd.wsdlgen.wsdlGenNature"); //$NON-NLS-1$
            naturesList.remove("com.tibco.xpd.n2.daa.cleanBpmFolderNature"); //$NON-NLS-1$
            naturesList.remove("com.tibco.xpd.decisions.resources.ui.beNature"); //$NON-NLS-1$

            /* Reset nature list. */
            String[] newNatures =
                    naturesList.toArray(new String[naturesList.size()]);
            description.setNatureIds(newNatures);

            /* Remove unwanted builders */
            ArrayList<ICommand> buildersList = new ArrayList<ICommand>(
                    Arrays.asList(description.getBuildSpec()));

            for (Iterator<ICommand> iterator = buildersList.iterator(); iterator
                    .hasNext();) {
                ICommand builder = iterator.next();

                if ("com.tibco.xpd.wsdltobom.wsdlToBomBuilder" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();

                } else if ("com.tibco.xpd.bom.xsdtransform.xsdBuilder" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();

                } else if ("com.tibco.xpd.n2.daa.cleanBpmFolderBuilder" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();

                } else if ("com.tibco.xpd.wsdlgen.wsdlGen" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();

                } else if ("com.tibco.xpd.decisions.resources.ui.beBuilder" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();

                } else if ("com.tibco.xpd.decisions.daa.cleanDecisionStagingBuilder" //$NON-NLS-1$
                        .equals(builder.getBuilderName())) {
                    iterator.remove();
                }

            }

            /* Reset nature list. */
            ICommand[] newBuilders =
                    buildersList.toArray(new ICommand[buildersList.size()]);
            description.setBuildSpec(newBuilders);

            /* Save the .project file. */
            project.setDescription(description, null);

        } catch (CoreException e) {
            e.printStackTrace();

            String message =
                    "During project import conversion could not remove folder: " //$NON-NLS-1$
                            + e.getMessage();
            BundleActivator.getDefault().getLogger().error(message);

            throw new CoreException(new Status(IStatus.ERROR,
                    BundleActivator.PLUGIN_ID, message));

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


        for (SpecialFolder specialFolder : specialFolders.getFolders()) {
            if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                    .equals(specialFolder.getKind())
                    && !BOMValidationUtil.GENERATED_BOM_FOLDER_TYPE
                            .equals(specialFolder.getGenerated())) {
                userBomSpecialFolder = specialFolder;
                break;
            }
        }

        IFolder userBomFolder = null;

        if (userBomSpecialFolder == null) {
            /* There is no user defined BOM folder. */
            userBomFolder = projectConfig.getProject().getFolder(
                    Messages.Bpm2CeProjectConfigPostImportTask_BusinessObjectsFolderName_label);

            if (!userBomFolder.exists()) {
                userBomFolder.create(true, true, null);
            }

            userBomSpecialFolder = specialFolders.addFolder(userBomFolder,
                    BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        } else if (userBomSpecialFolder.getFolder() != null) {
            /*
             * There is a special folder, use the folder (create below if not
             * exists).
             */
            userBomFolder = userBomSpecialFolder.getFolder();

        } else {
            /*
             * There is a special folder config BUT the actual folder is
             * missing.
             */
            userBomFolder = projectConfig.getProject()
                    .getFolder(userBomSpecialFolder.getLocation());
        }

        if (!userBomFolder.exists()) {
            createFolder(userBomFolder);
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

        } else if ("decisions".equals(specialFolder.getKind())) { //$NON-NLS-1$
            return true;

        }

        return false;
    }

    /**
     * Remove Workspace general interface, Workspace GWT and Workspace Email
     * channels from project specific configuration.
     * 
     * @param project
     * @throws CoreException
     */
    private void removeUnsupportedPresentationChannels(IProject project)
            throws CoreException {
        PresentationManager pm = PresentationManager.getInstance();

        Channels channelContainer = pm.getChannels(project);

        /*
         * Only care about channels store in project.
         */
        if (channelContainer != null
                && pm.isProjectChannels(channelContainer, project)) {
            
            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            ed.getCommandStack().execute(new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    for (Channel channel : channelContainer.getChannels()) {
        
                        for (Iterator iterator =
                                channel.getTypeAssociations().iterator(); iterator
                                        .hasNext();) {
                            TypeAssociation typeAssociation =
                                    (TypeAssociation) iterator.next();
        
                            ChannelType channelType = typeAssociation.getChannelType();
        
                            if (channelType != null) {
                                if (!PresentationManager.getInstance()
                                        .isAceSupportedChannelType(
                                                channelType.getId())) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
            });

            PresentationManager.saveChannels(channelContainer);
        }
    }

}
