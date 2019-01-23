/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * 
 * 
 * @author njpatel
 * @since 23 Nov 2012
 */
public interface IRCPContainer extends IRCPResource {

    /**
     * Get the children resources of this container.
     * 
     * @return
     */
    Collection<ProjectResource> getProjectResources();

    /**
     * Delete the project resource with the given name.
     * 
     * @param projectResource
     * @throws CoreException
     */
    void deleteProjectResource(ProjectResource projectResource)
            throws CoreException;

    /**
     * Create a project resource with the given name.
     * 
     * @param name
     * @return
     * @throws CoreException
     */
    ProjectResource createProjectResource(String name) throws CoreException;

    /**
     * Introspect this resource and return the list of names of the project
     * contained in this resource.
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    List<String> introspect(IProgressMonitor monitor) throws CoreException;
}
