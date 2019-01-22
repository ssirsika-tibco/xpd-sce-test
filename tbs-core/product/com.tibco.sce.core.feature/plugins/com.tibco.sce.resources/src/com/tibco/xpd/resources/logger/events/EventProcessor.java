/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.logger.events;

import org.eclipse.core.runtime.Assert;

import com.tibco.xpd.resources.logger.events.EventProcessor.Event.EventType;

/**
 * Utility to handle begin/end/log or custom events in a uniform way and
 * possibly measure time elapsed between events.
 * 
 * The typical usage would be to create instance of event processor passing and
 * passing appropriate event handler.
 * 
 * <pre>
 * Example usage:
 *    ...
 *    EventProcessor ep = new EventProcessor(new PrintEventHandler(),
 *                         new SummaryEventHandler());
 *    ep.begin("First Activity", "a", "b");
 *    thread.sleep(3000);
 *    ep.end("First Activity", "a", "b");
 * 
 *    ep.begin("Second Activity", "a");
 *    thread.sleep(3000);
 *    ep.end("Second Activity", "a");
 * 
 *    ep.runTimedAction("Timed Action", new TimedAction() {
 *             
 *             public void run() throws Exception {
 *                 thread.sleep(3000);
 *             }
 *         }, "c", "d", "e");
 * ep.event(SummaryEventHandler.PRINT_SUMMARY, "Test Report");
 * 
 * <pre>
 * @author jarciuch
 * @since 24 Nov 2011
 */
public class EventProcessor {

    /**
     * Represent one trace event. Event have a name and a list of labels which
     * can be used by handlers for corelating, gathering statistics, logging,
     * etc. All events are timed.
     * 
     */
    public static class Event {

        public enum EventType {
            BEGIN, END, LOG, CUSTOM
        }

        /**
         * Current time in nanoseconds.
         */
        private final long time;

        /**
         * Current time in nanoseconds.
         */
        private final long timeNano;

        private final String pid;

        private final EventType eventType;

        private final String name;

        private final String[] labels;

        /**
         * Represents custom data object which can be associated with event.
         */
        private final Object data;

        /**
         * String representing labels in form:
         * "[label-1, label-2, ..., label-n]".
         */
        private String labelsString;

        public Event(EventType eventType, String name, Object data, String pid,
                long time, long timeNano, String[] labels) {
            this.eventType = eventType;
            this.name = name;
            this.pid = pid;
            this.time = time;
            this.timeNano = timeNano;
            this.labels = labels;
            this.data = data;
        }

        public Event(EventType eventType, String name, String[] labels) {
            this(eventType, name, null, Long.toString(Thread.currentThread()
                    .getId()), System.currentTimeMillis(), System.nanoTime(),
                    labels);
        }

        /**
         * Creates an event.
         * 
         * @param eventType
         *            the type of event @see {@link EventType}.
         * @param name
         *            name of the event.
         * @param data
         *            the custom data which will be stored in the event or
         *            'null'. Event processors might cache events and
         *            referencing objects from events might prevent garbage
         *            collection.
         * @param labels
         *            additional string labels.
         */
        public Event(EventType eventType, String name, Object data,
                String[] labels) {
            this(eventType, name, data, Long.toString(Thread.currentThread()
                    .getId()), System.currentTimeMillis(), System.nanoTime(),
                    labels);
        }

        /**
         * @return the pid
         */
        public String getPid() {
            return pid;
        }

        /**
         * @return the time in nanosecods.
         */
        public long getTimeNano() {
            return timeNano;
        }

        /**
         * @return the time in miliseconds.
         */
        public long getTime() {
            return time;
        }

        /**
         * @return the event type
         */
        public EventType getEventType() {
            return eventType;
        }

        public String getName() {
            return name;
        }

        /**
         * @return labels for event.
         */
        public String[] getLabels() {
            return labels;
        }

        @Override
        public String toString() {
            return String
                    .format("%1$s:%2$s:%3$s:%4$d milisec.:%5$d nanosec.:%6$s", //$NON-NLS-1$
                            eventType.toString(),
                            name,
                            pid,
                            getTime(),
                            getTimeNano(),
                            getLabelsString());
        }

