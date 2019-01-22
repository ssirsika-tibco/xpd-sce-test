/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import java.util.Collection;

import org.eclipse.core.resources.IFile;

import com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest;
import com.tibco.xpd.core.test.util.TestResourceInfo;

/**
 * Tests the import of the IPM Xpdl resources containing Web Service Steps.The
 * Import should be successful.
 * 
 * @author aprasad
 * @since 25-Jul-2014
 */
public class IpmBpmImportWebServiceStepsTest extends
        AbstractIProcessConversionTest {

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractIProcessConversionTest#doTestConvertedXpdls(java.util.Collection)
     * 
     * @param convertedXpdls
     */
    @Override
    protected void doTestConvertedXpdls(Collection<IFile> convertedXpdls) {
        // Nothing to be tested in the converted process, just successful import
        // is enough
    }

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
                                "resources/ConversionTests", "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/bankxml.ipmxpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/iPWImport.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/banksoap.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/eaijarry.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/eaijdel.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/eaijimm.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/inbound.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/9EDTB.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/9resprob.ipmxpdl"), //$NON-NLS-1$
                        new TestResourceInfo("resources/ConversionTests", //$NON-NLS-1$
                                "IpmBpmImportTestForWebServiceSteps/ImportIpmXpdls/BWLiveAll_iProcess.ipmxpdl"), //$NON-NLS-1$
                };

        return testResources;
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getOtherResourcesInfo()
     * 
     * @return
     */
    @Override
    public TestResourceInfo[] getOtherResourcesInfo() {

        return new TestResourceInfo[0];
    }

    /**
     * @see com.tibco.customer.api.test.iprocess.amxbpm.AbstractBaseIProcessToBpmTest#getMainImportProjectName()
     * 
     * @return
     */
    @Override
    protected String getMainImportProjectName() {
        return "IpmBpmImportTestForWebServiceSteps"; //$NON-NLS-1$
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
