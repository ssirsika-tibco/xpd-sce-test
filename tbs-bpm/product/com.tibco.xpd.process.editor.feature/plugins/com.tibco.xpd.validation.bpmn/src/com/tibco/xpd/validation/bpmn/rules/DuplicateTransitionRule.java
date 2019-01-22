/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check for duplicate transitions.
 *
 * @author nwilson
 */
public class DuplicateTransitionRule extends FlowContainerValidationRule {

    /** The issue id. */
    private static final String ID = "bpmn.duplicateTransition"; //$NON-NLS-1$

    /**
     * @param process The process to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        Map<String, Map<String, List<Transition>>> fromToMap =
                new HashMap<String, Map<String, List<Transition>>>();
        List transitions = process.getTransitions();
        List<List<Transition>> duplicatesList =
                new ArrayList<List<Transition>>();
        for (Object next : transitions) {
            Transition transition = (Transition) next;
            if (!isConditional(transition)) {
                String from = transition.getFrom();
                String to = transition.getTo();
                Map<String, List<Transition>> toTransitionList =
                        fromToMap.get(from);
                if (toTransitionList == null) {
                    toTransitionList = new HashMap<String, List<Transition>>();
                    fromToMap.put(from, toTransitionList);
                }
                List<Transition> transitionList = toTransitionList.get(to);
                if (transitionList == null) {
                    transitionList = new ArrayList<Transition>();
                    toTransitionList.put(to, transitionList);
                    transitionList.add(transition);
                } else {
                    transitionList.add(transition);
                    duplicatesList.add(transitionList);
                }
            }
        }
        for (List<Transition> duplicates : duplicatesList) {
            for (Transition transition : duplicates) {
                addIssue(ID, transition);
            }
        }
    }

    /**
     * @param transition The transition to check.
     * @return true if conditional.
     */
    protected boolean isConditional(Transition transition) {
        boolean isConditional = false;
        Condition condition = transition.getCondition();
        if (condition != null) {
            if (ConditionType.CONDITION_LITERAL.equals(condition.getType())
                    || ConditionType.OTHERWISE_LITERAL.equals(condition
                            .getType())) {
                isConditional = true;
            }
        }
        return isConditional;
    }

}
