package com.tibco.xpd.bom.modeler.tests;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.core.test.util.TestUtil;

public class CreateClass extends TestCase {

    private static final String PROJECT_NAME = "CreateClassProject";

    private static final String BOM_FILE_NAME = "test.bom";

    protected Resource diagram;

    IProject project;

    /**
     * Create the class on the diagram using the GEF command.
     */
    public void testCreateClass() {
        BOMTestUtils.createClassInEditor(BOM_FILE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        TestUtil.createProject(PROJECT_NAME);

        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);
        assertNotNull(project);
        assertEquals(true, project.isAccessible());

        BOMTestUtils.createBOMdiagram(PROJECT_NAME, BOM_FILE_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300, 200);
        TestUtil.removeProject(PROJECT_NAME);
        super.tearDown();
    }

}
