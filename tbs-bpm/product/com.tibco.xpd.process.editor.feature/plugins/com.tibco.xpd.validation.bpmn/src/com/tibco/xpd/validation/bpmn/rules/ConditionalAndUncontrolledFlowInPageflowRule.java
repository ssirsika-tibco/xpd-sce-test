/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mixed use of Conditional and Uncontrolled outgoing flow is not recommended in
 * pageflow processes (can cause issues in downstream merges).
 * 
 * @author sajain
 * @since Mar 17, 2016
 */
public class ConditionalAndUncontrolledFlowInPageflowRule extends
        FlowContainerValidationRule {

    /**
     * Issue ID.
     */
    private static final String ID =
            "bpmn.conditionalAndUncontrolledFlowInPageflowRule"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {

        if (container instanceof Process) {

            Process proc = (Process) container;

            if (Xpdl2ModelUtil.isPageflowOrSubType(proc)) {

                for (Object next : container.getTransitions()) {

                    Transition transition = (Transition) next;

                    if (transition.getCondition() != null) {

                        String from = transition.getFrom();
                        Activity activity = container.getActivity(from);

                        if (activity != null) {

                            List<Transition> allOutgoingTransitions =
                                    new ArrayList<Transition>();

                            allOutgoingTransitions.addAll(activity
                                    .getOutgoingTransitions());

                            for (Transition eachOutgoingTransition : allOutgoingTransitions) {

                                if (eachOutgoingTransition.getCondition() == null) {

                                    addIssue(ID, activity);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
