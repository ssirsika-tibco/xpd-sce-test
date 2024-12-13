/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsA {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.newtests"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_07_MappingsAllPrimitiveTypeCombinationsTest.class);
        suite.addTestSuite(N2_14_XPD2045MapFromMISubprocSingleinstToChildSeqTest.class);

		// Sid ACE-8742 Moved to SCE test feature
		suite.addTestSuite(N2_15_CatchThrowErrorScriptMappingValidationErrorsTest.class);
		suite.addTestSuite(N2_16_XPD3911EnsureConsistentUseOfCorrelationFieldsTest.class);

        // $JUnit-END$
        return suite;
    }
}
