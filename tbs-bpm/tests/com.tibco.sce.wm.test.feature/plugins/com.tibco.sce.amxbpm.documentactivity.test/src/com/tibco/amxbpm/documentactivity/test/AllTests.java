/*
 * Copyright (c) TIBCO Software Inc 2014 All rights reserved.
 */

package com.tibco.amxbpm.documentactivity.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.amxbpm.documentactivity.test"); //$NON-NLS-1$

        suite.addTestSuite(DocumentActivityTest.class);

        return suite;
    }
}
