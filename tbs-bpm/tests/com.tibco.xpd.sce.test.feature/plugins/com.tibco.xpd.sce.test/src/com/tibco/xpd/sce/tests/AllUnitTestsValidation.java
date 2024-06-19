/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.validation.Ace5132DuplicateCatchSignalValidationTest;
import com.tibco.xpd.sce.tests.validation.AceAllowCrossClassTypeAndCrosssProjectReferenceTest;
import com.tibco.xpd.sce.tests.validation.AceBomFactoryValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomMigrationValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomTextPatternRemovalTest;
import com.tibco.xpd.sce.tests.validation.AceCaseAttributesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseCompositionValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseServiceValidationRulesTest;
import com.tibco.xpd.sce.tests.validation.AceDateTimeResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceDecPlacesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceDuplicatePackageIDTest;
import com.tibco.xpd.sce.tests.validation.AceDuplicatePackageNameTest;
import com.tibco.xpd.sce.tests.validation.AceEventHandlerValidationTest;
import com.tibco.xpd.sce.tests.validation.AceIncomingRequestActivityTest;
import com.tibco.xpd.sce.tests.validation.AceLoggerValidTest;
import com.tibco.xpd.sce.tests.validation.AceMigratedProcessValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceNameLengthRestrictionsTest;
import com.tibco.xpd.sce.tests.validation.AceProcessInvalidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AceProcessTemporalDefaultValueRuleTest;
import com.tibco.xpd.sce.tests.validation.AceProcessValidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AcePropertyLimitValidationTest;
import com.tibco.xpd.sce.tests.validation.AceSearchableArrayResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceSearchableInNonCaseClassTest;
import com.tibco.xpd.sce.tests.validation.AceSearchableMaxAllowedCountTest;
import com.tibco.xpd.sce.tests.validation.AceSummaryArrayResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceTemporalDefaultValueRuleTest;
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
import com.tibco.xpd.sce.tests.validation.RepeatingScriptFunctionParameterTest;
import com.tibco.xpd.sce.tests.validation.SearchSummaryValidationTest;
import com.tibco.xpd.sce.tests.validation.ServiceProcessTimerEventValidationTest;
import com.tibco.xpd.sce.tests.validation.TerminalStateValidationTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All SCE unit tests.
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
@SuppressWarnings("nls")
public class AllUnitTestsValidation {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");


        /*
         * Validations
         */
        suite.addTestSuite(Ace5132DuplicateCatchSignalValidationTest.class);
        suite.addTestSuite(AceAllowCrossClassTypeAndCrosssProjectReferenceTest.class);
        suite.addTestSuite(AceBomMigrationValidationsTest.class);
        suite.addTestSuite(AceCaseAttributesValidationTest.class);
        suite.addTestSuite(AceCaseServiceValidationRulesTest.class);
        suite.addTestSuite(AceMigratedProcessValidationsTest.class);
        suite.addTestSuite(AceProcessInvalidDataRulesTest.class);
        suite.addTestSuite(AceProcessTemporalDefaultValueRuleTest.class);
        suite.addTestSuite(AceProcessValidDataRulesTest.class);
        suite.addTestSuite(AceTemporalDefaultValueRuleTest.class);
        suite.addTestSuite(TerminalStateValidationTest.class);
        suite.addTestSuite(AceBomTextPatternRemovalTest.class);
        suite.addTestSuite(AceDecPlacesValidationTest.class);
        suite.addTestSuite(AceDateTimeResolutionTest.class);
        suite.addTestSuite(AceCaseCompositionValidationTest.class);
        suite.addTestSuite(AceBomFactoryValidationsTest.class);
        suite.addTestSuite(AceNameLengthRestrictionsTest.class);
        suite.addTestSuite(AceDuplicatePackageNameTest.class);
        suite.addTestSuite(AceIncomingRequestActivityTest.class);
        suite.addTestSuite(AcePropertyLimitValidationTest.class);
        suite.addTestSuite(SearchSummaryValidationTest.class);
        suite.addTestSuite(AceSearchableArrayResolutionTest.class);
		suite.addTestSuite(AceSearchableInNonCaseClassTest.class);
		suite.addTestSuite(AceSearchableMaxAllowedCountTest.class);
        suite.addTestSuite(AceSummaryArrayResolutionTest.class);
        suite.addTestSuite(ServiceProcessTimerEventValidationTest.class);

		suite.addTestSuite(AceLoggerValidTest.class);
        suite.addTestSuite(RepeatingScriptFunctionParameterTest.class);
		suite.addTestSuite(AceEventHandlerValidationTest.class);

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
        return suite;
    }
}
