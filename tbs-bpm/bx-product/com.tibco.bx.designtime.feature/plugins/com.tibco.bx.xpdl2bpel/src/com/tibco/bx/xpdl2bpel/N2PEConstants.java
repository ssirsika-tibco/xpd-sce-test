package com.tibco.bx.xpdl2bpel;

public interface N2PEConstants {
	
	public static final String N2_GLOBAL_DESTINATION_ID = "com.tibco.xpd.n2.core.n2GlobalDestination"; //$NON-NLS-1$

	//N2 process engine destination id
	static final String N2PE_DESTINATION_ID = "n2pe1.x"; //$NON-NLS-1$

	// bpel files output folder name
	static final String BPEL_FILES_OUTPUT_PATH = ".bpelFiles"; //$NON-NLS-1$

    static final String  XPDL_EXTENSION = ".xpdl"; //$NON-NLS-1$
    static final String  BPEL_EXTENSION = ".bpel"; //$NON-NLS-1$
    static final String  WSDL_EXTENSION = ".wsdl"; //$NON-NLS-1$

    static final String JSCRIPT_LANGUAGE = "urn:tibco:wsbpel:2.0:sublang:javascript"; //$NON-NLS-1$
    static final String  XPATH_1_LANGUAGE = "urn:oasis:names:tc:wsbpel:2.0:sublang:xpath1.0"; //$NON-NLS-1$

    static final String XPDL_ID_TAG = "xpdlId"; //$NON-NLS-1$
    static final String TYPE_TAG = "type"; //$NON-NLS-1$
    static final String MIGRATION_ALLOWED_TAG = "migrationAllowed"; //$NON-NLS-1$
    static final String UNCONTROLLED_MERGE_EXTENSION = "uncontrolledMerge"; //$NON-NLS-1$
    static final String LABEL_TAG = "label"; //$NON-NLS-1$
    static final String NAME_TAG = "name"; //$NON-NLS-1$

    
    static final String XSD_PREFIX = "xsd"; //$NON-NLS-1$
    static final String XSD_NAMESPACE_URI = "http://www.w3.org/2001/XMLSchema"; //$NON-NLS-1$
    
    static final String TASK_TYPE = "task"; //$NON-NLS-1$
    static final String USER_TASK_TYPE = "userTask"; //$NON-NLS-1$
    static final String MANUAL_TASK_TYPE = "manualTask"; //$NON-NLS-1$
    static final String SERVICE_TASK_TYPE = "serviceTask"; //$NON-NLS-1$
    static final String SCRIPT_TASK_TYPE = "scriptTask"; //$NON-NLS-1$
    static final String SEND_TASK_TYPE = "sendTask"; //$NON-NLS-1$
    static final String RECEIVE_TASK_TYPE = "receiveTask"; //$NON-NLS-1$
    static final String REFERENCE_TASK_TYPE = "referenceTask"; //$NON-NLS-1$
    static final String REUSEABLE_SUB_PROCESS_TYPE = "reusableSubProcess"; //$NON-NLS-1$
    static final String EMBEDDED_SUB_PROCESS_TYPE = "embeddedSubProcess"; //$NON-NLS-1$
    static final String EVENT_SUB_PROCESS_TYPE = "eventSubProcess"; //$NON-NLS-1$
    static final String GATEWAY_TYPE = "gateway"; //$NON-NLS-1$
    static final String GATEWAY_XOR_TYPE = "exclusiveGateway"; //$NON-NLS-1$
    static final String GATEWAY_XOR_EVENT_TYPE = "exclusiveEventBasedGateway"; //$NON-NLS-1$
    static final String GATEWAY_OR_TYPE = "inclusiveGateway"; //$NON-NLS-1$
    static final String GATEWAY_AND_TYPE = "parallelGateway"; //$NON-NLS-1$
    static final String GATEWAY_COMPLEX_TYPE = "complexGateway"; //$NON-NLS-1$
    static final String EVENT_TYPE = "event"; //$NON-NLS-1$
    static final String END_EVENT_TYPE = "endEvent"; //$NON-NLS-1$
    static final String MESSAGE_END_EVENT_TYPE = "messageEndEvent"; //$NON-NLS-1$
    static final String SIGNAL_END_EVENT_TYPE = "signalEndEvent"; //$NON-NLS-1$
    static final String ERROR_END_EVENT_TYPE = "errorEndEvent"; //$NON-NLS-1$
    static final String COMPENSATION_END_EVENT_TYPE = "compensationEndEvent"; //$NON-NLS-1$
    static final String TERMINATE_END_EVENT_TYPE = "terminateEndEvent"; //$NON-NLS-1$

