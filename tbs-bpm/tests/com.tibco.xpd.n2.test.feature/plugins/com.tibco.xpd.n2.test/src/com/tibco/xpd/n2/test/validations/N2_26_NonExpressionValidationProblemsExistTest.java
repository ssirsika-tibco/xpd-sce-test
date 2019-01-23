/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * Since some of the validation rules that are contributed by Xpdl2ScopeProvider
 * and had to move due to the new ExpressionScopeProvider, test that the problem
 * markers for these rules are correctly raised.
 * 
 * @author agondal
 * @since 26 Sep 2013
 */
public class N2_26_NonExpressionValidationProblemsExistTest extends
        AbstractBaseValidationTest {

    public N2_26_NonExpressionValidationProblemsExistTest() {
        super(true);
    }

    /**
     * N226NonExpressionValidationProblemsExistTest
     * 
     * @throws Exception
     */
    public void testN226NonExpressionValidationProblemsExistTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.missingScript", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.6/@implementation/@taskService/@messageIn/@dataMappings.1", //$NON-NLS-1$ 
                                "BPMN : No script defined for mapped script item. (Process1:ServiceTask2:wso:/part:p1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.missingScript", //$NON-NLS-1$ 
                                "//@package/@processes.0/@activities.6/@implementation/@taskService/@messageOut/@dataMappings.0", //$NON-NLS-1$ 
                                "BPMN : No script defined for mapped script item. (Process1:ServiceTask2:p1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.xpath.validateScript", //$NON-NLS-1$ 
                                "_0BYMECIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "xpath.validateScript", //$NON-NLS-1$ 
                                "_0BYMECIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "XPath 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.xpath.validateScript", //$NON-NLS-1$ 
                                "_5tMfYCIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "xpath.validateScript", //$NON-NLS-1$ 
                                "_5tMfYCIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "XPath 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.xpath.validateScript", //$NON-NLS-1$ 
                                "_9_xu4CIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "xpath.validateScript", //$NON-NLS-1$ 
                                "_9_xu4CIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "XPath 2.x : At Line:1 column:1, unexpected char: ';' (Process1:ServiceTask2:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.xpath.validateScript", //$NON-NLS-1$ 
                                "_E7IXcCILEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : At Line:1 column:6, unexpected token: a  (Process1:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "xpath.validateScript", //$NON-NLS-1$ 
                                "_E7IXcCILEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "XPath 2.x : At Line:1 column:6, unexpected token: a  (Process1:ServiceTask2:Script2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.emptyJavaScript", //$NON-NLS-1$ 
                                "_Q76ToCIJEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : For JavaScript grammar, script cannot be empty. (Process1:ScriptTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.initiateJavaScriptEmpty", //$NON-NLS-1$ 
                                "_TVMfMCIJEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Task initiate script must not be empty. (Process1:Task)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_WprSQCIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/Package2.xpdl", //$NON-NLS-1$ 
                                "bx.unmappedUDSNotSupported", //$NON-NLS-1$ 
                                "_ZMiQkCIKEeO1oKFVRlM0wg", //$NON-NLS-1$ 
                                "Process Manager 2.x : Unmapped User Defined Scripts are not supported (Process1:ServiceTask:Script1)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N226NonExpressionValidationProblemsExistTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/ExpressionValidationRulesTest", "BpmProject/Process Packages{processes}/Package2.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
