package com.tibco.xpd.bom.test.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.MoveResourcesOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.tests.BOMTestUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Test the BOM indexer. This has 3 tests:
 * <ol>
 * <li>Test project rename - this will test the inter-project referencing and
 * the indexer when a project is renamed</li>
 * <li>Test file rename - this will test the indexer when the bom file is
 * renamed</li>
 * <li>Test inserting a bom file into a bom special folder - this will test
 * whether the indexer reacts to direct creation of bom file in the special
 * folder without going through the working copy</li>
 * </ol>
 * 
 * @author njpatel
 * 
 */
public class BOMIndexerTests extends TestCase {

    private static final String MODEL_FILE = "/test-resources/mymodel.bom";

    private static final String PROJECT1_NEW_NAME = "Project1 New";

    private static final String PROJECT2_NAME = "Project2";

    private static final String PROJECT1_NAME = "Project1";

    private IProject project1;

    private IProject project2;

    @Override
    protected void setUp() throws Exception {
        // Create two projects
        project1 = TestUtil.createProject(PROJECT1_NAME);
        assertNotNull("Failed to create Project1", project1);
        assertTrue("Cannot access Project1", project1.isAccessible());
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForJobs();
        TestUtil.removeProject(PROJECT1_NAME);
        super.tearDown();
    }

    /**
     * Tests the referencing between projects and update of indexer (and
     * inter-project references) when project is renamed and then the rename
     * undone. This will do the following:
     * <ol>
     * <li>Create Project2 and set Project1 as a referenced project,</li>
     * <li>Create BOM models containing a class in each project,</li>
     * <li>Add attribute in class in Project2 and make it type of class in
     * Project1 - creating cross-project reference,</li>
     * <li>Rename Project1 and check that the attribute type is set as
     * unreferenced (is proxy) and the indexer has been updated with items from
     * this 'new' project (with the old items removed),</li>
     * <li>Undo the rename of the project and check that the attribute type
     * reference is restored and indexer updated.</li>
     * </ol>
     * 
     * @throws Exception
     */
    public void testReferencedProjectRename() throws Exception {
        project2 = TestUtil.createProject(PROJECT2_NAME);
        assertNotNull("Failed to create Project2", project2);
        assertTrue("Cannot access Project2", project2.isAccessible());

        // Create a project reference from Project2 to Project1
        IProjectDescription description = project2.getDescription();
        description.setReferencedProjects(new IProject[] { project1 });

        project2.setDescription(description, null);

        // Confirm that the reference has been set
        assertTrue("Project reference failed",
                project2.getReferencedProjects().length == 1);

        // Create model in project1 and project2
        IFile file1 = createModel(project1, "businessModel1.bom", 1);
        IFile file2 = createModel(project2, "businessModel2.bom", 1);

        Class class1 = getFirstClass(file1);
        Class class2 = getFirstClass(file2);

        // Add attribute in class2 of type class1
        Property prop = addAttribute(class2, class1);
        assertNotNull("Failed to create property", prop);
        saveModel(class2);

        renameResource(project1, PROJECT1_NEW_NAME);

        // Check that the indexer has been updated
        _testIndexerAfterProjectRename(PROJECT1_NAME, PROJECT1_NEW_NAME);

        // Check that the attribute type is unreferenced due to the rename of
        // project
        assertTrue("Property Type is not proxy as expected after project rename",
                prop.getType().eIsProxy());

        // Undo the rename
        undoResourceOperation();

        // Confirm that the project rename was undone
        IResource newRes =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(PROJECT1_NEW_NAME);
        IResource oldRes =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findMember(PROJECT1_NAME);

        assertTrue("Rename of project failed", newRes == null && oldRes != null);

        // Check that the attribute type has been restored
        assertFalse("Property Type is proxy after the project has been restored",
                prop.getType().eIsProxy());

        // Confirm that the indexer has been updated
        _testIndexerAfterProjectRename(PROJECT1_NEW_NAME, PROJECT1_NAME);

        // Remove project 2
        TestUtil.removeProject(PROJECT2_NAME);
    }

