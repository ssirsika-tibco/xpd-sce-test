/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.builder.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author njpatel
 * 
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.core.builder.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        /*
         * Builder test has been disabled as it fails intermittently. This is
         * due to it not waiting long enough, despite the waits, for the builder
         * to finish and thus reporting failure. As this test doesn't really
         * test any of our API it may be worth considering removing it
         * permanently.
         */
        // suite.addTestSuite(BuilderTest.class);
        // $JUnit-END$
        return suite;
    }

}
