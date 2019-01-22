/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.destinations.preferences;

/**
 * @author nwilson
 * 
 */
public class DestinationPreferencesEvent {
    /**
     * One or more items added.
     */
    public static final int ADDED = 1;

    /**
     * One or more items removed.
     */
    public static final int REMOVED = 2;

    /**
     * Item contents changed but no items added or removed.
     */
    public static final int CHANGED = 3;

    /**
     * All items reloaded.
     */
    public static final int REFRESHED = 4;

    private int type;

    private int startIndex = -1;

    private int endIndex = -1;

    public DestinationPreferencesEvent(int type) {
        this.type = type;
    }

    public DestinationPreferencesEvent(int type, int startIndex, int endIndex) {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getType() {
        return type;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

}
