/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget;

import org.eclipse.swt.graphics.RGB;

/**
 * Set of constants used by ProcessWidgetImpl
 * 
 * @author wzurek
 */
public final class ProcessWidgetConstants {

    /** type of factory for ProcessDiagram adapters */
    public static final String ADAPTER_TYPE = "ProcessDiagramAdapter"; //$NON-NLS-1$

    public static final String IMG_TOOL_ASSOCIATION_16 =
            "icons/palette/association_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_ASSOCIATION_24 =
            "icons/palette/association_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWDRAWER_16 =
            "icons/palette/flow_drawer_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOW_16 = "icons/palette/flow_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOW_24 = "icons/palette/flow_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWCONDITIONAL_16 =
            "icons/palette/flow_conditional_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWCONDITIONAL_24 =
            "icons/palette/flow_conditional_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWDEFAULT_16 =
            "icons/palette/flow_default_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWDEFAULT_24 =
            "icons/palette/flow_default_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWEXCEPTION_16 =
            "icons/palette/flow_exception_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FLOWEXCEPTION_24 =
            "icons/palette/flow_exception_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_MESSAGE_16 =
            "icons/palette/message_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_MESSAGE_24 =
            "icons/palette/message_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_16 =
            "icons/palette/event_start_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_24 =
            "icons/palette/event_start_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_MESSAGE_16 =
            "icons/palette/event_start_message_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_MESSAGE_24 =
            "icons/palette/event_start_message_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_MULTIPLE_16 =
            "icons/palette/event_start_multiple_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_MULTIPLE_24 =
            "icons/palette/event_start_multiple_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_CONDITIONAL_16 =
            "icons/palette/event_start_conditional_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_CONDITIONAL_24 =
            "icons/palette/event_start_conditional_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_SIGNAL_16 =
            "icons/palette/event_start_signal_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_SIGNAL_24 =
            "icons/palette/event_start_signal_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_TIMER_16 =
            "icons/palette/event_start_timer_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_START_TIMER_24 =
            "icons/palette/event_start_timer_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_16 =
            "icons/palette/event_intermediate_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_24 =
            "icons/palette/event_intermediate_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_16 =
            "icons/palette/event_intermediate_link_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_24 =
            "icons/palette/event_intermediate_link_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_16 =
            "icons/palette/event_intermediate_link_throw_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_24 =
            "icons/palette/event_intermediate_link_throw_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_16 =
            "icons/palette/event_intermediate_cancel_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_24 =
            "icons/palette/event_intermediate_cancel_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_16 =
            "icons/palette/event_intermediate_compensation_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_24 =
            "icons/palette/event_intermediate_compensation_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_16 =
            "icons/palette/event_intermediate_compensation_throw_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_24 =
            "icons/palette/event_intermediate_compensation_throw_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_16 =
            "icons/palette/event_intermediate_error_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_24 =
            "icons/palette/event_intermediate_error_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_16 =
            "icons/palette/event_intermediate_message_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_24 =
            "icons/palette/event_intermediate_message_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_16 =
            "icons/palette/event_intermediate_message_throw_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_24 =
            "icons/palette/event_intermediate_message_throw_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_16 =
            "icons/palette/event_intermediate_multiple_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_24 =
            "icons/palette/event_intermediate_multiple_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_16 =
            "icons/palette/event_intermediate_multiple_throw_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_24 =
            "icons/palette/event_intermediate_multiple_throw_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_16 =
            "icons/palette/event_intermediate_conditional_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_24 =
            "icons/palette/event_intermediate_conditional_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_TIMER_16 =
            "icons/palette/event_intermediate_timer_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_TIMER_24 =
            "icons/palette/event_intermediate_timer_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_16 =
            "icons/palette/event_intermediate_signal_catch_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_24 =
            "icons/palette/event_intermediate_signal_catch_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_16 =
            "icons/palette/event_intermediate_signal_throw_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_24 =
            "icons/palette/event_intermediate_signal_throw_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_16 =
            "icons/palette/event_end_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_24 =
            "icons/palette/event_end_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_CANCEL_16 =
            "icons/palette/event_end_cancel_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_CANCEL_24 =
            "icons/palette/event_end_cancel_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_COMPENSATION_16 =
            "icons/palette/event_end_compensation_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_COMPENSATION_24 =
            "icons/palette/event_end_compensation_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_ERROR_16 =
            "icons/palette/event_end_error_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_ERROR_24 =
            "icons/palette/event_end_error_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_MESSAGE_16 =
            "icons/palette/event_end_message_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_MESSAGE_24 =
            "icons/palette/event_end_message_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_MULTIPLE_16 =
            "icons/palette/event_end_multi_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_MULTIPLE_24 =
            "icons/palette/event_end_multi_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_TERMINATE_16 =
            "icons/palette/event_end_terminate_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_TERMINATE_24 =
            "icons/palette/event_end_terminate_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_SIGNAL_16 =
            "icons/palette/event_end_signal_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_EVENT_END_SIGNAL_24 =
            "icons/palette/event_end_signal_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_GENERIC_GATEWAY_16 =
            "icons/palette/gateway_generic_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_GENERIC_GATEWAY_24 =
            "icons/palette/gateway_generic_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_PARALLEL_GATEWAY_16 =
            "icons/palette/gateway_parallel_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_PARALLEL_GATEWAY_24 =
            "icons/palette/gateway_parallel_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_XORDATA_GATEWAY_16 =
            "icons/palette/gateway_XORData_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_XORDATA_GATEWAY_24 =
            "icons/palette/gateway_XORData_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_XOREVENT_GATEWAY_16 =
            "icons/palette/gateway_XOREvent_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_XOREVENT_GATEWAY_24 =
            "icons/palette/gateway_XOREvent_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_OR_GATEWAY_16 =
            "icons/palette/gateway_OR_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_OR_GATEWAY_24 =
            "icons/palette/gateway_OR_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_COMPLEX_GATEWAY_16 =
            "icons/palette/gateway_Complex_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_COMPLEX_GATEWAY_24 =
            "icons/palette/gateway_Complex_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_TASKDRAWER_16 =
            "icons/palette/task_drawer_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_TASK_16 = "icons/palette/task_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_TASK_24 = "icons/palette/task_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_USERTASK_16 =
            "icons/palette/task_user_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_USERTASK_24 =
            "icons/palette/task_user_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_MANUALTASK_16 =
            "icons/palette/task_manual_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_MANUALTASK_24 =
            "icons/palette/task_manual_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SENDTASK_16 =
            "icons/palette/task_send_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SENDTASK_24 =
            "icons/palette/task_send_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_RECEIVETASK_16 =
            "icons/palette/task_receive_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_RECEIVETASK_24 =
            "icons/palette/task_receive_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SERVICETASK_16 =
            "icons/palette/task_service_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SERVICETASK_24 =
            "icons/palette/task_service_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_DTABLETASK_16 =
            "icons/palette/taskDecisionTable_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_DTABLETASK_24 =
            "icons/palette/taskDecisionTable_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SCRIPTTASK_16 =
            "icons/palette/task_script_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SCRIPTTASK_24 =
            "icons/palette/task_script_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_REFERENCETASK_16 =
            "icons/palette/task_reference_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_REFERENCETASK_OVERLAY =
            "icons/diagram/task_reference_overlay.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_REFERENCETASK_24 =
            "icons/palette/task_reference_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_16 =
            "icons/palette/subproc_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_24 =
            "icons/palette/subproc_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_EMBEDDED_16 =
            "icons/palette/subproc_embedded_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_EVENT_16 =
            "icons/palette/eventSubProcess_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_EVENT_24 =
            "icons/palette/eventSubProcess_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SUBPROC_EMBEDDED_24 =
            "icons/palette/subproc_embedded_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_LANE_16 = "icons/palette/lane_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_LANE_24 = "icons/palette/lane_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_TASKSET_16 =
            "icons/palette/taskset_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_TASKSET_24 =
            "icons/palette/taskset_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_NOTE_16 = "icons/palette/note_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_NOTE_24 = "icons/palette/note_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_POOL_24 = "icons/palette/pool_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_POOL_16 = "icons/palette/pool_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_GROUP_16 = "icons/palette/group_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_GROUP_24 = "icons/palette/group_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_DATA_OBJECT_16 =
            "icons/palette/data_object_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_DATA_OBJECT_24 =
            "icons/palette/data_object_24.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_SPYGLASS_24 =
            "icons/palette/spyglass_24.gif"; //$NON-NLS-1$

