/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Identifies a split activity, a join activity and all of the activities
 * between the two. The group is such that the only entry points are to the
 * split activity and the only exit points are from the join activity. All paths
 * from the split activity must converge at the join activity.
 *
 * @author nwilson
 */
public class SplitJoinGroup {
    /** The set of activities in the group. */
    private TreeSet<String> activities;
    /** The split activity for the group. */
    private String split;
    /** The join activity for the group. */
    private String join;

    /**
     * @param split The split activity for the group.
     * @param join The join activity for the group.
     */
    public SplitJoinGroup(String split, String join) {
        this.split = split;
        this.join = join;
        activities = new TreeSet<String>();
        activities.add(split);
        activities.add(join);
    }
    
    /**
     * @param path The path to add to this group.
     */
    public void add(Collection<String> path) {
        activities.addAll(path);
    }

    /**
     * @return The unique id of this group.
     */
    public String getId() {
        StringBuffer buffer = new StringBuffer();
        for (Iterator i = activities.iterator(); i.hasNext();) {
            String id = (String) i.next();
            buffer.append(id);
            if (i.hasNext()) {
                buffer.append(","); //$NON-NLS-1$
            }
        }
        return buffer.toString();
    }

    /**
     * @param object The object to test for equality.
     * @return true if it is a SplitjoinGroup and the IDs match.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        boolean equal = false;
        if (object != null && object instanceof SplitJoinGroup) {
            SplitJoinGroup other = (SplitJoinGroup) object;
            if (getId().equals(other.getId())) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * @return The hashcode of the ID.
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return getId().hashCode();
    }

    /**
     * @return The ID.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getId();
    }

    /**
     * Tests if the group is closed. This checks that only the split and join
     * activities have inputs from activities outside the group. Outputs to
     * activities oustside the group are not possible due to the way the split
     * joing group is constructed, so they are not checked for here.
     *
     * @param tool The transition tool.
     * @return true if the group is closed.
     */
    public boolean isClosed(TransitionTool tool) {
        boolean closed = true;
        for (Iterator i = activities.iterator(); i.hasNext();) {
            String activity = (String) i.next();
            if (!activity.equals(split) && !activity.equals(join)) {
                String[] sources = tool.getSources(activity);
                for (int j = 0; j < sources.length; j++) {
                    if (!activities.contains(sources[j])) {
                        closed = false;
                        break;
                    }
                }
            }
        }
        return closed;
    }

    /**
     * @return The split activity.
     */
    public String getSplit() {
        return split;
    }

    /**
     * @return The join activity.
     */
    public String getJoin() {
        return join;
    }

    /**
     * @return An array of activities in the group.
     */
    public String[] getActivities() {
        String[] activityArray = new String[activities.size()];
        activities.toArray(activityArray);
        return activityArray;
    }

}
