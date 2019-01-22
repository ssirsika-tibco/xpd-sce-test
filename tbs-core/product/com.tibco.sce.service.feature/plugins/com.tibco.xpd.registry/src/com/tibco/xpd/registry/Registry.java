/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Storage class for registry definition data.
 * 
 * @author nwilson
 */
public class Registry implements Serializable {

    /** Serial UID. */
    private static final long serialVersionUID = -9151338301591851250L;

    /** The registry name. */
    private String name;

    /** The query manager/inquiry URL. */
    private String queryManagerUrl;

    /** The life cycle manager/publish URL. */
    private String lifeCycleManagerUrl;

    /** The registry semantic equivalences. */
    private String semanticEquivalences;

    /** The authentication method. */
    private String authenticationMethod;

    /** A list of searches assigned to this registry. */
    private final ArrayList<Search> searches;

    /** Registry change listeners. */
    private transient Collection<IRegistryListener> listeners;

    /**
     * Constructor.
     */
    public Registry() {
        searches = new ArrayList<Search>();
        listeners = new HashSet<IRegistryListener>();
    }

    /**
     * @return The authentication method.
     */
    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    /**
     * @param authenticationMethod
     *            The authentication method.
     */
    public void setAuthenticationMethod(final String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    /**
     * @return The life cycle manager/publish URL.
     */
    public String getLifeCycleManagerUrl() {
        return lifeCycleManagerUrl;
    }

    /**
     * @param lifeCycleManagerUrl
     *            The life cycle manager/publish URL.
     */
    public void setLifeCycleManagerUrl(final String lifeCycleManagerUrl) {
        this.lifeCycleManagerUrl = lifeCycleManagerUrl;
    }

    /**
     * @return The registry name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The registry name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return The query manager/inquiry URL.
     */
    public String getQueryManagerUrl() {
        return queryManagerUrl;
    }

    /**
     * @param queryManagerUrl
     *            The query manager/inquiry URL.
     */
    public void setQueryManagerUrl(final String queryManagerUrl) {
        this.queryManagerUrl = queryManagerUrl;
    }

    /**
     * @return The registry semantic equivalences.
     */
    public String getSemanticEquivalences() {
        return semanticEquivalences;
    }

    /**
     * @param semanticEquivalences
     *            The registry semantic equivalences.
     */
    public void setSemanticEquivalences(final String semanticEquivalences) {
        this.semanticEquivalences = semanticEquivalences;
    }

    /**
     * @param search
     *            The search to add.
     */
    public void addSearch(Search search) {
        searches.add(search);
        notifySearchAdded(search);
    }

    /**
     * @param search
     *            The search to remove.
     */
    public void removeSearch(Search search) {
        searches.remove(search);
        notifySearchRemoved(search);
    }

    /**
     * @return A list of searches.
     */
    public List<Search> getSearches() {
        return Collections.unmodifiableList(searches);
    }

    /**
     * @return The name of the registry.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * @param search
     *            The search added.
     */
    private void notifySearchAdded(Search search) {
        for (IRegistryListener listener : listeners) {
            listener.searchAdded(this, search);
        }
    }

    /**
     * @param search
     *            The search removed.
     */
    private void notifySearchRemoved(Search search) {
        for (IRegistryListener listener : listeners) {
            listener.searchRemoved(this, search);
        }
    }

    /**
     * @param listener
     *            The listener to add.
     */
    public void addRegistryListener(IRegistryListener listener) {
        if (listeners == null) {
            listeners = new HashSet<IRegistryListener>();
        }
        listeners.add(listener);
    }

    /**
     * @param listener
     *            The listener to remove.
     */
    public void removeRegistryListener(IRegistryListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }
}
