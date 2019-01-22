/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

/**
 * @author nwilson
 */
public class MappingDelta {
    /** Mapping before change. */
    private Mapping before;
    /** Mapping after change. */
    private Mapping after;
    
    /**
     * @param before Mapping before change.
     * @param after Mapping after change.
     */
    public MappingDelta(Mapping before, Mapping after) {
        this.before = before;
        this.after = after;
    }

    /**
     * @return Mapping before change.
     */
    public Mapping getBefore() {
        return before;
    }

    /**
     * @return Mapping after change.
     */
    public Mapping getAfter() {
        return after;
    }
}
