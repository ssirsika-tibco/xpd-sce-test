/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.internal.wc;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.tibco.xpd.resources.IWorkingCopyCreationListener;
import com.tibco.xpd.resources.WorkingCopy;

/**
 * Manager class that maintains listeners to the <code>WorkingCopy</code>
 * creation. When a working copy is created it will call
 * {@link #fireWorkingCopyCreated(WorkingCopy)
 * fireWorkingCopyCreated(WorkingCopy)} to inform all listeners of the creation.
 * <p>
 * Use {@link #getInstance() getInstance()} to get an instance of this class.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class WorkingCopyCreationListenersManager {

    private static final WorkingCopyCreationListenersManager INSTANCE = new WorkingCopyCreationListenersManager();

    /**
     * set of listeners.
     */
    private final Set<IWorkingCopyCreationListener> listeners;

    /**
     * Private constructor so this class cannot be instantiated
     */
    private WorkingCopyCreationListenersManager() {
        listeners = new CopyOnWriteArraySet<IWorkingCopyCreationListener>();
    }

    /**
     * Get the singleton instance of this class.
     * 
     * @return <code>WorkingCopyCreationListenersManager</code>
     */
    public static WorkingCopyCreationListenersManager getInstance() {
        return INSTANCE;
    }

    /**
     * Add a working copy creation listener.
     * 
     * @param listener
     */
    public void addListener(IWorkingCopyCreationListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Remove a working copy creation listener.
     * 
     * @param listener
     */
    public void removeListener(IWorkingCopyCreationListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Fire a working copy created event to all registered listeners.
     * 
     * @param wc
     */
    public void fireWorkingCopyCreated(WorkingCopy wc) {
        if (wc != null) {
            for (IWorkingCopyCreationListener listener : listeners) {
                listener.workingCopyCreated(wc);
            }
        }
    }
}
