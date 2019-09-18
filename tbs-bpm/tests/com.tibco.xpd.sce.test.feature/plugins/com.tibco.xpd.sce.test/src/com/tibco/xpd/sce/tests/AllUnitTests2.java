/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.ant.tasks.GenerateRascTaskTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelCaseDataOperationActivityTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelDataFieldDescriptorTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelGlobalSignalsTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelIncomingRequestActivityTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelLocalSignalsTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelSharedResourceTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelSubProcessConversionTest;
import com.tibco.xpd.sce.tests.brm.transform.BRMGenUC2FTest;
import com.tibco.xpd.sce.tests.ce.destination.BpmProjectMustBeCeTest;
import com.tibco.xpd.sce.tests.ce.destination.NewCeProjectsTest;
import com.tibco.xpd.sce.tests.importmigration.Bpm2CeProjectMigrationTest;
import com.tibco.xpd.sce.tests.importmigration.CaseSignalMigrationTest;
import com.tibco.xpd.sce.tests.importmigration.GlobalSignalMigrationTest;
import com.tibco.xpd.sce.tests.importmigration.JsToDataMapperMigrationsTest;
import com.tibco.xpd.sce.tests.importmigration.ScriptMigrationTests;
import com.tibco.xpd.sce.tests.importmigration.SystemActionMigrationTest;
import com.tibco.xpd.sce.tests.legacy.wm.WorkListFacadeGenTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Separate the failing SCE unit tests.
 *
 * @author pwatson
 * @since 15 July 2019
 */
@SuppressWarnings("nls")
public class AllUnitTests2 {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.sce.tests");

        /*
         * AMX BPM project migration
         */
        suite.addTestSuite(Bpm2CeProjectMigrationTest.class);
        suite.addTestSuite(ScriptMigrationTests.class);
        suite.addTestSuite(NewCeProjectsTest.class);
        suite.addTestSuite(GenerateRascTaskTest.class);
        suite.addTestSuite(SystemActionMigrationTest.class);
        suite.addTestSuite(GlobalSignalMigrationTest.class);
        suite.addTestSuite(JsToDataMapperMigrationsTest.class);
        suite.addTestSuite(CaseSignalMigrationTest.class);

        /*
         * BPEL transformation tests
         */
        suite.addTestSuite(BpelSharedResourceTest.class);
        suite.addTestSuite(BpelDataFieldDescriptorTest.class);
        suite.addTestSuite(BpelGlobalSignalsTest.class);
        suite.addTestSuite(BpelLocalSignalsTest.class);
        suite.addTestSuite(BpelSubProcessConversionTest.class);
        suite.addTestSuite(BpelIncomingRequestActivityTest.class);
        suite.addTestSuite(BpelCaseDataOperationActivityTest.class);

        /*
         * Miscellaneous
         */
        suite.addTestSuite(BpmProjectMustBeCeTest.class);
        suite.addTestSuite(BRMGenUC2FTest.class);

        /*
         * Related Legacy Studio tests moved to SCE once proved work
         */
        suite.addTestSuite(WorkListFacadeGenTest.class);

        return suite;
    }
}