    public static final String IMG_TOOL_SPYGLASS_16 =
            "icons/palette/spyglass_16.gif"; //$NON-NLS-1$

    public static final String IMG_TOOL_FAVOURITES_16 =
            "icons/palette/favourites_16.png"; //$NON-NLS-1$

    public static final String IMG_TOOL_FAVOURITES_24 =
            "icons/palette/favourites_24.png"; //$NON-NLS-1$

    public static final String IMG_USERTASK_ICON = "icons/diagram/taskUser.png"; //$NON-NLS-1$

    public static final String IMG_PAGEFLOW_ICON =
            "icons/diagram/taskPageflow.png"; //$NON-NLS-1$

    public static final String IMG_SERVICETASK_ICON =
            "icons/diagram/taskService.png"; //$NON-NLS-1$

    public static final String IMG_DTABLETASK_ICON =
            "icons/diagram/taskDecisionTable.png"; //$NON-NLS-1$

    public static final String IMG_SCRIPTTASK_ICON =
            "icons/diagram/taskScript.png"; //$NON-NLS-1$

    public static final String IMG_SENDTASK_ICON = "icons/diagram/taskSend.png"; //$NON-NLS-1$

    public static final String IMG_RECEIVETASK_ICON =
            "icons/diagram/taskReceive.png"; //$NON-NLS-1$

