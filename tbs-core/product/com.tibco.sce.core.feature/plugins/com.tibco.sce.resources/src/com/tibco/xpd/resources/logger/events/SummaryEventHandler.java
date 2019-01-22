/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

/**
 * Collects and print summary. To print summary you would need to call
 * {@link EventProcessor#event(String, String...)} with {@link #PRINT_SUMMARY}
 * constant passed as a name of the event.
 * 
 * @author jarciuch
 * @since 5 Dec 2011
 */
public class SummaryEventHandler extends OutputSummaryEventHandler {

    /**
     * @see com.tibco.xpd.resources.logger.events.OutputSummaryEventHandler#outputLine(java.lang.String)
     * 
     * @param message
     */
    @Override
    protected void outputLine(String message) {
        System.out.println(message);
    }
}
