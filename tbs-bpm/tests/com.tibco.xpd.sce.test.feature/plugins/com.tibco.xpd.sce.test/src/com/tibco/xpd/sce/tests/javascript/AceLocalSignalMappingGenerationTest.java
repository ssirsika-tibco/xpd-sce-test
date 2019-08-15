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
 * Test datamapper script generation for Local Signals
 *
 * @author aallway
 * @since 29 Jul 2019
 */
public class AceLocalSignalMappingGenerationTest extends TestCase {

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
        if (projectImporter != null) {
            projectImporter.performDelete();
        }
        super.tearDown();
    }

    // @Test
    public void testCatchLocalSignalMappingScriptGeneration() {

        Process process = ProcessUIUtil.getProcesById("_QKiX8LOkEemyOeCi73RnLA"); //$NON-NLS-1$

        assertNotNull("Cannot find test process 'LocalSignalProcess-Process' (_QKiX8LOkEemyOeCi73RnLA)", //$NON-NLS-1$
                process);

        checkCatchGlobalSignalScriptGeneration(process);

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
                "_iiLdoLOkEemyOeCi73RnLA"); //$NON-NLS-1$

        assertNotNull(
                "Cannot find test activity 'Catch: Signal1' (_iiLdoLOkEemyOeCi73RnLA)", //$NON-NLS-1$
                activity);

        /* Generate the script for the activity. */
        SignalData signalData = getEventSignalData(activity);
        
        
        assertTrue(
                "Cannot find data mappings in 'Catch: Signal1' (_iiLdoLOkEemyOeCi73RnLA)", //$NON-NLS-1$
                signalData != null && signalData.getOutputScriptDataMapper() != null);

        String script = new DataMapperJavascriptGenerator()
                .convertMappingsToJavascript(signalData.getOutputScriptDataMapper());

        assertNotNull(
                "Script generation failed for 'Catch: Signal1' (_iiLdoLOkEemyOeCi73RnLA)", //$NON-NLS-1$
                script);

        /* Do some general 'all scripts must not' checking. */
        String context = "Catch local signal"; //$NON-NLS-1$
        generalMappingScriptChecks(script, context);

        /*
         * Check some specifics of the Local Signal mappings.
         */
        assertTrue(context
                + ": Local Signal payload simple data should be prefixed with SIGNAL_.", //$NON-NLS-1$
                script.contains("= SIGNAL_TextField;")); //$NON-NLS-1$

        assertTrue(context
                + ": Local Signal payload complex data sources should be prefixed with SIGNAL_.", //$NON-NLS-1$
                script.contains("= SIGNAL_ComplexField.class2.attribute1;")); //$NON-NLS-1$

        assertTrue(context + ": Local Signal payload loops should work with SIGNAL_<payloadparam>.length.", //$NON-NLS-1$
                script.contains("< SIGNAL_ComplexArrayField.length;")); //$NON-NLS-1$

        assertTrue(context + ": Local Signal payload arrays should use SIGNAL_<payloadparam>[xxxx] for array access.", //$NON-NLS-1$
                script.contains("= SIGNAL_ComplexArrayField[")); //$NON-NLS-1$

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
                        "resources/AceLocalSignalMappingDeployTest/LocalSignalData/", //$NON-NLS-1$
                        "resources/AceLocalSignalMappingDeployTest/LocalSignalProcess/" }, //$NON-NLS-1$
                new String[] { "LocalSignalData", //$NON-NLS-1$
                        "LocalSignalProcess" }); //$NON-NLS-1$

        assertTrue(
                "Failed to load projects from resources/AceLocalSignalMappingDeployTest/", //$NON-NLS-1$
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }


}
