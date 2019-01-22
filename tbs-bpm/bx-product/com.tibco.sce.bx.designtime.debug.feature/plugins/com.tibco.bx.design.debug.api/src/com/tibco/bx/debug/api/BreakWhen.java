/**
 * 
 */
package com.tibco.bx.debug.api;

public enum BreakWhen {
    /**
     * Break before the event is handled by the behavior
     */
    ENTRY,
    /**
     * Break after the event is handled by the behavior.
     */
    RETURN,
    /**
     * Break both before and after the event is handled by the behavior
     */
    BOTH;
}