/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsA {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.newtests"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_01_SubProcessXSDAnyUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_02_SubProcessXSDAnyTypeUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_03_N2JavaServiceValidMappingTest.class);
        suite.addTestSuite(N2_04_SubProcessXSDAnySimpleTypeUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_05_XSDAnyTypeUserDefMappingsTest.class);
        suite.addTestSuite(N2_06_N2WebSvcAPIJavaScriptValidMappingsTest.class);
        suite.addTestSuite(N2_07_MappingsAllPrimitiveTypeCombinationsTest.class);
        suite.addTestSuite(N2_08_N2WebSvcJSValidEnumMappingsTest.class);
        suite.addTestSuite(N2_09_XSDAnySimpleTypeSimpleMappingsTest.class);
        suite.addTestSuite(N2_10_UnionAssignmentsInScriptTaskTest.class);
        suite.addTestSuite(N2_11_N2WebServiceXPathValidMappingsTest.class);
        suite.addTestSuite(N2_12_N2JavaServiceInvalidMappingsTest.class);
        suite.addTestSuite(N2_13_N2WebServiceXPathInvalidMappingsTest.class);
        suite.addTestSuite(N2_14_XPD2045MapFromMISubprocSingleinstToChildSeqTest.class);
        suite.addTestSuite(N2_15_CatchThrowErrorScriptMappingValidationErrorsTest.class);
        suite.addTestSuite(N2_16_XPD3911EnsureConsistentUseOfCorrelationFieldsTest.class);
        suite.addTestSuite(N2_17_SubProcessXSDAnySimpleTypeMappingsTest.class);
        suite.addTestSuite(N2_18_N2WebSvcJavaScriptInvalidMappingsTest.class);
        // $JUnit-END$
        return suite;
    }
}
