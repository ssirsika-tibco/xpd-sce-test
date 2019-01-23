/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test.rules;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * I try to create a BOM model with two attributes with identical names. The
 * validator should report an error after the build.
 * 
 * @author ramin
 */
public class ClassDuplicateNameRuleTest extends TestCase {

    @SuppressWarnings("nls")
    @Override
    protected void setUp() throws Exception {
        TestUtil.waitForJobs();
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();

        if (projects.length != 0) {
            fail("Workspace should be empty, but it contains "
                    + projects.length + " projects, including: "
                    + projects[0].getName());
        }
        TestUtil.createProject("pr1");
        TestUtil.waitForJobs();
    }

    @SuppressWarnings("nls")
    @Override
    protected void tearDown() throws Exception {
        TestUtil.removeProject("pr1");
        TestUtil.waitForJobs();

        assertTrue("Workspace have to be empty after this test. ",
                ResourcesPlugin.getWorkspace().getRoot().getProjects().length == 0);
    }

    @SuppressWarnings("nls")
    public void testClassNameDuplicateRule() throws Exception {

        XpdResourcesPlugin.getDefault();

        SpecialFolder sf =
                TestUtil.createSpecialFolder("pr1",
                        "bom",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        IndexerService indexer =
                XpdResourcesPlugin.getDefault().getIndexerService();
        //
        // System.out.println("-------------->>>>> indexer = " +
        // indexer.getClass().getName());

        IFile f1 = BOMTestUtils.createBOMdiagram(sf, "f1.bom");
        IFile f2 = BOMTestUtils.createBOMdiagram(sf, "f2.bom");

        WorkingCopy wc1 = XpdResourcesPlugin.getDefault().getWorkingCopy(f1);
        WorkingCopy wc2 = XpdResourcesPlugin.getDefault().getWorkingCopy(f2);

        Model m1 = (Model) wc1.getRootElement();
        final Model m2 = (Model) wc2.getRootElement();

        int size = 100;
        // create 100 classes in the first file
        BOMTestUtils.createClasses(m1, size);
        assertEquals("There should be 100 classes in the indexer. ",
                size + 2,
                indexer.query(BOMResourcesPlugin.BOM_INDEXER_ID,
                        (IndexerItem) null).size());

        // create 100 classes in the second file
        BOMTestUtils.createClasses(m2, size);
        assertEquals("There should be 200 classes in the indexer. ",
                size * 2 + 2,
                indexer.query(BOMResourcesPlugin.BOM_INDEXER_ID,
                        (IndexerItem) null).size());

        // check if there is no errors
        TestUtil.waitForValidatior();
        // TestUtil.waitForJobs();
        assertEquals(-1, f1.findMaxProblemSeverity(IMarker.PROBLEM,
                true,
                IResource.DEPTH_ZERO));
        assertEquals(-1, f2.findMaxProblemSeverity(IMarker.PROBLEM,
                true,
                IResource.DEPTH_ZERO));

        // change the second package to 'f1'
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        RecordingCommand cmd = new RecordingCommand(ed) {
            @Override
            protected void doExecute() {
                m2.setName("f1");
            }
        };
        ed.getCommandStack().execute(cmd);
        System.out.println("Index size: "
                + indexer.query(BOMResourcesPlugin.BOM_INDEXER_ID,
                        (IndexerItem) null).size());
        assertTrue(wc2.isWorkingCopyDirty());
        // wc2.save();

        // TestUtil.waitForJobs();
        // TestUtil.delay(30000);

        TestUtil.waitForValidatior();

        // assertEquals(IMarker.SEVERITY_ERROR, f1.findMaxProblemSeverity(
        // IMarker.PROBLEM, true, IResource.DEPTH_ZERO));
        assertEquals(IMarker.SEVERITY_ERROR,
                f2.findMaxProblemSeverity(IMarker.PROBLEM,
                        true,
                        IResource.DEPTH_ZERO));
    }
}

/*
 * 
 * - gravitation:
 */