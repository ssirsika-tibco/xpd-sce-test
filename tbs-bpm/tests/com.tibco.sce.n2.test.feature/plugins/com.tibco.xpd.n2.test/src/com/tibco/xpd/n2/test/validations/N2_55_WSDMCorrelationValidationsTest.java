/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect correlation mapping validation
 * "BPMN  : Event handler correlation data 'Trigger' must be mapped in all start activities. Alternatively, you must state when to intialize the Event Handler explicitly. The following start activities fail to initialize the data: 'Start Event' (CorrelationMappingValidationProcess2:CorrelateOnOrderID)"
 * for Web service activities.
 * 
 * @author sajain
 * @since Feb 23, 2016
 */
public class N2_55_WSDMCorrelationValidationsTest extends
        AbstractN2BaseValidationTest {

    public N2_55_WSDMCorrelationValidationsTest() {
        super(true);
    }

    /**
     * N2_55_WSDMCorrelationValidationsTest
     * 
     * @throws Exception
     */
    public void testN2_55_WSDMCorrelationValidationsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/N255WSDMCorrelationValidationsTest/Process Packages/CorrelationMappingValidation.xpdl", //$NON-NLS-1$ 
                        "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                        "_gnESsM8GEeWtw_R4Gajpqw", //$NON-NLS-1$ 
                        "BPMN  : Event handler correlation data 'Trigger' must be mapped in all start activities. Alternatively, you must state when to intialize the Event Handler explicitly. The following start activities fail to initialize the data: 'Start Event' (CorrelationMappingValidationProcess2:CorrelateOnOrderID)", //$NON-NLS-1$ 
                        ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_55_WSDMCorrelationValidationsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/N255WSDMCorrelationValidationsTest", "N255WSDMCorrelationValidationsTest/Process Packages{processes}/CorrelationMappingValidation.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N255WSDMCorrelationValidationsTest", "N255WSDMCorrelationValidationsTest/Business Objects{bom}/CorrelationMappingValidation.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N255WSDMCorrelationValidationsTest", "N255WSDMCorrelationValidationsTest/Service Descriptors{wsdl}/NewWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
