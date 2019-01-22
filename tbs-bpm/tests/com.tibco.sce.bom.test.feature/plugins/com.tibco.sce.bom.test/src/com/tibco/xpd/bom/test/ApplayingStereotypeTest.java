/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
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
 * I tries to create a concept with an attribute, then it delete the file and
 * tries to do the same again.
 * 
 * @author wzurek
 */
public class ApplayingStereotypeTest extends TestCase {

    /**
     * name of the file
     */
    private static final String CONCEPT_FILE_NAME = "ApplayingStereotypeTest."
            + IConstants.BOM_FILE_EXTENTION;

    private static final String PROJECT_NAME =
            "com.tibco.xpd.bom.resources.test.wc.ApplayingStereotypeTest";

    /**
     * I tries to create a concept with an attribute, then it delete the file
     * and tries to do the same again.
     */
    @SuppressWarnings("unchecked")
    public void testCreateAttributeInRecreatedFile() throws CoreException,
            InvocationTargetException, InterruptedException, IOException {
        SpecialFolder conceptsFolder =
                TestUtil.createSpecialFolder(PROJECT_NAME,
                        "Concepts",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getLocation() + "/" + CONCEPT_FILE_NAME);

        TestUtil.waitForJobs();

        // test if editor is open
        IEditorPart editor =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertEquals("After creating a concept, the editor should be open",
                editor.getSite().getId(),
                "com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorID");

        {
            IFile file = conceptsFolder.getFolder().getFile(CONCEPT_FILE_NAME);
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);
            // create concept with the attribute
            createConceptWithOneAttribute(wc);
            assertTrue("Working copy should be dirty", wc.isWorkingCopyDirty());
            wc.save();
            TestUtil.waitForJobs();
            assertFalse("Working copy should not be dirty",
                    wc.isWorkingCopyDirty());
        }

        // --
        // Delete the file
        // --

        final IFolder conceptsIFolder = conceptsFolder.getFolder();
        assertTrue("Special folder doesn't exist or can't be accessed",
                conceptsIFolder != null && conceptsIFolder.isAccessible());
        final IResource res = conceptsIFolder.findMember(CONCEPT_FILE_NAME);
        assertTrue(String.format("File %s not found or cannot be accessed",
                CONCEPT_FILE_NAME), res instanceof IFile && res.isAccessible());

        ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                // Delete the file
                res.delete(true, monitor);
            }

        }, new NullProgressMonitor());

        // give a chance to the delete file job and editor to close
        TestUtil.waitForJobs();
        // test if editor is closed
        editor =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();

        if (editor != null) {
            /*
             * IF after first try it is not there the may have needed a little
             * more time to complete, so wait here only after we're sure we need
             * to.
             */
            TestUtil.delay(2000);
            editor =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActiveEditor();
        }
        assertNull("After removing a concept file, "
                + "the editor should be closed", editor);

        // --
        // Create the file again
        // --

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getLocation() + "/" + CONCEPT_FILE_NAME);

        // --
        // create concept
        // --

        // obtain working copy
        IFile file = conceptsIFolder.getFile(CONCEPT_FILE_NAME);

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        assertTrue("Invalid type of working copy", wc instanceof BOMWorkingCopy);

        // create concept with the attribute
        createConceptWithOneAttribute(wc);
    }

    /**
     * Create a concept with an attribute in given working copy.
     * 
     * @param wc
     */
    private void createConceptWithOneAttribute(WorkingCopy wc) {
        Model model = (Model) wc.getRootElement();
        Class class1 = UMLFactory.eINSTANCE.createClass();
        class1.setName("Concept 1");

        Command cmd =
                AddCommand.create(wc.getEditingDomain(),
                        model,
                        UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT,
                        class1);
        assertTrue("Cannot add class", cmd.canExecute());
        wc.getEditingDomain().getCommandStack().execute(cmd);
        assertSame("Class was not added to the model",
                model,
                class1.eContainer());

        assertTrue("Working Copy not dirty after executing a command.",
                wc.isWorkingCopyDirty());
        EList<Stereotype> stereotypes = class1.getAppliedStereotypes();

        // create attribute
        Property prop = UMLFactory.eINSTANCE.createProperty();
        prop.setName("QWE");
        cmd =
                AddCommand
                        .create(wc.getEditingDomain(),
                                class1,
                                UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                                prop);
        assertTrue("Cannot add property", cmd.canExecute());
        wc.getEditingDomain().getCommandStack().execute(cmd);

        stereotypes = prop.getAppliedStereotypes();
        assertSame("Property is not added to the class",
                class1,
                prop.eContainer());

        // FIXME: temporary disabled check for stereotypes
        // assertEquals("Invalid numbers of stereotypes (property)", 1,
        // stereotypes.size());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestUtil.createProject(PROJECT_NAME);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300);

        TestUtil.removeProject(PROJECT_NAME);
        // THIS WAIT IS NECESSARY TO PROCESS DELTA AFTER PROJECT DELETE
    }
}