    public static final String IMG_MANUALTASK_ICON =
            "icons/diagram/taskManual.png"; //$NON-NLS-1$

    public static final String IMG_REFERENCETASK_ICON =
            "icons/diagram/taskReference.png"; //$NON-NLS-1$

    public static final String IMG_SUBPROCTASK_ICON =
            "icons/diagram/taskSubProcess.png"; //$NON-NLS-1$

    public static final String IMG_PARALLEL_GATEWAY_ICON =
            "icons/diagram/gatewayParallel.png"; //$NON-NLS-1$

    public static final String IMG_XORDATA_GATEWAY_ICON =
            "icons/diagram/gatewayXORData.png"; //$NON-NLS-1$

    public static final String IMG_XOREVENT_GATEWAY_ICON =
            "icons/diagram/gatewayExclusiveEvent.png"; //$NON-NLS-1$

    public static final String IMG_COMPLEX_GATEWAY_ICON =
            "icons/diagram/gatewayComplex.png"; //$NON-NLS-1$

    public static final String IMG_OR_GATEWAY_ICON =
            "icons/diagram/gatewayOR.png"; //$NON-NLS-1$

    public static final String IMG_CANCEL_EVENT_THROW_ICON =
            "icons/diagram/eventCancelThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_CANCEL_EVENT_CATCH_ICON =
            "icons/diagram/eventCancelCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_COMPENSATION_EVENT_THROW_ICON =
            "icons/diagram/eventCompensationThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_COMPENSATION_EVENT_CATCH_ICON =
            "icons/diagram/eventCompensationCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_EVENT_CATCH_ICON =
            "icons/diagram/eventErrorCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_EVENT_THROW_ICON =
            "icons/diagram/eventErrorThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_LINK_EVENT_CATCH_ICON =
            "icons/diagram/eventLinkCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_LINK_EVENT_THROW_ICON =
            "icons/diagram/eventLinkThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_MESSAGE_EVENT_CATCH_ICON =
            "icons/diagram/eventMessageCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_MESSAGE_EVENT_THROW_ICON =
            "icons/diagram/eventMessageThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_EVENT_CATCH_ICON =
            "icons/diagram/eventMultipleCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_EVENT_THROW_ICON =
            "icons/diagram/eventMultipleThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_SIGNAL_EVENT_CATCH_ICON =
            "icons/diagram/eventSignalCatch_16.png"; //$NON-NLS-1$

    public static final String IMG_SIGNAL_EVENT_THROW_ICON =
            "icons/diagram/eventSignalThrow_16.png"; //$NON-NLS-1$

    public static final String IMG_INCOMING_REQUEST_EVENT_ICON = "icons/diagram/eventIncomingRequest_16.png"; //$NON-NLS-1$

    public static final String IMG_CONDITIONAL_EVENT_ICON =
            "icons/diagram/eventConditional_16.png"; //$NON-NLS-1$

    public static final String IMG_TERMINATE_EVENT_ICON =
            "icons/diagram/eventTerminate_16.png"; //$NON-NLS-1$

