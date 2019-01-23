/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Junit test that verifies that the validation added under XPD-7454 is raised
 * correctly. i.e. a validation marker is raised if a Case Data Signal is
 * followed by a Global Data Task with same case ref field.
 * 
 * 
 * @author kthombar
 * @since Jun 18, 2015
 */
public class N2_50_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest
        extends AbstractN2BaseValidationTest {

    public N2_50_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest() {
        super(true);
    }

    /**
     * N2_49_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest
     * 
     * @throws Exception
     */
    public void testN2_50_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/BpmProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseDataSigWithDownstreamGlobalDataTask", //$NON-NLS-1$ 
                                "_b1QIsBWqEeWGj_Mkq1viFQ", //$NON-NLS-1$ 
                                "Process Manager  : Changing a case object ('Field') downstream of a case signal handler for the same object could cause an infinite event handler loop. (BpmProjectProcess:EventSubProcess:ServiceTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/BpmProject/Process Packages/BpmProject.xpdl", //$NON-NLS-1$ 
                                "bx.caseDataSigWithDownstreamGlobalDataTask", //$NON-NLS-1$ 
                                "_SBd6cBWqEeWGj_Mkq1viFQ", //$NON-NLS-1$ 
                                "Process Manager  : Changing a case object ('Field') downstream of a case signal handler for the same object could cause an infinite event handler loop. (BpmProjectProcess:ServiceTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_50_CaseSignalFollowedByGlobalDataTaskWithSameCaseRefFieldTest"; //$NON-NLS-1$
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
                                "resources/N249CaseSignalFollowedByGlobalDataTaskWithSameCaseRefField", "BizDataProject/Business Objects{bom}/BizDataProject.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N249CaseSignalFollowedByGlobalDataTaskWithSameCaseRefField", "BpmProject/Process Packages{processes}/BpmProject.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
