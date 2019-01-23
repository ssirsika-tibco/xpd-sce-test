package com.tibco.xpd.deploy.ui.api;

import com.tibco.xpd.deploy.ServerConfig;

/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

/**
 * This class defines the API for 'defaultServers' extension point to be
 * extended by the contributors in order to add a deployment server to be added
 * to the list of pre-configured servers on creation of workspace. The
 * deployment servers are displayed in the Deployment Server view.
 * 
 * @author agondal
 * @since 1 Sep 2014
 */
public abstract class DefaultServerProvider {

    /**
     * 
     * @return the name of server to be displayed in the Deployment Server view
     */
    public abstract String getServerName();

    /**
     * 
     * @return Id of the server type (ServerTypes are contributed using the
     *         serverTypes extension point)
     */
    public abstract String getServerTypeId();

    /**
     * 
     * @return ID of the server repository type (RepositoryTypes are contributed
     *         using the repositoryTypes extension point)
     */
    public abstract String getServerRepoTypeId();

    /**
     * 
     * @return config details of the server to be added
     */
    public abstract ServerConfig getServerConfig();

}
