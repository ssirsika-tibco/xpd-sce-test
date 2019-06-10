/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * In ACE all process data (fields/params) are wrapped in a special "data"
 * object in scripts - this tests all the implementation of ACE-1318
 * 
 * This test is for the script generation for the 'process data side' of
 * mappings.
 * 
 * Using a process with script task with a range of process data -> process-data
 * mappings.
 * 
 * And a sub-process call with Process <-> Sub-process data mappings
 * 
 * And a REST service call with Process <-> REST data mappings
 * 
 * @author aallway
 * @since 07 Jun 2019
 */
public class AceProcessDataWrapperMappingsTest extends TestCase {

    // @Test
    public void testDataIsWrappedInAllScriptScenarios() {
        ProjectImporter projectImporter = importMainTestProjects();

        Process process =
                ProcessUIUtil.getProcesById("_9JPIoIheEemL0JNuli1Mqw"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test process 'DataWrappingMappingsTests-Process' (_9JPIoIheEemL0JNuli1Mqw)", //$NON-NLS-1$
                process);

        checkProcessDataMapperScriptGeneration(process);

        checkDirectSubProcessInvokeDataMapperScriptGeneration(process);

        checkInflateSubProcessInvokeDataMapperScriptGeneration(process);

        checkRESTInvokeDataMapperScriptGeneration(process);

        projectImporter.performDelete();
    }

    /**
     * Test process data mapper script task. (checking that the various changes
     * from AMX BPM EMF based scripting have been made to deal with the JS based
     * ACE scripting).
     * 
     * @param process
     */
    private void checkProcessDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process,
                "_cm07cIhgEemL0JNuli1Mqw"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'Direct Script Mappings' (_9JPIoIheEemL0JNuli1Mqw)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        TaskScript taskScript =
                ((Task) activity.getImplementation()).getTaskScript();

        assertNotNull(
                "Cannot find test TaskScript in 'Direct Script Mappings' (_9JPIoIheEemL0JNuli1Mqw)", //$NON-NLS-1$
                taskScript);

        String script = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript(taskScript.getScript());

