/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.net.URL;
import java.util.List;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;

/**
 * Support for pre /post deployment operations.
 * The typical usage is that if the server supports workspace modules then its
 * connection is adaptable to this interface and should only be used if there is
 * a requirement to do some processing before / after deployment occurs. 
 * 
 * @author glewis
 *
 */
public interface DeploymentInterceptor {
    /**
     * Used to perform some operation before deployment takes place. 
     */
    void beforeDeployment(Server server, List<URL> moduleUrls, List<WorkspaceModule> modulesList);
    
    /**
     * Used to perform some operation after deployment takes place.
     */
    void afterDeployment(Server server, List<URL> moduleUrls, List<WorkspaceModule> modulesList);   
}
