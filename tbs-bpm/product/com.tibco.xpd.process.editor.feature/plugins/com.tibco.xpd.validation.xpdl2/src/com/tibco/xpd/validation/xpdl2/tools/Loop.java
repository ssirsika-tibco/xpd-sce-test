/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Maintains a loop of activity IDs in the order they are encountered in the
 * loop. Note that loops are considered equal if they have the same
 * "START OF LOOP" activity and contain the same activity IDs (regardless of
 * order just in case for some reason activities in parallel flows are added to
 * list in different orders.
 * 
 * @author nwilson, aallway
 * @since 2.0 - Improved loop detection technique @3.3
 */
public class Loop implements IActivityContainer {

    /** The flow container for this loop. */
    private FlowContainer container;

    /** The items in the loop (including start). */
    private LinkedHashSet<String> items;

    /** Id of the activity that's the start5 of the loop. */
    private String startLoopActivityId;

    /**
     * @param container
     *            The flow container for this loop.
     * @param startLoopActivityId
     *            Id of the activity that's the start5 of the loop.
     * @param items
     *            The items in the loop.
     */
    public Loop(FlowContainer container, String startLoopActivityId,
            Collection<String> items) {
        this.items = new LinkedHashSet<String>(items);
        this.startLoopActivityId = startLoopActivityId;
        this.container = container;
    }

    /**
     * @return The flow container for this loop.
     * @see com.tibco.xpd.validation.xpdl2.tools.IActivityContainer#getFlowContainer()
     */
    public FlowContainer getFlowContainer() {
        return container;
    }

    /**
     * This method produces a unique ID string for this loop. It consists of a
     * comma separated list of activity IDs in ascending order. This ensures
     * that the ID for a loop is always the same regardless of entry point.
     * 
     * @return The ID string identifying this loop.
     */
    public String getId() {
        TreeSet<String> set = new TreeSet<String>(items);
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
     * A Loop is "equal" if it has the SAME start activity and the items within
     * it are the same (regardless of their order (in case different branches of
     * splits within loop are processed in different order).
     * 
     * @param object
     *            The object to test.
     * @return true if equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        boolean equal = false;
        if (object instanceof Loop) {
            Loop otherLoop = (Loop) object;
            if (otherLoop.startLoopActivityId.equals(startLoopActivityId)) {
                equal = items.equals(otherLoop.items);
            }
        }
        return equal;
    }

    /**
     * @return The hashcode of the collection of items.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return items.hashCode();
    }

    /**
     * @return An array of activity IDs in loop order.
     */
    public String[] getActivities() {
        String[] activities = new String[items.size()];
        items.toArray(activities);
        return activities;
    }

    /**
     * @return the startLoopActivityId
     */
    public String getStartLoopActivityId() {
        return startLoopActivityId;
    }

    /**
     * @param activityId
     *            The id of the activity to check
     * @return true if the activity is in the loop, otherwise false.
     */
    public boolean contains(String activityId) {
        return items.contains(activityId);
    }
}