    /**
     * Tests whether the indexer gets updated with the bom file has been
     * renamed. This will:
     * <ol>
     * <li>Create a model with a number of classes</li>
     * <li>Query indexer service to see if all objects have been indexed</li>
     * <li>Rename the file</li>
     * <li>Re-query the indexer to check whether items from the original file
     * are present</li>
     * <li>Query the indexer for items from the new file created</li>
     * </ol>
     * 
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public void testFileRename() throws Exception {
        final String fileName = "BusinessModel.bom";
        final String newFileName = "New_Business_Model.bom";

        // Create the models with 10 classes
        IFile file = createModel(project1, fileName, 10);

        // Check that the indexer has got entries for this file
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        assertNotNull("Failed to get indexer service", service);
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PATH, file.getFullPath()
                .toPortableString());
        Collection<IndexerItem> items =
                service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        // 11 items = 1 model and 10 classes
        assertEquals("Number of items in the indexer when model created",
                11,
                items.size());

        // Save the resource
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull(String.format("Failed to get working copy for %s", file
                .getFileExtension().toString()), wc);
        wc.save();
        TestUtil.waitForJobs();
        assertFalse("Working copy still dirty after save",
                wc.isWorkingCopyDirty());

        // Rename the file
        IResource newResource = renameResource(file, newFileName);

        // Re-query the indexer with the old file name - should have no items
        items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals("Number of indexed items of the original file after rename",
                0,
                items.size());

        // Query with the new file path
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PATH, newResource
                .getFullPath().toPortableString());
        items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals("Number of indexer items for the renamed resource",
                11,
                items.size());
    }

    /**
     * Tests whether a file inserted into a special folder will be indexed. This
     * will:
     * <ol>
     * <li>Create BOM special folder</li>
     * <li>Query the indexer service to see if any items are indexed for this
     * project - should be none</li>
     * <li>Insert the file /test-resources/mymodel.bom into the special folder</li>
     * <li>Re-query the indexer service - this time it should have indexed items
     * from the created file</li>
     * <li>Undo the file create and retest the indexer - no indexed items should
     * be present</li>
     * </ol>
     * 
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public void testInsertFile() throws Exception {
        // Create BOM Special folder
        SpecialFolder sf =
                TestUtil.createSpecialFolder(project1,
                        "Business Models",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);

        IFolder folder = sf.getFolder();
        assertTrue("Cannot access the bom special folder", folder != null
                && folder.isAccessible());

        // Check for items in the indexer for this project - should be 0
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        assertNotNull("Failed to get indexer service, service", service);
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PROJECT, project1.getName());
        Collection<IndexerItem> items =
                service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals("Number of items before the model is created",
                0,
                items.size());

        // Inject the BOM file into the special folder
        final IFile file = folder.getFile("Test.bom");
        final InputStream inStream =
                getClass().getClassLoader().getResourceAsStream(MODEL_FILE);
        assertNotNull(String.format("Failed to access %s", MODEL_FILE),
                inStream);

        CreateFileOperation createOp =
                new CreateFileOperation(file, null, inStream, "Create BOM");

        PlatformUI
                .getWorkbench()
                .getOperationSupport()
                .getOperationHistory()
                .execute(createOp,
                        new NullProgressMonitor(),
                        WorkspaceUndoUtil.getUIInfoAdapter(Display.getDefault()
                                .getActiveShell()));

        TestUtil.waitForJobs();

        assertTrue("File not created", file.exists());

        migrate(file);

        TestUtil.waitForJobs();

        // Check indexer service to see if the file was indexed
        items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals("Number of items indexed for this project after file created",
                11,
                items.size());

        // Undo the create
        undoResourceOperation();

        // Check indexer service to see if it was updated
        items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals("Number of items indexed for this project after created file removed",
                0,
                items.size());
    }

    /**
     * Rename the given resource to the given name. This method will test that
     * the rename was successful.
     * 
     * @param res
     *            Resource to rename
     * @param string
     *            new name for the resource
     * @return new resource after rename.
     * @throws CoreException
     * @throws ExecutionException
     */
    private IResource renameResource(IResource res, String name)
            throws CoreException, ExecutionException {
        IResource newResource = null;

        if (res != null) {
            IPath path = res.getFullPath();
            path = path.removeLastSegments(1);
            path = path.append(name);
            final IPath newPath = path;

            MoveResourcesOperation op =
                    new MoveResourcesOperation(res, newPath, "Rename Resource");
            PlatformUI
                    .getWorkbench()
                    .getOperationSupport()
                    .getOperationHistory()
                    .execute(op,
                            new NullProgressMonitor(),
                            WorkspaceUndoUtil.getUIInfoAdapter(Display
                                    .getDefault().getActiveShell()));

            TestUtil.waitForJobs();

            // Confirm that resource that has been renamed doesn't exist
            assertFalse(String.format("Expected resource '%s' to be non-existent after rename",
                    res.getName()),
                    res.exists());

            // Check that the new resource has been created
            newResource = res.getParent().findMember(name);
            assertTrue(String.format("Expected project '%s' to be existing after rename",
                    name),
                    newResource != null && newResource.exists());
        }

        return newResource;
    }

    /**
     * Undo the last resource operation. This method does not test that the undo
     * was successful.
     * 
     * @throws ExecutionException
     */
    private void undoResourceOperation() throws ExecutionException {
        IOperationHistory opHistory =
                PlatformUI.getWorkbench().getOperationSupport()
                        .getOperationHistory();

        // Get the last workbench undoable command
        IUndoableOperation undoOperation =
                opHistory.getUndoOperation(PlatformUI.getWorkbench()
                        .getOperationSupport().getUndoContext());
        assertNotNull("Failed to get Resource operation from the undo history",
                undoOperation);

        // Undo the change
        opHistory.undoOperation(undoOperation,
                new NullProgressMonitor(),
                WorkspaceUndoUtil.getUIInfoAdapter(Display.getDefault()
                        .getActiveShell()));

        TestUtil.waitForJobs();
    }

