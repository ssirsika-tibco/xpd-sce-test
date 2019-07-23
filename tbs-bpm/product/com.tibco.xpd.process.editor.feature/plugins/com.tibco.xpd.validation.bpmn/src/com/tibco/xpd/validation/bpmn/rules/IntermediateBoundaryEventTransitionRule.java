/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author nwilson
 */
public class IntermediateBoundaryEventTransitionRule extends
        FlowContainerValidationRule {

    /** Incoming transition issue ID. */
    private static final String INCOMING = "bpmn.boundaryEventInboundFlow"; //$NON-NLS-1$

    /** Outgoing transition issue ID. */
    private static final String OUTGOING = "bpmn.boundaryEventOutboundFlow"; //$NON-NLS-1$

    private static final String EVENT_SHOULD_BE_ATTACHED =
            "bpmn.eventMustBeBoundaryEvent"; //$NON-NLS-1$

    private static final String EVENT_SHOULD_NOT_BE_ATTACHED =
            "bpmn.eventMustNotBeBoundaryEvent"; //$NON-NLS-1$

    /** Compensation event outgoing transition issue ID. */
    private static final String OUTGOING_COMPENSATION =
            "bpmn.compensationBoundaryEventOutboundFlow"; //$NON-NLS-1$

    /** Invalid boundary event issue ID. */
    private static final String INVALID_BOUNDARY_EVENT =
            "bpmn.invalidBoundaryEvent"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        boolean eventShouldBeAttached = false, eventShouldNotBeAttached = false;
        for (Object next : process.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();

            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;

                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);

                switch (eventTriggerType.getValue()) {
                case EventTriggerType.EVENT_COMPENSATION_CATCH:
                    /*
                     * There is a separate rule for "cancel must be on
                     * transaction embedded case EventTriggerType.EVENT_CANCEL:
                     */
                case EventTriggerType.EVENT_ERROR:
                    eventShouldBeAttached = true;
                    break;
                default:
                    eventShouldBeAttached = false;
                }

                if (eventShouldBeAttached && intermediate.getTarget() == null) {
                    addIssue(EVENT_SHOULD_BE_ATTACHED, activity);
                    continue;
                }
                if (intermediate.getTarget() != null) {

                    /* ACE-2014: In SCE incoming request event (EVENT_NONE) boundary event is now allowed. */
                    switch (eventTriggerType.getValue()) {
                    case EventTriggerType.EVENT_COMPENSATION_THROW:
                    case EventTriggerType.EVENT_MULTIPLE_THROW:
                    case EventTriggerType.EVENT_MESSAGE_THROW:
                    case EventTriggerType.EVENT_SIGNAL_THROW:
                    case EventTriggerType.EVENT_LINK_THROW:
                        addIssue(EVENT_SHOULD_NOT_BE_ATTACHED, activity);
                        continue;
                    case EventTriggerType.EVENT_LINK_CATCH:
                        addIssue(INVALID_BOUNDARY_EVENT, activity);
                        continue;
                    }

                    List<Transition> incoming =
                            activity.getIncomingTransitions();
                    List<Transition> outgoing =
                            activity.getOutgoingTransitions();
                    if (incoming.size() > 0) {
                        addIssue(INCOMING, activity);
                    }
                    if (TriggerType.COMPENSATION_LITERAL.equals(intermediate
                            .getTrigger())) {
                        if (outgoing.size() > 0) {
                            for (Transition transition : outgoing) {
                                addIssue(OUTGOING_COMPENSATION, transition);
                            }
                        }
                    } else if (!EventObjectUtil.isNonCancellingEvent(activity)) {
                        if (outgoing.size() != 1) {
                            addIssue(OUTGOING, activity);
                        }
                    }
                }
            }
        }
    }

}
