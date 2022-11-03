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
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * In ACE all process data (fields/params) are wrapped in a special "data" object in scripts - this tests all the
 * implementation of ACE-1318
 * 
 * This test is for the script generation for the 'process data side' of mappings.
 * 
 * Using a process with script task with a range of process data -> process-data mappings.
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
        try {
            IProject mapperProject = ResourcesPlugin.getWorkspace().getRoot().getProject("DataWrappingMapperTests"); //$NON-NLS-1$

            Process process = ProcessUIUtil.getProcesById("_9JPIoIheEemL0JNuli1Mqw"); //$NON-NLS-1$

            assertNotNull("Cannot find test process 'DataWrappingMappingsTests-Process' (_9JPIoIheEemL0JNuli1Mqw)", //$NON-NLS-1$
                    process);

            checkProcessDataMapperScriptGeneration(process);

            checkDirectSubProcessInvokeDataMapperScriptGeneration(process);

            checkInflateSubProcessInvokeDataMapperScriptGeneration(process);

            checkSubProcessCatchErrorDataMapperScriptGeneration(process);

            checkRESTInvokeDataMapperScriptGeneration(process);

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
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_cm07cIhgEemL0JNuli1Mqw"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Direct Script Mappings' (_cm07cIhgEemL0JNuli1Mqw)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        TaskScript taskScript = ((Task) activity.getImplementation()).getTaskScript();

        assertNotNull("Cannot find test TaskScript in 'Direct Script Mappings' (_cm07cIhgEemL0JNuli1Mqw)", //$NON-NLS-1$
                taskScript);

        String script = new DataMapperJavascriptGenerator().convertMappingsToJavascript(taskScript.getScript());

        assertNotNull("Script generation failed for 'Direct Script Mappings' (_cm07cIhgEemL0JNuli1Mqw)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "Data mapper script task"; //$NON-NLS-1$
        generalMappingScriptChecks(script, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with.
         */

        assertTrue(context + ": Should check wrapping data object for null even for simple process field assignment.", //$NON-NLS-1$
                script.contains("if (data != null)")); //$NON-NLS-1$

        assertTrue(context + ": Should do direct assignment of complex types (without ScriptUtil.copy().", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassField = data.ClassField;")); //$NON-NLS-1$

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassFieldtoInflate.complexList.length = 0;")); //$NON-NLS-1$

        assertTrue(context + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                script.contains("< data.ClassFieldtoInflate.complexList.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                script.contains("var $sVi1 = data.ClassFieldtoInflate.complexList[i1];")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassFieldtoInflate.complexList.push($tVi1);")); //$NON-NLS-1$

        assertTrue(context
                + ": Should do direct assignment of complex types when merging lists (without ScriptUtil.copy().", //$NON-NLS-1$
                script.contains("data.Copy_Of_MergingComplexListField[i6] = $sVi6")); //$NON-NLS-1$

        /*
         * Sid ACE-564 - Check that new BOM JS Class factories are used, enumerations are treated as simple text
         * properties (because that is what they are at run-time).
         */
        assertTrue(context + ": Should use new BOM JS factories wrapped in the 'factory' object.", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassFieldtoInflate = factory.com_example_data.createDataTypes()")); //$NON-NLS-1$

        assertTrue(
                context + ": Enumerations are just text properties at run-time now, so should just do direct assign.", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassFieldtoInflate.enum1 = data.ClassFieldtoInflate.enum1")); //$NON-NLS-1$

        assertTrue(context
                + ": Enumerations are just text properties at run-time now, so should just do push into target arrays.", //$NON-NLS-1$
                script.contains("data.Copy_Of_ClassFieldtoInflate.enumList.push($sVi2);")); //$NON-NLS-1$

    }

    /**
     * Tests sub-process data mappings (process side data only) where complex type data is directly mapped (don't
     * create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkDirectSubProcessInvokeDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_SuLQwIhfEemL0JNuli1Mqw"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Direct sub-process mappings' (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                activity);

        /**
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement((SubFlow) activity.getImplementation(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings()));

        assertNotNull(
                "Script generation failed for 'Direct sub-process mappings' input mappings (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "Direct sub-process mappings - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a smattering really because all of these
         * should be identical as for Script Task process data mappings (the process side of data will be the same.
         */
        assertTrue(context + ": Should do direct assignment of complex types (without ScriptUtil.copy().", //$NON-NLS-1$
                inputMappingsScript.contains("= data.ClassField;")); //$NON-NLS-1$

        assertTrue(context + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                inputMappingsScript.contains("< data.ComplexListField.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                inputMappingsScript.contains("var $sVi1 = data.ComplexListField[i1];")); //$NON-NLS-1$

        /*
         * Sid ACE-1599 Check that all sub-process side data (the sub-process parameters) are wrapped in the new
         * "parameters" object.
         */
        assertTrue(context + ": Should wrap target sub-process parameter assignments in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript.contains("parameters.BooleanParameter =")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameter list clear in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript.contains("parameters.ComplexListParameter.length = 0;")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameter unset in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript.contains("parameters.BooleanParameter = null;")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameter add list item in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript.contains("parameters.SimpleListParameter.push")); //$NON-NLS-1$

        /**
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement((SubFlow) activity.getImplementation(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings()));

        assertNotNull(
                "Script generation failed for 'Direct sub-process mappings' output mappings (_SuLQwIhfEemL0JNuli1Mqw)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "Direct sub-process mappings - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ComplexListField.length = 0")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ComplexListField.push($sVi1);")); //$NON-NLS-1$

        /*
         * Sid ACE-1599 Check that all sub-process side data (the sub-process parameters) are wrapped in the new
         * "parameters" object.
         */
        assertTrue(context + ": Should wrap target sub-process parameters in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains("= parameters.BooleanParameter")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameters get from list in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains("= parameters.ComplexListParameter[")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameters null checks in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains("if (parameters != null && parameters.ComplexListParameter != null) {")); //$NON-NLS-1$

    }

    /**
     * Tests sub-process data mappings (process side data only) where complex type data is created by mapping its
     * content (i.e. create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkInflateSubProcessInvokeDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_BgIzAIt2EemO5bbkB2KC9w"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Inflate sub-process mappings' (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                activity);

        /*
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement((SubFlow) activity.getImplementation(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings()));

        assertNotNull(
                "Script generation failed for 'Inflate sub-process mappings' input mappings (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "Inflate sub-process mappings - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Sid ACE-1599 Check that all sub-process side data (the sub-process parameters) are wrapped in the new
         * "parameters" object.
         */
        assertTrue(
                context + ": Should wrap target sub-process parameter creation from factory in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript
                        .contains("parameters.ClassParameter = factory.com_example_data.createDataTypes();")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameter data assignments in a 'parameters' object.", //$NON-NLS-1$
                inputMappingsScript.contains("parameters.ClassParameter.complexChild.attribute1 =")); //$NON-NLS-1$

        /*
         * Sid ACE-2896 - Check that BOm class name in creator methods for lower case BOM classes are have initial
         * character upper cased.
         */
        assertFalse(context
                + ": BOM Factory creator method name should not use lower case initial for BOM class name when BOM class has lower case initial.", //$NON-NLS-1$
                inputMappingsScript.contains("= factory.com_example_data.createchildClass()")); //$NON-NLS-1$

        assertTrue(context
                + ": BOM Factory creator method name should use upper case initial for BOM class name when BOM class has lower case initial.", //$NON-NLS-1$
                inputMappingsScript.contains("= factory.com_example_data.createChildClass()")); //$NON-NLS-1$

        /*
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement((SubFlow) activity.getImplementation(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings()));

        assertNotNull("Script generation failed for 'Inflate sub-process mappings' (_BgIzAIt2EemO5bbkB2KC9w)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "Inflate sub-process mappings - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a smattering really because all of these
         * should be identical as for Script Task process data mappings (the process side of data will be the same.
         */
        assertTrue(context + ": Should use array index to get array items fron target array when merging.", //$NON-NLS-1$
                outputMappingsScript.contains("$tVi3 = data.ComplexListField[i3];")); //$NON-NLS-1$

        /*
         * Sid ACE-1599 Check that all sub-process side data (the sub-process parameters) are wrapped in the new
         * "parameters" object.
         */
        assertTrue(context + ": Should wrap target sub-process parameter null checks in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains(
                        "if (parameters != null && parameters.ClassParameter != null && parameters.ClassParameter.complexChild != null) {")); //$NON-NLS-1$

        assertTrue(context + ": Should wrap target sub-process parameter get value in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains("= parameters.ClassParameter.complexChild.attribute1;")); //$NON-NLS-1$

    }

    /**
     * Sid ACE-1599 Check both sides of the data mappings for catch sub-process error events are wrapped correctly
     * 
     * @param process
     */
    private void checkSubProcessCatchErrorDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "__7qdUKyXEemzZMtlEWYyzQ"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Error Event' (__7qdUKyXEemzZMtlEWYyzQ)", //$NON-NLS-1$
                activity);

        /*
         * Generate the catch error mapping script for the activity.
         */
        CatchErrorMappings catchErrorMappings = (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                ((IntermediateEvent) activity.getEvent()).getResultError(),
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings());

        String outputMappingsScript = new DataMapperJavascriptGenerator().convertMappingsToJavascript(
                (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(catchErrorMappings.getMessage(),
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings()));

        assertNotNull("Script generation failed for 'Inflate sub-process mappings' (__7qdUKyXEemzZMtlEWYyzQ)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "Catch error event mappings"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        /*
         * And some more specific stuff
         */
        assertTrue(context + ": Target process data should be wrapped in 'data' object", //$NON-NLS-1$
                outputMappingsScript.contains("data.FixedNumberTypeDecl =")); //$NON-NLS-1$

        assertTrue(context
                + ": Should wrap source sub-process parameter in a 'parameters' object.", //$NON-NLS-1$
                outputMappingsScript.contains("= parameters.NumberParameter;")); //$NON-NLS-1$

        assertTrue(context + ": Should use original var_errorCode", //$NON-NLS-1$
                outputMappingsScript.contains("= var_errorCode")); //$NON-NLS-1$

    }

    /**
     * Tests sub-process data mappings (process side data only) where complex type data is directly mapped (don't
     * create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkRESTInvokeDataMapperScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_FBDUBIuBEemO5bbkB2KC9w"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'REST Call' (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                activity);

        /*
         * Generate the input mapping script for the activity.
         */
        String inputMappingsScript =
                new DataMapperJavascriptGenerator().convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement(((Task) activity.getImplementation()).getTaskService().getMessageIn(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper()));

        assertNotNull("Script generation failed for 'REST Call' input mappings (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                inputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        String context = "REST Call - Input"; //$NON-NLS-1$
        generalMappingScriptChecks(inputMappingsScript, context);

        /*
         * Check some specifics of the stuff that ACE-1318 dealt with - just a smattering really because all of these
         * should be identical as for Script Task process data mappings (the process side of data will be the same.
         */
        assertTrue(context + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                inputMappingsScript.contains("(data.Copy_Of_ClassField")); //$NON-NLS-1$
        assertTrue(context + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                !inputMappingsScript.contains("(Copy_Of_ClassField")); //$NON-NLS-1$

        assertTrue(context + ": Should include 'data' object in null checkimng", //$NON-NLS-1$
                inputMappingsScript.contains("if (data != null && data.Copy_Of_ClassField != null)")); //$NON-NLS-1$

        assertTrue(context + ": Should iterate thru source arrays using length attribute.", //$NON-NLS-1$
                inputMappingsScript.contains("< data.Copy_Of_ClassField.textList.length")); //$NON-NLS-1$

        assertTrue(context + ": Should use array index to get array items.", //$NON-NLS-1$
                inputMappingsScript.contains("var $sVi1 = data.Copy_Of_ClassField.complexList[i1];")); //$NON-NLS-1$

        /*
         * Sid ACE-6367: Should have code to delete target REST data if it is assigned from null
         */
        /*
         * Query and header parameters should NOT be deleted.
         */
        assertFalse(context + ": Should NOT delete QUERY params if they are null.", //$NON-NLS-1$
                inputMappingsScript.contains("delete REST_QUERY_queryparam1")); //$NON-NLS-1$

        assertFalse(context + ": Should NOT delete HEADER params if they are null.", //$NON-NLS-1$
                inputMappingsScript.contains("delete REST_QUERY_queryparam1")); //$NON-NLS-1$

        /*
         * Should NOT delete REST input payload arrays if they are null
         */
        assertFalse(context + ": Should NOT delete HEADER params if they are null.", //$NON-NLS-1$
                inputMappingsScript.contains("delete REST_PAYLOAD['textList']")); //$NON-NLS-1$

        /*
         * Should delete standard payload property assignments if they are null
         */
        assertTrue(context + ": Should delete normal properties if source child field property is null (1)", //$NON-NLS-1$
                inputMappingsScript.contains("if (REST_PAYLOAD['booleanProperty'] === null) {")); //$NON-NLS-1$

        assertTrue(context + ": Should delete normal properties if source child field property is null (2)", //$NON-NLS-1$
                inputMappingsScript.contains("delete REST_PAYLOAD['booleanProperty'];")); //$NON-NLS-1$

        /* Final check ensures that the expected code is contiguous */
        assertTrue(context + ": Should delete normal properties if source child field property is null (3)", //$NON-NLS-1$
                inputMappingsScript.replaceAll("\\s+", "").contains( //$NON-NLS-1$ //$NON-NLS-2$
                        "if(REST_PAYLOAD['booleanProperty']===null){deleteREST_PAYLOAD['booleanProperty'];}"));

        /*
         * Should delete standard payload property assignments if their parent property are null
         */
        assertTrue(context + ": Should delete normal properties if source child field parent property is null (1)", //$NON-NLS-1$
                inputMappingsScript.contains("REST_PAYLOAD['booleanProperty'] = null;")); //$NON-NLS-1$

        /* Final check ensures that the expected code is contiguous */
        assertTrue(context + ": Should delete normal properties if source child field parent property is null (2)", //$NON-NLS-1$
                inputMappingsScript.replaceAll("\\s+", "").contains( //$NON-NLS-1$ //$NON-NLS-2$
                        "else{REST_PAYLOAD['booleanProperty']=null;deleteREST_PAYLOAD['booleanProperty'];}"));

        /*
         * Should delete array child payload property assignments if they are null
         */
        assertTrue(context + ": Should delete array child properties if source child field property is null (1)", //$NON-NLS-1$
                inputMappingsScript.contains(
                        "if ($tVi1['nestedTypeProperty']['grandChildProperty'] === null) {")); //$NON-NLS-1$

        assertTrue(context + ": Should delete array child properties if source child field property is null (2)", //$NON-NLS-1$
                inputMappingsScript
                        .contains("delete $tVi1['nestedTypeProperty']['grandChildProperty'];")); //$NON-NLS-1$

        /* Final check ensures that the expected code is contiguous */
        assertTrue(context + ": Should delete array child properties if source child field property is null (3)", //$NON-NLS-1$
                inputMappingsScript.replaceAll("\\s+", "").contains( //$NON-NLS-1$ //$NON-NLS-2$
                        "if($tVi1['nestedTypeProperty']['grandChildProperty']===null){delete$tVi1['nestedTypeProperty']['grandChildProperty'];}"));

        /*
         * Should delete array child payload property assignments if their parent property are null
         */
        assertTrue(context + ": Should delete array child properties if source child field parent property is null (1)", //$NON-NLS-1$
                inputMappingsScript
                        .contains("$tVi1['nestedTypeProperty']['grandChildProperty'] = null;")); //$NON-NLS-1$

        /* Final check ensures that the expected code is contiguous */
        assertTrue(context + ": Should delete array child properties if source child field parent property is null (2)", //$NON-NLS-1$
                inputMappingsScript.replaceAll("\\s+", "").contains( //$NON-NLS-1$ //$NON-NLS-2$
                        "else{$tVi1['nestedTypeProperty']['grandChildProperty']=null;delete$tVi1['nestedTypeProperty']['grandChildProperty'];}"));

        // END OF ACE-6367

        /*
         * Sid ACE-564 - enumerations are treated as simple text properties (because that is what they are at run-time).
         */
        assertTrue(context + ": Should treat input enumerations as simple text values.", //$NON-NLS-1$
                inputMappingsScript.contains(
                        "REST_PAYLOAD['textToFromEnum'] = (data.Copy_Of_ClassField.enum1 != null) ? new String(data.Copy_Of_ClassField.enum1) : null;")); //$NON-NLS-1$

        assertTrue(context + ": Should treat input enumeration lists as simple text lists.", //$NON-NLS-1$
                inputMappingsScript.contains(
                        "REST_PAYLOAD['textToFromEnumList'].push(($sVi3 != null) ? new String($sVi3) : null);")); //$NON-NLS-1$

        /*
         * Generate the output mapping script for the activity.
         */
        String outputMappingsScript =
                new DataMapperJavascriptGenerator().convertMappingsToJavascript((ScriptDataMapper) Xpdl2ModelUtil
                        .getOtherElement(((Task) activity.getImplementation()).getTaskService().getMessageOut(),
                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper()));

        assertNotNull("Script generation failed for 'REST Call' output mappings (_FBDUBIuBEemO5bbkB2KC9w)", //$NON-NLS-1$
                outputMappingsScript);

        /* Do some general 'all scripts must not' checking. */
        context = "REST Call - Output"; //$NON-NLS-1$
        generalMappingScriptChecks(outputMappingsScript, context);

        assertTrue(context + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                outputMappingsScript.contains("if (data.ClassField")); //$NON-NLS-1$
        assertTrue(context + ": Should should wrap all process data in a 'data' object ", //$NON-NLS-1$
                !outputMappingsScript.contains("(ClassField")); //$NON-NLS-1$

        assertTrue(context + ": Should clear target arrays using length = 0.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ClassField.textList.length = 0;")); //$NON-NLS-1$

        assertTrue(context + ": Should use array push() to add array items.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ClassField.textList.push($sVi3);")); //$NON-NLS-1$

        /*
         * Sid ACE-564 - Check that new BOM JS Class factories are used, enumerations are treated as simple text
         * properties (because that is what they are at run-time).
         */
        assertTrue(context + ": Should treat output enumerations as simple text values.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ClassField.enum1 = REST_PAYLOAD['textToFromEnum'];")); //$NON-NLS-1$

        assertTrue(context + ": (1) Should treat output enumeration lists as simple text lists.", //$NON-NLS-1$
                outputMappingsScript.contains("var $sVi2 = REST_PAYLOAD['textToFromEnumList'][i2]")); //$NON-NLS-1$
        assertTrue(context + ": (2) Should treat output enumeration lists as simple text lists.", //$NON-NLS-1$
                outputMappingsScript.contains("data.ClassField.enumList.push($sVi2);")); //$NON-NLS-1$

        /*
         * Sid ACE-6367: Should NOT have code to delete target PROCESS data if it is assigned from null in output
         * mapping script (we only do this for REST payloads as BPMe is quite happy with null properties.
         */
        assertFalse(context + ": Output mapping scripts should not delete target process data", //$NON-NLS-1$
                outputMappingsScript.contains("delete ")); //$NON-NLS-1$

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
            if ("WM__fAv1EJaWEempqeJCl3Qq9A".equals(wm.getWorkModelUID())) { //$NON-NLS-1$
                taskWorkModel = wm;
                break;
            }
        }

        assertNotNull("workmodel.wm should have a WorkModel with UUID 'WM__fAv1EJaWEempqeJCl3Qq9A'", //$NON-NLS-1$
                taskWorkModel);

        WorkModelScript workModelScript = null;

        for (WorkModelScript script : taskWorkModel.getWorkModelScripts().getWorkModelScript()) {
            if (WorkItemScriptOperation.SYSAPPEND.equals(script.getScriptOperation())) {
                workModelScript = script;
                break;
            }
        }

        assertNotNull("WorkModel 'WM__fAv1EJaWEempqeJCl3Qq9A' should have a work WorkModelScript", //$NON-NLS-1$
                workModelScript);

        /*
         * Check content of script.
         */
        /*
         * Sid ACE-2036 update test to cover the target work manager data attribs changing from WorkManagerFactory.xxx
         * to bpm.workManager.xxxx
         */

        String scriptText = workModelScript.getScriptBody();

        assertTrue("Data used in conditions is wrapped in 'data' object", //$NON-NLS-1$
                scriptText.contains("if(data.ClassField == null)")); //$NON-NLS-1$

        assertTrue("Data used in logging is wrapped in 'data' object", //$NON-NLS-1$
                scriptText.contains(
                        "\"DataWrappingMappingsTestsProcess/DynamicOrgParticipant: Info: Work item attribute mapping: mapping from attribute `attribute3` was unset because parent element `data.ClassField.complexChild` of source path `data.ClassField.complexChild.attribute1` is null.\"")); //$NON-NLS-1$

        assertTrue("Data used in assignments is wrapped in 'data' object", //$NON-NLS-1$
                scriptText.contains(
                        "bpm.workManager.getWorkItem().workItemAttributes.attribute3 = data.ClassField.complexChild.attribute1")); //$NON-NLS-1$

        assertTrue("New bpm.workManager factory is used for atributes", //$NON-NLS-1$
                scriptText.contains(
                        "bpm.workManager.getWorkItem().workItemAttributes.attribute3 = data.ClassField.complexChild.attribute1")); //$NON-NLS-1$

        assertTrue("New bpm.workManager factory is used for atributes", //$NON-NLS-1$
                scriptText.contains(
                        "bpm.workManager.getWorkItem().workItemAttributes.attribute3 = data.ClassField.complexChild.attribute1")); //$NON-NLS-1$

        assertFalse("Old WorkManagerFactory should never be used", //$NON-NLS-1$
                scriptText.contains("WorkManagerFactory")); //$NON-NLS-1$
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
                new String[] { "resources/WrappedProcessDataTests/DataWrappingData/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWappingGlobalSignal/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingREST/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingOrganisation/", //$NON-NLS-1$
                        "resources/WrappedProcessDataTests/DataWrappingMapperTests/" }, //$NON-NLS-1$
                new String[] { "DataWrappingData", //$NON-NLS-1$
                        "DataWappingGlobalSignal", //$NON-NLS-1$
                        "DataWrappingREST", //$NON-NLS-1$
                        "DataWrappingOrganisation", //$NON-NLS-1$
                        "DataWrappingMapperTests" }); //$NON-NLS-1$

        assertTrue("Failed to load projects from resources/WrappedProcessDataTests/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

}
