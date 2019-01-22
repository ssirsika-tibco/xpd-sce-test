/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.builder;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Utility class to build and wait synchronously for background builds (refresh
 * jobs) to finish.
 * 
 * @author Jan Arciuchiewicz
 * @since 3.5.0
 */
public class BuildSynchronizerUtil {

    /**
     * Prevent instantiation.
     */
    private BuildSynchronizerUtil() {
    }

    /**
     * This method builds the provided projects and waits for the the builds (or
     * refresh job) to finish.
     * 
     * @param projectList
     *            the list of projects to be synchronized
     * @param monitor
     *            the job progress monitor.
     * 
     * @return IStatus the synchronization status
     **/
    public static IStatus synchronizedBuild(List<IProject> projectList,
            IProgressMonitor monitor, boolean doClean) {

        TRACE("** Starting synch build."); //$NON-NLS-1$
        long startTime = System.currentTimeMillis();

        IStatus status = Status.OK_STATUS;
        SubMonitor progress =
                SubMonitor.convert(monitor, IProgressMonitor.UNKNOWN);
        progress.setTaskName(Messages.BuildSynchronizerUtil_BuildingProjects_message);
        try {
            // Perform synchronized build.
            if (projectList != null && !projectList.isEmpty()) {
                for (IProject project : projectList) {
                    progress.setTaskName(String
                            .format(Messages.BuildSynchronizerUtil_BuildingProject_message,
                                    project.getName()));
                    if (doClean) {
                        project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                progress.newChild(20));
                    }
                    IWorkspace workspace = ResourcesPlugin.getWorkspace();
                    if (workspace.isAutoBuilding()) {
                        project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                progress.newChild(IProgressMonitor.UNKNOWN));
                    } else {
                        project.build(IncrementalProjectBuilder.FULL_BUILD,
                                progress.newChild(IProgressMonitor.UNKNOWN));
                    }
                }
            }

            try {
                waitForBuildsToFinish(progress
                        .newChild(IProgressMonitor.UNKNOWN));
            } catch (InterruptedException e) {
                status = Status.CANCEL_STATUS;
            }

        } catch (CoreException e) {
            status =
                    new Status(
                            IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            1,
                            Messages.BuildSynchronizerUtil_CoreExceptionThrown_message,
                            e);
        } catch (OperationCanceledException e) {
            status = Status.CANCEL_STATUS;
        } finally {
            progress.setTaskName(""); //$NON-NLS-1$
            progress.done();
        }
        TRACE("** Synch build finished. Time: %d ms.", System //$NON-NLS-1$
                .currentTimeMillis() - startTime);
        return status;
    }

    private static void TRACE(String s, Object... o) {
        // System.err.println(String.format(s, o));
    }

    public static void waitForBuildsToFinish(IProgressMonitor monitor)
            throws InterruptedException {
        // There is a wait interval needed because there may be another build(s)
        // scheduled for the artifacts generated during the first build (
        // this process can be recursive).
        final int WAIT_FOR_SCHEDULED_BUILD_INTERVAL = 250; // in milliseconds
        // Wait for the builds to finish.
        long start = System.currentTimeMillis();
        TRACE("++ Wait for build started"); //$NON-NLS-1$
        monitor.setTaskName(Messages.BuildSynchronizerUtil_WaitingForBuilds_message);
        IJobManager jobMan = Job.getJobManager();
        Object[] JOB_FAMILIES =
                new Object[] { ResourcesPlugin.FAMILY_AUTO_BUILD,
                        ResourcesPlugin.FAMILY_MANUAL_BUILD,
                        ResourcesPlugin.FAMILY_AUTO_REFRESH,
                        ResourcesPlugin.FAMILY_MANUAL_REFRESH };
        int foundActiveJobs;
        do {
            foundActiveJobs = 0;
            Thread.sleep(WAIT_FOR_SCHEDULED_BUILD_INTERVAL);
            for (Object family : JOB_FAMILIES) {
                Job[] familyActiveJobs = jobMan.find(family);
                if (familyActiveJobs.length > 0) {
                    foundActiveJobs++;
                    jobMan.join(family, monitor);
                }
            }
        } while (foundActiveJobs > 0);
        TRACE("++ Wait for builds finished. Time: %d", System.currentTimeMillis() - start); //$NON-NLS-1$
    }

}
