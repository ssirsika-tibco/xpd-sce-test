/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resolvers;

import com.tibco.xpd.xpdl2.edit.internal.Messages;

/**
 * Process data reference contexts are 'the specific place/context in which a
 * data reference appears'.
 * <p>
 * The context is expressed as a simple {@link String} identification because
 * the discovery of references to process data is a contributable framework and
 * hence base Studio will only 'know' of the potential existence a certain set
 * of contexts.
 * <p>
 * There are also static instances of DataReferenceContext's for the common
 * 'known' contexts in this class.
 * <p>
 * Note that contributed {@link IFieldContextResolverExtension} implementations
 * may choose to use their own context id's.
 * 
 * @author aallway
 * @since 19 Jun 2012
 */
public class DataReferenceContext {

    /** The context id. */
    private String contextId;

    /** The human readable label. */
    private String label;

    private String sortingKey;

    /**
     * @param contextId
     *            context id
     * @param label
     *            context label
     * @param sortingKey
     *            sorting key (to allow creating of categorised alpha sort order
     *            ).
     */
    public DataReferenceContext(String contextId, String label,
            String sortingKey) {
        super();
        this.contextId = contextId;
        this.label = label;
        this.sortingKey = sortingKey;
    }

    /**
     * @param contextId
     */
    public DataReferenceContext(String contextId, String label) {
        this(contextId, label, label);
    }

    /**
     * @param contextId
     * @param label
     */
    public DataReferenceContext(String contextId) {
        this(contextId, contextId);
    }

    /**
     * @return the data reference context id
     */
    public String getContextId() {
        return contextId;
    }

    /**
     * @return the data reference context label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the sortingKey that the instance was created with (label is
     *         default if not). This can be used for sorting when specific
     *         categorised sorting rather than alphabetic sorting is required
     */
    public String getSortingKey() {
        return sortingKey;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return contextId + "(" + label + ")"; //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * Use the context id as the hashcode because we would want separate
     * instances of {@link DataReferenceContext} with the smae context-id to be
     * equivalent in hash-sets etc
     * 
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return contextId.hashCode();
    }

