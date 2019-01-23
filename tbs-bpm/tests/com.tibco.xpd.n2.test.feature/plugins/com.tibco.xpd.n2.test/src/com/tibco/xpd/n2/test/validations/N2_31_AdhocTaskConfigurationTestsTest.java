/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * Junit to check if the expected validations are generated for Adhoc Tasks.
 * 
 * 
 * @author kthombar
 * @since 18-Aug-2014
 */
public class N2_31_AdhocTaskConfigurationTestsTest extends
        AbstractBaseValidationTest {

    public N2_31_AdhocTaskConfigurationTestsTest() {
        super(true);
    }

    /**
     * N231AdhocTaskConfigurationTestsTest
     * 
     * @throws Exception
     */
    public void testN231AdhocTaskConfigurationTestsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bx.adhocActivitiesShouldNoHaveIncomingOutgoingTransitions", //$NON-NLS-1$ 
                                "_11XhYCbFEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc user/sub-process activity cannot have incoming/outgoing flow. (N2_30_AdhocTaskConfigurationTestsProcess:Adhoctaskwithincomingoutgoingflow)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bx.adhocPreconditionJavaScriptEmpty", //$NON-NLS-1$ 
                                "_9K4rgCbHEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc precondition script must not be empty. (N2_30_AdhocTaskConfigurationTestsProcess:AdhocTaskwithEmptyJavaScript)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bx.warning.adhocPreconditionScript", //$NON-NLS-1$ 
                                "_Hz4DcCbIEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc Configuration::At Line:2 column:1, The last expression of the script is an assignment, this could give unexpected results at runtime (AdhocTaskNotInBusinessProcess:AdhocTaskOnlysupportedforbusinessprocess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.adhocInitializerActivityNotAvailable", //$NON-NLS-1$ 
                                "_MK15ICbHEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "BPMN : Selected Ad-Hoc initializer activity no longer exists. (N2_30_AdhocTaskConfigurationTestsProcess:AdhoctaskswithInitializerActivitiesDeleted)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bx.adhocPreconditionFreeTextUnsupported", //$NON-NLS-1$ 
                                "_nzZJgCbHEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc precondition script grammar must be JavaScript. (N2_30_AdhocTaskConfigurationTestsProcess:AdhocTaskWithFreeText)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_30_AdhocTaskConfigurationTests/Process Packages/N2_30_AdhocTaskConfigurationTests.xpdl", //$NON-NLS-1$ 
                                "bx.warning.adhocPreconditionScript", //$NON-NLS-1$ 
                                "_ZNkIkCbHEeSw3Kr_rGqARA", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc Configuration::At Line:2 column:1, The last expression of the script is an assignment, this could give unexpected results at runtime (N2_30_AdhocTaskConfigurationTestsProcess:AdhocTaskwithBoundaryEvents)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_31_AdhocTaskConfigurationTestsTest"; //$NON-NLS-1$
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
                                "resources/N2_31_AdhocTaskConfigurationTestsTest", "N2_30_AdhocTaskConfigurationTests/Organization{om}/N2_30_AdhocTaskConfigurationTests.om"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N2_31_AdhocTaskConfigurationTestsTest", "N2_30_AdhocTaskConfigurationTests/Process Packages{processes}/N2_30_AdhocTaskConfigurationTests.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }
}
