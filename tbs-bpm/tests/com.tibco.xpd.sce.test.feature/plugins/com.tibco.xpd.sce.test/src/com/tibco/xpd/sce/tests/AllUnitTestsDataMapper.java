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
import com.tibco.xpd.sce.tests.legacy.datamapper.RestScriptGeneratorInfoProviderStatementTest;
import com.tibco.xpd.sce.tests.legacy.datamapper.RestScriptGeneratorInfoProviderUriTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputArrayBooleanPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputArrayDatePayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputArrayNumberPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputArrayStringPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputBooleanPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputComplexPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputDatePayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputNumberPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerInputStringPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputBooleanArrayPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputBooleanPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputComplexPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputDateArrayPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputDatePayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputNumberArrayPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputNumberPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputStringArrayPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerOutputStringPayloadTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerRequestHeaderParamTypesTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerRequestScriptAppendTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerRequestScriptPrependTest;
import com.tibco.xpd.sce.tests.swagger.datamapper.scripts.SwaggerResponseScriptPrependTest;
import com.tibco.xpd.sce.tests.swaggerschema.SwaggerTaskItemProviderTests;
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
		suite.addTestSuite(RestScriptGeneratorInfoProviderUriTest.class);
		suite.addTestSuite(RestScriptGeneratorInfoProviderStatementTest.class);

		/*
		 * START: Swagger service mapping script generation...
		 */
		suite.addTestSuite(SwaggerInputArrayBooleanPayloadTest.class);
		suite.addTestSuite(SwaggerInputArrayDatePayloadTest.class);
		suite.addTestSuite(SwaggerInputArrayNumberPayloadTest.class);
		suite.addTestSuite(SwaggerInputArrayStringPayloadTest.class);

		suite.addTestSuite(SwaggerInputBooleanPayloadTest.class);
		suite.addTestSuite(SwaggerInputDatePayloadTest.class);
		suite.addTestSuite(SwaggerInputNumberPayloadTest.class);
		suite.addTestSuite(SwaggerInputStringPayloadTest.class);

		suite.addTestSuite(SwaggerOutputBooleanArrayPayloadTest.class);
		suite.addTestSuite(SwaggerOutputBooleanPayloadTest.class);
		suite.addTestSuite(SwaggerOutputDateArrayPayloadTest.class);
		suite.addTestSuite(SwaggerOutputDatePayloadTest.class);
		suite.addTestSuite(SwaggerOutputNumberArrayPayloadTest.class);
		suite.addTestSuite(SwaggerOutputNumberPayloadTest.class);
		suite.addTestSuite(SwaggerOutputStringArrayPayloadTest.class);
		suite.addTestSuite(SwaggerOutputStringPayloadTest.class);

		suite.addTestSuite(SwaggerResponseScriptPrependTest.class);
		suite.addTestSuite(SwaggerRequestScriptPrependTest.class);
		suite.addTestSuite(SwaggerRequestScriptAppendTest.class);
		suite.addTestSuite(SwaggerRequestHeaderParamTypesTest.class);
		suite.addTestSuite(SwaggerInputComplexPayloadTest.class);
		suite.addTestSuite(SwaggerOutputComplexPayloadTest.class);
		/*
		 * ... END: Swagger service mapping script generation...
		 */

		suite.addTestSuite(SwaggerTaskItemProviderTests.class);

        return suite;
    }
}
