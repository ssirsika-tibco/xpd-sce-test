/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

/**
 * @author nwilson
 */
public interface IRegistryListener {
    /**
     * @param registry
     *            The registry to which the search was added.
     * @param search
     *            The search that was added.
     */
    void searchAdded(Registry registry, Search search);

    /**
     * @param registry
     *            The registry from which the search was removed.
     * @param search
     *            The search that was removed.
     */
    void searchRemoved(Registry registry, Search search);

}
