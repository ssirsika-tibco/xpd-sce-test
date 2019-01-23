/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Test that checks if appropriate validation markers are raised if an adhoc
 * task has timeout script defined.
 * 
 * 
 * @author kthombar
 * @since 24-Sep-2014
 */
public class N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest extends
        AbstractN2BaseValidationTest {

    public N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest() {
        super(true);
    }

    /**
     * N235TimeOutScriptsNotSupportedForAdhocActivitiesTest
     * 
     * @throws Exception
     */
    public void testN235TimeOutScriptsNotSupportedForAdhocActivitiesTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest/Process Packages/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest.xpdl", //$NON-NLS-1$ 
                                "bx.timeoutScriptNotSupportedForAdhocActivities", //$NON-NLS-1$ 
                                "_M8WH0EPBEeSshL3KLzK-IQ", //$NON-NLS-1$ 
                                "Process Manager  : Timeout script is not supported for Ad-Hoc activities (as attached timer events are required for this but not supported). (N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTestProcess:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest/Process Packages/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest.xpdl", //$NON-NLS-1$ 
                                "bx.timeoutScriptNotSupportedForAdhocActivities", //$NON-NLS-1$ 
                                "_NcKQUEPBEeSshL3KLzK-IQ", //$NON-NLS-1$ 
                                "Process Manager  : Timeout script is not supported for Ad-Hoc activities (as attached timer events are required for this but not supported). (N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTestProcess:CallSubProcess)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest/Process Packages/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest.xpdl", //$NON-NLS-1$ 
                                "bx.timeoutScriptWithNoAttachedEvent", //$NON-NLS-1$ 
                                "_Vf5dQEPBEeSshL3KLzK-IQ", //$NON-NLS-1$ 
                                "Process Manager  : Task Timeout script is not supported unless a timer event is attached to the task boundary (to define the timeout).   (N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTestProcess:UserTask2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N235TimeOutScriptsNotSupportedForAdhocActivitiesTest", "N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest/Process Packages{processes}/N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
