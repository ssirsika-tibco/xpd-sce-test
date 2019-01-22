/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;

/**
 * Constants used for configuration of iProcess services.
 * <p>
 * <i>Created: 26 October 2008</i>
 * 
 * @author glewis
 * 
 */
public class BaseJmxConstants {	

	/** JMX/RMI protocol host:port. */
	public static final String HOST_PORT = "n2JMX"; //$NON-NLS-1$		

	/** user name. */
	public static final String USERNAME = "username"; //$NON-NLS-1$

	/** user's password. */
	public static final String PASSWORD = "password"; //$NON-NLS-1$
	
	public static final String PATH = "path"; //$NON-NLS-1$

    /** JMX connector name (default should be rmi). */
    public static final String MBEAN_CONNECTOR_TYPE = "mbeanConnector"; //$NON-NLS-1$
    
    /** domain name e.g DEDeploymentServer */
    public static final String DOMAIN_NAME = "domainName"; //$NON-NLS-1$

    /** object type e.g. com.tibco.n2.deployment.jmx.DeploymentService */
    public static final String OBJECT_TYPE = "objectType"; //$NON-NLS-1$
    
    /** valid extensions (space delimted) e.g .jar .xml */
    public static final String VALID_EXTS = "validExts"; //$NON-NLS-1$
    
	
	/**
	 * 	/**
	 * The private constructor. For preventing instantiation.
	 */
	private BaseJmxConstants() {
	}

	/**
	 * Obtains value from configuration.
	 * <p>
	 * Consider using
	 * <code>getConfigParamValue(:ServerConfig, :String, :Server):String</code>
	 * as it will permit default value to be returned if the param has no value. 
	 * This better supports migration between versions when new parameters are 
	 * added in new versions. 
	 * </p>
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
		return getConfigParamValue(config, key, null);
	}

	/**
	 * Obtains value from configuration.
	 * 
	 * @param config
	 *            server configuration.
	 * @param key
	 *            key of the parameter.
	 * @param server
	 *            the server whose configuration is being interrogated. If no
	 *            param value is found in the config the server type's default
	 *            will be returned.
	 * @return String value of the parameter or default for the server type if
	 *         none is found.
	 * @throws IllegalArgumentException
	 *             if parameter for the provided key is not defined.
	 */
	public static String getConfigParamValue(ServerConfig config, String key,
			Server server) {
		if (config != null) {
			ConfigParameter configParameter = config.getConfigParameter(key);
			if (configParameter == null) {
				if (server != null){
    				// this may happen when new parameters are added
    				// take default to support existing (unmigrated) configurations
    				for (ConfigParameterInfo paramInfo : server.getServerType()
    						.getServerConfigInfo().getConfigParameterInfos()) {
    					if (paramInfo.getKey().equals(key)) {
    						return paramInfo.getDefaultValue();
    					}
    				}
				}
			} else {
				return configParameter.getValue();
			}
		}
		return null;
	}
}
