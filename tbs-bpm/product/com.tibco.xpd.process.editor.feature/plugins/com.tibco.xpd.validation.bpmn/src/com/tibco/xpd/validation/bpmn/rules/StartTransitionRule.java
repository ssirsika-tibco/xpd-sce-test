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
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class StartTransitionRule extends FlowContainerValidationRule {

    /** The issue ID. */
    public static final String ID = "bpmn.startTransition"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(
     *      FlowContainer)
     */
    @Override
    public void validate(FlowContainer process) {
        Map<String, Activity> starts = new HashMap<String, Activity>();
        List<String> startsWithInputs = new ArrayList<String>();
        List contents = process.eContents();
        for (Object next : contents) {
            if (next instanceof Activity) {
                Activity activity = (Activity) next;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    starts.put(activity.getId(), activity);
                }
            }
        }
        List transitions = process.getTransitions();
        for (Object next : transitions) {
            Transition transition = (Transition) next;
            String to = transition.getTo();
            if (starts.containsKey(to)) {
                if (!startsWithInputs.contains(to)) {
                    Activity activity = starts.get(to);
                    addIssue(ID, activity);
                    startsWithInputs.add(to);
                }
            }
        }
    }

}
