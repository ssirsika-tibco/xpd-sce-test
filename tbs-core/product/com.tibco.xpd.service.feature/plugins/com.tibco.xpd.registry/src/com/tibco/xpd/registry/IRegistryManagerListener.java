/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

/**
 * @author nwilson
 */
public interface IRegistryManagerListener {

    /**
     * Callback to indicate that the registry has substantially changed.
     */
    void registryChanged();

    /**
     * Callback to indicate that the registry list has added.
     * 
     * @param registry
     *            The registry added.
     */
    void registryAdded(Registry registry);

    /**
     * Callback to indicate that the registry list has removed.
     * 
     * @param registry
     *            The registry removed.
     */
    void registryRemoved(Registry registry);

}
