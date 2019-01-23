/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM42_BOMGenFor_de_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM43_BOMGenFor_brm_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM44_BOMGenFor_busserv_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM45_BOMGenFor_dac_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM46_BOMGenFor_ec_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM47_BOMGenFor_ProcessManagement_wsdlTest;
import com.tibco.xpd.bom.test.transform.imports_wsdl_bom.WSDLBOM48_BOMGenFor_wp_wsdlTest;

/**
 * All tests for testing wsdl to bom generation for api wsdls like brm,
 * bussserv, dac, de, ec, processManagement and wp
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class AllTestsG {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set G"); //$NON-NLS-1$

        addWsdlToBomRoundTripTests(suite);

        return suite;
    }

    /**
     * @param suite
     */
    private static void addWsdlToBomRoundTripTests(TestSuite suite) {

        suite.addTestSuite(WSDLBOM42_BOMGenFor_de_wsdlTest.class);
        suite.addTestSuite(WSDLBOM43_BOMGenFor_brm_wsdlTest.class);
        suite.addTestSuite(WSDLBOM44_BOMGenFor_busserv_wsdlTest.class);
        suite.addTestSuite(WSDLBOM45_BOMGenFor_dac_wsdlTest.class);
        suite.addTestSuite(WSDLBOM46_BOMGenFor_ec_wsdlTest.class);
        suite.addTestSuite(WSDLBOM47_BOMGenFor_ProcessManagement_wsdlTest.class);
        suite.addTestSuite(WSDLBOM48_BOMGenFor_wp_wsdlTest.class);
    }
}
