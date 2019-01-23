/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Test to check if the fix in XPD-6627 works fine. Check if there is any Script
 * assignment of Date or Time fields then they are done by Value and not by
 * Reference.
 * 
 * 
 * @author kthombar
 * @since 04-Aug-2014
 */
public class IpmBpm30_DateTimeScriptConversionTest extends
        AbstractIProcessConversionScriptComparisonTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getGoldXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getGoldXpdlResourceInfo() {

        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm30_DateTimeScriptConversionTest/Process Packages{processes}/DTIME_GOLD.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getImportIProcessXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getImportIProcessXpdlResourceInfo() {

        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm30_DateTimeScriptConversionTest/ImportIpmXpdls/dtime.ipmxpdl");//$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getConvertedTestXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getConvertedTestXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm30_DateTimeScriptConversionTest/Process Packages{processes}/DTIME.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {

        return "IpmBpm30_DateTimeScriptConversionTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getConversionType()
     * 
     * @return
     */
    @Override
    protected CONVERSION_TYPE getConversionType() {

        return CONVERSION_TYPE.IPM_TO_BPM_IMPORT_AND_CONVERT;
    }

}
