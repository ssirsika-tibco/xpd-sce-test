/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Test to check if the fix in XPD-6558 works fine. Checks if all the user tasks
 * in the process have work allocation strategy set.
 * 
 * @author kthombar
 * @since 17-Jul-2014
 */
public class IpsBpm17_UserTaskWorkAllocationTest extends
        IpmBpm27_UserTaskWorkAllocationTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {

        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpsBpm17_UserTaskWorkAllocationTest/Process Packages{processes}/event1.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/ConversionTests", "IpsBpm17_UserTaskWorkAllocationTest/Process Packages{processes}/startevent.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpsBpm17_UserTaskWorkAllocationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }
}
