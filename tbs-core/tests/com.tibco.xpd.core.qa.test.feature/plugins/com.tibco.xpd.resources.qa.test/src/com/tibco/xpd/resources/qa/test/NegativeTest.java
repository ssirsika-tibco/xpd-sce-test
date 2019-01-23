package com.tibco.xpd.resources.qa.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

/**
 * Test case that simulates negative scenarios such as adding the same element
 * twice, removing an element that doesn't exist or was already removed etc. It
 * uses APIs from ProjectConfig and SpecialFolders class.
 * 
 * @author sghani
 * 
 */
@SuppressWarnings("unchecked")
public class NegativeTest extends TestCase {

    // Test Constants
    private static final String PROJECT_NAME = "NegativeTest"; //$NON-NLS-1$

    private static final String ASSET_ID_1 = "com.tibco.xpd.asset.newasset1"; //$NON-NLS-1$

    private static final String ASSET_ID_INVALID =
            "com.tibco.xpd.asset.invalidasset"; //$NON-NLS-1$

    private static final String FOLDER_1 = "TestSpecialFolder1"; //$NON-NLS-1$

    private static final String FOLDER_INVALID = "InvalidSpecialFolder"; //$NON-NLS-1$

    private static final String FOLDER_KIND_INVALID = "invalidkind"; //$NON-NLS-1$

    // Test related variables
    IProject project;

    ProjectConfig projConfig;

    SpecialFolders folders;

    /**
     * This tests adding the same AssetType twice to a ProjectConfig. The
     * expected behavior is that the second element should not be added and the
     * total size of asset types should NOT increase.
     * 
     * NOTE: Fixed MR# 35633 in Studio 3.0.1
     */
    public void testAddAssetTypeTwice() {
        setName("NegativeTest.testAddAssetTypeTwice()"); //$NON-NLS-1$
        // Add same asset type twice
        projConfig.addAssetTask(ASSET_ID_1);
        System.out.println("old assets size: " //$NON-NLS-1$
                + projConfig.getAssetTypes().size());
        assertEquals("Expected one asset", 1, projConfig.getAssetTypes().size()); //$NON-NLS-1$
        projConfig.addAssetTask(ASSET_ID_1);
        System.out.println("new assets size: " //$NON-NLS-1$
                + projConfig.getAssetTypes().size());
        assertEquals("Expected one asset", 1, projConfig.getAssetTypes().size()); //$NON-NLS-1$
    }

    /**
     * This tests adding the same special folder twice to a project's configured
     * special folders element. The expected behavior is that it should be
     * allowed to add the same element twice.
     */
    public void testAddSpecialFolderTwice() {
        setName("NegativeTest.testAddSpecialFolderTwice()"); //$NON-NLS-1$
        // Create a folder
        IFolder folder = project.getFolder(FOLDER_1);
        try {
            folder.create(true, true, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        // Add the folder as a special folder to project
        folders.addFolder(folder, "testSingleton"); //$NON-NLS-1$
        assertEquals("Expected one special folder", 1, folders.getFolders() //$NON-NLS-1$
                .size());
        // Add the same special folder again, which should fail
        try {
            folders.addFolder(folder, "testSingleton"); //$NON-NLS-1$
        } catch (IllegalArgumentException e) {
            // e.printStackTrace();
            System.out.println("Expected IllegalArgumentException caught!"); //$NON-NLS-1$
            assertEquals("Expected one special folder", 1, folders.getFolders() //$NON-NLS-1$
                    .size());
            return;
        }
        // Code should only come this far if expected exception wasn't caught!
        fail("Expected IllegalArgumentException not thrown"); //$NON-NLS-1$
    }

    /**
     * This tests adding a special folder of invalid kind (not registered with
     * project) to a project. It is expected to throw an IllegalArgument
     * exception.
     * 
     * NOTE: Fixed MR 35640 in Studio 3.0.1
     */
    public void testAddInvalidFolderKind() {
        setName("NegativeTest.testAddInvalidFolderKind()"); //$NON-NLS-1$
        // Create a new folder and add it as special folders
        // of kind that is not defined, hence invalid. We need to create
        // the folders first.
        IFolder f1 = project.getFolder(FOLDER_INVALID);
        try {
            f1.create(true, true, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        // Add the folder as project's special folder of invalid kind,
        // which should raise some warning/exception
        try {
            folders.addFolder(f1, FOLDER_KIND_INVALID);
        } catch (IllegalArgumentException e) {
            System.out.println("Expected IllegalArgumentException caught!"); //$NON-NLS-1$
            assertEquals("Expected no special folders to be added!", folders //$NON-NLS-1$
                    .getFolders().size(), 0);
            return;
        } catch (Exception e) {
            System.out
                    .println("Caught unexpected exception: " + e.getMessage()); //$NON-NLS-1$
            e.printStackTrace();
            return;
        }
        // Code should only come this far if expected exception wasn't caught!
        fail("Expected IllegalArgumentException not thrown!"); //$NON-NLS-1$
    }

    /**
     * This tests adding an invalid asset type (not registered with project) to
     * the project. This should throw an exception or not add the invalid asset
     * id to project.
     * 
     * NOTE: Fixed MR 35634 in Studio 3.0.1.
     */
    public void testInvalidAssetType() {
        setName("NegativeTest.testInvalidAssetType()"); //$NON-NLS-1$
        // Get default asset types, which should be zero
        EList<AssetType> assets = projConfig.getAssetTypes();
        int len = assets.size();
        assertEquals("Expected no assets", 0, len); //$NON-NLS-1$
        // Add a new asset type that is not registered hence
        // it is invalid and should not be allowed.
        try {
            projConfig.addAssetTask(ASSET_ID_INVALID);
            assets = projConfig.getAssetTypes();
        } catch (IllegalArgumentException e) {
            System.out.println("Expected IllegalArgumentException caught!"); //$NON-NLS-1$
            assets = projConfig.getAssetTypes();
            len = assets.size();
            assertEquals("Expected no asset", 0, len); //$NON-NLS-1$
            return;
        }
        // Code should only come this far if expected exception wasn't caught!
        fail("Expected ???ArgumentException notthrown"); //$NON-NLS-1$
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Resets the perspective
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        page.resetPerspective();
        TestUtil.waitForJobs();

        // Creates a project with no asset type
        System.out.println("Create a new project ..."); //$NON-NLS-1$
        project = TestUtil.createProject(PROJECT_NAME);
        assertNotNull("Project should NOT be NULL", project); //$NON-NLS-1$
        assertTrue("Project is NOT accessible", project.isAccessible()); //$NON-NLS-1$

        TestUtil.waitForBuilds(300);

        // Get ProjectConfig object
        projConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);
        // Get the specials folders element of project config
        folders = projConfig.getSpecialFolders();
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300);
        // Remove project
        TestUtil.removeProject(PROJECT_NAME);
        TestUtil.waitForJobs();
        // Project shouldn't be accessible anymore
        assertFalse("Project is still accessible", project.isAccessible()); //$NON-NLS-1$
        super.tearDown();
    }

}
