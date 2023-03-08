/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.javascript.AceGlobalSignalMappingGenerationTest;
import com.tibco.xpd.sce.tests.javascript.AceLocalSignalMappingGenerationTest;
import com.tibco.xpd.sce.tests.javascript.AceProcessAndWMScriptMappingTest;
import com.tibco.xpd.sce.tests.javascript.AceProcessDataWrapperMappingsTest;
import com.tibco.xpd.sce.tests.javascript.AceRestInputEmptyObjectExclusionsTest;
import com.tibco.xpd.sce.tests.legacy.datamapper.AllLegacyDataMapperTests;
import com.tibco.xpd.sce.tests.validation.AceGlobalSignalDataMapperTest;
import com.tibco.xpd.sce.tests.validation.AceImplicitTypeConversionTest;
import com.tibco.xpd.sce.tests.validation.AceIntegerWorkItemAttributeMappingTest;
import com.tibco.xpd.sce.tests.validation.AceJSMappingsNotSupportedValidationTest;
import com.tibco.xpd.sce.tests.validation.AceLocalSignalDataMapperNegativeTest;
import com.tibco.xpd.sce.tests.validation.AceLocalSignalDataMapperTest;
import com.tibco.xpd.sce.tests.validation.AceMultiInstanceSubProcMappingValidationTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All SCE unit tests.
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
@SuppressWarnings("nls")
public class AllUnitTestsDataMapper {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");

        suite.addTest(AllLegacyDataMapperTests.suite());
        suite.addTestSuite(AceProcessAndWMScriptMappingTest.class);
        suite.addTestSuite(AceProcessDataWrapperMappingsTest.class);
        suite.addTestSuite(AceGlobalSignalMappingGenerationTest.class);
        suite.addTestSuite(AceGlobalSignalDataMapperTest.class);
        suite.addTestSuite(AceLocalSignalDataMapperTest.class);
        suite.addTestSuite(AceLocalSignalDataMapperNegativeTest.class);
        suite.addTestSuite(AceLocalSignalMappingGenerationTest.class);
        suite.addTestSuite(AceIntegerWorkItemAttributeMappingTest.class);
        suite.addTestSuite(AceMultiInstanceSubProcMappingValidationTest.class);
        suite.addTestSuite(AceImplicitTypeConversionTest.class);
        suite.addTestSuite(AceJSMappingsNotSupportedValidationTest.class);
        suite.addTestSuite(AceRestInputEmptyObjectExclusionsTest.class);
        return suite;
    }
}
