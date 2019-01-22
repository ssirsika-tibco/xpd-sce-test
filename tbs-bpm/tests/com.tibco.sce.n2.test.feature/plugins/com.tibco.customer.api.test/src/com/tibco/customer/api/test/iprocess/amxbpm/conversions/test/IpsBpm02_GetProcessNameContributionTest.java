/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Junit to check if the fix in XPD-6522 works fine. Checks if all the
 * IPEProcessNameUtil.GETPROCESSNAME("sting") methods are replaced by the
 * Literal String present in the method.
 * 
 * @author kthombar
 * @since 01-Jul-2014
 */
public class IpsBpm02_GetProcessNameContributionTest extends
        AbstractIProcessConversionScriptComparisonTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getGoldXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getGoldXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpsBpm02_GetProcessNameContributionTest/Process Packages{processes}/callIntf_GOLD.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getImportIProcessXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getImportIProcessXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpsBpm02_GetProcessNameContributionTest/Process Packages{processes}/IntfPack.xpdl");//$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getConvertedTestXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getConvertedTestXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpsBpm02_GetProcessNameContributionTest/Process Packages{processes}/callIntf.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm02_GetProcessNameContributionTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {
        return CONVERSION_TYPE.IPS_TO_BPM_CONVERT;
    }

}
