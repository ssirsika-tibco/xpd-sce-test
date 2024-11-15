/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.swagger.validation.SwaggerArrayParamValidationTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerCatchEventInvalidScriptMappingTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerCatchEventValidScriptMappingTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerCompositeKeywordValidationTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerInvalidScriptMappingTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerMappingValidationTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerMediaTypeValidationTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerRecursiveSchemaValidationTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerUnsupportedSchemaContentTest;
import com.tibco.xpd.sce.tests.swagger.validation.SwaggerValidScriptMappingTest;
import com.tibco.xpd.sce.tests.validation.AceAllDataTypeAssignments;
import com.tibco.xpd.sce.tests.validation.AceCompositeCaseIdTest;
import com.tibco.xpd.sce.tests.validation.AceDuplicatePackageIDTest;
import com.tibco.xpd.sce.tests.validation.DQLValidationsTest;
import com.tibco.xpd.sce.tests.validation.PSLArrayGenericTypeParamAndReturnTest;
import com.tibco.xpd.sce.tests.validation.PSLBPMScriptsVariableAssignmentValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLFileNameValidatorTest;
import com.tibco.xpd.sce.tests.validation.PSLFunctionNameValidator;
import com.tibco.xpd.sce.tests.validation.PSLFunctionParameterValidation;
import com.tibco.xpd.sce.tests.validation.PSLFunctionTypeCheckingValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLProjectNameValidatorTest;
import com.tibco.xpd.sce.tests.validation.PSLProjectReferenceValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLReturnTypeValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLTargetEnvValidatorTest;
import com.tibco.xpd.sce.tests.validation.PSLValidBPMScriptsVariableAssignmentValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLValidProjectReferenceValidationTest;
import com.tibco.xpd.sce.tests.validation.PSLValidReturnTypeValidationTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All SCE unit tests.
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
@SuppressWarnings("nls")
public class AllUnitTestsValidation2 {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");


        /*
		 * Validations 2
		 */

		suite.addTestSuite(PSLFunctionNameValidator.class);
		suite.addTestSuite(AceDuplicatePackageIDTest.class);

		suite.addTestSuite(PSLFileNameValidatorTest.class);
		suite.addTestSuite(PSLProjectNameValidatorTest.class);
		suite.addTestSuite(PSLTargetEnvValidatorTest.class);
		suite.addTestSuite(PSLFunctionParameterValidation.class);

		suite.addTestSuite(PSLReturnTypeValidationTest.class);
		suite.addTestSuite(PSLProjectReferenceValidationTest.class);
		suite.addTestSuite(PSLValidProjectReferenceValidationTest.class);
		suite.addTestSuite(PSLBPMScriptsVariableAssignmentValidationTest.class);
		suite.addTestSuite(PSLValidBPMScriptsVariableAssignmentValidationTest.class);

		suite.addTestSuite(PSLValidReturnTypeValidationTest.class);

		suite.addTestSuite(PSLFunctionTypeCheckingValidationTest.class);
		suite.addTestSuite(PSLArrayGenericTypeParamAndReturnTest.class);

		suite.addTestSuite(AceCompositeCaseIdTest.class);
		suite.addTestSuite(DQLValidationsTest.class);

		suite.addTestSuite(AceAllDataTypeAssignments.class);

		// Swagger validation tests
		suite.addTestSuite(SwaggerMappingValidationTest.class);
		suite.addTestSuite(SwaggerArrayParamValidationTest.class);
		suite.addTestSuite(SwaggerMediaTypeValidationTest.class);
		suite.addTestSuite(SwaggerCompositeKeywordValidationTest.class);
		suite.addTestSuite(SwaggerRecursiveSchemaValidationTest.class);
		suite.addTestSuite(SwaggerValidScriptMappingTest.class);
		suite.addTestSuite(SwaggerInvalidScriptMappingTest.class);
		suite.addTestSuite(SwaggerCatchEventInvalidScriptMappingTest.class);
		suite.addTestSuite(SwaggerCatchEventValidScriptMappingTest.class);
		suite.addTestSuite(SwaggerUnsupportedSchemaContentTest.class);

		return suite;
    }
}
