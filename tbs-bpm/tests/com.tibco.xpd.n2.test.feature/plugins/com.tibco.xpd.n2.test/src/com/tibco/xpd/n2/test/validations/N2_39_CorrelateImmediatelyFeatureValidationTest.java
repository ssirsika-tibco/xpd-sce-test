/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Junit test for "Correlate Immediately" Feature added in XPD-6972
 * 
 * 
 * @author kthombar
 * @since Dec 18, 2014
 */
public class N2_39_CorrelateImmediatelyFeatureValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_39_CorrelateImmediatelyFeatureValidationTest() {
        super(true);
    }

    /**
     * N2_39_CorrelateImmediatelyFeatureValidationTest
     * 
     * @throws Exception
     */
    public void testN2_39_CorrelateImmediatelyFeatureValidationTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.correlationTimeoutUsedInConjunctionWithCorrelateImmediately.error", //$NON-NLS-1$ 
                                "_-8_5YIauEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Incoming request activity 'Correlation Timeout' cannot be used in conjunction with the 'Correlate  Immediately' feature. (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:One)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_-8_5YIauEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process/Two'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:One)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_-8_5YIauEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process2/Catch Message Event'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:One)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_AkxvgIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process/One'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:Two)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_AkxvgIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process3/Catch Message Event 2'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:Two)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_AkxvgIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process3/Catch Message Event'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess:Two)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.correlationTimeoutUsedInConjunctionWithCorrelateImmediately.error", //$NON-NLS-1$ 
                                "_bltmQIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Incoming request activity 'Correlation Timeout' cannot be used in conjunction with the 'Correlate  Immediately' feature. (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:EventSubProcess:CatchMessageEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_bltmQIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process/Two'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:EventSubProcess:CatchMessageEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_bltmQIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process2/Catch Message Event'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:EventSubProcess:CatchMessageEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_FzpoIIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process/One'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess2:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_FzpoIIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process3/Catch Message Event 2'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess2:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_FzpoIIavEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process3/Catch Message Event'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess2:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.correlationTimeoutUsedInConjunctionWithCorrelateImmediately.error", //$NON-NLS-1$ 
                                "_J77FF4avEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Incoming request activity 'Correlation Timeout' cannot be used in conjunction with the 'Correlate  Immediately' feature. (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_J77FF4avEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process/Two'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl", //$NON-NLS-1$ 
                                "bx.uniqueCorrelationConfigForActivityWithSameOperation.error", //$NON-NLS-1$ 
                                "_J77FF4avEeS4I9GGrysJHA", //$NON-NLS-1$ 
                                "Process Manager  : Correlating Activities for the same service operation ('N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl/myWsdl') must have same 'Correlate Immediately' configuration('N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl/N2_39_CorrelateImmediatelyFeatureValidationTest-Process2/Catch Message Event'). (N2_39_CorrelateImmediatelyFeatureValidationTestProcess3:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_39_CorrelateImmediatelyFeatureValidationTest"; //$NON-NLS-1$
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
                                "resources/N239CorrelateImmediatelyFeatureValidationTest", "N2_39_CorrelateImmediatelyFeatureValidationTest/Process Packages{processes}/N2_39_CorrelateImmediatelyFeatureValidationTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N239CorrelateImmediatelyFeatureValidationTest", "N2_39_CorrelateImmediatelyFeatureValidationTest/Service Descriptors{wsdl}/N2_39_CorrelateImmediatelyFeatureValidationTestProcess-myWsdl.wsdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
