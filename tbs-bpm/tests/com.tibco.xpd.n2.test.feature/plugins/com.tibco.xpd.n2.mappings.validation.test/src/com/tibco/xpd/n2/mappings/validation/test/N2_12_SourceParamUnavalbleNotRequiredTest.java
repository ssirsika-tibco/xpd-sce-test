/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * SourceParamUnavalbleNotRequiredTest
 * <p>
 * SourceParamUnavalbleNotRequiredTest - Test selected validations are correctly
 * raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * Service tasks in this process shouldnt show the marker that the source
 * parameter is not available.
 * 
 * @author
 * @since
 */
public class N2_12_SourceParamUnavalbleNotRequiredTest extends
        AbstractN2BaseValidationTest {

    public N2_12_SourceParamUnavalbleNotRequiredTest() {
        super(false);
    }

    /**
     * SourceParamUnavalbleNotRequiredTest
     * 
     * @throws Exception
     */
    public void testSourceParamUnavalbleNotRequiredTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/UC1CdRPCLiteralWSInvokeJavaScript/Process Packages/UC1CdWebServiceInvoke.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.sourceUnavailable", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.1/@implementation/@taskService/@messageIn/@dataMappings.0", //$NON-NLS-1$ 
                                "BPMN : Mapping source parameter postCode is not available. (UC1CdWebServiceInvoke:Lookupaddress:zip_code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/UC1CdRPCLiteralWSInvokeJavaScript/Process Packages/UC1CdWebServiceInvoke.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.sourceUnavailable", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activitySets.0/@activities.2/@implementation/@taskService/@messageIn/@dataMappings.0", //$NON-NLS-1$ 
                                "BPMN : Mapping source parameter postCode is not available. (UC1CdWebServiceInvoke:EmbeddedSubProcess:EmbeddedLookupaddress:zip_code)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "SourceParamUnavalbleNotRequiredTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.mappings.validation.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/SourceParamUnavalbleNotRequiredTest", "UC1CdRPCLiteralWSInvokeJavaScript/Process Packages{processes}/UC1CdWebServiceInvoke.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/SourceParamUnavalbleNotRequiredTest", "UC1CdRPCLiteralWSInvokeJavaScript/Service Descriptors{wsdl}/AddressLookupAppService.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
