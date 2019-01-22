/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.resources;

import java.beans.PropertyChangeEvent;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * An interface to be implemented to receive resource change events.
 * 
 */
public interface RCPResourceChangeListener {

    /**
     * RCP resource change event type.
     * 
     * @author njpatel
     * 
     */
    public enum RCPResourceEventType {
        /**
         * Dirty state changed.
         */
        DIRTY,
        /**
         * Contents of a resource in the project has changed.
         */
        CHANGED,
        /**
         * MAA resource has been disposed
         */
        DISPOSED,
        /**
         * New resource has been added to the project
         */
        ADDED,
        /**
         * Resource has been deleted from the project
         */
        REMOVED,
        /**
         * An maa archive has been created for the application (on first save of
         * new application)
         */
        ARCHIVE_CREATED,
    }

    public static class RCPResourceChangeEvent {
        /**
         * Type of event.
         */
        public RCPResourceEventType eventType;

        /**
         * if event type is CHANGED then this will contain the
         * {@link PropertyChangeEvent} which will have been triggered from a
         * {@link WorkingCopy} change. If event type is ADDED or REMOVED then
         * the eventObj will be the resource that was added/removed.
         */
        public Object eventObj;

        /**
         * The actual source that caused this change event (this will probably
         * be the ProjectResource).
         */
        public IRCPResource source;

        /**
         * 
         */
        public RCPResourceChangeEvent(RCPResourceEventType type, Object evtObj,
                IRCPResource source) {
            eventType = type;
            eventObj = evtObj;
            this.source = source;
        }
    }

    /**
     * Notification that the given resource has changed state.
     * 
     * @param resource
     *            the resource that fired this event
     * @param event
     *            event information
     * 
     */
    void resourceChanged(IRCPResource resource, RCPResourceChangeEvent event);
}