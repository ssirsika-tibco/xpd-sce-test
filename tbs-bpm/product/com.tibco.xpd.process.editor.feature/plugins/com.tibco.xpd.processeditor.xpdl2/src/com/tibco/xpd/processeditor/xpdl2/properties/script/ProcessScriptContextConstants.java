/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

/**
 * Declares all the script context constants.
 * 
 * @author rsomayaj
 * 
 */
public interface ProcessScriptContextConstants {

    public static final String RESCHEDULE_USER_TASK = "RescheduleUserTask"; //$NON-NLS-1$

    public static final String SCHEDULE_USER_TASK = "ScheduleUserTask"; //$NON-NLS-1$

    public static final String OPEN_USER_TASK = "OpenUserTask"; //$NON-NLS-1$

    public static final String CLOSE_USER_TASK = "CloseUserTask"; //$NON-NLS-1$

    public static final String SUBMIT_USER_TASK = "SubmitUserTask"; //$NON-NLS-1$

    public static final String SCRIPT_TASK = "ScriptTask"; //$NON-NLS-1$

    public static final String ADHOC_PRECONDITION = "AdhocPrecondition"; //$NON-NLS-1$

    public static final String TIMER_EVENT = "TimerEvent"; //$NON-NLS-1$

    public static final String RESCHEDULE_TIMER_EVENT = "RescheduleTimerEvent"; //$NON-NLS-1$

    public static final String SEQUENCE_FLOW = "SequenceFlow"; //$NON-NLS-1$

    public static final String WEB_SERVICE_TASK = "WebServiceTask"; //$NON-NLS-1$

    public static final String TRANSFORM_SCRIPT_TASK = "TransformScriptTask"; //$NON-NLS-1$

    public static final String BW_SERVICE_TASK = "BWServiceTask"; //$NON-NLS-1$

    public static final String JAVA_SERVICE_TASK = "JavaServiceTask"; //$NON-NLS-1$

    public static final String SUB_PROCESS_TASK = "SubProcessTask"; //$NON-NLS-1$

	public static final String	PROCESS_SCRIPT_LIBRARY_FUNCTION			= "ScriptLibraryFunction";				//$NON-NLS-1$

    public static final String DATA_MAPPER_PE_MAPPING_SCRIPTS =
            "DataMapperPEMappingScripts"; //$NON-NLS-1$

    public static final String DATA_MAPPER_WM_MAPPING_SCRIPTS =
            "DataMapperWMMappingScripts"; //$NON-NLS-1$

    public static final String DATA_MAPPER_REST_MAPPING_SCRIPTS =
            "DataMapperRestMappingScripts"; //$NON-NLS-1$
    
    public static final String DATA_MAPPER_SWAGGER_MAPPING_SCRIPTS =
            "DataMapperSwaggerMappingScripts"; //$NON-NLS-1$

    public static final String CATCH_SIGNAL_EVENTMAPPING =
            "CatchSignalEventMapping"; //$NON-NLS-1$

    public static final String GLOBAL_THROW_SIGNAL_EVENTMAPPING =
            "GlobalThrowSignalEventMapping"; //$NON-NLS-1$

    public static final String GLOBAL_CATCH_SIGNAL_EVENTMAPPING =
            "GlobalCatchSignalEventMapping"; //$NON-NLS-1$

    public static final String DEC_FLOW_SERVICE_TASK = "DecFlowServiceTask"; //$NON-NLS-1$

    public static final String STD_LOOP_EXPR = "StdLoopExpr"; //$NON-NLS-1$

    public static final String MI_LOOP_EXPR = "MILoopExpr"; //$NON-NLS-1$

    public static final String PERFORMER_DATA_FIELD = "PerformerDataField"; //$NON-NLS-1$

    public static final String QUERY_PARTICIPANT = "QueryParticipant"; //$NON-NLS-1$

    public static final String MI_COMPLEX_EXIT_EXPR = "MIComplexExitExpr"; //$NON-NLS-1$

    public static final String MI_ADDITIONAL_INSTANCES_LOOP_EXPR =
            "MIAdditionalInstancesExpr"; //$NON-NLS-1$

    public static final String INITIATED_SCRIPT_TASK = "InitiatedScriptTask"; //$NON-NLS-1$

    public static final String COMPLETED_SCRIPT_TASK = "CompletedScriptTask"; //$NON-NLS-1$

    public static final String DEADLINE_EXPIRED_SCRIPT_TASK =
            "DeadlineExpiredScriptTask"; //$NON-NLS-1$

    public static final String CANCELLED_SCRIPT_TASK = "CancelledScriptTask"; //$NON-NLS-1$

    public static final String DURATION_CALCULATION = "DurationCalculation"; //$NON-NLS-1$

    public static final String ADVANCED_PROPERTY__VALIDATION_KEY =
            "AdvancedPropertyValidationKey"; //$NON-NLS-1$

    public static final String ADVANCED_PROPERTY_NAME_VALIDATION_KEY =
            "AdvancedPropertyNameValidationKey"; //$NON-NLS-1$

    public static final String CASESTART_USERATTRIBUTE =
            "CaseStartUserAttribute"; //$NON-NLS-1$

    public static final String CASEADMIN_USERATTRIBUTE =
            "CaseAdminUserAttribute"; //$NON-NLS-1$    

    public static final String CONDITIONAL_DEADLINE = "ConditionalDeadline"; //$NON-NLS-1$

    public static final String DELAYED_RELEASE_CONDITION =
            "DelayedReleaseCondition"; //$NON-NLS-1$

    public static final String WORK_ITEM_PRIORITY_PERIOD_TYPE =
            "WorkItemPriorityPeriodType"; //$NON-NLS-1$

    public static final String WORK_ITEM_PRIORITY = "WorkItemPriority"; //$NON-NLS-1$

    public static final String REST_SERVICE_TASK = "RestServiceTask"; //$NON-NLS-1$

}
