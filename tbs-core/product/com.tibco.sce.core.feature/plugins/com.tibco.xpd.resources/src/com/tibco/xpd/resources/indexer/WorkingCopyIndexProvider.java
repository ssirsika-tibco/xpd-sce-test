/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.indexer;

import java.beans.PropertyChangeEvent;
import java.util.Collection;

import com.tibco.xpd.resources.WorkingCopy;

/**
 * Indexer provider is an interface that should be implemented by clients that
 * want to provide index for working copy using extension point.
 * 
 * @author wzurek
 */
public interface WorkingCopyIndexProvider {

    /**
     * Return all indexer items for given working copy.
     * 
     * @param wc
     * @return collection of items to add to the indexer, empty collection if none to be added.
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc);

    /**
     * Test if given working copy change event is relevant for the indexer. This
     * is invoked by the indexer service for every change in the working copy
     * and if it returns 'true', the index will be reconciled with the result of
     * {@link #getIndexItems(WorkingCopy)} method.
     * 
     * @param event
     *            WorkingCopy change event
     * @return true when the change is affecting the index.
     */
    public boolean isAffectingEvent(PropertyChangeEvent event);

}
