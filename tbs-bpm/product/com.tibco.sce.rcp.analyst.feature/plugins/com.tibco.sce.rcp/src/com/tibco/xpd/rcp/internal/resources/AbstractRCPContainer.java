/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * 
 * @author njpatel
 * @since 23 Nov 2012
 */
public abstract class AbstractRCPContainer extends AbstractRCPResource
        implements IRCPContainer {

    private final List<ProjectResource> projectResources;

    private final ProjectResourceListener projectListener;

    private final WorkspaceListener workspaceListener;

    /**
     * 
     */
    public AbstractRCPContainer() {
        projectResources =
                Collections.synchronizedList(new ArrayList<ProjectResource>());
        projectListener = new ProjectResourceListener();

        /*
         * Register the workspace listener to monitor any project
         * renames/deletes
         */
        workspaceListener = new WorkspaceListener();

        ResourcesPlugin.getWorkspace()
                .addResourceChangeListener(workspaceListener,
                        IResourceChangeEvent.POST_CHANGE);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#getProjectResources()
     * 
     * @return
     */
    @Override
    public Collection<ProjectResource> getProjectResources() {
        return new ArrayList<ProjectResource>(projectResources);
    }

    /**
     * Add the given project resource to the list of resources being managed by
     * this container.
     * 
     * @param projectResource
     */
    protected void addProjectResource(ProjectResource projectResource) {
        if (projectResource != null
                && !projectResources.contains(projectResource)) {
            projectResource.addChangeListener(projectListener);
            projectResources.add(projectResource);
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#deleteProjectResource(com.tibco.xpd.rcp.internal.resources.ProjectResource)
     * 
     * @param projectResource
     * @throws CoreException
     */
    @Override
    public void deleteProjectResource(ProjectResource projectResource)
            throws CoreException {
        deleteProjectResource(projectResource, true);
    }

    /**
     * Delete the given project resource
     * 
     * @param projectResource
     *            resource to delete
     * @param notify
     *            <code>true</code> if a project_remove notification should be
     *            sent.
     * @throws CoreException
     */
    private void deleteProjectResource(ProjectResource projectResource,
            boolean notify) throws CoreException {
        if (projectResource != null && !projectResources.isEmpty()) {
            projectResource.delete();
            removeProjectResource(projectResource, notify);
        }
    }

    /**
     * Removes the given project resource from this MAA resource. Note: Use
     * {@link #deleteProjectResource(ProjectResource)} if the underlying project
     * should be deleted too.
     * 
     * @param projectResource
     *            resource to remove
     * @param notify
     *            <code>true</code> if a project_removed notification should be
     *            sent.
     * 
     * @return <code>true</code> if the project resource was removed.
     */
    protected boolean removeProjectResource(ProjectResource projectResource,
            boolean notify) {
        if (projectResource != null && !projectResources.isEmpty()) {
            projectResource.removeChangeListener(projectListener);
            projectResource.dispose();

            projectResources.remove(projectResource);

            if (notify) {
                notifyResourceChange(RCPResourceEventType.REMOVED,
                        projectResource.getProject(),
                        this);
            }

            return true;
        }

        return false;
    }

    /**
     * Get the project resource that manages the given project.
     * 
     * @param project
     * @return {@link ProjectResource} if one is managing the given project,
     *         <code>null</code> if no project resource is found for this
     *         project.
     */
    private ProjectResource getProjectResource(IProject project) {
        synchronized (projectResources) {
            for (ProjectResource resource : projectResources) {
                if (project.equals(resource.getProject())) {
                    return resource;
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPContainer#createProjectResource(java.lang.String)
     * 
     * @param name
     * @return
     * @throws CoreException
     */
    @Override
    public ProjectResource createProjectResource(String name)
            throws CoreException {
        if (name != null) {
            ProjectResource resource = doCreateProjectResource(name);

            if (resource != null) {
                addProjectResource(resource);
                return resource;
            }
        }
        return null;
    }

    /**
     * Create a {@link ProjectResource} for a project with the given name.
     * 
     * @param name
     * @return {@link ProjectResource} or <code>null</code> if one cannot be
     *         created.
     */
    protected abstract ProjectResource doCreateProjectResource(String name);

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#isDirty()
     * 
     * @return
     */
    @Override
    public boolean isDirty() {
        synchronized (projectResources) {
            for (ProjectResource resource : projectResources) {
                if (resource.isDirty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @see org.eclipse.ui.services.IDisposable#dispose()
     * 
     */
    @Override
    public void dispose() {

        // Unregister the workspace listener
        if (workspaceListener != null) {
            ResourcesPlugin.getWorkspace()
                    .removeResourceChangeListener(workspaceListener);
        }

        // Delete all projects
        synchronized (projectResources) {
            for (ProjectResource resource : projectResources) {
                resource.removeChangeListener(projectListener);
                try {
                    resource.delete();
                } catch (CoreException e) {
                    RCPActivator.getDefault().getLogger().error(e);
                }
                resource.dispose();
            }
            projectResources.clear();
        }

        notifyResourceChange(RCPResourceEventType.DISPOSED, null, this);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPResource#doDelete()
     * 
     * @throws CoreException
     */
    @Override
    protected void doDelete() throws CoreException {
        synchronized (projectResources) {
            for (ProjectResource resource : projectResources) {
                deleteProjectResource(resource);
            }
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#open(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    public final boolean open(IProgressMonitor monitor) throws CoreException {
        /*
         * Indicate the import is in progress. This will stop any
         * validation/indexing to happen while the projects are being
         * opened(imported into the workspace).
         */
        XpdResourcesPlugin.getDefault().setIsProjectsImportInProgress(true);
        try {
            return doOpen(monitor);
        } finally {
            XpdResourcesPlugin.getDefault()
                    .setIsProjectsImportInProgress(false);
        }
    }

    /**
     * @param monitor
     * @return
     */
    protected abstract boolean doOpen(IProgressMonitor monitor)
            throws CoreException;

    /**
     * Project listener that will delegate change events to listeners of this
     * resource.
     * 
     */
    private class ProjectResourceListener implements RCPResourceChangeListener {

        /**
         * @see com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener#resourceChanged(com.tibco.xpd.rcp.internal.resources.IRCPResource,
         *      com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.ResourceChangeEvent)
         * 
         * @param resource
         * @param event
         */
        @Override
        public void resourceChanged(IRCPResource resource,
                RCPResourceChangeEvent event) {
            if (event.eventType == RCPResourceEventType.DIRTY) {
                // Dirty state of one of the projects has changed so fire dirty
                // change
                notifyDirtyChange(isDirty());
            } else {
                notifyResourceChange(event.eventType,
                        event.eventObj,
                        event.source);
            }
        }

    }

    /**
     * Workspace resource listener to monitor for project deletes/renames. This
     * needs to update the project resources list maintained by this MAA
     * resource.
     * 
     */
    private class WorkspaceListener implements IResourceChangeListener,
            IResourceDeltaVisitor {

        /**
         * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
         * 
         * @param event
         */
        @Override
        public void resourceChanged(IResourceChangeEvent event) {
            IResourceDelta delta = event.getDelta();
            if (delta != null) {
                try {
                    delta.accept(this);
                } catch (CoreException e) {
                    // Log the issue
                    RCPActivator
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    "RCP container workspace resource listener reported a problem."); //$NON-NLS-1$
                }
            }
        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        @Override
        public boolean visit(IResourceDelta delta) throws CoreException {
            /*
             * Check for project changes.
             */
            IResource resource = delta.getResource();

            if (resource instanceof IProject) {
                IProject project = (IProject) resource;
                IProject newProject = null;
                if (delta.getKind() == IResourceDelta.REMOVED) {
                    // Project has been removed
                    ProjectResource projectResource =
                            getProjectResource(project);

                    if (projectResource != null) {
                        removeProjectResource(projectResource, true);

                        if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
                            /*
                             * This project has been moved
                             */
                            IPath movedToPath = delta.getMovedToPath();
                            if (movedToPath != null) {
                                newProject =
                                        (IProject) resource.getWorkspace()
                                                .getRoot()
                                                .findMember(movedToPath);
                            }
                        }
                    }
                } else if (delta.getKind() == IResourceDelta.ADDED) {
                    if (isNewProject(project)) {
                        /*
                         * New project has been added to the workspace so add to
                         * this resource - this is probably because the user
                         * undid a project delete.
                         */
                        newProject = project;
                    }
                }

                /*
                 * If a new project was added then create a project resource for
                 * it and add it to this container.
                 */
                if (newProject != null) {
                    ProjectResource newProjectResource =
                            createProjectResource(newProject.getName());

                    if (newProjectResource != null) {
                        newProjectResource.open(new NullProgressMonitor());
                    }
                }

                return false;
            }
            return true;
        }

        /**
         * Check if the given project already exists in this container.
         * 
         * @param resource
         * @return
         */
        private boolean isNewProject(IProject project) {
            ProjectResource projectResource = getProjectResource(project);
            return projectResource == null;
        }

    }
}
