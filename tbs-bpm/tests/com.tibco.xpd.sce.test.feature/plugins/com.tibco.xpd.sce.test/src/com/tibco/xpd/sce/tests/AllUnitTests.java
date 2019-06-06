/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.ant.tasks.GenerateRascTaskTest;
import com.tibco.xpd.sce.tests.brm.transform.BRMGenUC2FTest;
import com.tibco.xpd.sce.tests.cdm.transform.AllBomCdmTransformTests;
import com.tibco.xpd.sce.tests.ce.destination.BpmProjectMustBeCeTest;
import com.tibco.xpd.sce.tests.ce.destination.NewCeProjectsTest;
import com.tibco.xpd.sce.tests.importmigration.Bpm2CeProjectMigrationTest;
import com.tibco.xpd.sce.tests.javascript.JavascriptArrayValidTest;
import com.tibco.xpd.sce.tests.legacy.wm.WorkListFacadeGenTest;
import com.tibco.xpd.sce.tests.rasc.contributors.BrmModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PeSharedResourceContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.CdmRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.OrgModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PERascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WlfModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.WpModelRascContributorTest;
import com.tibco.xpd.sce.tests.validation.AceAllowCrossClassTypeAndCrosssProjectReferenceTest;
import com.tibco.xpd.sce.tests.validation.AceBomMigrationValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceBomTextPatternRemovalTest;
import com.tibco.xpd.sce.tests.validation.AceCaseAttributesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceCaseServiceValidationRulesTest;
import com.tibco.xpd.sce.tests.validation.AceDateTimeResolutionTest;
import com.tibco.xpd.sce.tests.validation.AceDecPlacesValidationTest;
import com.tibco.xpd.sce.tests.validation.AceMigratedProcessValidationsTest;
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
         * AMX BPM project migration
         */
        suite.addTestSuite(Bpm2CeProjectMigrationTest.class);

        /*
         * Javascript
         */
        suite.addTestSuite(JavascriptArrayValidTest.class);

        /*
         * Validations
         */
        suite.addTestSuite(
                AceAllowCrossClassTypeAndCrosssProjectReferenceTest.class);
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

        /*
         * Miscellaneous
         */
        suite.addTestSuite(NewCeProjectsTest.class);
        suite.addTestSuite(BpmProjectMustBeCeTest.class);

        suite.addTestSuite(BRMGenUC2FTest.class);
        suite.addTestSuite(GenerateRascTaskTest.class);

        /*
         * Related Legacy Studio tests moved to SCE once proved work
         */
        suite.addTestSuite(WorkListFacadeGenTest.class);

        return suite;
    }
}
