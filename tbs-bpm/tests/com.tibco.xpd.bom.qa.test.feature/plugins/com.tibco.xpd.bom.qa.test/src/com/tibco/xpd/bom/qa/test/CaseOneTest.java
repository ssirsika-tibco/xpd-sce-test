package com.tibco.xpd.bom.qa.test;

import java.io.File;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

public class CaseOneTest extends TestCase {

	// Constants
	private static final String PROJECT_NAME = "CaseOneProj"; //$NON-NLS-1$
	private static final String FOLDER_NAME = "MyBOM"; //$NON-NLS-1$
	private static final String FOLDER_KIND = "bom"; //$NON-NLS-1$
	private static final String BOM_FILE_NAME = "mytest.bom"; //$NON-NLS-1$

	private String bomLocation = FOLDER_NAME + "/" + BOM_FILE_NAME; //$NON-NLS-1$
	private IProgressMonitor monitor = new NullProgressMonitor();

	/**
	 * Test scenario:
	 * <ol>
	 * <li>Create an empty project with no assets
	 * <li>Create a folder and set it BOM special folder
	 * <li>Create a BOM diagram
	 * <li>Create 2 classes and ensure names are unique
	 * <li>Delete the classes
	 * <li>Ensure the classes are gone
	 * <li>Save BOM diagram/editor
	 * <li>Delete the .bom file from file system
	 * <li>Ensure BOM diagram doesn't exist in project
	 * <li>Ensure BOM editor is not open
	 * <li>Remove Project (done in teardown)
	 * </ol>
	 * 
	 * @throws Exception
	 */
	public void testCaseOne() throws Exception {

		this.setName("CaseOneTest.testCaseOne()"); //$NON-NLS-1$

		// Resets the perspective
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		page.resetPerspective();

		// Creates a project with no asset type
		System.out.println("Create a new project ..."); //$NON-NLS-1$
		IProject project = TestUtil.createProject(PROJECT_NAME);
		// Ensure project is not null
		assertNotNull(project);
		// Ensure project is accessible
		assertEquals(true, project.isAccessible());

		TestUtil.delay(2000);

		// Create a folder and assign it BOM type special folder
		System.out.println("Create BOM special folder ..."); //$NON-NLS-1$
		SpecialFolder bomFolder = TestUtil.createSpecialFolder(project,
				FOLDER_NAME, FOLDER_KIND);

		TestUtil.delay(2000);

		// Create a BOM diagram in the BOM folder
		System.out.println("Create BOM model ..."); //$NON-NLS-1$
		IFile file = BOMTestUtils.createBOMdiagram(bomFolder, BOM_FILE_NAME);

		TestUtil.delay(2000);

		// Check the BOM diagram is added to project
		IResource resource = project.findMember(bomLocation);
		assertNotNull(resource);

		// Get a handle to the BOM model
		AbstractGMFWorkingCopy wc = (AbstractGMFWorkingCopy) XpdResourcesPlugin
				.getDefault().getWorkingCopy(file);
		Model model = (Model) wc.getRootElement();
		assertNotNull(model);

		// Add 2 classes to BOM model
		Class[] cls = BOMTestUtils.createClasses(model, 1);
		Class c1 = cls[0];
		TestUtil.delay(2000);
		cls = BOMTestUtils.createClasses(model, 1);
		Class c2 = cls[0];
		TestUtil.delay(2000);

		// Check class names are unique
		assertFalse((c1.getName()).equals(c2.getName()));

		// Delete class c2
		deleteClass(model, c2);
		TestUtil.delay(2000);

		// Create class again
		cls = BOMTestUtils.createClasses(model, 1);
		c2 = cls[0];
		// Check class name is still unique
		assertFalse((c1.getName()).equals(c2.getName()));
		TestUtil.delay(2000);

		// Delete classes
		deleteClass(model, c1);
		deleteClass(model, c2);
		TestUtil.delay(1000);

		// Verify the classes were really deleted from Model
		assertNull(model.getPackagedElement(c1.getName()));
		assertNull(model.getPackagedElement(c2.getName()));
		System.out.println("Verified classes are indeed deleted"); //$NON-NLS-1$

		// Save the editor
		page.saveAllEditors(false);
		TestUtil.delay(2000);

		// // The following can be done in one shot using
		// // IResource delete!!!! Try that in a method variation

		// Delete BOM file from file system
		String fileName = file.getLocation().toString();
		System.out.println("Removing BOM file " + fileName); //$NON-NLS-1$
		boolean removed = removeFile(fileName);
		assertTrue(removed);
		TestUtil.delay(2000);

		// Refresh the project to remove BOM from editor
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		TestUtil.delay(2000);

		// Now check the BOM diagram is gone from project
		resource = project.findMember(bomLocation);
		assertNull(resource);

		// If BOM file was removed, BOM editor should be closed,
		// so check if the editor is still open!
		IEditorReference[] ers = page.getEditorReferences();
		assertEquals("Editors should be closed after removing the file", 0, //$NON-NLS-1$
				ers.length);

		TestUtil.delay(2000);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		TestUtil.delay(2000);
		// Remove project
		TestUtil.removeProject(PROJECT_NAME);
		super.tearDown();
	}

	/**
	 * This method will delete a Class object from a BOM.
	 * 
	 * @param model -
	 *            A Handle to the BOM
	 * @param c -
	 *            The Class being deleted
	 * @throws ExecutionException
	 */
	public void deleteClass(Model model, Class c) throws ExecutionException {
		IOperationHistory stack = PlatformUI.getWorkbench()
				.getOperationSupport().getOperationHistory();
		DestroyElementRequest request = new DestroyElementRequest(
				TransactionUtil.getEditingDomain(c), c, false);
		ICommand cmd = ElementTypeRegistry.getInstance().getElementType(model)
				.getEditCommand(request);
		assertTrue(cmd.canExecute());
		stack.execute(cmd, monitor, null);
	}

	/**
	 * This method will remove the file with given name from file system.
	 * 
	 * @param fileName -
	 *            Name of file to be deleted. FileName should contain the
	 *            absolute path.
	 * @return boolean - Returns true if file was successfully removed. False
	 *         otherwise.
	 */
	public boolean removeFile(String fileName) {
		try {
			File file = new File(fileName);
			if (file.exists()) {
				boolean deleted = file.delete();
				if (deleted == false) {
					System.out.println("Unable to remove file: " + fileName); //$NON-NLS-1$
					return false;
				}
				if (file.exists()) {
					System.out.println("Unable to remove file: " + fileName); //$NON-NLS-1$
					return false;
				}
			} else {
				System.out.println("File Not Found: " + fileName); //$NON-NLS-1$
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception thrown when removing file: " //$NON-NLS-1$
					+ fileName);
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
