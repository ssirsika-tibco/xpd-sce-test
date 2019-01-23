/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
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
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Build job to run a given job after a (full) build on a given project list is
 * finished.
 * 
 * @author mtorres
 * 
 * @since 3.5
 * 
 * @depricated {@link BuildSynchronizerUtil} should be used instead. Will be
 *             removed before release.
 */
public class BuildJobSynchronizer extends Job {

    private final Job job;

    private final List<IProject> projectList;

    private final boolean doClean;

    private final Job buildSynchronizerJob;

    private IStatus synchronisationStatus = Status.CANCEL_STATUS;

    /**
     * Constructor
     * 
     * @param name
     *            name of the job
     * @param projectList
     *            projects to build, if this is <code>null</code> then the
     *            entire workspace will be built.
     */
    public BuildJobSynchronizer(String name, Job job, List<IProject> projectList) {
        this(name, job, projectList, false);
    }

    /**
     * Constructor
     * 
     * @param name
     *            name of the job
     * @param projectList
     *            projects to build, if this is <code>null</code> then the
     *            entire workspace will be built.
     * @param doClean
     *            set to <code>true</code> if a clean build on the project is
     *            required(if project is not provided then this will have no
     *            effect).
     */
    public BuildJobSynchronizer(String name, Job job,
            List<IProject> projectList, boolean doClean) {
        super(name);
        this.job = job;
        this.projectList = projectList;
        this.doClean = doClean;

        buildSynchronizerJob =
                new Job(Messages.BuildJobSynchronizer_WorkspaceSynchronizerJob) {
                    @Override
                    protected IStatus run(IProgressMonitor monitor) {
                        return performPreDeployment(monitor);
                    }
                };
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
     * IProgressMonitor)
     */
    @Override
    protected IStatus run(IProgressMonitor monitor) {

        buildSynchronizerJob.schedule();
        try {
            buildSynchronizerJob.join();
        } catch (InterruptedException e) {
            return Status.CANCEL_STATUS;
        }
        if (synchronisationStatus.getSeverity() == IStatus.OK) {
            job.schedule();
        } else {
            return Status.CANCEL_STATUS;
        }
        return Status.OK_STATUS;
    }

    /**
     * Perform preDeployment tasks, synchronize workspace
     * 
     * @param monitor
     * @param bpmProjectDragged
     * @return
     */
    private IStatus performPreDeployment(IProgressMonitor monitor) {

        WorkspaceBuildSynchronizer synchronizer =
                new WorkspaceBuildSynchronizer();
        if (synchronizer != null) {
            return synchronizer.synchronize(projectList, monitor);
        }
        return Status.OK_STATUS;
    }

    /**
     * This is the base class to synchronise the build artifacts
     * 
     * @author mtorres
     * 
     */
    public class WorkspaceBuildSynchronizer {

        public WorkspaceBuildSynchronizer() {
        }

        /**
         * This method synchronises the project This method should be called
         * from a Job.
         * 
         * @param projectList
         *            the list of projects to be synchronized
         * @param monitor
         *            the job progress monitor
         * 
         * @return IStatus the synchronisation status
         **/
        public IStatus synchronize(List<IProject> projectList,
                IProgressMonitor monitor) {
            try {
                synchronisationStatus = Status.OK_STATUS;
                if (projectList != null && !projectList.isEmpty()) {
                    for (IProject project : projectList) {
                        if (doClean) {
                            project
                                    .build(IncrementalProjectBuilder.CLEAN_BUILD,
                                            monitor);
                        }
                        IWorkspace workspace = ResourcesPlugin.getWorkspace();
                        if (workspace.isAutoBuilding()) {
                            project
                                    .build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                            monitor);
                        } else {
                            project.build(IncrementalProjectBuilder.FULL_BUILD,
                                    monitor);
                        }
                    }
                }
                // Wait for the builds to finish
                try {
                    Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD,
                            null);
                } catch (InterruptedException e) {
                    synchronisationStatus =
                            new Status(
                                    IStatus.CANCEL,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    1,
                                    Messages.BuildJobSynchronizer_InterruptedSynchronization,
                                    e);
                }
                try {
                    Job.getJobManager()
                            .join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
                } catch (InterruptedException e) {
                    synchronisationStatus =
                            new Status(
                                    IStatus.CANCEL,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    1,
                                    Messages.BuildJobSynchronizer_InterruptedSynchronization,
                                    e);
                }
            } catch (CoreException e) {
                synchronisationStatus =
                        new Status(
                                IStatus.ERROR,
                                XpdResourcesPlugin.ID_PLUGIN,
                                1,
                                Messages.BuildJobSynchronizer_SynchronizationError,
                                e);
            } catch (OperationCanceledException e) {
                synchronisationStatus =
                        new Status(
                                IStatus.CANCEL,
                                XpdResourcesPlugin.ID_PLUGIN,
                                1,
                                Messages.BuildJobSynchronizer_InterruptedSynchronization,
                                e);
            }

            return synchronisationStatus;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
     */
    @Override
    public boolean belongsTo(Object family) {
        return XpdResourcesPlugin.FAMILY_SYNCH_BUILD == family;
    }
}