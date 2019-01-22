/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test.resource;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * BOM project dependency test. This will do the following:
 * <ol>
 * <li>Create 2 projects with BOM special folders</li>
 * <li>Make the first project a reference project in the second project</li>
 * <li>Create BOM resources in each special folder</li>
 * <li>Create a class in each model</li>
 * <li>Create a generalization from class in project 2 to class in project 1</li>
 * <li>Remove the project references from project 2</li>
 * <li>Check that the generalization reference in model in project 2 is now
 * unresolved (will be proxy)</li>
 * <li>Re-add the reference between the projects</li>
 * <li>Check that the reference in the model has been restored</li>
 * </ol>
 * 
 * @author njpatel
 * 
 */
public class BOMProjectDependecyTest extends TestCase {

    private static final String PROJECT1_NAME = "TestBOMProject1";

    private static final String PROJECT2_NAME = "TestBOMProject2";

    private static final String FOLDER1_NAME = "bom_Folder_1";

    private static final String FOLDER2_NAME = "bomFolder2";

    private static final String BOM_FILE1_NAME = "file1.bom";

    private static final String BOM_FILE2_NAME = "bom_file_2.bom";

    private IProject project1, project2;

    private SpecialFolder folder1, folder2;

    private BOMWorkingCopy bomWc1, bomWc2;

    @Override
    protected void setUp() throws Exception {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        // Create project1
        TestUtil.createProject(PROJECT1_NAME);
        project1 = root.getProject(PROJECT1_NAME);
        assertNotNull(String.format("Failed to create %s", PROJECT1_NAME),
                project1);
        // Create project2
        TestUtil.createProject(PROJECT2_NAME);
        project2 = root.getProject(PROJECT2_NAME);
        assertNotNull(String.format("Failed to create %s", PROJECT2_NAME),
                project2);
        // Create special folder in project1
        folder1 =
                TestUtil.createSpecialFolder(PROJECT1_NAME,
                        FOLDER1_NAME,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        assertNotNull(String.format("Failed to create, or cannot access special folder in %s",
                PROJECT1_NAME),
                folder1);
        // Create special folder in project2
        folder2 =
                TestUtil.createSpecialFolder(PROJECT2_NAME,
                        FOLDER2_NAME,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        assertNotNull(String.format("Failed to create, or cannot access special folder in %s",
                PROJECT2_NAME),
                folder2);

        // Make project1 a reference project in project2
        addProjectReference(project2, project1);
    }

    public void testDependency() throws Exception {
        // Create the bom files in each project
        BOMTestUtils.createBOMdiagram(folder1, BOM_FILE1_NAME);
        BOMTestUtils.createBOMdiagram(folder2, BOM_FILE2_NAME);

        // Create a class in each resource
        BOMTestUtils.createClassInEditor(BOM_FILE1_NAME);
        BOMTestUtils.createClassInEditor(BOM_FILE2_NAME);

        // Get the files created
        IResource bomFile1 = folder1.getFolder().findMember(BOM_FILE1_NAME);
        assertNotNull(String.format("Cannot find %1$s in %2$s",
                BOM_FILE1_NAME,
                PROJECT1_NAME), bomFile1);
        IResource bomFile2 = folder2.getFolder().findMember(BOM_FILE2_NAME);
        assertNotNull(String.format("Cannot find %1$s in %2$s",
                BOM_FILE2_NAME,
                PROJECT2_NAME), bomFile2);

        // Get the working copies
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile1);
        assertTrue(String.format("Working copy for %s is not BOMWorkingCopy",
                BOM_FILE1_NAME), wc instanceof BOMWorkingCopy);
        bomWc1 = (BOMWorkingCopy) wc;
        wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile2);
        assertTrue(String.format("Working copy for %s is not BOMWorkingCopy",
                BOM_FILE2_NAME), wc instanceof BOMWorkingCopy);
        bomWc2 = (BOMWorkingCopy) wc;

        // Add generalization from model in project2 to model in project1
        addGeneralization(bomWc1, bomWc2);
        // Remove the project reference
        removeReferencedProjects(project2);
        // Test the broken reference link in model in project2
        testClassReference(bomWc2,
                true,
                "Class reference is still present after the project reference has been removed");
        // Re-add the project reference
        addProjectReference(project2, project1);
        // Re-test the class reference - should be restored
        testClassReference(bomWc2,
                false,
                "Class reference should have been restored after project reference was re-added");
    }

