/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;

/**
 * @author glewis
 *
 */
public class BaseJmxConnectionFactory implements ConnectionFactory {

	public Connection createConnection(Server server) {		
	    return new BaseJmxConnection(server);
	}

}
