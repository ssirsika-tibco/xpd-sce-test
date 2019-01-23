/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.tibco.xpd.resources.logger.events.EventProcessor.Event;
import com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler;

/**
 * Correlates begin and end events. Adds an overridden method:
 * {@link #handleEnd(Event, Event)} which provides access to correlated
 * 
 * @author jarciuch
 * @since 5 Dec 2011
 */
public class CorrelatingEventHandler extends EventHandler {
    // private PrintStream ps = System.err;
    private static class EventEntry {
        public EventEntry(String key, Event event) {
            this.key = key;
            this.event = event;
        }

        String key;

        Event event;
    }

    private Deque<EventEntry> beginEvents =
            new LinkedList<CorrelatingEventHandler.EventEntry>();

    /**
     * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleEvent(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
     * 
     * @param event
     */
    @Override
    protected final void handleEvent(Event event) {
        switch (event.getEventType()) {
        case BEGIN:
            beginEvents.addLast(new EventEntry(getCorrelationId(event), event));
            handleBegin(event);
            break;
        case END:
            Event correlatedBeginEvent = findBeginEvent(event);
            handleEnd(event);
            handleEnd(event, correlatedBeginEvent);
            break;
        case LOG:
            handleLog(event);
            break;
        case CUSTOM:
            handleCustomEvent(event);
            break;
        default:
            // DO NOTHING. Ignoring unknown events.
            break;
        }
    }

    /**
     * Handles END event and provides access to correlated BEGIN event if
     * available. The events are correlated using:
     * {@link #getCorrelationId(Event)}.
     * 
     * @param event
     *            the END event.
     * @param correlatedBeginEvent
     *            correlated BEGIN event if found or 'null'.
     */
    public void handleEnd(Event event, Event correlatedBeginEvent) {
    }

    private Event findBeginEvent(Event end) {
        String correlationId = getCorrelationId(end);

        for (Iterator<EventEntry> iter = beginEvents.descendingIterator(); iter
                .hasNext();) {
            EventEntry entry = iter.next();
            if (entry.key.equals(correlationId)) {
                iter.remove();
                return entry.event;
            }
        }
        return null;
    }

    /**
     * Correlation id is the sting used to find the corresponding begin event of
     * an end event.(Is by default calculated based on name and labels).
     * 
     * @param event
     *            the event to get correlation id of.
     * @return the correlation id used to find correlated start event.
     */
    protected String getCorrelationId(Event event) {
        return new StringBuilder().append(event.getName())
                .append(event.getLabelsString()).toString();
    }

    /**
     * @param elapsedMillisecs
     * @return Ellapsed time description given an ellapsed time in millisecs
     */
    public static String getElapsedTimeString(long elapsedMillisecs) {
        /*
         * We are passing the string format %tH etc an "elapsed time difference"
         * BUT it expects and absolute time which is
         * "number of milisecs since start-of-clock" (1st jan 1970 or something
         * 
         * I think that start-of-clock acutally starts at 01:00 a.m not midnight
         * - so we subtract an hour off the time to adjust for this.
         */
        long adjustedElapsed = elapsedMillisecs - (60 * 60 * 1000);
        return String
                .format("%tH:%tM:%tS.%tL", adjustedElapsed, adjustedElapsed, adjustedElapsed, adjustedElapsed); //$NON-NLS-1$
    }
}
