/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * JUnit to keep an eye on all the validations added under ABPM-911 (wherein we
 * introduced event subproceses.)
 * 
 * @author sajain
 * @since 17th Aug, 2014
 */
public class N2_30_EventSubProcessValidationsTest extends
        AbstractBaseValidationTest {

    public N2_30_EventSubProcessValidationsTest() {
        super(true);
    }

    /**
     * N2_30_EventSubProcessValidationsTest
     * 
     * @throws Exception
     */
    public void testN230EventSubProcessValidationsTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bpmn.eventSubProcessMustHaveExactlyOneStartEvent", //$NON-NLS-1$ 
                                "_5jeJgD4sEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "BPMN : Event Sub-Process must have exactly one start event activity. (testProcess:EventSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bpmn.eventSubProcessMustNotHaveFlows", //$NON-NLS-1$ 
                                "_5jeJgD4sEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "BPMN : Event Sub-Process cannot have incoming or outgoing flow. (testProcess:EventSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$
                                "bx.eventSubProcStartEventTriggerTypeRestrictionInBusinessProc", //$NON-NLS-1$
                                "_7DDs8D4sEeSaGbHAYrhcpw", //$NON-NLS-1$
                                "Process Manager  : Only catch-message / catch-signal event sub-processes are supported in Business Processes. (testProcess:EventSubProcess:StartEvent2)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bx.activityWithNoAssociatedCorrelationData", //$NON-NLS-1$ 
                                "_7hMacD4sEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "Process Manager  : Process requires at least one correlation data field for in-flow incoming request activities to identify target process instance. (testProcess:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$                              

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.eventHandlerCorrelationDataInitialization", //$NON-NLS-1$ 
                                "_dno7UD4tEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "BPMN :Event-handlers must be initialized only after the correlation field 'CorrelationField' is assigned in the main process flow. Use the 'Event Handler->Initializers' configuration to specify the activity(s) that set the correlation data. (testProcess3:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bpmn.dev.unmappedCorrelationDataImplicit", //$NON-NLS-1$ 
                                "_dno7UD4tEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "Process Manager  : Implicitly associated Correlation Field (CorrelationField) has not been mapped. (testProcess3:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ABPM-911_EventSubProc_TestProj/Process Packages/test.xpdl", //$NON-NLS-1$ 
                                "bx.eventSubProcStartEventTriggerTypeRestrictionInPageflow", //$NON-NLS-1$ 
                                "_QVH8YD4tEeSaGbHAYrhcpw", //$NON-NLS-1$ 
                                "Process Manager  : Only catch-signal / type-none event sub-processes are supported in Pageflows. (testProcess2:EventSubProcess:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_30_EventSubProcessValidationsTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N2_30_EventSubProcessValidationsTest", "ABPM-911_EventSubProc_TestProj/Process Packages{processes}/test.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
