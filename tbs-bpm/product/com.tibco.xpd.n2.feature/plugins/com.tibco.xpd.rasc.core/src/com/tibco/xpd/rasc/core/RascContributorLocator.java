/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core;

import java.util.List;

/**
 * Locates and instantiates the registered {@link RascContributor}
 * implementations.
 *
 * @author pwatson
 * @since 1 Mar 2019
 */
public interface RascContributorLocator {
    /**
     * Returns list of the RascContributor implementation instances. The list
     * will be immutable and ordered according to the order in which they are to
     * be invoked.
     * 
     * @return the ordered collection of RascContributor instances.
     */
    public List<RascContributor> getContributors();
}
