/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * In ACE all process data (fields/params) are wrapped in a special "data" object in scripts
 * 
 * The associated test project (DataWrappingTests) uses this data object in all possible scripts, so if something goes
 * wrong and the fields don't get wrapped in a "data" object then there will be problem markers and the test will fail.
 * 
 * @author aallway
 * @since 05 Jun 2019
 */
public class AceDataWrapperScriptObjectTest extends TestCase {

    // @Test
    public void testDataIsWrappedInAllScriptScenarios() {
        ProjectImporter projectImporter = importMainTestProjects();

        try {
            assertFalse("DataWrappingTests" //$NON-NLS-1$
                    + " project should not have any ERROR level problem markers", //$NON-NLS-1$
                    TestUtil.hasErrorProblemMarker(
                            ResourcesPlugin.getWorkspace().getRoot().getProject("DataWrappingTests"), //$NON-NLS-1$
                            true));

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Import all projects from test plugin resources for the main test
     * 
     * @return the project importer
     */
    private ProjectImporter importMainTestProjects() {
        /*
         * Import and mgirate the project
         */

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/WrappedProcessDataTests/DataWrappingData/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWappingGlobalSignal/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingREST/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingTests/" }, //$NON-NLS-1$
                new String[] { "DataWrappingData", //$NON-NLS-1$
                        "DataWappingGlobalSignal", //$NON-NLS-1$
                        "DataWrappingREST", //$NON-NLS-1$
                        "DataWrappingTests" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from resources/WrappedProcessDataTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }



}
