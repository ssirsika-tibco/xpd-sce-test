/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.TriggerTimer;

/**
 * Validate generic timer event configuration rules.
 * 
 * @author aallway
 * @since 14 Mar 2013
 */
public class SignalEventRule extends ProcessActivitiesValidationRule {

    /** Moved from now-removed SignalEventNameRule */
    private static final String ISSUE_THROW_SIGNAL_NAME_MANDATORY =
            "bpmn.signalEventNameMandatory2"; //$NON-NLS-1$

    /** No timer events to reschedule selected */
    private static final String ISSUE_RESCHEDULE_TIMERS_NONE_SELECTED =
            "bpmn.rescheduleTimersNoneSelected"; //$NON-NLS-1$

    /** Reschedule timers contains deleted events.. */
    private static final String ISSUE_RESCHEDULE_TIMER_DELETED =
            "bpmn.rescheduleTimerDeleted"; //$NON-NLS-1$

    /**
     * Reschedule timers contains events that are not timers or not attached to
     * task.
     */
    private static final String ISSUE_RESCHEDULE_TIMER_INVALID =
            "bpmn.rescheduleTimerInvalid"; //$NON-NLS-1$

    /**
     * Timer events that can be rescheduled via signal event(s) must have a
     * Reschedule Timer script defined.
     */
    private static final String ISSUE_RESCHEDULE_TIMER_NEEDS_SCRIPT =
            "bpmn.rescheduleTimerNeedsScript"; //$NON-NLS-1$

    /**
     * Cannot reschedule deadline timer event with none selected as activity
     * deadline.
     */
    private static final String ISSUE_RESCHEDULE_DEADLINE_TIMER_UNDEFINED =
            "bpmn.rescheduleDeadlineTimerNotDefined"; //$NON-NLS-1$

    /**
     * Cannot reschedule ALL timers when there are NO timers attached to task.
     */
    private static final String ISSUE_NO_TIMERS_TO_RESCHEDULE =
            "bpmn.rescheduleNoTimersDefined"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        if (EventObjectUtil.isLocalSignalEvent(activity)) {
            /*
             * XPD-7075: Show these validations only for Local Signal events.
             */
            if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                validateCatchSignal(activity);

            } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                validateThrowSignal(activity);

            }
        }
    }

    /**
     * Validate activity that's a throw signal event.
     * 
     * @param signalEvent
     */
    private void validateThrowSignal(Activity signalEvent) {
        String signalName = EventObjectUtil.getSignalName(signalEvent);

        if (signalName == null || signalName.length() == 0) {
            addIssue(ISSUE_THROW_SIGNAL_NAME_MANDATORY, signalEvent);
        }

    }

    /**
     * Validate activity that's a catch signal event.
     * 
     * @param signalEvent
     */
    private void validateCatchSignal(Activity signalEvent) {

        /* validate reschedule timer configuration. */
        valdiateRescheduleTimers(signalEvent);

    }

    /**
     * validate reschedule timer configuration
     * 
     * @param signalEvent
     */
    private void valdiateRescheduleTimers(Activity signalEvent) {
        SignalData signalData =
                EventObjectUtil.getSignalDataExtensionElement(signalEvent);

        if (signalData != null) {
            RescheduleTimers rescheduleTimers =
                    signalData.getRescheduleTimers();

            if (rescheduleTimers != null) {

                Collection<Activity> rescheduleTimerEvents =
                        EventObjectUtil.getRescheduleTimerEvents(signalEvent);

                boolean okSoFar = true;

                /* Rules for explicit user-selected timer events. */
                if (RescheduleTimerSelectionType.SELECTED
                        .equals(rescheduleTimers.getTimerSelectionType())) {

                    okSoFar =
                            validateExplicitTimerEventSelections(signalEvent,
                                    rescheduleTimers);

                } else if (RescheduleTimerSelectionType.DEADLINE
                        .equals(rescheduleTimers.getTimerSelectionType())) {

                    if (rescheduleTimerEvents.isEmpty()) {
                        addIssue(ISSUE_RESCHEDULE_DEADLINE_TIMER_UNDEFINED,
                                signalEvent);
                    }

                } else if (RescheduleTimerSelectionType.ALL
                        .equals(rescheduleTimers.getTimerSelectionType())) {
                    if (rescheduleTimerEvents.isEmpty()) {
                        addIssue(ISSUE_NO_TIMERS_TO_RESCHEDULE, signalEvent);
                    }
                }

                if (okSoFar) {
                    /*
                     * Check implicitly/explicitly defined timers have
                     * reschedule script defined.
                     */
                    for (Activity timerEvent : rescheduleTimerEvents) {
                        if (!hasRescheduleTimerScript(timerEvent)) {
                            addIssue(ISSUE_RESCHEDULE_TIMER_NEEDS_SCRIPT,
                                    timerEvent);
                        }
                    }
                }
            }
        }
    }

    /**
     * Validate that all explicitly selected timers are correct.
     * 
     * @param signalEvent
     * @param rescheduleTimers
     * 
     * @return <code>true</code> if ok, else <code>false</code> if problem
     *         raised.
     */
    private boolean validateExplicitTimerEventSelections(Activity signalEvent,
            RescheduleTimers rescheduleTimers) {
        boolean noProblemsFound = true;

        EList<ActivityRef> selectedTimerEvents =
                rescheduleTimers.getTimerEvents();

        /* Must be some selected... */
        if (selectedTimerEvents.isEmpty()) {
            addIssue(ISSUE_RESCHEDULE_TIMERS_NONE_SELECTED, signalEvent);
            noProblemsFound = false;

        } else {
            /* The selected ones must... */
            Activity taskAttachedTo =
                    EventObjectUtil.getTaskAttachedTo(signalEvent);

            for (ActivityRef timerEventRef : selectedTimerEvents) {
                Activity timerEvent = timerEventRef.getActivity();

                /* must still exist... */
                if (timerEvent == null) {
                    addIssue(ISSUE_RESCHEDULE_TIMER_DELETED, signalEvent);
                    noProblemsFound = false;
                }
                /* Must still be a timer trigger */
                else if (!EventTriggerType.EVENT_TIMER_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(timerEvent))) {
                    addIssue(ISSUE_RESCHEDULE_TIMER_INVALID, signalEvent);
                    noProblemsFound = false;
                }
                /* Must still be attached to same task as signal */
                else if (taskAttachedTo != null) {
                    if (!taskAttachedTo.equals(EventObjectUtil
                            .getTaskAttachedTo(timerEvent))) {
                        addIssue(ISSUE_RESCHEDULE_TIMER_INVALID, signalEvent);
                        noProblemsFound = false;
                    }
                }
            }
        }

        return noProblemsFound;
    }

    /**
     * @param timerEvent
     * 
     * @return <code>true</code> if the given timer event has a reschedule timer
     *         script.
     */
    private boolean hasRescheduleTimerScript(Activity timerEvent) {
        if (timerEvent.getEvent() != null) {

            if (timerEvent.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {
                TriggerTimer triggerTimer =
                        (TriggerTimer) timerEvent.getEvent()
                                .getEventTriggerTypeNode();

                RescheduleTimerScript script =
                        EventObjectUtil.getRescheduleTimerScript(timerEvent);

                if (script != null && !script.getMixed().isEmpty()) {
                    return true;
                }

            }
        }
        return false;
    }
}
