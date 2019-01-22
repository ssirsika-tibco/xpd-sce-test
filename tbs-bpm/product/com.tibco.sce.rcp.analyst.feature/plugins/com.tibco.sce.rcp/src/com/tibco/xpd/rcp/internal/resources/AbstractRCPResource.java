/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceChangeEvent;
import com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener.RCPResourceEventType;

/**
 * 
 * 
 * @author njpatel
 * @since 23 Nov 2012
 */
public abstract class AbstractRCPResource implements IRCPResource,
        Comparable<AbstractRCPResource> {

    private boolean stopListening = false;

    private final List<RCPResourceChangeListener> listeners;

    private boolean lastDirtyState;

    /**
     * 
     */
    public AbstractRCPResource() {
        listeners =
                Collections
                        .synchronizedList(new ArrayList<RCPResourceChangeListener>());
    }

    /**
     * Check whether this resource should stop listening to the underlying
     * resource. This would be called when an action this resource is going to
     * activate will cause the underlying project to send events (e.g. during
     * save).
     * 
     * @return
     */
    protected final boolean shouldStopListener() {
        return stopListening;
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#save(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param monitor
     * @return
     * @throws CoreException
     */
    @Override
    public final boolean save(IProgressMonitor monitor) throws CoreException {
        if (isDirty()) {
            stopListening = true;
            try {
                return doSave(monitor);
            } finally {
                stopListening = false;

                notifyDirtyChange(isDirty());
            }
        }
        return false;
    }

    /**
     * Send notification of dirty state change if required.
     */
    protected void notifyDirtyChange(boolean isDirty) {
        // Only send out notification if the state has changed since last
        // notification
        if (lastDirtyState != isDirty) {
            lastDirtyState = isDirty;
            notifyResourceChange(RCPResourceEventType.DIRTY, null, this);
        }
    }

    /**
     * Save the resource.
     * 
     * @param monitor
     * @return <code>true</code> if the save was done, <code>false</code> if
     *         save was not done (or used cancelled)
     */
    protected abstract boolean doSave(IProgressMonitor monitor)
            throws CoreException;

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#delete()
     * 
     * @throws CoreException
     */
    @Override
    public final void delete() throws CoreException {
        stopListening = true;
        try {
            doDelete();
        } finally {
            stopListening = false;
        }
    }

    /**
     * Delete the underlying resource being managed by this instance.
     */
    protected abstract void doDelete() throws CoreException;

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#addChangeListener(com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener)
     * 
     * @param listener
     */
    @Override
    public final void addChangeListener(RCPResourceChangeListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * @see com.tibco.xpd.rcp.internal.resources.IRCPResource#removeChangeListener(com.tibco.xpd.rcp.internal.resources.RCPResourceChangeListener)
     * 
     * @param listener
     */
    @Override
    public final void removeChangeListener(RCPResourceChangeListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Notify listeners of a change to this resource
     * 
     * @param eventType
     *            change type
     * @param eventObj
     *            additional information on the change, can be <code>null</code>
     *            .
     */
    protected final void notifyResourceChange(RCPResourceEventType eventType,
            Object eventObj, IRCPResource source) {
        synchronized (listeners) {
            for (RCPResourceChangeListener listener : listeners) {
                listener.resourceChanged(this, new RCPResourceChangeEvent(
                        eventType, eventObj, source));
            }
        }
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(AbstractRCPResource other) {
        String name =
                getName() != null ? getName() : getPath() != null ? getPath()
                        .toString() : ""; //$NON-NLS-1$
        String otherName =
                other.getName() != null ? other.getName()
                        : other.getPath() != null ? other.getPath().toString()
                                : ""; //$NON-NLS-1$

        return name.compareTo(otherName);
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (getPath() != null) {
            return getPath().toString();
        } else if (getName() != null) {
            return getName();
        }
        return super.toString();
    }
}
