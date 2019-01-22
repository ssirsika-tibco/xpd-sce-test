package com.tibco.xpd.bom.qa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.tibco.xpd.bom.qa.test"); //$NON-NLS-1$
		//$JUnit-BEGIN$
		suite.addTestSuite(CaseOneTest.class);
		//$JUnit-END$
		return suite;
	}
}
