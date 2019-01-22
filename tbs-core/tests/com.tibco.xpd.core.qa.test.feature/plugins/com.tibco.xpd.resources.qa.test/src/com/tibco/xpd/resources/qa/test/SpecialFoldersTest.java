package com.tibco.xpd.resources.qa.test;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;

/**
 * This test case checks the APIs of SpecialFolders class. It checks the
 * registered specials folders for a project and adds/gets newly created special
 * folders.
 * 
 * @author sghani
 * 
 */
@SuppressWarnings("unchecked")
public class SpecialFoldersTest extends TestCase {

    // Test Constants
    private static final String PROJECT_NAME = "SpecialFoldersTest"; //$NON-NLS-1$

    private static final int SPECIAL_FOLDERS = 0;

    private static final String FOLDER_NAME_1 = "BOM Folder"; //$NON-NLS-1$

    private static final String FOLDER_KIND_1 = "bom"; //$NON-NLS-1$

    private static final String FOLDER_NAME_2 = "Process Packages Folder"; //$NON-NLS-1$

    private static final String FOLDER_KIND_2 = "processes"; //$NON-NLS-1$

    private static final String FOLDER_NAME_3 = "TestSpecialFolder1"; //$NON-NLS-1$

    private static final String FOLDER_KIND_3 = "qafolder1"; //$NON-NLS-1$

    private static final String FOLDER_NAME_4 = "TestSpecialFolder2"; //$NON-NLS-1$

    private static final String FOLDER_KIND_4 = "qafolder2"; //$NON-NLS-1$

    private static final String FOLDER_1 = "ValidFolder1"; //$NON-NLS-1$

    private static final String FOLDER_2 = "ValidFolder2"; //$NON-NLS-1$

    private static final String FOLDER_3 = "ChangeFolder2"; //$NON-NLS-1$

    // Hashtable to contain expected values of registered assetTypes
    private static Hashtable folderTable = new Hashtable();
    // Static constructor to populate hashtable with:
    // Key: Folder Kind
    // Value: {Folder Name, AssetTypeId}
    // So the value is an array containing corresponding folder's
    // kind and assetId
    static {
        folderTable.put(FOLDER_NAME_1, new String[] { FOLDER_KIND_1,
                "com.tibco.xpd.asset.bom" }); //$NON-NLS-1$
        folderTable.put(FOLDER_NAME_2, new String[] { FOLDER_KIND_2,
                "com.tibco.xpd.asset.businessProcess" }); //$NON-NLS-1$
        folderTable.put(FOLDER_NAME_3, new String[] { FOLDER_KIND_3,
                "com.tibco.xpd.asset.newasset1" }); //$NON-NLS-1$
        folderTable.put(FOLDER_NAME_4, new String[] { FOLDER_KIND_4,
                "com.tibco.xpd.asset.newasset2" }); //$NON-NLS-1$
    }

    // Test related variables
    IProject project;

    ProjectConfig projConfig;

    SpecialFolders folders;

    /**
     * This method tests the registered special folders of a project. It
     * compares each special folder element with a constant set.
     * 
     * @throws Exception
     */
    public void testGetRegisteredSpecialFolders() throws Exception {
        setName("SpecialFoldersTest.testGetRegisteredSpecialFolders"); //$NON-NLS-1$
        // Get the registered special folders
        EList<ISpecialFolderModel> reg = folders.getFolderKindInfo();
        // We can't test that a particular number of registered
        // folders are returned because that will vary with
        // the components and features that are test is run with.

        // Only a subset of the registered folders are being tested
        // along with 2 special folders created for this test
        String name;
        String[] value;
        String kind;
        String assetId;

        // Iterate through list of registered special folders and
        // validate each contains expected value
        Iterator ite = reg.iterator();
        while (ite.hasNext()) {
            ISpecialFolderModel folder = (ISpecialFolderModel) ite.next();
            name = folder.getName();
            // We only test a subset of the folders that we know should
            // be included given this test project
            if (folderTable.containsKey(name)) {
                System.out.println("Verifying folder: " + name); //$NON-NLS-1$
                value = (String[]) folderTable.get(name);
                kind = value[0];
                assetId = value[1];
                assertEquals("SpecialFolder kind doesn't match", //$NON-NLS-1$
                        kind,
                        folder.getKind());
                assertEquals("SpecialFolder project asset id doesn't match", //$NON-NLS-1$
                        assetId,
                        folder.getProjectAssetId());
            }
        }// end while

        // Also check a particular registered folder can be retrieved
        ISpecialFolderModel regFolder =
                folders.getFolderKindInfo(FOLDER_KIND_3);
        assertEquals("Registered special folder name doesn't match", //$NON-NLS-1$
                FOLDER_NAME_3,
                regFolder.getName());
    }

