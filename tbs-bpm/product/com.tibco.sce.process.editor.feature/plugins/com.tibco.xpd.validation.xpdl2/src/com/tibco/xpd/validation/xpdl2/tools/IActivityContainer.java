/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.tools;

import com.tibco.xpd.xpdl2.FlowContainer;

/**
 * Interface to identify a class that contains a set of activities and provides
 * a unique id for that set.
 *
 * @author nwilson
 */
public interface IActivityContainer {
    
    /** Id for the activity container. */
    String ID = "activityContainer"; //$NON-NLS-1$

    /**
     * @return The flow container for this set of activities.
     */
    FlowContainer getFlowContainer();
    
    /**
     * @return An array of activity IDs contained in this set.
     */
    String[] getActivities();
    /**
     * @param activityId The activity ID to check for.
     * @return true if the set contains the activity, otherwise false.
     */
    boolean contains(String activityId);
    /**
     * @return The unique ID of this activity set.
     */
    String getId();
}