    /**
     * Use the context id as the hashcode because we would want separate
     * instances of {@link DataReferenceContext} with the smae context-id to be
     * equivalent in hash-sets etc
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof DataReferenceContext) {
            return ((DataReferenceContext) obj).contextId.equals(contextId);
        }

        return false;
    }

    /**
     * The data is referenced in the activity implementation (other than in the
     * contexts already already listed above.
     * <p>
     * This is the data required for the internal task-type specific
     * implementation of the activity (such as the data used in the email
     * task-specific parts of activity, or user task and so on).
     */
    public static final DataReferenceContext CONTEXT_ACTIVITY_IMPLEMENTATION =
            new DataReferenceContext(
                    "taskImplementationData", Messages.DataReferenceContext_ActivityImplementation_label, "D_1_implementation"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in a script task script.
     */
    public static final DataReferenceContext CONTEXT_SCRIPT_TASK_SCRIPT =
            new DataReferenceContext(
                    "scriptTaskScript", Messages.DataReferenceContext_ScriptTaskScript_label, "D_1_scriptTaskScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the timer event script.
     */
    public static final DataReferenceContext CONTEXT_TIMER_EVENT_SCRIPT =
            new DataReferenceContext(
                    "timerEventScript", Messages.DataReferenceContext_TimerScript_label, "D_1_timerScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the reschedule timer event script.
     */
    public static final DataReferenceContext CONTEXT_RESCHEDULE_TIMER_EVENT_SCRIPT =
            new DataReferenceContext(
                    "rescheduleTimerEventScript", Messages.DataReferenceContext_RescheduleTimerEventScript_label, "D_1_timerScriptReschedule"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Generic Context for the data is referenced in an input (to service) or
     * output (from process) data or mapping-script (basically anything with
     * mapping direction "IN" (invoke service or reply from process).
     * <p>
     * You can also use {@link MappingInReferenceContext} class below to use
     * same context but provide a different label for different mapping
     * situations. The mapping contexts will still be considered equal.
     */
    public static final DataReferenceContext CONTEXT_MAPPING_IN =
            new DataReferenceContext(
                    "mappingIn", Messages.DataReferenceContext_MappingIn_label, "D_2_mappingIn"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Mapping In context class for situations where the basic context is
     * Direction="IN" mappings but where the label is different.
     * <p>
     * For instance, Direction="IN" on a service invoke is "input To Service"
     * whereas Direction="IN" on a process-as-service reply activity is
     * "Output From Process" because Direction is relative to the WSDL not the
     * process or the activity it's being used in (i.e. from process data into
     * WSDL data).
     * <p>
     * The important thing is that all MappingInContext's are comparable (equal
     * to) CONTEXT_MAPPING_IN and each other - because the contextId is the
     * same.
     * 
     * @author aallway
     * @since 4 Jul 2012
     */
    public static final class MappingInReferenceContext extends
            DataReferenceContext {
        public MappingInReferenceContext(String label) {
            super(CONTEXT_MAPPING_IN.getContextId(), label,
                    DataReferenceContext.CONTEXT_MAPPING_IN.getSortingKey());
        }
    }

    /**
     * The data is referenced in an output (from service) / input (to process)
     * mapping or mapping script (basically anything with mapping direction
     * "OUT" (reply from invoked service or request into process).
     * <p>
     * You can also use {@link MappingOutReferenceContext} class below to use
     * same context but provide a different label for different mapping
     * situations. The mapping contexts will still be considered equal.
     */
    public static final DataReferenceContext CONTEXT_MAPPING_OUT =
            new DataReferenceContext(
                    "mappingOut", Messages.DataReferenceContext_MappingOut_label, "D_3_mappingOut"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Mapping Out context class for situations where the basic context is
     * Direction="OUT" mappings but where the label is different.
     * <p>
     * For instance, Direction="OUT" on a service invoke is
     * "Output From Service" whereas Direction="OUT" on a process-as-service
     * incoming request activity is "Input To Process" because Direction is
     * relative to the WSDL not the process or the activity it's being used in
     * (i.e. from process data into WSDL data).
     * <p>
     * The important thing is that all MappingOutContext's are comparable (equal
     * to) CONTEXT_MAPPING_OUT and each other - because the contextId is the
     * same.
     * 
     * @author aallway
     * @since 4 Jul 2012
     */
    public static final class MappingOutReferenceContext extends
            DataReferenceContext {
        public MappingOutReferenceContext(String label) {
            super(CONTEXT_MAPPING_OUT.getContextId(), label,
                    DataReferenceContext.CONTEXT_MAPPING_OUT.getSortingKey());
        }
    }

    /**
     * The data is referenced as a dynamic sub-process task runtime identifier
     * field.
     */
    public static final DataReferenceContext CONTEXT_SUBPROC_RUNTIME_IDENTIFIER =
            new DataReferenceContext(
                    "runtimeIdentifier", Messages.DataReferenceContext_RuntimeIdField_label, "D_4_runtimeId"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced as in a calendar reference
     */
    public static final DataReferenceContext CONTEXT_CALENDAR_REFERENCE =
            new DataReferenceContext(
                    "calendarReference", Messages.DataReferenceContext_CalendarRef_label, "D_5_calendarRef"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced asan activity performer
     */
    public static final DataReferenceContext CONTEXT_ACTIVITY_PERFORMER =
            new DataReferenceContext(
                    "activityPerformer", Messages.DataReferenceContext_Performer_label, "D_5_performer"); //$NON-NLS-1$ //$NON-NLS-2$

    /** The data is explicitly associated with the activity. */
    public static final DataReferenceContext CONTEXT_EXPLICIT_ASSOCIATION =
            new DataReferenceContext(
                    "explicitAssociation", Messages.DataReferenceContext_ExplicitAssociation_label, "D_8_explicitAssoc"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity initiate script
     */
    public static final DataReferenceContext CONTEXT_INITIATE_SCRIPT =
            new DataReferenceContext(
                    "initiateScript", Messages.DataReferenceContext_InitiateScript_label, "S_1_initiateScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity complete script
     */
    public static final DataReferenceContext CONTEXT_COMPLETE_SCRIPT =
            new DataReferenceContext(
                    "completeScript", Messages.DataReferenceContext_CompleteScript_label, "S_2_completeScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity cancel script
     */
    public static final DataReferenceContext CONTEXT_CANCEL_SCRIPT =
            new DataReferenceContext(
                    "cancelScript", Messages.DataReferenceContext_CancelScript_label, "S_3_cancelScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity timeout script
     */

    public static final DataReferenceContext CONTEXT_TIMEOUT_SCRIPT =
            new DataReferenceContext(
                    "timeoutScript", Messages.DataReferenceContext_TimeoutScript_label, "S_4_timeoutScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in Adhoc Activities.
     */
    public static final DataReferenceContext CONTEXT_ADHOC_PRECONDITION =
            new DataReferenceContext(
                    "preconditionScript", Messages.DataReferenceContext_AdhocPreconditionScript_label, "S_5_preconditionScript"); //$NON-NLS-1$ //$NON-NLS-2$ 

    /**
     * The data is referenced in the activity work-manager schedule script
     */
    public static final DataReferenceContext CONTEXT_SCHEDULE_SCRIPT =
            new DataReferenceContext(
                    "scheduleScript", Messages.DataReferenceContext_ScheduleScript_label, "T_1a_scheduleScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity work-manager reschedule script
     */
    public static final DataReferenceContext CONTEXT_RESCHEDULE_SCRIPT =
            new DataReferenceContext(
                    "rescheduleScript", Messages.DataReferenceContext_RescheduleScript_label, "T_1b_rescheduleScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity work-manager open script
     */
    public static final DataReferenceContext CONTEXT_OPEN_SCRIPT =
            new DataReferenceContext(
                    "openScript", Messages.DataReferenceContext_OpenScript_label, "T_2_openScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity work-manager close script
     */
    public static final DataReferenceContext CONTEXT_CLOSE_SCRIPT =
            new DataReferenceContext(
                    "closeScript", Messages.DataReferenceContext_CloseScript_label, "T_3_closeScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in the activity work-manager submit script
     */
    public static final DataReferenceContext CONTEXT_SUBMIT_SCRIPT =
            new DataReferenceContext(
                    "submitScript", Messages.DataReferenceContext_SubmitScript_label, "T_4_submitScript"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in one of the activity loop scripts
     */
    public static final DataReferenceContext CONTEXT_LOOP_SCRIPTS =
            new DataReferenceContext(
                    "loopScripts", Messages.DataReferenceContext_LoopScripts_label, "U_1_loopScripts"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced in a sequence flow script.
     */
    public static final DataReferenceContext CONTEXT_SEQUENCEFLOW_SCRIPT =
            new DataReferenceContext(
                    "sequenceFlowScript", Messages.DataReferenceContext_ConditionalFlowScript_label, "T_1_flow"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced from process simulation properties.
     */
    public static final DataReferenceContext CONTEXT_SIMULATION_DATA =
            new DataReferenceContext(
                    "simualtionData", Messages.DataReferenceContext_SimulationData_label, "W_1_simulation"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The data is referenced from another process-interface method
     */
    public static final DataReferenceContext CONTEXT_INTERFACE_METHOD =
            new DataReferenceContext(
                    "interfaceEvent", Messages.DataReferenceContext_IntefaceEvent_label, "W_2_interfaceEvent"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Used where a data is referenced but no contextual information is
     * available (this can happen where a data reference resolver contribution
     * does not support the= new {@link IFieldContextResolverExtension}
     * interface.
     */
    public static final DataReferenceContext CONTEXT_UNKNOWN =
            new DataReferenceContext(
                    "unknown", Messages.DataReferenceContext_Unkown_label, "Z_9_unknown"); //$NON-NLS-1$ //$NON-NLS-2$

}
