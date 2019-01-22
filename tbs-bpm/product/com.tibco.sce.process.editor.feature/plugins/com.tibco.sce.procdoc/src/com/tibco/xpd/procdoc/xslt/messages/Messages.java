/**
 * Messages.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.procdoc.xslt.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages
 * 
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.procdoc.xslt.messages.messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }

    public static String Name_label;

    public static String Type_label;

    public static String Value_label;

    public static String DataTypeLength_label;

    public static String DataTypeString_label;

    public static String DataTypeInteger_label;

    public static String DataTypeFloat_label;

    public static String DataTypeBoolean_label;

    public static String DataTypePerformer_label;

    public static String DataTypeDateTime_label;

    public static String ParameterDirection_label;

    public static String PublicationUNDERTEST_label;

    public static String PublicationUNDERREV_label;

    public static String PublicationRELEASED_label;

    public static String ActivityTypeSTARTEVENT_label;

    public static String ActivityTypeSTARTEVENTMessage_label;

    public static String ActivityTypeSTARTEVENTTimer_label;

    public static String ActivityTypeSTARTEVENTConditional_label;

    public static String ActivityTypeSTARTEVENTMultiple_label;

    public static String ActivityTypeSTARTEVENTSignal_label;

    public static String ActivityTypeINTEREVENT_label;

    public static String ActivityTypeINTEREVENTMessage_label;

    public static String ActivityTypeINTEREVENTTimer_label;

    public static String ActivityTypeINTEREVENTConditional_label;

    public static String ActivityTypeINTEREVENTLink_label;

    public static String ActivityTypeINTEREVENTMultiple_label;

    public static String ActivityTypeINTEREVENTCatchError_label;

    public static String ActivityTypeINTEREVENTThrowError_label;

    public static String ActivityTypeINTEREVENTCatchCompensation_label;

    public static String ActivityTypeINTEREVENTThrowCompensation_label;

    public static String ActivityTypeINTEREVENTThrowSignal_label;

    public static String ActivityTypeINTEREVENTCatchCancel_label;

    public static String ActivityTypeINTEREVENTCatchMessage_label;

    public static String ActivityTypeINTEREVENTCatchConditional_label;

    public static String ActivityTypeINTEREVENTCatchLink_label;

    public static String ActivityTypeINTEREVENTCatchSignal_label;

    public static String ActivityTypeINTEREVENTCatchMultiple_label;

    public static String ActivityTypeINTEREVENTCatchTimer_label;

    public static String ActivityTypeINTEREVENTCatch_label;

    public static String ActivityTypeENDEVENT_label;

    public static String ActivityTypeENDEVENTMessage_label;

    public static String ActivityTypeENDEVENTSignal_label;

    public static String ActivityTypeENDEVENTMultiple_label;

    public static String ActivityTypeENDEVENTError_label;

    public static String ActivityTypeENDEVENTCompensation_label;

    public static String ActivityTypeENDEVENTCancel_label;

    public static String ActivityTypeENDEVENTTerminate_label;

    public static String ActivityTypeGATEWAY_label;

    public static String ActivityTypeXORDATAGATEWAY_label;

    public static String ActivityTypeXOREVENTGATEWAY_label;

    public static String ActivityTypePARALLELGATEWAY_label;

    public static String ActivityTypeORGATEWAY_label;

    public static String ActivityTypeCOMPLEXGATEWAY_label;

    public static String ActivityTypeTASK_label;

    public static String ActivityTypeSERVICETASK_label;

    public static String ActivityTypeMANUALTASK_label;

    public static String ActivityTypeUSERTASK_label;

    public static String ActivityTypeSCRIPTTASK_label;

    public static String ActivityTypeSENDTASK_label;

    public static String ActivityTypeRECEIVETASK_label;

    public static String ActivityTypeREFERENCETASK_label;

    public static String ActivityTypeINDEPSUBFLOW_label;

    public static String ActivityTypeEMBEDSUBFLOW_label;

    public static String ActivityTypeEVENTSUBFLOW_label;

    public static String EventSubProcContents_label;

    public static String ActivityMarkerMULTILOOP_label;

    public static String ActivityMarkerSTDLOOP_label;

    public static String ActivityMarkerCOMPENSATION_label;

    public static String ActivityMarkerADHOC_label;

    public static String EventFlowTypeSTART_label;

    public static String EventFlowTypeEND_label;

    public static String TransTypeTIMER_label;

    public static String TransTypeUNKNOWN_label;

    public static String TransTypeCONDITIONAL_label;

    public static String TransTypeDEFAULT_label;

    public static String TransTypeUNCONTROLLED_label;

    public static String SeqFlowsTitle_label;

    public static String SeqFlowsNone_label;

    public static String StartEventMessage_label;

    public static String StartEventTimer_label;

    public static String StartEventConditional_label;

    public static String StartEventSignal_label;

    public static String StartEventAnySignal_label;

    public static String StartEventMultiple_label;

    public static String StartEventNone_label;

    public static String InterEventMessage_label;

    public static String InterEventMessageThrow_label_2;

    public static String InterEventTimer_label;

    public static String InterEventConditional_label;

    public static String InterEventLink_label;

    public static String InterEventMultiple_label;

    public static String InterEventMultipleThrow_label_2;

    public static String InterEventProcessMultipleThrow_label;

    public static String InterEventThrowCompensation_label_2;

    public static String InterEventProcessThrowCompensation_label;

    public static String InterEventCatchCompensation_label;

    public static String InterEventThrowSignal_label_2;

    public static String InterEventCatchSignal_label;

    public static String InterEventCatchError_label;

    public static String InterEventBadCancel_label;

    public static String InterEventNone_label;

    public static String InterEventMessageCaught_label;

    public static String InterEventTimerCaught_label;

    public static String InterEventSignalCaught_label;

    public static String InterEventConditionalCaught_label;

    public static String InterEventAttachThrowNotLegal_label;

    public static String InterEventMultipleCaught_label;

    public static String InterEventCompensationCaught_label;

    public static String InterEventErrorCaught_label;

    public static String InterEventCancelCaught_label;

    public static String InterEventNoneCaught_label;

    public static String InterEventCancelOnEvent_label_2;

    public static String InterEventNonCancelling_label_2;

    public static String UpdateWorkItem_label;

    public static String TaskCompletes_label;

    public static String EndOfFlow_label;

    public static String EndOfFlowMessage_label;

    public static String EndOfFlowError_label;

    public static String EndOfFlowCancel_label;

    public static String EndOfFlowCompensation_label;

    public static String EndOfFlowLink_label;

    public static String EndOfFlowTerminate_label;

    public static String EndOfFlowMultiple_label;

    public static String LinksTo_label;

    public static String LinksFrom_label;

    public static String WaitForMessage_label;

    public static String WaitUntilTimeout_label;

    public static String WaitForMultiple_label;

    public static String WaitForConditional_label;

    public static String ThrowSignal_label;

    public static String ThrowSignalNoSignalName_label;

    public static String ThrowCompensation_label;

    public static String WaitFor_label;

    public static String WaitForSignal_label;

    public static String WaitForAnySignal_label;

    public static String BadlyPlacedCancel_label;

    public static String InterEventLinkCaught_label;

    public static String BadlyPlacedError_label;

    public static String SendMessage_label;

    public static String ProcessServiceCall_label;

    public static String ProcessManual_label;

    public static String ProcessUser_label;

    public static String ProcessScript_label;

    public static String ProcessSend_label;

    public static String ProcessReceive_label;

    public static String ProcessTask_label;

    public static String ProcessRefTask_label;

    public static String ProcessEmbSubProc_label;

    public static String ProcessSubProc_label;

    public static String ProcessSubProcInterface_label;

    public static String ProcessSubProcInterfaceAndField_label;

    public static String ProcessUnspecifiedSubProc_label;

    public static String SubProcInExtPkg_label;

    public static String UnspecifiedErrorCode_label;

    public static String Unspecified_label;

    public static String ConditionIf_label;

    public static String ConditionElseIf_label;

    public static String ConditionUndefined_label;

    public static String ConditionXORElse_label;

    public static String ConditionORElse_label;

    public static String EventGatewayLeader_label;

    public static String ComplexGatewayFormat_label;

    public static String ComplexGatewayComplete_label;

    public static String CaughtEventsTitle_label;

    public static String EmbeddedContents_label;

    public static String ExtendedAttributes_label;

    public static String DestEnvs_label;

    public static String ImageMapGoto_label;

    public static String ImageMapGotoSeqFlow_label;

    public static String Created_label;

    public static String Modified_label;

    public static String Package_label;

    public static String Description_label;

    public static String InterfaceType_label;

    public static String ServiceProcessInterface_label;

    public static String ProcessRuntimeDeploy_label;

    public static String PageflowRuntimeDeploy_label;

    public static String QuickLinks_label;

    public static String Participants_label;

    public static String Annotations_label;

    public static String DataObjs_label;

    public static String DataObject_label;

    public static String TypeDeclarations_label;

    public static String DataFieldsTitle_label;

    public static String Processes_label;

    public static String FormalParameters_label;

    public static String Input_label;

    public static String Output_label;

    public static String InputOutput_label;

    public static String ThrownBy_label;

    public static String CaughtBy_label;

    public static String CatchesError_label;

    public static String ThrowsError_label;

    public static String AllErrors_label;

    public static String ErrorUnspecified_label;

    public static String SignalThrownBy_label;

    public static String SignalCaughtBy_label;

    public static String CatchesSignal_label;

    public static String SignalType_label;

    public static String SignalTypeLocal_label;

    public static String SignalTypeCaseData_label;

    public static String SignalTypeGlobal_label;

    public static String ThrowsSignal_label;

    public static String AllSignals_label;

    public static String SignalUnspecified_label;

    public static String PoolLaneTitle_label;

    public static String PoolLaneEmpty_label;

    public static String DataTypeArray_label;

    public static String PoolLaneContent_label;

    public static String TaskInterface_label;

    public static String EventInterface_label;

    public static String AssociatedData_label;

    public static String Visibility_label;

    public static String Public_label;

    public static String Private_label;

    public static String Mandatory_label;

    public static String AllApplicableData_label;

    public static String AllApplicableProcessParams_label;

    public static String AllApplicableInterfaceParams_label;

    public static String NoProcessData_label;

    public static String NoProcessParams_label;

    public static String NoInterfaceParams_label;

    public static String ImplementsInterface_label;

    public static String ImplementsServiceInterface_label;

    public static String ImplementsEvent_label;

    public static String InheritedFrom_label;

    public static String MethodTypeSTARTEVENTMessage_label;

    public static String MethodTypeSTARTEVENT_label;

    public static String MethodTypeINTEREVENTMessage_label;

    public static String MethodTypeINTEREVENT_label;

    public static String Documentation_label;

    public static String ProcessInterfaces_label;

    public static String ProcessInterface_label;

    public static String InterfaceEvents_label;

    public static String StartEvents_label;

    public static String IntermediateEvents_label;

    public static String GotoProcessInterface_label;

    public static String MessageTo_label;

    public static String MessageFrom_label;

    public static String MessageFault_label;

    public static String SubProcess_label;

    public static String Invoke_SubProcess_label;

    public static String Invoke_SubProcessInterface_label;

    public static String Invocation_Type_Label;

    public static String AsynchronousDetached_Label;

    public static String AsynchronousAttached_Label;

    public static String Synchronous_Label;

    public static String SubProcessInterface_label;

    public static String RuntimeIdentifier_label;

    public static String ParameterMappings_label;

    public static String CallingProcessData_label;

    public static String InterfaceParameter_label;

    public static String SubProcessParameter_label;

    public static String UnmappedScripts_label;

    public static String XpdlVersion_label;

    public static String Vendor_label;

    public static String PublicationStatus_label;

    public static String Author_label;

    public static String BusinessVersion_label;

    public static String ContentSummary_label;

    public static String Process_label;

    public static String ParticipantTypeRole_label;

    public static String ParticipantTypeExtRef_label;

    public static String ParticipantTypeHuman_label;

    public static String ParticipantTypeOrganisationalUnit_label;

    public static String ParticipantTypeSystem_label;

    public static String GotoDiagram_label;

    public static String TasksAndEvents_label;

    public static String TaskServiceType_label;

    public static String WebService_label;

    public static String BWService_label;

    public static String WebServiceOperation_label;

    public static String WebServiceOperationName_label;

    public static String WebServicePortName_label;

    public static String WebServicePortTypeName_label;

    public static String WebServiceServiceName_label;

    public static String WebServiceLocation_label;

    public static String TaskScripts_label;

    public static String ScriptTitle_label;

    public static String TaskScriptExecutionTypeTitle_label;

    public static String TaskScriptExecutionTypeOpen_label;

    public static String TaskScriptExecutionTypeClose_label;

    public static String TaskScriptExecutionTypeSubmit_label;

    public static String MessageEventInformation_label;

    public static String Transaction_label;

    public static String DataObjectState_label;

    public static String DataObjectReqdForStart_label;

    public static String Yes_label;

    public static String No_label;

    public static String DataObjectProducedAtCompletion_label;

    public static String DataObjectExternalReference_label;

    public static String DataObjectProperties_label;

    public static String DataObjectProperty_label;

    public static String DataObjectAssociations_label;

    public static String DataObjectAssociationObject_label;

    public static String DataObjectAssociationDirection_label;

    public static String EventTimerType_label;

    public static String EventTimerTypeDateTime_label;

    public static String EventTimerTypeCycle_label;

    public static String EventTimerTypeContinue_label;

    public static String EventTimerTypeWithdraw_label;

    public static String EventTimerPeriod_label;

    public static String EventTimerPeriodYears_label;

    public static String EventTimerPeriodMonths_label;

    public static String EventTimerPeriodWeeks_label;

    public static String EventTimerPeriodDays_label;

    public static String EventTimerPeriodHours_label;

    public static String EventTimerPeriodMinutes_label;

    public static String EventTimerPeriodSeconds_label;

    public static String EventTimerPeriodMicros_label;

    public static String EventConditionalName_label;

    public static String SeqFlowsTooComplex_label;

    public static String SeqFlowsResultIn_label;

    public static String SeqFlowsMayResultIn_label;

    public static String DataTypeDate_label;

    public static String DataTypeTime_label;

    public static String DeploymentTargetsLabel_label;

    public static String RestService_label;

    public static String Service_label;

    public static String Resource_label;

    public static String Method_label;

}
