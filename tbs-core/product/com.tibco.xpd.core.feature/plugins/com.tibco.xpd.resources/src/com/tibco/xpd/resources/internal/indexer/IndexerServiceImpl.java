/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.internal.indexer;

import static com.tibco.xpd.resources.XpdResourcesPlugin.traceINDEX;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexResourceMatcher;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.internal.XpdResourcesDebugOptions;
import com.tibco.xpd.resources.internal.db.Column;
import com.tibco.xpd.resources.internal.db.IResourceDb;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.db.ResourceDbFactory;

/**
 * Indexer service implementation that updates the indexer with items from
 * registered providers.
 * <p>
 * Since 3.3 this indexer service will be explicitly called from the validation
 * framework to index the models. The Builder will call this when a build is
 * called to index the affected resources and the "live" validation will call
 * when a working copy change event is received (or new working copy is
 * created).
 * </p>
 * 
 * @author rassisi
 * 
 */
public class IndexerServiceImpl implements IndexerService {

    private static final String TIMESTAMP_FRAGMENT = "TIMESTAMP"; //$NON-NLS-1$

    /**
     * name of the table that holds all versions related to the different
     * providers.
     */
    public static final String VERSION_TABLE_NAME = "versions"; //$NON-NLS-1$

    /**
     * The Db version. Every time the schema of the database changes the version
     * should be changed (increased) as well. This will trigger the drop of the
     * resource table and the recreation of it.
     */
    public static final String ATTRIBUTE_VERSION = "internal_version"; //$NON-NLS-1$

    /**
     * every index provider has it's own table. To be able to migrate it when
     * the schema changes a version is linked to it.
     */
    public static final String ATTRIBUTE_VERSION_TABLE_NAME =
            "internal_version_table_name"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_name' one of them.
     */
    public static final String ATTRIBUTE_NAME = "internal_name"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_type' one of them.
     */
    public static final String ATTRIBUTE_TYPE = "internal_type"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_uri' one of them.
     */
    public static final String ATTRIBUTE_URI = "internal_uri"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_path' one of them.
     */
    public static final String ATTRIBUTE_PATH = "internal_path"; //$NON-NLS-1$

    /**
     * Though the implementation can specify its own columns in the database,
     * some columns have to be always there. the 'internal_project' one of them.
     */
    public static final String ATTRIBUTE_PROJECT = "internal_project"; //$NON-NLS-1$

    public static final String ATTRIBUTE_UPDATETIMESTAMP =
            "internal_updatetimestamp"; //$NON-NLS-1$

    /**
     * persistent attribute that indicats wether a resource was already indexed.
     */
    public final static String INDEXED_RESOURCE = "key://indexedResource"; //$NON-NLS-1$

    /**
     * local part of the persistent attribute that indicats wether a resource
     * was already indexed.
     */
    public final static String XPD = "XPD"; //$NON-NLS-1$

    /**
     * persistent attribute key that indicats wether a resource was already
     * indexed.
     */
    public final static QualifiedName XPD_RESOURCE_INDEXED = new QualifiedName(
            INDEXED_RESOURCE, XPD);

    /**
     * This flag is only used for JUnit Tests.
     */
    public final static int IS_AFFECTED_METHOD_NOT_EFFECTED = 1;

    /**
     * This flag is only used for JUnit Tests.
     */
    public final static int IS_AFFECTED_METHOD_EFFECTED = 2;

    /***
     * Indexers can now define their own primary keys thru the primary key
     * configuration element if they dont want to go with the default URI
     * primary key. This primaryKeysForTheIndexer would be used to determine if
     * the indexer is having any primary key(s) defined
     */
    private static String[] indexerKeyAttributes = new String[0];

    /**
     * the database provider
     */
    private static IResourceDb resourceDb;
    
    /**
     * WSDL indexer id.
     */
    public static final String WSDLINDEXER_ID = "com.tibco.xpd.wsdl.wsdlIndexer"; //$NON-NLS-1$


    /**
     * Represents each extension of the index provider.
     * 
     * @author njpatel
     * 
     */
    private class Indexer {
        /**
         * Indexer provider id
         */
        String id;

        /**
         * Working copy index provider
         */
        WorkingCopyIndexProvider provider;

        /**
         * Dynamic resource matcher used by the indexer if programmatic control
         * over the files to index is required.
         */
        IndexResourceMatcher matcher;
    }

    /**
     * Map of file patterns (file extensions) to indexer provider(s)
     */
    private final Map<String, Set<Indexer>> indexerMap;

    /**
     * Set of indexers that have resource matchers specified.
     */
    private final Set<Indexer> indexersWithMatchers;

    /**
     * the default database provider
     */
    @SuppressWarnings("unused")
    private String dbType = ResourceDbFactory.DB_HASHMAP;

