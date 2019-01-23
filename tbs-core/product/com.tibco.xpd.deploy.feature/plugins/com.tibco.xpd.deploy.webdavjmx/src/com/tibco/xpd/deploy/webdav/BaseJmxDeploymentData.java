/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

/**
 * 
 * @author tstephen
 *
 */
public class BaseJmxDeploymentData {

	/**
	 * Find out if JMX Notifications are being handled
	 *
	 * @return	True if supported, otherwise False
	 */
	public boolean isJmxNotificationsEnabled()
	{
		// We now always support JMX notifications
		boolean jmxNotificationsEnabled = true;
		return jmxNotificationsEnabled;
	}
}
