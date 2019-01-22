/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;
import com.tibco.xpd.deploy.webdav.WebDAVConnection;

public class DeployConnectionFactory implements ConnectionFactory {

    /* (non-Javadoc)
     * @see com.tibco.xpd.deploy.model.extension.ConnectionFactory#createConnection(com.tibco.xpd.deploy.Server)
     */
    public Connection createConnection(Server server) {
        return new WebDAVConnection(server);
    }
    
    /**
     * @param server
     * @param configSiteURLKey
     * @param configRootFolderKey
     * @param configUsernameKey
     * @param configPasswordKey
     * @return
     */
    public Connection createConnection(Server server, String configSiteURLKey, String configRootFolderKey, String configUsernameKey, String configPasswordKey) {
        return new WebDAVConnection(server, configSiteURLKey, configRootFolderKey, configUsernameKey, configPasswordKey);
    }

}
