/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.navigator;

import com.tibco.xpd.deploy.ui.api.IDeploymentResolver;

/**
 * Class to wrap the Drag and drop deployment settings
 * for a server
 *
 * 
 * @author Miguel Torres
 * 
 */
public class DeployResolver {

    String validExtensions;
    
    IDeploymentResolver deploymentResolver;
    
    private String serverTypeId;

    public DeployResolver(String serverTypeId,
            IDeploymentResolver deploymentResolver, String validExtensions) {
        this.serverTypeId = serverTypeId;
        this.deploymentResolver = deploymentResolver;
        this.validExtensions = validExtensions;
    }
    
    public String getServerTypeId() {
        return serverTypeId;
    }

    public void setServerTypeId(String serverTypeId) {
        this.serverTypeId = serverTypeId;
    }
    
    public String getValidExtensions() {
        return validExtensions;
    }

    public void setValidExtensions(String validExtensions) {
        this.validExtensions = validExtensions;
    }

    public IDeploymentResolver getDeploymentResolver() {
        return deploymentResolver;
    }

    public void setDeploymentResolver(IDeploymentResolver deploymentResolver) {
        this.deploymentResolver = deploymentResolver;
    }
}
