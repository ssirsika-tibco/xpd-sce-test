/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.services.IDisposable;

/**
 * 
 * 
 * @author njpatel
 * @since 23 Nov 2012
 */
public interface IRCPResource extends IDisposable {

    /**
     * Get the human-readable name of this resource.
     * 
     * @return name of this resource, <code>null</code> if not set.
     */
    String getName();

    /**
     * Get the path of this resource.
     * 
     * @return path of this resource, <code>null</code> if not set.
     */
    IPath getPath();

    /**
     * Open the underlying resource - this will also create the {@link IProject}
     * in the RCP workspace.
     * 
     * @param monitor
     * @return <code>true</code> if the resource was opened, <code>false</code>
     *         if failed to open or user cancelled (e.g. cancelled migrating to
     *         latest revision).
     * @throws CoreException
     */
    boolean open(IProgressMonitor monitor) throws CoreException;

    /**
     * Save all changes in the underlying resource.
     * 
     * @throws CoreException
     * 
     * @return <code>true</code> if the save was done, <code>false</code> if
     *         save was not done (resource was not dirty or used cancelled).
     */
    boolean save(IProgressMonitor monitor) throws CoreException;

    /**
     * Delete the underlying resource (project).
     * 
     * @throws CoreException
     */
    void delete() throws CoreException;

    /**
     * Check if the underlying resource is dirty.
     * 
     * @return
     */
    boolean isDirty();

    /**
     * Add a change notification listener.
     * 
     * @param listener
     */
    void addChangeListener(RCPResourceChangeListener listener);

    /**
     * Remove a change notification listener.
     * 
     * @param listener
     */
    void removeChangeListener(RCPResourceChangeListener listener);

}
