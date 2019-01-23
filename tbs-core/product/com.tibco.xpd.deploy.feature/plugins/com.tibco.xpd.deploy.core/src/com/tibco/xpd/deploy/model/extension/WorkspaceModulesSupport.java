/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IResourceDelta;

import com.tibco.xpd.deploy.WorkspaceModule;

/**
 * Support for notifications of resource changes which affect workspace modules.
 * The typical usage is that if the server supports workspace modules then its
 * connection is adaptable to this interface.
 * <p>
 * <i>Created: 14 Feb 2007</i>
 * @see com.tibco.xpd.deploy.model.extension.DeploymentInterceptor
 * @author Jan Arciuchiewicz
 * 
 */
public interface WorkspaceModulesSupport {
    /**
     * Returns list of workspace modules which were affected by the workspace
     * chenge. If the resources representing module were changed then it will be
     * included in the list.
     * 
     * @param delta
     *            resource delta with workspace changes.
     * @return list of WorkspaceModules for which corresponding resources has
     *         changed. If no workspace modules were affected then method will
     *         return empty list.
     */
    List<WorkspaceModule> getAffectedWorkspaceModules(IResourceDelta delta);

    /**
     * Remove unnecessary workspace modules if the delta indicates that
     * corresponding module resources were deleted.
     * 
     * @param delta
     *            resource delta with workspace changes.
     */
    void cleanWorkspaceModules(IResourceDelta delta);

    /**
     * Remove unnecessary workspace modules if the corresponding workspace
     * resources don't exist.
     */
    void cleanWorkspaceModules();

    /**
     * Returns URL to the compiled workspace module resource. Compiled resource
     * is a resource which is ready to deploy.
     * 
     * @param module
     * @return URL to the compiled workspace module resource. Compiled resource
     *         is a resource which is ready to deploy.
     */
    URL getWorkspaceModuleDeploymentURL(WorkspaceModule module);
}
