/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.test.datamapper;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.tibco.xpd.n2.mappings.validation.test.datamapper"); //$NON-NLS-1$
		// $JUnit-BEGIN$
            suite.addTestSuite(ProcessDataMapperScriptGenerationTests.class);
		// $JUnit-END$
		return suite;
	}
}