    /**
     * This Flag is needed only for JUnit testing.
     */
    public int isAffectedEvent;

    /**
     * This Flag is needed only for JUnit testing.
     */
    public boolean isAffectedEventCalled;

    private static Map<String, Integer> indexerUsageLog =
            new HashMap<String, Integer>();

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.IndexerService#query(java.lang.String,
     * java.util.Map)
     */
    @Override
    public Collection<IndexerItem> query(String indexerId,
            Map<String, String> criteria) {

        /**
         * Sid XPD-3641. Added this debug console output for
         * "number of query hits on each index" for a performance problem caused
         * by hits on bom 2 cds index.
         * 
         * It came in very handy so though I would leave it here for future use.
         */
        boolean debug = false;
        if (debug) {
            Integer indexHitCount = indexerUsageLog.get(indexerId);
            if (indexHitCount == null) {
                indexHitCount = 1;
            } else {
                indexHitCount = indexHitCount + 1;
            }

            indexerUsageLog.put(indexerId, indexHitCount);

            /*
             * Limit number of messages per index type, only report after nth
             * and every so many after that
             */
            if ((indexHitCount % 10) == 0) {
                String msg =
                        String.format("%s.query(): Index '%s' hit count = %d", //$NON-NLS-1$
                                this.getClass().getSimpleName(),
                                indexerId,
                                indexHitCount);
                System.out.println(msg);
            }
        }

        try {
            Collection<IndexerItem> result =
                    getResourceDb().query(indexerId, criteria);
            /*
             * Remove the internal row that is used to store the timestamp.
             */
            if (result != null) {
                for (Iterator<IndexerItem> iter = result.iterator(); iter
                        .hasNext();) {
                    IndexerItem item = iter.next();
                    String timeStamp = item.get(ATTRIBUTE_UPDATETIMESTAMP);
                    if (timeStamp != null && timeStamp.length() > 0
                            && item.getURI() != null) {
                        URI uri = URI.createURI(item.getURI());
                        if (TIMESTAMP_FRAGMENT.equals(uri.fragment())) {
                            iter.remove();
                        }
                    }
                }
            }
            return result;
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return new ArrayList<IndexerItem>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.IndexerService#query(java.lang.String,
     * com.tibco.xpd.resources.indexer.IndexerItem)
     */
    @Override
    public Collection<IndexerItem> query(String indexerId, IndexerItem criteria) {
        return query(indexerId, toMap(criteria));
    }

    /**
     * convert IndexerItem into Map<String, String>
     * 
     * @param criteria
     * @return
     */
    private Map<String, String> toMap(IndexerItem criteria) {
        if (criteria == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String> result = new HashMap<String, String>();

        Set<String> keys = criteria.keys();
        for (String key : keys) {
            result.put(key, criteria.get(key));
        }
        if (criteria.getName() != null) {
            result.put(IndexerServiceImpl.ATTRIBUTE_NAME, criteria.getName());
        }
        if (criteria.getType() != null) {
            result.put(IndexerServiceImpl.ATTRIBUTE_TYPE, criteria.getType());
        }
        if (criteria.getURI() != null) {
            result.put(IndexerServiceImpl.ATTRIBUTE_URI, criteria.getURI());
        }
        if (result.isEmpty()) {
            return Collections.emptyMap();
        }
        return result;
    }

    /**
     * constructor
     */
    public IndexerServiceImpl() {
        indexerMap = new HashMap<String, Set<Indexer>>();
        indexersWithMatchers = new HashSet<Indexer>();

        try {
            getResourceDb().open();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.log(e);
        }
    }

    /**
     * Process the given working copy property change event. This will update
     * the appropriate indexes based on the change event. (Called during live
     * validation).
     * 
     * @param event
     * @since 3.3
     */
    public void processWorkingCopyPropertyChange(PropertyChangeEvent event) {
        Object source = event.getSource();
        if (source instanceof WorkingCopy) {
            WorkingCopy wc = (WorkingCopy) source;
            traceINDEX(String
                    .format("WC property change event '%s' received on '%s'", //$NON-NLS-1$
                            event.getPropertyName(),
                            wc.getEclipseResources().get(0).getFullPath()
                                    .toString()));
            Set<Indexer> indexers = getIndexers(wc);

            if (indexers != null) {
                for (Indexer indexer : indexers) {
                    if (indexer.provider != null) {
                        if (WorkingCopy.PROP_REMOVED.equals(event
                                .getPropertyName())
                                || WorkingCopy.PROP_RELOADED.equals(event
                                        .getPropertyName())) {
                            removeIndex(indexer.id, getPath(wc));
                        }

                        /*
                         * If the working copy has re-loaded then all items
                         * relating to the model have been cleared from the
                         * indexer - see block above. Therefore just update the
                         * indexer without asking whether it's affected - as it
                         * will be.
                         */
                        if (WorkingCopy.PROP_RELOADED.equals(event
                                .getPropertyName())) {
                            updateIndex(indexer, wc);

                        } else if (WorkingCopy.CHANGED.equals(event
                                .getPropertyName())
                                || WorkingCopy.PROP_DIRTY.equals(event
                                        .getPropertyName())
                                || WorkingCopy.PROP_DEPENDENCY_CHANGED
                                        .equals(event.getPropertyName())) {

                            if (indexer.provider.isAffectingEvent(event)) {
                                isAffectedEvent = IS_AFFECTED_METHOD_EFFECTED;
                                updateIndex(indexer, wc);
                            }
                            isAffectedEventCalled = true;
                        }
                    }
                }
            }
            traceINDEX("Property change complete."); //$NON-NLS-1$
        }
    }

    /**
     * Get the path of the resource managed by the given working copy.
     * 
     * @param wc
     * @return path of the resource
     */
    private String getPath(WorkingCopy wc) {
        return wc.getEclipseResources().get(0).getFullPath().toPortableString();
    }

    /**
     * Get all indexer providers registered for the resource being managed by
     * the given working copy.
     * 
     * @param wc
     *            <code>WorkingCopy</code>
     * @return set of indexer providers. This can be empty if no indexers are
     *         registered for the resource.
     */
    private Set<Indexer> getIndexers(WorkingCopy wc) {
        Set<Indexer> indexers = new HashSet<Indexer>();

        if (wc != null && wc.getEclipseResources() != null) {
            // First check any indexers that have a resource matcher specified
            // rather than file extension
            for (Indexer indexer : indexersWithMatchers) {
                if (indexer.matcher.accept(wc)) {
                    indexers.add(indexer);
                }
            }

            // Check indexers that have a file pattern
            Set<Indexer> filePatternIndexers =
                    getIndexers(wc.getEclipseResources().get(0));
            if (filePatternIndexers != null) {
                indexers.addAll(filePatternIndexers);
            }
        }

        return indexers;
    }

    /**
     * Get the indexer providers that are registered for the given resource.
     * 
     * @param resource
     *            <code>IResource</code>.
     * @return set of index providers. This can be <code>null</code> or empty.
     */
    private Set<Indexer> getIndexers(IResource resource) {
        Set<Indexer> indexers = null;

        if (resource != null) {
            String fileExt = resource.getFileExtension();
            if (fileExt != null) {
                indexers = indexerMap.get(fileExt);
            }
        }

        return indexers;
    }

    /**
     * Call to initialise the indexer for this working copy. If there are items
     * already indexed for this working copy then the indexer will not be
     * updated, if no items are found in the indexer for this working copy then
     * the indexer will be updated. This should typically be called when the
     * working copy is created.
     * 
     * @param wc
     */
    public void initialiseIndexer(WorkingCopy wc) {
        if (wc != null) {
            traceINDEX(String
                    .format("Initialise index called on working copy for '%s'...", //$NON-NLS-1$
                            wc.getEclipseResources().get(0)));
            index(wc);

            traceINDEX("Initialise complete."); //$NON-NLS-1$
        }
    }

    /**
     * Explicitly index the given working copy. This will only update the
     * indexer if it needs to, ie the model has been modified and saved since
     * the last index.
     * 
     * @param wc
     * @since 3.3
     */
    public void index(WorkingCopy wc) {
        index(wc, false);
    }

    /**
     * Explicitly index the given working copy. This will only update the
     * indexer if it needs to, ie the model has been modified and saved since
     * the last index.
     * 
     * @param wc
     * @param force
     *            FOrces the index to be rebuilt
     * 
     * @since 3.5.2
     */
    public void index(WorkingCopy wc, boolean force) {
        if (wc != null) {
            traceINDEX(String
                    .format("Explicit index called on working copy for '%s'", //$NON-NLS-1$
                            wc.getEclipseResources().get(0)));
            // check if there are indexers for this working copy
            Set<Indexer> indexers = getIndexers(wc);

            if (indexers != null) {
                for (Indexer indexer : indexers) {
                    try {
                        if (force || needsUpdating(indexer.id, wc)) {
                            traceINDEX(indexer.id, "Updating index."); //$NON-NLS-1$
                            updateIndex(indexer, wc);
                        } else {
                            traceINDEX(String
                                    .format("  --> Index '%s' is up-to-date.", //$NON-NLS-1$
                                            indexer.id));
                        }
                    } catch (Exception e) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Indexer %1$s failed for file '%2$s'", //$NON-NLS-1$
                                                indexer.id,
                                                wc.getEclipseResources().get(0)
                                                        .getFullPath()
                                                        .toString()));
                    }
                }
            }
            traceINDEX(String
                    .format("Explicit indexing complete for on working copy for '%s'.", wc.getEclipseResources().get(0))); //$NON-NLS-1$
        }
    }

    /**
     * Explicitly index the given working copy for the given indexer only. This
     * will only update the indexer if it needs to, ie the model has been
     * modified and saved since the last index.
     * 
     * @param wc
     * @param force
     * @param indexerId
     * 
     * @since 3.5.3
     */
    public void index(WorkingCopy wc, boolean force, String indexerId) {
        if (wc != null) {
            traceINDEX(String
                    .format("Explicit index called on working copy for '%s' Indexer '%s': ", //$NON-NLS-1$
                            wc.getEclipseResources().get(0),
                            indexerId));
            // check if there are indexers for this working copy
            Set<Indexer> indexers = getIndexers(wc);

            if (indexers != null) {
                Indexer indexer = null;

                for (Indexer ind : indexers) {
                    if (indexerId.equals(ind.id)) {
                        indexer = ind;
                        break;
                    }
                }

                if (indexer != null) {
                    try {
                        if (force || needsUpdating(indexer.id, wc)) {
                            updateIndex(indexer, wc);
                        } else {
                            traceINDEX(String
                                    .format("  --> Index '%s' is up-to-date.", //$NON-NLS-1$
                                            indexer.id));
                        }
                    } catch (Exception e) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Indexer %1$s failed for file '%2$s'", //$NON-NLS-1$
                                                indexer.id,
                                                wc.getEclipseResources().get(0)
                                                        .getFullPath()
                                                        .toString()));
                    }
                }
            }
            traceINDEX("Explicit indexing complete."); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.IndexerService#clean(org.eclipse.core
     * .resources.IProject)
     */
    @Override
    public void clean(IProject project, final IProgressMonitor monitor) {
        if (project != null) {
            traceINDEX(String.format("Clean called on project '%s'", project //$NON-NLS-1$
                    .getName()));
            long cleanStart = System.currentTimeMillis();
            try {
                getResourceDb().cleanProject(project.getName());

                /*
                 * The following can only apply to "full" Studio for the
                 * following reason: When Studio RCP has more than one instance
                 * running and the first instance is closed it will remove the
                 * project it is editing and clean up the database. The
                 * following code will also remove all data for projects that
                 * are no longer in the workspace - which means, as the first
                 * instance of RCP won't be aware of the project being edited by
                 * the second instance it will clear up all its data.
                 */
                if (!XpdResourcesPlugin.isRCP()) {
                    /*
                     * Purge all non-existing or closed projects
                     */
                    Collection<IndexerItem> result = getResourceDb().query("*", //$NON-NLS-1$
                            new HashMap<String, String>());

                    if (result != null) {
                        // Get all project names
                        Set<String> projectNames = new HashSet<String>();

                        for (IndexerItem item : result) {
                            String projName = item.get(ATTRIBUTE_PROJECT);

                            if (projName != null) {
                                projectNames.add(projName);
                            }
                        }

                        IWorkspaceRoot root = project.getWorkspace().getRoot();
                        for (String name : projectNames) {
                            IProject projHandle = root.getProject(name);

                            if (projHandle == null
                                    || !projHandle.isAccessible()) {
                                // Project doesn't exist or is closed
                                getResourceDb().cleanProject(name);
                            }
                        }
                    }
                }
            } catch (ResourceDbException e1) {
                XpdResourcesPlugin.log(e1);
                return;
            } finally {
                traceINDEX(String.format("Clean complete.  Took %d ms.", System //$NON-NLS-1$
                        .currentTimeMillis() - cleanStart));
            }

            // Check if the project exists and is open
            if (project.isAccessible()) {
                traceINDEX(String.format("Re-indexing project '%s' after clean.", //$NON-NLS-1$
                        project.getName()));
                try {
                    project.accept(new IResourceVisitor() {
                        @Override
                        public boolean visit(IResource resource)
                                throws CoreException {
                            WorkingCopy wc =
                                    XpdResourcesPlugin.getDefault()
                                            .getWorkingCopy(resource);
                            if (wc != null && !wc.isInvalidFile()) {
                                Set<Indexer> indexers = getIndexers(wc);

                                if (indexers != null) {
                                    traceINDEX(String
                                            .format("Starting indexing of '%s'", //$NON-NLS-1$
                                                    resource.getFullPath()
                                                            .toString()));
                                    for (Indexer indexer : indexers) {
                                        updateIndex(indexer, wc);

                                        if (monitor != null) {
                                            monitor.subTask(Messages.ResourceIndexerSubTask_indexingResource
                                                    + " " + wc.getName()); //$NON-NLS-1$
                                        }
                                    }
                                }
                            }
                            return true;
                        }
                    });
                } catch (CoreException e) {
                    XpdResourcesPlugin.log(e);
                } finally {
                    traceINDEX(String
                            .format("Re-indexing completed on project '%s'", //$NON-NLS-1$
                                    project));
                }
            }
        }
    }

    /**
     * Update the indexer of the given indexer id with the items.
     * 
     * @param indexer
     *            indexer.
     * @param wc
     *            <code>WorkingCopy</code> being updated.
     */
    private void updateIndex(Indexer indexer, WorkingCopy wc) {
        try {
            if (wc.getEclipseResources() != null
                    && !wc.getEclipseResources().isEmpty()) {
                IResource res = wc.getEclipseResources().get(0);
                boolean isFirstLoad = !wc.isLoaded();
                traceINDEX(String.format("  --> Updating index '%s'", indexer.id)); //$NON-NLS-1$
                long start = System.currentTimeMillis();

                List<IndexerItem> items = new ArrayList<IndexerItem>();
                IndexerItem timeStampItem = createTimeStampItem(res);
                if (timeStampItem != null) {
                    items.add(timeStampItem);
                }

                Collection<IndexerItem> itemsToAdd =
                        indexer.provider.getIndexItems(wc);
                if (itemsToAdd != null && !itemsToAdd.isEmpty()) {
                    items.addAll(itemsToAdd);
                }

                update(indexer, wc, items);

                long timeTaken = System.currentTimeMillis() - start;
                // Ignore timings if it includes loading of the model
                if (!isFirstLoad && timeTaken > 500) {
                    /*
                     * Add warning to log if the indexing has taken more than
                     * 500ms.
                     */
                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .log(new Status(
                                    IStatus.WARNING,
                                    XpdResourcesPlugin.ID_PLUGIN,
                                    String.format("Indexer %1$s has taken longer than 500ms (%2$d ms) for '%3$s'.", //$NON-NLS-1$
                                            indexer.id,
                                            timeTaken,
                                            res)));
                }

                traceINDEX(String
                        .format("    --> (%s) Took %d ms.", indexer.id, timeTaken)); //$NON-NLS-1$
            }
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.log(e);
        }
    }

    /**
     * Update the indexer with the given items. This method will ensure that the
     * database update does not occur in the UI thread.
     * 
     * @param indexer
     * @param wc
     * @param items
     * @throws ResourceDbException
     * @since 3.5.5
     */
    private void update(final Indexer indexer, final WorkingCopy wc,
            final List<IndexerItem> items) throws ResourceDbException {

        /*
         * If this method is called from the UI (or main) thread then it needs
         * to spawn off another (non-UI) thread to run this as updating the
         * database on the UI thread can cause problems.
         */
        if (Display.getCurrent() != null) {

            /*
             * If we come from main UI thread with running workbench then we
             * would like to (for better user experience) show a busy cursor
             * while indexes are being updated (if index update is taking a
             * noticeable time).THIS WAS CAUSING A PROBLEM WHEN SCRIPT WAS
             * EDITED AND THEN BY OPENING PROGRESS DIALOG FOCUS WAS LOST AND
             * MODEL UPDATED (RESULTING WITH LOCKUP).
             * 
             * 
             * In case studio is being run from command line then there is still
             * a main thread with a current display. In that case we run an
             * index db update operation as a new job which will just join main
             * job. It is now using join when workbench is running.
             */

            Job job =
                    new Job(
                            Messages.IndexerServiceImpl_updateIndexer_job_shortdesc) {
                        @Override
                        protected IStatus run(IProgressMonitor monitor) {
                            monitor.beginTask(Messages.IndexerServiceImpl_updateIndexer_job_shortdesc,
                                    IProgressMonitor.UNKNOWN);
                            try {
                                getResourceDb().update(indexer.id, items, wc);
                            } catch (ResourceDbException e) {
                                return new Status(IStatus.ERROR,
                                        XpdResourcesPlugin.ID_PLUGIN,
                                        "Error in updating the Indexer", e); //$NON-NLS-1$
                            } finally {
                                monitor.done();
                            }
                            return Status.OK_STATUS;
                        }

                    };
            job.setPriority(Job.SHORT);
            job.setSystem(true);
            job.schedule();
            try {
                // DB update needs to be synchronous so wait for update job to
                // complete
                job.join();
            } catch (InterruptedException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
            // If an error result has been returned then throw exception
            IStatus result = job.getResult();
            if (result != null && result.getSeverity() == IStatus.ERROR) {
                if (result.getException() instanceof ResourceDbException) {
                    throw (ResourceDbException) result.getException();
                } else {
                    throw new ResourceDbException(
                            result.getException() != null ? result
                                    .getException() : new CoreException(result));
                }
            }

        } else {
            getResourceDb().update(indexer.id, items, wc);
        }
    }

    /**
     * Create a time-stamp item to add to the indexer for the given resource.
     * 
     * @param res
     * @return
     * @since 3.3
     */
    private IndexerItem createTimeStampItem(IResource res) {
        if (res != null) {
            IndexerItemImpl item =
                    new IndexerItemImpl(null, null, URI
                            .createPlatformResourceURI(res.getFullPath()
                                    .toString(),
                                    true).appendFragment(TIMESTAMP_FRAGMENT)
                            .toString(), null);
            /*
             * SCF-79: ts now combines modification stamp and local time stamp.
             * We found out that modification stamp was sometimes not updated
             * for generated BOMs.
             */
            long ts = res.getModificationStamp() + res.getLocalTimeStamp();
            item.set(ATTRIBUTE_UPDATETIMESTAMP, String.valueOf(ts));
            return item;
        }
        return null;
    }

    /**
     * Check if the indexer with the given indexerId is up-to-date for the given
     * working copy. This will compare the last time stamp stored for this
     * working copy with modified time stamp of the working copy's resource.
     * 
     * @param indexerId
     * @param wc
     * @return <code>true</code> if the indexer should be updated for the given
     *         model.
     * @since 3.3
     */
    private boolean needsUpdating(String indexerId, WorkingCopy wc) {
        if (indexerId != null && wc != null) {
            IResource res = wc.getEclipseResources().get(0);
            long previousTs = 0;
            IndexerItemImpl criteria =
                    new IndexerItemImpl(null, null, URI
                            .createPlatformResourceURI(res.getFullPath()
                                    .toString(),
                                    true).appendFragment(TIMESTAMP_FRAGMENT) //$NON-NLS-1$
                            .toString(), null);
            Collection<IndexerItem> items;
            try {
                items = getResourceDb().query(indexerId, toMap(criteria));

                if (items != null && !items.isEmpty()) {
                    IndexerItem item = items.iterator().next();
                    String value = item.get(ATTRIBUTE_UPDATETIMESTAMP);
                    if (value != null && value.length() > 0) {
                        previousTs = Long.valueOf(value);
                    }
                    long ts =
                            res.getModificationStamp()
                                    + res.getLocalTimeStamp();
                    return previousTs != ts;
                }
            } catch (ResourceDbException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }

        }
        return true;
    }

    /**
     * Remove the indexer items that are from the resource with the given path.
     * 
     * @param indexerId
     *            id of indexer
     * @param path
     *            path of the resource being removed from the indexer.
     */
    private void removeIndex(String indexerId, String path) {
        try {
            traceINDEX(String.format("  --> Cleaning index '%s'", indexerId)); //$NON-NLS-1$
            long start = System.currentTimeMillis();
            getResourceDb().cleanPath(indexerId, path);
            traceINDEX(String.format("    --> Took %d ms.", System //$NON-NLS-1$
                    .currentTimeMillis() - start));
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.log(e);
        }
    }

    /**
     * Get the resource database
     * 
     * @return resource database
     */
    public IResourceDb getResourceDb() {

        if (resourceDb == null) {
            resourceDb = ResourceDbFactory.createDb(ResourceDbFactory.DB_DERBY);
        }
        return resourceDb;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.indexer.IndexerService#printAllEntries()
     */
    public Collection<IndexerItem> printAllEntries() {
        try {
            getResourceDb().printAllEntries();
        } catch (ResourceDbException ignore) {
            ignore.printStackTrace();
        }
        return null;
    }

    /**
     * @param resource
     * @return
     */
    static public boolean isIndexed(IResource resource) {
        Object o;
        try {
            o = resource.getPersistentProperty(XPD_RESOURCE_INDEXED);
        } catch (CoreException e) {
            XpdResourcesPlugin.log(e);
            return false;
        }

        if (o instanceof String && ((String) o).equalsIgnoreCase("y")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * @param resource
     * @param indexed
     */
    static public void setIndexed(IResource resource, boolean indexed) {
        if (resource.isAccessible()) {
            try {
                resource.setPersistentProperty(XPD_RESOURCE_INDEXED,
                        indexed ? "y" : "n"); //$NON-NLS-1$ //$NON-NLS-2$
            } catch (CoreException e) {
                XpdResourcesPlugin.log(e);
            }
        }
    }

    /**
     * The main purpose of this method is to read additional attributes from the
     * extension point. These attributes form additional columns in the resource
     * database.<br>
     * The method should be called at the end of the start method of the start
     * method of the xpd resource plugin
     */
    public void start() {

        String extensionName = "workingCopyIndexer"; //$NON-NLS-1$
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                                extensionName);

        if (extensionPoint != null) { // no plugins contributed to
            // extension
            IConfigurationElement[] indexProviderContributers =
                    extensionPoint.getConfigurationElements();

            for (IConfigurationElement indexerElement : indexProviderContributers) {

                // we have already the indexer element
                // one plug-in can have more than one indexer class
                // to find out the getContributer() method can be used
                Indexer indexer = new Indexer();
                indexer.id = indexerElement.getAttribute("indexID"); //$NON-NLS-1$
                
                //ignore WSDL indexer in case suppress flag is Set. 
                if(indexer.id!=null && indexer.id.equalsIgnoreCase(WSDLINDEXER_ID) 
                		   && XpdConfigOption.SUPPRESS_DEFAULT_WSDL_INDEXER.isOn()){
                	
                       continue;
                }
                
                try {
                    indexer.provider =
                            (WorkingCopyIndexProvider) indexerElement
                                    .createExecutableExtension("indexerClass"); //$NON-NLS-1$

                    if (indexer.id != null && indexer.provider != null) {
                        String filePattern =
                                indexerElement.getAttribute("filePattern"); //$NON-NLS-1$
                        IndexResourceMatcher matcher =
                                (IndexResourceMatcher) (indexerElement
                                        .getAttribute("resourceMatcher") != null ? indexerElement //$NON-NLS-1$
                                        .createExecutableExtension("resourceMatcher") //$NON-NLS-1$
                                        : null);

                        if (filePattern != null || matcher != null) {
                            // If resource matcher specified then use the
                            // matcher, otherwise use the file extension
                            if (matcher != null) {
                                indexer.matcher = matcher;
                                indexersWithMatchers.add(indexer);
                            } else {
                                Set<Indexer> indexers =
                                        indexerMap.get(filePattern);

                                if (indexers == null) {
                                    indexers = new HashSet<Indexer>();
                                    indexerMap.put(filePattern, indexers);
                                }
                                // Add new indexer to map
                                indexers.add(indexer);
                            }

                            // there can be only one 'attributes' element
                            IConfigurationElement[] attributesElements =
                                    indexerElement
                                            .getChildren("additionalAttributes"); //$NON-NLS-1$

                            Column[] columns = new Column[0];

                            if (attributesElements != null
                                    && attributesElements.length > 0) {
                                IConfigurationElement attributesElement =
                                        attributesElements[0];
                                IConfigurationElement[] attributes =
                                        attributesElement.getChildren();

                                columns = new Column[attributes.length];
                                for (int j = 0; j < attributes.length; j++) {
                                    IConfigurationElement attribute =
                                            attributes[j];
                                    String name =
                                            attribute.getAttribute("name"); //$NON-NLS-1$
                                    String length =
                                            attribute.getAttribute("maxLength"); //$NON-NLS-1$
                                    columns[j] = new Column(name, length);
                                }
                            }

                            /*
                             * Get primary key(s) for the indexer if they are
                             * defined. (indexers can define their own primary
                             * key(s) (different to the default URI primary key)
                             * or can decide not to have any primary key).
                             * 
                             * JA: This sets a static variable which will be
                             * read next immediately in
                             * ResourceDB#initTable(...) method for each indexer
                             * extension. TODO: Refactor!!!
                             */
                            setIndexerKeyAttributes(getKeyAttributes(indexer,
                                    indexerElement,
                                    columns));

                            getResourceDb().initTable(indexer.id,
                                    getBundleVersion(indexerElement),
                                    columns);

                        } else {
                            // Log and continue with the next extension
                            XpdResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(String.format("Neither the file pattern nor the resource matcher is specified for indexer '%s'.  This indexer will be ignored.", //$NON-NLS-1$
                                            indexer.id));

                        }
                    }
                } catch (CoreException e) {
                    // Problem loading extension
                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format(Messages.IndexerServiceImpl_failedToLoadIndexer_error_shortdesc,
                                            indexer.id));
                } catch (ResourceDbException e) {
                    /*
                     * Unregister this indexer as its table in the database has
                     * not been set up properly
                     */
                    if (indexersWithMatchers.contains(indexer)) {
                        indexersWithMatchers.remove(indexer);
                    }

                    Set<String> fileMappingsToRemove = new HashSet<String>();
                    for (Entry<String, Set<Indexer>> entry : indexerMap
                            .entrySet()) {
                        if (entry.getValue() != null) {
                            for (Iterator<Indexer> iter =
                                    entry.getValue().iterator(); iter.hasNext();) {
                                if (iter.next() == indexer) {
                                    iter.remove();
                                }
                            }
                        }
                        if (entry.getValue().isEmpty()) {
                            fileMappingsToRemove.add(entry.getKey());
                        }
                    }

                    if (!fileMappingsToRemove.isEmpty()) {
                        for (String fileExt : fileMappingsToRemove) {
                            indexerMap.remove(fileExt);
                        }
                    }

                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(e,
                                    String.format(Messages.IndexerServiceImpl_indexerInitFailed_error_shortdesc,
                                            indexer.id));
                }
            } // end for
        }
    }

    /**
     * 
     * Get key attributes for the indexer if they are defined. (indexers can
     * define their own primary key(s) (different to the default URI primary
     * key) or can decide not to have any primary key)
     * 
     * @param indexer
     * @param indexerElement
     * @param columns
     *            to check if the keys defined for primary key match the column
     *            names of the indexer
     * @return array of primary keys if defined
     */
    private String[] getKeyAttributes(Indexer indexer,
            IConfigurationElement indexerElement, Column[] columns) {

        String[] keyAttributes = null;
        IConfigurationElement[] indexerKeys =
                indexerElement.getChildren("indexKey"); //$NON-NLS-1$
        if (null != indexerKeys && indexerKeys.length > 0) {

            IConfigurationElement indexerKey = indexerKeys[0];
            IConfigurationElement[] primaryKeys = indexerKey.getChildren();
            /* primary keys are defined */
            keyAttributes = new String[primaryKeys.length];

            for (int i = 0; i < primaryKeys.length; i++) {

                IConfigurationElement primaryKeyId = primaryKeys[i];
                String indexerColumnForPrimKey =
                        primaryKeyId.getAttribute("attributeName"); //$NON-NLS-1$
                /*
                 * get all columns for an indexer to check if the primary key
                 * being used exists in the indexer
                 */
                List<String> allColumns = getAllColumns(columns);
                /*
                 * if the indexer column used for primary key does not exist in
                 * the columns of indexer table then log it in the error log
                 */
                if (allColumns.contains(indexerColumnForPrimKey)) {
                    keyAttributes[i] = indexerColumnForPrimKey;
                } else {
                    XpdResourcesPlugin
                            .getDefault()
                            .getLogger()
                            .error(String.format(Messages.IndexerServiceImpl_indexerInitFailedForPrimaryKey_error_shortdesc,
                                    indexer.id,
                                    indexerColumnForPrimKey));
                }
            }
        }
        return keyAttributes;
    }

    /**
     * Gets all the columns of an indexer (implementation specific columns and
     * default columns of any indexer)
     * 
     * @param columns
     * @return
     */
    private List<String> getAllColumns(Column[] columns) {

        List<String> allColumns = new ArrayList<String>();
        for (Column column : columns) {
            allColumns.add(column.getName());
        }
        allColumns.add(ATTRIBUTE_NAME);
        allColumns.add(ATTRIBUTE_PATH);
        allColumns.add(ATTRIBUTE_PROJECT);
        allColumns.add(ATTRIBUTE_TYPE);
        allColumns.add(ATTRIBUTE_UPDATETIMESTAMP);
        allColumns.add(ATTRIBUTE_URI);
        return allColumns;
    }

    /**
     * The main purpose of this method is to close the Database connection.
     */
    public void stop() {
        try {
            getResourceDb().close();
        } catch (ResourceDbException e) {
            XpdResourcesPlugin.log(e);
        }
    }

    /**
     * Find the bundle for the given configurationElement and return its name as
     * major.minor.patch. Drop any additional parts.
     * 
     * @param indexerElement
     * @return
     */
    @SuppressWarnings("unchecked")
    String getBundleVersion(IConfigurationElement indexerElement) {
        String bundleName = indexerElement.getContributor().getName();

        String version = ""; //$NON-NLS-1$
        if (bundleName != null) {
            Bundle[] bundles =
                    XpdResourcesPlugin.getDefault().context.getBundles();
            for (Bundle bundle : bundles) {
                String symbolicName = bundle.getSymbolicName();
                if (symbolicName.equals(bundleName)) {
                    java.util.Dictionary<?, ?> dict = bundle.getHeaders();
                    Object bundleVersion = dict.get("Bundle-version"); //$NON-NLS-1$
                    if (bundleVersion instanceof String) {
                        version = (String) bundleVersion;
                        break;
                    }
                }
            }
        }
        return version;
    }

    /**
     * @param version
     * @return
     */
    String normalizeVersion(String version) {
        StringTokenizer tok = new StringTokenizer(version, "."); //$NON-NLS-1$

        String major = ""; //$NON-NLS-1$
        String minor = ""; //$NON-NLS-1$
        String patch = ""; //$NON-NLS-1$

        for (int count = 0; count < 3; count++) {
            switch (count) {
            case 0:
                major = tok.nextToken();
                break;
            case 1:
                minor = "." + tok.nextToken(); //$NON-NLS-1$
                break;
            case 2:
                patch = "." + tok.nextToken(); //$NON-NLS-1$
                break;
            }
        }

        version = major + minor + patch;
        return version;
    }

    /**
     * @return the indexerKeyAttributes
     */
    public static String[] getIndexerKeyAttributes() {
        return indexerKeyAttributes;
    }

    /**
     * @param indexerKeyAttributes
     *            the indexerKeyAttributes to set
     */
    public static void setIndexerKeyAttributes(String[] indexerKeyAttributes) {
        IndexerServiceImpl.indexerKeyAttributes = indexerKeyAttributes;
    }

}
