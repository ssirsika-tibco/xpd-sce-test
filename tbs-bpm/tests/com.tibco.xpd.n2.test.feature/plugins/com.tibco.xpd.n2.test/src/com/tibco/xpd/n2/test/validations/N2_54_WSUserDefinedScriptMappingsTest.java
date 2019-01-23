/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to protect User defined script mapping validations for Web service
 * activities.
 * 
 * @author sajain
 * @since Feb 19, 2016
 */
public class N2_54_WSUserDefinedScriptMappingsTest extends
        AbstractN2BaseValidationTest {

    public N2_54_WSUserDefinedScriptMappingsTest() {
        super(true);
    }

    /**
     * N2_54_WSUserDefinedScriptMappingsTest
     * 
     * @throws Exception
     */
    public void testN2_54_WSUserDefinedScriptMappingsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskService/@messageIn/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:parameters.in3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.2/@implementation/@taskService/@messageOut/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Field3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.2/@implementation/@taskReceive/@message/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Parameter)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "//@package/@processes.1/@activities.3/@implementation/@taskSend/@message/@dataMappings.2", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:parameters.out13)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.emptyUserDefinedScript", //$NON-NLS-1$ 
                                "_1fXQoNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Empty user defined mapping scripts ('Script5') are not supported (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_203gcNbvEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_2eNMkNbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_2fkT8NbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_35YxINbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_35YxINbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.emptyUserDefinedScript", //$NON-NLS-1$ 
                                "_4IBU8NbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Empty user defined mapping scripts ('Script5') are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_5ZbnkNbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_5zBToNbvEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: Statement  (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_5zBToNbvEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_65oPANbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_65oPANbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_7vckENbvEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_9Eq_sNbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Aq304NbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_AW-I0NbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_AW-I0NbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToCatchMessageEvent:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_B6QEANbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_B6QEANbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_BhabUdbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_BhabUdbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_bxx4INbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_C2Mc8tbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_cHGV8NbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_eCzfkNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_eCzfkNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.emptyScriptNotSupported", //$NON-NLS-1$ 
                                "_EG0XUNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Empty User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_EG0XUNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_GbO74NbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_GbO74NbxEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_H6e9kNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_I8RDUNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_J3BiwNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_J3BiwNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (TestWSUserDefinedScriptMappingsProcess2:CatchMessageEvent:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_KWbUMNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_KWbUMNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_Ln3t8NbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_NJcOYNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_NJcOYNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_P8fk0NbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.emptyUserDefinedScript", //$NON-NLS-1$ 
                                "_PBYncNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Empty user defined mapping scripts ('Script5') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_r1S_ENbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_r1S_ENbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_RHGWwNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_RHGWwNbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script2') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_S2i7IdbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script3)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_UGBmANbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, Last statement must be a return. (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.unmappedScriptUnsupported", //$NON-NLS-1$ 
                                "_UGBmANbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped user defined mapping scripts ('Script4') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_walvMNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Unmapped User Defined Scripts are not supported (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_walvMNbwEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:2 column:1, return statement is not allowed (TestWSUserDefinedScriptMappingsProcess2:ReceiveTask:Script4)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "dataMapper.emptyUserDefinedScript", //$NON-NLS-1$ 
                                "_WBEWYdbzEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : Empty user defined mapping scripts ('Script5') are not supported (TestWSUserDefinedScriptMappingsProcess:ServiceTask2:Script5)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestWSUserDefinedScriptMappings/Process Packages/TestWSUserDefinedScriptMappings.xpdl", //$NON-NLS-1$ 
                                "bx.validateScriptTask", //$NON-NLS-1$ 
                                "_ZRTNgNbyEeWbZoUlmq9kFA", //$NON-NLS-1$ 
                                "Process Manager  : At Line:1 column:9, unexpected token: statement  (TestWSUserDefinedScriptMappingsProcess2:ReplyToReceiveTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_54_WSUserDefinedScriptMappingsTest"; //$NON-NLS-1$
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
                                "resources/N254WSUserDefinedScriptMappingsTest", "TestWSUserDefinedScriptMappings/Process Packages{processes}/TestWSUserDefinedScriptMappings.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$,
                        new TestResourceInfo(
                                "resources/N254WSUserDefinedScriptMappingsTest", "TestWSUserDefinedScriptMappings/Business Objects{bom}/TestWSUserDefinedScriptMappings.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N254WSUserDefinedScriptMappingsTest", "TestWSUserDefinedScriptMappings/Service Descriptors{wsdl}/NewWSDLFile.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N254WSUserDefinedScriptMappingsTest", "TestWSUserDefinedScriptMappings/Service Descriptors{wsdl}/NewWSDLFile1.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
