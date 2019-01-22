/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.tibco.xpd.process.analyst.branding.messages"; //$NON-NLS-1$

	public static String EnableActivities_ActivityNotFound_message;

	public static String EnableActivities_EmptyActivityId_message;

	public static String EnableActivities_EnablingActivity_message;

	public static String EnableActivities_NoActivityId_message;

	public static String IllegalCheatSheetId_message;

	public static String InstallSampleProjectOperation_ProjectAlreadyExistsConfirmOverwrite_message;

	public static String InstallSampleProjectOperation_shortdesc;

	public static String InstallSampleProjectOperation_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String InstallSampleWizard_IntroPage_label;

	public static String InstallSampleWizard_longdesc;

	public static String InstallSampleWizard_title;

	public static String SampleProjectsIntroAction_ProjectIdNonExistent_message;

	public static String SampleProjectsIntroAction_ProjectIdParamMissing_message;

	public static String ShowStandby_EmptyPartId_message;

	public static String ShowStandby_EmptyPluginId_message;

	public static String ShowStandby_InvalidUrl_message;

	public static String ShowStandby_NullInputId_message;

	public static String ShowStandby_NullPartId_message;

	public static String ShowStandby_NullPluginId_message;

	public static String SwitchPerspective_IllegalPerspectiveId_message;

	public static String SwitchPerspective_PerspectiveNotFound_message;

	private Messages() {
	}
}
