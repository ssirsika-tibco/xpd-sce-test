/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import com.tibco.xpd.resources.logger.events.EventProcessor.Event;

/**
 * Prints event log to the sub-class's chosen output method..
 * 
 * @author jarciuch
 * @since 5 Dec 2011
 */
public abstract class OutputEventHandler extends CorrelatingEventHandler {

    public OutputEventHandler() {
    }

    /**
     * Output an event message line ot your chosen device.
     * 
     * @param message
     */
    protected abstract void outputLine(String message);

    /**
     * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleBegin(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
     * 
     * @param event
     */
    @Override
    public void handleBegin(Event event) {
        outputLine(getEventString(event));
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
        long elapsed = event.getTime() - correlatedBeginEvent.getTime();
        StringBuilder sb = new StringBuilder(getEventString(event));
        sb.append(String.format(" : Taken: %s ", //$NON-NLS-1$
                CorrelatingEventHandler.getElapsedTimeString(elapsed)));
        outputLine(sb.toString());
    }

    /**
     * @see com.tibco.xpd.resources.logger.events.EventProcessor.EventHandler#handleLog(com.tibco.xpd.resources.logger.events.EventProcessor.Event)
     * 
     * @param event
     */
    @Override
    public void handleLog(Event event) {
        outputLine(getEventString(event));
    }

    /**
     * Prints the details of the event to the stream.
     * 
     * @param event
     *            the event to be logged.
     */
    protected String getEventString(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append("*** ").append(event.getEventType().toString()); //$NON-NLS-1$
        sb.append(' ').append(event.getName()).append(' ');
        if (event.getLabels() != null && event.getLabels().length > 0) {
            sb.append(event.getLabelsString());
        }
        return sb.toString();
    }
}
