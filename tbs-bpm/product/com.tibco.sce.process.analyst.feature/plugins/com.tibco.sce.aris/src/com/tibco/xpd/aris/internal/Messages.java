/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.aris.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tibco.xpd.aris.internal.messages"; //$NON-NLS-1$

	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String ImportWizard_arisXml_WizardDesc;
	public static String ImportWizard_arisXml_WizardTitle;
    public static String ImportWizard_migrationFailed_error_message;
    public static String ImportWizard_Perform_Post_Import_task_message;
    public static String ImportWizard_shortDesc;
    public static String ImportWizard_title;
}
