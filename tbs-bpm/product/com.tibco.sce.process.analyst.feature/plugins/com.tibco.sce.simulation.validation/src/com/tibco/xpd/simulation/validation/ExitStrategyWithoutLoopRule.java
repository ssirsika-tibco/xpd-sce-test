/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.Loop;
import com.tibco.xpd.validation.xpdl2.tools.LoopAnalyser;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check that activities outside of loops do not have exit strategies.
 * 
 * @author nwilson
 */
public class ExitStrategyWithoutLoopRule extends ProcessValidationRule {

    /** Exit strategy issue ID. */
    public static final String ID = "sim.exitStrategyWithoutLoop"; //$NON-NLS-1$

  
    /**
     * @param loopAnalyser The loop analyser tool.
     * @param activity The activity to check.
     * @return true if the activity is in a loop.
     */
    private boolean isInLoop(LoopAnalyser loopAnalyser, Activity activity) {
        boolean isInLoop = false;
        String activityId = activity.getId();
        Loop[] loops = loopAnalyser.getLoops();
        for (int i = 0; i < loops.length; i++) {
            if (loops[i].contains(activityId)) {
                isInLoop = true;
                break;
            }
        }
        return isInLoop;
    }

    /**
     * @param activity The activity to check.
     * @return true if the activity has an exit strategy.
     */
    private boolean hasExitStrategy(Activity activity) {
        ActivitySimulationDataType activitySimulationData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        if (activitySimulationData != null) {
            LoopControlType type = activitySimulationData.getLoopControl();
            if (type != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        LoopAnalyser loopAnalyser = null;        
        for (Object next : activities) {
            Activity activity = (Activity) next;
            
            if (loopAnalyser == null){
                loopAnalyser = getTool(LoopAnalyser.class, activity.getProcess());
            }
            
            if (hasExitStrategy(activity)) {
                if (!isInLoop(loopAnalyser, activity)) {
                    addIssue(ID, activity);
                }
            }
        }
    }
}
