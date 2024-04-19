package com.tibco.xpd.process.js.model;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;

public class ProcessJsConsts {
    public static final String UNDEFINED_DATA_TYPE = "UndefinedDataType"; //$NON-NLS-1$

    // integer value representing the undefined_data_type
    public static final String JSCRIPT_DESTINATION = "javaScript1.x"; //$NON-NLS-1$

    public static final String BPMN_DESTINATION = "bpmn"; //$NON-NLS-1$

    public static final String SIMULATION_DEST_ENVIR = "simulation1.2"; //$NON-NLS-1$

    public static final String SCRIPT_TASK = "ScriptTask"; //$NON-NLS-1$

	public static final String	SCRIPT_LIBRARY_FUNCTION	 = "ScriptLibraryFunction"; //$NON-NLS-1$

    public static final String PERFORMER_SCRIPT = "PerformerDataField"; //$NON-NLS-1$

    public static final String WEBSERVICE_TASK = "WebServiceTask"; //$NON-NLS-1$

    public static final String RESTSERVICE_TASK = "RestServiceTask"; //$NON-NLS-1$

    public static final String JAVASERVICE_TASK = "JavaServiceTask"; //$NON-NLS-1$

    public static final String SUBPROCESS_TASK = "SubProcessTask"; //$NON-NLS-1$

    public static final String OPEN_USER_TASK = "OpenUserTask"; //$NON-NLS-1$

    public static final String CLOSE_USER_TASK = "CloseUserTask"; //$NON-NLS-1$

    public static final String SUBMIT_USER_TASK = "SubmitUserTask"; //$NON-NLS-1$

    public static final String SCHEDULE_USER_TASK = "ScheduleUserTask"; //$NON-NLS-1$

    public static final String RESCHEDULE_USER_TASK = "RescheduleUserTask"; //$NON-NLS-1$

    public static final String STD_LOOP_EXPR = "StdLoopExpr"; //$NON-NLS-1$

    public static final String MI_LOOP_EXPR = "MILoopExpr"; //$NON-NLS-1$

    public static final String MI_COMPLEX_EXIT_EXPR = "MIComplexExitExpr"; //$NON-NLS-1$

    public static final String MI_ADDITIONAL_INSTANCES_LOOP_EXPR =
            "MIAdditionalInstancesExpr"; //$NON-NLS-1$

    public static final String SEQUENCE_FLOW = "SequenceFlow"; //$NON-NLS-1$

    public static final String TIMER_EVENT = "TimerEvent"; //$NON-NLS-1$

    public static final String RESCHEDULE_TIMER_EVENT =
            ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT;

    public static final String MESSAGE_EVENT = "MessageEvent"; //$NON-NLS-1$

    public static final String MESSAGE_METHOD = "MessageMethod"; //$NON-NLS-1$

    public static final String INPUT_TO_SERVICE = "InputToService"; //$NON-NLS-1$

    public static final String OUTPUT_TO_SERVICE = "OutputToService"; //$NON-NLS-1$

    public static final String DEC_FLOW_SERVICE_TASK = "DecFlowServiceTask"; //$NON-NLS-1$

    /*
     * Represents the date part of the DateTime Process data
     */
    public static final String DATE = "Date"; //$NON-NLS-1$

    /*
     * Represents the time part of the DateTime Process data
     */
    public static final String TIME = "Time"; //$NON-NLS-1$

    /**
     * Represents the length attribute of the an array field
     */
    public static final String PERFORMER = "Performer"; //$NON-NLS-1$

    public static final String REFERENCE = "Reference"; //$NON-NLS-1$

    public static final String DATETIME = "DateTime"; //$NON-NLS-1$

    public static final String VAR_TYPE = "VarType"; //$NON-NLS-1$

    public static final String PARAM_IN_MODE = "IN"; //$NON-NLS-1$

    public static final String PARAM_OUT_MODE = "OUT"; //$NON-NLS-1$

    public static final String PARAM_IN_OUT_MODE = "INOUT"; //$NON-NLS-1$

    public static final String PARAM_PASS_BY_REFERENCE = "isPassByReference"; //$NON-NLS-1$

    public static final String TIMER_EVENT_TIME_EXPR = "TIMER_EVENT_TIME_EXPR"; //$NON-NLS-1$

    public static final String TIMER_EVENT_DATE_EXPR = "TIMER_EVENT_DATE_EXPR"; //$NON-NLS-1$

    public static final String JAVASCRIPT_GRAMMAR =
            ScriptGrammarFactory.JAVASCRIPT;

    public static final String DATA_MAPPER_GRAMMAR =
            ScriptGrammarFactory.DATAMAPPER;

    public static final String POJO_PARAMETER_VALUE = "parameter"; //$NON-NLS-1$    

    public static final String POJO_RETURN_VALUE = "RV"; //$NON-NLS-1$    

    public static final String RESOLVER_CLASSNAME = "resolver"; //$NON-NLS-1$    

    public static final String FREE_TEXT_GRAMMAR = "Text"; //$NON-NLS-1$

}
