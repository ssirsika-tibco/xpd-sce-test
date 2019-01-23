/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.core.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * @since 3
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.core.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(StudioValidationTest.class);
        suite.addTestSuite(SimpleTest.class);
        suite.addTestSuite(ProjectConfigTest.class);
        suite.addTestSuite(SpecialFolderAssetTest.class);
        suite.addTestSuite(SpecialFolderTest.class);
        suite.addTestSuite(ValidationPreferenceUtilTest.class);
        suite.addTestSuite(ActivateCapabilityTest.class);
        suite.addTestSuite(DestinationsTest.class);
        suite.addTestSuite(SpecialFolderUtilTest.class);
        suite.addTestSuite(ProjectUtilTest.class);
        suite.addTestSuite(ResourceDbDerbyTest.class);
        // $JUnit-END$
        return suite;
    }

}
