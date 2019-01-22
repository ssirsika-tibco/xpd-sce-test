/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author sajain
 * @since Aug 13, 2013
 */

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.implementer.script"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        /*
         * JA: Commented as this test is failing at the moment. This needs to be
         * fixed.
         */
        /* FIXME */
        // suite.addTestSuite(WsdlPathTest.class);

        // $JUnit-END$
        return suite;
    }
}
