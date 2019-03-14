/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.test;

import com.tibco.xpd.n2.cdm.test.transform.SimpleCdmTransformTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * N2 CDM test suite.
 * 
 * @author Jan Arciuchiewicz
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.n2.cdm.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(SimpleCdmTransformTest.class);
        // $JUnit-END$
        return suite;
    }
}
