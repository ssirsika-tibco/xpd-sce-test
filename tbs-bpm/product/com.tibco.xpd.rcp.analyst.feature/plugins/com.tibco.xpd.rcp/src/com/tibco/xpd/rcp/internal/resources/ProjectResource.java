/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;
import com.tibco.xpd.resources.IWorkingCopyCreationListener;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.wc.ProjectConfigWorkingCopy;

/**
 * Manages the underlying {@link IProject}.
 * 
 * @author njpatel
 * @since 23 Nov 2012
 */
public class ProjectResource extends AbstractRCPResource implements IAdaptable {

    private final String name;

    private IPath location;

    private IProject project;

    private WorkingCopiesListener wcListener;

    private final Set<WorkingCopy> registeredWCList;

    private boolean isDirty;

    /**
     * 
     */
    public ProjectResource(String name) {
        this.name = name;
        registeredWCList = new HashSet<WorkingCopy>();
    }

    public ProjectResource(String name, IPath location) {
        this(name);
        this.location = location;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#getPath()
     * 
     * @return
     */
    @Override
    public IPath getPath() {
        return project != null ? project.getFullPath() : null;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#open()
     * 
     * @throws CoreException
     */
    @Override
    public boolean open(IProgressMonitor monitor) throws CoreException {
        try {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            monitor.beginTask(name, 5);
            project = root.getProject(name);

            if (!project.exists()) {
                project.create(getProjectDescription(name, location),
                        new NullProgressMonitor());
            }
            monitor.worked(1);

            if (!project.isOpen()) {
                project.open(new NullProgressMonitor());
            }

            monitor.worked(1);

            notifyResourceChange(RCPResourceEventType.ADDED, project, this);

            monitor.worked(1);

            wcListener = new WorkingCopiesListener();

            XpdResourcesPlugin.getDefault()
                    .addWorkingCopyCreationListener(wcListener, true);

            monitor.worked(1);
        } finally {
            monitor.done();
        }
        return true;
    }

    /**
     * Get the project description for the given project.
     * 
     * @param projectName
     *            the name of the project being opened/created
     * @param externalProjectPath
     *            the external path of the project folder, <code>null</code> if
     *            this project will be in the workspace.
     * @return
     */
    private IProjectDescription getProjectDescription(String projectName,
            IPath externalProjectPath) {
        IProjectDescription description =
                ResourcesPlugin.getWorkspace()
                        .newProjectDescription(projectName);
        // Set the location to the external location
        description.setLocation(externalProjectPath);

        return description;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#isDirty()
     * 
     * @return
     */
    @Override
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * Get the Eclipse project wrapped by this instance.
     * 
     * @return
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.ui.services.IDisposable#dispose()
     * 
     */
    @Override
    public void dispose() {
        unregisterWorkingCopyListener();

    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ProjectResource) {
            return ((ProjectResource) obj).name.equals(name);
        }
        return super.equals(obj);
    }

    /**
     * Unregister the working copy listener from all managed working copies.
     */
    private void unregisterWorkingCopyListener() {
        for (WorkingCopy wc : registeredWCList) {
            wc.removeListener(wcListener);
        }
        registeredWCList.clear();
        XpdResourcesPlugin.getDefault()
                .removeWorkingCopyCreationListener(wcListener);
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPResource#doSave(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    protected boolean doSave(IProgressMonitor monitor) throws CoreException {
        if (monitor == null) {
            monitor = new NullProgressMonitor();
        } else {
            if (monitor.isCanceled()) {
                return false;
            }
        }
        if (project != null) {
            // If the project is out-of-sync then update it
            if (!project.isSynchronized(IResource.DEPTH_INFINITE)) {
                project.refreshLocal(IResource.DEPTH_INFINITE, null);
            }

            // Save any open dirty editors, if any
            saveAllEditors();

            // Save all working copies
            saveAllWorkingCopies();

            setIsDirty(false);
        }
        return true;

    }

    /**
     * Save all dirty editors.
     */
    private void saveAllEditors() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null && workbench.getDisplay() != null) {
            workbench.getDisplay().syncExec(new Runnable() {

                @Override
                public void run() {
                    workbench.saveAllEditors(false);
                }
            });
        }
    }

    /**
     * Save all working copies
     * 
     * @throws IOException
     */
    protected void saveAllWorkingCopies() throws CoreException {
        try {
            for (WorkingCopy wc : registeredWCList) {
                if (wc.isWorkingCopyDirty()) {
                    wc.save();
                }
            }
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.ERROR,
                    RCPActivator.PLUGIN_ID,
                    Messages.MAAResource_errorDuringSave_error_message, e));
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.AbstractRCPResource#doDelete()
     * 
     * @throws CoreException
     */
    @Override
    protected void doDelete() throws CoreException {
        unregisterWorkingCopyListener();
        if (project != null) {
            if (location != null) {
                // External project so don't delete project from file system
                project.delete(false, true, new NullProgressMonitor());
            } else {
                project.delete(true, new NullProgressMonitor());
            }

        }

    }

    /**
     * Mark this resource as dirty/saved.
     */
    private void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
        notifyDirtyChange(isDirty);
    }

    /**
     * Working copy listener.
     * 
     * @author njpatel
     * 
     */
    private class WorkingCopiesListener implements
            IWorkingCopyCreationListener, PropertyChangeListener {

        @Override
        public void workingCopyCreated(WorkingCopy wc) {

            if (!(wc instanceof ProjectConfigWorkingCopy)
                    && isFromThisProject(wc)) {
                if (!registeredWCList.contains(wc)) {
                    wc.addListener(this);
                    registeredWCList.add(wc);

                    /*
                     * If called during import of a project then ignore.
                     */
                    if (!XpdResourcesPlugin.getDefault()
                            .isProjectsImportInProgress()) {
                        notifyResourceChange(RCPResourceEventType.ADDED,
                                wc.getEclipseResources().get(0),
                                ProjectResource.this);
                        setIsDirty(true);
                    }
                }
            }
        }

        private boolean isFromThisProject(WorkingCopy wc) {
            if (wc != null && wc.getEclipseResources() != null
                    && !wc.getEclipseResources().isEmpty()) {
                IResource resource = wc.getEclipseResources().get(0);
                return project.equals(resource.getProject());
            }
            return false;
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (shouldStopListener()
                    || XpdResourcesPlugin.getDefault()
                            .isProjectsImportInProgress()) {
                return;
            }
            if (WorkingCopy.CHANGED.equals(event.getPropertyName())
                    || WorkingCopy.PROP_RELOADED
                            .equals(event.getPropertyName())) {
                // Notify that this resource has changed
                notifyResourceChange(RCPResourceEventType.CHANGED,
                        event,
                        ProjectResource.this);
            } else if (event.getSource() instanceof WorkingCopy) {
                WorkingCopy wc = (WorkingCopy) event.getSource();
                if (WorkingCopy.PROP_DIRTY.equals(event.getPropertyName())) {
                    setIsDirty(true);
                } else if (WorkingCopy.PROP_REMOVED.equals(event
                        .getPropertyName())) {
                    if (event.getSource() instanceof WorkingCopy) {
                        wc.removeListener(this);
                        registeredWCList.remove(event.getSource());
                        notifyResourceChange(RCPResourceEventType.REMOVED,
                                wc.getEclipseResources().get(0),
                                ProjectResource.this);
                        setIsDirty(true);
                    }
                }
            }
        }
    }

    /**
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     * 
     * @param adapter
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Object getAdapter(Class adapter) {
        if (adapter == IResource.class || adapter == IContainer.class
                || adapter == IProject.class) {
            return project;
        }
        return null;
    }

}
