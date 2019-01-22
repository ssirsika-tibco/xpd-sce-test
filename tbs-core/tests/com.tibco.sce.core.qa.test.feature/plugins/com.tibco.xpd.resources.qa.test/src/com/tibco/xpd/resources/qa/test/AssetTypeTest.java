package com.tibco.xpd.resources.qa.test;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAsset;

/**
 * This test case invokes the APIs belonging ot AssetType class. It focuses on
 * add/get APIs of the AssetType class.
 * 
 * @author sghani
 */
public class AssetTypeTest extends TestCase {

    // Test Constants
    private static final String PROJECT_NAME = "AssetTypeTest"; //$NON-NLS-1$

    private static final String ASSET_ID_1 = "com.tibco.xpd.asset.newasset1"; //$NON-NLS-1$

    private static final String ASSET_ID_2 = "com.tibco.xpd.asset.newasset2"; //$NON-NLS-1$

    // Test related variables
    IProject project;

    ProjectConfig projConfig;

    /**
     * This method tests the following cases: -- adding new asset type -- get
     * asset types that are configured for the project -- get asset types that
     * are registered in the project
     * 
     * @throws Exception
     */
    public void testAssetType() throws Exception {
        setName("AssetTypeTest.testAssetType()"); //$NON-NLS-1$
        // Get default asset types, which should be zero
        EList<AssetType> assets = projConfig.getAssetTypes();
        int len = assets.size();
        assertEquals("Expected no assets", 0, len); //$NON-NLS-1$

        // Add a new asset type
        projConfig.addAssetTask(ASSET_ID_1);
        assets = projConfig.getAssetTypes();
        len = assets.size();
        assertEquals("Expected one asset", 1, len); //$NON-NLS-1$

        // Add a second asset type
        projConfig.addAssetTask(ASSET_ID_2);
        assets = projConfig.getAssetTypes();
        len = assets.size();
        assertEquals("Expected two assets", 2, len); //$NON-NLS-1$

        // Get asset by Id (check a registered asset type)
        IProjectAsset asset =
                projConfig.getAssetById("com.tibco.xpd.asset.wsdl"); //$NON-NLS-1$
        assertNotNull("Expected NON-NULL asset", asset); //$NON-NLS-1$
        assertEquals("Service Descriptors", asset.getName()); //$NON-NLS-1$

        // hasAssetType() is used to check if a configured asset types
        // exists in the project
        boolean has = projConfig.hasAssetType(ASSET_ID_2);
        assertTrue(has);
        // But wsdl asset wasn't configured for project, so should be false
        has = projConfig.hasAssetType("com.tibco.xpd.asset.wsdl"); //$NON-NLS-1$
        assertFalse(has);
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
        TestUtil.waitForBuilds(300);
        assertNotNull("Project should NOT be NULL", project); //$NON-NLS-1$
        // Get ProjectConfig object
        projConfig = XpdResourcesPlugin.getDefault().getProjectConfig(project);
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.waitForBuilds(300);
        // Remove project
        TestUtil.removeProject(PROJECT_NAME);
        super.tearDown();
    }
}
