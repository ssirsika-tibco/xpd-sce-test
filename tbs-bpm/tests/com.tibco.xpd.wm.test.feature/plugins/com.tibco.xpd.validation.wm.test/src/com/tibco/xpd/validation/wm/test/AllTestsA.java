package com.tibco.xpd.validation.wm.test;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AllTestsA {

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
        suite.addTestSuite(N2JavaScriptAssignmentValidationTest.class);
        suite.addTestSuite(N2JavaScriptComparisonOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptDivisionOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptEqualityOperatorValidatorTest.class);
        suite.addTestSuite(N2JavaScriptInequalityOperatorValidatorTest.class);
        suite.addTestSuite(N2JavaScriptPlusOperatorValidatorTest.class);
        suite.addTestSuite(N2JavaScriptMinusOperationValidationTest.class);
        suite.addTestSuite(N2JavaScriptMultiplicationOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptExponentialOperatorValidationTest.class);
        suite.addTestSuite(EnumerationScriptValidationTest.class);
        suite.addTestSuite(N2JavaScriptMethodParamsValidationTest.class);
        suite.addTestSuite(N2JavaScriptUnaryOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptModOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptIdentifierValidatorTest.class);
        suite.addTestSuite(N2JavaScriptStaticCallsValidationTest.class);

        // $JUnit-END$
        return suite;
    }
}
