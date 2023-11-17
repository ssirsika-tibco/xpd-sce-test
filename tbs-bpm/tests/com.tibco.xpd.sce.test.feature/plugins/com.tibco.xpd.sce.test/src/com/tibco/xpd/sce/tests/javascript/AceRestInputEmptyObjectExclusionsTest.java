/*
 * Copyright (c) 2004-2023. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.sce.tests.javascript;

import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * Sid ACE-6808 Tests the correct function and script generation for the REST task input mapping configurations..
 * <li>Exclude empty objects for optional REST input properties</li>
 * <li>Exclude empty arrays for optional REST input properties</li>
 * <li>Exclude empty objects from REST input arrays</li>
 * 
 * @author aallway
 * @since Mar 2023
 */
@SuppressWarnings("nls")
public class AceRestInputEmptyObjectExclusionsTest extends TestCase {

    /**
     * Various script fragments with whitespace removed (for comparison with generate script)
     */

    private static final String REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT =
            "if (!!REST_PAYLOAD['child'] && Object.getPrototypeOf(REST_PAYLOAD['child']) === Object.prototype && !Calculation.deepContainsPrimitiveProperties(REST_PAYLOAD['child'])) { delete REST_PAYLOAD['child']; }";

    private static final String REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT =
            "if (!!REST_PAYLOAD['child']['grandChild1'] && Object.getPrototypeOf(REST_PAYLOAD['child']['grandChild1']) === Object.prototype && !Calculation.deepContainsPrimitiveProperties(REST_PAYLOAD['child']['grandChild1'])) { delete REST_PAYLOAD['child']['grandChild1']; }";

    private static final String REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT =
            "if (!!$tVi3['grandChild1'] && Object.getPrototypeOf($tVi3['grandChild1']) === Object.prototype && !Calculation.deepContainsPrimitiveProperties($tVi3['grandChild1'])) { delete $tVi3['grandChild1']; }";

    private static final String REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT =
            "if (Array.isArray(REST_PAYLOAD['child']['grandChildArray']) && REST_PAYLOAD['child']['grandChildArray'].length === 0) { delete REST_PAYLOAD['child']['grandChildArray']; }";

    private static final String REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT =
            "if (Array.isArray($tVi3['childTextArray']) && $tVi3['childTextArray'].length === 0) { delete $tVi3['childTextArray']; }";

    private static final String REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT =
            "if (Array.isArray($tVi3['grandChildArray']) && $tVi3['grandChildArray'].length === 0) { delete $tVi3['grandChildArray']; }";

    private static final String REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT =
            "if (Array.isArray(REST_PAYLOAD['childArray']) && REST_PAYLOAD['childArray'].length === 0) { delete REST_PAYLOAD['childArray']; }";

    private static final String REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT =
            "if (!(!!$tVi2 && Object.getPrototypeOf($tVi2) === Object.prototype && !Calculation.deepContainsPrimitiveProperties($tVi2))) { REST_PAYLOAD['child']['grandChildArray'].push($tVi2); }";

    private static final String REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT =
            "if (!(!!$tVi5 && Object.getPrototypeOf($tVi5) === Object.prototype && !Calculation.deepContainsPrimitiveProperties($tVi5))) { $tVi3['grandChildArray'].push($tVi5); }";

    private static final String REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT =
            "if (!(!!$tVi3 && Object.getPrototypeOf($tVi3) === Object.prototype && !Calculation.deepContainsPrimitiveProperties($tVi3))) { REST_PAYLOAD['childArray'].push($tVi3); } ";

