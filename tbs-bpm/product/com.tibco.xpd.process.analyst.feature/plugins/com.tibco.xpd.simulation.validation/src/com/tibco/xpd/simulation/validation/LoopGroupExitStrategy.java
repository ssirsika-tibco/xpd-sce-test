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
import com.tibco.xpd.validation.xpdl2.tools.LoopGroup;
import com.tibco.xpd.validation.xpdl2.tools.LoopGrouper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class LoopGroupExitStrategy extends ProcessValidationRule {

    /** Exit strategy issue ID. */
    public static final String ID = "sim.noLoopGroupExitStrategy"; //$NON-NLS-1$

    /**
     * @param process The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(
     *      com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        LoopGrouper loopGrouper = getTool(LoopGrouper.class, process);
        LoopGroup[] loops = loopGrouper.getLoopGroups();
        for (int i = 0; i < loops.length; i++) {
            if (!hasValidExitStrategy(process, loops[i])) {
                addIssue(ID, process, loops[i]);
            }
        }
    }

    /**
     * Checks to see if there's an exit strategy with a destination outside the
     * loop group.
     * 
     * @param process The workflow process
     * @param loop The loop group to check.
     * @return true if there is a valid strategy, otherwise false.
     */
    private boolean hasValidExitStrategy(Process process, LoopGroup loop) {
        boolean hasValidExitStrategy = false;
        String[] activities = loop.getActivities();
        for (int i = 0; i < activities.length; i++) {
            Activity activity = process.getActivity(activities[i]);
            if (hasValidExitStrategy(activity, loop)) {
                hasValidExitStrategy = true;
                break;
            }
        }
        return hasValidExitStrategy;
    }

    /**
     * @param activity The activity to check.
     * @param loop The loop group to check.
     * @return true if the loop group has an exit stragtegy.
     */
    private boolean hasValidExitStrategy(Activity activity, LoopGroup loop) {
        ActivitySimulationDataType activitySimulationData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
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
                    String destination = strategyType.getToActivity();
                    if (destination != null && !loop.contains(destination)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {     
    }
}
