/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlTransitionType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.Loop;
import com.tibco.xpd.validation.xpdl2.tools.LoopAnalyser;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class LoopExitStrategyRule extends ProcessValidationRule {

    /** No loop exit strategy issue ID. */
    public static final String ID = "sim.noLoopExitStrategy"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        LoopAnalyser loopAnalyser = getTool(LoopAnalyser.class, process);
        Loop[] loops = loopAnalyser.getLoops();
        for (int i = 0; i < loops.length; i++) {
            if (!hasExitStrategy(process, loops[i])) {
                addIssue(ID, process, loops[i]);
            }
        }
    }

    /**
     * @param process The process to check.
     * @param loop The loop to check.
     * @return true if the loop has an exit strategy.
     */
    private boolean hasExitStrategy(Process process, Loop loop) {
        boolean hasExitStrategy = false;
        String[] activities = loop.getActivities();
        for (int i = 0; i < activities.length; i++) {
            Activity activity = process.getActivity(activities[i]);
            if (hasExitStrategy(activity)) {
                hasExitStrategy = true;
                break;
            }
        }
        return hasExitStrategy;
    }

    /**
     * @param activity The activity to check.
     * @return true if the activity has an exit strategy.
     */
    private boolean hasExitStrategy(Activity activity) {
        ActivitySimulationDataType activitySimulationData = SimulationXpdlUtils
                .getActivitySimulationData(activity);
        if (activitySimulationData != null) {
            LoopControlType type = activitySimulationData.getLoopControl();
            if (type != null) {
                LoopControlTransitionType strategyType = null;
                if (type.getMaxElapseTimeStrategy() != null) {
                    strategyType = type.getMaxElapseTimeStrategy();
                }
                if (type.getMaxLoopCountStrategy() != null) {
                    strategyType = type.getMaxLoopCountStrategy();
                }
                if (type.getNormalDistributionStrategy() != null) {
                    strategyType = type.getNormalDistributionStrategy();
                }
                if (strategyType != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {               
    }

}
