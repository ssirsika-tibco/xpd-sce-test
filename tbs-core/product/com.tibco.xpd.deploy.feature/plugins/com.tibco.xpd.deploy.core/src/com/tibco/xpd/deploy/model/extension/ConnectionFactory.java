/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import com.tibco.xpd.deploy.Server;

/**
 * Factory for creating connections to server. Concrete implementations of this
 * interface are created for particular types of server which are defined by
 * <code>com.tibco.xpd.deploy.core.serverTypes</code> extension.
 * <p>
 * <i>Created: 27 Sep 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface ConnectionFactory {

    /**
     * Creates connection for particular server. Returned connection is in
     * initial state (not connected).
     * 
     * @param server
     *            server for which the connection is being created.
     * @return class responsible for connecting server.
     */
    Connection createConnection(Server server);
}