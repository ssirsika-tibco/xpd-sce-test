/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check that if an Inclusive decision merge gateway has multiple output
 * sequence flows , then, 1 output sequence should be default flow and others
 * conditional
 * 
 * @author rsawant
 * @since 07-Nov-2012
 */
public class OrGatewayRule extends FlowContainerValidationRule {

    /** OR gateway issue ID. */
    public static final String ID = "bpmn.orGatewayWarning"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(com.tibco.xpd.xpdl2.FlowContainer)
     * 
     * @param container
     */
    @Override
    public void validate(FlowContainer container) {

        checkInclusiveGatewayTransitions(container);
    }

    /**
     * @param process
     *            The process to check
     */
    private void checkInclusiveGatewayTransitions(FlowContainer flowContainer) {

        EList<Activity> activities = flowContainer.getActivities();

        for (Activity activity : activities) {
            String activityId = activity.getId();
            Route route = activity.getRoute();
            if (route != null) {
                if (JoinSplitType.INCLUSIVE_LITERAL.equals(Xpdl2ModelUtil
                        .safeGetGatewayType(activity))) {
                    boolean hasOutputs = false, hasMultipleOutputs = false, hasNonConditionalOutputs =
                            false, hasDefault = false, hasMultipleDefaults =
                            false;
                    List<Transition> transitions =
                            flowContainer.getTransitions();

                    for (Transition transition : transitions) {
                        if (transition.getFrom().equals(activityId)) {
                            if (hasOutputs) {
                                hasMultipleOutputs = true;
                            } else {
                                hasOutputs = true;
                            }
                            Condition condition = transition.getCondition();
                            if (condition != null) {
                                if (ConditionType.OTHERWISE_LITERAL
                                        .equals(condition.getType())) {
                                    if (hasDefault) {
                                        hasMultipleDefaults = true;
                                    } else {
                                        hasDefault = true;
                                    }
                                } else if (!ConditionType.CONDITION_LITERAL
                                        .equals(condition.getType())) {
                                    hasNonConditionalOutputs = true;
                                }
                            } else {
                                hasNonConditionalOutputs = true;
                            }
                        }
                    }

                    if ((hasMultipleOutputs && (hasNonConditionalOutputs || !hasDefault))
                            || hasMultipleDefaults) {
                        addIssue(ID, activity);
                    }
                }
            }
        }

    }

}
