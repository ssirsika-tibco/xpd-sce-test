/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.internal.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;

/**
 * Validator to check the project lifecycle id.
 * 
 * @author njpatel
 * 
 */
public class ProjectLifecycleValidator implements IResourceChangeListener {

    private static final String MARKER_TYPE =
            "com.tibco.xpd.validation.lifecycleMarker"; //$NON-NLS-1$

    public static final String ATT_DUPLICATE_PROJECT = "duplicateProject"; //$NON-NLS-1$

    public static final String ATT_PROJECT_ID = "projectId"; //$NON-NLS-1$

    private static final String ISSUE_ID = "duplicate.lifecycleId.issue"; //$NON-NLS-1$

    private final IWorkspaceRoot workspaceRoot;

    private final IssueInfo issue;

    private IProgressMonitor monitor;

    /**
     * @param project
     *            project being built
     */
    public ProjectLifecycleValidator() {
        workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        issue = getIssue(ISSUE_ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org
     * .eclipse.core.resources.IResourceChangeEvent)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        IResource resource = event.getResource();

        if (resource instanceof IProject) {
            // Validate if a project is being closed or deleted
            IProject project = (IProject) resource;
            if (event.getType() == IResourceChangeEvent.PRE_CLOSE
                    || event.getType() == IResourceChangeEvent.PRE_DELETE) {
                try {
                    if (project.isAccessible()
                            && project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                        scheduleValidationJob(project);
                    }
                } catch (CoreException e) {
                    ValidationActivator.getDefault().getLogger().error(e,
                            Messages.ProjectLifecycleValidator_errorDuringValidation_message);
                }
            }
        } else {
            IResourceDelta delta = event.getDelta();
            if (delta != null) {
                try {
                    // Validate if the delta contains a project being opened
                    delta.accept(new IResourceDeltaVisitor() {
                        public boolean visit(IResourceDelta delta)
                                throws CoreException {
                            IResource resource = delta.getResource();
                            if (resource instanceof IProject) {
                                if ((delta.getFlags()
                                        & IResourceDelta.OPEN) != 0) {
                                    if (resource.isAccessible()
                                            && ((IProject) resource).hasNature(
                                                    XpdConsts.PROJECT_NATURE_ID)) {
                                        scheduleValidationJob(
                                                (IProject) resource);
                                    }
                                }
                                return false;
                            }
                            return true;
                        }
                    });
                } catch (CoreException e) {
                    ValidationActivator.getDefault().getLogger().error(e,
                            Messages.ProjectLifecycleValidator_errorDuringValidation_message);
                }
            }
        }
    }

    /**
     * Validate the given project.
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    public void validate(IProject project, IProgressMonitor monitor)
            throws CoreException {
        if (!monitor.isCanceled()) {
            Map<String, Set<IProject>> idMap = getIdMap(getStudioProjects());
            if (monitor.isCanceled()) {
                return;
            }
            // Clear markers for all projects that have unique ids
            for (Entry<String, Set<IProject>> entry : idMap.entrySet()) {
                Set<IProject> projects = entry.getValue();
                if (projects.size() == 1) {
                    removeMarkers(projects.iterator().next());
                }
            }

            if (monitor.isCanceled()) {
                return;
            }

            // Create new markers if needed
            if (project != null && project.isAccessible()) {
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);
                if (config != null && config.getProjectDetails() != null) {
                    String id = config.getProjectDetails().getId();
                    if (id != null) {
                        Set<IProject> projects = idMap.get(id);
                        if (projects.size() > 1) {
                            IProject[] projArray = projects
                                    .toArray(new IProject[projects.size()]);
                            for (int idx = 0; idx < projArray.length; idx++) {
                                // XPD-3594
                                removeMarkers(projArray[idx]);
                                addMarker(projArray[idx],
                                        idx == 0 ? projArray[1] : projArray[0],
                                        id);

                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Add a project lifecycle marker to the given project.
     * 
     * @param project
     *            project to add marker to
     * @param duplicateProject
     *            the project with the same id as the project being validated
     * @param id
     *            lifecycle id
     * @return created marker
     * @throws CoreException
     */
    private IMarker addMarker(IProject project, IProject duplicateProject,
            String id) throws CoreException {
        IMarker marker = project.createMarker(MARKER_TYPE);
        marker.setAttribute(IMarker.LOCATION, project.getFullPath().toString());
        marker.setAttribute(IMarker.MESSAGE,
                String.format(issue.getMessage(),
                        project.getName(),
                        duplicateProject.getName(),
                        id));
        marker.setAttribute(IMarker.SEVERITY, issue.getSeverity());
        marker.setAttribute(IIssue.ID, issue.getId());
        marker.setAttribute(ATT_DUPLICATE_PROJECT,
                duplicateProject.getFullPath().toString());
        marker.setAttribute(ATT_PROJECT_ID, id);

        return marker;
    }

