/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.swagger.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;

import junit.framework.TestCase;

/**
 * SwaggerValidScriptMappingTest
 * ACE-8641: Test class to check all the valid mapping scenario. In this test, no validation error should be raised.
 * @author ssirsika
 * @since 22 Oct 2024
 */
public class SwaggerValidScriptMappingTest extends TestCase {

	  // @Test
    public void testScriptMapping() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/SwaggerValidScriptMappingTest/SwaggerValidScriptMapping/",
                        "resources/SwaggerValidScriptMappingTest/SwaggerData/",
                        "resources/SwaggerValidScriptMappingTest/BOMData/"},
                new String[] { "SwaggerValidScriptMapping", "SwaggerData", "BOMData" });

        assertTrue("Failed to load projects from resources/SwaggerValidScriptMapping/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("SwaggerValidScriptMapping");

            // we expect no validation markers
            TestUtil.outputErrorMarkers(project, true);
            assertTrue(TestUtil.getErrorMarkers(project, true, "").isEmpty());
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

}
