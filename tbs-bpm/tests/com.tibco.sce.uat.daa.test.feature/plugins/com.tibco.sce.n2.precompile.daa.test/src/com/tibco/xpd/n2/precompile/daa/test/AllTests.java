/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.precompile.daa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.n2.daa.test.junit.CleanWorkSpaceTest;

/**
 * @author kupadhya
 * 
 */
public class AllTests {

    public static Test suite() {

        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.precompile.daa.test.junit");
        // $JUnit-BEGIN$
        suite.addTestSuite(PreCompileSetUpTest.class);
        suite.addTestSuite(PreCompileProjectsDAAContentsTester.class);
        suite.addTestSuite(CleanWorkSpaceTest.class);

        // $JUnit-END$
        return suite;
    }
}
