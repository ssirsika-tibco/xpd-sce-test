/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.validation.xpdl2.tools.IActivityContainer;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. Separation Of Duties task group must contain at least two user/manual
 * tasks
 * 
 * 2. The Separation of Duties group cannot contain the same activities as
 * another Separation of Duties group.
 * 
 * @author nwilson
 */
public class SeparationOfDutiesRule extends ProcessValidationRule {

    /** Issue id. */
    private static final String ID = "bpmn.invalidTaskGroup"; //$NON-NLS-1$

    /** Duplicate separation group. */
    private static final String DUPLICATE = "bpmn.duplicateSeparationGroup"; //$NON-NLS-1$

    /** Separation of Duties group id added as additional info */
    public static final String GROUP_ID = "SEPARATION_OF_DUTIES_GROUP_ID"; //$NON-NLS-1$

    /**
     * @param process
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     */
    @Override
    public void validate(Process process) {
        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
        List<SeparationGroup> allGroups = new ArrayList<SeparationGroup>();
        Set<SeparationGroup> uniqueGroups = new HashSet<SeparationGroup>();
        Set<SeparationGroup> duplicateGroups = new HashSet<SeparationGroup>();

        if (other instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns patterns = (ProcessResourcePatterns) other;
            for (Object next : patterns.getSeparationOfDutiesActivities()) {
                if (next instanceof SeparationOfDutiesActivities) {
                    SeparationOfDutiesActivities separation =
                            (SeparationOfDutiesActivities) next;
                    // MR 37234 - begin
                    /**
                     * raise issue there is a single task in the group
                     */
                    EList<ActivityRef> activityRef =
                            separation.getActivityRef();
                    List<String> msgs = new ArrayList<String>();
                    msgs.add(separation.getName());
                    if (activityRef.size() == 1) {
                        addIssue(ID, separation, msgs);
                    }
                    // MR 37234 - ends
                    /**
                     * raise issue if two groups are identical (in the sense
                     * they have the same tasks in them)
                     */
                    Set<String> activities = new HashSet<String>();
                    for (Object nextRef : separation.getActivityRef()) {
                        if (nextRef instanceof ActivityRef) {
                            ActivityRef activityRef2 = (ActivityRef) nextRef;
                            Activity activity =
                                    process
                                            .getActivity(activityRef2
                                                    .getIdRef());
                            if (null != activity) {
                                activities.add(activity.getId());
                            }
                        }
                    }
                    SeparationGroup group =
                            new SeparationGroup(process, separation, activities);
                    allGroups.add(group);
                    if (uniqueGroups.contains(group)) {
                        duplicateGroups.add(group);
                    } else {
                        uniqueGroups.add(group);
                    }
                }
            }
            for (SeparationGroup group : allGroups) {
                HashMap<String, String> addInfo = new HashMap<String, String>();
                if (duplicateGroups.contains(group)) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(group.getName());
                    addInfo.put(GROUP_ID, group.getId());
                    addIssue(DUPLICATE, group.separation, messages, addInfo);
                }
            }
        }
    }

    /**
     * @param process
     *            The process to check.
     * @param activities
     *            The activities.
     * @param transitions
     *            The transitions.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     *      validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing here.
    }

    /**
     * Container class for separation of duties activites.
     * 
     * @author nwilson
     */
    class SeparationGroup implements IActivityContainer {

        /** The flow container. */
        private FlowContainer container;

        /** The separation of duties container. */
        private SeparationOfDutiesActivities separation;

        /** The activities in the group. */
        private Set<String> activities;

        /**
         * @param container
         *            The flow container.
         * @param separation
         *            The separation of duties container.
         * @param activities
         *            The activities in the group.
         */
        public SeparationGroup(FlowContainer container,
                SeparationOfDutiesActivities separation, Set<String> activities) {
            this.container = container;
            this.separation = separation;
            this.activities = activities;
        }

        /**
         * @return The name of the separation group.
         */
        public String getName() {
            return separation.getName();
        }

        /**
         * @param activityId
         *            The activityId of the activity to check.
         * @return true if it is in the group.
         * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#
         *      contains(java.lang.String)
         */
        public boolean contains(String activityId) {
            return activities.contains(activityId);
        }

        /**
         * @return An array of activity Ids.
         * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#
         *      getActivities()
         */
        public String[] getActivities() {
            return activities.toArray(new String[activities.size()]);
        }

        /**
         * @return The flow container.
         * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#
         *      getFlowContainer()
         */
        public FlowContainer getFlowContainer() {
            return container;
        }

        /**
         * @return The separation group id.
         * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#getId()
         */
        public String getId() {
            return separation.getId();
        }

        /**
         * @param obj
         *            The object to test.
         * @return true if the separation groups contain the same activities.
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof SeparationGroup) {
                SeparationGroup other = (SeparationGroup) obj;
                if (activities.equals(other.activities)) {
                    equal = true;
                }
            }
            return equal;
        }

        /**
         * @return The hashcode of the activity set.
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return activities.hashCode();
        }
    }
}
