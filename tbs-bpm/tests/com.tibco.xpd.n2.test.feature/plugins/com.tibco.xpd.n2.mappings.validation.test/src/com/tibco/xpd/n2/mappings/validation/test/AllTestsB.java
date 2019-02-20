/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_14_ProcessToBOMValidationTest.class);

        suite.addTestSuite(N2_17_ChildAttribMappingNotRequiredTest.class);
        suite.addTestSuite(N2_19_JSRequiredAttributeTest.class);
        suite.addTestSuite(N2_20_DateTimeIncorrectValidationMarkerTest.class);
        suite.addTestSuite(N2_21_ParticipantRequiredTest.class);
        suite.addTestSuite(N2_24_ConcatenationMappingUnsupportedTest.class);
        suite.addTestSuite(N2_26_PageflowProcessCorrelationDataUnsupportedTest.class);
        suite.addTestSuite(N2_27_ReqdParamNotMappedTest.class);
        suite.addTestSuite(N2_29_BOMToBOMMappingsTypeValidationTest.class);
        suite.addTestSuite(N2_30_OptionalAttribMarkerShownTest.class);
        suite.addTestSuite(N2_31_ArrayNotSupportedTest.class);
        suite.addTestSuite(N2_32_CaseRefToFromNonCaseRefMappingTest.class);
        // $JUnit-END$
        return suite;
    }
}
