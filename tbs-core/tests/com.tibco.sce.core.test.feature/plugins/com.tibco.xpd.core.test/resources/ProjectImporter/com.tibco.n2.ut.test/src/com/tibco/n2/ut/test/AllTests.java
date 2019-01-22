/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.test;

import junit.framework.Test;
import junit.framework.TestSuite;


public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.n2.ut.test"); //$NON-NLS-1$

        suite.addTestSuite(UserActivityTest.class);
        suite.addTestSuite(PageActivityTest.class);

        
        return suite;
    }
}
