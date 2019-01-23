/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.processinterface.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author rsomayaj
 * 
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.analyst.processinterface.internal.messages"; //$NON-NLS-1$

    public static String ParamDeleteWrapperCmd_DeleteParam_menu;

    public static String ProcessInterfaceEObjectSection_CmdAddAssociatedParam_label;

    public static String ProcessInterfaceEObjectSection_CmdRmvIntermediateMethod_label;

    public static String ProcessInterfaceEObjectSection_CmdRmvParameterMethod_label;

    public static String ProcessInterfaceEObjectSection_CmdRmvStartMethod_label;

    public static String ProcessInterfaceEObjectSection_CmdSetProcessDesc_label;

    public static String ProcessInterfaceEObjectSection_Desc_label;

    public static String ProcessInterfaceEObjectSection_ErrorsFolder_label;

    public static String ProcessInterfaceEObjectSection_FormalParamHeader_label;

    public static String ProcessInterfaceEObjectSection_GeneralSection_longdesc;

    public static String ProcessInterfaceEObjectSection_GeneralSectionHeader_label;

    public static String ProcessInterfaceEObjectSection_InterfaceHeader_label;

    public static String ProcessInterfaceEObjectSection_IntermediateEventsHeader_label;

    public static String ProcessInterfaceEObjectSection_IntermediateMethodsDesc_longdesc;

    public static String ProcessInterfaceEObjectSection_Name_label;

    public static String ProcessInterfaceEObjectSection_NewBtn_label;

    public static String ProcessInterfaceEObjectSection_ParamDesc_longdesc;

    public static String ProcessInterfaceEObjectSection_RemoveBtn_label;

    public static String ProcessInterfaceEObjectSection_StartEventHeader_label;

    public static String ProcessInterfaceEObjectSection_StartEventsDesc_longdesc;

    public static String ProcessInterfaceEObjectSection_DeploymentTarget_label;

    public static String ProcessInterfaceEObjectSection_DeploymentTarget_desc_text;

    public static String ProcessInterfaceFormEditor_EditorDesc_shortdesc;

    public static String ProcessInterfaceFormEditor_ErrIncorrectEditorinput_shortdesc;

    public static String ProcessInterfaceFormEditor_PrefDontAsk_shortdesc;

    public static String ProcessInterfaceFormEditor_PrefSaveAllElements_longdesc;

    public static String ProcessInterfaceFormEditor_ReadOnly_label;

    public static String ProcessInterfaceFormEditor_SavePackage_label;

    public static String ProcIntfcToServiceProcIntfcAction_ConvertToServiceProcessInterface_menu;

    public static String ServiceProcIntfcToProcIntfcAction_ConvertToProcessInterface_menu;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
