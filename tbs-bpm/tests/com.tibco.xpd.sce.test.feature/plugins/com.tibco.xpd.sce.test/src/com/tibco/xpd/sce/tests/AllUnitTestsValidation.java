/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.validation.AceAllowCrossClassTypeAndCrosssProjectReferenceTest;
import com.tibco.xpd.sce.tests.validation.AceBomFactoryValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomMigrationValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomTextPatternRemovalTest;
import com.tibco.xpd.sce.tests.validation.AceCaseAttributesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseCompositionValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseServiceValidationRulesTest;
import com.tibco.xpd.sce.tests.validation.AceDateTimeResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceDecPlacesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceDuplicatePackageNameTest;
import com.tibco.xpd.sce.tests.validation.AceIncomingRequestActivityTest;
import com.tibco.xpd.sce.tests.validation.AceMigratedProcessValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceNameLengthRestrictionsTest;
import com.tibco.xpd.sce.tests.validation.AceProcessInvalidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AceProcessTemporalDefaultValueRuleTest;
import com.tibco.xpd.sce.tests.validation.AceProcessValidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AcePropertyLimitValidationTest;
import com.tibco.xpd.sce.tests.validation.AceSearchableArrayResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceSummaryArrayResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceTemporalDefaultValueRuleTest;
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
        suite.addTestSuite(AceSummaryArrayResolutionTest.class);
        suite.addTestSuite(ServiceProcessTimerEventValidationTest.class);

        suite.addTestSuite(RepeatingScriptFunctionParameterTest.class);
        return suite;
    }
}
