/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.cdm.transform.AllBomCdmTransformTests;
import com.tibco.xpd.sce.tests.javascript.CaseDataScriptGenerationTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataTaskInvalidTest;
import com.tibco.xpd.sce.tests.javascript.CaseDataTaskTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptArrayInvalidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptArrayValidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptDateValidTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptNumberValidTest;
import com.tibco.xpd.sce.tests.rasc.contributors.BrmModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.CdmRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.GlobalSignalRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.OrgModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PERascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PeSharedResourceContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WlfModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WpModelRascContributorTest;
import com.tibco.xpd.sce.tests.validation.AceAllowCrossClassTypeAndCrosssProjectReferenceTest;
import com.tibco.xpd.sce.tests.validation.AceBomFactoryValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomMigrationValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomTextPatternRemovalTest;
import com.tibco.xpd.sce.tests.validation.AceCaseAttributesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseCompositionValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseServiceValidationRulesTest;
import com.tibco.xpd.sce.tests.validation.AceDateTimeResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceDecPlacesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceIntegerWorkItemAttributeMappingTest;
import com.tibco.xpd.sce.tests.validation.AceMigratedProcessValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceNameLengthRestrictionsTest;
import com.tibco.xpd.sce.tests.validation.AceProcessInvalidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AceProcessTemporalDefaultValueRuleTest;
import com.tibco.xpd.sce.tests.validation.AceProcessValidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AceTemporalDefaultValueRuleTest;
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
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");

        /*
         * RASC contributors
         */
        suite.addTestSuite(OrgModelRascContributorTest.class);
        suite.addTestSuite(PERascContributorTest.class);
        suite.addTestSuite(GlobalSignalRascContributorTest.class);
        suite.addTestSuite(CdmRascContributorTest.class);
        suite.addTestSuite(BrmModelRascContributorTest.class);
        suite.addTestSuite(WpModelRascContributorTest.class);
        suite.addTestSuite(WlfModelRascContributorTest.class);
        suite.addTestSuite(PeSharedResourceContributorTest.class);

        /*
         * BOM->CDM transformation
         */
        suite.addTest(AllBomCdmTransformTests.suite());


        /*
         * Javascript
         */
        suite.addTestSuite(JavascriptArrayValidTest.class);
        suite.addTestSuite(JavascriptArrayInvalidTest.class);
        suite.addTestSuite(JavascriptDateValidTest.class);
        suite.addTestSuite(JavascriptNumberValidTest.class);
        suite.addTestSuite(CaseDataTaskTest.class);
        suite.addTestSuite(CaseDataTaskInvalidTest.class);
        suite.addTestSuite(CaseDataScriptGenerationTest.class);

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
        suite.addTestSuite(AceIntegerWorkItemAttributeMappingTest.class);
        suite.addTestSuite(AceNameLengthRestrictionsTest.class);

        return suite;
    }
}
