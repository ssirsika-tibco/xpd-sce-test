/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author wzurek
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.validation.xpdl2.internal.messages"; //$NON-NLS-1$

    public static String ChangeImplEventTriggerType_ChangeTriggerType_shortdesc;

    public static String ChangeStartMethodEventType_ChangeStartMethodTriggerTypeCmd_label;

    public static String DeleteNonImplementedEventsResolution_RemoveUnimplementedActivity_menu;

    public static String InterfaceDestinationResolution_EnableInterfaceDests_menu;

    public static String RecreateObjectIDs_message;

    public static String RecreateObjectIDs_title;

    public static String SingleNoneTypeEventResolution_SetTrigTypeMsg_menu;

    public static String DeleteNonExistentAssociatedParameter;

    public static String SetActivityNameResolution_EnterActName_title;

    public static String SetActivityNameResolution_MustEnterValidName_longdesc;

    public static String SetActivityNameResolution_MustNotHaveLeadTrailSpaces_longdesc;

    public static String SetActivityNameResolution_Name_label;

    public static String SetActivityNameResolution_SetActivityName_menu;

    public static String InterfaceDestinationResolution_EnableDestinationEnv_DialogTitle;

    public static String InterfaceDestinationResolution_EnableDestinationEnv_DialogText;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
