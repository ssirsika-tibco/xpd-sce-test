/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.n2.brm.api.WorkItemScriptOperation;
import com.tibco.n2.brm.api.WorkModel;
import com.tibco.n2.brm.api.WorkModelScript;
import com.tibco.n2.brm.workmodel.DocumentRoot;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.n2.brm.BRMGenerator;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * In ACE the old "Process" and "WorkManagerFactory" static JS classes are now wrapped up in as "bpm.process" and
 * "bpm.workManager" class properties respectively.
 * 
 * @author aallway
 * @since 02 Jul 2019
 */
public class AceProcessAndWMScriptTest extends TestCase {

    // @Test
    public void testDataIsWrappedInAllScriptScenarios() {
        ProjectImporter projectImporter = importMainTestProjects();
        try {
            IProject mapperProject =
                    ResourcesPlugin.getWorkspace().getRoot().getProject("ProcessAndWorkManagerMappingsTest"); //$NON-NLS-1$

            Process process = ProcessUIUtil.getProcesById("_QH-ZoJ5PEemBuMq6mmcR8A"); //$NON-NLS-1$

            assertNotNull(
                    "Cannot find test process 'ProcessAndWorkManagerMappingsTest-Process' (_QH-ZoJ5PEemBuMq6mmcR8A)", //$NON-NLS-1$
                    process);

            checkProcessDataMapperScriptGeneration(process);

            checkWorkItemAttributeMapperScriptGeneration(mapperProject);
        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }
    }

    /**
     * Test process data mapper script task. (checking that the various changes from AMX BPM EMF based scripting have
     * been made to deal with the JS based ACE scripting).
     * 
     * @param process
     */
    private void checkProcessDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_cZl8QZ5PEemBuMq6mmcR8A"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Script Task' (_cZl8QZ5PEemBuMq6mmcR8A)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        TaskScript taskScript = ((Task) activity.getImplementation()).getTaskScript();

        assertNotNull("Cannot find test TaskScript in 'Script Task' (_cZl8QZ5PEemBuMq6mmcR8A)", //$NON-NLS-1$
                taskScript);

        String script = new DataMapperJavascriptGenerator().convertMappingsToJavascript(taskScript.getScript());

        assertNotNull("Script generation failed for 'Script Task' (_cZl8QZ5PEemBuMq6mmcR8A)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "bpm.process mappings"; //$NON-NLS-1$

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with.
         */
        assertTrue(context + ": Map from Process.priority property should be wrapped as 'bpm.process'.", //$NON-NLS-1$
                script.contains("= bpm.process.priority;")); //$NON-NLS-1$

        assertTrue(context + ": Map from Process.starttime property should be wrapped as 'bpm.process'.", //$NON-NLS-1$
                script.contains("= bpm.process.getStartTime();")); //$NON-NLS-1$

        assertTrue(context + ": Map to Process.priority property should be wrapped as 'bpm.process'.", //$NON-NLS-1$
                script.contains("bpm.process.priority =")); //$NON-NLS-1$

    }

    /**
     * Check that the work item attribute data mapping script generation is working correctly (all process data is
     * wrapped in the "data" object)
     * 
     * @param mapperProject
     */
    private void checkWorkItemAttributeMapperScriptGeneration(IProject mapperProject) {

        Map<String, Resource> brmModels =
                BRMGenerator.getInstance().generateBRMModels(mapperProject, "1.0.0.123456789"); //$NON-NLS-1$

        Resource workModelResource = brmModels.get(BRMGenerator.WORKMODEL_ARTIFACT_NAME);

        assertNotNull("workmodel.wm should have been generated", //$NON-NLS-1$
                workModelResource);

        com.tibco.n2.brm.workmodel.DocumentRoot documentRoot = (DocumentRoot) workModelResource.getContents().get(0);

        com.tibco.n2.brm.workmodel.WorkModel workModel = documentRoot.getWorkModels();
        WorkModel taskWorkModel = null;
        for (WorkModel wm : workModel.getWorkModel()) {
            if ("WM___QTccZ5PEemBuMq6mmcR8A".equals(wm.getWorkModelUID())) { //$NON-NLS-1$
                taskWorkModel = wm;
                break;
            }
        }

        assertNotNull("workmodel.wm should have a WorkModel with UUID 'WM___QTccZ5PEemBuMq6mmcR8A'", //$NON-NLS-1$
                taskWorkModel);

        WorkModelScript workModelScript = null;

        for (WorkModelScript script : taskWorkModel.getWorkModelScripts().getWorkModelScript()) {
            if (WorkItemScriptOperation.SCHEDULE.equals(script.getScriptOperation())) {
                workModelScript = script;
                break;
            }
        }

        assertNotNull("WorkModel 'WM___QTccZ5PEemBuMq6mmcR8A' should have a work WorkModelScript", //$NON-NLS-1$
                workModelScript);

        /*
         * Check content of script.
         */
        String scriptText = workModelScript.getScriptBody();

        /* Do some general 'all scripts must not' checking. */
        String context = "bpm.workManager mappings"; //$NON-NLS-1$

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with.
         */
        assertTrue(context + ": Map from WorkManager.cancel property should be wrapped as 'bpm.workManager'.", //$NON-NLS-1$
                scriptText.contains("= bpm.workManager.getWorkItem().cancel;")); //$NON-NLS-1$

        assertTrue(context
                + ": Map from WorkManager.workItemAttributes.attributes properties should be wrapped as 'bpm.workManager'.", //$NON-NLS-1$
                scriptText.contains("= bpm.workManager.getWorkItem().workItemAttributes.attribute6;") && //$NON-NLS-1$
                        scriptText.contains("= bpm.workManager.getWorkItem().workItemAttributes.attribute1;") //$NON-NLS-1$
                        && scriptText.contains("= bpm.workManager.getWorkItem().workItemAttributes.attribute5;") //$NON-NLS-1$
                        && scriptText.contains("= bpm.workManager.getWorkItem().workItemAttributes.attribute2;")); //$NON-NLS-1$

        assertTrue(context + ": Map from WorkManager.priority property should be wrapped as 'bpm.workManager'.", //$NON-NLS-1$
                scriptText.contains("= bpm.workManager.getWorkItem().priority;")); //$NON-NLS-1$

        assertTrue(context + ": Map to WorkManager.priority property should be wrapped as 'bpm.workManager'.", //$NON-NLS-1$
                scriptText.contains("bpm.workManager.getWorkItem().priority =")); //$NON-NLS-1$

        assertTrue(
                context + ": Map to WorkManager.workItemAttributes properties should be wrapped as 'bpm.workManager'.", //$NON-NLS-1$
                scriptText.contains("bpm.workManager.getWorkItem().workItemAttributes.attribute1 =") && //$NON-NLS-1$
                        scriptText.contains("bpm.workManager.getWorkItem().workItemAttributes.attribute2 =") && //$NON-NLS-1$
                        scriptText.contains("bpm.workManager.getWorkItem().workItemAttributes.attribute5 =") && //$NON-NLS-1$
                        scriptText.contains("bpm.workManager.getWorkItem().workItemAttributes.attribute6 =")); //$NON-NLS-1$

    }

    /**
     * Import all projects from test plugin resources for the main test
     * 
     * @return the project importer
     */
    private ProjectImporter importMainTestProjects() {
        /*
         * Import and migrate the project
         */

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/ProcessAndWMScriptClassTests/ProcessAndWorkManagerMappingsTest/" }, //$NON-NLS-1$
                new String[] { "ProcessAndWorkManagerMappingsTest" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from resources/ProcessAndWMScriptClassTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }
}