    static final String START_EVENT_TYPE = "startEvent"; //$NON-NLS-1$
    static final String MESSAGE_START_EVENT_TYPE = "messageStartEvent"; //$NON-NLS-1$
    static final String TIMER_START_EVENT_TYPE = "timerStartEvent"; //$NON-NLS-1$
    static final String SIGNAL_START_EVENT_TYPE = "signalStartEvent"; //$NON-NLS-1$
    static final String THROW_INTERMEDIATE_EVENT_TYPE = "throwIntermediateEvent"; //$NON-NLS-1$
    static final String THROW_MESSAGE_INTERMEDIATE_EVENT_TYPE = "throwMessageIntermediateEvent"; //$NON-NLS-1$
    static final String THROW_SIGNAL_INTERMEDIATE_EVENT_TYPE = "throwSignalIntermediateEvent"; //$NON-NLS-1$
    static final String THROW_COMPENSATION_INTERMEDIATE_EVENT_TYPE = "throwCompensationIntermediateEvent"; //$NON-NLS-1$
    static final String THROW_ERROR_INTERMEDIATE_EVENT_TYPE = "throwErrorIntermediateEvent"; //$NON-NLS-1$
    static final String THROW_LINK_INTERMEDIATE_EVENT_TYPE = "throwLinkIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_INTERMEDIATE_EVENT_TYPE = "catchIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_MESSAGE_INTERMEDIATE_EVENT_TYPE = "catchMessageIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_SIGNAL_INTERMEDIATE_EVENT_TYPE = "catchSignalIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_TIMER_INTERMEDIATE_EVENT_TYPE = "catchTimerIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_ERROR_INTERMEDIATE_EVENT_TYPE = "catchErrorIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_COMPENSATION_INTERMEDIATE_EVENT_TYPE = "catchCompensationIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_LINK_INTERMEDIATE_EVENT_TYPE = "catchLinkIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_MULTIPLE_INTERMEDIATE_EVENT_TYPE = "catchMultipleIntermediateEvent"; //$NON-NLS-1$
    static final String CATCH_CANCEL_INTERMEDIATE_EVENT_TYPE = "catchCancelIntermediateEvent"; //$NON-NLS-1$
    static final String BOUNDARY_EVENT_TYPE = "boundaryEvent"; //$NON-NLS-1$
    static final String BOUNDARY_MESSAGE_EVENT_TYPE = "boundaryMessageEvent"; //$NON-NLS-1$
    static final String BOUNDARY_TIMER_EVENT_TYPE = "boundaryTimerEvent"; //$NON-NLS-1$
    static final String BOUNDARY_SIGNAL_EVENT_TYPE = "boundarySignalEvent"; //$NON-NLS-1$
    static final String BOUNDARY_ERROR_EVENT_TYPE = "boundaryErrorEvent"; //$NON-NLS-1$
    static final String BOUNDARY_COMPENSATION_EVENT_TYPE = "boundaryCompensationEvent"; //$NON-NLS-1$

    static final String CORRELATE_IMMEDIATE = "correlateImmediate"; //$NON-NLS-1$
    
	/*
	 * the prefix of targetNameSpace for the generated WSDL
	 * the suffix is the name of process
	 */
	static final String TARGET_WSDL_NS_PREFIX = "http://www.tibco.com/bx/"; //$NON-NLS-1$

	/*
	 * the prefix for generated BPEL variable or task names to separate from user defined names
	 */
    static final String NAME_PREFIX = "_BX_"; //$NON-NLS-1$

	/*
	 * the prefix for temp variables in the generated data mapping scripts
	 */
	static final String MESSAGE_PREFIX = "MESSAGE_"; //$NON-NLS-1$


    /**
     * Extended attribute names for process/activity retries.
     */
	public static final String RETRY_INITIAL_PERIOD = "retryIntervalInSeconds"; //$NON-NLS-1$
	public static final String RETRY_MAX = "maxNumberOfRetries"; //$NON-NLS-1$
	public static final String RETRY_PERIOD_INCREMENT = "retryIntervalIncrementInSeconds"; //$NON-NLS-1$
	public static final String RETRY_MAX_RETRY_ACTION = "haltOnError"; //$NON-NLS-1$

    /**
     * Extended attribute name for calendar reference.
     */
	public static final String CALENDAR_REFERENCE = "calendarRef"; //$NON-NLS-1$
	
    /**
     * Extended attribute name for event flow.
     */
	public static final String EVENTHANDLER_BLOCK_UNTIL_COMPLETED = "blockUntilCompleted"; //$NON-NLS-1$
}
