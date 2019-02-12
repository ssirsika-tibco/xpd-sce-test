/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.newtests"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_30_N2SubProcessMappingsValidationTest.class);
        suite.addTestSuite(N2_35_XPD3821MissingCorrelationMappingNamespaceRuleTest.class);
        suite.addTestSuite(N2_39_LocalCatchSignalEventMappingValidationsTest.class);
        suite.addTestSuite(N2_40_GlobalSignalReferencingMappingValidationTest.class);

        // $JUnit-END$
        return suite;
    }
}
