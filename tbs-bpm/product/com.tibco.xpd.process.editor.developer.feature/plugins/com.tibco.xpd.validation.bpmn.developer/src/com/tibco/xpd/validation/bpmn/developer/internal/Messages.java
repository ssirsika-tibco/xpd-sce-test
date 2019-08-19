package com.tibco.xpd.validation.bpmn.developer.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.validation.bpmn.developer.internal.messages"; //$NON-NLS-1$

    public static String SetToInvokeMsgStartEventResolution_menu;

    public static String AbstractProcessDataToWebSvcMappingRule_MappingRule_OutputFaultFromProcess_label;

    public static String AbstractWebSvcToProcessDataRule_MapFromError_label;

    public static String BindingOperationStyleRule_WSDLBindingLocation_label;

    public static String BindingOperationStyleRule_WSDLPortLocation_label;

    public static String CaseDocumentServiceTaskRule_DelDocOprStringForErrorMsg;

    public static String CaseDocumentServiceTaskRule_FindDocOprStringForErrorMsg;

    public static String CaseDocumentServiceTaskRule_LinkDocOprStringForErrorMsg;

    public static String CaseDocumentServiceTaskRule_LinkSysDocOprStringForErrorMsg;

    public static String CaseDocumentServiceTaskRule_MoveDocOprStringForErrorMsg;

    public static String CaseDocumentServiceTaskRule_UnlinkDocOprStringForErrorMsg;

    public static String ChangeAssocParamsToInModeResolution_ChangePAramsToIn_menu;

    public static String ConvertToPlugInProjectResolution_unableToConvertToPluginProject_error_message;

    public static String CreateReplyActivityResolution_CreateRewplyActivity_menu;

    public static String FixMissingXpdExtImplementationTypeResolution_FixImplType_menu;

    public static String MappingRule_InputToProcess_label;

    public static String MappingRule_InputToService_label;

    public static String MappingRule_OuputFromService_label;

    public static String MappingRule_OutputFromProcess_label;

    public static String MappingRule_OutputProcessId;

    public static String MessageValidationRule_messages_error_location_shortdesc;

    public static String ReconfigureBusinessProcessResolution_ReconfigBizProc_menu;

    public static String SetCorrelationNotRequired_CommandLabel;

    public static String SetReferencedProjectResolution_errorInReadingProjectDesc_error_message;

    public static String EmailRule_Bcc;

    public static String EmailRule_Body;

    public static String EmailRule_CC;

    public static String EmailRule_FieldContents;

    public static String EmailRule_From;

    public static String EmailRule_Headers;

    public static String EmailRule_Priority;

    public static String EmailRule_ReplyTo;

    public static String EmailRule_Subject;

    public static String EmailRule_To;

    public static String EventHandlerCorrelationDataRemoveInitialiserEntriesResolution_RemovalOfHangingActivityReferences_menu;

    public static String ConvertBWToWebServiceCmd_label;

    public static String ConvertDeleteByCaseIdToReferenceResolution_commandLabel;

    public static String DeleteInvalidInitializerActivities_RemoveInvalidInitializerActivitiesResolution_label;

    public static String InitializeEventHandlerCorrelationDataResolution_InitializeListWithStartActivities_menu;

    public static String DuplicateUnboundOperationRule_messages_error_location_shortdesc;

    public static String GenerateRESTServiceParticipantResolution_command_label;

    public static String ConvertToRESTServiceParticipantResolution_command_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
