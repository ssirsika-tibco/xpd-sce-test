/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.selector;

import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.Search;

/**
 * A helper class to associate a search with a specific registry for the
 * purposes of representing a search in the tree content provider.
 * 
 * @author nwilson
 */
public class RegistrySearch {

    /** The registry. */
    private final Registry registry;
    /** The search. */
    private final Search search;
    private Exception exception;
    private Object[] children;
    private final Uddi4jContentProvider contentProvider;

    /**
     * @param registry
     *            The registry.
     * @param search
     *            The search.
     */
    public RegistrySearch(Registry registry, Search search,
            Uddi4jContentProvider contentProvider) {
        this.registry = registry;
        this.search = search;
        this.contentProvider = contentProvider;
    }

    /**
     * @return The registry.
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @return The search.
     */
    public Search getSearch() {
        return search;
    }

    /**
     * @param obj
     *            The object to test.
     * @return true if registry and search are equal otherwise false.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj == this) {
            equal = true;
        } else {
            if (obj instanceof RegistrySearch) {
                RegistrySearch other = (RegistrySearch) obj;
                if (registry.equals(other.registry)
                        && search.equals(other.search)) {
                    equal = true;
                }
            }
        }
        return equal;
    }

    /**
     * @return Sum of registry and search hashcodes.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return registry.hashCode() + search.hashCode();
    }

    /**
     * @return The search name.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return search.toString();
    }

    public void update(boolean labelsOnly) {
        if (contentProvider != null) {
            contentProvider.updateRegistrySearch(this, labelsOnly);
            PlatformUI.getWorkbench().getDecoratorManager().update(
                    FailureLabelDecorator.SEARCH_FAILURE_DECORATOR_ID);
        }
    }

    /**
     * Sets exception on the search. Exception should be set if last search
     * attempt returned exception.
     * 
     * @param exception
     *            The exception thrown during last search attempt or null if
     *            attempt was successful.
     */
    public synchronized void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * Returns the last search attempt exception or null if attempt was
     * successful.
     * 
     * @return
     */
    public synchronized Exception getException() {
        return exception;
    }

    /**
     * Sets the children.
     * 
     * @param children
     */
    public synchronized void setChildren(Object[] children) {
        this.children = children;
    }

    /**
     * @return Children of the search or null if the search didn't run yet.
     */
    public synchronized Object[] getChildren() {
        return children;
    }

}