    /**
     * Remove the project lifecycle markers from the given project.
     * 
     * @param project
     * @throws CoreException
     */
    public void removeMarkers(IProject project) throws CoreException {
        for (IMarker marker : getMarkers(project)) {
            marker.delete();
        }
    }

    /**
     * Get the project lifecycle markers from the given project
     * 
     * @param project
     * @return
     * @throws CoreException
     */
    private IMarker[] getMarkers(IProject project) throws CoreException {
        return project.findMarkers(MARKER_TYPE, false, IResource.DEPTH_ZERO);
    }

    /**
     * Get a map of ids and their corresponding addresses.
     * 
     * @param projects
     * @return
     */
    private Map<String, Set<IProject>> getIdMap(Set<IProject> projects) {
        Map<String, Set<IProject>> ids = new HashMap<String, Set<IProject>>();

        if (projects != null) {
            for (IProject project : projects) {
                ProjectConfig config = XpdResourcesPlugin.getDefault()
                        .getProjectConfig(project);
                if (config != null && config.getProjectDetails() != null) {
                    String id = config.getProjectDetails().getId();
                    if (id != null) {
                        Set<IProject> pr = ids.get(id);
                        if (pr == null) {
                            pr = new HashSet<IProject>();
                            ids.put(id, pr);
                        }
                        pr.add(project);
                    }
                }
            }
        }

        return ids;
    }

    /**
     * Get all Studio (open) projects from the workspace.
     * 
     * @return Set of projects, empty set if none found.
     * @throws CoreException
     */
    private Set<IProject> getStudioProjects() throws CoreException {
        Set<IProject> sProjects = new HashSet<IProject>();
        for (IProject project : workspaceRoot.getProjects()) {
            if (project.isAccessible()
                    && project.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                sProjects.add(project);
            }
        }
        return sProjects;
    }

    /**
     * Schedule a validation job to run the project lifecycle validation.
     * 
     * @param project
     *            The project to validate.
     * @throws CoreException
     *             If an error occurs during validation.
     */
    private synchronized void scheduleValidationJob(final IProject project)
            throws CoreException {
        // Cancel any jobs scheduled before running another job
        if (monitor != null) {
            monitor.setCanceled(true);
        }
        monitor = new NullProgressMonitor();

        // SDA-474 Contrary to previous changes the project lifecycle validator
        // should absolutely not be run in the UI thread. It appears that was
        // done to avoid one deadlock issue, but it actually causes other
        // deadlock issues. The validator does not update the UI, so should be
        // run as a workspace job.
        if (project != null && project.isAccessible()) {
            WorkspaceJob wj = new WorkspaceJob("Project lifecycle validator") { //$NON-NLS-1$
                
                @Override
                public IStatus runInWorkspace(IProgressMonitor monitor)
                        throws CoreException {
                    validate(project, monitor);
                    return Status.OK_STATUS;
                }
            };
            wj.setRule(workspaceRoot);
            wj.setSystem(true);
            wj.schedule();
        }
    }

    /**
     * Get the issue info of the given issue id.
     * 
     * @param issueId
     * @return <code>IssueInfo</code> if found, <code>null</code> otherwise.
     */
    private IssueInfo getIssue(String issueId) {
        IssueInfo info = null;
        Assert.isNotNull(issueId);
        info = ValidationManager.getInstance().getValidationEngine()
                .getIssueInfo(issueId);
        if (info == null) {
            throw new IllegalArgumentException(
                    String.format("Issue with id %s not found", ISSUE_ID)); //$NON-NLS-1$
        }
        return info;
    }
}
