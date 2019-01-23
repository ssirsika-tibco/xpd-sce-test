package com.tibco.xpd.deploy.ui.api;

import java.util.Set;

import org.eclipse.core.resources.IResource;

public interface IDeploymentResolver {

    /**
     * Finds dependent resources. 
     *
     * @param resource
     *            One or more resources to find dependencies for. For example, one xpdl
     *            or a set of form files or a project.
     * @return 
     */
    Set<IResource> getDependentModules(Set<IResource> resource);
    
}
