/**
 * 
 */
package com.tibco.xpd.bom.test;

import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
 * @author wzurek
 * 
 */
public class IndexingOfBomElementsTest extends TestCase {

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
        TestUtil.waitForJobs();
    }

    @Override
    protected void tearDown() throws Exception {
        IProject[] projects =
                ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for (IProject pr : projects) {
            pr.delete(true, true, null);
        }
        TestUtil.waitForJobs();
    }

    /**
     * scenario:<br>
     * <li>create project <li>create bom special folder <li>create bom diagram
     * <li>add 100 classes <li>indexer should have 101 objects (100 classes+1
     * package) <li>close project <li>indexer should be empty <li>open project
     * <li>indexer should have 101 objects (100 classes+1 package) <li>delete
     * project <li>indexer should be empty
     * 
     * @throws Exception
     */
    public void testCloseIndexedProject() throws Exception {

        IndexerService is = XpdResourcesPlugin.getDefault().getIndexerService();
        Collection<IndexerItem> result =
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null);
        assertEquals("Index should be empty", 0, result.size());

        String projectName = "testCloseIndexedProject.pr1";
        TestUtil.createProject(projectName);
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);

        assertTrue(project.exists());
        assertTrue(project.isOpen());

        TestUtil.waitForJobs();
        SpecialFolder sf =
                TestUtil.createSpecialFolder(projectName,
                        "bom",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        IFile f1 = BOMTestUtils.createBOMdiagram(sf, "f1.bom");
        WorkingCopy wc1 = XpdResourcesPlugin.getDefault().getWorkingCopy(f1);
        Model m1 = (Model) wc1.getRootElement();
        int size = 100;
        BOMTestUtils.createClasses(m1, size);
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());
        TestUtil.waitForValidatior();
        wc1.save();
        TestUtil.waitForValidatior();
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());
        is.clean(project, null);
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());

        project.close(null);
        TestUtil.waitForJobs();
        assertFalse(project.isOpen());

        assertEquals("There should be 0 classes in the indexer. ",
                0,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());

        TestUtil.waitForJobs();
        project.open(null);
        TestUtil.waitForJobs();

        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());

        TestUtil.waitForJobs();
        project.delete(true, true, null);
        TestUtil.waitForJobs();

        assertEquals("There should be 0 classes in the indexer. ",
                0,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());
    }

    /**
     * scenario:<br>
     * <li>create project <li>create bom special folder <li>create bom diagram
     * <li>add 100 classes <li>indexer should have 101 objects (100 classes+1
     * package) <li>close project <li>indexer should be empty <li>open project
     * <li>indexer should have 101 objects (100 classes+1 package) <li>delete
     * project <li>indexer should be empty
     * 
     * @throws Exception
     */
    public void testRemoveIndexedProject() throws Exception {

        IndexerService is = XpdResourcesPlugin.getDefault().getIndexerService();
        Collection<IndexerItem> result =
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null);
        assertEquals("Index should be empty", 0, result.size());

        String projectName = "testCloseIndexedProject.pr2";
        TestUtil.createProject(projectName);
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(projectName);

        assertTrue(project.exists());
        assertTrue(project.isOpen());

        TestUtil.waitForJobs();
        SpecialFolder sf =
                TestUtil.createSpecialFolder(projectName,
                        "bom",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        IFile f1 = BOMTestUtils.createBOMdiagram(sf, "f1.bom");
        WorkingCopy wc1 = XpdResourcesPlugin.getDefault().getWorkingCopy(f1);
        Model m1 = (Model) wc1.getRootElement();
        int size = 100;
        BOMTestUtils.createClasses(m1, size);
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());
        TestUtil.waitForValidatior();
        wc1.save();
        TestUtil.waitForValidatior();
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());
        is.clean(project, null);
        assertEquals("There should be 100 classes in the indexer. ",
                size + 1,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());

        project.delete(true, true, null);
        TestUtil.waitForJobs();

        assertEquals("There should be 0 classes in the indexer. ",
                0,
                is.query(BOMResourcesPlugin.BOM_INDEXER_ID, (IndexerItem) null)
                        .size());

    }
}