    /**
     * Test various Exclude Empty Object configuration options impact on REST script mapping generation.
     * 
     * ALL done in one test because Plugin Junit tests don't have a single initialisation for ALL tests, so don't want
     * to import and build, then delete projects for every individual test
     */
    public void testEmptyObjectExclusionOptionsScriptGeneration() {
        /*
         * SETUP
         */
        ProjectImporter projectImporter = importMainTestProjects();

        IProject mapperProject = ResourcesPlugin.getWorkspace().getRoot().getProject("CallService");

		assertFalse(
				"CallService project should not have any ERROR level problem markers (other than expected com.tibco.xpd.forms.validation.project.misconfigured) :\n" //$NON-NLS-1$
						+ TestUtil.getErrorProblemMarkerList(mapperProject, true), 
                TestUtil.hasErrorProblemMarker(mapperProject, // $NON-NLS-1$
                        true,
                        Collections.singletonList("com.tibco.xpd.forms.validation.project.misconfigured"),
                        "testDataIsWrappedInAllScriptScenarios")); //$NON-NLS-1$

        /* Process calls service with optional inputs. */
        Process process = (Process) ProcessUIUtil.getAllElements("_eiltRb28Ee28HIOfZkNSRw").get(0);

        /*
         * TEST
         */
        testNoExcludeEmptyOptionsSet(process);

        testAllExcludeEmptyOptionsSet(process);
        
        testOnlyExcludeEmptyOptionalObject(process);
        
        testOnlyExcludeEmptyOptionalArray(process);

        testOnlyExcludeEmptyObjectsFromOptionalArray(process);

        testDeleteEmptyObjectsAfterAllDescendantMappings(process);

        testExcludeEmptyOptionsNotUsedForOutputs(process);

        /* Process calls service with requeired inputs. */
        Process mandatoryInputsProcess = (Process) ProcessUIUtil.getAllElements("_JEWj6L3ZEe28HIOfZkNSRw").get(0);

        testExcludeEmptyOptionsNotUsedForMandatoryInputs(mandatoryInputsProcess);

        /*
         * TEAR DOWN
         */
        if (projectImporter != null) {
            projectImporter.performDelete();
        }
    }

