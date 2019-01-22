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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager;
import com.tibco.xpd.iprocess.amxbpm.converter.external.DebugResourcesManager.DEBUG_MODE;

/**
 * This test checks that a Debug Files written in NONE mode.
 * <p>
 * In Debug mode NONE, no folders/files should be created, test if the root
 * debug folder exists.
 * </p>
 * 
 * 
 * The xpdl used as import contains 2 processes, MainProcess and referenced
 * SubProcess,to test debug files of multiple processes.
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm09a_DebugResourceCreationTest extends
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

    public IpmBpm09a_DebugResourceCreationTest() {
        setDebugMode(DEBUG_MODE.NONE);
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm09a_DebugResourceCreationTest"; //$NON-NLS-1$
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
                        "IpmBpm09a_DebugResourceCreationTest/ImportIpmXpdls/7c8_MainProcBRefProcA.ipmxpdl") }; //$NON-NLS-1$

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

        // Check if root Debug folder exist, remove if it does
        List<IFile> convertedFiles = new ArrayList<IFile>(convertedXpdls);

        IFile iFile = convertedFiles.get(0);

        IProject project = iFile.getProject();

        IFolder rootDebugFolder =
                project.getFolder(DebugResourcesManager.ROOT_DEBUG_FOLDER);

        removeResource(rootDebugFolder);

        // Debug Mode NONE
        validateNoneMode(project);
    }

    /**
     * 
     */
    private void validateNoneMode(IProject project) {

        // check existence of Debug Mode Folder, it should not exist
        IFolder rootDebugFolder =
                project.getFolder(DebugResourcesManager.ROOT_DEBUG_FOLDER);
        Assert.assertFalse("Debug folder Should not be created in NONE mode", //$NON-NLS-1$
                rootDebugFolder.exists());
    }

    /**
     * @param rootResource
     */
    protected void removeResource(IResource rootResource) {
        // delete if the root debug folder exists
        if (rootResource.exists()) {
            try {
                rootResource.delete(true, new NullProgressMonitor());
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

}
