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
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 1. Retain Familiar task group must contain at least two user/manual tasks.
 * 
 * 2. A Retain Familiar Group cannot contain the same activities as another
 * Retain Familiar Group
 * 
 * @author bharge
 * 
 */
public class RetainFamiliarRule extends ProcessValidationRule {

    private static final String ID = "bpmn.invalidRetainFamiliarTaskGroup"; //$NON-NLS-1$

    /** Retain Familiar group id added as additional info */
    public static final String GROUP_ID = "RETAIN_FAMILIAR_GROUP_ID"; //$NON-NLS-1$

    private static final String DUPLICATE_GROUP_ISSUE =
            "bpmn.duplicateRetainFamiliarGroup"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {
        List<RetainFamiliarGroup> allGroups =
                new ArrayList<RetainFamiliarGroup>();
        Set<RetainFamiliarGroup> uniqueGroups =
                new HashSet<RetainFamiliarGroup>();
        Set<RetainFamiliarGroup> duplicateGroups =
                new HashSet<RetainFamiliarGroup>();

        Object other =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());
        if (other instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns patterns = (ProcessResourcePatterns) other;
            for (Object next : patterns.getRetainFamiliarActivities()) {
                if (next instanceof RetainFamiliarActivities) {
                    RetainFamiliarActivities retainFamiliarActivities =
                            (RetainFamiliarActivities) next;
                    EList<ActivityRef> activityRef =
                            retainFamiliarActivities.getActivityRef();

                    /**
                     * raise issue there is a single task in the group
                     */
                    List<String> msgs = new ArrayList<String>();
                    msgs.add(retainFamiliarActivities.getName());
                    if (activityRef.size() == 1) {
                        addIssue(ID, retainFamiliarActivities, msgs);
                    }

                    /**
                     * raise issue if two groups are identical (in the sense
                     * they have the same tasks in them)
                     */
                    Set<String> activities = new HashSet<String>();
                    for (Object nextRef : retainFamiliarActivities
                            .getActivityRef()) {
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

                    RetainFamiliarGroup group =
                            new RetainFamiliarGroup(process,
                                    retainFamiliarActivities, activities);
                    allGroups.add(group);
                    if (uniqueGroups.contains(group)) {
                        duplicateGroups.add(group);
                    } else {
                        uniqueGroups.add(group);
                    }
                }
            }

            for (RetainFamiliarGroup group : allGroups) {
                HashMap<String, String> addInfo = new HashMap<String, String>();
                if (duplicateGroups.contains(group)) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(group.getName());
                    /**
                     * adding the particular retain familiar group id as
                     * additional info to the issue; so that the resolution can
                     * pick this info and remove that group
                     */
                    addInfo.put(GROUP_ID, group.getId());
                    addIssue(DUPLICATE_GROUP_ISSUE,
                            group.retainFamiliarActivities,
                            messages,
                            addInfo);
                }
            }
        }
    }

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Do nothing, we're just validating process...
    }

    class RetainFamiliarGroup implements IActivityContainer {

        /** The flow container. */
        private FlowContainer container;

        /** The retain familiar container. */
        private RetainFamiliarActivities retainFamiliarActivities;

        /** The activities in the group. */
        private Set<String> activities;

        /**
         * @param container
         *            The flow container.
         * @param separation
         *            The retain familiar container.
         * @param activities
         *            The activities in the group.
         */
        public RetainFamiliarGroup(FlowContainer container,
                RetainFamiliarActivities retainFamiliarActivities,
                Set<String> activities) {
            this.container = container;
            this.retainFamiliarActivities = retainFamiliarActivities;
            this.activities = activities;
        }

        /**
         * @return The name of the retain familiar group.
         */
        public String getName() {
            return retainFamiliarActivities.getName();
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
         * @return The retain familiar group id.
         * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#getId()
         */
        public String getId() {
            return retainFamiliarActivities.getId();
        }

        /**
         * @param obj
         *            The object to test.
         * @return true if the retain familiar groups contain the same
         *         activities.
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof RetainFamiliarGroup) {
                RetainFamiliarGroup other = (RetainFamiliarGroup) obj;
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
