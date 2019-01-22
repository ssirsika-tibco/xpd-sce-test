/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.fragments;

import java.util.Set;

import org.eclipse.jface.viewers.IFilter;

/**
 * Provider binding data. This contains the bound provider id and any additional
 * filters specified for a given binding.
 * 
 * @author njpatel
 * 
 */
public class ProviderBinding {
    private String providerId;

    private Set<IFilter> filters;

    public ProviderBinding(String providerId, Set<IFilter> filters) {
        this.providerId = providerId;
        this.filters = filters;
    }

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @return the filters
     */
    public Set<IFilter> getFilters() {
        return filters;
    }
}