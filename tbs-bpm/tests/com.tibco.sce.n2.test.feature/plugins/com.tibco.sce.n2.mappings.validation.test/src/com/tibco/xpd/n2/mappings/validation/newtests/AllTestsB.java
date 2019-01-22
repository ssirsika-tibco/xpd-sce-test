/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.mappings.validation.newtests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.mappings.validation.newtests"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_19_PojoXSDAllTypesInvalidSimpleMappingsTest.class);
        suite.addTestSuite(N2_20_XPD1491XPathXsdanyMappingsTest.class);
        suite.addTestSuite(N2_21_XSDAnyAttributeUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_22_N2WebSvcUserDefinedScriptUnionMappingsTest.class);
        suite.addTestSuite(N2_23_XSDAnySimpleTypeUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_24_N2WebSvcJSEnumerationMappingsTest.class);
        suite.addTestSuite(N2_25_N2WebSvcAPIJavaScriptInvalidMappingsTest.class);
        suite.addTestSuite(N2_26_SubProcessXSDAnyTypeSimpleMappingsTest.class);
        suite.addTestSuite(N2_27_SubProcessXSDAnyAttributeSimpleMappingsTest.class);
        suite.addTestSuite(N2_28_N2WebSvcSimpleJavaScriptUnionMappingsTest.class);
        suite.addTestSuite(N2_29_XSDAnySimpleMappingsTest.class);
        suite.addTestSuite(N2_30_N2SubProcessMappingsValidationTest.class);
        suite.addTestSuite(N2_31_N2WebSvcJavaScriptValidMappingsTest.class);
        suite.addTestSuite(N2_32_XSDAnyTypeSimpleMappingsTest.class);
        suite.addTestSuite(N2_33_SubProcessXSDAnyAttributeUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_34_XSDAnyUserDefinedMappingsTest.class);
        suite.addTestSuite(N2_35_XPD3821MissingCorrelationMappingNamespaceRuleTest.class);
        suite.addTestSuite(N2_36_SubProcessXSDAnySimpleMappingsTest.class);
        suite.addTestSuite(N2_37_XSDAnyAttributeSimpleMappingsTest.class);
        suite.addTestSuite(N2_38_XPD3773JavaScriptToXPathConversionTest.class);
        suite.addTestSuite(N2_39_LocalCatchSignalEventMappingValidationsTest.class);
        suite.addTestSuite(N2_40_GlobalSignalReferencingMappingValidationTest.class);

        // $JUnit-END$
        return suite;
    }
}
