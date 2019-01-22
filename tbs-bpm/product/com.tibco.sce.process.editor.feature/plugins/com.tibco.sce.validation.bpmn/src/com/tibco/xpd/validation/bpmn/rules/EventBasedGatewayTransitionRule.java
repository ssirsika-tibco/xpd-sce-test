/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class EventBasedGatewayTransitionRule extends
        FlowContainerValidationRule {

    /** Invalid event based gateway transition ID. */
    private static final String INVALID_TARGET =
            "bpmn.invalidEventBasedGatewayTransition"; //$NON-NLS-1$

    /** Invalid event based gateway transition ID. */
    private static final String MIXED =
            "bpmn.invalidMixedTypesFromEventBasedGateway"; //$NON-NLS-1$

    /** Invalid event based gateway transition ID. */
    private static final String NO_START_EVENT =
            "bpmn.startEventUnusedForEventBasedGateway"; //$NON-NLS-1$

    /**
     * @param container
     *            The container to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(FlowContainer container) {
        boolean hasStartEvent = false;
        ArrayList<Activity> startGateways = new ArrayList<Activity>();
        ArrayList<Activity> others = new ArrayList<Activity>();
        for (Object next : container.getActivities()) {
            Activity activity = (Activity) next;
            if (activity.getEvent() instanceof StartEvent) {
                hasStartEvent = true;
            }
            Route route = activity.getRoute();
            if (route != null) {
                if (JoinSplitType.EXCLUSIVE_LITERAL.equals(Xpdl2ModelUtil
                        .safeGetGatewayType(activity))) {
                    ExclusiveType exclusiveType =
                            Xpdl2ModelUtil.safeGetExclusiveType(activity);
                    if (ExclusiveType.EVENT.equals(exclusiveType)) {
                        List<?> incoming = activity.getIncomingTransitions();
                        if (incoming.size() == 0) {
                            startGateways.add(activity);
                        } else if (incoming.size() == 1) {
                            Transition transition =
                                    (Transition) incoming.get(0);
                            String fromId = transition.getFrom();
                            Activity source = container.getActivity(fromId);
                            if (source == null
                                    || !(source.getEvent() instanceof StartEvent)) {
                                others.add(activity);
                            }
                        } else {
                            others.add(activity);
                        }
                        checkTransitions(container, activity);
                    }
                }
            }
        }

        // SID... UTB
        // "BPMN 1.2 : In a process with Start Events an Event-Based gateway with flows to Timer Intermediate Events must have a single incoming transition from a Start Event."
        // 
        // if (hasStartEvent) {
        // for (Activity gateway : startGateways) {
        // if (checkTransitionsFromStart(container, gateway)) {
        // addIssue(NO_START_EVENT, gateway);
        // }
        // }
        // }

        // SID... UTB
        // "An Event-based gateway with flows to Timer Intermediate Events must be the start of the process."
        // Which is complete tosh!
        //
        // for (Activity gateway : others) {
        // if (checkTransitionsFromStart(container, gateway)) {
        // addIssue(INVALID_TIMER, gateway);
        // }
        // }

    }

    /**
     * @param container
     *            The container to check.
     * @param activity
     *            The activity to check.
     */
    private void checkTransitions(FlowContainer container, Activity activity) {
        boolean hasReceiveTasks = false;
        boolean hasMessageEvents = false;
        boolean valid;
        for (Object next : activity.getOutgoingTransitions()) {
            Transition transition = (Transition) next;
            String to = transition.getTo();
            Activity target = container.getActivity(to);
            if (target != null) {
                valid = false;
                TaskType taskType = TaskObjectUtil.getTaskType(target);
                if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                    valid = true;
                    hasReceiveTasks = true;
                } else {
                    EventFlowType eventFlowType =
                            EventObjectUtil.getFlowType(target);
                    if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                            .equals(eventFlowType)) {
                        EventTriggerType eventTriggerType =
                                EventObjectUtil.getEventTriggerType(target);
                        switch (eventTriggerType.getValue()) {
                        case EventTriggerType.EVENT_MESSAGE_CATCH:
                            hasMessageEvents = true;
                            valid = true;
                            break;
                        case EventTriggerType.EVENT_TIMER:
                        case EventTriggerType.EVENT_CONDITIONAL:
                        case EventTriggerType.EVENT_SIGNAL_CATCH:
                        case EventTriggerType.EVENT_MULTIPLE_CATCH:
                            valid = true;
                            break;
                        }
                    }
                }
                if (!valid) {
                    addIssue(INVALID_TARGET, transition);
                }
            }
        }
        if (hasReceiveTasks && hasMessageEvents) {
            addIssue(MIXED, activity);
        }
    }

    /**
     * @param container
     *            The container to check.
     * @param activity
     *            The activity to check.
     * @return true if there are timer events.
     */
    private boolean checkTransitionsFromStart(FlowContainer container,
            Activity activity) {
        boolean hasTimers = false;
        for (Object next : activity.getOutgoingTransitions()) {
            Transition transition = (Transition) next;
            String to = transition.getTo();
            Activity target = container.getActivity(to);
            if (target != null) {
                Event event = target.getEvent();
                if (event instanceof IntermediateEvent) {
                    IntermediateEvent intermediate = (IntermediateEvent) event;
                    if (intermediate.getTriggerTimer() != null) {
                        hasTimers = true;
                    }
                }
            }
        }
        return hasTimers;
    }

}
