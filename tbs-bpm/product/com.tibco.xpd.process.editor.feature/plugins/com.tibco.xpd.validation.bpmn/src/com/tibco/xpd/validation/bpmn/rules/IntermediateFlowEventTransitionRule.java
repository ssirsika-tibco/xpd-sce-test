/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author nwilson
 */
public class IntermediateFlowEventTransitionRule extends
        FlowContainerValidationRule {

    /** Optional inbound flow issue ID. */
    private static final String MAX_ONE_INCOMING_FLOW =
            "bpmn.optionalInboundFlow"; //$NON-NLS-1$

    /** Single outbound flow issue ID. */
    private static final String SINGLE_OUTBOUND_FLOW =
            "bpmn.singleOutboundFlow"; //$NON-NLS-1$

    private static final String THROWLINK_MUST_HAVE_IN =
            "bpmn.throwLinkMustHaveIn"; //$NON-NLS-1$

    private static final String CATCHLINK_MUST_HAVE_OUT =
            "bpmn.catchLinkMustHaveOut"; //$NON-NLS-1$

    private static final String THROWLINK_CANT_HAVE_OUT =
            "bpmn.throwLinkCantHaveOut"; //$NON-NLS-1$

    private static final String CATCHLINK_CANT_HAVE_IN =
            "bpmn.catchLinkCantHaveIn"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        for (Object next : process.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;
                if (intermediate.getTarget() == null) {
                    TriggerType trigger = intermediate.getTrigger();
                    if (trigger != null) {
                        List incoming = activity.getIncomingTransitions();
                        List outgoing = activity.getOutgoingTransitions();
                        int triggerValue = trigger.getValue();
                        if (triggerValue == TriggerType.LINK) {
                            CatchThrow type =
                                    intermediate.getTriggerResultLink()
                                            .getCatchThrow();

                            if (CatchThrow.THROW.equals(type)) {
                                if (incoming.size() != 1) {
                                    addIssue(THROWLINK_MUST_HAVE_IN, activity);
                                }

                                if (outgoing.size() != 0) {
                                    addIssue(THROWLINK_CANT_HAVE_OUT, activity);
                                }

                            } else {
                                if (outgoing.size() != 1) {
                                    addIssue(CATCHLINK_MUST_HAVE_OUT, activity);
                                }

                                if (incoming.size() != 0) {
                                    addIssue(CATCHLINK_CANT_HAVE_IN, activity);
                                }
                            }

                        } else {
                            if (incoming.size() > 1) {
                                addIssue(MAX_ONE_INCOMING_FLOW, activity);
                            }

                            if (outgoing.size() != 1) {
                                addIssue(SINGLE_OUTBOUND_FLOW, activity);
                            }
                        }
                    }
                }
            }
        }
    }

}
