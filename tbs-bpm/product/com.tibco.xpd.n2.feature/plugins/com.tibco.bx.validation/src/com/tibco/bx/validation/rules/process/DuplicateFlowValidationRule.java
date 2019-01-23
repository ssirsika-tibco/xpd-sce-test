/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule which raises an error when there are multiple flows between two
 * activities in same direction regardless of flow type.
 * 
 * @author aprasad
 * @since 15-Sep-2014
 */
public class DuplicateFlowValidationRule extends FlowContainerValidationRule {

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer flowContainer) {

        /*
         * Map contains mapping of the source , map of target and the list of
         * transitions from the source to that target to handle duplicates
         */

        Map<String, Map<String, List<Transition>>> srcTargetAndTransitions =
                new HashMap<String, Map<String, List<Transition>>>();

        /* All transitions in the process */
        List<Transition> transitions = flowContainer.getTransitions();

        List<List<Transition>> duplicatesList =
                new ArrayList<List<Transition>>();

        /* Check each transition */
        for (Object next : transitions) {

            Transition transition = (Transition) next;

            String src = transition.getFrom();
            String target = transition.getTo();

            /* Map of target and Transitions for this source */
            Map<String, List<Transition>> toTransitionList =
                    srcTargetAndTransitions.get(src);

            /* create entry for this source if does not exist */
            if (toTransitionList == null) {

                toTransitionList = new HashMap<String, List<Transition>>();

                srcTargetAndTransitions.put(src, toTransitionList);
            }

            List<Transition> transitionList = toTransitionList.get(target);

            if (transitionList == null) {
                /* create entry for this target if does not exist */
                transitionList = new ArrayList<Transition>();
                toTransitionList.put(target, transitionList);
                transitionList.add(transition);

            } else {
                /* if exists add to the list and collect as Duplicate */
                transitionList.add(transition);
                /* collect if not already collected */
                if (!duplicatesList.contains(transitionList)) {

                    duplicatesList.add(transitionList);
                }
            }

        }
        /* for each duplicate transition in the list raise error */
        for (List<Transition> duplicates : duplicatesList) {

            for (Transition transition : duplicates) {

                addIssue("bx.activityDuplicateFlowNotAllowed", transition); //$NON-NLS-1$
            }
        }
    }

}