    /**
     * Test that correct script is generated when NONE of the Exclude Empty options are set.
     */
    private void testNoExcludeEmptyOptionsSet(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, false, false, false);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.child",
                !script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.child.grandChild",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.childarray[].grandChild",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].childTextArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testNoExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
     * Test that correct script is generated when only the Exclude Empty Optional Objects is set.
     * 
     * This ensures that individual config options control ONLY the aspect of script mapping we expect it to.
     */
    private void testOnlyExcludeEmptyOptionalObject(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, true, false, false);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should contain Empty Object deletion script for - REST_PAYLOAD.child",
                script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should contain Empty Object deletion script for - REST_PAYLOAD.child.grandChild",
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should contain Empty Object deletion script for - REST_PAYLOAD.childarray[].grandChild",
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Empty Array deletion script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].childTextArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalObject: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
     * Test that correct script is generated when only the Exclude Empty Optional Arrays is set.
     * 
     * This ensures that individual config options control ONLY the aspect of script mapping we expect it to.
     */
    private void testOnlyExcludeEmptyOptionalArray(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, false, true, false);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.child",
                !script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.child.grandChild",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.childarray[].grandChild",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should contain Empty Array deletion script for - REST_PAYLOAD.child.grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should contain Empty Array deletion script for - REST_PAYLOAD.childarray[].childTextArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should contain Empty Array deletion script for - REST_PAYLOAD.childarray[].grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should contain Empty Array deletion script for - REST_PAYLOAD.childarray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyOptionalArray: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
     * Test that correct script is generated when only the Exclude Empty Objects From Optional Arrays is set.
     * 
     * This ensures that individual config options control ONLY the aspect of script mapping we expect it to.
     */
    private void testOnlyExcludeEmptyObjectsFromOptionalArray(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, false, false, true);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.child",
                !script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.child.grandChild",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Object deletion script for - REST_PAYLOAD.childarray[].grandChild",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Array deletion script for - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].childTextArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should contain Exclude Empty Object From Array script for - REST_PAYLOAD.child.grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[].grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testOnlyExcludeEmptyObjectsFromOptionalArray: Should contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
	 * Test that correct script is generated when ALL of the Exclude Empty options are set.
	 * 
	 */
    private void testAllExcludeEmptyOptionsSet(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, true, true, true);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.child\n"
						+ REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.child.grandChild\n"
						+ REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Object deletion script for - REST_PAYLOAD.childarray[].grandChild\n"
						+ REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.child.grandChildArray[]\n"
						+ REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].childTextArray[]\n"
						+ REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[].grandChildArray[]\n"
						+ REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Empty Array deletion script for - REST_PAYLOAD.childarray[]\n"
						+ REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.child.grandChildArray[]\n"
						+ REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT + "\nIn==>\n"
						+ script,
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[].grandChildArray[]\n"
						+ REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT + "\nIn==>\n"
						+ script,
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
				"testAllExcludeEmptyOptionsSet: Should not contain Exclude Empty Object From Array script for - REST_PAYLOAD.childArray[]\n"
						+ REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT + "\nIn==>\n" + script,
                script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
     * Test that the check and Delete Empty Complex Objects is done AFTER all other mappings have been applied to it's
     * descendants
     * 
     * We can do this to ensure that the given target object is not referenced in script after it's deletion script.
     * 
     */
    private void testDeleteEmptyObjectsAfterAllDescendantMappings(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", true, true, true, true);

        /*
         * Exclude Empty Object script fragments...
         */
        int idx = script.indexOf(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT);

        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: REST_PAYLOAD['child'] Delete Empty Object should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT.length())
                        .contains("REST_PAYLOAD['child']"));

        idx = script.indexOf(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: REST_PAYLOAD['child']['grandChild1'] Delete Empty Object should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT.length())
                        .contains("REST['child']['grandChild1']"));

        idx = script.indexOf(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: $tVi3['grandChild1'] Delete Empty Object should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT.length())
                        .contains("$tVi3['grandChild1']"));

        /*
         * Exclude Empty Array script fragments...
         */
        idx = script.indexOf(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: REST_PAYLOAD['child']['grandChildArray'] Delete Empty Array should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT.length())
                        .contains("REST_PAYLOAD['child']['grandChildArray']"));

        idx = script.indexOf(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: $tVi3['childTextArray'] Delete Empty Array should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT.length())
                        .contains("$tVi3['childTextArray']"));

        idx = script.indexOf(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: $tVi3['grandChildArray'] Delete Empty Array should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT.length())
                        .contains("REST_PAYLOAD['childArray']['grandChildArray']"));

        idx = script.indexOf(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT);
        assertTrue(
                "testDeleteEmptyObjectsAfterAllDescendantMappings: REST_PAYLOAD['childArray'] Delete Empty Array should be applied after all descendant mappings applied.",
                !script.substring(idx + REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT.length())
                        .contains("REST_PAYLOAD['childArray']"));
    }

    /**
     * Test that correct script is generated for MANDATORY input properties (should not include empty objects exclusions
     * scripting when target is mandatory).
     */
    private void testExcludeEmptyOptionsNotUsedForMandatoryInputs(Process mandatoryInputsProcess) {
        String script =
                generateMappingScript(mandatoryInputsProcess, "_JEWj673ZEe28HIOfZkNSRw", true, true, true, true);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Object deletion script for Mandatory Inputs - REST_PAYLOAD.child",
                !script.contains(REST_PAYLOAD_CHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Object deletion script for Mandatory Inputs - REST_PAYLOAD.child.grandChild",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Object deletion script for Mandatory Inputs - REST_PAYLOAD.childarray[].grandChild",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILD_DELETE_EMPTY_OBJECT_FRAGMENT));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Array deletion script for Mandatory Inputs - REST_PAYLOAD.child.grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Array deletion script for Mandatory Inputs - REST_PAYLOAD.childarray[].childTextArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_CHILDTEXTARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Array deletion script for Mandatory Inputs - REST_PAYLOAD.childarray[].grandChildArray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Array deletion script for Mandatory Inputs - REST_PAYLOAD.childarray[]",
                !script.contains(REST_PAYLOAD_CHILDARRAY_DELETE_EMPTY_ARRAY_FRAGMENT));

        /*
         * Exclude Empty Object From Array script fragments...
         * 
         * IN THIS CASE, it doesn't matter if the input array is mandatory or input. The option is related ONLY to not
         * adding empty objects to arrays.
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should contain Exclude Empty Object From Array script for Mandatory Inputs - REST_PAYLOAD.child.grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILD_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Exclude Empty Object From Array script for Mandatory Inputs - REST_PAYLOAD.childArray[].grandChildArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_GRANDCHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));

        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should contain Exclude Empty Object From Array script for Mandatory Inputs - REST_PAYLOAD.childArray[]",
                script.contains(REST_PAYLOAD_CHILDARRAY_EXCLUDE_EMPTY_OBJECT_FROM_ARRAY_FRAGMENT));
    }

    /**
     * Test that correct script is generated for Output mapping properties (should not include empty objects exclusions
     * scripting when target is mandatory).
     */
    private void testExcludeEmptyOptionsNotUsedForOutputs(Process process) {
        String script = generateMappingScript(process, "_eiltSL28Ee28HIOfZkNSRw", false, true, true, true);

        /*
         * Exclude Empty Object script fragments...
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Object deletion script for Output Mappings",
                !script.contains("Calculation.deepContainsPrimitiveProperties(data.OutField)"));

        /*
         * Exclude Empty Array script fragments...
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Empty Array deletion script for Output Mappings",
                !script.contains("Calculation.deepContainsPrimitiveProperties(data.OutField.childclassList1)"));

        /*
         * Exclude Empty Object From Array script fragments...
         */
        assertTrue(
                "testExcludeEmptyOptionsNotUsedForMandatoryInputs: Should not contain Exclude Empty Object From Array deletion script for Output Mappings",
                !script.contains("Calculation.deepContainsPrimitiveProperties($tVi3)"));

    }

    /**
     * Generate the input or output mapping script for the given REST service task
     * 
     * @param process
     * @param restServiceTaskId
     * @param isInput
     * @param excludeEmptyObjects
     * @param excludeEmptyArrays
     * @param excludeEmptyObjectsFromArrays
     * 
     * @return the script
     */
    private String generateMappingScript(Process process, String restServiceTaskId, boolean isInput,
            boolean excludeEmptyObjects, boolean excludeEmptyArrays, boolean excludeEmptyObjectsFromArrays) {
        Activity activity = Xpdl2ModelUtil.getActivityById(process, restServiceTaskId);
        Task task = (Task) activity.getImplementation();
        TaskService taskService = task.getTaskService();

        /* Generate the script for the activity. */
        Message restMessage = (isInput ? taskService.getMessageIn() : taskService.getMessageOut());
        assertNotNull(restMessage);

        final ScriptDataMapper scriptDataMapper = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(restMessage,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_ScriptDataMapper());
        assertNotNull(scriptDataMapper);

        /* Set the required options on the data mapper config */
        TransactionalEditingDomain editingDomain =
                (TransactionalEditingDomain) WorkingCopyUtil.getEditingDomain(process);
        RecordingCommand cmd = new RecordingCommand(editingDomain) {
            @Override
            protected void doExecute() {
                scriptDataMapper.setExcludeEmptyOptionalObjects(excludeEmptyObjects);
                scriptDataMapper.setExcludeEmptyOptionalArrays(excludeEmptyArrays);
                scriptDataMapper.setExcludeEmptyObjectsFromArrays(excludeEmptyObjectsFromArrays);

            }
        };
        editingDomain.getCommandStack().execute(cmd);

        String script = new DataMapperJavascriptGenerator().convertMappingsToJavascript(scriptDataMapper);

        assertNotNull(String.format("%s script generation failed for %s/%s",
                (isInput ? "Input" : "Output"),
                process.getName(),
                activity.getName()), script);

        return script;
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

        ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test",
                new String[] { "resources/AceRestInputEmptyObjectExclusionsTest/REST_Services/",
                        "resources/AceRestInputEmptyObjectExclusionsTest/CallServiceData/",
                        "resources/AceRestInputEmptyObjectExclusionsTest/CallService/" },
                new String[] { "REST_Services", "CallServiceData", "CallService" });

        assertTrue("Failed to load projects from resources/AceRestInputEmptyObjectExclusionsTest/",
                projectImporter != null);

        TestUtil.buildAndWait();

        return projectImporter;
    }

}
