/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processinterface.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.processinterface.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(ProcIfcAddOutParamTest.class);
        suite.addTestSuite(ProcIfcNoDestTest.class);
        // $JUnit-END$
        return suite;
    }
}
