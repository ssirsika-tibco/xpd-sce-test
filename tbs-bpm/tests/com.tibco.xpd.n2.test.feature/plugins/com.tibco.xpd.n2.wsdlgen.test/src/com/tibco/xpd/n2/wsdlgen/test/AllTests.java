/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.wsdlgen.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.wsdlgen.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_01_ProcessNestedBOMTestCase.class);
        /* N2_02 test was removed under XPD-5982 as it was no longer valid. */
        suite.addTestSuite(N2_03_ProcIfcBPMDestTest.class);
        suite.addTestSuite(N2_04_WsdlGenEnumValuesTest.class);
        suite.addTestSuite(N2_05_AbsoluteURINotAllowedTest.class);
        // commenting this out as it doesn't do anything
        // suite.addTestSuite(N2_06_ValidateGeneratedWsdlIsDerivedTest.class);
        suite.addTestSuite(N2_07_ValidateNonNCNAMEInProcessTest.class);
        suite.addTestSuite(N2_08_ExternalRefOneWayWsdlTest.class);
        suite.addTestSuite(N2_09_SimpleTypeOneWayWsdlTest.class);
        suite.addTestSuite(N2_11_XPD1024ExtPrjTest.class);
        suite.addTestSuite(N2_12_DocLiteralBOMandSimpleTypesAPIActTest.class);
        suite.addTestSuite(N2_13_BOMExtRefBOMTest.class);
        suite.addTestSuite(N2_14_MultOpsUnsupportedNegValidationTest.class);
        suite.addTestSuite(N2_15_ProcIfNestedBOMTestCase.class);
        /*
         * XPD-7009: Saket: Moved to AllTestsB.
         */
        // suite.addTestSuite(N2_10_ServiceTaskWsdlGenTest.class);
        // $JUnit-END$
        return suite;
    }
}
