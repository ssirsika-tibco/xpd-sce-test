/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.rest.daa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.n2.daa.test.junit.CleanWorkSpaceTest;

/**
 * Test entry point
 * 
 */
public class AllTests {

    public static Test suite() {

        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.rest.daa.test.junit");
        // $JUnit-BEGIN$
        suite.addTestSuite(RestSetUpTest.class);
        suite.addTestSuite(RestDAAContentsTester.class);
        suite.addTestSuite(CleanWorkSpaceTest.class);

        // $JUnit-END$
        return suite;
    }
}
