/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect Data Mapper mappings in CSP and WS activities.
 * 
 * @author sajain
 * @since Feb 24, 2016
 */
public class N2_56_DataMapperMappingsValidationsTest extends
        AbstractN2BaseValidationTest {

    public N2_56_DataMapperMappingsValidationsTest() {
        super(true);
    }

    /**
     * N256DataMapperMappingsValidationsTest
     * 
     * @throws Exception
     */
    public void testN256DataMapperMappingsValidationsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.0/@event/@triggerResultMessage/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Input to Process: The data types are incompatible for mapping 'parameters.in1' to 'Process.priority'. (DataMapperMappingValidationsTestProcess:CatchMessageEvent:Process_priority$)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.1/@event/@triggerResultMessage/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Output From Process: The data types are incompatible for mapping 'Process.id' to 'parameters.out'. (DataMapperMappingValidationsTestProcess:ReplyToCatchMessageEvent:parameters.out)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskService/@messageIn/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Input to Service: The data types are incompatible for mapping 'Process.id' to 'parameters.in1'. (DataMapperMappingValidationsTestProcess:ServiceTask:parameters.in1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskService/@messageOut/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Output From Service: The data types are incompatible for mapping 'parameters.out' to 'Process.priority'. (DataMapperMappingValidationsTestProcess:ServiceTask:Process_priority$)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map To Sub-Process: The data types are incompatible for mapping 'Process.id' to 'Parameter'. (DataMapperMappingValidationsTestProcess:CallSubProcess:Parameter)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.3/@implementation/@otherElements.1/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Map From Sub-Process: The data types are incompatible for mapping 'Parameter' to 'Process.priority'. (DataMapperMappingValidationsTestProcess:CallSubProcess:Process_priority$)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.2/@implementation/@taskReceive/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Input to Process: The data types are incompatible for mapping 'parameters.in1' to 'Process.priority'. (DataMapperMappingValidationsTestProcess2:ReceiveTask:Process_priority$)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.incompatibleTypes", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.3/@implementation/@taskSend/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                                "Process Manager  : Output From Process: The data types are incompatible for mapping 'Process.id' to 'parameters.out'. (DataMapperMappingValidationsTestProcess2:ReplyToReceiveTask:parameters.out)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/DataMapperMappingValidationsTest/Process Packages/DataMapperMappingValidationsTest.xpdl", //$NON-NLS-1$ 
                                "bx.activityWithNoAssociatedCorrelationData", //$NON-NLS-1$ 
                                "_Vam0MNrhEeW45YZmXhrjgQ", //$NON-NLS-1$ 
                                "Process Manager  : Process requires at least one correlation data field for in-flow incoming request activities to identify target process instance. (DataMapperMappingValidationsTestProcess2:ReceiveTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N256DataMapperMappingsValidationsTest"; //$NON-NLS-1$
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
                                "resources/N256DataMapperMappingsValidationsTest", "DataMapperMappingValidationsTest/Process Packages{processes}/DataMapperMappingValidationsTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$,
                        new TestResourceInfo(
                                "resources/N256DataMapperMappingsValidationsTest", "DataMapperMappingValidationsTest/Business Objects{bom}/BusinessObjectModel.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N256DataMapperMappingsValidationsTest", "DataMapperMappingValidationsTest/Service Descriptors{wsdl}/NewWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N256DataMapperMappingsValidationsTest", "DataMapperMappingValidationsTest/Service Descriptors{wsdl}/NewWSDLFile1.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
