/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsA {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_01_JSBaseElRequiresMappingTest.class);

        suite.addTestSuite(N2_06_JSInvalidModeTest.class);
        suite.addTestSuite(N2_07_BOMToProcessValidationTest.class);
        suite.addTestSuite(N2_08_BrokenMappingsTest.class);

        suite.addTestSuite(N2_11_ComplextoComplexTypeTest.class);
        suite.addTestSuite(N2_12_SourceParamUnavalbleNotRequiredTest.class);
        // Kapil: commenting out this test as it is no longer valid.
        // suite.addTestSuite(N2_13_BOMNotPresentValidationTest.class);

        // $JUnit-END$
        return suite;
    }
}
