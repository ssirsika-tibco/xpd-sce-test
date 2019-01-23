/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import java.util.Collections;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * JUnit test to test the correct disposal of the working copy and the editing
 * domain. This test will do the following:
 * <ol>
 * <li>Create a project with all assets</li>
 * <li>Create a Concept model</li>
 * <li>Delete the concept model</li>
 * <li>Recreate the concept model and check whether the working copy and editing
 * domain were recreated</li>
 * <li>Add a class and attribute to the model and check whether they were added
 * correctly</li>
 * </ol>
 * 
 * @author njpatel
 * 
 */
public class RecreateEditingDomainTest extends TestCase {

    private static final String PROJECT_NAME = "RecreateEdDomainTestProject";

    private static final String CONCEPT_FOLDER = "Concepts";

    private static final String CONCEPT_FILE_NAME = "CC14."
            + IConstants.BOM_FILE_EXTENTION;

    private IProject project;

    private IFolder conceptsFolder;

    @Override
    protected void setUp() throws Exception {
        System.out.println("-->Start of SetUp");
        TestUtil.createProject(PROJECT_NAME);
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);

        assertNotNull("Failed to create project", project);

        // Create concepts special folder
        SpecialFolder sf =
                TestUtil.createSpecialFolder(PROJECT_NAME,
                        CONCEPT_FOLDER,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        conceptsFolder = sf.getFolder();
        assertNotNull("Failed to get folder resource from concepts special folder",
                conceptsFolder);

        super.setUp();
        System.out.println("-->End of SetUp");
    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("-->Start of tearDown");

        // Delete the project
        TestUtil.removeProject(PROJECT_NAME);

        super.tearDown();
        System.out.println("-->End of tearDown");
    }

    /**
     * Test the editing domain recreation.
     * 
     * @throws Exception
     */
    public void testEditingDomain() throws Exception {
        IResource conceptFile;
        System.out.println("-->Start of testEditingDomain");

        // Add a concepts file

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getProjectRelativePath() + "/"
                        + CONCEPT_FILE_NAME);

        // Wait for background jobs to complete
        TestUtil.waitForJobs();

        // Get the concept file that has just been created
        conceptFile = conceptsFolder.findMember(CONCEPT_FILE_NAME);
        assertNotNull("Concept file not found: " + CONCEPT_FILE_NAME,
                conceptFile);

        // Get the working copy and the editing domain
        WorkingCopy firstWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(conceptFile);
        assertNotNull("Failed to get working copy (first attempt) for the concept model",
                firstWC);

        EditingDomain firstED = firstWC.getEditingDomain();
        assertNotNull("Failed to get the editing domain (first attempt) from the working copy",
                firstED);

        // Delete the concept file
        deleteConceptModel(conceptsFolder, conceptFile);
        // Wait for background jobs to complete
        TestUtil.waitForJobs();

        // Recreate the concept model

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getProjectRelativePath() + "/"
                        + CONCEPT_FILE_NAME);

        // Wait for background jobs to complete
        TestUtil.waitForJobs();

        // Get the concept file that has just been created
        conceptFile = conceptsFolder.findMember(CONCEPT_FILE_NAME);
        assertNotNull("Concept file not found", conceptFile);

        WorkingCopy secondWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(conceptFile);
        assertNotNull("Failed to get working copy (second attempt) for the concept model",
                secondWC);

        // Check that the working copy was recreated
        assertNotSame("Working copy was not recreated", firstWC, secondWC);

        EditingDomain secondED = secondWC.getEditingDomain();
        assertNotNull("Failed to get the editing domain (second attempt) from the working copy",
                secondED);

        // Test adding class and attribute to the model
        addClassAndAttribute(secondWC);
    }

    // /**
    // * Create the concept model
    // *
    // * @param conceptFolder
    // * concept folder to create concept file in
    // * @return <code>true</code> if operation was successful,
    // * <code>false</code> otherwise.
    // * @throws InvocationTargetException
    // * @throws InterruptedException
    // */
    // private boolean createConceptModel(IFolder conceptFolder)
    // throws InvocationTargetException, InterruptedException {
    // assertNotNull("Concept folder is null", conceptFolder);
    //
    // System.out.println("---->Creating concept model");
    //
    // BOMTestUtils.createBOMdiagram(conceptsFolder.getName(),
    // CONEPT_FILE_NAME);
    //
    // System.out.println("---->Completed creating concept model");
    //
    // return result[0];
    // }

    /**
     * Delete the given concept file
     * 
     * @param conceptFolder
     *            concepts folder
     * @param conceptFile
     *            concept file to delete
     * @throws CoreException
     */
    private void deleteConceptModel(IFolder conceptFolder, IResource conceptFile)
            throws CoreException {

        System.out.println("---->Deleting concept model");
        // Delete the concept file
        conceptFile.delete(true, null);
        TestUtil.waitForBuilds(300);

        conceptFile = conceptFolder.findMember(CONCEPT_FILE_NAME);
        assertNull("Concept file failed to be deleted", conceptFile);
        System.out.println("---->Completed deleting concept model");
    }

    /**
     * Test the adding of class and attribute (using two separate commands) to
     * the concept model
     * 
     * @param wc
     */
    private void addClassAndAttribute(WorkingCopy wc) {
        System.out.println("---->Adding class and attribute");
        // create a class
        Class c1 = UMLFactory.eINSTANCE.createClass();
        c1.setName("C1");
        Command cmd1 =
                AddCommand.create(wc.getEditingDomain(),
                        wc.getRootElement(),
                        UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT,
                        Collections.singletonList(c1));
        assertTrue(cmd1.canExecute());
        wc.getEditingDomain().getCommandStack().execute(cmd1);

        Property prop1 = UMLFactory.eINSTANCE.createProperty();
        prop1.setName("property1");

        Command cmd2 =
                AddCommand
                        .create(wc.getEditingDomain(),
                                c1,
                                UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                                prop1);
        assertTrue(cmd2.canExecute());
        wc.getEditingDomain().getCommandStack().execute(cmd2);

        System.out.println("---->Completed adding class and attribute");

        assertSame("Class not added", wc.getRootElement(), c1.eContainer());

        // Check attribute was added
        EList attrs = c1.getAttributes();

        assertNotNull("Attributes list is null", attrs);
        assertEquals("Wrong number of attributes", 1, attrs.size());

        assertSame("Attribute not added", prop1, attrs.get(0));

    }

}
