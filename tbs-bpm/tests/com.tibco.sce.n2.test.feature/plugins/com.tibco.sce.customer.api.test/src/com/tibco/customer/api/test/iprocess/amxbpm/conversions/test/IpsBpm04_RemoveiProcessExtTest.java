/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Tests that checks if all the iProcessExt: tags are removed after the
 * conversion from IPS XPDL to BPM XPDL.
 * 
 * @author kthombar
 * @since 11-Jul-2014
 */
public class IpsBpm04_RemoveiProcessExtTest extends
        IpmBpm10_RemoveiProcessExtTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.conversions.test.IpmBpm10_RemoveiProcessExtTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpsBpm04_RemoveiProcessExtTest/Process Packages{processes}/mstmain.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpsBpm04_RemoveiProcessExtTest/Process Packages{processes}/sidproc.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm04_RemoveiProcessExtTest"; //$NON-NLS-1$
    }

}