        assertNotNull(
                "Script generation failed for 'Direct Script Mappings' (_9JPIoIheEemL0JNuli1Mqw)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "Data mapper script task"; //$NON-NLS-1$
        generalMappingScriptChecks(script, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with.
         */

        assertTrue(context
                + ": Should check wrapping data object for null even for simple process field assignment.", //$NON-NLS-1$
                script.contains("if (data != null)")); //$NON-NLS-1$

        assertTrue(context
                + ": Should do direct assignment of complex types (without ScriptUtil.copy().", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassField = data.ClassField;")); //$NON-NLS-1$

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                script.contains(
                        "data.Copy_Of_ClassFieldtoInflate.complexList.length = 0;")); //$NON-NLS-1$

        assertTrue(context
                + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                script.contains(
                        "< data.ClassFieldtoInflate.complexList.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                script.contains(
                        "var $sVi1 = data.ClassFieldtoInflate.complexList[i1];")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                script.contains(
                        "data.Copy_Of_ClassFieldtoInflate.complexList.push($tVi1);")); //$NON-NLS-1$

        assertTrue(context
                + ": Should do direct assignment of complex types when merging lists (without ScriptUtil.copy().", //$NON-NLS-1$
                script.contains(
                        "data.Copy_Of_MergingComplexListField[i5] = $sVi5")); //$NON-NLS-1$
    }

    /**
     * Tests sub-process data mappings (process side data only) where complex
     * type data is directly mapped (don't create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkDirectSubProcessInvokeDataMapperScriptGeneration(
            Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process,
                "_SuLQwIhfEemL0JNuli1Mqw"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'Direct sub-process mappings' (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                activity);

        /*
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement((SubFlow) activity.getImplementation(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InputMappings()));

        assertNotNull(
                "Script generation failed for 'Direct sub-process mappings' input mappings (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "Direct sub-process mappings - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a
         * smattering really because all of these should be identical as for
         * Script Task process data mappings (the process side of data will be
         * the same.
         */
        assertTrue(context
                + ": Should do direct assignment of complex types (without ScriptUtil.copy().", //$NON-NLS-1$
                inputMappingsScript.contains("= data.ClassField;")); //$NON-NLS-1$

        assertTrue(context
                + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                inputMappingsScript.contains("< data.ComplexListField.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                inputMappingsScript
                        .contains("var $sVi1 = data.ComplexListField[i1];")); //$NON-NLS-1$

        /*
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement((SubFlow) activity.getImplementation(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings()));

        assertNotNull(
                "Script generation failed for 'Direct sub-process mappings' output mappings (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "Direct sub-process mappings - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                outputMappingsScript
                        .contains("data.ComplexListField.length = 0")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                outputMappingsScript
                        .contains("data.ComplexListField.push($sVi1);")); //$NON-NLS-1$

    }

    /**
     * Tests sub-process data mappings (process side data only) where complex
     * type data is created by mapping its content (i.e. create-and-inflate
     * target objects).
     * 
     * @param process
     */
    private void checkInflateSubProcessInvokeDataMapperScriptGeneration(
            Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process,
                "_BgIzAIt2EemO5bbkB2KC9w"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'Inflate sub-process mappings' (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                activity);

        /*
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement((SubFlow) activity.getImplementation(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_InputMappings()));

        assertNotNull(
                "Script generation failed for 'Inflate sub-process mappings' input mappings (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "Inflate sub-process mappings - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement((SubFlow) activity.getImplementation(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_OutputMappings()));

        assertNotNull(
                "Script generation failed for 'Inflate sub-process mappings' (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "Inflate sub-process mappings - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a
         * smattering really because all of these should be identical as for
         * Script Task process data mappings (the process side of data will be
         * the same.
         */
        assertTrue(context
                + ": Should use array index to get array items fron target array when merging.", //$NON-NLS-1$
                outputMappingsScript
                        .contains("$tVi3 = data.ComplexListField[i3];")); //$NON-NLS-1$

    }

    /**
     * Tests sub-process data mappings (process side data only) where complex
     * type data is directly mapped (don't create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkRESTInvokeDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process,
                "_FBDUBIuBEemO5bbkB2KC9w"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'REST Call' (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                activity);

        /*
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript =
                new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                        (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(
                                ((Task) activity.getImplementation())
                                        .getTaskService().getMessageIn(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ScriptDataMapper()));

        assertNotNull(
                "Script generation failed for 'REST Call' input mappings (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "REST Call - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a
         * smattering really because all of these should be identical as for
         * Script Task process data mappings (the process side of data will be
         * the same.
         */
        assertTrue(context
                + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                inputMappingsScript.contains("(data.Copy_Of_ClassField")); //$NON-NLS-1$
        assertTrue(context
                + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                !inputMappingsScript.contains("(Copy_Of_ClassField")); //$NON-NLS-1$

        assertTrue(context + ": Should include 'data' object in null checkimng", //$NON-NLS-1$
                inputMappingsScript.contains(
                        "if (data != null && data.Copy_Of_ClassField != null)")); //$NON-NLS-1$

        assertTrue(context
                + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                inputMappingsScript
                        .contains("< data.Copy_Of_ClassField.textList.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                inputMappingsScript
                        .contains(
                                "var $sVi1 = data.Copy_Of_ClassField.complexList[i1];")); //$NON-NLS-1$

        /*
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript =
                new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                        (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(
                                ((Task) activity.getImplementation())
                                        .getTaskService().getMessageOut(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ScriptDataMapper()));

        assertNotNull(
                "Script generation failed for 'REST Call' output mappings (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "REST Call - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        assertTrue(context
                + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                outputMappingsScript.contains("if (data.ClassField")); //$NON-NLS-1$
        assertTrue(context
                + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                !outputMappingsScript.contains("(ClassField")); //$NON-NLS-1$

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                outputMappingsScript
                        .contains("data.ClassField.textList.length = 0;")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                outputMappingsScript
                        .contains("data.ClassField.textList.push($sVi2);")); //$NON-NLS-1$

    }

    /**
     * Do some general 'all scripts must not' checking.
     * 
     * @param script
     */
    private void generalMappingScriptChecks(String script, String context) {
        assertFalse(context + ": should not use ScriptUtil.copy() method", //$NON-NLS-1$
                script.contains("ScriptUtil.copy")); //$NON-NLS-1$

        assertFalse(context + ": should not use list get() method", //$NON-NLS-1$
                script.contains(".get(")); //$NON-NLS-1$

        assertFalse(context + ": should not use list add() method", //$NON-NLS-1$
                script.contains(".add")); //$NON-NLS-1$

        assertFalse(context + ": should not use list clear() method", //$NON-NLS-1$
                script.contains(".clear")); //$NON-NLS-1$

        assertFalse(context + ": should not use list size() method", //$NON-NLS-1$
                script.contains(".size")); //$NON-NLS-1$

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

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip(
                "com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] {
                        "resources/WrappedProcessDataTests/DataWrappingData/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWappingGlobalSignal/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingREST/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingMapperTests/" }, //$NON-NLS-1$
                new String[] { "DataWrappingData", //$NON-NLS-1$
                        "DataWappingGlobalSignal", //$NON-NLS-1$
                        "DataWrappingREST", //$NON-NLS-1$
                        "DataWrappingMapperTests" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from resources/WrappedProcessDataTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

    /**
     * 
     * @param resource
     * @param markerId
     * @return <code>true</code> if given resource has given problem marker
     *         raised on it.
     */
    private boolean hasErrorProblemMarker(IResource resource) {
        try {
            IMarker[] markers = resource.findMarkers(IMarker.PROBLEM,
                    true,
                    IResource.DEPTH_INFINITE);

            if (markers != null) {
                for (IMarker marker : markers) {
                    if (marker.getAttribute(IMarker.SEVERITY,
                            -1) == IMarker.SEVERITY_ERROR) {
                        return true;
                    }
                }

            }

        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

}
