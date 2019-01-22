/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to ensure that the validations for case data signals are raise
 * appropriately. This test checks for the following validations:
 * <p>
 * 1. Case Data Signals can only be caught in Business Process signal event
 * sub-processes and event handlers.
 * <p>
 * 2. Case Data Signal Events must specify a case reference field / parameter.
 * <p>
 * 3. The case reference field / parameter for Case Data signal could not be
 * found.
 * <p>
 * 4. The case reference field / parameter selected in the catch signal event
 * should not be an array field.
 * <p>
 * 
 * @author sajain
 * @since Mar 31, 2015
 */
public class N2_46_CaseDataSignalValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_46_CaseDataSignalValidationTest() {
        super(true);
    }

    /**
     * N2_46_CaseDataSignalValidationTest
     * 
     * @throws Exception
     */
    public void testN2_46_CaseDataSignalValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCaseDataSignalProject/Process Packages/TestCaseDataSignalProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseDataSigsMustBeInSigEventHandlersAndESPStartEvents", //$NON-NLS-1$ 
                                "_94ENoddrEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Process Manager  : Case Data Signals can only be caught in Business Process signal event sub-processes and event handlers. (TestCaseDataSignalProjectProcess:CatchSignalEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCaseDataSignalProject/Process Packages/TestCaseDataSignalProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseDataSigEventsMustSpecifyCaseRefData", //$NON-NLS-1$ 
                                "_cbR4UNdsEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Process Manager  : Case Data Signal Events must specify a case reference field / parameter. (TestCaseDataSignalProjectProcess:EventSubProcess:CatchSignalEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCaseDataSignalProject/Process Packages/TestCaseDataSignalProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseRefDataInCatchEventShouldNotBeArray", //$NON-NLS-1$ 
                                "_jN7Q0NdsEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Process Manager  : The case reference field / parameter selected in the catch signal event should not be an array. (TestCaseDataSignalProjectProcess:CatchCustomerDataChanged)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/TestCaseDataSignalProject/Process Packages/TestCaseDataSignalProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseRefDataNotFound", //$NON-NLS-1$ 
                                "_MCeocNdtEeSVdLYQ5RUqsQ", //$NON-NLS-1$ 
                                "Process Manager  : The case reference field / parameter (CustomerData2) for Case Data signal could not be found. (TestCaseDataSignalProjectProcess:CatchCustomerData2Changed)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_46_CaseDataSignalValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N246CaseDataSignalValidationTest", "TestCaseDataSignalProject/Process Packages{processes}/TestCaseDataSignalProject.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
