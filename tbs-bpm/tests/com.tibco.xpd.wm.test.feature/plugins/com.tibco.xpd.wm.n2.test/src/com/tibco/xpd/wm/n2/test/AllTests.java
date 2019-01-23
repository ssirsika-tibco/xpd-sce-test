package com.tibco.xpd.wm.n2.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.wm.n2.test.schemas.ServiceArchiveDescriptorMatchesSchemaTest;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.n2.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(ServiceArchiveDescriptorMatchesSchemaTest.class);
        // $JUnit-END$
        return suite;
    }

}
