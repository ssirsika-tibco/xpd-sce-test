/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.wc;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

/**
 * An extension to the <code>PropertyChangeEvent</code> that also supplies the
 * event {@link Notification}s.
 * 
 * @author njpatel
 * 
 * @since 3.1
 * 
 */
public class NotificationPropertyChangeEvent extends PropertyChangeEvent {

    /**
     * Generated
     */
    private static final long serialVersionUID = 325504948208805465L;
    private final Collection<Notification> notifications;

    /**
     * <code>PropertyChangeEvent</code> that supplies the event
     * {@link Notification}s.
     * 
     * @param source
     *            event source
     * @param propertyName
     *            event name
     * @param oldValue
     *            old value
     * @param newValue
     *            new value
     * @param notifications
     *            event notifications
     */
    public NotificationPropertyChangeEvent(Object source, String propertyName,
            Object oldValue, Object newValue,
            Collection<Notification> notifications) {
        super(source, propertyName, oldValue, newValue);
        this.notifications = notifications;
    }

    /**
     * Get the event {@link Notification}s.
     * 
     * @return collection of notifications, or <code>null</code> or empty
     *         collection if no notifications available.
     */
    public Collection<Notification> getNotifications() {
        return notifications;
    }

}
