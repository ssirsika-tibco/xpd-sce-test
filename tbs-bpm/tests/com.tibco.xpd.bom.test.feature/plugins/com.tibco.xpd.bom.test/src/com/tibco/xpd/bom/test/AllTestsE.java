/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.transform.wsdl_coverage_amx_bpm.WSDLCoverageAMXBPMTest;

/**
 * @author wzurek
 * 
 */
public class AllTestsE {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set E"); //$NON-NLS-1$

        addWsdlCoverageAMXBPMTests(suite);

        return suite;
    }

    private static void addWsdlCoverageAMXBPMTests(TestSuite suite) {
        suite.addTestSuite(WSDLCoverageAMXBPMTest.class);
    }

}
