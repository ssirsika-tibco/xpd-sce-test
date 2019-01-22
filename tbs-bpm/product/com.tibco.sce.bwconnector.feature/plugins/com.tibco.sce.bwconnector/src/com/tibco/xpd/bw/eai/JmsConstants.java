/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.bw.eai;

import com.tibco.xpd.deploy.ServerConfig;

/**
 * Constants used for configuration of jms services.
 * <p>
 * <i>Created: 18 October 2006</i>
 * 
 * @author glewis
 * 
 */
public class JmsConstants {    
    /** JMS protocol host. */
    public static final String HOST = "host"; //$NON-NLS-1$

    /** JMS protocol port. */
    public static final String PORT = "port"; //$NON-NLS-1$
    
    /** JMS Deployment target queue name. */
    public static final String TARGET_QUEUE_NAME = "targetQueueName"; //$NON-NLS-1$

    /** JMS Deployment JNDI name. */
    public static final String JNDI_NAME = "jndi"; //$NON-NLS-1$

    /** Jms user. */
    public static final String USERNAME = "username"; //$NON-NLS-1$

    /** Jms user's password. */
    public static final String PASSWORD = "password"; //$NON-NLS-1$

    

    /**
     * Obtains value from configuration.
     * 
     * @param config
     *            server configuration.
     * @param key
     *            key of the parameter.
     * @return String value of the parameter.
     * @throws IllegalArgumentException
     *             if parameter for the provided key is not defined.
     */
    public static String getConfigParamValue(ServerConfig config, String key) {
        if (config != null) {
            return config.getConfigParameter(key).getValue();
        }
        return null;
    }

    /**
     * The private constructor. For preventing instantiation.
     */
    private JmsConstants() {
    }
}
