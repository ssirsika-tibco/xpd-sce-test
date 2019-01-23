/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * JUnit to protect public start step contribution.
 * 
 * @author sajain
 * @since Jul 16, 2014
 */
public class IpsBpm16_PublicStepsAndEventsTest extends
        IpmBpm13_PublicStepsAndEventsConversionTest {

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
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpsBpm16_PublicStepsAndEventsTest/Process Packages{processes}/testIPSProj01.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpsBpm16_PublicStepsAndEventsTest"; //$NON-NLS-1$
    }
}
