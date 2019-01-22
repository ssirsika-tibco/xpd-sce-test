package com.tibco.xpd.capabilities.developer;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tibco.xpd.capabilities.developer.messages"; //$NON-NLS-1$

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

	public static String DeveloperActivityTogglerAction_Confirm_title;
	public static String DeveloperActivityTogglerAction_ConfirmSwitchProcessAnalystCapability_message;
	public static String DeveloperActivityTogglerAction_ConfirmSwitchProcessDeveloperCapability_message;
}
