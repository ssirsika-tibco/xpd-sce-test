/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.indexer;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Indexer service monitor all working copies and keep the indexes in sync with
 * live changes in the models.
 * 
 * @author wzurek
 */
public interface IndexerService {

    /**
     * This method executes a query in the resource with the given criteria.
     * 
     * @param indexerId
     *            id the ID of the indexer as contributed in extension point.
     * @param criteria
     *            'query by example' type of query criteria.
     * @return list of items from given provider that match given criteria.
     */
    public Collection<IndexerItem> query(String indexerId, IndexerItem criteria);

    /**
     * This will be removed before 3.0.0 GA
     * 
     * @deprecated use {@link #query(String, IndexerItem)}
     */
    @Deprecated
    public Collection<IndexerItem> query(String indexerId,
            Map<String, String> criteria);

    /**
     * This method removes all database records for the project and if project
     * exists and is open re-populates it. Messages about the progress are
     * reported as subtasks to the progress monitor.
     * 
     * @param project
     * @param monitor
     */
    public void clean(IProject project, IProgressMonitor monitor);

}
