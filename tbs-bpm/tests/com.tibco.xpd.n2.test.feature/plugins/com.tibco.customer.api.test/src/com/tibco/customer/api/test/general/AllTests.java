/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.customer.api.test.general;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests class for "com.tibco.customer.api.test".
 * 
 * @author sajain
 * @since Sep 27, 2013
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Tests for com.tibco.customer.api.test"); //$NON-NLS-1$

        // $JUnit-BEGIN$

        /*
         * Tests for customer APIs begin.
         */
        suite.addTestSuite(N2_CustomerAPI_IProcessScriptToJavaScriptConverter_Test.class);
        suite.addTestSuite(N2_CustomerAPI_StudioResourceMigrator_Test.class);
        /*
         * Tests for customer APIs end.
         */

        // $JUnit-END$

        return suite;
    }

}
