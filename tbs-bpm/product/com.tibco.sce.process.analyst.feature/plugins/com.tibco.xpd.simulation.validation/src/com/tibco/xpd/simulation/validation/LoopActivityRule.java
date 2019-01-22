/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.Loop;
import com.tibco.xpd.validation.xpdl2.tools.LoopAnalyser;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Checks for one or more activities in a loop.
 *
 * @author nwilson
 */
public class LoopActivityRule extends ProcessValidationRule {

    /** Loop activity issue ID. */
    public static final String ID = "sim.loopActivityCount"; //$NON-NLS-1$

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
            String[] activities = loops[i].getActivities();
            boolean loopValid = false;
            for (int j = 0; j < activities.length; j++) {
                if (isActivity(process, activities[j])) {
                    loopValid = true;
                    break;
                }
            }
            if (!loopValid) {
                addIssue(ID, process, loops[i]);
            }
        }
    }

    /**
     * @param process The process to check.
     * @param activityId The activity ID.
     * @return true if the activity is valid within a loop.
     */
    protected boolean isActivity(Process process, String activityId) {
        boolean isActivity = false;
        Activity activity = process.getActivity(activityId);
        if (activity != null) {
            Implementation implementation = activity.getImplementation();
            if (implementation != null) {
                if (implementation instanceof Task || implementation instanceof SubFlow) {
                    isActivity = true;
                }
            }
        }
        return isActivity;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
      
    }
}