    public static final String IMG_TIMER_EVENT_ICON =
            "icons/diagram/eventTimer_16.png"; //$NON-NLS-1$

    public static final String IMG_SUBFLOW_MARKER =
            "icons/bpmn/activityMarker_subflow.png"; //$NON-NLS-1$

    public static final String IMG_COLLAPSE_EMB = "icons/bpmn/collapseEmb.png"; //$NON-NLS-1$

    public static final String IMG_LOOP_MARKER =
            "icons/bpmn/activityMarker_loop.png"; //$NON-NLS-1$

    public static final String IMG_AD_HOC_MARKER =
            "icons/bpmn/activityMarker_adHoc.png"; //$NON-NLS-1$

    public static final String IMG_COMPENSATION_MARKER =
            "icons/bpmn/activityMarker_compensation.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_MARKER_PARALLEL =
            "icons/bpmn/activityMarker_multipleParallel.png"; //$NON-NLS-1$

    public static final String IMG_MULTIPLE_MARKER_SEQUENTIAL =
            "icons/bpmn/activityMarker_multipleSequential.png"; //$NON-NLS-1$

    public static final String IMG_LINK = "icons/other/arrow-link.gif"; //$NON-NLS-1$

    public static final String IMG_DELETE = "icons/other/delete.gif"; //$NON-NLS-1$

    public static final String IMG_DELETE_ALL = "icons/other/delete_all.gif"; //$NON-NLS-1$

    public static final String IMG_ADD = "icons/other/add.gif"; //$NON-NLS-1$

    public static final String IMG_LETTER = "icons/other/message_12.png"; //$NON-NLS-1$

    public static final String IMG_PROCESS_WIZARD =
            "icons/other/ProcessWizardBusiness.png"; //$NON-NLS-1$

    public static final String IMG_TOOLBAR_HORIZONTAL_LAYOUT =
            "icons/elcl16/th_horizontal.gif"; //$NON-NLS-1$

    public static final String IMG_TOOLBAR_VERTICAL_LAYOUT =
            "icons/elcl16/th_vertical.gif"; //$NON-NLS-1$

    public static final String IMG_PLUS_BUTTON = "icons/other/plusButton.png"; //$NON-NLS-1$

    public static final String IMG_MINUS_BUTTON = "icons/other/minusButton.png"; //$NON-NLS-1$

    public static final String IMG_EMBSUBPROC_OPENCLOSE =
            "icons/other/OpenCloseEmbSubProc.png"; //$NON-NLS-1$

    public static final String IMG_EMBSUBPROC_OPEN =
            "icons/other/OpenEmbSubProc.png"; //$NON-NLS-1$

    public static final String IMG_EMBSUBPROC_CLOSE =
            "icons/other/CloseEmbSubProc.png"; //$NON-NLS-1$

    public static final String IMG_EMBSUBPROC_OPENHOT =
            "icons/other/OpenEmbSubProcHot.png"; //$NON-NLS-1$

    public static final String IMG_EMBSUBPROC_CLOSEHOT =
            "icons/other/CloseEmbSubProcHot.png"; //$NON-NLS-1$

    public static final String IMG_PROCESS = "icons/other/ProcessBusiness.png"; //$NON-NLS-1$

    public static final String IMG_PAGEFLOW_PROCESS =
            "icons/other/ProcessPageflow.png"; //$NON-NLS-1$

    public static final String IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS =
            "icons/other/ProcessBusinessService.png"; //$NON-NLS-1$

    public static final String IMG_CASE_SERVICE_PAGEFLOW_PROCESS =
            "icons/other/CaseService.png"; //$NON-NLS-1$

    public static final String IMG_SERVICE_PROCESS =
            "icons/other/ServiceProcess.png"; //$NON-NLS-1$

    public static final String IMG_TASK_LIBRARY = "icons/other/TaskLibrary.png"; //$NON-NLS-1$

    public static final String IMG_DECISIONS_FLOW =
            "icons/other/DecisionFlow.png"; //$NON-NLS-1$

    public static final String IMG_UP = "icons/other/up.gif"; //$NON-NLS-1$

    public static final String IMG_DOWN = "icons/other/down.gif"; //$NON-NLS-1$

    // Festive fun....
    public static final String IMG_1 = "icons/stuff/1.png"; //$NON-NLS-1$

