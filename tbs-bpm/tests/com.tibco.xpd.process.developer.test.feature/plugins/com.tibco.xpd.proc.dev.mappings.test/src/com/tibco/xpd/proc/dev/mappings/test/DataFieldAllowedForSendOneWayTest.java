/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.proc.dev.mappings.test;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * DataFieldAllowedForSendOneWayTest
 * <p>
 * DataFieldAllowedForSendOneWayTest - Test selected validations are correctly
 * raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * Data Fields must be allowed to be mapped for Send one way requests. Send one
 * way requests could be Intermediate throw message, Send Task, or End Message
 * 
 * @author
 * @since
 */
public class DataFieldAllowedForSendOneWayTest extends
        AbstractBaseValidationTest {

    public DataFieldAllowedForSendOneWayTest() {
        super(false);
    }

    /**
     * DataFieldAllowedForSendOneWayTest
     * 
     * @throws Exception
     */
    public void testDataFieldAllowedForSendOneWayTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.9/@event/@triggerResultMessage/@message/@dataMappings.3", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field2) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:EndSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/finiteMultiplicity[3]/finiteMultiplicity{0})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultMessage/@message/@dataMappings.4", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:IntermediateSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/finiteMultiplicity[3]/finiteMultiplicity{1})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultMessage/@message/@dataMappings.8", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field5) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:IntermediateSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{4})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.9/@event/@triggerResultMessage/@message/@dataMappings.4", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field3) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:EndSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/finiteMultiplicity[3]/finiteMultiplicity{1})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultMessage/@message/@dataMappings.7", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field4) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:IntermediateSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{3})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.9/@event/@triggerResultMessage/@message/@dataMappings.5", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field4) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:EndSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{0})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultMessage/@message/@dataMappings.6", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field3) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:IntermediateSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{2})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.9/@event/@triggerResultMessage/@message/@dataMappings.6", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field5) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:EndSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{1})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPathMappingsTest No Dest Set/Process Packages/MaxMapped.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.datafieldMappingError", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.11/@event/@triggerResultMessage/@message/@dataMappings.5", //$NON-NLS-1$ 
                                "BPMN : Data Fields (Field2) cannot be used for mappings in Process API activities (Start / Catch Message Events, Receive Tasks and Reply Activities). (MaxMapped:IntermediateSendOneWay:wso:NewOperation/part:parameters/group:sequence[0]/strArr[4]/strArr{1})", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "DataFieldAllowedForSendOneWayTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.proc.dev.mappings.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] {
                        new TestResourceInfo(
                                "resources/DataFieldAllowedForSendOneWayTest", "XPathMappingsTest No Dest Set/Process Packages{processes}/MaxMapped.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/DataFieldAllowedForSendOneWayTest", "XPathMappingsTest No Dest Set/Service Descriptors{wsdl}/MR40550.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
