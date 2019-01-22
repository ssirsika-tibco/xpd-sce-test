/**
 * 
 */
package com.tibco.xpd.bom.test;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard;
import com.tibco.xpd.ui.wizards.newproject.XpdProjectWizard.CreateXpdProjectOperation;

/**
 * @author wzurek
 * 
 */
public class ConceptWorkingCopyTest extends TestCase {

    /**
     * 
     */
    private static final String BOM_DIAGRAM_EDITOR_ID =
            "com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorID";

    private static final String PROJECT_NAME =
            "com.tibco.xpd.bom.resources.test.wc.ConceptWorkingCopyTest";

    @Override
    protected void setUp() throws Exception {
        System.out.println("*** setUp start");
        super.setUp();

        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        assertTrue("All editors have to be closed before this test.",
                page.closeAllEditors(false));

        // setup the project
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject project = workspace.getRoot().getProject(PROJECT_NAME);

        final IProjectDescription description =
                workspace.newProjectDescription(project.getName());
        // description.setLocation(new Path(PROJECT_NAME));

        CreateXpdProjectOperation op =
                new XpdProjectWizard.CreateXpdProjectOperation(project,
                        description, null);

        op.run(new NullProgressMonitor());
        System.out.println(String.format("-- Project: %s created.",
                PROJECT_NAME));
        System.out.println("*** setUp end");
    }

    @Override
    protected void tearDown() throws Exception {
        System.out.println("*** tearDown start");

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        workspace.run(new IWorkspaceRunnable() {

            @Override
            public void run(IProgressMonitor monitor) throws CoreException {
                workspace.getRoot().getProject(PROJECT_NAME)
                        .delete(true, true, new NullProgressMonitor());
            }

        },
                null);
        System.out.println(String.format("-- Project: %s deleted.",
                PROJECT_NAME));
        super.tearDown();
        System.out.println("*** tearDown end");
        // THIS WAIT IS NECESSARY TO PROCESS DELTA AFTER PROJECT DELETE
        delay(2000);
    }

    @SuppressWarnings("unchecked")
    public void testConceptAsset() {
        ProjectConfig pc = getProjectConfig();
        EList<IProjectAsset> types = pc.getRegisteredAssetTypes();
        for (IProjectAsset at : types) {
            if ("com.tibco.xpd.asset.bom".equals(at.getId())) {
                return;
            }
        }
        fail("No concept asset type registered.");
    }

    @SuppressWarnings("unchecked")
    public void testCreateConcept() throws InvocationTargetException,
            InterruptedException, CoreException, IOException {
        System.out.println("*** testCreateConcept start");
        ProjectConfig pc = getProjectConfig();

        // create folder and mark is as a special folder
        IFolder conceptsIFolder = pc.getProject().getFolder("Concepts");
        conceptsIFolder.create(true, true, null);
        pc.getSpecialFolders().addFolder(conceptsIFolder,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        // access this folder through project config
        List<SpecialFolder> conceptsFolders =
                pc.getSpecialFolders()
                        .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        assertEquals("There should be one concepts special folder",
                1,
                conceptsFolders.size());
        SpecialFolder conceptsFolder = conceptsFolders.get(0);

        // create concept model

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getLocation() + "/Concept1."
                        + IConstants.BOM_FILE_EXTENTION);

        // test if editor is open
        IEditorPart editor =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertEquals("After creating a concept, the editor should be open",
                editor.getSite().getId(),
                BOM_DIAGRAM_EDITOR_ID);

        // --
        // create concept
        // --

        // obtain working copy
        IFile file =
                conceptsFolder.getFolder().getFile("Concept1."
                        + IConstants.BOM_FILE_EXTENTION);

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        assertTrue("Invalid type of working copy", wc instanceof BOMWorkingCopy);

        Model model = (Model) wc.getRootElement();

        // create concept
        Class class1 = UMLFactory.eINSTANCE.createClass();
        class1.setName("Concept 1");

        Command cmd =
                AddCommand.create(wc.getEditingDomain(),
                        model,
                        UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT,
                        class1);
        assertTrue(cmd.canExecute());
        wc.getEditingDomain().getCommandStack().execute(cmd);

        assertTrue("Working Copy not dirty after executing a command.",
                wc.isWorkingCopyDirty());

        // save the file
        wc.save();
        assertFalse("Working Copy dirty after save.", wc.isWorkingCopyDirty());
        System.out.println("*** testCreateConcept end");

    }

