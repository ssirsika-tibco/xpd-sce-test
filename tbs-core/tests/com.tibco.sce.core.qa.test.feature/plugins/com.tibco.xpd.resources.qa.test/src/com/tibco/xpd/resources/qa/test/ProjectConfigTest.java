package com.tibco.xpd.resources.qa.test;

import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;

import junit.framework.TestCase;

/**
 * This is a test case that checks the basic getter APIs for ProjectConfig. It
 * also checks for expected registered asset types.
 * 
 * @author sghani
 */
@SuppressWarnings("unchecked")
public class ProjectConfigTest extends TestCase {

    // Test Constants
    private static final String PROJECT_NAME = "ProjectConfigTest"; //$NON-NLS-1$

    private static final String ASSET_TYPE_1 = "Business Processes"; //$NON-NLS-1$

    private static final String ASSET_TYPE_2 = "Forms"; //$NON-NLS-1$

    private static final String ASSET_TYPE_3 = "Test Asset"; //$NON-NLS-1$

    private static final String ASSET_TYPE_4 = "Business Object Model"; //$NON-NLS-1$

    private static final String ASSET_TYPE_5 = "Business Assets"; //$NON-NLS-1$

    private static final String ASSET_TYPE_6 = "Service Descriptors"; //$NON-NLS-1$

    private static final String ASSET_TYPE_7 = "TestAsset1"; //$NON-NLS-1$

    private static final String ASSET_TYPE_8 = "TestAsset2"; //$NON-NLS-1$

    // Hashtable containing expected values of registered assetTypes
    private static Hashtable assetTable = new Hashtable();
    // Static constructor to populate hashtable where:
    // Key: asset type name
    // Value: asset type id, wizards
    // So each value contains corresponding asset type's id and wizards length
    static {
        assetTable.put(ASSET_TYPE_1,
                new String[] { "com.tibco.xpd.asset.businessProcess", "3" }); //$NON-NLS-1$ //$NON-NLS-2$
        assetTable.put(ASSET_TYPE_2,
                new String[] { "com.tibco.xpd.asset.form", //$NON-NLS-1$
                        "0" }); //$NON-NLS-1$
        assetTable.put(ASSET_TYPE_3,
                new String[] { "com.tibco.xpd.test.assettype", "0" }); //$NON-NLS-1$ //$NON-NLS-2$
        assetTable.put(ASSET_TYPE_4,
                new String[] { "com.tibco.xpd.asset.bom", //$NON-NLS-1$
                        "1" }); //$NON-NLS-1$
        assetTable.put(ASSET_TYPE_5,
                new String[] { "com.tibco.xpd.asset.businessAssets", "2" }); //$NON-NLS-1$ //$NON-NLS-2$
        assetTable.put(ASSET_TYPE_6,
                new String[] { "com.tibco.xpd.asset.wsdl", //$NON-NLS-1$
                        "0" }); //$NON-NLS-1$
        assetTable.put(ASSET_TYPE_7,
                new String[] { "com.tibco.xpd.asset.newasset1", "0" }); //$NON-NLS-1$ //$NON-NLS-2$
        assetTable.put(ASSET_TYPE_8,
                new String[] { "com.tibco.xpd.asset.newasset2", "0" }); //$NON-NLS-1$ //$NON-NLS-2$
    }

    // Test related variables
    IProject project;

    ProjectConfig projConfig;

    // TODO: add comment
    public void testGetRegisteredAssetTypes() throws Exception {
        setName("ProjectConfigTest.testGetRegisteredAssetTypes"); //$NON-NLS-1$
        // Get all registered assetTypes and verify items
        EList<IProjectAsset> assetTypes = projConfig.getRegisteredAssetTypes();
        // We can't test that a particular number of registered
        // assets are returned because that will vary with
        // the components and features that are test is run with.

        // Only a subset of the registered assets are being tested
        // along with 2 assets created for this test

        // Iterate the list of registered assetTypes and ensure
        // the following expected asset attributes match:
        // Name, Id, Length of Wizards
        String name;
        String id;
        int len;
        String[] value;
        Iterator ite = assetTypes.iterator();
        while (ite.hasNext()) {
            ProjectAssetElement asset = (ProjectAssetElement) ite.next();
            name = asset.getName();
            // Only compare the elements if we find it because it could
            // not exist.
            if (assetTable.containsKey(name)) {
                System.out.println("Verifying asset type: " + name); //$NON-NLS-1$
                // Get the expected Id and Wizards Length
                value = (String[]) assetTable.get(name);
                id = value[0];
                len = Integer.parseInt(value[1]);
                assertEquals("Asset id doesn't match", id, asset.getId()); //$NON-NLS-1$
                assertEquals("Asset Wizards (" + name + ") lenth doesn't match", //$NON-NLS-1$ //$NON-NLS-2$
                        len,
                        asset.getWizardPages().length);
            }
        } // end while

        // SCF features don't register any assets, so only if tested with bpm
        // features then this part should be executed.
        if (!projConfig.getAssetTypes().isEmpty()) {
            // Get a particular registered asset type using getAssetByID()
            IProjectAsset asset =
                    projConfig.getAssetById("com.tibco.xpd.asset.bom"); //$NON-NLS-1$
            assertNotNull("Expected non-NULL asset type", asset); //$NON-NLS-1$
            assertEquals("Retrieved asset type name doesn't match", //$NON-NLS-1$
                    "Business Object Model", //$NON-NLS-1$
                    asset.getName());
        }
        // The hasAssetType() only works on asset types that are
        // configured for the project. It doesn't check for registered asset
        // types.
        // Hence it is expected to return false.
        boolean has = projConfig.hasAssetType("com.tibco.xpd.asset.newasset1"); //$NON-NLS-1$
        assertFalse(has);
    }

    /**
     * This method simply tests the getProjecType() API, which is NULL by
     * default.
     * 
     * @throws Exception
     */
    public void testGetProjectType() throws Exception {
        setName("ProjectConfigTest.testGetProjectType"); //$NON-NLS-1$
        // Get the project type and check default type
        String type = projConfig.getProjectType();
        assertNull("Expected projectType to be null, but it is " + type, type); //$NON-NLS-1$
    }

    /**
     * Setups up the following: -- reset workspace perspective -- create a
     * project with no asset type or special folders -- retrieve the config of
     * the project created
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Creates a project with no asset type/special folder
        System.out.println("Create a new project ..."); //$NON-NLS-1$
        project = TestUtil.createProject(PROJECT_NAME);
        try {
            TestUtil.waitForBuilds(300);
            assertNotNull("Project should NOT be NULL", project); //$NON-NLS-1$
            assertTrue("Project is NOT accessible", project.isAccessible()); //$NON-NLS-1$
            assertEquals("Expected project name " + PROJECT_NAME //$NON-NLS-1$
                    + " but it is " + project.getName(), //$NON-NLS-1$
                    PROJECT_NAME,
                    project.getName());
            // Get ProjectConfig object
            projConfig =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
        } finally {
            // If project was created then remove it
            if (project != null) {
                tearDown();
            }
        }
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
