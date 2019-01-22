/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Tests validation markers for an activity with REST support (XPD-4320)
 * 
 * @author agondal
 * @since 7 Jan 2013
 */
public class N2_11_RESTServiceValidationTest extends AbstractN2BaseValidationTest {

    public N2_11_RESTServiceValidationTest() {
        super(true);
    }

    /**
     * RESTServiceValidationTest
     * 
     * @throws Exception
     */
    public void testRESTServiceValidationTest() throws Exception {
        doTestValidations();
        return;
    }

    /**
     * @see com.tibco.xpd.core.test.validations.AbstractBaseValidationTest#getValidationProblemMarkerInfos()
     * 
     * @return
     */
    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/RestActivityProblemMarkers/Process Packages/RestActivityProblemMarkers.xpdl", //$NON-NLS-1$ 
                                "bx.restServiceGenerationFailed", //$NON-NLS-1$ 
                                "_XSiOMll5EeKls7uMAhga-Q", //$NON-NLS-1$ 
                                "Process Manager 2.x : The internal REST Service has not been generated for the activity 'CatchMessageEvent1'. (MissingRestServiceProblemMarkerProcess:CatchMessageEvent1)", //$NON-NLS-1$ 
                                "Generate internal REST Service"), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/RestActivityProblemMarkers/Process Packages/RestActivityProblemMarkers.xpdl", //$NON-NLS-1$ 
                                "bx.restServiceNameConflict", //$NON-NLS-1$ 
                                "_UeG-wFl5EeKls7uMAhga-Q", //$NON-NLS-1$ 
                                "Process Manager 2.x : The internal REST service name 'DuplicateRestServiceNameProblemMarkerProcess_CatchMessageEvent2' clashes with the process name 'DuplicateRestServiceNameProblemMarkerProcess_CatchMessageEvent2' in the package. (RestActivityProblemMarkers)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/RestActivityProblemMarkers/Process Packages/RestActivityProblemMarkers.xpdl", //$NON-NLS-1$ 
                                "bx.restServiceParticipantNameConflict", //$NON-NLS-1$ 
                                "_UeG-wFl5EeKls7uMAhga-Q", //$NON-NLS-1$ 
                                "Process Manager 2.x : The internal REST service participant name 'Partic_vXWqYVl_EeKls7uMAhga-Q' clashes with another participant in the package. (RestActivityProblemMarkers)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };

        return markerInfos;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestName()
     * 
     * @return
     */
    @Override
    protected String getTestName() {
        return "RESTServiceValidationTest"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestResources()
     * 
     * @return
     */
    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/RESTServiceValidationTest", "RestActivityProblemMarkers/Process Packages{processes}/RestActivityProblemMarkers.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#getTestPlugInId()
     * 
     * @return
     */
    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

}
