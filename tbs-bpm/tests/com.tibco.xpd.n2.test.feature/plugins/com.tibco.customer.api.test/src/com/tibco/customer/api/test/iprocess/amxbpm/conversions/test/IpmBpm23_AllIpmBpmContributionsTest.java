/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * JUnit test to verify that all conversion contributions work fine during the
 * IPM to BPM conversion.
 * 
 * @author sajain
 * @since Jun 23, 2014
 */
public class IpmBpm23_AllIpmBpmContributionsTest extends
        AbstractIProcessConversionComparisonTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ConversionTests", "IpmBpm23_AllIpmBpmContributionsTest/ImportIpmXpdls/contr.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
                        "resources/ConversionTests", "IpmBpm23_AllIpmBpmContributionsTest/GoldBPMXpdls/CONTR.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
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
        // Nothing needed here
        return null;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm23_AllIpmBpmContributionsTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {
        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }
}
