package com.tibco.xpd.deploy.server.jms;

import org.eclipse.osgi.util.NLS;

/**
 * @author glewis
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tibco.xpd.deploy.server.jms.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}	
	
	public static String JMSServerSelectionPage_JMSServerSelectionTitle;	
	public static String JMSServerSelectionPage_JMSServerSelectionMessage;
	public static String JMSConnection_ConnectionFailedOutOfDatePasswordMessage;
}
