/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.HashMap;
import java.util.Map;

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
 * Checks that gateways are exclusive data based or parallel and that parallel
 * gateways have unconditional outputs.
 * 
 * @author nwilson
 */
public class GatewayTypeRule extends ProcessValidationRule {

    /** Exclusive data based or parallel gateway issue. */
    public static final String ID = "sim.invalidGatewayType"; //$NON-NLS-1$

    /** Parallel gateway condiional outgoing transition issue ID. */
    public static final String ID_PARALLEL = "sim.parallelGatewayTransition"; //$NON-NLS-1$

    private boolean isSupportedGatewayType(Activity activity) {
        JoinSplitType gatewayType = Xpdl2ModelUtil.safeGetGatewayType(activity);
        ExclusiveType exclusiveType =
                Xpdl2ModelUtil.safeGetExclusiveType(activity);
        Xpdl2ModelUtil.safeGetExclusiveType(activity);
        return (JoinSplitType.EXCLUSIVE_LITERAL == gatewayType && ExclusiveType.DATA
                .equals(exclusiveType))
                || JoinSplitType.PARALLEL_LITERAL == gatewayType;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        if (process != null) {
            Map<String, Activity> parallels = new HashMap<String, Activity>();
            for (Object next : activities) {
                Activity activity = (Activity) next;
                Route route = activity.getRoute();
                if (route != null) {
                    JoinSplitType gatewayType =
                            Xpdl2ModelUtil.safeGetGatewayType(activity);
                    if (JoinSplitType.PARALLEL_LITERAL.equals(gatewayType)) {
                        parallels.put(activity.getId(), activity);
                    }
                    if (!isSupportedGatewayType(activity)) {
                        addIssue(ID, activity);
                    }
                }
            }
            if (transitions != null) {
                for (Object next : transitions) {
                    Transition transition = (Transition) next;
                    String from = transition.getFrom();
                    Condition condition = transition.getCondition();
                    if (condition != null) {
                        if (!ConditionType.EXCEPTION_LITERAL.equals(condition
                                .getType())
                                && !ConditionType.DEFAULTEXCEPTION_LITERAL
                                        .equals(condition.getType())) {
                            if (parallels.containsKey(from)) {
                                addIssue(ID_PARALLEL, transition);
                            }
                        }
                    }
                }
            }
        }
    }
}