    /**
     * Add project p2 to the referenced list of project p2.
     * 
     * @param p1
     * @param p2
     * @throws CoreException
     */
    private void addProjectReference(IProject p1, IProject p2)
            throws CoreException {
        // Make project1 depend on project2
        IProjectDescription description = p1.getDescription();
        description.setReferencedProjects(new IProject[] { p2 });
        p1.setDescription(description, null);

        TestUtil.waitForJobs();

        assertTrue("Failed to add dependency in project",
                p1.getReferencedProjects().length == 1);
    }

    /**
     * Add a generalization from class in bom file in project2 to bom file in
     * project1 - thus creating a reference between the two.
     * 
     * @param bomWc12
     * @param bomWc22
     */
    private void addGeneralization(BOMWorkingCopy wc1, BOMWorkingCopy wc2) {
        EObject root1 = wc1.getRootElement();
        assertNotNull(String.format("Root element in %s is null", wc1.getName()),
                wc1);

        // Find the class in first model
        Class c1 = null;
        for (EObject eo : root1.eContents()) {
            if (eo instanceof Class) {
                c1 = (Class) eo;
                break;
            }
        }
        assertNotNull(String.format("No class found in %s", wc1.getName()), c1);

        EObject root2 = wc2.getRootElement();
        assertNotNull(String.format("Root element in %s is null", wc1.getName()),
                wc2);

        // Find the class in 2nd model
        Class c2 = null;
        for (EObject eo : root2.eContents()) {
            if (eo instanceof Class) {
                c2 = (Class) eo;
                break;
            }
        }

        assertNotNull(String.format("No class found in %s", wc2.getName()), c2);

        // Create generalization
        EditingDomain ed = wc2.getEditingDomain();
        assertNotNull(String.format("Failed to get editing domain from %s",
                wc1.getName()),
                ed);

        Generalization gen = UMLFactory.eINSTANCE.createGeneralization();
        gen.setGeneral(c1);
        Command cmd =
                AddCommand.create(ed,
                        c2,
                        UMLPackage.eINSTANCE.getClassifier_Generalization(),
                        gen);

        assertTrue("Cannot execute add generalization command",
                cmd.canExecute());

        ed.getCommandStack().execute(cmd);

        TestUtil.waitForJobs();
    }

    /**
     * Remove all project dependencies from the given project.
     * 
     * @param project
     * @throws CoreException
     */
    private void removeReferencedProjects(IProject project)
            throws CoreException {
        IProjectDescription description = project2.getDescription();
        description.setReferencedProjects(new IProject[] {});
        project.setDescription(description, null);
        assertTrue(String.format("Failed to remove project dependencies from %s",
                project.getName()),
                project.getReferencedProjects().length == 0);

        TestUtil.waitForJobs();
    }

    /**
     * Test the class reference between the two models.
     * 
     * @param wc
     *            model to check reference in.
     * @param isProxy
     *            <code>true</code> if the reference should be proxy (ie not
     *            resolved), <code>false</code> otherwise.
     * @param assertMsg
     *            assert message to print if test failed.
     */
    private void testClassReference(BOMWorkingCopy wc, boolean isProxy,
            String assertMsg) {
        EObject root = wc.getRootElement();

        // Take into account badge EAnnotation
        Class cl = (Class) root.eContents().get(1);
        EList<Generalization> generalizations = cl.getGeneralizations();
        assertTrue("Expected one generalization", generalizations.size() == 1);
        Generalization gen = generalizations.get(0);
        Classifier general = gen.getGeneral();
        assertTrue(assertMsg, general.eIsProxy() == isProxy);
    }

    @Override
    protected void tearDown() throws Exception {
        if (project1 != null) {
            TestUtil.removeProject(PROJECT1_NAME);
        }

        if (project2 != null) {
            TestUtil.removeProject(PROJECT2_NAME);
        }
    }
}
