/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.wsdlgen.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.wsdlgen.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_10_ServiceTaskWsdlGenTest.class);
        // $JUnit-END$
        return suite;
    }
}
