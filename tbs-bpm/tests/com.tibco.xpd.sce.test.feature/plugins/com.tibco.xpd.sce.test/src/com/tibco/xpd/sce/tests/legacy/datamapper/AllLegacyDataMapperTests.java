/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.legacy.datamapper;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllLegacyDataMapperTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.sce.tests.legacy.datamapper"); //$NON-NLS-1$
        // $JUnit-BEGIN$

        suite.addTestSuite(ProcessDataMapperScriptGenerationTests.class);
        suite.addTestSuite(N2_49_DataMapperValidationMarkerTest.class);
        suite.addTestSuite(N2_49bXPD_7735MappingParentAndChild1Test.class);
        suite.addTestSuite(N2_49c_XPD_7735_MappingParentAndChildTest2Test.class);

		suite.addTestSuite(RestScriptGeneratorInfoProviderStatementTest.class);
		suite.addTestSuite(RestScriptGeneratorInfoProviderUriTest.class);

        // $JUnit-END$
        return suite;
    }
}
