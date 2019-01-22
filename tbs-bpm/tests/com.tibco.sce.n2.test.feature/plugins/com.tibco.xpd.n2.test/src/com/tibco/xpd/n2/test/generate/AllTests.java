package com.tibco.xpd.n2.test.generate;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.n2.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(IncrementalGenTest.class);
        suite.addTestSuite(OnDemandGenTest.class);
        // $JUnit-END$
        return suite;
    }

}
