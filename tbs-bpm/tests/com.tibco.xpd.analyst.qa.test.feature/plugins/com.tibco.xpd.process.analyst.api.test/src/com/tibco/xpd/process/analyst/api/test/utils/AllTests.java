/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.process.analyst.api.test.utils;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.tibco.xpd.process.analyst.api.test.utils"); //$NON-NLS-1$
		// $JUnit-BEGIN$
            suite.addTestSuite(LocalPackageProcessInterfaceUtilApiTest.class);
            suite.addTestSuite(DataMappingUtilApiTest.class);
            suite.addTestSuite(ProcessInterfaceUtilApiTest.class);
            suite.addTestSuite(ProcessOrgModelUtilApiTest.class);
            suite.addTestSuite(ReplyActivityUtilApiTest.class);
            suite.addTestSuite(ExtPointUtilApiTest.class);
            suite.addTestSuite(Xpdl2ModelUtilApiTest.class);
            suite.addTestSuite(Xpdl2ProcessorUtilApiTest.class);
            suite.addTestSuite(WebServiceOperationUtilApiTest.class);
            suite.addTestSuite(GatewayObjectUtilApiTest.class);
            suite.addTestSuite(SubProcUtilApiTest.class);
            suite.addTestSuite(ContextualDataReferenceApiTest.class);
            suite.addTestSuite(ProcessRelevantDataUtilApiTest.class);
            suite.addTestSuite(ProcessFeaturesUtilApiTest.class);
            suite.addTestSuite(ProcessScriptUtilApiTest.class);
            suite.addTestSuite(ConceptUtilApiTest.class);
            suite.addTestSuite(ProcessInfoUtilApiTest.class);
            suite.addTestSuite(EMFSearchUtilApiTest.class);
            suite.addTestSuite(XpdlSearchUtilApiTest.class);
            suite.addTestSuite(ThrowErrorEventUtilApiTest.class);
            suite.addTestSuite(EventObjectUtilApiTest.class);
            suite.addTestSuite(TaskObjectUtilTest.class);
            suite.addTestSuite(ProcessUIUtilApiTest.class);
            suite.addTestSuite(ProcessDialogUtilApiTest.class);
            suite.addTestSuite(DestinationUtilApiTest.class);
            suite.addTestSuite(BpmnCatchableErrorUtilApiTest.class);
            suite.addTestSuite(ProcessDataUtilApiTest.class);
            suite.addTestSuite(ReferenceTaskUtilApiTest.class);
            suite.addTestSuite(FileUtilApiTest.class);
            suite.addTestSuite(ProcessDestinationUtilApiTest.class);
		// $JUnit-END$
		return suite;
	}
}
