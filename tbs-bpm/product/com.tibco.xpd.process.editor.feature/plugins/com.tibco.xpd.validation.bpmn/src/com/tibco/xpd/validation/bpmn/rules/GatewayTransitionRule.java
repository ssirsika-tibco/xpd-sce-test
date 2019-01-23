/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check for exception transitions from a gateway.
 *
 * @author nwilson
 */
public class GatewayTransitionRule extends FlowContainerValidationRule {

    /** The issue ID. */
    public static final String ID = "bpmn.gatewayExceptionTransition"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        Map<String, Activity> gateways = new HashMap<String, Activity>();
        List contents = process.eContents();
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                Route route = activity.getRoute();
                if (route != null) {
                    gateways.put(activity.getId(), activity);
                }
            }
        }
        List transitions = process.getTransitions();
        for (Object next : transitions) {
            Transition transition = (Transition) next;
            String from = transition.getFrom();
            Condition condition = transition.getCondition();
            if (condition != null) {
                ConditionType type = condition.getType();
                if (ConditionType.EXCEPTION_LITERAL.equals(type)
                        || ConditionType.DEFAULTEXCEPTION_LITERAL.equals(type)) {
                    if (gateways.containsKey(from)) {
                        addIssue(ID, transition);
                    }
                }
            }
        }
    }

}
