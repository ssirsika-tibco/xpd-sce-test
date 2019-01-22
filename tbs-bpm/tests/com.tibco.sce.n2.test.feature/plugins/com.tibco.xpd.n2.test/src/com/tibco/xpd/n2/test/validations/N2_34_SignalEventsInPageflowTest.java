/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;

/**
 * JUnit test to check that expected validation markers are raised for the usage
 * of signal events in pageflows.
 * 
 * @author sajain
 * @since 15th Sep, 2014
 */
public class N2_34_SignalEventsInPageflowTest extends
        AbstractBaseValidationTest {

    public N2_34_SignalEventsInPageflowTest() {
        super(true);
    }

    /**
     * N2_34_SignalEventsInPageflowTest
     * 
     * @throws Exception
     */
    public void testN2_34_SignalEventsInPageflowTest() throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                new ValidationsTestProblemMarkerInfo(
                        "/testProj04/Process Packages/testProj03.xpdl", //$NON-NLS-1$ 
                        "pageflow.startTypeNotSupported", //$NON-NLS-1$ 
                        "_PcJtIzylEeS-hqmj6dRSPA", //$NON-NLS-1$ 
                        "Pageflow  : Start event trigger type 'Catch Signal Event' is not supported. (testProj03Process:Start)", //$NON-NLS-1$ 
                        ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_34_SignalEventsInPageflowTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N2_34_SignalEventsInPageflowTest", "testProj04/Process Packages{processes}/testProj03.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
