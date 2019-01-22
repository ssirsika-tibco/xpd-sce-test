/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.globaldata;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.validation.wm.globaldata"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        /*
         * bharti: commenting this test as there have been several changes in
         * the scripting content assist from the initial work - like changes to
         * createCriteria methods being moved from DataUtil to cac classes,
         * removing create/update/delete methods from cac and case ref to global
         * data task etc.
         * 
         * Added two new tests for CAC validations and case ref validations that
         * are supposed to have no validation problems in the script
         */
        // suite.addTestSuite(GDCACAndCaseRefValidationTest.class);
        suite.addTestSuite(CACValidationsTest.class);
        suite.addTestSuite(CaseRefValidationsTest.class);
        suite.addTestSuite(CaseRefInheritanceAssignmentsTest.class);
        suite.addTestSuite(CaseRefToObjectOrBomTypesValidationTest.class);
        suite.addTestSuite(CriteriaAndDQLContextValidationsTest.class);
        suite.addTestSuite(CaseServiceValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}