    public static final String IMG_2 = "icons/stuff/2.png"; //$NON-NLS-1$

    public static final String IMG_3 = "icons/stuff/3.png"; //$NON-NLS-1$

    public static final String IMG_4 = "icons/stuff/4.png"; //$NON-NLS-1$

    public static final String IMG_5 = "icons/stuff/5.png"; //$NON-NLS-1$

    public static final String IMG_6 = "icons/stuff/6.png"; //$NON-NLS-1$

    public static final String IMG_7 = "icons/stuff/7.png"; //$NON-NLS-1$

    public static final String IMG_8 = "icons/stuff/8.png"; //$NON-NLS-1$

    public static final String IMG_9 = "icons/stuff/9.png"; //$NON-NLS-1$

    public static final String IMG_10 = "icons/stuff/10.png"; //$NON-NLS-1$

    public static final String IMG_R1 = "icons/stuff/r1.png"; //$NON-NLS-1$

    public static final String IMG_R2 = "icons/stuff/r2.png"; //$NON-NLS-1$

    public static final String IMG_R3 = "icons/stuff/r3.png"; //$NON-NLS-1$

    public static final String IMG_R4 = "icons/stuff/r4.png"; //$NON-NLS-1$

    public static final String IMG_R5 = "icons/stuff/r5.png"; //$NON-NLS-1$

    public static final String IMG_R6 = "icons/stuff/r6.png"; //$NON-NLS-1$

    public static final String IMG_R7 = "icons/stuff/r7.png"; //$NON-NLS-1$

    public static final String IMG_R8 = "icons/stuff/r8.png"; //$NON-NLS-1$

    public static final String IMG_R9 = "icons/stuff/r9.png"; //$NON-NLS-1$

    public static final String IMG_R10 = "icons/stuff/r10.png"; //$NON-NLS-1$

    public static final String IMG_ERROR_ICON = "icons/other/error.gif"; //$NON-NLS-1$

    public static final String IMG_WARNING_ICON = "icons/other/warning.gif"; //$NON-NLS-1$

    public static final String IMG_INFO_ICON = "icons/other/inforLarge.gif"; //$NON-NLS-1$

    public static final String IMG_ERRORLARGE_ICON =
            "icons/other/errorLarge.gif"; //$NON-NLS-1$

    public static final String IMG_WARNINGLARGE_ICON =
            "icons/other/warningLarge.gif"; //$NON-NLS-1$

    public static final String IMG_CREATECONN_GADGET_ICON =
            "icons/other/createConnGadget.png"; //$NON-NLS-1$

    public static final String IMG_CYCLETYPE_GADGET_ICON =
            "icons/other/cycleTypeGadget.png"; //$NON-NLS-1$

