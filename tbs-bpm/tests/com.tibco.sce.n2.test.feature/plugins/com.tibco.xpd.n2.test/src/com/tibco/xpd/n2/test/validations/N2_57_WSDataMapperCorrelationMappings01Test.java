/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect Data Mapper correlation mappings WS activities.
 * 
 * @author sajain
 * @since Feb 24, 2016
 */
public class N2_57_WSDataMapperCorrelationMappings01Test extends
        AbstractN2BaseValidationTest {

    public N2_57_WSDataMapperCorrelationMappings01Test() {
        super(true);
    }

    /**
     * N2_57_WSDataMapperCorrelationMappings01Test
     * 
     * @throws Exception
     */
    public void testN2_57_WSDataMapperCorrelationMappings01Test()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bx.inconsistentUseOfCorrelationForSameMessage", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskReceive/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : All correlation mappings from the same operation message-part (parameters) to the same correlation field (CorrelationField) must be identical within a process. (WSCorrelationInDataMapperTestProcess:ReceiveTask:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bx.inconsistentUseOfCorrelationForSameMessage", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@implementation/@taskReceive/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : All correlation mappings from the same operation message-part (parameters) to the same correlation field (CorrelationField) must be identical within a process. (WSCorrelationInDataMapperTestProcess:ReceiveTask2:CorrelationField)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_F9a48NubEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "BPMN  : Event handler correlation data 'CorrelationField' must be mapped in all start activities. Alternatively, you must state when to intialize the Event Handler explicitly. The following start activities fail to initialize the data: 'Start Event' (WSCorrelationInDataMapperTestProcess2:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.unmappedCorrelationData", //$NON-NLS-1$ 
                                "_F9a48NubEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "Process Manager  : Explicitly associated Correlation Field (CorrelationField) has not been mapped. (WSCorrelationInDataMapperTestProcess2:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.unmappedCorrelationData", //$NON-NLS-1$ 
                                "_OOTCpNueEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "Process Manager  : Explicitly associated Correlation Field (CorrelationField) has not been mapped. (WSCorrelationInDataMapperTestProcess3:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.unmappedCorrelationDataImplicit", //$NON-NLS-1$ 
                                "_PQoCUNueEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "Process Manager  : Implicitly associated Correlation Field (CorrelationField) has not been mapped. (WSCorrelationInDataMapperTestProcess3:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSCorrelationInDataMapperTest/Process Packages/WSCorrelationInDataMapperTest.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.unmappedStartCorrelationData", //$NON-NLS-1$ 
                                "_r83iKduPEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "Process Manager  : Implicitly associated correlation data (CorrelationField) must either be initialized (mapped) in Start process activity or explicitly set to not required. (WSCorrelationInDataMapperTestProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_57_WSDataMapperCorrelationMappings01Test"; //$NON-NLS-1$
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
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Process Packages{processes}/WSCorrelationInDataMapperTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$,
                        new TestResourceInfo(
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Business Objects{bom}/WSCorrelationInDataMapperTest.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Service Descriptors{wsdl}/WSDL1.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Service Descriptors{wsdl}/WSDL2.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Service Descriptors{wsdl}/WSDL3.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N257WSDataMapperCorrelationMappings01Test", "WSCorrelationInDataMapperTest/Service Descriptors{wsdl}/WSDL4.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
