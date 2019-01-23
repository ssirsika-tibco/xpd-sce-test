/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.util.Collection;

import org.eclipse.core.resources.IResource;

/**
 * Interface to be implemented by the working copy dependency provider
 * registered using the extension point
 * <code>com.tibco.xpd.resources.workingCopyDependencyProvider</code>
 * 
 * @author nwilson
 */
public interface IWorkingCopyDependencyProvider {

    /**
     * Get the {@link WorkingCopy} class that this dependency provider is
     * providing for.
     * 
     * @return The class to which dependencies should be contributed.
     */
    Class<? extends WorkingCopy> getWorkingCopyClass();

    /**
     * Get the list of resources that the given working copy references.
     * <p>
     * Since 3.5.5 a {@link DependencyProxyResource} can be returned for all
     * references that cannot be resolved to a physical resource in the
     * workspace. This proxy will be resolved (if possible) when the working
     * copy is asked for the dependencies.
     * </p>
     * 
     * @param wc
     *            The working copy instance.
     * @return A collection of dependencies to contribute.
     */
    Collection<IResource> getDependencies(WorkingCopy wc);

}
