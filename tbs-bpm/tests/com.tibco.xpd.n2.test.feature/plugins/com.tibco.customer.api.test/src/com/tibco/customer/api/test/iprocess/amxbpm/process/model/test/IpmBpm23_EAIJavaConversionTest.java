/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.process.model.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Test to check the conversion of EAI Java Step object to Service task using
 * the iProcess to BPM process conversion framework.
 * 
 * @author aprasad
 * @since July 15, 2014
 */
public class IpmBpm23_EAIJavaConversionTest extends
        AbstractIProcessConversionComparisonTest {

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ProcessModelTests", "IpmBpm23_EAIJavaConversionTest/ImportIpmXpdls/eaijava.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getGoldResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getGoldResourcesInfo() {

        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ProcessModelTests", "IpmBpm23_EAIJavaConversionTest/GoldBPMXpdls/EAIJAVA.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm23_EAIJavaConversionTest"; //$NON-NLS-1$
    }
}
