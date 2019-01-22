/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.deploy.webdav.internal;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;

/**
 * Connection factory for Generic WebDAV server.
 * 
 * @author Jan Arciuchiewicz
 */
public class GenericWebDAVConnectionFactory implements ConnectionFactory {

    /**
     * {@inheritDoc}
     */
    public Connection createConnection(Server server) {
        return new GenericWebDAVConnection(server);
    }

}
