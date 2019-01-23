/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;

/**
 * Factory used to get needed resources for xpd project. Factory should be
 * obtained through XpdResourcesPlugin for IProject
 */
public interface XpdProjectResourceFactory {
    /**
     * Resolve reference from given resource using registered
     * ResourceReferenceResolvers.
     * 
     * @param source
     *            resource that contains reference (may be null for global
     *            references)
     * @param reference
     *            textual reference description
     * @param referenceType
     *            type of reference (used to find registered resolver)
     * 
     * @return IResource that is referenced
     */
    IResource resolveResourceReference(IResource source, String reference,
            String referenceType);

    /**
     * Returns working copy for given eclipse resource. Returned WorkingCopy can
     * contain other resources as well.
     * 
     * It can return null, if given reosurce doesn't have working copy.
     * 
     * @param resource
     *            eclipse resource that contains the reference
     * @return working copy for this resource
     */
    WorkingCopy getWorkingCopy(IResource resource);

    /**
     * This method iterates through map of working copies of the resources and
     * returns the ones who are dirty.
     * 
     * @return map with dirty resources.
     */
    Map getDirtyResources();

    /**
     * Get all working copies that have been loaded.
     * 
     * @return Array of <code>WorkingCopy</code> objects, empty list of none
     *         created.
     */
    public WorkingCopy[] getWorkingCopies();

    /**
     * Notifies that resources which may be associated with the factory has
     * changed.
     * 
     * @param delta
     *            Delta with details about changes.
     * 
     * @deprecated This method is no longer used (since 3.0).
     */
    void factoryResourceChanged(IResourceDelta delta);

}
