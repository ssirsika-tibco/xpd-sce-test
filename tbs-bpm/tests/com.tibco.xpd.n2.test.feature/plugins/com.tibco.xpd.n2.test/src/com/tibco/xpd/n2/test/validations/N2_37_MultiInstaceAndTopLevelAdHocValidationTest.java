/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;

/**
 * JUnit to keep an eye on the following validations:
 * <p>
 * 1. Process Manager: Multiple instance Ad-Hoc tasks are not supported
 * <p>
 * 2. Process Manager: Ad-Hoc tasks are only permitted in the top level process
 * (not in embedded / event sub-processes).
 * <p>
 * 
 * @author sajain
 * @since 08th Oct, 2014
 */
public class N2_37_MultiInstaceAndTopLevelAdHocValidationTest extends
        AbstractN2BaseValidationTest {

    public N2_37_MultiInstaceAndTopLevelAdHocValidationTest() {
        super(true);
    }

    /**
     * N237MultiInstaceAndTopLevelAdHocValidationTest
     * 
     * @throws Exception
     */
    public void testN237MultiInstaceAndTopLevelAdHocValidationTest()
            throws Exception {
        doTestValidations();
        return;
    }

    @Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
        ValidationsTestProblemMarkerInfo[] markerInfos =
                new ValidationsTestProblemMarkerInfo[] {

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD6862ANDXPD6869TestProj/Process Packages/XPD6862ANDXPD6869TestProj.xpdl", //$NON-NLS-1$ 
                                "bx.adHocMustBeTopLevel", //$NON-NLS-1$ 
                                "_FOgwYE6sEeSuEp2l5JYYCg", //$NON-NLS-1$ 
                                "Process Manager  : Ad-Hoc tasks are only permitted in the top level process (not in embedded / event sub-processes). (XPD6862ANDXPD6869TestProjProcess:EventSubProcess:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                        new ValidationsTestProblemMarkerInfo(
                                "/XPD6862ANDXPD6869TestProj/Process Packages/XPD6862ANDXPD6869TestProj.xpdl", //$NON-NLS-1$ 
                                "bx.multiInstanceAdHocTasksNotSupported", //$NON-NLS-1$ 
                                "_FOgwYE6sEeSuEp2l5JYYCg", //$NON-NLS-1$ 
                                "Process Manager  : Multiple instance Ad-Hoc tasks are not supported. (XPD6862ANDXPD6869TestProjProcess:EventSubProcess:UserTask)", //$NON-NLS-1$ 
                                ""), //$NON-NLS-1$ 

                };
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "N2_37_MultiInstaceAndTopLevelAdHocValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.n2.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources =
                new TestResourceInfo[] { new TestResourceInfo(
                        "resources/N237MultiInstaceAndTopLevelAdHocValidationTest", "XPD6862ANDXPD6869TestProj/Process Packages{processes}/XPD6862ANDXPD6869TestProj.xpdl"), //$NON-NLS-1$ //$NON-NLS-2$
                };

        return testResources;
    }

}
