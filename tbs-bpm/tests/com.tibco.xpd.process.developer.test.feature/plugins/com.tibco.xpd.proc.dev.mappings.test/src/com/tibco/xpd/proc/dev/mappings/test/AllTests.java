/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.proc.dev.mappings.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.proc.dev.mappings.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$

        suite.addTestSuite(DataFieldAllowedForSendOneWayTest.class);

        // XPath Max Mapped test is not required as the marker could not be
        // reproduced. However, keeping this in place, in case detected later
        // on.

        // suite.addTestSuite(XPathMaxMappedTest.class);
        // $JUnit-END$
        return suite;
    }
}
