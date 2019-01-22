/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.LoopControlType;
import com.tibco.xpd.simulation.MaxElapseTimeStrategyType;
import com.tibco.xpd.simulation.MaxLoopCountStrategyType;
import com.tibco.xpd.simulation.NormalDistributionStrategyType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Checks for valid loop control exit strategies.
 * 
 * @author nwilson
 */
public class IncompleteLoopControlStrategyRule extends ProcessValidationRule {

    /** Decision activity issue ID. */
    public static final String DECISION_ACTIVITY_DOES_NOT_EXIST =
            "sim.decisionActivityDoesNotExist"; //$NON-NLS-1$

    /** To activity issue ID. */
    public static final String TO_ACTIVITY_DOES_NOT_EXIST =
            "sim.toActivityDoesNotExist"; //$NON-NLS-1$

    /** Outgoing transition issue ID. */
    public static final String OUT_GOING_TRANSITION_COUNT =
            "sim.outGoingTransitionCountLessThanTwo"; //$NON-NLS-1$

    /** Outgoing transition to activity issue ID. */
    public static final String TO_ACTIVITY_NOT_ON_OUT_GOING_TRANSITION =
            "sim.toActivityNotOnOutGoingTransition"; //$NON-NLS-1$

    /** Loop count issue ID. */
    public static final String NEGATIVE_LOOP_COUNT = "sim.negativeLoopCount"; //$NON-NLS-1$

    /** Elapsed time issue ID. */
    public static final String NEGATIVE_ELAPSE_TIME = "sim.negativeElapseTime"; //$NON-NLS-1$

    /** Mean or Standard deviation issue ID. */
    public static final String NEGATIVE_NORMAL_DIST_VALUES =
            "sim.negativeMeanOrStdDeviation"; //$NON-NLS-1$

