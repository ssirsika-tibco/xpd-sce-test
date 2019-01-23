/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 * This is the base class to synchronise the build artifacts
 * 
 * @author mtorres
 *
 */
public class BaseDeploymentSynchronizer {
    
    public BaseDeploymentSynchronizer() {        
    }

    /**
     * This method synchronises the output resources with the source resources
     * This method should be called from a Job.
     * 
     * @param outputResourcesMap
     *            the map containing the map of source and list of output
     *            resources
     * @param monitor
     *            the job progress monitor
     * 
     * @return true if the synchronisation was correct
     **/
    protected boolean synchronize(
            Map<IResource, List<IResource>> outputResourcesMap,
            IProgressMonitor monitor) {
        boolean synchronisationOk = true;
        Collection<IProject> projectsNeedingBuild =
                resourcesNeedingBuild(outputResourcesMap);
        if (projectsNeedingBuild != null && !projectsNeedingBuild.isEmpty()) {
            for (IProject project : projectsNeedingBuild) {
                try {
                    project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                            monitor);
                } catch (CoreException e) {
                    return false;
                }
            }
        }
        // Wait for the builds to finish
        try {
            Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
        } catch (InterruptedException e) {
            synchronisationOk = false;
        }
        try {
            Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
        } catch (InterruptedException e) {
            synchronisationOk = false;
        }
        return synchronisationOk;
    }
    
    /**
     * This method synchronises the project
     * This method should be called from a Job.
     * 
     * @param project
     *            the project to be synchronized
     * @param monitor
     *            the job progress monitor
     * 
     * @return true if the synchronisation was correct
     **/
    public boolean synchronize(IProject project, IProgressMonitor monitor) {
        boolean synchronisationOk = true;
        if (project != null) {
            try {
                project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD,
                        monitor);
            } catch (CoreException e) {
                return false;
            }
        }
        // Wait for the builds to finish
        try {
            Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
        } catch (InterruptedException e) {
            synchronisationOk = false;
        }
        try {
            Job.getJobManager().join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
        } catch (InterruptedException e) {
            synchronisationOk = false;
        }
        return synchronisationOk;
    }
    
    /**
     * This method returns all the projects that need building
     * 
     * @param outputResourcesMap
     *            the map containing the map of source and list of output
     *            resources
     * 
     * @return Collection<IProject> list of projects needing build
     **/
    private Collection<IProject> resourcesNeedingBuild(
            Map<IResource, List<IResource>> outputResourcesMap) {
        if (outputResourcesMap == null || outputResourcesMap.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        if (outputResourcesMap.keySet() != null) {
            // Check if autobuild is selected
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            if (workspace != null && workspace.getDescription() != null) {
                if (!workspace.getDescription().isAutoBuilding()) {
                    Set<IProject> projectsNeedingBuild =
                            new HashSet<IProject>();
                    // Run a build for all projects
                    for (IResource sourceModule : outputResourcesMap.keySet()) {
                        if (sourceModule != null
                                && sourceModule.getProject() != null) {
                            projectsNeedingBuild.add(sourceModule.getProject());
                        }
                    }
                    return projectsNeedingBuild;
                }
            }
            // Check if timestamps are correct
            Set<IProject> projectsNeedingBuild = new HashSet<IProject>();
            for (IResource sourceModule : outputResourcesMap.keySet()) {
                if (sourceModule != null
                        && sourceModule.getProject() != null
                        && !areTimestampsInSync(sourceModule,
                                outputResourcesMap.get(sourceModule))) {
                    projectsNeedingBuild.add(sourceModule.getProject());
                }
            }
            return projectsNeedingBuild;
        }
        return Collections.EMPTY_LIST;
    }
    
    /**
     * This method checks if the timestamps of the source module and the output
     * modules are in sync
     * 
     * @param sourceModule
     *            the source module
     * 
     *@param outputModules
     *            the output modules of that source module
     * 
     * 
     * @return true if the the timestamps are in sync false otherwise
     **/
    private boolean areTimestampsInSync(IResource sourceModule,
            List<IResource> outputModules) {
        boolean inSync = false;
        if (sourceModule != null && outputModules != null
                && !outputModules.isEmpty()) {
            boolean allTimestampsCorrect = false;
            long sourceMS = sourceModule.getLocalTimeStamp();
            if (sourceMS == IResource.NULL_STAMP) {
                allTimestampsCorrect = false;
            } else {
                allTimestampsCorrect = true;
                for (IResource outputResource : outputModules) {
                    long outputMS = outputResource.getLocalTimeStamp();
                    if (outputMS == IResource.NULL_STAMP || outputMS < sourceMS) {
                        allTimestampsCorrect = false;
                        break;
                    } else {
                        allTimestampsCorrect = true;
                    }
                }
            }
            if (!allTimestampsCorrect) {
                return false;
            }
            inSync = allTimestampsCorrect;
        }
        return inSync;
    }   
    
    
}
