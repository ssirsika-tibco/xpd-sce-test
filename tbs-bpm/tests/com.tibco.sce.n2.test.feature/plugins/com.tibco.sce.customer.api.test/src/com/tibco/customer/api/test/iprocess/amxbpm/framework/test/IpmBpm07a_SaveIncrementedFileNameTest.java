/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Tests Import/Conversion, to save a filename with increment when another file
 * with same name exists in the target folder. This test checks if he timestamp
 * of the converted file is greater than the timestamp of the existing file.
 * 
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm07a_SaveIncrementedFileNameTest extends
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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", "IpmBpm07a_SaveIncrementedFileNameTest/ImportIpmXpdls/5c_ProcessA.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        return new TestResourceInfo[] { new TestResourceInfo(
                "resources/FrameworkTests", //$NON-NLS-1$
                "IpmBpm07a_SaveIncrementedFileNameTest/Process Packages{processes}/PROCA.xpdl"), }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm07a_SaveIncrementedFileNameTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        List<IFile> file = new ArrayList<IFile>(convertedXpdls);

        IFile iFile = file.get(0);

        IContainer parent = iFile.getParent();

        Folder folder = (Folder) parent;

        IFile existingFile = folder.getFile("PROCA.xpdl"); //$NON-NLS-1$

        if (!existingFile.isAccessible() || !existingFile.exists()) {
            fail("File 'PROCA.xpdl' not found"); //$NON-NLS-1$
        }

        IFile convertedFile = folder.getFile("PROCA_1.xpdl"); //$NON-NLS-1$

        if (!convertedFile.isAccessible() || !convertedFile.exists()) {
            fail("File 'PROCA_1.xpdl' not found"); //$NON-NLS-1$
        }

        if (convertedFile.getLocalTimeStamp() < existingFile
                .getLocalTimeStamp()) {
            fail("The timestamp of converted file should be greater than the existing file"); //$NON-NLS-1$
        }

    }
}