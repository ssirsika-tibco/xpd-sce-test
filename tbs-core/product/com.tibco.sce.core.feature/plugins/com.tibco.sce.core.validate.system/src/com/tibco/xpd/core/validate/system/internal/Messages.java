/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.core.validate.system.internal;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {

    private static final String BUNDLE_NAME =
            "com.tibco.xpd.core.validate.system.internal.messages"; //$NON-NLS-1$

    /**
     * Do not instantiate this class
     */
    private Messages() {
    }

    public static String CheckSuccess_title;

    public static String CheckNoSuccess_title;

    public static String AdviseExecutionFailed;

    public static String SystemCheckRules_activePerspective_shortdesc;

    public static String SystemCheckRules_cannotDeterminePerspective_shortdesc;

    public static String SystemCheckRules_configurationOptions_heading_title;

    public static String SystemCheckRules_executionEnv_heading_title;

    public static String SystemCheckRules_executionEnvironmentIsValid_shortdesc;

    public static String SystemCheckRules_javaIsBelowRequiredVersion_warning_shortdesc;

    public static String SystemCheckRules_Perspective_heading_title;

    public static String SystemCheckRules_perspectiveIsNotModeling_shortdesc;

    public static String SystemCheckRules_potentialProblems_shortdesc;

    public static String SystemCheckRules_switchToModelingPerpsective_hyperlink_shortdesc;

    public static String ValidateSystemDialog_Cancelling_shortdesc;

    public static String ValidateSystemDialog_cancelProcess_tooltip;

    public static String ValidateSystemDialog_checkingInstallation_wait_shortdesc;

    public static String ValidateSystemDialog_errorMessage_shortdesc;

    public static String ValidateSystemDialog_infoMessage_shortdesc;

    public static String ValidateSystemDialog_processCancelled_shortdesc;

    public static String ValidateSystemDialog_section_shortdesc;

    public static String ValidateSystemDialog_section_title;

    public static String ValidateSystemDialog_title_notrans;

    public static String ValidateSystemDialog_warningMessage_shortdesc;

    public static String SystemCheckRules_TargetPlatform_SetValid_Platform;

    public static String SystemCheckRules_TargetPlatform_Valid;

    public static String TargetPlatformValidationUtils_DEFAULT_USED;

    public static String TargetPlatformValidationUtils_TargetPlatform_Default_Not_Used_Msg;

    public static String TargetPlatformValidationUtils_TargetPlatform_InvalidError_Msg;

    public static String TargetPlatformValidationUtils_TargetPlatform_NotSet_Msg;

    static {
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

}
