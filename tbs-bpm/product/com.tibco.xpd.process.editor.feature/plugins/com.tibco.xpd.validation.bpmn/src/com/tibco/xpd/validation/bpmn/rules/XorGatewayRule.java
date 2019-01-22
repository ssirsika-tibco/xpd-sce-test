/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author glewis
 */
public class XorGatewayRule extends ProcessValidationRule {

    /** XOR gateway issue ID. */
    public static final String ID = "bpmn.xorDataBasedGatewayError"; //$NON-NLS-1$

    /**
     * @param process
     *            The process.
     * @param activity
     *            The activity to check.
     */
    private void checkExclusiveDataBasedGatewayTransitions(Process process,
            Activity activity) {
        boolean hasOutputs = false;
        boolean hasMultipleOutputs = false;
        boolean hasNonConditionalOutputs = false;
        boolean hasDefault = false;
        boolean hasMultipleDefaults = false;
        String activityId = activity.getId();

        /*
         * Sid XPD-3044: Don't use process transitions (seq flows) because
         * activity may be in embedded sub-process (activitySet) which has it's
         * own transitions - use getFlowContainer() instead.
         */
        List<Transition> transitions =
                activity.getFlowContainer().getTransitions();
        for (Transition transition : transitions) {
            if (transition.getFrom().equals(activityId)) {
                if (hasOutputs) {
                    hasMultipleOutputs = true;
                } else {
                    hasOutputs = true;
                }
                Condition condition = transition.getCondition();
                if (condition != null) {
                    if (ConditionType.OTHERWISE_LITERAL.equals(condition
                            .getType())) {
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

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Object next : activities) {
            Activity activity = (Activity) next;
            Route route = activity.getRoute();
            if (route != null) {
                if (JoinSplitType.EXCLUSIVE_LITERAL.equals(Xpdl2ModelUtil
                        .safeGetGatewayType(activity))) {
                    ExclusiveType exclusiveType =
                            Xpdl2ModelUtil.safeGetExclusiveType(activity);
                    if (ExclusiveType.DATA.equals(exclusiveType)) {
                        checkExclusiveDataBasedGatewayTransitions(activity.getProcess(),
                                activity);
                    }
                }
            }
        }
    }
}
