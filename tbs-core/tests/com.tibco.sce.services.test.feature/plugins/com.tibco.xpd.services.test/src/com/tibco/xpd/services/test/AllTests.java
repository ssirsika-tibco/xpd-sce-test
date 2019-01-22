/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.services.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * 
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.services.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(SimpleTest.class);
        /*
         * Disabled as it require a specific UDDI server to be set (UDDI import
         * is no longer supported).
         */
        // suite.addTestSuite(UriFromUddiTest.class);
        suite.addTestSuite(WsdlImportTest.class);
        // $JUnit-END$
        return suite;
    }

}
