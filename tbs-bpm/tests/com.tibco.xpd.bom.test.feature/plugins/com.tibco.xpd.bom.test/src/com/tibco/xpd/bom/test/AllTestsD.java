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
public class AllTestsD {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set D"); //$NON-NLS-1$

        // Kapil: Adding copy paste tests to the suite
        suite.addTest(CopyPasteAllTests.suite());

        return suite;
    }


}