    /** Invalid decision activity issue ID. */
    private static final String INVALID_DECISION_ACTIVITY =
            "sim.invalidDecisionActivity"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param activitySimulationData
     *            The activty simulation data.
     */
    private void isValidLoopControlStrategy(Process process, Activity activity,
            ActivitySimulationDataType activitySimulationData) {
        LoopControlType loopControl = activitySimulationData.getLoopControl();
        if (loopControl == null) {
            return;
        }
        MaxElapseTimeStrategyType maxElapseTimeStrategy =
                loopControl.getMaxElapseTimeStrategy();
        if (maxElapseTimeStrategy != null) {
            isValidMaxElapseTimeStrategy(process,
                    activity,
                    maxElapseTimeStrategy);
            return;
        }
        MaxLoopCountStrategyType maxLoopCountStrategy =
                loopControl.getMaxLoopCountStrategy();
        if (maxLoopCountStrategy != null) {
            isValidMaxLoopCountStrategy(process, activity, maxLoopCountStrategy);
            return;
        }
        NormalDistributionStrategyType normalDistributionStrategy =
                loopControl.getNormalDistributionStrategy();
        if (normalDistributionStrategy != null) {
            isValidNormalDistributionStrategy(process,
                    activity,
                    normalDistributionStrategy);
            return;
        }
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param normalDistributionStrategy
     *            The distribution strategy.
     */
    private void isValidNormalDistributionStrategy(Process process,
            Activity activity,
            NormalDistributionStrategyType normalDistributionStrategy) {
        String decisionActivityId =
                normalDistributionStrategy.getDecisionActivity();
        String toActivityId = normalDistributionStrategy.getToActivity();
        boolean c =
                doTransitionCheck(process,
                        activity,
                        decisionActivityId,
                        toActivityId);
        if (!c) {
            return;
        }
        double mean = normalDistributionStrategy.getMean();
        double stdDeviation = normalDistributionStrategy.getStandardDeviation();
        if (mean < 0 || stdDeviation < 0) {
            addIssue(NEGATIVE_NORMAL_DIST_VALUES, activity);
            return;
        }
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param maxLoopCountStrategy
     *            Loop count strategy.
     */
    private void isValidMaxLoopCountStrategy(Process process,
            Activity activity, MaxLoopCountStrategyType maxLoopCountStrategy) {

        String decisionActivityId = maxLoopCountStrategy.getDecisionActivity();
        String toActivityId = maxLoopCountStrategy.getToActivity();

        boolean c =
                doTransitionCheck(process,
                        activity,
                        decisionActivityId,
                        toActivityId);
        if (!c) {
            return;
        }
        int maxLoopCount = maxLoopCountStrategy.getMaxLoopCount();
        if (maxLoopCount < 0) {
            addIssue(NEGATIVE_LOOP_COUNT, activity);
            return;
        }
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param maxLoopCountStrategy
     *            Loop count strategy.
     */
    private boolean isValidDecisionActivity(Process process,
            Activity loopActivity, Activity decisionActivity) {
        if (decisionActivity != null) {
            Route route = decisionActivity.getRoute();
            if (route == null
                    || !JoinSplitType.EXCLUSIVE_LITERAL.equals(Xpdl2ModelUtil
                            .safeGetGatewayType(decisionActivity))) {
                addIssue(INVALID_DECISION_ACTIVITY, loopActivity);
                return false;
            }
        }
        return true;
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param maxElapseTimeStrategy
     *            Elapsed time strategy.
     */
    private void isValidMaxElapseTimeStrategy(Process process,
            Activity activity, MaxElapseTimeStrategyType maxElapseTimeStrategy) {
        String decisionActivityId = maxElapseTimeStrategy.getDecisionActivity();
        String toActivityId = maxElapseTimeStrategy.getToActivity();
        boolean c =
                doTransitionCheck(process,
                        activity,
                        decisionActivityId,
                        toActivityId);
        if (!c) {
            return;
        }
        double maxElapseTime = maxElapseTimeStrategy.getMaxElapseTime();
        if (maxElapseTime < 0) {
            addIssue(NEGATIVE_ELAPSE_TIME, activity);
            return;
        }
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param decisionActivityId
     *            The decision activity.
     * @param toActivityId
     *            The destination activity.
     * @return true if transition ok.
     */
    private boolean doTransitionCheck(Process process, Activity activity,
            String decisionActivityId, String toActivityId) {
        Activity fromActivity = process.getActivity(decisionActivityId);
        if (fromActivity == null) {
            addIssue(DECISION_ACTIVITY_DOES_NOT_EXIST, activity);
            return false;
        }
        Activity toActivity = process.getActivity(toActivityId);
        if (toActivity == null) {
            addIssue(TO_ACTIVITY_DOES_NOT_EXIST, activity);
            return false;
        }
        boolean isValidDecisionActivity =
                isValidDecisionActivity(process, activity, fromActivity);
        boolean b =
                checkOutgoingTransitions(process,
                        activity,
                        decisionActivityId,
                        toActivityId);
        return b && isValidDecisionActivity;
    }

    /**
     * @param process
     *            The process to check.
     * @param activity
     *            The activity to check.
     * @param decisionActivityId
     *            The decision activity.
     * @param toActivityId
     *            The destination activity.
     * @return true if transitions ok.
     */
    private boolean checkOutgoingTransitions(Process process,
            Activity activity, String decisionActivityId, String toActivityId) {
        List transitions = process.getTransitions();
        List<Transition> tranList = new ArrayList<Transition>();
        for (Object next : transitions) {
            Transition tran = (Transition) next;
            String from = tran.getFrom();
            if (from.equals(decisionActivityId)) {
                tranList.add(tran);
            }
        }
        if (tranList.size() < 2) {
            addIssue(OUT_GOING_TRANSITION_COUNT, activity);
            return false;
        }
        boolean toReturn = false;
        for (Object next : tranList) {
            Transition tranElement = (Transition) next;
            String to = tranElement.getTo();
            if (to.equals(toActivityId)) {
                toReturn = true;
                break;
            }
        }
        if (!toReturn) {
            addIssue(TO_ACTIVITY_NOT_ON_OUT_GOING_TRANSITION, activity);
        }
        return toReturn;
    }

    /**
     * @param act
     *            The activity to check.
     * @return true if it can hold simulation data.
     */
    private boolean canSimDataExist(Activity act) {
        Implementation impl = act.getImplementation();
        if (impl instanceof Task) {
            // task activity
            return true;
        }
        return false;
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Object next : activities) {
            Activity activity = (Activity) next;
            boolean b = canSimDataExist(activity);
            if (!b) {
                continue;
            }
            ActivitySimulationDataType activitySimulationData =
                    SimulationXpdlUtils.getActivitySimulationData(activity);
            if (activitySimulationData == null) {
                continue;
            }
            isValidLoopControlStrategy(activity.getProcess(),
                    activity,
                    activitySimulationData);
        }

    }
}
