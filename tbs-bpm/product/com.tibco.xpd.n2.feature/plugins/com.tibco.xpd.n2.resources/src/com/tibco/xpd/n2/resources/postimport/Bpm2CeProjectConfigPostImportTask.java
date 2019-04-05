/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.resources.postimport;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigFactory;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Post import task to for importing any XPD related projects and converting the
 * project configuration to be appropriate for the CE run-time destination
 * 
 * <li>Replaces all destination environments with the "CE" destination</i>
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
        SubMonitor monitor = SubMonitor
                .convert(aProgressMonitor, "Resetting project destination", 1); //$NON-NLS-1$

        try {

            if (project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);

                if (config != null) {
                    ProjectDetails projectDetails = config.getProjectDetails();

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

                        resetDestinationEnvironments(projectDetails);

                        /* Save it. */
                        ProjectConfigWorkingCopy wc =
                                (ProjectConfigWorkingCopy) WorkingCopyUtil
                                        .getWorkingCopyFor(config);
                        wc.setDetails(projectDetails);
                    }
                }
            }

        } catch (IOException e) {
            BundleActivator.getDefault().getLogger().error(e,
                    "Failed to save Project details working copy for project: " //$NON-NLS-1$
                            + project.getName());
        } finally {
            monitor.worked(1);
            monitor.done();
        }
    }

    /**
     * Replace all existing destinations with CE destination.
     * 
     * @param projectDetails
     */
    private void resetDestinationEnvironments(ProjectDetails projectDetails) {
        Destinations destinations =
                ProjectConfigFactory.eINSTANCE
                        .createDestinations();

        Destination ceDestination =
                ProjectConfigFactory.eINSTANCE
                        .createDestination();
        ceDestination.setType(XpdConsts.ACE_DESTINATION_NAME);
        destinations.getDestination().add(ceDestination);

        projectDetails.setGlobalDestinations(destinations);
    }

}
