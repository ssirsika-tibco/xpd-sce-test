/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

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


        return suite;
    }


}
