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

package com.tibco.xpd.processwidget.adapters;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorObjectType;
import com.tibco.xpd.processwidget.internal.Messages;

public final class EventTriggerType {
    public static final int EVENT_NONE = 0;

    public static final int EVENT_MESSAGE_CATCH = 1;

    public static final int EVENT_MESSAGE_THROW = 2;

    public static final int EVENT_TIMER = 3;

    public static final int EVENT_ERROR = 4;

    public static final int EVENT_CANCEL = 5;

    public static final int EVENT_COMPENSATION_CATCH = 6;

    public static final int EVENT_COMPENSATION_THROW = 7;

    public static final int EVENT_CONDITIONAL = 8;

    public static final int EVENT_MULTIPLE_CATCH = 9;

    public static final int EVENT_MULTIPLE_THROW = 10;

    public static final int EVENT_TERMINATE = 11;

    public static final int EVENT_SIGNAL_CATCH = 12;

    public static final int EVENT_SIGNAL_THROW = 13;

    public static final int EVENT_LINK_CATCH = 14;

    public static final int EVENT_LINK_THROW = 15;

    public static final EventTriggerType EVENT_MESSAGE_CATCH_LITERAL =
            new EventTriggerType(EVENT_MESSAGE_CATCH);

    public static final EventTriggerType EVENT_MESSAGE_THROW_LITERAL =
            new EventTriggerType(EVENT_MESSAGE_THROW);

    public static final EventTriggerType EVENT_TIMER_LITERAL =
            new EventTriggerType(EVENT_TIMER);

    public static final EventTriggerType EVENT_ERROR_LITERAL =
            new EventTriggerType(EVENT_ERROR);

    public static final EventTriggerType EVENT_CANCEL_LITERAL =
            new EventTriggerType(EVENT_CANCEL);

    public static final EventTriggerType EVENT_COMPENSATION_CATCH_LITERAL =
            new EventTriggerType(EVENT_COMPENSATION_CATCH);

    public static final EventTriggerType EVENT_COMPENSATION_THROW_LITERAL =
            new EventTriggerType(EVENT_COMPENSATION_THROW);

    public static final EventTriggerType EVENT_CONDITIONAL_LITERAL =
            new EventTriggerType(EVENT_CONDITIONAL);

    public static final EventTriggerType EVENT_MULTIPLE_CATCH_LITERAL =
            new EventTriggerType(EVENT_MULTIPLE_CATCH);

    public static final EventTriggerType EVENT_MULTIPLE_THROW_LITERAL =
            new EventTriggerType(EVENT_MULTIPLE_THROW);

    public static final EventTriggerType EVENT_TERMINATE_LITERAL =
            new EventTriggerType(EVENT_TERMINATE);

    public static final EventTriggerType EVENT_NONE_LITERAL =
            new EventTriggerType(EVENT_NONE);

    public static final EventTriggerType EVENT_SIGNAL_CATCH_LITERAL =
            new EventTriggerType(EVENT_SIGNAL_CATCH);

    public static final EventTriggerType EVENT_SIGNAL_THROW_LITERAL =
            new EventTriggerType(EVENT_SIGNAL_THROW);

    public static final EventTriggerType EVENT_LINK_CATCH_LITERAL =
            new EventTriggerType(EVENT_LINK_CATCH);

    public static final EventTriggerType EVENT_LINK_THROW_LITERAL =
            new EventTriggerType(EVENT_LINK_THROW);

    private final int type;

    private final String name;

    private EventTriggerType(int type) {
        this.type = type;
        switch (type) {
        case EVENT_NONE:
            name = Messages.EventTriggerType_Untriggered_label;
            break;
        case EVENT_CANCEL:
            name = Messages.EventTriggerType_Cancel_label;
            break;
        case EVENT_COMPENSATION_CATCH:
            name = Messages.EventTriggerType_CatchCompensation_label;
            break;
        case EVENT_COMPENSATION_THROW:
            name = Messages.EventTriggerType_ThrowCompensation_label;
            break;
        case EVENT_ERROR:
            name = Messages.EventTriggerType_Error_label;
            break;
        case EVENT_MESSAGE_CATCH:
            name = Messages.EventTriggerType_Message_Catch_label;
            break;
        case EVENT_MESSAGE_THROW:
            name = Messages.EventTriggerType_Message_Throw_label;
            break;
        case EVENT_MULTIPLE_CATCH:
            name = Messages.EventTriggerType_CatchMultiple_label;
            break;
        case EVENT_MULTIPLE_THROW:
            name = Messages.EventTriggerType_ThrowMultiple_label;
            break;
        case EVENT_CONDITIONAL:
            name = Messages.EventTriggerType_Conditional_label;
            break;
        case EVENT_TERMINATE:
            name = Messages.EventTriggerType_Terminate_label;
            break;
        case EVENT_TIMER:
            name = Messages.EventTriggerType_Timer_label;
            break;
        case EVENT_SIGNAL_CATCH:
            name = Messages.EventTriggerType_Signal_Catch_label;
            break;
        case EVENT_SIGNAL_THROW:
            name = Messages.EventTriggerType_Signal_Throw_label;
            break;
        case EVENT_LINK_CATCH:
            name = Messages.EventTriggerType_Link_Catch_label;
            break;
        case EVENT_LINK_THROW:
            name = Messages.EventTriggerType_Link_Throw_label;
            break;
        default:
            throw new IllegalArgumentException();
        }
    }

