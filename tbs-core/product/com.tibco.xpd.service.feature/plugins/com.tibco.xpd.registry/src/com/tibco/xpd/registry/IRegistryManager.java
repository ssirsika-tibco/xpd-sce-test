/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

import java.util.Collection;

/**
 * @author nwilson
 */
public interface IRegistryManager {
    /**
     * @param registry
     *            The registry to add.
     */
    void addRegistry(Registry registry);

    /**
     * @param registry
     *            The registry to remove.
     */
    void removeRegistry(Registry registry);

    /**
     * @return A collection of available registries.
     */
    Collection<Registry> getRegistries();

    /**
     * Adds a new registry manager listener.
     * 
     * @param listener
     *            The new registry manager listener.
     */
    void addRegistryManagerListener(IRegistryManagerListener listener);

    /**
     * Removes a registry manager listener.
     * 
     * @param listener
     *            The listener to remove.
     */
    void removeReportListener(IRegistryManagerListener listener);

    /**
     * Notifies all listeners about registry change.
     */
    void updateRegistry(Registry registry);

    /**
     * Persist the the current array of Registry objects.
     */
    public void saveRegisters();
}