    /**
     * This method tests the add/change/remove/get APIs of SpecialFolders class.
     * The following steps are executed: -- add two special folders to project
     * -- get configured special folders of project -- change folder of a
     * special folder -- remove the newly added special folders
     * 
     * @throws Exception
     */
    public void testSpecialFolders() throws Exception {
        setName("SpecialFoldersTest.testSpecialFolders"); //$NON-NLS-1$
        // Get default special folders, it should be null
        // since project was created with no folders
        EList<SpecialFolder> confFolders = folders.getFolders();
        // Check no special folders exist
        assertEquals("Expected no special folders", //$NON-NLS-1$
                SPECIAL_FOLDERS,
                confFolders.size());

        // Create two new folders and add them as special folders
        // of kind that was defined for our test. We need to create
        // the folders first.
        IFolder f1 = project.getFolder(FOLDER_1);
        IFolder f2 = project.getFolder(FOLDER_2);
        try {
            f1.create(true, true, null);
            f2.create(true, true, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        // Add the folders as project's special folder
        folders.addFolder(f1, FOLDER_KIND_3);
        folders.addFolder(f2, FOLDER_KIND_4);

        // Check folders size by getting all configured folders
        confFolders = folders.getFolders();
        assertEquals("Expected two special folders", 2, confFolders.size()); //$NON-NLS-1$
        // Get folders of special Kind
        confFolders = folders.getFoldersOfKind(FOLDER_KIND_3);
        assertEquals("Expected one special folder of kind " + FOLDER_KIND_3, //$NON-NLS-1$
                1,
                confFolders.size());
        confFolders = folders.getFoldersOfKind(FOLDER_KIND_4);
        assertEquals("Expected one special folder of kind " + FOLDER_KIND_4, //$NON-NLS-1$
                1,
                confFolders.size());

        // Get each newly created folder
        SpecialFolder folder1 = folders.getFolder(f1);
        assertEquals("Folder kind doesn't match", //$NON-NLS-1$
                FOLDER_KIND_3,
                folder1.getKind());
        SpecialFolder folder2 = folders.getFolder(f2, FOLDER_KIND_4);
        assertEquals("Folder kind doesn't match", //$NON-NLS-1$
                FOLDER_KIND_4,
                folder2.getKind());

        // Change folder of special folder
        // Create the folder first
        IFolder targetFolder = project.getFolder(FOLDER_3);
        try {
            targetFolder.create(true, true, null);
        } catch (CoreException e) {
            throw new RuntimeException(e);
        }
        // Now change the special folder's folder
        folders.changeFolder(folder2, targetFolder);
        assertEquals("Special folder's location not changed", //$NON-NLS-1$
                FOLDER_3,
                folder2.getLocation());
        // Check folders size by getting all configured folders again
        confFolders = folders.getFolders();
        assertEquals("Expected two special folders", 2, confFolders.size()); //$NON-NLS-1$

        // Remove special folders that were added
        folders.removeFolder(folder1);
        assertEquals("Expected one special folder", 1, folders.getFolders() //$NON-NLS-1$
                .size());
        folders.removeFolder(folder2);
        assertEquals("Expected no special folder", 0, folders.getFolders() //$NON-NLS-1$
                .size());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        IWorkbenchPage page =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage();
        page.resetPerspective();
        TestUtil.waitForJobs();
        // Creates a project with no asset type
        System.out.println("Create a new project ..."); //$NON-NLS-1$
        TestUtil.createProject(PROJECT_NAME);
        TestUtil.waitForBuilds(300);
        project =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getProject(PROJECT_NAME);
        assertNotNull("Project should NOT be NULL", project); //$NON-NLS-1$
        assertTrue("Project is NOT accessible", project.isAccessible()); //$NON-NLS-1$

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
        TestUtil.waitForBuilds(300);
        // Project shouldn't be accessible anymore
        assertFalse("Project is still accessible", project.isAccessible()); //$NON-NLS-1$
        super.tearDown();
    }
}
