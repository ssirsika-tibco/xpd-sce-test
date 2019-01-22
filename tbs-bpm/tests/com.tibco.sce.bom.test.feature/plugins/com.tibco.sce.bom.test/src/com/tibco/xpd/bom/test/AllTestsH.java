/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.transform.global_data.XSDBOM_GlobalData02_UniDirectionalComposition;
import com.tibco.xpd.bom.test.transform.global_data.XSDBOM_GlobalData05_BiDirectionalAssociation;
import com.tibco.xpd.bom.test.transform.global_data.XSDBOM_GlobalData07_BiDirectionalAggregation;

/**
 * All tests for testing user defined global data boms
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class AllTestsH {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set H"); //$NON-NLS-1$

        addGlobalDataTests(suite);

        return suite;
    }

    /**
     * @param suite
     */
    private static void addGlobalDataTests(TestSuite suite) {

        /*
         * This test can be deleted as things have changed a lot after this test
         * was created
         */
        // suite.addTestSuite(XSDBOM_GlobalData01_Everything.class);
        /*
         * This test can be uncommented when we support bidirectional
         * composition
         */
        // suite.addTestSuite(XSDBOM_GlobalData03_BiDirectionalComposition.class);
        /*
         * This test can be uncommented when we support uni directional
         * association
         */
        // suite.addTestSuite(XSDBOM_GlobalData04_UniDirectionalAssociation.class);
        /*
         * This test can be uncommented when we support uni directional
         * aggregation
         */
        // suite.addTestSuite(XSDBOM_GlobalData06_UniDirectionalAggregation.class);

        /*
         * Having these tests in the suite as these are supported cases.
         */
        suite.addTestSuite(XSDBOM_GlobalData07_BiDirectionalAggregation.class);
        suite.addTestSuite(XSDBOM_GlobalData05_BiDirectionalAssociation.class);
        suite.addTestSuite(XSDBOM_GlobalData02_UniDirectionalComposition.class);
    }
}
