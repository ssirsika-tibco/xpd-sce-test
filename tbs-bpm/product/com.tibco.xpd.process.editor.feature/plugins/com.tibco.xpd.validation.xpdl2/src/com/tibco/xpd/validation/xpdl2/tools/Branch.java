/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Maintains a list of activity IDs in the order they are encountered in the
 * branch. Branches represent a series of activities where there are no splits
 * or joins.
 * 
 * @author nwilson
 */
public class Branch {
    /** The start activity of the branch. */
    private String start;

    /** The end activity of the branch. */
    private String end;

    /** The activities in the branch. */
    private LinkedHashSet<String> items;

    /** True if the branch is conditional. */
    private boolean conditional;

    /**
     * @param activity The start activity.
     */
    public Branch(String activity) {
        start = activity;
        end = activity;
        this.items = new LinkedHashSet<String>();
        items.add(activity);
        conditional = false;
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
     * @param object The object to test.
     * @return true if the object is equal to this.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object object) {
        boolean equal = false;
        if (object instanceof Branch) {
            equal = items.equals(((Branch) object).items);
        }
        return equal;
    }

    /**
     * @return The hashcode of the collected items.
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
     * @return The start activity.
     */
    public String getStart() {
        return start;
    }

    /**
     * @return The end activity.
     */
    public String getEnd() {
        return end;
    }

    /**
     * @return A text description of the branch.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[" + getId() + "] (" //$NON-NLS-1$ //$NON-NLS-2$
                + (isConditional() ? "Conditional" : "Unconditional") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * @param activity The activity to add.
     */
    public void add(String activity) {
        items.add(activity);
        end = activity;
    }

    /**
     * @param conditional true if the branch is conditional.
     */
    public void setConditional(boolean conditional) {
        this.conditional = conditional;
    }

    /**
     * @return true if the branch is conditional.
     */
    public boolean isConditional() {
        return conditional;
    }
}
