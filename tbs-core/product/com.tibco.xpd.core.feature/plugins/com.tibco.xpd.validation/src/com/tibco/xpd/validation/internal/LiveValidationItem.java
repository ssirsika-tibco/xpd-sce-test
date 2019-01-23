/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.provider.IValidationItem2;

/**
 * Validation item (working copy).
 * 
 * @author nwilson
 */
public class LiveValidationItem implements Delayed, IValidationItem2 {

    /** The delay before validation. */
    private static final long DELAY = 800;

    /** The validation resource. */
    private WorkingCopy wc;

    /** The validation objects. */
    private Collection<EObject> objects;

    /** The validation due time. */
    private long due;

    /** Flag to indicate if markers should be cleaned. */
    private boolean clean;

    private List<Notification> notifications;

    /**
     * Validation item.
     * 
     * @param wc
     *            resource to validate.
     */
    public LiveValidationItem(WorkingCopy wc) {
        this.wc = wc;
        clean = false;
        objects = new HashSet<EObject>();
        reset();
    }

    /**
     * Validation item.
     * 
     * @param wc
     *            resource to validate.
     * @param notifications
     *            {@link Notification}s from the resource that caused the
     *            validation.
     */
    public LiveValidationItem(WorkingCopy wc, Collection<Notification> notifications) {
        this(wc);
        if (notifications != null) {
            this.notifications = new ArrayList<Notification>(notifications);
        }
    }

    /**
     * Add objects to the list of objects to validate in this validation item.
     * 
     * @param toAdd
     *            The objects to add.
     */
    public void addObjects(Collection<EObject> toAdd) {
        if (toAdd != null && !toAdd.isEmpty()) {
            objects.addAll(toAdd);
        }
    }

    /**
     * Add notifications to the list of notifications in this validation item.
     * 
     * @param notificationsToAdd
     * @since 3.3
     */
    public void addNotifications(Collection<Notification> notificationsToAdd) {
        if (notificationsToAdd != null && !notificationsToAdd.isEmpty()) {
            if (notifications == null) {
                notifications = new ArrayList<Notification>();
            }
            notifications.addAll(notificationsToAdd);
        }
    }

    /**
     * Resets the timer for this validation.
     */
    public void reset() {
        due = System.currentTimeMillis() + DELAY;
    }

    /**
     * @param unit
     *            The time unit for the delay.
     * @return The remaining delay.
     * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
     */
    public long getDelay(TimeUnit unit) {
        long now = System.currentTimeMillis();
        return unit.convert(due - now, TimeUnit.MILLISECONDS);
    }

    /**
     * @param other
     *            The object to compare to.
     * @return The comparison results.
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Delayed other) {
        return (int) (getDelay(TimeUnit.NANOSECONDS) - other
                .getDelay(TimeUnit.NANOSECONDS));
    }

    /**
     * @return The validation resource.
     */
    public WorkingCopy getWorkingCopy() {
        return wc;
    }

    /**
     * @return The validation objects.
     */
    public Collection<EObject> getObjects() {
        return Collections.unmodifiableCollection(objects);
    }

    /**
     * @param obj
     *            The object to check for equality.
     * @return true if the contained objects are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (wc != null && obj != null && obj instanceof LiveValidationItem) {
            equal = wc.equals(((LiveValidationItem) obj).getWorkingCopy());
        }
        return equal;
    }

    /**
     * @return The hashcode of the contained object.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return wc == null ? -1 : wc.hashCode();
    }

    /**
     * @param clean
     *            flag to indicate if markers should be cleaned.
     */
    public void setClean(boolean clean) {
        this.clean = clean;
    }

    /**
     * @return true if markers should be cleaned.
     */
    public boolean getClean() {
        return clean;
    }

    /**
     * @return true.
     * @see com.tibco.xpd.validation.provider.IValidationItem#affectMarkers()
     */
    public boolean affectMarkers() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.provider.IValidationItem2#getNotifications()
     */
    public Collection<Notification> getNotifications() {
        return notifications;
    }
}
