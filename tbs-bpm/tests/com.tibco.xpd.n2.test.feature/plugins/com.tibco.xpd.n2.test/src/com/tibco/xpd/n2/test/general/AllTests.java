/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.general;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.test.general"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_01_JavaScriptStringCompleteTest.class);
        suite.addTestSuite(N2_03_RESTServiceTest.class);
        suite.addTestSuite(N2_05_DynamicOrgMappingsGenerationTest.class);
        suite.addTestSuite(N2_06_AdhocPreconditionScriptContextualDataReferenceApiTest.class);
        suite.addTestSuite(N2_07_BizServiceGenerationFromStartEventTest.class);
        suite.addTestSuite(N2_09_RestServiceOnStartNoneEventsTest.class);

        // $JUnit-END$
        return suite;
    }
}
