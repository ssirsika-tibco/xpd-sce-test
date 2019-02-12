/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.analyst.qa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.analyst.qa.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(BPMN10ValidationTest.class);
        suite.addTestSuite(NimbusImportTest.class);
        suite.addTestSuite(ProcessStartInvalidRuleTest.class);
        suite.addTestSuite(ValidateJavaScriptTest.class);
        suite.addTestSuite(TestConceptPathConvert.class);
        suite.addTestSuite(ReadonlyParamsWarningTest.class);
        suite.addTestSuite(ErrorEventsSyncTest.class);
        suite.addTestSuite(RenameParameterTest.class);
        suite.addTestSuite(ParticipantAsFieldTest.class);
        suite.addTestSuite(PerformerToStringMappingValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}
