package com.tibco.xpd.validations.bpmn.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author glewis
 * 
 */

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.validations.bpmn.internal.messages"; //$NON-NLS-1$

    public static String AbstractCatchSignalEventMappingRule_MapFromSignal_label;

    public static String AbstractSelectActivityReferenceResolution_SelectActivity_title;

    public static String AbstractSubProcessCatchErrorJSMappingRule_MapFromErrorPrefix_label;

    public static String AbstractSuppressValidationIssueResolution_SetIssueOverride_menu;

    public static String AssociateActivityLaneResolution_1;

    public static String AssociateActivityLaneResolution_DefaultLane_label;

    public static String AssocPageflowDataForUserTaskInterfaceResolution_AddDataToPageflowStartInteface_menu;

    public static String AssocUserTaskDataForPageflowParamResolution_AddDataToUserTaskInteface_menu;

    public static String BrokenApiProcessParticRefResolution_FixBrokenRef_menu;

    public static String BuildProjectResolution_CleanAndBuildProjectResolution_message;

    public static String DefaultPool_label;

    public static String ChangeDataTypeToString_ChangeDataTypeCommand;

    public static String ChangeDataTypeToString_ChangeDataTypeToNonArrayCommand;

    public static String ConvertPageflowSubProcessTaskToUserTaskResolution_ConvertToPageflowUserTask_menu;

    public static String CopyInterfaceToOtherSameFaultsResolution_SynchThrowFaultPArameters_menu;

    public static String CorrelationDataToDataField_ConvertCommand;

    public static String DataFieldToCorrelationData_ConvertCommand;

    public static String DontRescheduleTimersResolution_UnsetRescheduleTimers_menu;

    public static String FixNamedElementName_FixName;

    public static String ProcessImplementedMethodsRule_EventChanged;

    public static String ProcessImplementedMissingMethodsRule_EndErrorEvent;

    public static String ProcessImplementedMissingMethodsRule_EndMessageEvent;

    public static String ProcessImplementedMissingMethodsRule_IntermediateEvent;

    public static String ProcessImplementedMissingMethodsRule_IntermediateEventUnimplemented_shortdesc;

    public static String ProcessImplementedMissingMethodsRule_StartEvent;

    public static String RemoveActivityScriptsResolution_RemiveActScripts_menu;

    public static String RemoveCorrelationAssociations_RemoveCommand;

    public static String RemoveDeletedTasksFromGroupResolution_RemoveDeletedTasksFromTaskGroup_menu;

    public static String RemoveDestinationFromPackage_RemopveProcessDest_menu;

    public static String RemoveMessageFlowResolution_RemoveMsgFlow_menu;

    public static String RemovePageflowParameterResolution_RemovePageflowParam_menu;

    public static String RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_longdesc;

    public static String RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_menu;

    public static String RemovePageflowUserTaskAssocParam_RemoveDataFromUserTaskIfc_title;

    public static String RemoveUserTaskScriptsResolution_RemoveUserTaskScripts_menu;

    public static String CatchErrorEventRules_Unnamed_label;

    public static String CreatePageflowParamFromUSerTaskDataResolution_CreatePageflowParam_menu;

    public static String CreateUserTaskDataForPageflowParamResolution_CreateDataToMatchPageflowParam_menu;

    public static String ExternalPackageIfcImplRule_AssocParamDontMatch_shortdesc;

    public static String ExternalPackageIfcImplRule_DisplayNamesDontMatch_shortdesc;

    public static String ExternalPackageIfcImplRule_ErrorCodesMismatch_label;

    public static String ExternalPackageIfcImplRule_NamesDontMatch_shortdesc;

    public static String InitialValueMappingResolution_MapToAllowedValueCommand;

    public static String InitialValueMappingResolution_ValidAllowedValueNotFoundError;

    public static String MatchPageflowParamToUserTaskDataResolution_MatchPageflowParamToUserTaskData_menu;

    public static String MatchPageflowParamToUserTaskDataResolution_PageflowPackageMustBeSavedBeforeFix_longdesc;

    public static String MatchPageflowParamToUserTaskDataResolution_Save_title;

    public static String MatchUserTaskDataToPageflowParamResolution_MatchUSerTaskDataToPageflowParam_menu;

    public static String SelectRequestActivityForThrowError_SelectRequestActivity_title;

    public static String SetAssociatedParamModeToIn_SetAssocParamMode_menu;

    public static String SetDefaultTaskIconResolution_DefIconMissing_message;

    public static String SetDefaultTaskIconResolution_DefIconMissing_title;

    public static String SetDefaultTaskIconResolution_ResetTaskIconToDefault_menu;

    public static String SetErrorCodeResolution_EnterErrorCode_title;

    public static String SetErrorCodeResolution_ErrorCode_label;

    public static String SetErrorCodeResolution_ErrorCodeMustHaveAValue_longdesc;

    public static String SetErrorCodeResolution_ErrorCodeMustNotHaveLeadTrailSpace_longdesc;

    public static String SetLoopMultiInstanceSequentialResolution_SetMultiInstSequential_menu;

    public static String SetLoopStandardResolution_SetTaskLoopStandard_menu;

    public static String SetThrowFaultNameResolution_FaultName_label;

    public static String SetThrowFaultNameResolution_MustHaveFaultName_longdesc;

    public static String SetThrowFaultNameResolution_SetFaultName_label;

    public static String ThrowErrorEventRules_UnnamedEvent_label;

    public static String Annotation_OR_NamedElement_TODO;

    public static String Annotaion_OR_NamedElement_FIXME;

    public static String SetParamModeToIn_SetParamMode_menu;

    public static String SetCallSubProcessStartStrategyResolution_Command_label;

    public static String SetCallSubProcessInvocationModeResolution_Command_label;

    public static String SetCallSubProcessSuspendResumeResolution_Command_label;

    public static String UnsetCallSubProcessSuspendResumeResolution_Command_label;

    public static String CaseReferenceToBOMTypeResolution_Command_label;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
