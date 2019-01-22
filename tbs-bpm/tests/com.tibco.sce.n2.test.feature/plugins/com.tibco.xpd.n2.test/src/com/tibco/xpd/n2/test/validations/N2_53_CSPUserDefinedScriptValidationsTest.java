/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect User defined script mapping validations for Call
 * sub-process.
 * 
 * @author sajain
 * @since Feb 19, 2016
 */
public class N2_53_CSPUserDefinedScriptValidationsTest extends
        AbstractN2BaseValidationTest {

    public N2_53_CSPUserDefinedScriptValidationsTest() {
        super(true);
    }

    /**
     * N2_53_CSPUserDefinedScriptValidationsTest
     * 
     * @throws Exception
     */
    public void testN2_53_CSPUserDefinedScriptValidationsTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.scriptUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.2/@implementation/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Map From Sub-Process: script mappings are not supported. (MainProcess:CSP1:Field)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "abstractMappingRule.scriptUnsupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.2/@implementation/@dataMappings.3", //$NON-NLS-1$ 
                                "Process Manager  : Map From Sub-Process: script mappings are not supported. (MainProcess:CSP1:Field2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_2tjJwNbqEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (MainProcess:CSP1:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_2tjJwNbqEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (MainProcess:CSP1:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_brAUANbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (MainProcess:CSP1:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_brAUANbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP1:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_dNurANbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (MainProcess:CSP1:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_fDJ7kNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (MainProcess:CSP1:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_fDJ7kNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (MainProcess:CSP1:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_hnPo8NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_HO2_8NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP1:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_jw3n4NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_jw3n4NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (MainProcess:CSP2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_lcJd8NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_LqbMUNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (MainProcess:CSP1:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_mfiNkNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_mfiNkNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (MainProcess:CSP2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_oTgsoNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (MainProcess:CSP2:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_pzeDgNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (MainProcess:CSP2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_pzeDgNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (MainProcess:CSP2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_sGxi4NbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (MainProcess:CSP2:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_t5JfcNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (MainProcess:CSP2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_t5JfcNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (MainProcess:CSP2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_vhvDUNbqEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (MainProcess:CSP1:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_vhvDUNbqEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP1:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCSPUserDefinedScriptValidations/Process Packages/TestCSPUserDefinedScriptValidations.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_ZifvYNbrEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (MainProcess:CSP1:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_53_CSPUserDefinedScriptValidationsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N253CSPUserDefinedScriptValidations", "TestCSPUserDefinedScriptValidations/Process Packages{processes}/TestCSPUserDefinedScriptValidations.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
