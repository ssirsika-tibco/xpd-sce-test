/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources;

import org.eclipse.core.resources.IResource;

/**
 * Factory for {@link com.tibco.xpd.resources.WorkingCopy} instances provided by
 * the extension point <code>com.tibco.xpd.resources.workingCopyFactory</code>.
 * 
 * @author mmaciuki
 */
public interface WorkingCopyFactory {

    /**
     * Returns new instance working copy for given eclipse resource.
     * 
     * @param resource
     *            resource for which we need WorkingCopy
     * @return working copy
     */
    WorkingCopy getWorkingCopy(IResource resource);

    /**
     * Check if the factory is applicable for given resource.
     * 
     * @param resource
     *            resource to check
     * @return true, if the factory is applicable for given resource
     */
    boolean isFactoryFor(IResource resource);
}
