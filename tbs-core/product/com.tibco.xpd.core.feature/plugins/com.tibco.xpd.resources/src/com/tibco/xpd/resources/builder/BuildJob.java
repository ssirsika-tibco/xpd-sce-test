/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

import com.tibco.xpd.resources.internal.Messages;

/**
 * Build job to run a (full) build on a given project. If no project is
 * specified then the whole workspace will be built.
 * 
 * @author njpatel
 * 
 * @since 3.3
 */
public class BuildJob extends Job {
    private final IProject project;

    private final boolean doClean;

    /**
     * Constructor
     * 
     * @param name
     *            name of the job
     * @param project
     *            project to build, if this is <code>null</code> then the entire
     *            workspace will be built.
     */
    public BuildJob(String name, IProject project) {
        this(name, project, false);
    }

    /**
     * Constructor
     * 
     * @param name
     *            name of the job
     * @param project
     *            project to build, if this is <code>null</code> then the entire
     *            workspace will be built.
     * @param doClean
     *            set to <code>true</code> if a clean build on the project is
     *            required(if project is not provided then this will have no
     *            effect).
     */
    public BuildJob(String name, IProject project, boolean doClean) {
        super(name);
        this.project = project;
        this.doClean = doClean;
    }

    /**
     * Check if this build is covered by another job.
     * 
     * @param other
     * @return <code>true</code> if this build is covered by the <i>other</i>
     *         build job.
     */
    public boolean isCoveredBy(BuildJob other) {
        if (other.project == null) {
            return true;
        }

        return project != null && project.equals(other.project);
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
     * IProgressMonitor)
     */
    @Override
    protected IStatus run(IProgressMonitor monitor) {
        synchronized (getClass()) {
            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }
            // Check if a build job is already running
            Job[] buildJobs =
                    Job.getJobManager()
                            .find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
            for (int i = 0; i < buildJobs.length; i++) {
                Job curr = buildJobs[i];
                if (curr != this && curr instanceof BuildJob) {
                    BuildJob job = (BuildJob) curr;
                    if (job.isCoveredBy(this)) {
                        // cancel all other build jobs of our kind
                        curr.cancel();
                    }
                }
            }
        }
        try {
            if (project != null) {
                monitor
                        .beginTask(String
                                .format(Messages.BuildJob_buildProject_monitor_shortdesc,
                                        project.getName()),
                                doClean ? 3 : 2);

                if (doClean) {
                    // Do a clean before a full build
                    project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                            new SubProgressMonitor(monitor, 1));
                }

                project.build(IncrementalProjectBuilder.FULL_BUILD,
                        new SubProgressMonitor(monitor, 1));

                ResourcesPlugin.getWorkspace()
                        .build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                new SubProgressMonitor(monitor, 1));
            } else {
                // Full workspace build requested
                monitor
                        .beginTask(Messages.BuildJob_buildWorkspace_monitor_shortdesc,
                                1);
                ResourcesPlugin.getWorkspace()
                        .build(IncrementalProjectBuilder.FULL_BUILD, monitor);
            }
        } catch (CoreException e) {
            return e.getStatus();
        } catch (OperationCanceledException e) {
            return Status.CANCEL_STATUS;
        } finally {
            monitor.done();
        }
        return Status.OK_STATUS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
     */
    @Override
    public boolean belongsTo(Object family) {
        return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
    }
}