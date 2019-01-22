/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect Data Mapper correlation mappings validation
 * "Process Manager  : Schema namespace 'http://not.mentioned/in.wsdldefinitions.element' must be specified in the WSDL definition header as it is referenced via correlation mapping. (eventHandlerCorrelationDataInitializationProcess:StartEvent:CorrelationField)"
 * .
 * 
 * @author sajain
 * @since Feb 24, 2016
 */
public class N2_58_WSDataMapperSchemaNamespaceValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_58_WSDataMapperSchemaNamespaceValidationTest() {
        super(true);
    }

    /**
     * N2_58_WSDataMapperSchemaNamespaceValidationTest
     * 
     * @throws Exception
     */
    public void testN2_58_WSDataMapperSchemaNamespaceValidationTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/eventHandlerCorrelationDataInitialization/Process Packages/eventHandlerCorrelationDataInitialization.xpdl", //$NON-NLS-1$ 
                        "bx.missingNamespaceReferencedInCorrelationMapping", //$NON-NLS-1$ 
                        "//@package/@processes.0/@activities.0/@event/@triggerResultMessage/@message/@otherElements.0/@dataMappings.0", //$NON-NLS-1$ 
                        "Process Manager  : Schema namespace 'http://not.mentioned/in.wsdldefinitions.element' must be specified in the WSDL definition header as it is referenced via correlation mapping. (eventHandlerCorrelationDataInitializationProcess:StartEvent:CorrelationField)", //$NON-NLS-1$ 
                        ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_58_WSDataMapperSchemaNamespaceValidationTest"; //$NON-NLS-1$
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
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Business Objects{bom}/bdpproj.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/bds-document-service.xsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/bds-exception.xsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/bds.xsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/comexception.xsd"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/bds.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "bdpproj/Service Descriptors{wsdl}/FaultWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "eventHandlerCorrelationDataInitialization/Process Packages{processes}/eventHandlerCorrelationDataInitialization.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$,
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "eventHandlerCorrelationDataInitialization/Business Objects{bom}/eventHandlerCorrelationDataInitialization.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N258WSDataMapperSchemaNamespaceValidationTest", "eventHandlerCorrelationDataInitialization/Service Descriptors{wsdl}/NewWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
