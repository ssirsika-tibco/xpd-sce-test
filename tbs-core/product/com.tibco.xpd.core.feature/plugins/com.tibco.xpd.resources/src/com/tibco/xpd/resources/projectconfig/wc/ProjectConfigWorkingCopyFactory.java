/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.wc;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Factory for project folders working copy.
 * 
 * @author njpatel
 */
public class ProjectConfigWorkingCopyFactory implements WorkingCopyFactory {

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     */
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new ProjectConfigWorkingCopy(resource);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     */
    public boolean isFactoryFor(IResource resource) {

        return resource.getName().equals(XpdResourcesPlugin.PROJECTCONFIGFILE);
    }

}
