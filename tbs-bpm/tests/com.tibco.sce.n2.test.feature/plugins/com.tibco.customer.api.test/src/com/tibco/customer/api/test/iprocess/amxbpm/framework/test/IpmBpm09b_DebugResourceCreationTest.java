/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;

/**
 * This test checks that a Debug Files written in BASIC mode.
 * <p>
 * In Basic mode, the Debug Folders and resources should be created at all basic
 * levels. Test for Root Folder, time-stamp folder, the Basic Level Sub Folders,
 * and files under those basic folders.
 * </p>
 * 
 * 
 * The xpdl used as import contains 2 processes, MainProcess and referenced
 * SubProcess,to test debug files of multiple processes.
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm09b_DebugResourceCreationTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#testIProcessConversion()
     * 
     * @throws Exception
     */
    @Override
    public void testIProcessConversion() throws Exception {
        setDebugMode(DEBUG_MODE.BASIC);
        super.testIProcessConversion();
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm09b_DebugResourceCreationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {

        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", //$NON-NLS-1$
                        "IpmBpm09b_DebugResourceCreationTest/ImportIpmXpdls/7c8_MainProcBRefProcA.ipmxpdl") }; //$NON-NLS-1$

        return importResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // None required
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        List<IFile> convertedFiles = new ArrayList<IFile>(convertedXpdls);

        IFile iFile = convertedFiles.get(0);

        IProject project = iFile.getProject();

        // Debug Mode BASIC
        validateBasicMode(project);
    }

    /**
     * 
     */
    private void validateBasicMode(IProject project) {

        // check existence of Debug Mode Folder, it should not exist
        IFolder rootDebugFolder =
                project.getFolder(DebugResourcesManager.ROOT_DEBUG_FOLDER);

        //        Assert.assertTrue("BASIC: Debug folder Should not be created in NONE mode", //$NON-NLS-1$
        // rootDebugFolder.exists());
        IResource[] rootFolderChildren = null;
        try {
            rootFolderChildren = rootDebugFolder.members();

        } catch (CoreException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull("BASIC: Root Debug folder is Empty", //$NON-NLS-1$
                rootFolderChildren);
        Assert.assertEquals("BASIC: Only one timestamp folder should exist ", //$NON-NLS-1$
                1,
                rootFolderChildren.length);
        IResource timeStampFolder = rootFolderChildren[0];
        Assert.assertTrue("Root folder should only contain Direct Folders", //$NON-NLS-1$
                (timeStampFolder instanceof IFolder));

        IFolder folder = (IFolder) timeStampFolder;

        IResource resource = null;

        if (getConversionType()
                .equals(CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT)) {
            // 1_BS_iProcess_XPDL
            /*
             * 1_BS_iProcess_XPDL should only be created during IPM to BPM
             * conversion and not for IPS to BPM conversion.
             */
            resource =
                    folder.findMember(DebugResourcesManager.STUDIO_IPROCESS_DEBUG_FOLDER);
            Assert.assertTrue("IPM_IPS Debug Folder is Missing", resource.exists()); //$NON-NLS-1$

            resource =
                    ((IFolder) resource)
                            .findMember("7c8_MainProcBRefProcA.xml"); //$NON-NLS-1$
            Assert.assertTrue("IPM_IPS Debug File 7c8_MainProcBRefProcA.xpdl is Missing", //$NON-NLS-1$
                    resource.exists());
        }

        // 2_BS_AMX_BPM_XPDL

        resource =
                folder.findMember(DebugResourcesManager.SEPARATE_PROCESSES_DEBUG_FOLDER);
        Assert.assertTrue("Separate Processes Debug Folder is Missing", //$NON-NLS-1$
                resource.exists());

        resource = ((IFolder) resource).findMember("MPTEST8.xml"); //$NON-NLS-1$
        Assert.assertTrue("Separate Processes Debug File is Missing", //$NON-NLS-1$
                resource.exists());

        // 3_BS_CUST_EXT_XPDL
        resource =
                folder.findMember(DebugResourcesManager.CUSTOM_CONVERSION_DEBUG_FOLDER);
        Assert.assertTrue("Conversion Contribution Debug Folder is Missing", //$NON-NLS-1$
                resource.exists());

        resource = ((IFolder) resource).findMember("MPTEST8.xml"); //$NON-NLS-1$
        Assert.assertTrue("Conversion Contribution Debug File is Missing", //$NON-NLS-1$
                resource.exists());

    }

}
