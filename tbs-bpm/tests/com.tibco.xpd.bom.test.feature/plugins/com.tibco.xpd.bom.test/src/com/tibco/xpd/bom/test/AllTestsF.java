/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import com.tibco.xpd.bom.test.copypaste.CopyPasteAllTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * 
 */
public class AllTestsF {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set F"); //$NON-NLS-1$


        // Sid XPD-8351 - Already in AllTestsD
        suite.addTest(CopyPasteAllTests.suite());

        return suite;
    }


}
