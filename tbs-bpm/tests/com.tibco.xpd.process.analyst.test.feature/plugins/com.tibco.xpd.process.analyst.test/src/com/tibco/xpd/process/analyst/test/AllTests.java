/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.process.analyst.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.process.analyst.test.transform.XPDLToMultiXPDLTransformationTest;
import com.tibco.xpd.process.analyst.test.transform.XPDLToOMTransformationTest;

/**
 * 
 * @author sajain
 * @since Aug 23, 2013
 */

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.process.analyst.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(XPDLToMultiXPDLTransformationTest.class);
        suite.addTestSuite(XPDLToOMTransformationTest.class);
        // $JUnit-END$
        return suite;
    }
}
