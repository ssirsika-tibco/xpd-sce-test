package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

/**
 * Process editor configuation element types for use with the
 * processEditorConfiguration extension point.
 * <p>
 * The literal names of these enums are designed to match the extension point
 * schema configuration's "restrictions" for ObjectType.
 * 
 * @author aallway
 * @since 31 Oct 2011
 */
public enum ProcessEditorElementType {
    datafield,

    participant,

    correlation_data,

    formal_parameter,

    type_declaration,

    process_interface,

    business_process,

    pageflow_process,

    business_service,

    case_service,

    service_process,

    service_process_interface,

}