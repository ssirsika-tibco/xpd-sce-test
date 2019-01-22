/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.process.model.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Test to check the conversion of Event step object to Receive task using the
 * iProcess to BPM process conversion framework.
 * 
 * @author sajain
 * @since Apr 29, 2014
 */
public class IpmBpm05_EventStepConversionTest extends
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
                        "resources/ProcessModelTests", "IpmBpm05_EventStepConversionTest/ImportIpmXpdls/eventstp.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
                        "resources/ProcessModelTests", "IpmBpm05_EventStepConversionTest/GoldBPMXpdls/EVENTSTP.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        return "IpmBpm05_EventStepConversionTest"; //$NON-NLS-1$
    }
}
