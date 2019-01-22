/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

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
 * Conditional transitions cannot come from event-based exclusive gateways,
 * parallel gateways, complex gateways, start events or intermediate events.
 * 
 * @author nwilson
 */
public class ConditionalTransitionRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String ID = "bpmn.conditionalTransitionSource"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {
        for (Object next : container.getTransitions()) {
            Transition transition = (Transition) next;
            if (transition.getCondition() != null) {
                String from = transition.getFrom();
                Activity activity = container.getActivity(from);
                if (activity != null) {
                    checkActivity(activity, transition);
                }
            }
        }
    }

    /**
     * Checks that the source activity is a valid type.
     * 
     * @param activity
     *            The activity to check.
     * @param transition
     *            The transition to check.
     */
    private void checkActivity(Activity activity, Transition transition) {
        Route route = activity.getRoute();
        if (route != null) {
            JoinSplitType type = Xpdl2ModelUtil.safeGetGatewayType(activity);
            ExclusiveType exclusiveType =
                    Xpdl2ModelUtil.safeGetExclusiveType(activity);
            if (JoinSplitType.EXCLUSIVE_LITERAL.equals(type)) {
                if (ExclusiveType.EVENT.equals(exclusiveType)) {
                    addIssue(ID, transition);
                }
            }
            if (JoinSplitType.COMPLEX_LITERAL.equals(type)) {
                addIssue(ID, transition);
            }
            if (JoinSplitType.PARALLEL_LITERAL.equals(type)) {
                addIssue(ID, transition);
            }
        }
        Event event = activity.getEvent();
        if (event != null) {
            if (event instanceof StartEvent
                    || event instanceof IntermediateEvent) {
                addIssue(ID, transition);
            }
        }
    }

}
