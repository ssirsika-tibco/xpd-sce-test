/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Junit to test that after IPS to BPM conversion all iProcess destinations are
 * removed and BPM destination is added.
 * 
 * @author kthombar
 * @since 11-Jul-2014
 */
public class IpsBpm05_iProcessToBpmDestinationTest extends
        IpmBpm11_iProcessToBpmDestinationTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#getConversionType()
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
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpsBpm05_iProcessToBpmDestinationTest/Process Packages{processes}/first.xpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpsBpm05_iProcessToBpmDestinationTest/Process Packages{processes}/second.xpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpsBpm05_iProcessToBpmDestinationTest/Process Packages{processes}/third.xpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpsBpm05_iProcessToBpmDestinationTest/Process Packages{processes}/forth.xpdl"), }; //$NON-NLS-1$

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

        return "IpsBpm05_iProcessToBpmDestinationTest"; //$NON-NLS-1$
    }

}
