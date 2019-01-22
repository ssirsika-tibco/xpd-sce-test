package com.tibco.xpd.deploy.server.jms;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.model.extension.Connection;
import com.tibco.xpd.deploy.model.extension.ConnectionFactory;

/**
 * Connection factory for the JMS specific servers connection.
 * @author glewis
 *
 */
public class JMSConnectionFactory implements ConnectionFactory {

    public Connection createConnection(Server server) {
       
        return new JmsConnection(server);
    }

}
