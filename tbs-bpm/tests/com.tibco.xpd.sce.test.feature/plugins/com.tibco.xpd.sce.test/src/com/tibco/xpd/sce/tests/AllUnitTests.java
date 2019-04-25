/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.sce.tests.brm.transform.BRMGenUC2FTest;
import com.tibco.xpd.sce.tests.cdm.transform.AllBomCdmTransformTests;
import com.tibco.xpd.sce.tests.ce.destination.BpmProjectMustBeCeTest;
import com.tibco.xpd.sce.tests.ce.destination.NewCeProjectsTest;
import com.tibco.xpd.sce.tests.importmigration.Bpm2CeProjectMigrationTest;
import com.tibco.xpd.sce.tests.rasc.contributors.BrmModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.CdmRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.OrgModelRascContributorTest;
import com.tibco.xpd.sce.tests.rasc.contributors.PERascContributorTest;
import com.tibco.xpd.sce.tests.validation.AceBomMigrationValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceProcessInvalidDataRulesTest;
import com.tibco.xpd.sce.tests.validation.AceMigratedProcessValidationsTest;
import com.tibco.xpd.sce.tests.validation.AceProcessValidDataRulesTest;

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

        /*
         * BOM->CDM transformation
         */
        suite.addTest(AllBomCdmTransformTests.suite());

        /*
         * AMX BPM project migration
         */
        suite.addTestSuite(Bpm2CeProjectMigrationTest.class);

        /*
         * Validations
         */
        suite.addTestSuite(AceBomMigrationValidationsTest.class);
        suite.addTestSuite(AceMigratedProcessValidationsTest.class);
        suite.addTestSuite(AceProcessInvalidDataRulesTest.class);
        suite.addTestSuite(AceProcessValidDataRulesTest.class);

        /*
         * Miscellaneous
         */
        suite.addTestSuite(NewCeProjectsTest.class);
        suite.addTestSuite(BpmProjectMustBeCeTest.class);

        suite.addTestSuite(BRMGenUC2FTest.class);


        return suite;
    }
}
