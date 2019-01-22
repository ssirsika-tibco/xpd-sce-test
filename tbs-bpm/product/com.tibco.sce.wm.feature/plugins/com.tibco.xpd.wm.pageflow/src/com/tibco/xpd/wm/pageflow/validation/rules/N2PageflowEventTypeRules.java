/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.validation.rules;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * N2PageflowEventTypeRules
 * 
 * 
 * @author aallway
 * @since 3.3 (10 Nov 2009)
 */
public class N2PageflowEventTypeRules extends ProcessValidationRule {

    private static final String ISSUE_END_EVENT_NOT_SUPPORTED =
            "wm.pageflow.endEventTypeNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_INTERMEDIATE_INFLOW_EVENT_NOT_SUPPORTED =
            "wm.pageflow.intermediateInflowEventTypeNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_EVENTHANDLER_MUST_BE_CATCH_SIGNAL_OR_UNTRIGGERED =
            "wm.pageflow.eventHandlerMustBeCatchSignalOrUntriggered"; //$NON-NLS-1$

    private static final String ISSUE_USERTASK_BOUNDARYTIMER_NOTSUPPORTED =
            "wm.pageflow.userTaskBoundaryTimerNotSupported"; //$NON-NLS-1$

    private static final String ISSUE_EVENT_HANDLER_TYPE_NOT_SUPPORTED_IN_EMBEDDED_SUB_PROC =
            "wm.eventHandlerTypeNotSupportedInEmbeddedSubProc"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getEvent() instanceof StartEvent) {
                validateStartEvent(activity, (StartEvent) activity.getEvent());

            } else if (activity.getEvent() instanceof IntermediateEvent) {
                validateIntermediateEvent(activity,
                        (IntermediateEvent) activity.getEvent());

            } else if (activity.getEvent() instanceof EndEvent) {
                validateEndEvent(activity, (EndEvent) activity.getEvent());
            }
        }

        return;
    }

    /**
     * @param activity
     * @param event
     */
    private void validateStartEvent(Activity activity, StartEvent event) {
        return;
    }

    /**
     * @param activity
     * @param event
     */
    private void validateIntermediateEvent(Activity activity,
            IntermediateEvent event) {
        if (EventObjectUtil.isAttachedToTask(activity)) {
            validateTaskBoundaryIntermediateEvent(activity, event);
        } else {
            validateUnattachedIntermediateEvent(activity, event);
        }
        return;
    }

    /**
     * @param activity
     * @param event
     */
    private void validateTaskBoundaryIntermediateEvent(Activity activity,
            IntermediateEvent event) {

        if (TriggerType.TIMER_LITERAL.equals(event.getTrigger())) {
            /* Sid XPD-1930: user task boundary timer event is not supported. */
            Activity hostTask = EventObjectUtil.getTaskAttachedTo(activity);
            if (hostTask != null
                    && TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict(hostTask))) {
                addIssue(ISSUE_USERTASK_BOUNDARYTIMER_NOTSUPPORTED, activity);

            } else {
                /*
                 * SID XPD-1930: Parallel processing is now supported in
                 * Pageflow therefore continue on timeout boundary timer event
                 * is now supported.
                 */
            }
        }

    }

    /**
     * @param activity
     * @param event
     */
    private void validateUnattachedIntermediateEvent(Activity activity,
            IntermediateEvent event) {

        if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
            EventTriggerType eventTriggerType =
                    EventObjectUtil.getEventTriggerType(activity);

            if (activity.eContainer() instanceof ActivitySet
                    && !EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(eventTriggerType)) {
                addIssue(ISSUE_EVENT_HANDLER_TYPE_NOT_SUPPORTED_IN_EMBEDDED_SUB_PROC,
                        activity);
            } else if (!EventTriggerType.EVENT_NONE_LITERAL
                    .equals(eventTriggerType)
                    && !EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(eventTriggerType)
                    && !EventTriggerType.EVENT_CANCEL_LITERAL
                            .equals(eventTriggerType)) {
                /*
                 * XPD-6576: Only catch-signal/ catch-cancel / type-none event handlers are supported in pageflow processes.
                 */
                addIssue(ISSUE_EVENTHANDLER_MUST_BE_CATCH_SIGNAL_OR_UNTRIGGERED,
                        activity);
            }

        } else {
            boolean invalidType = false;

            if (TriggerType.MULTIPLE_LITERAL.equals(event.getTrigger())) {
                invalidType = true;
            } else if (TriggerType.CONDITIONAL_LITERAL.equals(event
                    .getTrigger())) {
                invalidType = true;
            }

            if (invalidType) {
                String typeName = getEventTriggerTypeName(activity);
                addIssue(ISSUE_INTERMEDIATE_INFLOW_EVENT_NOT_SUPPORTED,
                        activity,
                        Collections.singletonList(typeName));
            }
        }
        return;
    }

    /**
     * @param activity
     * @param event
     */
    private void validateEndEvent(Activity activity, EndEvent event) {
        boolean invalidType = false;

        if (ResultType.MULTIPLE_LITERAL.equals(event.getResult())) {
            invalidType = true;
        }

        if (invalidType) {
            String typeName = getEventTriggerTypeName(activity);
            addIssue(ISSUE_END_EVENT_NOT_SUPPORTED,
                    activity,
                    Collections.singletonList(typeName));
        }
        return;
    }

    /**
     * @param activity
     * @return internationalised event trigger type name.
     */
    private String getEventTriggerTypeName(Activity activity) {
        EventTriggerType type = EventObjectUtil.getEventTriggerType(activity);
        if (type != null) {
            String triggerName = type.toString();
            return triggerName;
        }
        return "?"; //$NON-NLS-1$
    }
}
