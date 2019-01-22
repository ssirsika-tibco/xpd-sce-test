/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.adapters;

/**
 * Routing style options for the process diagram editor.
 * 
 * @author aallway
 * @since 22 Feb 2012
 */
public enum XpdFlowRoutingStyle {

    /**
     * Flow connections with default anchor location (not fixed border location)
     * are routed from the center of gateways and events and and from the middle
     * of the overlap of the best facing side of tasks/sub-procs (which has the
     * effect of allow the connection to slide along the side of a task in order
     * that it remains straight if possible..
     * <p>
     * The calculation of "most appropriate side" is based on whether the target
     * point (other end of connection) is in one of four triangular quadrants
     * (whose tip is always the center of the object) to the north,east, south
     * or west.
     */
    UncenteredOnTasks,

    /**
     * Flow connections with default anchor location (not fixed border location)
     * are routed to the middle of the most appropriate side.
     * <p>
     * The calculation of "most appropriate side" favours a horizontal layout
     * (if left edge of target is to teh right of the right edge of source then
     * the right hand edge is always chosen regardless of the vertical locationb
     * and so on).
     */
    SingleEntryExit,

    /**
     * Flow connections with default anchor location (not fixed border location)
     * are routed as for {@link #SingleEntryExit} except that when multiple
     * connections start or end on the same side of an activity then they are
     * automatically separated evenly along that side of the activity and sorted
     * by the location of the other end of connection (or next bendpoint) so
     * that they don't overlap.
     * <p>
     * The calculation of "most appropriate side" favours a horizontal layout
     * (if left edge of target is to teh right of the right edge of source then
     * the right hand edge is always chosen regardless of the vertical locationb
     * and so on).
     */
    MultiEntryExit
}