    public static final String[] IMAGES = new String[] {
            IMG_TOOL_ASSOCIATION_16, IMG_TOOL_ASSOCIATION_24, IMG_1, IMG_2,
            IMG_3, IMG_4, IMG_5, IMG_6, IMG_7, IMG_8, IMG_9, IMG_10, IMG_R1,
            IMG_R2, IMG_R3, IMG_R4, IMG_R5, IMG_R6, IMG_R7, IMG_R8, IMG_R9,
            IMG_R10, IMG_TOOL_FLOWDRAWER_16, IMG_TOOL_FLOW_16,
            IMG_TOOL_FLOW_24, IMG_TOOL_FLOWCONDITIONAL_16,
            IMG_TOOL_FLOWCONDITIONAL_24, IMG_TOOL_FLOWDEFAULT_16,
            IMG_TOOL_FLOWDEFAULT_24, IMG_TOOL_FLOWEXCEPTION_16,
            IMG_TOOL_FLOWEXCEPTION_24, IMG_TOOL_MESSAGE_16,
            IMG_TOOL_MESSAGE_24, IMG_TOOL_EVENT_END_16, IMG_TOOL_EVENT_END_24,
            IMG_TOOL_EVENT_START_16, IMG_TOOL_EVENT_START_24,
            IMG_TOOL_GENERIC_GATEWAY_16, IMG_TOOL_GENERIC_GATEWAY_24,
            IMG_TOOL_PARALLEL_GATEWAY_16, IMG_TOOL_PARALLEL_GATEWAY_24,
            IMG_TOOL_XORDATA_GATEWAY_16, IMG_TOOL_XORDATA_GATEWAY_24,
            IMG_TOOL_XOREVENT_GATEWAY_16, IMG_TOOL_XOREVENT_GATEWAY_24,
            IMG_TOOL_OR_GATEWAY_16, IMG_TOOL_OR_GATEWAY_24,
            IMG_TOOL_COMPLEX_GATEWAY_16, IMG_TOOL_COMPLEX_GATEWAY_24,
            IMG_TOOL_TASKDRAWER_16, IMG_TOOL_TASK_16, IMG_TOOL_TASK_24,
            IMG_TOOL_LANE_16, IMG_TOOL_LANE_24, IMG_TOOL_TASKSET_16,
            IMG_TOOL_TASKSET_24, IMG_TOOL_NOTE_16, IMG_TOOL_NOTE_24,
            IMG_SERVICETASK_ICON, IMG_DTABLETASK_ICON, IMG_USERTASK_ICON,
            IMG_PAGEFLOW_ICON, IMG_SUBFLOW_MARKER, IMG_LOOP_MARKER,
            IMG_AD_HOC_MARKER, IMG_COMPENSATION_MARKER,
            IMG_MULTIPLE_MARKER_PARALLEL, IMG_LINK, IMG_DELETE, IMG_DELETE_ALL,
            IMG_ADD, IMG_PROCESS_WIZARD, IMG_TOOL_EVENT_INTERMEDIATE_16,
            IMG_TOOL_EVENT_INTERMEDIATE_24, IMG_TOOL_POOL_16, IMG_TOOL_POOL_24,
            IMG_TOOL_DATA_OBJECT_16, IMG_TOOL_DATA_OBJECT_24,
            IMG_TOOL_GROUP_16, IMG_TOOL_GROUP_24, IMG_LETTER,
            IMG_TOOL_SUBPROC_EMBEDDED_16, IMG_TOOL_SUBPROC_EMBEDDED_24,
            IMG_TOOL_SUBPROC_EVENT_16, IMG_TOOL_SUBPROC_EVENT_24,
            IMG_TOOL_SUBPROC_16, IMG_TOOL_SUBPROC_24, IMG_SUBPROCTASK_ICON,
            IMG_SCRIPTTASK_ICON, IMG_SENDTASK_ICON, IMG_RECEIVETASK_ICON,
            IMG_MANUALTASK_ICON, IMG_REFERENCETASK_ICON, IMG_TOOL_USERTASK_16,
            IMG_TOOL_USERTASK_24, IMG_TOOL_MANUALTASK_16,
            IMG_TOOL_MANUALTASK_24, IMG_TOOL_SERVICETASK_16,
            IMG_TOOL_SERVICETASK_24, IMG_TOOL_SCRIPTTASK_16,
            IMG_TOOL_SCRIPTTASK_24, IMG_TOOL_SENDTASK_16, IMG_TOOL_SENDTASK_24,
            IMG_TOOL_RECEIVETASK_16, IMG_TOOL_RECEIVETASK_24,
            IMG_TOOL_REFERENCETASK_16, IMG_TOOL_REFERENCETASK_24,
            IMG_XORDATA_GATEWAY_ICON, IMG_XOREVENT_GATEWAY_ICON,
            IMG_OR_GATEWAY_ICON, IMG_PARALLEL_GATEWAY_ICON,
            IMG_COMPLEX_GATEWAY_ICON, IMG_CANCEL_EVENT_THROW_ICON,
            IMG_CANCEL_EVENT_CATCH_ICON, IMG_COMPENSATION_EVENT_THROW_ICON,
            IMG_COMPENSATION_EVENT_CATCH_ICON, IMG_ERROR_EVENT_CATCH_ICON,
            IMG_ERROR_EVENT_THROW_ICON, IMG_LINK_EVENT_CATCH_ICON,
            IMG_LINK_EVENT_THROW_ICON, IMG_MESSAGE_EVENT_CATCH_ICON,
            IMG_MESSAGE_EVENT_THROW_ICON, IMG_MULTIPLE_EVENT_CATCH_ICON,
            IMG_MULTIPLE_EVENT_THROW_ICON, IMG_CONDITIONAL_EVENT_ICON,
            IMG_TERMINATE_EVENT_ICON, IMG_TIMER_EVENT_ICON,
            IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_16,
            IMG_TOOLBAR_HORIZONTAL_LAYOUT, IMG_TOOLBAR_VERTICAL_LAYOUT,
            IMG_TOOL_EVENT_START_MESSAGE_16, IMG_TOOL_EVENT_START_MESSAGE_24,
            IMG_TOOL_EVENT_START_MULTIPLE_16, IMG_TOOL_EVENT_START_MULTIPLE_24,
            IMG_TOOL_EVENT_START_CONDITIONAL_16,
            IMG_TOOL_EVENT_START_CONDITIONAL_24, IMG_TOOL_EVENT_START_TIMER_16,
            IMG_TOOL_EVENT_START_TIMER_24, IMG_TOOL_EVENT_START_SIGNAL_16,
            IMG_TOOL_EVENT_START_SIGNAL_24, IMG_TOOL_EVENT_END_CANCEL_16,
            IMG_TOOL_EVENT_END_CANCEL_24, IMG_TOOL_EVENT_END_COMPENSATION_16,
            IMG_TOOL_EVENT_END_COMPENSATION_24, IMG_TOOL_EVENT_END_ERROR_16,
            IMG_TOOL_EVENT_END_ERROR_24, IMG_TOOL_EVENT_END_MESSAGE_16,
            IMG_TOOL_EVENT_END_MESSAGE_24, IMG_TOOL_EVENT_END_MULTIPLE_16,
            IMG_TOOL_EVENT_END_MULTIPLE_24, IMG_TOOL_EVENT_END_TERMINATE_16,
            IMG_TOOL_EVENT_END_TERMINATE_24, IMG_TOOL_EVENT_END_SIGNAL_16,
            IMG_TOOL_EVENT_END_SIGNAL_24,
            IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_16,
            IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_24,
            IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_24,
            IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_16,
            IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_24,
            IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_16,
            IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_24,
            IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_16,
            IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_24,
            IMG_TOOL_EVENT_INTERMEDIATE_TIMER_16,
            IMG_TOOL_EVENT_INTERMEDIATE_TIMER_24,
            IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_16,
            IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_24,
            IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_16,
            IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_24,
            IMG_TOOL_FAVOURITES_16, IMG_TOOL_FAVOURITES_24, IMG_PLUS_BUTTON,
            IMG_MINUS_BUTTON, IMG_EMBSUBPROC_OPENCLOSE, IMG_EMBSUBPROC_OPEN,
            IMG_EMBSUBPROC_CLOSE, IMG_EMBSUBPROC_OPENHOT,
            IMG_EMBSUBPROC_CLOSEHOT, IMG_PROCESS, IMG_PAGEFLOW_PROCESS,
            IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS, IMG_SERVICE_PROCESS,
            IMG_CASE_SERVICE_PAGEFLOW_PROCESS, IMG_TASK_LIBRARY,
            IMG_SIGNAL_EVENT_CATCH_ICON, IMG_SIGNAL_EVENT_THROW_ICON,
            IMG_ERROR_ICON, IMG_WARNING_ICON, IMG_INFO_ICON,
            IMG_ERRORLARGE_ICON, IMG_WARNINGLARGE_ICON,
            IMG_CREATECONN_GADGET_ICON, IMG_COLLAPSE_EMB,
            IMG_CYCLETYPE_GADGET_ICON, IMG_DECISIONS_FLOW, IMG_UP, IMG_DOWN,
            IMG_TOOL_DTABLETASK_16, IMG_TOOL_DTABLETASK_24,
            IMG_TOOL_REFERENCETASK_OVERLAY, IMG_MULTIPLE_MARKER_SEQUENTIAL,
            IMG_INCOMING_REQUEST_EVENT_ICON

    };

