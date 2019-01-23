/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.pageflow.rules;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * PageflowEventTypeRules
 * 
 * 
 * @author aallway
 * @since 3.3 (5 Nov 2009)
 */
public class PageflowEventTypeRules extends ProcessValidationRule {
    private static final String ISSUE_START_TYPE_NOT_SUPPORTED =
            "pageflow.startTypeNotSupported"; //$NON-NLS-1$ 

    private static final String ISSUE_INFLOW_INTERMEDIATE_TYPE_NOT_SUPPORTED =
            "pageflow.inflowIntermediateTypeNotSupported"; //$NON-NLS-1$ 

    private static final String ISSUE_TASKBOUNDARY_INTERMEDIATE_TYPE_NOT_SUPPORTED =
            "pageflow.taskBoundaryIntermediateTypeNotSupported"; //$NON-NLS-1$ 

    private static final String ISSUE_INFLOW_TIMER_NOT_SUPPORTED =
            "pageflow.inFlowTimersNotMakeSense"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        for (Activity activity : activities) {
            if (activity.getEvent() instanceof StartEvent) {
                validateStartEvent(activity, (StartEvent) activity.getEvent());

            } else if (activity.getEvent() instanceof EndEvent) {
                validateEndEvent(activity, (EndEvent) activity.getEvent());

            } else if (activity.getEvent() instanceof IntermediateEvent) {
                if (EventObjectUtil.isAttachedToTask(activity)) {
                    validateTaskBoundaryIntermediateEvent(activity,
                            (IntermediateEvent) activity.getEvent());

                } else {
                    validateInFlowIntermediateEvent(activity,
                            (IntermediateEvent) activity.getEvent());
                }
            }
        }

        return;
    }

    /**
     * Validate start event. Only trigger type none (in general) and catch
     * signal start events in event subprocess are supported
     * 
     * @param activity
     * @param event
     */
    private void validateStartEvent(Activity activity, StartEvent event) {

        /*
         * XPD-6718: Saket: Allow the usage of
         * "catch signal start event subprocess" in pageflows.
         */
        if (!EventObjectUtil.isEventSubProcessStartEvent(activity)) {
            /*
             * For normal start events, just check for type NONE.
             */
            if (!TriggerType.NONE_LITERAL.equals(event.getTrigger())) {
                addIssue(ISSUE_START_TYPE_NOT_SUPPORTED,
                        activity,
                        Collections
                                .singletonList(getEventTriggerTypeName(activity)));
            }
        }

        return;
    }

    /**
     * Validate End Event - ciurrently all types supported.
     * 
     * @param activity
     * @param event
     */
    private void validateEndEvent(Activity activity, EndEvent event) {
        return;
    }

    /**
     * Validate Intermediate event
     * 
     * @param activity
     * @param event
     */
    private void validateInFlowIntermediateEvent(Activity activity,
            IntermediateEvent event) {

        /*
         * Check ISSUE_INFLOW_INTERMEDIATE_TYPE_NOT_SUPPORTED
         */
        boolean valid = false;
        int triggerType = event.getTrigger().getValue();

        switch (triggerType) {
        case TriggerType.LINK:
        case TriggerType.SIGNAL:
        case TriggerType.COMPENSATION:
        case TriggerType.CONDITIONAL:
        case TriggerType.MULTIPLE:
            valid = true;
            break;

        case TriggerType.ERROR:
        case TriggerType.CANCEL:
            /*
             * Note that whilst error and cancel are not really valid, WE will
             * consider them valid for our purposes as they are already covered
             * by the rule that says they cannot be used inflow anyway
             */
            valid = true;
            break;

        case TriggerType.TIMER:
            /*
             * Note that whilst timer is supported by the pageflow runner, it
             * doesn't make sense to pause between pages in a pageflow, so we'll
             * consider it valid for 'not supported' and treat with a different
             * error below
             */
            valid = true;
            break;

        case TriggerType.MESSAGE:
            if (event.getTriggerResultMessage() != null) {
                if (CatchThrow.THROW.equals(event.getTriggerResultMessage()
                        .getCatchThrow())) {
                    valid = true;
                }
            }
            break;
        }

        if (!valid) {
            /* Ignore event handler activities */
            if (!Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                addIssue(ISSUE_INFLOW_INTERMEDIATE_TYPE_NOT_SUPPORTED,
                        activity,
                        Collections
                                .singletonList(getEventTriggerTypeName(activity)));
            }
        }

        /*
         * Check ISSUE_INFLOW_TIMER_NOT_SUPPORTED
         */
        if (TriggerType.TIMER_LITERAL.equals(event.getTrigger())) {
            addIssue(ISSUE_INFLOW_TIMER_NOT_SUPPORTED, activity);
        }

        return;
    }

    /**
     * Validate Intermediate event that is attached to a task's boundary.
     * 
     * @param activity
     * @param event
     */
    private void validateTaskBoundaryIntermediateEvent(Activity activity,
            IntermediateEvent event) {
        boolean valid = false;
        int triggerType = event.getTrigger().getValue();

        switch (triggerType) {
        case TriggerType.ERROR:
        case TriggerType.COMPENSATION:
        case TriggerType.TIMER:
        case TriggerType.CONDITIONAL:
        case TriggerType.SIGNAL:
        case TriggerType.MULTIPLE:
        case TriggerType.CANCEL:
            valid = true;
            break;
        }

        if (!valid) {
            addIssue(ISSUE_TASKBOUNDARY_INTERMEDIATE_TYPE_NOT_SUPPORTED,
                    activity,
                    Collections
                            .singletonList(getEventTriggerTypeName(activity)));
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