    /**
     * Save the model in which the given class belongs.
     * 
     * @param class2
     * @throws IOException
     */
    private void saveModel(Class cls) throws IOException {
        if (cls != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(cls);
            assertTrue(String.format("Unable to get working copy for class '%s'",
                    cls.getName()),
                    wc instanceof BOMWorkingCopy);

            assertTrue(String.format("Expected resource containing class '%s' to be dirty",
                    cls.getName()),
                    wc.isWorkingCopyDirty());

            // Save the model
            wc.save();
            TestUtil.waitForJobs();

            // Confirm model is saved
            assertFalse(String.format("Expected resource containing class '%s' to be saved",
                    cls.getName()),
                    wc.isWorkingCopyDirty());
        }
    }

    /**
     * Add attribute in class2 and make a type of class1.
     * 
     * @param class2
     * @param class1
     */
    private Property addAttribute(Class class2, Class class1) {
        Property prop = null;
        if (class2 != null && class1 != null) {
            prop = UMLFactory.eINSTANCE.createProperty();
            prop.setName("Attribute1");
            prop.setType(class1);

            TransactionalEditingDomain ed =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            Command cmd =
                    AddCommand.create(ed, class2, UMLPackage.eINSTANCE
                            .getStructuredClassifier_OwnedAttribute(), prop);

            if (cmd.canExecute()) {
                ed.getCommandStack().execute(cmd);
            }
        }

        return prop;
    }

    /**
     * Create BOM models in the given project using the given filename as the
     * bom filename (it will also create a BOM special folder automatically).
     * This will check that the file has been created.
     * 
     * @param project
     * @param fileName
     * @param numOfClasses
     *            number of classes to create in the model
     * @return created <code>IFile</code>, or <code>null</code> if failed to
     *         create file.
     * @throws Exception
     */
    private IFile createModel(IProject project, String fileName,
            int numOfClasses) throws Exception {
        // Create BOM special folder
        SpecialFolder sFolder =
                TestUtil.createSpecialFolder(project,
                        "Bom",
                        BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        TestUtil.waitForJobs();
        assertNotNull("Failed to create special folder in Project1", sFolder);

        IFile file = BOMTestUtils.createBOMdiagram(sFolder, fileName);
        TestUtil.waitForJobs();
        assertNotNull(String.format("Failed to create model in %s",
                project.getName()),
                file);

        if (numOfClasses == 1) {
            // Create a class in the model
            BOMTestUtils.createClassInEditor(fileName);
        } else if (numOfClasses > 1) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);
            assertNotNull(String.format("Failed to get working copy for %s",
                    file.getFullPath().toString()), wc);

            EObject root = wc.getRootElement();
            assertTrue("Root element is not model", root instanceof Model);

            BOMTestUtils.createClasses((Package) root, numOfClasses);
        }

        return file;
    }

    /**
     * Get the class from the given bom file.
     * 
     * @param file
     * @return <code>Class</code> if found, <code>null</code> otherwise.
     */
    private Class getFirstClass(IFile file) {
        Class cls = null;

        if (file != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(file);
            assertTrue(String.format("Failed to get working copy for %s", file
                    .getFullPath().toString()), wc instanceof BOMWorkingCopy);

            EObject root = wc.getRootElement();
            assertNotNull(String.format("Failed to get root element of the model in %s",
                    file.getFullPath().toString()),
                    root);

            EObject eo = root.eContents().get(1);
            assertTrue(String.format("Cannot access class in %s", file
                    .getFullPath().toString()), eo instanceof Class);

            cls = (Class) eo;
        }
        return cls;
    }

    /**
     * Tests the indexer service. Called after a rename of project to check
     * whether the indexer was updated.
     * 
     * @param oldProjectName
     *            resource name before rename.
     * @param newProjectName
     *            resource name after rename.
     */
    @SuppressWarnings("restriction")
    private void _testIndexerAfterProjectRename(String oldProjectName,
            String newProjectName) {
        IndexerService service =
                XpdResourcesPlugin.getDefault().getIndexerService();
        assertNotNull("Indexer Service is null", service);
        // No items from the old resource should exist
        IndexerItemImpl criteria = new IndexerItemImpl();
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PROJECT, oldProjectName);
        Collection<IndexerItem> items =
                service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertEquals(String.format("Number of items from old project '%s' before rename",
                oldProjectName),
                0,
                items.size());

        // New project after rename should be indexed
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PROJECT, newProjectName);
        items = service.query(BOMResourcesPlugin.BOM_INDEXER_ID, criteria);
        assertTrue("Expected items to be indexed from the new project after rename",
                items.size() > 0);
    }

    /**
     * Migrate the given bom model.
     * 
     * @param bomFile
     * @throws CoreException
     */
    private void migrate(IFile bomFile) throws CoreException {
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(bomFile);
        if (wc instanceof AbstractWorkingCopy) {
            EObject root = wc.getRootElement();
            if (root == null && ((AbstractWorkingCopy) wc).isInvalidVersion()) {
                ((AbstractWorkingCopy) wc).migrate();
            }
        }
    }
}
