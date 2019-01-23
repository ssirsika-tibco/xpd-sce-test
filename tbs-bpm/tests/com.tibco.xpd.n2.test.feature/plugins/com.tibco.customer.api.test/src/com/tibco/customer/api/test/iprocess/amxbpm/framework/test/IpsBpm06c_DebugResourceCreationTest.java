/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * This test checks that a Debug Files written in ENHANCED mode.
 * <p>
 * In Enhanced Mode, check for new time-stamp folder and all the basic folders
 * and files under that and additionally the Folder for Contribution specific
 * folders and files under those folders.
 * </p>
 * 
 * 
 * The xpdl used as import contains 2 processes, MainProcess and referenced
 * SubProcess,to test debug files of multiple processes.
 * 
 * @author kthombar
 * @since 15-Jul-2014
 */
public class IpsBpm06c_DebugResourceCreationTest extends
        IpmBpm09c_DebugResourceCreationTest {

    public IpsBpm06c_DebugResourceCreationTest() {
        super();
    }

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
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpsBpm06c_DebugResourceCreationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractValidationTest#getImportResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getImportResourcesInfo() {

        TestResourceInfo[] importResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/FrameworkTests", //$NON-NLS-1$
                        "IpsBpm06c_DebugResourceCreationTest/Process Packages{processes}/7c8_MainProcBRefProcA.xpdl") }; //$NON-NLS-1$

        return importResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIpmBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {
        // None required
        return null;
    }

}
