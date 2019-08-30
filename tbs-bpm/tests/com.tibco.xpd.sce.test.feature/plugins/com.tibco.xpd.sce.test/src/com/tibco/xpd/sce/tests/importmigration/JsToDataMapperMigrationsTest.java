/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Test for All of JavaScript to Data-Mapper mapping grammar migrations.
 * 
 * Sub-PRocess
 * 
 * Global Signal
 * 
 * Local Signal (attached to task boundary)
 * 
 * Error Event (catch all and catch specific sub-proc / REST service error)
 * 
 * @author aallway
 * @since 22 Mar 2019
 */
public class JsToDataMapperMigrationsTest extends TestCase {

    // @Test
    public void testDataMappingMigration() {
        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
                new String[] { "resources/ImportMigrationTests/MigrateMappingsData/", //$NON-NLS-1$
                        "resources/ImportMigrationTests/MigrateMappingsGlobalSignal/", //$NON-NLS-1$
                        "resources/ImportMigrationTests/MigrateMappings/" }, //$NON-NLS-1$
                new String[] { "MigrateMappingsData", "MigrateMappingsGlobalSignal", "MigrateMappings" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        assertTrue("Failed to load projects from resources/ImportMigrationTests/", projectImporter != null); //$NON-NLS-1$
        try {
            TestUtil.buildAndWait();

            IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("MigrateMappings"); //$NON-NLS-1$

            /*
             * Seem to occasionally get a Forms Resource 11.x issue (The project natures, special folders etc do not
             * match the asset configuration)
             */
            List<String> okProblems = new ArrayList<String>();
            okProblems.add("Last statement must be a return"); //$NON-NLS-1$ - Expected error (script mappings need a
                                                                 // return statement for data-mapper
            okProblems.add("Unmapped user defined mapping scripts"); //$NON-NLS-1$ - Expected error, these weren't
                                                                       // legal in AMX BPM either but possible to model
                                                                       // so they are included in the migration
            okProblems.add("Forms Resources 11.x"); //$NON-NLS-1$ - Sometimes get these unrelated forms problems.

            assertFalse(
                    "ScriptMigrationTests project should have migrated with only a few expected script-mapping problems.", //$NON-NLS-1$
                    TestUtil.hasErrorProblemMarker(project,
                            true,
                            okProblems)); // $NON-NLS-1$

            checkSubProcessMappingMigrations(project);

            checkCatchAllErrorMappingMigrations(project);

            checkCatchSpecificSubProcessErrorMappingMigrations(project);

            checkThrowGlobalSignalMappingMigrations(project);

            checkCatchGlobalSignalMappingMigrations(project);

            checkCatchLocalSignalMappingMigrations(project);

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }

    }

    /**
     * Check the migrations performed for sub-process mappings
     * 
     * @param project
     */
    private void checkSubProcessMappingMigrations(IProject project) {
        String context = "SubProcessMappingsMainProcess - JavaScriptSubProcess: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_jV5uwMjbEemv4a_2cRgU_g"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_p7b4UcjbEemv4a_2cRgU_g"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        SubFlow subFlow = (SubFlow) activity.getImplementation();

        /*
         * Check input mappings
         */
        ScriptDataMapper inputMappings = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(subFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings());

        assertTrue(context + "DataMapper input mappings should have been created.", inputMappings != null); //$NON-NLS-1$
        
        assertTrue(context + "There should be 7 input DataMappings", inputMappings.getDataMappings().size() == 7); //$NON-NLS-1$

        assertTrue(context + "Sub-process input mapper context should be 'ProcessToSubProcess'", //$NON-NLS-1$
                "ProcessToSubProcess".equals(inputMappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : inputMappings.getDataMappings()) {
            if ("ActivityInterface.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "ProcessToSubProcess.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "TextParameter2".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "ComplexField.instanceName".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct input standard mapping example (from ComplexField.instanceName to TextParameter2)", //$NON-NLS-1$
                foundCorrectStdMapping);
        
        boolean foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : inputMappings.getDataMappings()) {
            if ("ProcessToSubProcess.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptParameter".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "My Input Script".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "\"abc\" + \"xyz\";".equals(scriptInformation.getExpression().getText())) { //$NON-NLS-1$
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context
                + "Expected to find a correct input script mapping example (from Script to FromScriptParameter)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 2 unmapped input script mappings", //$NON-NLS-1$
                inputMappings.getUnmappedScripts().size() == 2);


        /*
         * Check output mappings
         */
        ScriptDataMapper outputMappings = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(subFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());

        assertTrue(context + "DataMapper output mappings should have been created.", outputMappings != null); //$NON-NLS-1$
        
        assertTrue(context + "There should be 6 output DataMappings", outputMappings.getDataMappings().size() == 6); //$NON-NLS-1$

        assertTrue(context + "Sub-process output mapper context should be 'SubProcessToProcess'", //$NON-NLS-1$
                "SubProcessToProcess".equals(outputMappings.getMapperContext())); //$NON-NLS-1$

        foundCorrectStdMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("SubProcessToProcess.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "ComplexField.instanceName".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "TextParameter2".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct output standard mapping example (from TextParameter2 to ComplexField.instanceName)", //$NON-NLS-1$
                foundCorrectStdMapping);

        foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptField".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "My Output Script".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "\"zyx\" + \"cba\";".equals(scriptInformation.getExpression().getText())) { //$NON-NLS-1$
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context
                + "Expected to find a correct output script mapping example (from Script to FromScriptField)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 2 unmapped output script mappings", //$NON-NLS-1$
                outputMappings.getUnmappedScripts().size() == 2);

    }

    /**
     * Check the migrations performed for catch error mappings (Catch all)
     * 
     * @param project
     */
    private void checkCatchAllErrorMappingMigrations(IProject project) {
        String context = "SubProcessMappingsMainProcess - CatchAllError: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_jV5uwMjbEemv4a_2cRgU_g"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_H_J3gMs1EemGjIDcVTaZOg"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

        ResultError resultError = intermediateEvent.getResultError();

        CatchErrorMappings catchErrorMappings = (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resultError,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings());

        Message message = catchErrorMappings.getMessage();

        ScriptDataMapper outputMappings = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(message,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());

        /*
         * Check output mappings
         */
        assertTrue(context + "DataMapper output mappings should have been created.", outputMappings != null); //$NON-NLS-1$

        assertTrue(context + "There should be 2 output DataMappings", outputMappings.getDataMappings().size() == 2); //$NON-NLS-1$

        assertTrue(context + "Catch all mapper context should be 'CatchAll'", //$NON-NLS-1$
                "CatchAll".equals(outputMappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("CatchAll.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "TextField1".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "$ERRORCODE".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct output standard mapping example (from $ERRORCODE to TextField1)", //$NON-NLS-1$
                foundCorrectStdMapping);


        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 0 unmapped output script mappings", //$NON-NLS-1$
                outputMappings.getUnmappedScripts().size() == 0);

    }

    /**
     * Check the migrations performed for catch error mappings (Catch specific sub-process)
     * 
     * @param project
     */
    private void checkCatchSpecificSubProcessErrorMappingMigrations(IProject project) {
        String context = "SubProcessMappingsMainProcess - CatchSpecificSubProcess: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_jV5uwMjbEemv4a_2cRgU_g"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_PpTfwMs1EemGjIDcVTaZOg"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

        ResultError resultError = intermediateEvent.getResultError();

        CatchErrorMappings catchErrorMappings = (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(resultError,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchErrorMappings());

        Message message = catchErrorMappings.getMessage();

        ScriptDataMapper outputMappings = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(message,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings());

        /*
         * Check output mappings
         */
        assertTrue(context + "DataMapper output mappings should have been created.", outputMappings != null); //$NON-NLS-1$

        assertTrue(context + "There should be 7 output DataMappings", outputMappings.getDataMappings().size() == 7); //$NON-NLS-1$

        assertTrue(context + "Catch all mapper context should be 'CatchSubProcessError'", //$NON-NLS-1$
                "CatchSubProcessError".equals(outputMappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdErrorCodeMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("CatchSubProcessError.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "TextField1".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "$ERRORCODE".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdErrorCodeMapping = true;
                break;
            }
        }
        assertTrue(
                context + "Expected to find a correct output standard mapping example (from $ERRORCODE to TextField1)", //$NON-NLS-1$
                foundCorrectStdErrorCodeMapping);

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("CatchSubProcessErrorProcessData.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "ComplexField.instanceNumber".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "DecimalParameter2".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(
                context + "Expected to find a correct output standard mapping example (from DecimalParameter2 to ComplexField.instanceNumber)", //$NON-NLS-1$
                foundCorrectStdMapping);

        boolean foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : outputMappings.getDataMappings()) {
            if ("ActivityInterface.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    // NOTE: for DataMapper mappings Direction is ALWAYS "IN" even on output mappings.
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptField".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "Script Mapping".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "\"abc\" + \"xyz\";".equals(scriptInformation.getExpression().getText())) { //$NON-NLS-1$
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(
                context + "Expected to find a correct output script mapping example (from Script to FromScriptField)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 1 unmapped output script mappings", //$NON-NLS-1$
                outputMappings.getUnmappedScripts().size() == 1);

    }

    /**
     * Check the migrations performed for throw global signal mappings (intermediate and end events.
     * 
     * @param project
     */
    private void checkThrowGlobalSignalMappingMigrations(IProject project) {
        /*
         * Check mappings on Throw Signal End Event
         */
        String context = "ThrowGlobalSignalProcess - ThrowGlobalEndEvent: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_RgkTgMpdEemqrfkcQCortA"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_bPhFE8pdEemqrfkcQCortA"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        EndEvent endEvent = (EndEvent) activity.getEvent();

        TriggerResultSignal triggerResultSignal = endEvent.getTriggerResultSignal();

        SignalData signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(triggerResultSignal,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

        checkThrowGlobalSignalEventMappings(context, signalData);

        /*
         * Check mappings on Throw Signal Intermediate Event
         */
        context = "ThrowGlobalSignalProcess - ThrowGlobalIntermediateEvent: "; //$NON-NLS-1$

        activity = process.getActivity("_vqQD4MpdEemqrfkcQCortA"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

        triggerResultSignal = intermediateEvent.getTriggerResultSignal();

        signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(triggerResultSignal,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

        checkThrowGlobalSignalEventMappings(context, signalData);

    }

    /**
     * Check the mappings for an individual throw signal event
     * 
     * @param context
     * @param signalData
     */
    public void checkThrowGlobalSignalEventMappings(String context, SignalData signalData) {
        ScriptDataMapper mappings = signalData.getInputScriptDataMapper();

        assertTrue(context + "DataMapper input mappings should have been created.", mappings != null); //$NON-NLS-1$

        assertTrue(context + "There should be 5 input DataMappings", mappings.getDataMappings().size() == 5); //$NON-NLS-1$

        assertTrue(context + "Throw global signal mapper context should be 'GlobalSignalThrow'", //$NON-NLS-1$
                "GlobalSignalThrow".equals(mappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("ActivityInterface.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "MapToGlobalSignal.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "ComplexPayloadData".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "ComplexField".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct standard mapping example (from ComplexField to ComplexPayloadData)", //$NON-NLS-1$
                foundCorrectStdMapping);

        boolean foundCorrectCorrelationMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("ActivityInterface.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "MapToGlobalSignalCorrelation.DataMapperContent" //$NON-NLS-1$
                            .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "SignalCorrelationField1".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "TextField1".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectCorrelationMapping = true;
                break;
            }
        }
        assertTrue(
                context + "Expected to find a correct correlation mapping example (from TextField3 to SignalCorrelationField1)", //$NON-NLS-1$
                foundCorrectCorrelationMapping);

        boolean foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapToGlobalSignal.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptPayloadData".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "Throw Signal Script".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "\"abc\" + \"xyz\";".equals(scriptInformation.getExpression().getText())) { //$NON-NLS-1$
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context
                + "Expected to find a correct script mapping example (from Script to FromScriptPayloadData)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        boolean foundCorrectCorrelationScriptMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapToGlobalSignalCorrelation.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "SignalCorrelationField2".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "Correlation data mapping".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "\"Correlation data mapping\";".equals(scriptInformation.getExpression().getText())) { //$NON-NLS-1$
                    foundCorrectCorrelationScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context
                + "Expected to find a correct correlation script mapping example (from Script to SignalCorrelationField2)", //$NON-NLS-1$
                foundCorrectCorrelationScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 1 unmapped script mapping", //$NON-NLS-1$
                mappings.getUnmappedScripts().size() == 1);

    }

    /**
     * Check the migrations performed for catch global signal mappings (event handler and event sub-process)
     * 
     * @param project
     */
    private void checkCatchGlobalSignalMappingMigrations(IProject project) {
        /*
         * Check mappings on Catch Signal Event Handler
         */
        String context = "CatchGlobalSignalProcess - CatchGlobalEventHandler: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_VM-n8MpqEemqrfkcQCortA"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_8i5B4MprEemqrfkcQCortA"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

        TriggerResultSignal triggerResultSignal = intermediateEvent.getTriggerResultSignal();

        SignalData signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(triggerResultSignal,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

        checkCatchGlobalSignalEventMappings(context, signalData);

        /*
         * Check mappings on Catch Signal Event sub-process
         */
        context = "CatchGlobalSignalProcess - CatchGlobalEventSubProcess: "; //$NON-NLS-1$

        ActivitySet activitySet = process.getActivitySet("_v6Pp4MpsEemqrfkcQCortA"); //$NON-NLS-1$

        activity = activitySet.getActivity("_uIVnYMpsEemqrfkcQCortA"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        StartEvent startEvent = (StartEvent) activity.getEvent();

        triggerResultSignal = startEvent.getTriggerResultSignal();

        signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(triggerResultSignal,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());


        checkCatchGlobalSignalEventMappings(context, signalData);

    }

    /**
     * Check the mappings for an individual catch global signal event
     * 
     * @param context
     * @param signalData
     */
    public void checkCatchGlobalSignalEventMappings(String context, SignalData signalData) {
        ScriptDataMapper mappings = signalData.getOutputScriptDataMapper();

        assertTrue(context + "DataMapper output mappings should have been created.", mappings != null); //$NON-NLS-1$

        assertTrue(context + "There should be 5 output DataMappings", mappings.getDataMappings().size() == 5); //$NON-NLS-1$

        assertTrue(context + "Catch global signal mapper context should be 'GlobalSignalCatch'", //$NON-NLS-1$
                "GlobalSignalCatch".equals(mappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapFromGlobalSignal.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "MapFromGlobalSignalTarget.DataMapperContent" //$NON-NLS-1$
                            .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "ComplexField".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "ComplexPayloadData".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct standard mapping example (from ComplexPayloadData to ComplexField)", //$NON-NLS-1$
                foundCorrectStdMapping);

        boolean foundCorrectCorrelationMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapFromGlobalSignal.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "MapFromGlobalSignalCorrelation.DataMapperContent" //$NON-NLS-1$
                            .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "CorrelationTextField2".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "SignalCorrelationField1".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectCorrelationMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct correlation mapping example (from SignalCorrelationField1 to CorrelationTextField2)", //$NON-NLS-1$
                foundCorrectCorrelationMapping);

        boolean foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapFromGlobalSignalTarget.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptField".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "Catch Signal Script".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null
                        && "SIGNAL_TextPayloadData + \"zyx\" + \"cba\";" //$NON-NLS-1$
                                .equals(scriptInformation.getExpression().getText())) {
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context + "Expected to find a correct script mapping example (from Script to FromScriptField)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 1 unmapped script mapping", //$NON-NLS-1$
                mappings.getUnmappedScripts().size() == 1);

    }

    /**
     * Check the migrations performed for catch local signal mappings (attached to user task boundary)
     * 
     * @param project
     */
    private void checkCatchLocalSignalMappingMigrations(IProject project) {
        /*
         * Check mappings on Catch Local Signal Event Handler
         */
        String context = "CatchLocalSignalProcess - CatchSignal1: "; //$NON-NLS-1$

        Process process = ProcessUIUtil.getProcesById("_4fsO9MsrEemGjIDcVTaZOg"); //$NON-NLS-1$ -
                                                                                  // "SubProcessMappingsMainProcess"
        Activity activity = process.getActivity("_QdHRYMssEemGjIDcVTaZOg"); //$NON-NLS-1$ - "JavaScriptSubProcess"

        IntermediateEvent intermediateEvent = (IntermediateEvent) activity.getEvent();

        TriggerResultSignal triggerResultSignal = intermediateEvent.getTriggerResultSignal();

        SignalData signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(triggerResultSignal,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());

        checkCatchLocalSignalEventMappings(context, signalData);

    }

    /**
     * Check the mappings for an individual catch local signal event
     * 
     * @param context
     * @param signalData
     */
    public void checkCatchLocalSignalEventMappings(String context, SignalData signalData) {
        ScriptDataMapper mappings = signalData.getOutputScriptDataMapper();

        assertTrue(context + "DataMapper output mappings should have been created.", mappings != null); //$NON-NLS-1$

        assertTrue(context + "There should be 7 output DataMappings", mappings.getDataMappings().size() == 7); //$NON-NLS-1$

        assertTrue(context + "Catch global signal mapper context should be 'LocalSignalCatch'", //$NON-NLS-1$
                "LocalSignalCatch".equals(mappings.getMapperContext())); //$NON-NLS-1$

        boolean foundCorrectStdMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapFromLocalSignal.DataMapperContent" //$NON-NLS-1$
                    .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()))
                    && "MapFromLocalSignalTarget.DataMapperContent" //$NON-NLS-1$
                            .equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "TextField2".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "FromScriptField".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$
                foundCorrectStdMapping = true;
                break;
            }
        }
        assertTrue(context
                + "Expected to find a correct standard mapping example (from FromScriptField to TextField2)", //$NON-NLS-1$
                foundCorrectStdMapping);

        boolean foundCorrectScriptMapping = false;
        for (DataMapping dataMapping : mappings.getDataMappings()) {
            if ("MapFromLocalSignalTarget.DataMapperContent".equals(Xpdl2ModelUtil.getOtherAttribute(dataMapping, //$NON-NLS-1$
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()))
                    && DirectionType.IN_LITERAL.equals(dataMapping.getDirection())
                    && "FromScriptField".equals(dataMapping.getFormal()) && dataMapping.getActual() != null //$NON-NLS-1$
                    && "__SCRIPT__".equals(dataMapping.getActual().getText())) { //$NON-NLS-1$

                ScriptInformation scriptInformation = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());

                if (scriptInformation != null && "Script Mapping".equals(scriptInformation.getName()) //$NON-NLS-1$
                        && scriptInformation.getExpression() != null && "\"abc\" + \"xyz\";" //$NON-NLS-1$
                                .equals(scriptInformation.getExpression().getText())) {
                    foundCorrectScriptMapping = true;
                    break;
                }
            }
        }
        assertTrue(context + "Expected to find a correct script mapping example (from Script to FromScriptField)", //$NON-NLS-1$
                foundCorrectScriptMapping);

        /* Check that even the unmapped scripts have been moved. */
        assertTrue(context + "Expected to find 1 unmapped script mapping", //$NON-NLS-1$
                mappings.getUnmappedScripts().size() == 1);

    }

}