    @SuppressWarnings("unchecked")
    public void testCreateRemoveCreateConceptModel()
            throws InvocationTargetException, InterruptedException,
            CoreException, IOException {
        System.out.println("*** testCreateRemoveCreateConceptModel start");
        ProjectConfig pc = getProjectConfig();

        String conceptFileName = "Concept2." + IConstants.BOM_FILE_EXTENTION;

        System.out.println("-- Special filer created");
        // create folder and mark is as a special folder
        IFolder conceptsIFolder = pc.getProject().getFolder("Concepts");
        conceptsIFolder.create(true, true, null);
        // delay(5000);
        pc.getSpecialFolders().addFolder(conceptsIFolder,
                BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        System.out.println("-- Special filer created");

        // access this folder through project config
        List<SpecialFolder> conceptsFolders =
                pc.getSpecialFolders()
                        .getFoldersOfKind(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        assertEquals("There should be one concepts special folder",
                1,
                conceptsFolders.size());
        SpecialFolder conceptsFolder = conceptsFolders.get(0);

        // create concept model

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getLocation() + "/" + conceptFileName);

        // test if editor is open
        IEditorPart editor1 =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertEquals("After creating a concept, the editor should be open",
                editor1.getSite().getId(),
                BOM_DIAGRAM_EDITOR_ID);

        // load the file

        // obtain working copy
        final IFile file = conceptsFolder.getFolder().getFile(conceptFileName);
        WorkingCopy wc1 = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertTrue("Invalid type of working copy",
                wc1 instanceof BOMWorkingCopy);
        EObject model1 = wc1.getRootElement();
        assertNotNull(model1);

        wc1.addListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent arg0) {
                System.out.println("=-=-=-=-=-=-=-=-=-=-=-=: "
                        + arg0.getPropertyName());
            }
        });

        // ------------------------------------------------------------------------
        // remove the file
        System.out.println("-- Deleteing file");
        // WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
        // @Override
        // protected void execute(IProgressMonitor monitor)
        // throws CoreException, InvocationTargetException,
        // InterruptedException {
        // }
        // };
        file.delete(true, new NullProgressMonitor());
        // op.run(null);
        assertFalse(file.exists());
        System.out.println("-- File deleted");
        TestUtil.waitForJobs();
        System.out.println("-- After wait");

        // ------------------------------------------------------------------------
        // check if the editor is closed

        IEditorPart editor2 =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        if (editor2 != null) {
            /*
             * IF after first try it is not there the may have needed a little
             * more time to complete, so wait here only after we're sure we need
             * to.
             */
            TestUtil.delay(2000);
            editor2 =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage().getActiveEditor();
        }

        if (editor2 != null) {
            IEditorInput ei = editor2.getEditorInput();
            if (ei instanceof IFileEditorInput) {
                fail("After removing the file, editor should be closed: "
                        + ((IFileEditorInput) ei).getFile().getFullPath()
                                .toPortableString());
            } else {
                fail("After removing the file, editor should be closed: "
                        + ((IFileEditorInput) ei).getFile().getFullPath()
                                .toPortableString());
            }
        }
        assertNull("After removing the file, editor should be closed", editor2);

        WorkingCopy wc2 = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNull("After deleting the file, working copy should be removed",
                wc2);

        // create the file again
        // create concept model

        BOMTestUtils.createBOMdiagram(conceptsFolder.getProject().getName(),
                conceptsFolder.getLocation() + "/" + conceptFileName);

        // test if editor is open
        IEditorPart editor3 =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActiveEditor();
        assertEquals("After creating a concept, the editor should be open",
                editor3.getSite().getId(),
                BOM_DIAGRAM_EDITOR_ID);
        IFile file2 = conceptsFolder.getFolder().getFile(conceptFileName);
        assertTrue("File created again should exist.", file2.exists());
        WorkingCopy wc3 = XpdResourcesPlugin.getDefault().getWorkingCopy(file2);
        assertNotNull(wc3);

        System.out.println("*** testCreateRemoveCreateConceptModel end");
    }

    private ProjectConfig getProjectConfig() {
        XpdResourcesPlugin rp = XpdResourcesPlugin.getDefault();
        IProject project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);
        ProjectConfig pc = rp.getProjectConfig(project);
        assertNotNull("Project Config is not avaiable", pc);
        return pc;
    }

    /**
     * Process UI input but do not return for the specified time interval.
     * 
     * @param waitTimeMillis
     *            the number of milliseconds
     */
    private void delay(long waitTimeMillis) {
        Display display = Display.getCurrent();

        // If this is the UI thread,
        // then process input.
        if (display != null) {
            long endTimeMillis = System.currentTimeMillis() + waitTimeMillis;
            while (System.currentTimeMillis() < endTimeMillis) {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            }
            display.update();
        }
        // Otherwise, perform a simple sleep.
        else {
            try {
                Thread.sleep(waitTimeMillis);
            } catch (InterruptedException e) {
                // Ignored.
            }
        }
    }

    /**
     * Wait until all background tasks are complete.
     */
    public void waitForJobs() {
        while (Platform.getJobManager().currentJob() != null) {
            delay(1000);
        }
    }

}
