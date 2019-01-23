/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Tests Import/Conversion, to save a file with its default name when another
 * file with same name exists inside another folder or project but not in the
 * target import.
 * 
 * 
 * @author aprasad
 * @since 12-May-2014
 */
public class IpmBpm07b_SaveDefaultFileNameTest extends
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
                        "resources/FrameworkTests", "IpmBpm07b_SaveDefaultFileNameTest/ImportIpmXpdls/5c_ProcessA.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
                "IpmBpm07b_SaveDefaultFileNameTest/BpmXpdls{processes}/PROCA.xpdl"), }; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm07b_SaveDefaultFileNameTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {

        List<IFile> file = new ArrayList<IFile>(convertedXpdls);

        IFile existingFile = file.get(0);

        if (!existingFile.isAccessible() || !existingFile.exists()) {
            fail("File 'PROCA.xpdl' not found"); //$NON-NLS-1$
        }

        if (!"PROCA.xpdl".equals(existingFile.getName())) { //$NON-NLS-1$
            fail("File 'PROCA.xpdl' not found"); //$NON-NLS-1$
        }
    }
}