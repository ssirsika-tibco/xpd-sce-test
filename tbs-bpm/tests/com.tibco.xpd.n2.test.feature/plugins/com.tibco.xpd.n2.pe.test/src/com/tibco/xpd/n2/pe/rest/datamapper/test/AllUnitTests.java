/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.rest.datamapper.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All REST Schema UI tests.
 * 
 * @author nwilson
 * @since 6 Feb 2015
 */
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for com.tibco.xpd.n2.pe.rest.datamapper.test"); //$NON-NLS-1$

		// Sid ACE-8742 - Moved tests to SCE test feature
		// suite.addTestSuite(RestScriptGeneratorInfoProviderUriTest.class);
		// suite.addTestSuite(RestScriptGeneratorInfoProviderStatementTest.class);

        return suite;
    }

}
