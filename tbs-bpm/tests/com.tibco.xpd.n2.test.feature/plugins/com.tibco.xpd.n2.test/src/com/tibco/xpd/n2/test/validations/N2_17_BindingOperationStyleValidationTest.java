/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * BindingOperationStyleValidationTest
 * <p>
 * BindingOperationStyleValidationTest - Test selected validations are correctly
 * raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * Test to see if validation rule appears that says that the WSDL Binding Style
 * and the Binding Operation styles must be the same.
 * 
 * @author
 * @since
 */
public class N2_17_BindingOperationStyleValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_17_BindingOperationStyleValidationTest() {
        super(true);
    }

    /**
     * BindingOperationStyleValidationTest
     * 
     * @throws Exception
     */
    public void testBindingOperationStyleValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/WSDLBindingStyleValidations/Service Descriptors/DocLitRPC.wsdl", //$NON-NLS-1$ 
                                "binding_operation_style_attribute_match", //$NON-NLS-1$ 
                                "//@eBindings.0/@eBindingOperations.0", //$NON-NLS-1$ 
                                "WSDL 1.1 : The style attribute of the binding and operation must match in the WSDL binding. (WSDL Binding: DocLitRPCSOAP)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/WSDLBindingStyleValidations/Service Descriptors/RPCDocLit.wsdl", //$NON-NLS-1$ 
                                "binding_operation_style_attribute_match", //$NON-NLS-1$ 
                                "//@eBindings.0/@eBindingOperations.0", //$NON-NLS-1$ 
                                "WSDL 1.1 : The style attribute of the binding and operation must match in the WSDL binding. (WSDL Binding: NewWSDLFileSOAP)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "BindingOperationStyleValidationTest"; //$NON-NLS-1$
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
                                "resources/BindingOperationStyleValidationTest", "WSDLBindingStyleValidations/Service Descriptors{wsdl}/DocLitRPC.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/BindingOperationStyleValidationTest", "WSDLBindingStyleValidations/Service Descriptors{wsdl}/RPCDocLit.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
