/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

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
        SubMonitor monitor = SubMonitor.convert(aProgressMonitor, "", 2); //$NON-NLS-1$

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
                         * Remove the physical folder itself if it exists.
                         */
                        String sfLocation = specialFolder.getLocation();

                        IFolder actualFolder = project.getFolder(sfLocation);

                        if (actualFolder.exists()) {
                            actualFolder.delete(true,
                                    new NullProgressMonitor());
                        }

                        /*
                         * Remove the special folder from project config
                         */
                        iterator.remove();
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

}
