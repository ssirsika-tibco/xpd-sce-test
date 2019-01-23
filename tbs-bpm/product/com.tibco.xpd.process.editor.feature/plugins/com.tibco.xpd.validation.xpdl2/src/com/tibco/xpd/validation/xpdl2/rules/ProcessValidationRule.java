/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Abstract base class for rules that operate on an XPDL2 Process.
 * 
 * @author nwilson
 */
public abstract class ProcessValidationRule extends Xpdl2ValidationRule {

    /**
     * @return The class type on which this rule will operate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    @Override
    public Class<?> getTargetClass() {
        return Process.class;
    }

    /**
     * @param o
     *            The object to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     */
    @Override
    public void validate(Object o) {
        if (o instanceof Process) {
            Process process = (Process) o;
            validate(process);

            // validate the activity sets within this process also
            ActivitySet[] activitySets =
                    process.getActivitySets().toArray(new ActivitySet[process
                            .getActivitySets().size()]);

            for (ActivitySet actSet : activitySets) {
                // validates each of the activities within the activity set
                validateFlowContainer(process,
                        new BasicEList<Activity>(actSet.getActivities()),
                        new BasicEList<Transition>(actSet.getTransitions()));
            }
        }
    }

    /**
     * SID - Changed this method to non-abstract. This rule should never have
     * been changed emphasis the validateFlowContainer() method as the thing to
     * fill in. Only overwrite this for when you wish to addres the set of
     * activities in the process and the set of activities in each embedded
     * sub-process in ISOLATION from each other.
     * 
     * In which case there is already a FlowContainerValidaitonRule for that
     * purpose anyway!
     * 
     * This validateFlowContainer() method was introduced by way back because
     * someone kept forgetting to deal with embedded sub-process activities as
     * well as those in main process. *
     * 
     * @param process
     *            The process containing all activities and sets
     * @param activities
     *            The activities to validate from either activity set or the
     *            process /**
     * @param transitions
     *            The transitions for this flow container
     * 
     * @deprecated If you wish to look at activities/transitions in main process
     *             and in each embedded sub-process IN ISOLATION then use
     *             {@link FlowContainerValidationRule}. Otherwise you should
     *             override {@link #validate(Process)} and then if you wish to
     *             look at all activities use
     *             {@link Xpdl2ModelUtil#getAllActivitiesInProc(Process)} etc
     */
    @Deprecated
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        /*
         * SID - Changed this method to non-abstract. This rule should never
         * have been changed emphasis the validateFlowContainer() method as the
         * thing to fill in. Only overwrite this for when you wish to address
         * the set of activities in the process and the set of activities in
         * each embedded sub-process in ISOLATION from each other.
         * 
         * In which case there is already a FlowContainerValidaitonRule for that
         * purpose anyway!
         * 
         * This validateFlowContainer() method was introduced by way back
         * because someone kept forgetting to deal with embedded sub-process
         * activities as well as those in main process.
         */
    }

    /**
     * @param process
     *            The process to validate.
     */
    public void validate(Process process) {
        // validates the activities within this process
        validateFlowContainer(process,
                new BasicEList<Activity>(process.getActivities()),
                new BasicEList<Transition>(process.getTransitions()));
    }
}
