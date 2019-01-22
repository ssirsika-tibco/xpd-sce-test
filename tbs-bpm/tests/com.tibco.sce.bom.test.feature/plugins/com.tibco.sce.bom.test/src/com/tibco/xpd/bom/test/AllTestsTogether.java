/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Convenience test runner class to run all BOM test sets.
 * 
 * @author jarciuch
 * 
 */
public class AllTestsTogether {

    public static Test suite() {
        TestSuite suite = new TestSuite("All Test for com.tibco.xpd.bom.test"); //$NON-NLS-1$

        suite.addTest(AllTestsA.suite());
        suite.addTest(AllTestsB.suite());
        suite.addTest(AllTestsC.suite());
        suite.addTest(AllTestsD.suite());
        suite.addTest(AllTestsE.suite());
        suite.addTest(AllTestsF.suite());
        suite.addTest(AllTestsG.suite());
        suite.addTest(AllTestsH.suite());

        return suite;
    }

}