    public static final String PROP_EDITING_DOMAIN = "EditingDomain"; //$NON-NLS-1$

    public static final String PROP_ANNOTATION_FACTORY = "AnnotationFactory"; //$NON-NLS-1$

    public static final String PROP_TEXT_HANDLERS = "TextHandlers"; //$NON-NLS-1$

    public static final String PROP_WIDGET = "Widget"; //$NON-NLS-1$

    public static final String PROP_LABEL_PROVIDER = "LabelProvider"; //$NON-NLS-1$

    public static final String PICKER_PERFORMER = "PerformaerPicker"; //$NON-NLS-1$

    public static final String PICKER_SUB_PROCESS = "SubProcessPicker"; //$NON-NLS-1$

    public static final String PICKER_APPLICATION = "ApplicationPicker"; //$NON-NLS-1$

    // Values for PRINT_PAGE_FIT_TYPE property.
    public static final int PRINT_FIT_ZOOM = 0;

    public static final int PRINT_FIT_HORIZONTALLY = 1;

    public static final int PRINT_FIT_VERTICALLY = 2;

    public static final int PRINT_FIT_TO_PAGE = 3;

    public static final String EXTENSIONS_LAYER = "ExtensionsLayer"; //$NON-NLS-1$

    public static final String PAGINATION_LAYER = "PaginationLayer"; //$NON-NLS-1$

