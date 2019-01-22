/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * Test that checks that the validations added under XPD-7866 are raised
 * correctly. i.e. Complex parameters cannot be associated with Throw Error
 * Events.
 * 
 * 
 * @author kthombar
 * @since Sep 17, 2015
 */
public class N2_52_ThrowErrorEventsComplexAssociatedParamsTest extends
        AbstractN2BaseValidationTest {

    public N2_52_ThrowErrorEventsComplexAssociatedParamsTest() {
        super(true);
    }

    /**
     * N2_52_ThrowErrorEventsComplexAssociatedParamsTest
     * 
     * @throws Exception
     */
    public void testN2_52_ThrowErrorEventsComplexAssociatedParamsTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/ThrowErrorEventsComplexDataAssociation/Process Packages/ThrowErrorEventsComplexDataAssociation.xpdl", //$NON-NLS-1$ 
                                "bx.complexTypeCannotBeAssociatedWithErrorCodeThrowErrorEvents", //$NON-NLS-1$ 
                                "_jq690F0OEeWIaoAzZuxnCw", //$NON-NLS-1$ 
                                "Process Manager  : Complex type data (Parameter) cannot be associated with 'Throw Process / Sub-Process Error' events. (ThrowErrorEventsComplexDataAssociationProcess:ErrorEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ThrowErrorEventsComplexDataAssociation/Process Packages/ThrowErrorEventsComplexDataAssociation.xpdl", //$NON-NLS-1$ 
                                "bx.complexTypeCannotBeAssociatedWithErrorCodeThrowErrorEvents", //$NON-NLS-1$ 
                                "_jq690F0OEeWIaoAzZuxnCw", //$NON-NLS-1$ 
                                "Process Manager  : Complex type data (Parameter2) cannot be associated with 'Throw Process / Sub-Process Error' events. (ThrowErrorEventsComplexDataAssociationProcess:ErrorEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ThrowErrorEventsComplexDataAssociation/Process Packages/ThrowErrorEventsComplexDataAssociation.xpdl", //$NON-NLS-1$ 
                                "bx.complexTypeCannotBeAssociatedWithErrorCodeThrowErrorEvents", //$NON-NLS-1$ 
                                "_jq690F0OEeWIaoAzZuxnCw", //$NON-NLS-1$ 
                                "Process Manager  : Complex type data (Parameter3) cannot be associated with 'Throw Process / Sub-Process Error' events. (ThrowErrorEventsComplexDataAssociationProcess:ErrorEvent)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/ThrowErrorEventsComplexDataAssociation/Process Packages/ThrowErrorEventsComplexDataAssociation.xpdl", //$NON-NLS-1$ 
                                "bx.complexTypeCannotBeAssociatedWithErrorCodeThrowErrorEvents", //$NON-NLS-1$ 
                                "_vtU_wF0OEeWIaoAzZuxnCw", //$NON-NLS-1$ 
                                "Process Manager  : Complex type data (Parameter) cannot be associated with 'Throw Process / Sub-Process Error' events. (ThrowErrorEventsComplexDataAssociationProcess:ErrorEvent2)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_52_ThrowErrorEventsComplexAssociatedParamsTest"; //$NON-NLS-1$
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
                                "resources/N252ThrowErrorEventsComplexAssociatedParamsTest", "ThrowErrorEventsComplexDataAssociation/Business Objects{bom}/ThrowErrorEventsComplexDataAssociation.bom"), //$NON-NLS-1$ //$NON-NLS-2$
                        new TestResourceInfo(
                                "resources/N252ThrowErrorEventsComplexAssociatedParamsTest", "ThrowErrorEventsComplexDataAssociation/Process Packages{processes}/ThrowErrorEventsComplexDataAssociation.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
