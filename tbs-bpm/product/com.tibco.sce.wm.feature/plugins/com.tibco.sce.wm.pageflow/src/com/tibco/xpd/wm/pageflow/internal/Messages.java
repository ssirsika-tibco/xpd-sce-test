/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.wm.pageflow.internal.messages"; //$NON-NLS-1$

    public static String N2PageflowActivityScriptsRules_UserTaskTypeValueCancelled;

    public static String N2PageflowActivityScriptsRules_UserTaskTypeValueCompleted;

    public static String N2PageflowActivityScriptsRules_UserTaskTypeValueDeadlineExpired;

    public static String N2PageflowActivityScriptsRules_UserTaskTypeValueInitiated;

    public static String N2PageflowImplementationTypeRules_Unspecified_label;

    public static String RemoveInvalidUserTaskScriptsResolution_RemoveUnsupprotedUserTaskScripts_menu;

    public static String RenameActionContributorParticipant_ChangeOk_Label;

    public static String RenameActionContributorParticipant_projectConfigNotFound_error_shortdesc;

    public static String RenameProjectContributorParticForBSCategory_InteruptedException_message;

    public static String RenameProjectContributorParticForBSCategory_IOException_message;

    public static String RenameProjectContributorParticForBSCategory_RenameBusinessService_refactorname;

    public static String RenameProjectContributorParticForBSCategory_RolledBackException_message;

    public static String SetAssociatedDataToInModeResolution_SetAssocDataToInMode_menu;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