    public int getValue() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the proceee editor object type for this THIS {@link EventTriggerType}
     * when combined with the given {@link EventFlowType}
     * (start/intermediate/end)
     * 
     * @param flowType
     * 
     * @return {@link ProcessEditorObjectType}
     */
    public ProcessEditorObjectType getProcessEditorObjectType(
            EventFlowType flowType) {
        ProcessEditorObjectType objectType = null;

        if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
            switch (type) {
            case EVENT_NONE:
                objectType = ProcessEditorObjectType.start_event_none;
                break;
            case EVENT_MESSAGE_CATCH:
                objectType = ProcessEditorObjectType.start_event_message;
                break;
            case EVENT_MULTIPLE_CATCH:
                objectType = ProcessEditorObjectType.start_event_multi;
                break;
            case EVENT_CONDITIONAL:
                objectType = ProcessEditorObjectType.start_event_conditional;
                break;
            case EVENT_TIMER:
                objectType = ProcessEditorObjectType.start_event_timer;
                break;
            case EVENT_SIGNAL_CATCH:
                objectType = ProcessEditorObjectType.start_event_signal;
                break;
            }

        } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(flowType)) {
            switch (type) {
            case EVENT_NONE:
                objectType = ProcessEditorObjectType.intermediate_event_none;
                break;
            case EVENT_CANCEL:
                objectType =
                        ProcessEditorObjectType.intermediate_event_cancel_catch;
                break;
            case EVENT_COMPENSATION_CATCH:
                objectType =
                        ProcessEditorObjectType.intermediate_event_compensation_catch;
                break;
            case EVENT_COMPENSATION_THROW:
                objectType =
                        ProcessEditorObjectType.intermediate_event_compensation_throw;
                break;
            case EVENT_ERROR:
                objectType =
                        ProcessEditorObjectType.intermediate_event_error_catch;
                break;
            case EVENT_MESSAGE_CATCH:
                objectType =
                        ProcessEditorObjectType.intermediate_event_message_catch;
                break;
            case EVENT_MESSAGE_THROW:
                objectType =
                        ProcessEditorObjectType.intermediate_event_message_throw;
                break;
            case EVENT_MULTIPLE_CATCH:
                objectType =
                        ProcessEditorObjectType.intermediate_event_multi_catch;
                break;
            case EVENT_MULTIPLE_THROW:
                objectType =
                        ProcessEditorObjectType.intermediate_event_multi_throw;
                break;
            case EVENT_CONDITIONAL:
                objectType =
                        ProcessEditorObjectType.intermediate_event_conditional;
                break;
            case EVENT_TIMER:
                objectType = ProcessEditorObjectType.intermediate_event_timer;
                break;
            case EVENT_SIGNAL_CATCH:
                objectType =
                        ProcessEditorObjectType.intermediate_event_signal_catch;
                break;
            case EVENT_SIGNAL_THROW:
                objectType =
                        ProcessEditorObjectType.intermediate_event_signal_throw;
                break;
            case EVENT_LINK_CATCH:
                objectType =
                        ProcessEditorObjectType.intermediate_event_link_catch;
                break;
            case EVENT_LINK_THROW:
                objectType =
                        ProcessEditorObjectType.intermediate_event_link_throw;
                break;
            }

        } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
            switch (type) {
            case EVENT_NONE:
                objectType = ProcessEditorObjectType.end_event_none;
                break;
            case EVENT_CANCEL:
                objectType = ProcessEditorObjectType.end_event_cancel;
                break;
            case EVENT_COMPENSATION_THROW:
                objectType = ProcessEditorObjectType.end_event_compensation;
                break;
            case EVENT_ERROR:
                objectType = ProcessEditorObjectType.end_event_error;
                break;
            case EVENT_MESSAGE_THROW:
                objectType = ProcessEditorObjectType.end_event_message;
                break;
            case EVENT_MULTIPLE_THROW:
                objectType = ProcessEditorObjectType.end_event_multi;
                break;
            case EVENT_TERMINATE:
                objectType = ProcessEditorObjectType.end_event_terminate;
                break;
            case EVENT_SIGNAL_THROW:
                objectType = ProcessEditorObjectType.end_event_signal;
                break;
            }
        }

        return objectType;
    }

}
