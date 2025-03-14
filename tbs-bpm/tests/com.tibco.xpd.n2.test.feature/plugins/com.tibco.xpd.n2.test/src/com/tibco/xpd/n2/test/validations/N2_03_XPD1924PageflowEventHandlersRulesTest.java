/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * XPD1924PageflowEventHandlersRulesTest
 * <p>
 * XPD1924PageflowEventHandlersRulesTest - Test selected validations are
 * correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 * 
 * Test new rules introduced by XPD-1924 for event handlers in pageflow
 * processes
 * 
 * @author
 * @since
 */
public class N2_03_XPD1924PageflowEventHandlersRulesTest extends
        AbstractN2BaseValidationTest {

    public N2_03_XPD1924PageflowEventHandlersRulesTest() {
        super(true);
    }

    /**
     * XPD1924PageflowEventHandlersRulesTest
     * 
     * @throws Exception
     */
    public void testXPD1924PageflowEventHandlersRulesTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "wm.pageflow.eventHandlerMustBeCatchSignalOrUntriggered", //$NON-NLS-1$ 
                                "_y621UHpWEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Work Manager 2.x : Only catch-signal/catch-cancel/type-none event handlers are supported in Pageflow Processes. (XPD1924Process2:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "wm.pageflow.eventHandlerParamsOnly", //$NON-NLS-1$ 
                                "_WlohoXpVEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Work Manager 1.x : Data fields are not supported in an event handler activity's interface. (XPD1924Process2:UntriggeredEvent)", //$NON-NLS-1$ 
                                "Remove data fields from activity interface. "), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "wm.pageflow.eventHandlerParamModeInOnly", //$NON-NLS-1$ 
                                "_WlohoXpVEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Work Manager 1.x : Event handler activity interface data must be mode in or in/out. (XPD1924Process2:UntriggeredEvent)", //$NON-NLS-1$ 
                                "Set activity interface data to 'In' mode."), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "bx.eventHandlerFlowMayNotJoinOthers", //$NON-NLS-1$ 
                                "_Wloho3pVEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Process Manager 1.x : Event handler flows cannot join into other flows. (XPD1924Process2:UntriggeredEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "bx.eventHandlerFlowMayNotJoinOthers", //$NON-NLS-1$ 
                                "_y621UHpWEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Process Manager 1.x : Event handler flows cannot join into other flows. (XPD1924Process2:CatchMessageEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "bx.eventHandlerFlowMayNotJoinOthers", //$NON-NLS-1$ 
                                "_amgykHpUEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Process Manager 1.x : Event handler flows cannot join into other flows. (XPD1924Process:UntriggeredEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "bx.eventHandlerFlowMayNotJoinOthers", //$NON-NLS-1$ 
                                "_Fe-_MHpUEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Process Manager 1.x : Event handler flows cannot join into other flows. (XPD1924Process:UntriggeredEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/new/Process Packages/XPD-1924.xpdl", //$NON-NLS-1$ 
                                "bx.eventHandlerFlowMayNotJoinOthers", //$NON-NLS-1$ 
                                "_WlohoXpVEeCEp_mStEp74w", //$NON-NLS-1$ 
                                "Process Manager 1.x : Event handler flows cannot join into other flows. (XPD1924Process2:UntriggeredEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "XPD1924PageflowEventHandlersRulesTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/XPD1924 PageflowEventHandlersRulesTest", "new/Process Packages{processes}/XPD-1924.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
