/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.ConditionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class SingleTransitionRule extends ProcessValidationRule {

    /** Invalid single transition issue ID. */
    public static final String ID = "sim.invalidSingleTranstion"; //$NON-NLS-1$

    /**
     * @param transition The transition to check.
     * @return true if the transitional is not conditional.
     */
    protected boolean isUnConditional(Transition transition) {
        boolean isUnConditional = true;
        Condition condition = transition.getCondition();
        if (condition != null) {
            if (ConditionType.CONDITION_LITERAL.equals(condition.getType())
                    || ConditionType.OTHERWISE_LITERAL.equals(condition
                            .getType())
                    || ConditionType.EXCEPTION_LITERAL.equals(condition
                            .getType())) {
                isUnConditional = false;
            }
        }
        return isUnConditional;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {        
        for (Object next : activities) {
            Activity activity = (Activity) next;
            List actTransitionList = activity.getOutgoingTransitions();
            if (actTransitionList.size() == 1) {
                Transition singleTran = (Transition) actTransitionList.get(0);
                if (!isUnConditional(singleTran)) {
                    addIssue(ID, singleTran);
                }
            }
        }
        
    }
}
