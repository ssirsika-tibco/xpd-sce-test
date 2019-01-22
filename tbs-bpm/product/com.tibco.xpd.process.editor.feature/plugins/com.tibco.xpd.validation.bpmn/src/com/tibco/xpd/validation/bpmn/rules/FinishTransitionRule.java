/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check that there aren't any transitions from an end event.
 *
 * @author nwilson
 */
public class FinishTransitionRule extends FlowContainerValidationRule {

    /** The issue id. */
    public static final String ID = "bpmn.finishTransition"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        Map<String, Activity> finishes = new HashMap<String, Activity>();
        List<String> finishesWithOutputs = new ArrayList<String>();
        List contents = process.eContents();
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                Event event = activity.getEvent();
                if (event instanceof EndEvent) {
                    finishes.put(activity.getId(), activity);
                }
            }
        }
        List transitions = process.getTransitions();
        for (Object next : transitions) {
            Transition transition = (Transition) next;
            String from = transition.getFrom();
            if (finishes.containsKey(from)) {
                if (!finishesWithOutputs.contains(from)) {
                    Activity activity = finishes.get(from);
                    addIssue(ID, activity);
                    finishesWithOutputs.add(from);
                }
            }
        }
    }

}
