package com.tibco.xpd.resources.qa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.tibco.xpd.resources.qa.test"); //$NON-NLS-1$
		//$JUnit-BEGIN$		
		suite.addTestSuite(NegativeTest.class);
		suite.addTestSuite(ProjectConfigTest.class);
		suite.addTestSuite(SpecialFoldersTest.class);		
		suite.addTestSuite(AssetTypeTest.class);
		//$JUnit-END$
		return suite;
	}
}
