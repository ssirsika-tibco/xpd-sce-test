/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * 
 * Test to check if post conversion all the Standalone Scripts get inlined in
 * normal expression scripts.
 * 
 * @author kthombar
 * @since 06-Jun-2014
 */
public class IpmBpm17_StandaloneScriptContributionTest extends
        AbstractIProcessConversionScriptComparisonTest {

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpm17_StandaloneScriptContributionTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getGoldXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getGoldXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm17_StandaloneScriptContributionTest/Process Packages{processes}/STANDALO_GOLD.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getImportIProcessXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getImportIProcessXpdlResourceInfo() {
        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm17_StandaloneScriptContributionTest/ImportIpmXpdls/standalo.ipmxpdl"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionScriptComparisonTest#getConvertedTestXpdlResourceInfo()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo getConvertedTestXpdlResourceInfo() {

        return new TestResourceInfo(
                "resources/ConversionTests", "IpmBpm17_StandaloneScriptContributionTest/Process Packages{processes}/STANDALO.xpdl"); //$NON-NLS-1$ //$NON-NLS-2$
    }

}
