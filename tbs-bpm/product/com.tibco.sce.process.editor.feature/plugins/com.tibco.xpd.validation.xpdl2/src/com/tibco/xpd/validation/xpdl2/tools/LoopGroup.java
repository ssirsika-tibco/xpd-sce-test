/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * A LoopGroup is composed of one or more Loops that overlap (share one or more
 * activites). It acts a container for all of the activities from those loops
 * and allows identification of all of the entry and exit points of the group.
 * 
 * @author nwilson
 */
public class LoopGroup implements IActivityContainer {
    /** The transition tool. */
    private TransitionTool tool;

    /** The set of activities in this group. */
    private HashSet<String> activities;

    /** The set of loop entry activities. */
    private HashSet<String> starts;

    /** The set of loop exit activities. */
    private HashSet<String> ends;

    /**
     * @param tool The transition tool.
     * @param loop The initial loop for this group.
     */
    public LoopGroup(TransitionTool tool, Loop loop) {
        this.tool = tool;
        activities = new HashSet<String>();
        String[] loopActivities = loop.getActivities();
        for (int i = 0; i < loopActivities.length; i++) {
            activities.add(loopActivities[i]);
        }
        updateLinks();
    }

    /**
     * This method produces a unique ID string for this loop group. It consists
     * of a comma separated list of activity IDs in ascending order. This
     * ensures that the ID for a loop group is always the same regardless of
     * entry point.
     * 
     * @return The ID string identifying this loop.
     */
    public String getId() {
        TreeSet<String> set = new TreeSet<String>(activities);
        StringBuffer buffer = new StringBuffer();
        for (Iterator i = set.iterator(); i.hasNext();) {
            String id = (String) i.next();
            buffer.append(id);
            if (i.hasNext()) {
                buffer.append(","); //$NON-NLS-1$
            }
        }
        return buffer.toString();
    }

    /**
     * Identifies the start and end activities for the loop group.
     */
    private void updateLinks() {
        starts = new HashSet<String>();
        ends = new HashSet<String>();
        for (Iterator i = activities.iterator(); i.hasNext();) {
            String activity = (String) i.next();
            String[] sources = tool.getSources(activity);
            for (int j = 0; j < sources.length; j++) {
                if (!activities.contains(sources[j])) {
                    starts.add(activity);
                }
            }
            String[] destinations = tool.getDestinations(activity);
            for (int j = 0; j < destinations.length; j++) {
                if (!activities.contains(destinations[j])) {
                    ends.add(activity);
                }
            }
        }
    }

    /**
     * Merges a second loop group with this one.
     * 
     * @param toMerge The loop group to merge with this one.
     */
    public void merge(LoopGroup toMerge) {
        Collection<String> mergeActivities = toMerge.getActivitySet();
        activities.addAll(mergeActivities);
        updateLinks();
    }

    /**
     * @return The collection of activities in the loop group.
     */
    public Collection<String> getActivitySet() {
        return activities;
    }

    /**
     * @return An array of activities in the loop group.
     * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#
     *      getActivities()
     */
    public String[] getActivities() {
        String[] activityArray = new String[activities.size()];
        activities.toArray(activityArray);
        return activityArray;
    }

    /**
     * @return The collection of start activities.
     */
    public Collection<String> getStarts() {
        return starts;
    }

    /**
     * @return The collection of end activities.
     */
    public Collection<String> getEnds() {
        return ends;
    }

    /**
     * @param activity The activity to check for.
     * @return true if the activity is in the loop group.
     * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#contains(
     *      java.lang.String)
     */
    public boolean contains(String activity) {
        return activities.contains(activity);
    }

    /**
     * @return The flow container for this loop group.
     * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#
     *      getFlowContainer()
     */
    public FlowContainer getFlowContainer() {
        return tool.getFlowContainer();
    }

}
