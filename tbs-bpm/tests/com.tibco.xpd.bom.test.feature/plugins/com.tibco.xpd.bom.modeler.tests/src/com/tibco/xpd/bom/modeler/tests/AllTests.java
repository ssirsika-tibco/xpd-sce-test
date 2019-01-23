/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * 
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.bom.modeler.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(AssociationTest.class);
        suite.addTestSuite(CreateClass.class);
        suite.addTestSuite(ReParentTest.class);
        suite.addTestSuite(DefaultPrimitiveTypeTest.class);
        // $JUnit-END$
        return suite;
    }

}
