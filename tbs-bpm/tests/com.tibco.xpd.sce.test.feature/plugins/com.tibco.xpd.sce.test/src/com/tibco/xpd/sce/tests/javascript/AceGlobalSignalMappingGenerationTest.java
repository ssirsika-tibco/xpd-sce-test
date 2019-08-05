/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.javascript;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Test datamapper script generation for global signals
 *
 * @author aallway
 * @since 29 Jul 2019
 */
public class AceGlobalSignalMappingGenerationTest extends TestCase {

    private ProjectImporter projectImporter;

    /**
     * @see junit.framework.TestCase#setUp()
     *
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        projectImporter = importMainTestProjects();
    }

    /**
     * @see junit.framework.TestCase#tearDown()
     *
     * @throws Exception
     */
    @Override
    protected void tearDown() throws Exception {
        projectImporter.performDelete();
        super.tearDown();
    }

    // @Test
    public void testDataIsWrappedInAllScriptScenarios() {

        Process process = ProcessUIUtil.getProcesById("_1j4skLH6Eemhueri8__qmA"); //$NON-NLS-1$

        assertNotNull("Cannot find test process 'GlobalSignalProcess-Process' (_1j4skLH6Eemhueri8__qmA)", //$NON-NLS-1$
                process);

        checkCatchGlobalSignalScriptGeneration(process);

        checkThrowSignalScriptGeneration(process);

    }

    /**
     * Test process data mapper script task. (checking that the various changes
     * from AMX BPM EMF based scripting have been made to deal with the JS based
     * ACE scripting).
     * 
     * @param process
     */
    private void checkCatchGlobalSignalScriptGeneration(Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process,
                "_uyPkMLH7Eemhueri8__qmA"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'Event Handler' (_uyPkMLH7Eemhueri8__qmA)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        SignalData signalData = getEventSignalData(activity);
        
        
        assertTrue(
                "Cannot find data mappings in 'Event Handler' (_uyPkMLH7Eemhueri8__qmA)", //$NON-NLS-1$
                signalData != null && signalData.getOutputScriptDataMapper() != null);

        String script = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript(signalData.getOutputScriptDataMapper());

        assertNotNull(
                "Script generation failed for 'Event Handler' (_uyPkMLH7Eemhueri8__qmA)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "Catch global signal"; //$NON-NLS-1$
        generalMappingScriptChecks(script, context);

        /*
         * Check some specifics of the global signal mappings.
         */
        assertTrue(context
                + ": Global Signal payload simple data should be prefixed with SIGNAL_.", //$NON-NLS-1$
                script.contains("= SIGNAL_TextPayload;")); //$NON-NLS-1$

        assertTrue(context
                + ": Global Signal payload complex data should be prefixed with SIGNAL_ and not ScriptUtil.copy()'d.", //$NON-NLS-1$
                script.contains("= SIGNAL_ComplexPayload;")); //$NON-NLS-1$

        assertTrue(context + ": Global Signal payload loops should work with SIGNAL_<payloadparam>.length.", //$NON-NLS-1$
                script.contains("< SIGNAL_ComplexArrayPayload.length;")); //$NON-NLS-1$

        assertTrue(context + ": Global Signal payload arrays should use SIGNAL_<payloadparam>[xxxx] for array access.", //$NON-NLS-1$
                script.contains("= SIGNAL_ComplexArrayPayload[")); //$NON-NLS-1$

        /*
         * Correlation mappings should not be included in mapping script.
         */
        assertFalse(context + ": Global signal mapping script should not contain correlation data mappings", //$NON-NLS-1$
                script.contains("Correlation")); //$NON-NLS-1$



    }

    /**
     * @param activity
     * @return The SignalData extension element from the given throw/catch
     *         signal event activity
     */
    private SignalData getEventSignalData(Activity activity) {
        SignalData signalData = null;
        
        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {
            signalData = (SignalData) Xpdl2ModelUtil.getOtherElement(
                    ((TriggerResultSignal) activity.getEvent().getEventTriggerTypeNode()),
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_SignalData());
        }
        return signalData;
    }

    /**
     * Tests sub-process data mappings (process side data only) where complex
     * type data is directly mapped (don't create-and-inflate target objects).
     * 
     * @param process
     */
    private void checkThrowSignalScriptGeneration(
            Process process) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, "_gNx1MbH7Eemhueri8__qmA"); //$NON-NLS-1$

        assertNotNull("Cannot find test activity 'Throw Intermediate' (_gNx1MbH7Eemhueri8__qmA)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        SignalData signalData = getEventSignalData(activity);

        assertTrue("Cannot find data mappings in 'Throw Intermediate' (_gNx1MbH7Eemhueri8__qmA)", //$NON-NLS-1$
                signalData != null && signalData.getInputScriptDataMapper() != null);

        String script =
                new DataMapperJavascriptGenerator().convertMappingsToJavascript(signalData.getInputScriptDataMapper());

        assertNotNull("Script generation failed for 'Throw Intermediate' (_gNx1MbH7Eemhueri8__qmA)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "Throw global signal"; //$NON-NLS-1$
        generalMappingScriptChecks(script, context);

        /*
         * Check some specifics of the global signal mappings.
         */
        assertTrue(context + ": Global Signal payload simple data should be prefixed with SIGNAL_.", //$NON-NLS-1$
                script.contains("SIGNAL_TextPayload =")); //$NON-NLS-1$

        assertTrue(context + ": Global Signal payload complex data should be prefixed with SIGNAL_.", //$NON-NLS-1$
                script.contains("SIGNAL_ComplexPayload =")); //$NON-NLS-1$

        assertTrue(context + ": Global Signal payload arrays should use push SIGNAL_<payloadparam>.push", //$NON-NLS-1$
                script.contains("SIGNAL_ComplexArrayPayload.push(")); //$NON-NLS-1$

        assertTrue(context + ": Global Signal payload arrays should be initialised with SIGNAL_payloadArray.length = 0", //$NON-NLS-1$
                script.contains("SIGNAL_ComplexArrayPayload.length = 0")); //$NON-NLS-1$

        /*
         * Correlation mappings should not be included in mapping script.
         */
        assertTrue(context
                + ": Global signal mapping script SHOULD contain normal data mappings for correlation for THROW signals", //$NON-NLS-1$
                script.contains("Correlation")); //$NON-NLS-1$


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
                        "resources/AceGlobalSignalMappingDeployTest/GlobalSignalData/", //$NON-NLS-1$
                        "resources/AceGlobalSignalMappingDeployTest/GlobalSignal/", //$NON-NLS-1$
                        "resources/AceGlobalSignalMappingDeployTest/GlobalSignalProcess/" }, //$NON-NLS-1$
                new String[] { "GlobalSignalData", //$NON-NLS-1$
                        "GlobalSignal", //$NON-NLS-1$
                        "GlobalSignalProcess" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from resources/AceGlobalSignalMappingDeployTest/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }


}
