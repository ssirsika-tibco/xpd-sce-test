package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

/**
 * Process editor configuation object types for use with the
 * processEditorConfiguration extension point.
 * <p>
 * The literal names of these enums are designed to match the extension point
 * schema configuration's "restrictions" for ObjectType.
 * 
 * @author aallway
 * @since 31 Oct 2011
 */
public enum ProcessEditorObjectType {
    /* Start event types. */
    start_event_none,

    start_event_message,

    start_event_timer,

    start_event_conditional,

    start_event_multi,

    start_event_signal,

    /* Intermediate event types. */
    intermediate_event_none,

    intermediate_event_message_catch,

    intermediate_event_message_throw,

    intermediate_event_timer,

    intermediate_event_conditional,

    intermediate_event_link_catch,

    intermediate_event_link_throw,

    intermediate_event_signal_catch,

    intermediate_event_signal_throw,

    intermediate_event_multi_catch,

    intermediate_event_multi_throw,

    intermediate_event_error_catch,

    intermediate_event_compensation_catch,

    intermediate_event_compensation_throw,

    intermediate_event_cancel_catch,

    /* End event types. */
    end_event_none,

    end_event_message,

    end_event_multi,

    end_event_error,

    end_event_compensation,

    end_event_cancel,

    end_event_signal,

    end_event_terminate,

    /* Gateway types. */
    gateway_exclusive_data_based,

    gateway_parallel,

    gateway_exclusive_event_based,

    gateway_inclusive,

    gateway_complex,

    /* Task types. */
    task_none,

    task_user,

    task_manual,

    task_service,

    task_script,

    task_send,

    task_receive,

    task_reference,

    task_decisiontable,

    /* Sub-proc. */
    task_subprocess,

    embedded_subprocess,

    /*
     * ABPM-911: Saket: Adding new type.
     */
    event_subprocess,

    /* Artifacts. */
    artifact_text_annotation,

    artifact_data_object,

    artifact_group_box,

    /* Connections. */
    sequenceflow_uncontrolled,

    sequenceflow_conditional,

    sequenceflow_default,

    messageflow,

    association_connection,

    pool,
}