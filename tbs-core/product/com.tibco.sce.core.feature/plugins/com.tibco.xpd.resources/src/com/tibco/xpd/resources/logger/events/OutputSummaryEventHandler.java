/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.resources.logger.events.EventProcessor.Event;

/**
 * Collects and print summary. To print summary you would need to call
 * {@link EventProcessor#event(String, String...)} with {@link #PRINT_SUMMARY}
 * constant passed as a name of the event.
 * 
 * @author jarciuch
 * @since 5 Dec 2011
 */
public abstract class OutputSummaryEventHandler extends CorrelatingEventHandler {
    public static final String PRINT_SUMMARY = "[PRINT SUMMARY]"; //$NON-NLS-1$

    /**
     * Contains 2 elements table with correlated events: event[0] - begin event,
     * event[1] - end event.
     */
    protected List<Event[]> endEvents = new ArrayList<Event[]>();

    /**
     * Output a message line on your chosen device.
     * 
     * @param message
     */
    protected abstract void outputLine(String message);

    /**
     * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleLog(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
     * 
     * @param event
     */
    @Override
    public void handleCustomEvent(Event event) {
        if (PRINT_SUMMARY.equals(event.getName())) {
            StringBuilder sb = new StringBuilder();
            sb.append("------------------------------------------SUMMARY-------------------------------------------\n"); //$NON-NLS-1$
            if (PRINT_SUMMARY.equals(event.getName())) {
                for (Event[] events : endEvents) {
                    Event begin = events[0];
                    Event end = events[1];

                    long elapsed = (end.getTime() - begin.getTime());
                    sb.append(String
                            .format("|%-60s | Taken: %s |%s\n", end.getName(), CorrelatingEventHandler.getElapsedTimeString(elapsed), end.getLabelsString())); //$NON-NLS-1$

                }
            }
            sb.append("-------------------------------------------------------------------------------------------\n"); //$NON-NLS-1$
            outputLine(sb.toString());
        }
    }

    /**
     * @see com.tibco.xpd.resources.logger.events.CorrelatingEventHandler#handleEnd(com.tibco.xpd.resources.logger.events.EventProcessor.Event,
     *      com.tibco.xpd.resources.logger.events.EventProcessor.Event)
     * 
     * @param event
     * @param correlatedBeginEvent
     */
    @Override
    public void handleEnd(Event event, Event correlatedBeginEvent) {
        endEvents.add(new Event[] { correlatedBeginEvent, event });
    }
}
