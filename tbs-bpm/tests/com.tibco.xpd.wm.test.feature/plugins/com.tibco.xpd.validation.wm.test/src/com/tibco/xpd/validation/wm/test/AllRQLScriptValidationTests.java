package com.tibco.xpd.validation.wm.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllRQLScriptValidationTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.validation.wm.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(RQLScriptInValidNavigationValidationTest.class);
        suite.addTestSuite(RQLScriptValidationTest.class);
        suite.addTestSuite(RQLScriptValidationUC2Test.class);
        suite.addTestSuite(RQLScriptValidationUC3Test.class);
        suite.addTestSuite(RQLScriptValidNavigationValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}
