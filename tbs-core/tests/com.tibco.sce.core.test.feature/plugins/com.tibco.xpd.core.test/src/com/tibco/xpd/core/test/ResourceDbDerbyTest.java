/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.core.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.db.Column;
import com.tibco.xpd.resources.internal.db.IResourceDb;
import com.tibco.xpd.resources.internal.db.ResourceDbException;
import com.tibco.xpd.resources.internal.db.impl.derby.ResourceDbDerby;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Test the ResourceDbDerby class to check how recent changes affect performance
 * and the ability to run in multi-threaded environments.
 * 
 * @author nwilson
 * @since 5 Dec 2014
 */
@SuppressWarnings("restriction")
public class ResourceDbDerbyTest extends TestCase {

    private static final String INDEXER_ID = "test";

    private IResource resource;

    volatile private int remaining;

    @Override
    protected void setUp() throws Exception {
        IProject project = TestUtil.createBPMProjectFromWizard("DerbyTest");
        resource = project.getFile("Process Packages/DerbyTest.xpdl");
    }

    public void testMultiThreadAccess() {
        IResourceDb db = new ResourceDbDerby("TestDB");
        String version = "1";
        Column name = new Column("name", "15", true);
        Column url = new Column("url", "100", false);
        Column group = new Column("indexgroup", "10", true);
        Column[] columns = new Column[] { name, url, group };
        try {
            db.initTable(INDEXER_ID, version, columns);
            ExecutorService service = Executors.newFixedThreadPool(10);
            long start = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                service.execute(new UpdateCommand(i, db));
                remaining++;
                service.execute(new QueryCommand(db));
                remaining++;
            }
            System.out.println("Jobs submitted.");
            service.shutdown();
            while (!service.isTerminated()) {
                service.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("Tasks left: " + remaining);
            }
            System.out.println("Completed in "
                    + (System.currentTimeMillis() - start) + " ms");
        } catch (ResourceDbException | InterruptedException e) {
            fail(e.getMessage());
        } finally {
            try {
                db.close();
            } catch (ResourceDbException e) {
                fail(e.getMessage());
            }
        }
    }

    class UpdateCommand implements Runnable {

        private int index;

        private IResourceDb db;

        public UpdateCommand(int index, IResourceDb db) {
            this.index = index;
            this.db = db;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                Collection<IndexerItem> items = new ArrayList<>();
                for (int j = 0; j < 10; j++) {
                    String name = "name" + index + "-" + i + "-" + j;
                    String type = "type";
                    String uri = "http://name" + index + "-" + i + "-" + j;
                    Map<String, String> attributes =
                            new HashMap<String, String>();
                    attributes.put("name", name);
                    attributes.put("url", uri);
                    attributes.put("indexgroup", "" + i);
                    IndexerItem item =
                            new IndexerItemImpl(name, type, uri, attributes);
                    items.add(item);
                }
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(resource);
                try {
                    db.update(INDEXER_ID, items, wc);
                } catch (ResourceDbException e) {
                    e.printStackTrace();
                }
            }
            remaining--;
        }

    }

    class QueryCommand implements Runnable {
        private IResourceDb db;

        public QueryCommand(IResourceDb db) {
            this.db = db;
        }

        @Override
        public void run() {
            int count = 0;
            for (int i = 99; i > 0; i--) {
                Map<String, String> criteria = new HashMap<>();
                criteria.put("indexgroup", "" + i);
                try {
                    Collection<IndexerItem> results =
                            db.query(INDEXER_ID, criteria);
                    count = results == null ? 0 : results.size();
                } catch (ResourceDbException e) {
                    e.printStackTrace();
                }
            }
            remaining--;
            System.out.println("Count: " + count);
        }

    }
}
