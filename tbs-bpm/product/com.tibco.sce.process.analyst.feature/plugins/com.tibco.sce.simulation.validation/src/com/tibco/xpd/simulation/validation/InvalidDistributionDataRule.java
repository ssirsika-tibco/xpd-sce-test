/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.AbstractBasicDistribution;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.UniformRealDistribution;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Checks distribution data for tasks and start event.
 * <p>
 * <i>Created: 16 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class InvalidDistributionDataRule extends ProcessValidationRule {

    /** Multiple start event issue ID. */
    public static final String DISTRIBUTION_MIN_GREATER_THEN_MAX = "sim.distributionMinGreaterThenMax"; //$NON-NLS-1$

    /**
     * Check if the activity is start event.
     * 
     * @param activity
     *            activity to check.
     * @return true if activity is start event.
     */
    private boolean isStartEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event instanceof StartEvent) {
            return true;
        }
        return false;
    }

    /**
     * Check if activity is task activity.
     * 
     * @param activity
     *            The activity.
     * @return true if it is a Task activity.
     */
    private boolean isTaskActivity(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            return true;
        }
        return false;
    }

    /**
     * Checks if activity is independent subprocess.
     * 
     * @param activity
     *            activity to check.
     * @return true if activity is an independent subprocess.
     */
    private boolean isIndependentSubFlow(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof SubFlow) {
            return true;
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            if (isStartEvent(activity)) {
                StartSimulationDataType ssd = SimulationXpdlUtils
                        .getStartSimulationData(activity);
                if (ssd != null && ssd.getDuration() != null) {
                    AbstractBasicDistribution basicDistribution = ssd
                            .getDuration();
                    if (basicDistribution instanceof UniformRealDistribution) {
                        UniformRealDistribution dist = (UniformRealDistribution) basicDistribution;
                        if (dist.getLowerBorder() > dist.getUpperBorder()) {
                            addIssue(DISTRIBUTION_MIN_GREATER_THEN_MAX,
                                    activity);
                        }
                    }
                }
            }
            if (isTaskActivity(activity) || isIndependentSubFlow(activity)) {
                ActivitySimulationDataType asd = SimulationXpdlUtils
                        .getActivitySimulationData(activity);
                if (asd != null && asd.getDuration() != null
                        && asd.getDuration().getBasicDistribution() != null) {
                    AbstractBasicDistribution basicDistribution = asd
                            .getDuration().getBasicDistribution();
                    if (basicDistribution instanceof UniformRealDistribution) {
                        UniformRealDistribution dist = (UniformRealDistribution) basicDistribution;
                        if (dist.getLowerBorder() > dist.getUpperBorder()) {
                            addIssue(DISTRIBUTION_MIN_GREATER_THEN_MAX,
                                    activity);
                        }
                    }
                }
            }
        }        
    }

}