    /** Layer for extra figure's for process progressions (mouse insensitive) */
    public static final String PROGRESSION_FIGURES_LAYER =
            "ProgressionFigureLayer"; //$NON-NLS-1$

    /**
     * Layer for extra figures for controls for process progression (mouse
     * sensitive)
     */
    public static final String PROGRESSION_CONTROLS_LAYER =
            "ProgressionControlsLayer"; //$NON-NLS-1$

    /**
     * XPD-1431: Layer for extra figure's for process progressions (mouse
     * insensitive)
     */
    public static final String EDITPART_HIGHLIGHTER_LAYER =
            "EditPartHighlighterLayer"; //$NON-NLS-1$

    // Make sure all objects default to same background colour...
    public static final RGB SHAPE_BGCOLOR_DEFAULT = new RGB(250, 250, 230);

    public static final String GROUP_LAYER = "GroupLayer"; //$NON-NLS-1$

    // Size of inner squares of main snap grid.
    public static final int SNAPGRID_SIZE = 32;

    // Note that EVENT may end up larger because of line width
    public static final int END_EVENT_SIZE = 27;

    public static final int INTERMEDIATE_EVENT_SIZE = 27;

    public static final int START_EVENT_SIZE = 27;

    public static final int DATAOBJECT_WIDTH_SIZE = 32;

    public static final int DATAOBJECT_HEIGHT_SIZE = 38;

    public static final int DATAOBJECT_CORNERBEND_SIZE = 8;

    public static final int NOTE_HEIGHT = 20;

    public static final int GATEWAY_WIDTH_SIZE = 41;

    public static final int GATEWAY_HEIGHT_SIZE = 43;

    public static final int TASK_WIDTH_SIZE = SNAPGRID_SIZE * 3;

    public static final int TASK_HEIGHT_SIZE = SNAPGRID_SIZE * 2;

    public static final int EMBSUBFLOW_WIDTH_SIZE = SNAPGRID_SIZE * 9;

    public static final int EMBSUBFLOW_HEIGHT_SIZE = SNAPGRID_SIZE * 6;

    public static final int SUBFLOW_WIDTH_SIZE = (SNAPGRID_SIZE * 4);

    public static final int SUBFLOW_HEIGHT_SIZE = (int) (SNAPGRID_SIZE * 2.5);

    public static final int EMB_SUBPROC_CONTENT_MARGIN = 20;

    public static final int GROUP_WIDTH_SIZE = SNAPGRID_SIZE * 9;

    public static final int GROUP_HEIGHT_SIZE = SNAPGRID_SIZE * 6;

    // The last right-click position in Lane / Embedded sub-proc.
    public static final String VIEWPROP_FLOWCONTAINER_LASTCLICKPOS =
            "FlowContainer_LastClickPos"; //$NON-NLS-1$

    // When right-click on sequence flow, this is the Lane / Task behind the seq
    // flow at position clicked
    public static final String VIEWPROP_SEQFLOW_LASTCLICKFLOWCONTAINER =
            "SeqFlow_LastClick_Container"; //$NON-NLS-1$

    // btw the 7 and 3 constants shamelessly ripped out of
    // PolygoDecoration class, for scaling connection decoration
    // according to scale of embedded subflow content we have
    // to scale the decoration - unfortunately PolygonDecoration
    // doesn't have a way of getting the current scale.
    // So that's why I've ripped these off!
    public static final double POLYGONDECORATION_XSCALE = 7.0;

    public static final double POLYGONDECORATION_YSCALE = 3.0;

    public static final int BORDER_HIT_TOLERANCE = 10;

}
