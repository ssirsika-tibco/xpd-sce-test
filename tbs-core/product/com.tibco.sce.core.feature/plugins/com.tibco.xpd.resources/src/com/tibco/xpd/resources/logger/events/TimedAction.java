/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

/**
 * Represents the closure for running code that will be timed. The run method
 * will be execute around begin-end events of {@link EventProcessor}.
 * 
 * @see EventProcessor#runTimedAction(String, TimedAction, String...)
 * @author jarciuch
 * @since 1 Dec 2011
 */
public interface TimedAction {
    /**
     * Runs around begin-end events to log/calculate execution time.
     * 
     * @throws Exception
     */
    public void run() throws Exception;
}