        protected String createLablesString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            boolean first = true;
            for (String label : getLabels()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(',').append(' ');
                }
                sb.append(label);
            }
            sb.append(']');
            return sb.toString();
        }

        /**
         * Returns string representing labels in form:
         * "[label-1, label-2, ..., label-n]".
         */
        public String getLabelsString() {
            if (labelsString == null) {
                labelsString = createLablesString();
            }
            return labelsString;
        }

        /**
         * Returns custom data object which can be associated with event or
         * 'null' if not set.
         * 
         * @return custom data object which can be associated with event.
         */
        public Object getData() {
            return data;
        }
    }

    /**
     * Abstract class base for all events handlers.
     * 
     * @author jarciuch
     * @since 1 Dec 2011
     */
    public abstract static class EventHandler {

        private boolean enabled = true;

        protected void handleEvent(Event event) {
            switch (event.eventType) {
            case BEGIN:
                handleBegin(event);
                break;
            case END:
                handleEnd(event);
                break;
            case LOG:
                handleLog(event);
                break;
            case CUSTOM:
                handleCustomEvent(event);
                break;
            default:
                Assert.isLegal(false, "Unsupported event type."); //$NON-NLS-1$
                break;
            }
        }

        /**
         * Handles BEGIN event.
         * 
         * @param event
         */
        public void handleBegin(Event event) {
        }

        /**
         * Handles END event.
         * 
         * @param event
         */
        public void handleEnd(Event event) {
        }

        /**
         * Handles LOG event.
         * 
         * @param event
         */
        public void handleLog(Event event) {
        }

        /**
         * Handles CUSTOM event.
         * 
         * @param event
         */
        public void handleCustomEvent(Event event) {
        }

        /**
         * @return the enabled
         */
        public synchronized boolean isEnabled() {
            return enabled;
        }

        /**
         * @param enabled
         *            the enabled to set
         */
        public synchronized void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

    }

    private final EventHandler[] handlers;

    /**
     * Creates the processor with the handler to process events.
     */
    public EventProcessor(EventHandler... handler) {
        handlers = handler;
    }

    /**
     * Indicates begin of activity.
     * 
     * @param name
     *            name of the activity.
     * @param labels
     *            additional (optional) labels.
     */
    public void begin(String name, String... labels) {
        begin(name, null, labels);
    }

    /**
     * Indicates begin of activity.
     * 
     * @param name
     *            name of the activity.
     * @param data
     *            the custom data which will be stored in the event or 'null'.
     *            Event processors might cache events and referencing objects
     *            from the events might prevent their garbage collection.
     * @param labels
     *            additional (optional) labels.
     */
    public void begin(String name, Object data, String... labels) {
        Event event = new Event(EventType.BEGIN, name, data, labels);
        processEvent(event);
    }

    /**
     * Indicates end of activity. The name (and optionally labels if provided
     * should match corresponding start event) so the correlation can be made by
     * the handler if it's required.
     * 
     * @param name
     *            name of the activity.
     * @param labels
     *            additional (optional) labels.
     */
    public void end(String name, String... labels) {
        end(name, null, labels);

    }

    /**
     * Indicates end of activity. The name (and optionally labels if provided
     * should match corresponding start event) so the correlation can be made by
     * the handler if it's required.
     * 
     * @param name
     *            name of the activity.
     * @param data
     *            the custom data which will be stored in the event or 'null'.
     *            Event processors might cache events and referencing objects
     *            from the events might prevent their garbage collection.
     * @param labels
     *            additional (optional) labels.
     */
    public void end(String name, Object data, String... labels) {
        Event event = new Event(EventType.END, name, data, labels);
        processEvent(event);

    }

    /**
     * Indicates log activity.
     * 
     * @param name
     *            name of the activity.
     * @param labels
     *            additional (optional) labels.
     */
    public void log(String name, String... labels) {
        log(name, null, labels);
    }

    /**
     * Indicates log activity.
     * 
     * @param name
     *            name of the activity.
     * @param data
     *            the custom data which will be stored in the event or 'null'.
     *            Event processors might cache events and referencing objects
     *            from the events might prevent their garbage collection.
     * @param labels
     *            additional (optional) labels.
     */
    public void log(String name, Object data, String... labels) {
        Event event = new Event(EventType.LOG, name, data, labels);
        processEvent(event);
    }

    /**
     * Indicates custom event. It's up to the event processors how it will be
     * handled.
     * 
     * @param name
     *            name of the activity.
     * @param labels
     *            additional (optional) labels.
     */
    public void event(String name, String... labels) {
        event(name, null, labels);
    }

    /**
     * Indicates custom event. It's up to the event processors how it will be
     * handled.
     * 
     * @param name
     *            name of the activity.
     * @param data
     *            the custom data which will be stored in the event or 'null'.
     *            Event processors might cache events and referencing objects
     *            from the events might prevent their garbage collection.
     * @param labels
     *            additional (optional) labels.
     */
    public void event(String name, Object data, String... labels) {
        Event event = new Event(EventType.CUSTOM, name, data, labels);
        processEvent(event);
    }

    /**
     * Process event.
     * 
     * @param event
     *            the event to process.
     */
    protected void processEvent(Event event) {
        for (EventHandler handler : handlers) {
            if (handler.isEnabled()) {
                handler.handleEvent(event);
            }
        }
    }

    /**
     * Runs TimedAction around begin-end events.
     * 
     * @param name
     *            the name of the begin and end events.
     * @param action
     *            the action to run.
     * @param labels
     *            the lablels of the begin and end events.
     * @throws Exception
     *             exception thrown from within {@link TimedAction#run()}
     *             method.
     * 
     */
    public void runTimedAction(String name, TimedAction action,
            String... labels) throws Exception {
        begin(name, labels);
        try {
            action.run();
        } finally {
            end(name, labels);
        }
    }
}
