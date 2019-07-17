/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests;

import com.tibco.xpd.ant.tasks.GenerateRascTaskTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelDataFieldDescriptorTest;
import com.tibco.xpd.sce.tests.bpel.transform.BpelSharedResourceTest;
import com.tibco.xpd.sce.tests.brm.transform.BRMGenUC2FTest;
import com.tibco.xpd.sce.tests.ce.destination.BpmProjectMustBeCeTest;
import com.tibco.xpd.sce.tests.ce.destination.NewCeProjectsTest;
import com.tibco.xpd.sce.tests.importmigration.Bpm2CeProjectMigrationTest;
import com.tibco.xpd.sce.tests.importmigration.ScriptMigrationTests;
import com.tibco.xpd.sce.tests.javascript.AceDataWrapperScriptObjectTest;
import com.tibco.xpd.sce.tests.javascript.AceProcessDataWrapperMappingsTest;
import com.tibco.xpd.sce.tests.legacy.wm.WorkListFacadeGenTest;
import com.tibco.xpd.sce.tests.validation.AceGlobalSignalDataMapperTest;

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

        suite.addTestSuite(AceGlobalSignalDataMapperTest.class);
        suite.addTestSuite(NewCeProjectsTest.class);
        suite.addTestSuite(GenerateRascTaskTest.class);
        suite.addTestSuite(AceDataWrapperScriptObjectTest.class);
        suite.addTestSuite(AceProcessDataWrapperMappingsTest.class);

        /*
         * BPEL transformation tests
         */
        suite.addTestSuite(BpelSharedResourceTest.class);
        suite.addTestSuite(BpelDataFieldDescriptorTest.class);

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
