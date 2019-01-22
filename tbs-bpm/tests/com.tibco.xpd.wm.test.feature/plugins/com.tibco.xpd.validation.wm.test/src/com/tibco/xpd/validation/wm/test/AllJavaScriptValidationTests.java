package com.tibco.xpd.validation.wm.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaScriptValidationTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.validation.wm.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2JavaScriptAssignmentValidationTest.class);
        suite.addTestSuite(N2JavaScriptComparisonOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptDivisionOperatorValidationTest.class);
        suite.addTestSuite(N2JavaScriptEqualityOperatorValidatorTest.class);
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
        suite.addTestSuite(N2JavaScriptBitwiseValidationTest.class);
        suite.addTestSuite(N2JavaScriptReadOnlyAssignmentValidationTest.class);
        suite.addTestSuite(N2JavaScriptGlobalMethodsValidationTest.class);
        suite.addTestSuite(WorkItemScriptValidationTest.class);
        suite.addTestSuite(N2JavaScriptPOJOValidationTest.class);
        suite.addTestSuite(N2JavaScriptPrimitiveTypesValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}
