/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.xpdl2.edit.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author wzurek
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.xpdl2.edit.internal.messages"; //$NON-NLS-1$

    public static String DataReferenceContext_CalendarRef_label;

    public static String DataReferenceContext_CancelScript_label;

    public static String DataReferenceContext_CloseScript_label;

    public static String DataReferenceContext_CompleteScript_label;

    public static String DataReferenceContext_ConditionalFlowScript_label;

    public static String DataReferenceContext_ExplicitAssociation_label;

    public static String DataReferenceContext_InitiateScript_label;

    public static String DataReferenceContext_IntefaceEvent_label;

    public static String DataReferenceContext_LoopScripts_label;

    public static String DataReferenceContext_MappingIn_label;

    public static String DataReferenceContext_MappingOut_label;

    public static String DataReferenceContext_OpenScript_label;

    public static String DataReferenceContext_Performer_label;

    public static String DataReferenceContext_RescheduleScript_label;

    public static String DataReferenceContext_RescheduleTimerEventScript_label;

    public static String DataReferenceContext_RuntimeIdField_label;

    public static String DataReferenceContext_ScheduleScript_label;

    public static String DataReferenceContext_ScriptTaskScript_label;

    public static String DataReferenceContext_SimulationData_label;

    public static String DataReferenceContext_SubmitScript_label;

    public static String DataReferenceContext_ActivityImplementation_label;

    public static String DataReferenceContext_TimeoutScript_label;

    public static String DataReferenceContext_AdhocPreconditionScript_label;

    public static String DataReferenceContext_TimerScript_label;

    public static String DataReferenceContext_Unkown_label;

    public static String DecisionFlowUtil_NoOrMoreThanOneStartEvent;

    public static String DecisionFlowUtil_NoOutGoingActivitiesForActivityId;

    public static String RenameFieldOrParamCommand_SetFieldName_menu;

    public static String RenameFieldOrParamCommand_SetParamName_menu;

    public static String RenameParticipantCommand_SetParticipantName_menu;

    public static String ReplyActivityUtil_ThrowEvent_label;

    public static String ReplyActivityUtil_EndEvent_label;

    public static String ReplyActivityUtil_ReplyTo_label;

    public static String ReplyActivityUtil_SendTask_label;

    public static String ReplyActivityUtil_SetReplyToIncomingRequestCommand_title;

    public static String ReplyActivityUtil_SetSendOneWayMessage_menu;

    public static String ReplyActivityUtil_UnnamedEvent_label;

    public static String ReplyActivityUtil_UnnamedTask_label;

    public static String ThrowErrorEventUtil_SetThrowFaultMessage_menu;

    public static String ThrowErrorEventUtil_SetThrowProcessError_menu;

    public static String Xpdl2FieldOrParamResolver_InputToSubProcess_label;

    public static String Xpdl2FieldOrParamResolver_OutputFromError_label;

    public static String Xpdl2FieldOrParamResolver_OutputFromSubProcess_label;

    public static String Xpdl2ModelUtil_error2_label;

    public static String Xpdl2ModelUtil_error3_label;

    public static String Xpdl2ModelUtil_error4_label;

    public static String Xpdl2ModelUtil_GeneratedServiceFolder_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
