package com.tibco.xpd.validation.wm.test;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.validation.wm.test") { //$NON-NLS-1$
                    /**
                     * @see junit.framework.TestSuite#runTest(junit.framework.Test,
                     *      junit.framework.TestResult)
                     * 
                     * @param test
                     * @param result
                     */
                    @Override
                    public void runTest(Test test, TestResult result) {
                        try {
                            System.out.println("Running test: " //$NON-NLS-1$
                                    + test.toString() + "..."); //$NON-NLS-1$
                            System.out
                                    .println("------------------------------------------------------------------------------"); //$NON-NLS-1$

                            super.runTest(test, result);
                        } finally {
                            System.out
                                    .println("=============================================================================="); //$NON-NLS-1$

                        }

                    }
                };
        // $JUnit-BEGIN$
        suite.addTestSuite(N2JavaScriptBitwiseValidationTest.class);
        suite.addTestSuite(N2JavaScriptReadOnlyAssignmentValidationTest.class);
        suite.addTestSuite(N2JavaScriptGlobalMethodsValidationTest.class);
        suite.addTestSuite(WorkItemScriptValidationTest.class);
        suite.addTestSuite(N2JavaScriptPOJOValidationTest.class);
        suite.addTestSuite(N2JavaScriptPrimitiveTypesValidationTest.class);
        suite.addTestSuite(XPD2493UserDefPrimitveCoverageTest.class);
        suite.addTestSuite(N2_DynamicIdentifierMappingTest.class);

        suite.addTestSuite(RQLScriptValidationTest.class);
        suite.addTestSuite(RQLScriptValidationUC2Test.class);
        suite.addTestSuite(RQLScriptValidationUC3Test.class);
        suite.addTestSuite(RQLScriptInValidNavigationValidationTest.class);
        suite.addTestSuite(RQLScriptValidNavigationValidationTest.class);
        suite.addTestSuite(XPD2098TestScripting11Test.class);
        suite.addTestSuite(XPD2338BDSScriptTest.class);
        suite.addTestSuite(WorkListFacadeValidationTest.class);
        suite.addTestSuite(WorkItemAttributeMappingValidationsTest.class);
        // $JUnit-END$
        return suite;
    }
}
