/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.Collection;
import java.util.concurrent.DelayQueue;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationActivator.ValidatorStatus;
import com.tibco.xpd.validation.internal.LiveValidationItem;

/**
 * Validation queue. This will queue up validation items for the engine to
 * process.
 * 
 * @author nwilson
 */
public class ValidationQueue {
    /** The validation queue. */
    private DelayQueue<LiveValidationItem> queue;

    /**
     * Constructor.
     */
    public ValidationQueue() {
        queue = new DelayQueue<LiveValidationItem>();
    }

    /**
     * Add the object to validate to the queue.
     * 
     * @param wc
     *            The validation resource.
     * @param objects
     *            The objects to add to the queue.
     * @param clean
     *            <code>true</code> if all of the markers should be cleaned
     *            first.
     */
    public void add(WorkingCopy wc, Collection<EObject> objects, boolean clean) {
        add(wc, objects, clean, null);
    }

    /**
     * Add the object to validate to the queue.
     * 
     * @param wc
     *            The validation resource.
     * @param objects
     *            The object to add to the queue.
     * @param clean
     *            <code>true</code> if all markers should be cleaned first.
     * @param notifications
     *            resource change <code>Notification</code>s that caused this
     *            validation.
     * 
     * @since 3.1
     */
    public void add(WorkingCopy wc, Collection<EObject> objects, boolean clean,
            Collection<Notification> notifications) {

        if (ValidationActivator.getDefault().getStatus() == ValidatorStatus.READY) {
            ValidationActivator.getDefault()
                    .setStatus(ValidatorStatus.SCHEDULED);
        }

        LiveValidationItem vo = null;
        for (LiveValidationItem next : queue) {
            if (wc.equals(next.getWorkingCopy())) {
                vo = next;
                vo.addObjects(objects);
                vo.addNotifications(notifications);
                vo.reset();
                break;
            }
        }
        if (vo == null) {
            vo = new LiveValidationItem(wc, notifications);
            vo.addObjects(objects);
            queue.add(vo);
        }
        if (clean) {
            vo.setClean(clean);
        }
    }

    /**
     * @return The next available object for validation.
     * @throws InterruptedException
     *             If interrupted while waiting.
     */
    public LiveValidationItem getNext() throws InterruptedException {
        return queue.take();
    }

    /**
     * Method for testing purposes only.
     * 
     * @return if the queue is empty.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
