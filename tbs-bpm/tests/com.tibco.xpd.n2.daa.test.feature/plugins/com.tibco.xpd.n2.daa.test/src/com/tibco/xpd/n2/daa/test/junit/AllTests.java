package com.tibco.xpd.n2.daa.test.junit;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.daa.test.junit"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(CleanWorkSpaceTest.class);
        // $JUnit-END$
        return suite;
    }

}
