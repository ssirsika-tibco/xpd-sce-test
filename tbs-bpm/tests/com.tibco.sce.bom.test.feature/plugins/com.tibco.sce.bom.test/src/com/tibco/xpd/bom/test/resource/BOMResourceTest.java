/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test.resource;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditor;
import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * Test for BOM resources. This test suite contain 3 tests:
 * <ol>
 * <li><strong>{@link #testResourceUnload() Test resource unload}:</strong> This
 * will confirm that when an EMF resource is unloaded its associated working
 * copy will be unloaded and any editor closed.</li>
 * <li><strong>{@link #testEditModelOutsideEditor() Test editing model outside
 * editor}:</strong> This will confirm that when the model is changed outside
 * the editor the editor is synch'd with the changes (if the editor is not dirty
 * - if editor is dirty then the user will be given an option to update the
 * editor.</li>
 * </ol>
 * 
 * @author njpatel
 * 
 */
public class BOMResourceTest extends TestCase {

    private static final String PROJECT_NAME = "BOMResourceTest";

    private static final String FOLDER_NAME = "Business model";

    private static final String BOM_FILE = "testBom.bom";

    private IProject project;

    private SpecialFolder sFolder;

    private BOMWorkingCopy bomWc;

    @Override
    protected void setUp() throws Exception {
        // Create Xpd Project
        TestUtil.createProject(PROJECT_NAME);
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);
        assertTrue("Failed to create project, or project not accessible",
                project != null && project.isAccessible());

        // Create special folder
        sFolder =
                TestUtil.createSpecialFolder(PROJECT_NAME,
                        FOLDER_NAME,
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        assertTrue("Failed to create special folder, or special folder not accessible",
                sFolder != null && sFolder.getFolder() != null
                        && sFolder.getFolder().isAccessible());
    }

    /**
     * Test resource unload. This will:
     * <ol>
     * <li>Create bom resource,</li>
     * <li>Check that a working copy is available for the resource,</li>
     * <li>Check that the resource has been loaded in resourceset,</li>
     * <li>Check that the editor is open,</li>
     * <li>Unload the resource - which should close the editor and remove the
     * working copy.</li>
     * </ol>
     * 
     * @throws Exception
     */
    public void testResourceUnload() throws Exception {
        IResource bomFile = _testLoadDiagram();

        EObject rootElement = bomWc.getRootElement();
        assertNotNull("Root element is null", rootElement);

        // Check that the resource has been loaded in resource set
        URI uri =
                URI.createPlatformResourceURI(bomFile.getFullPath().toString(),
                        true);
        TransactionalEditingDomain ed =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        assertNotNull("Shared transactional editing domain is null", ed);

        final Resource resource = ed.getResourceSet().getResource(uri, false);
        assertNotNull("Xpd resource is null", resource);
        assertTrue("Resource not loaded", resource.isLoaded());

        // Unload the resource
        ed.runExclusive(new Runnable() {
            @Override
            public void run() {
                resource.unload();
            }
        });

        TestUtil.waitForJobs();

        IEditorPart editor = BOMTestUtils.getEditor(BOM_FILE);
        assertNotNull("Editor is not open after resource unloaded", editor);

        // Check that the working copy has reloaded the model
        assertNotSame("Root element in working copy not reloaded after resource unloaded",
                bomWc.getRootElement(),
                rootElement);
    }

    /**
     * Test editing of model outside editor. This is to test the editor's synch
     * with the model. This test will:
     * <ol>
     * <li>Create a bom resource,</li>
     * <li>Add a class to the model in the data model,</li>
     * <li>Check the editor for the class edit part.</li>
     * </ol>
     * 
     * @throws Exception
     */
    public void testEditModelOutsideEditor() throws Exception {
        IResource bomFile = _testLoadDiagram();
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertTrue("Woking copy is not instance of BOMWorkingCopy",
                wc instanceof BOMWorkingCopy);

        BOMWorkingCopy bomWc = (BOMWorkingCopy) wc;

        IEditorPart editor = BOMTestUtils.getEditor(BOM_FILE);
        assertNotNull("Editor not found", editor);

        // Add a new class to the model outside the editor
        EObject rootElement = wc.getRootElement();
        assertTrue("Model not found in bom working copy",
                rootElement instanceof Model);
        EditingDomain ed = bomWc.getEditingDomain();
        assertNotNull("Editing domain from working copy is null", ed);

        Class cl = UMLFactory.eINSTANCE.createClass();
        cl.setName("MyClass");

        Command cmd =
                AddCommand.create(ed,
                        rootElement,
                        UMLPackage.eINSTANCE.getPackage_PackagedElement(),
                        cl);

        assertTrue("Add class command is not executable", cmd.canExecute());

        ed.getCommandStack().execute(cmd);

        TestUtil.waitForJobs();

        // Ignore first element which is the EAnnotations
        // Model now also have profile applications as a part of the content, so
        // better ask more precisely.
        Model model = (Model) rootElement;
        assertTrue("Class not added to model",
                model.getPackagedElement("MyClass") instanceof Class);

        // Verify that the class has been added to the editor
        if (editor instanceof UMLDiagramEditor) {
            GraphicalViewer viewer =
                    (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);

            RootEditPart rootEditPart = viewer.getRootEditPart();
            assertNotNull("Failed to get root edit part from the editor",
                    rootEditPart);

            // Get the model edit part
            EditPart modelPart = (EditPart) rootEditPart.getChildren().get(0);

            // Confirm that the model edit part has one class edit part
            assertTrue("Model edit part has no children", modelPart
                    .getChildren().size() == 2);
            Object object = modelPart.getChildren().get(1);
            assertTrue("Class edit part not found in the model",
                    object instanceof ClassEditPart);

        } else {
            fail("Editor is not UMLDiagramEditor");
        }
    }

    /**
     * Test the creation of a diagram. This will:
     * <ol>
     * <li>Create the diagram,</li>
     * <li>Check that the working copy and editor are created.</li>
     * </ol>
     */
    private IResource _testLoadDiagram() {
        BOMTestUtils.createBOMdiagram(sFolder, BOM_FILE);
        // Check if file was created
        IResource bomFile = sFolder.getFolder().findMember(BOM_FILE);
        assertNotNull("Cannot find bom file in special folder", bomFile);

        // Load working copy
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertTrue("Working copy not an instance of BOMWorkingCopy",
                wc instanceof BOMWorkingCopy);
        bomWc = (BOMWorkingCopy) wc;

        // Check editor
        IEditorPart editor =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertNotNull("Cannot find editor", editor);
        IEditorInput input = editor.getEditorInput();
        assertTrue("Editor input is not FileEditorInput",
                input instanceof FileEditorInput);
        FileEditorInput fileInput = (FileEditorInput) input;
        assertEquals("Editor input hasn't been updated after move",
                fileInput.getFile(),
                bomFile);
        assertFalse("Editor is dirty after move", editor.isDirty());

        return bomFile;
    }

    @Override
    protected void tearDown() throws Exception {
        if (project != null) {
            TestUtil.removeProject(PROJECT_NAME);
        }
    }
}
