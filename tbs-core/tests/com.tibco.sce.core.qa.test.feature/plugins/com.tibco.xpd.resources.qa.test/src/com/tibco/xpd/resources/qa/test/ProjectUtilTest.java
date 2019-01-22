package com.tibco.xpd.resources.qa.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * This test is not really useful because it simply calls to Eclipse framework
 * for adding Nature and Builder.
 * 
 * @author sghani
 * 
 */
public class ProjectUtilTest extends TestCase {

    // Test constants
    private static final String PROJECT_NAME = "ProjectUtilTest"; //$NON-NLS-1$

    private static final String NATURE_XPD =
            "com.tibco.xpd.resources.xpdNature"; //$NON-NLS-1$

    private static final String NATURE_BPM =
            "com.tibco.xpd.resources.bpmNature"; //$NON-NLS-1$

    private static final String BUILDER_VALIDATION =
            "com.tibco.xpd.validation.validationBuilder"; //$NON-NLS-1$

    private static final String BUILDER_IPE =
            "com.tibco.xpd.deploy.server.ipe.ipeBuilder"; //$NON-NLS-1$

    private static final String BUILDER_WSDL = "com.tibco.xpd.wsdl.wsdlBuilder"; //$NON-NLS-1$

    // Test variables
    private IProject project;

    public void testNature() throws Exception {
        setName("com.tibco.xpd.resources.qa.test.ProjectUtilTest.testNature()"); //$NON-NLS-1$
        // Add xpdNature to the project
        try {
            ProjectUtil.addNature(project, NATURE_XPD);
            // Check that xpdNature is present in the project
            assertTrue(project.hasNature(NATURE_XPD));
        } catch (CoreException e) {
            fail("Unable to add xpdNature to the project due to error:"); //$NON-NLS-1$
            e.printStackTrace();
        }
        // Add bpmNature to the project
        try {
            ProjectUtil.addNature(project, NATURE_BPM);
            // Check that both natures are present in the project
            assertTrue(project.hasNature(NATURE_BPM));
        } catch (CoreException e) {
            fail("Unable to add bpmNature to the project due to error: " //$NON-NLS-1$
                    + e.getMessage());
            e.printStackTrace();
        }
        // Try adding a nature that is already present in the project
        try {
            // Ensure that the project already has xpdNature and bpmNature.
            assertTrue(project.hasNature(NATURE_XPD));
            assertTrue(project.hasNature(NATURE_BPM));

            // Try adding bpmNature again to the project.
            // It should not throw any error and just ignore the add
            // since the bpmNature is already present.
            ProjectUtil.addNature(project, NATURE_BPM);

            // Ensure that xpdNature and bpmNature is present in the project
            assertTrue(project.hasNature(NATURE_XPD));
            assertTrue(project.hasNature(NATURE_BPM));
        } catch (CoreException e) {
            fail("Unable to add bpmNature to the project again due to error:"); //$NON-NLS-1$
            e.printStackTrace();
        }

        // Remove bpmNature from the project
        try {
            ProjectUtil.removeNature(project, NATURE_BPM);
            // Check that xpdNature is present in the project
            assertTrue(project.hasNature(NATURE_XPD));
            // Check that bpmNature is NOT present in the project
            assertFalse(project.hasNature(NATURE_BPM));
        } catch (CoreException e) {
            fail("Unable to remove bpmNature from project due to error:"); //$NON-NLS-1$
            e.printStackTrace();
        }
    }

    public void testBuilder() throws Exception {
        setName("com.tibco.xpd.resources.qa.test.ProjectUtilTest.testBuilder()"); //$NON-NLS-1$
        // Check there are no builders in project
        IProjectDescription desc = project.getDescription();
        ICommand[] commands = desc.getBuildSpec();
        // Add builders to the project
        try {
            ProjectUtil.addBuilderToProject(project, BUILDER_VALIDATION);
            ProjectUtil.addBuilderToProject(project, BUILDER_IPE);
            ProjectUtil.addBuilderToProject(project, BUILDER_WSDL);
            commands = project.getDescription().getBuildSpec();
            // Now check the builders are present
            assertEquals("Builder count doesn't match", 3, commands.length); //$NON-NLS-1$
            assertEquals("Builder[0] name doesn't match", //$NON-NLS-1$
                    BUILDER_VALIDATION,
                    commands[0].getBuilderName());
            assertEquals("Builder[1] name doesn't match", //$NON-NLS-1$
                    BUILDER_IPE,
                    commands[1].getBuilderName());
            assertEquals("Builder[2] name doesn't match", //$NON-NLS-1$
                    BUILDER_WSDL,
                    commands[2].getBuilderName());
        } catch (CoreException e) {
            fail("Unable to add builder due to error:"); //$NON-NLS-1$
            e.printStackTrace();
        }

        // Try adding a builder that is already present in the project
        try {
            // Add validationBuilder again to the project.
            // It should not throw any error and just ignore the add
            // since the wsdlBuilder is already present.
            ProjectUtil.addBuilderToProject(project, BUILDER_WSDL);
            // The builders count should remain same
            commands = project.getDescription().getBuildSpec();
            assertEquals("Builder count doesn't match", 3, commands.length); //$NON-NLS-1$
            assertEquals(BUILDER_VALIDATION, commands[0].getBuilderName());
            assertEquals(BUILDER_IPE, commands[1].getBuilderName());
            assertEquals(BUILDER_WSDL, commands[2].getBuilderName());
        } catch (CoreException e) {
            fail("Exception thrown when adding existing builder:"); //$NON-NLS-1$
            e.printStackTrace();
        }
        // Remove builders from the project
        try {
            ProjectUtil.removeBuilderFromProject(project, BUILDER_VALIDATION);
            ProjectUtil.removeBuilderFromProject(project, BUILDER_WSDL);
            // Check that only ipeBuilder is present in the project
            commands = project.getDescription().getBuildSpec();
            assertEquals("Expected one builder", 1, commands.length); //$NON-NLS-1$
            assertEquals(BUILDER_IPE, commands[0].getBuilderName());
        } catch (CoreException e) {
            fail("Remove builder failed with error:"); //$NON-NLS-1$
            e.printStackTrace();
        }
    }

    /**
     * Setups up the following: -- reset workspace perspective -- create a
     * project with no asset type or special folders
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Creates a project with no asset type/special folder
        System.out.println("Create a new project ..."); //$NON-NLS-1$
        project = TestUtil.createProject(PROJECT_NAME);
        TestUtil.waitForBuilds(300);

        assertNotNull("Project should NOT be NULL", project); //$NON-NLS-1$
        assertTrue("Project is NOT accessible", project.isAccessible()); //$NON-NLS-1$
    }

    /**
     * Cleanup the project created for tests.
     */
    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300);
        // Remove project
        TestUtil.removeProject(PROJECT_NAME);
        // Project shouldn't be accessible anymore
        assertFalse("Project is still accessible", project.isAccessible()); //$NON-NLS-1$
        super.tearDown();
    }
}
