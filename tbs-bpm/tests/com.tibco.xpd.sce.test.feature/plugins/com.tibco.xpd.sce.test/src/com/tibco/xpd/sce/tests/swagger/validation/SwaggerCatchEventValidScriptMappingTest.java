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
 * SwaggerCatchEventValidScriptMapping
 * ACE-8641: Test class to check all the valid mapping scenario for catch event. In this test, no validation error should be raised.
 * @author ssirsika
 * @since 25 Oct 2024
 */
public class SwaggerCatchEventValidScriptMappingTest extends TestCase {

	  // @Test
    public void testScriptMapping() throws Exception {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/SwaggerCatchEventValidScriptMappingTest/SwaggerCatchEventValidScriptMapping/",
                        "resources/SwaggerCatchEventValidScriptMappingTest/SwaggerData/",
                        "resources/SwaggerCatchEventValidScriptMappingTest/BOMData/"},
                new String[] { "SwaggerCatchEventValidScriptMapping", "SwaggerData", "BOMData" });

        assertTrue("Failed to load projects from resources/SwaggerCatchEventValidScriptMapping/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("SwaggerCatchEventValidScriptMapping");

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
