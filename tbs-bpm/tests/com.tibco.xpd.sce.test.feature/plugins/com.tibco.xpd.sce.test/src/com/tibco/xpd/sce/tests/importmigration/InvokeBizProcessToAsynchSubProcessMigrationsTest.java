/*
 * Copyright (c) Cloud Software Group 2004, 2024. All rights reserved.
 */

package com.tibco.xpd.sce.tests.importmigration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExecutionType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import junit.framework.TestCase;

/**
 * ACE-7095 Test for migration of Business Service Send Tasks with "Invoke Business Process" implementation type to
 * equivalent "Asynch Sub-Process invocation" tasks.
 * 
 * Where source send task can be either JavaScript mapping Grammar or DataMapper mapping grammar.
 * 
 * @author aallway
 * @since 13 Feb 2024
 */
@SuppressWarnings("nls")
public class InvokeBizProcessToAsynchSubProcessMigrationsTest extends TestCase {

    // @Test
	public void testInvokeBusinessProcessMigration()
	{
		ProjectImporter projectImporter = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //
				new String[]{"resources/ImportMigrationTests/ACE7095InvokeBusinessProcessData/", //
						"resources/ImportMigrationTests/ACE7095InvokeBusinessProcesses/"}, //
				new String[]{"ACE7095InvokeBusinessProcessData", "ACE7095InvokeBusinessProcesses"});

		assertTrue("Failed to load projects from resources/ImportMigrationTests/", projectImporter != null);
        try {
            TestUtil.buildAndWait();

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("ACE7095InvokeBusinessProcesses");

            /*
             * Seem to occasionally get a Forms Resource 11.x issue (The project natures, special folders etc do not
             * match the asset configuration)
             */
            List<String> okProblems = new ArrayList<String>();
			okProblems.add("Last statement must be a return"); // - Expected error (script mappings need a
                                                                 // return statement for data-mapper
			// Expected error, there is a WSDL service call based invocation in test project
			okProblems.add(
					"Incoming web-service message are not supported. Use 'Incoming Request' events/tasks instead and invoke these thru run-time API if required.");

			// SOAP participants used for the invokeBusinessProcess tasks will have been converted to REST participants.
			okProblems.add("REST Service participants must have Shared Resource Name set.");

			// There will still be a web service provider for the message start event
			okProblems.add("System participants must have a shared resource type selected.");

			// New message when converted asynch sub-proc task calls message starter.
			okProblems.add(
					"The invoked process must have a Start Request Event to be invoked from a sub-process task (message events are no longer supported). Change the event type or select a different process.");

			// The expected error for WSDL service send task calling business process message startt event
			okProblems.add(
					"Web-service invocation activities are not supported. Use REST service activities to invoke 3rd party services or asynchronous sub-process for invoking business processes from business service or pageflow processes");
			
			// Sometimes get these unrelated forms problems.
			okProblems.add("Forms Resources 11.x"); // -

            assertFalse(
					"MigrateMappings project should have migrated with only a few expected problems.",
                    TestUtil.hasErrorProblemMarker(project,
                            true,
                            okProblems,
							"testDataMappingMigration")); //


			/*
			 * Check that a call from Biz Service to Biz process that was already an asynch sub-process call remain
			 * untouched.
			 */
			checkSubProcessCallAndMappings("_hmtC4MmkEe6Q4NrtYwk5GA", "_hoValMmkEe6Q4NrtYwk5GA",
					"_CoSfYMmjEe6Q4NrtYwk5GA", null, new ExpectedSubProcessInputMapping[]{ //
							new ExpectedSubProcessInputMapping(false, "SourceNum", "Num"), //
							new ExpectedSubProcessInputMapping(false, "SourceText", "Text"), //
							new ExpectedSubProcessInputMapping(true, "return data.SourceBOM.text + \"!!!\";",
									"BOM.text"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.number", //
									"BOM.bizdata2.number"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.subtext", //
									"BOM.bizdata2.subtext"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.bizdata3", //
									"BOM.bizdata2.bizdata3"), //
					});

			/*
			 * Check that a call from Biz Service to Biz process that was formerly a send task with Invoke Business
			 * Process implementation type and DATAMAPPER grammar mappings is converted to asynch sub-proc
			 */
			checkSubProcessCallAndMappings("_PhO10MmlEe6Q4NrtYwk5GA", "_Pi8tFMmlEe6Q4NrtYwk5GA",
					"_9DK_EsmkEe6Q4NrtYwk5GA", null, new ExpectedSubProcessInputMapping[]{ //
							new ExpectedSubProcessInputMapping(true, "return data.SourceBOM.text = \"abc!\";",
									"BOM.text"), //
							new ExpectedSubProcessInputMapping(false, "SourceNum", "Num"), //
							new ExpectedSubProcessInputMapping(false, "SourceText", "Text"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.number", //
									"BOM.bizdata2.number"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.subtext", //
									"BOM.bizdata2.subtext"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.bizdata3", //
									"BOM.bizdata2.bizdata3"), //
					});

			/*
			 * Check that a call from Biz Service to Biz process that was formerly a send task with Invoke Business
			 * Process implementation type and JAVASCRIPT grammar mappings is converted to asynch sub-proc with
			 * DATAMAPPER grammar mappings.
			 */
			checkSubProcessCallAndMappings("_5CR_sMmoEe633omd1oOMLg", "_5CR_tcmoEe633omd1oOMLg",
					"_9DK_EsmkEe6Q4NrtYwk5GA", null, new ExpectedSubProcessInputMapping[]{ //
							new ExpectedSubProcessInputMapping(false, "SourceNum", "Num"), //
							new ExpectedSubProcessInputMapping(false, "SourceText", "Text"), //
							new ExpectedSubProcessInputMapping(true,
									"if (data.SourceBOM.text != null) { data.SourceBOM.text + \"xyz!!\";} else { \"\";}",
									"BOM.text"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.number", //
									"BOM.bizdata2.number"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.subtext", //
									"BOM.bizdata2.subtext"), //
							new ExpectedSubProcessInputMapping(false, "SourceBOM.bizdata2.bizdata3", //
									"BOM.bizdata2.bizdata3"), //
					});

        } finally {
            if (projectImporter != null) {
                projectImporter.performDelete();
            }
        }

    }

	/**
	 * Check that the given activity in given process is configured as an asynch sub-process call to the given process
	 * and has the given DataMapper grammar input mappings.
	 * 
	 * @param processId
	 * @param activityId
	 * @param invokedProcessPackage
	 * @param invokedProcessId
	 * @param expectedInputMappings
	 */
	private void checkSubProcessCallAndMappings(String processId, String activityId, String invokedProcessId,
			String invokedProcessPackage, ExpectedSubProcessInputMapping[] expectedInputMappings)
	{
		Process process = ProcessUIUtil.getPageflowById(processId); // -
                                                                                  // "SubProcessMappingsMainProcess"
		Activity activity = process.getActivity(activityId); // - "JavaScriptSubProcess"

        SubFlow subFlow = (SubFlow) activity.getImplementation();

		/* Check that the sub-process reference remains intact */
		assertEquals(invokedProcessId, subFlow.getProcessId());
		assertEquals(invokedProcessPackage, subFlow.getPackageRefId());

		assertEquals(ExecutionType.ASYNCHR_LITERAL, subFlow.getExecution());
		assertEquals(SubProcessStartStrategy.SCHEDULE_START, Xpdl2ModelUtil.getOtherAttribute(subFlow,
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_StartStrategy()));
		assertEquals(AsyncExecutionMode.DETACHED, Xpdl2ModelUtil.getOtherAttribute(subFlow,
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_AsyncExecutionMode()));
		assertEquals(false, Xpdl2ModelUtil.getOtherAttribute(subFlow,
				XpdExtensionPackage.eINSTANCE.getDocumentRoot_SuspendResumeWithParent()));

        /*
         * Check input mappings
         */
        ScriptDataMapper inputMappings = (ScriptDataMapper) Xpdl2ModelUtil.getOtherElement(subFlow,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings());

		assertNotNull(inputMappings);
		assertEquals("ProcessToSubProcess", inputMappings.getMapperContext());
		assertEquals(DirectionType.IN_LITERAL, inputMappings.getMappingDirection());


		checkExpectedSubProcessInputMappings(
				expectedInputMappings,
				inputMappings.getDataMappings());

		/*
		 * If the activity used to have performers these should have been removed.
		 */
		assertEquals(0, activity.getPerformerList().size());
		assertNull(activity.getPerformer());
	}

	/**
	 * Compare expected data mappings with actual sub-process input mappings.
	 * 
	 * @param expectedInputMappings
	 * @param actualDataMappings
	 */
	private void checkExpectedSubProcessInputMappings(ExpectedSubProcessInputMapping[] expectedInputMappings,
			EList<DataMapping> actualDataMappings)
	{
		assertEquals(expectedInputMappings.length, actualDataMappings.size());

		for (int i = 0; i < expectedInputMappings.length; i++)
		{
			ExpectedSubProcessInputMapping expected = expectedInputMappings[i];

			expected.assertEqualsDataMapping(actualDataMappings.get(i));
		}
	}

	/**
	 * Data class for holding expected data mapping info for a sub-process input mapping
	 * 
	 * For script mappings a comparison is made after all whitespace removed from script.
	 */
	private class ExpectedSubProcessInputMapping
	{
		String	from;
		String	to;

		boolean	isScriptMapping;

		public ExpectedSubProcessInputMapping(boolean isScriptMapping, String from, String to)
		{
			super();
			this.from = from;
			this.to = to;
			this.isScriptMapping = isScriptMapping;
		}

		/**
		 * Compare and assert the given data mappings against the expected from / to and also the standard sub-process
		 * input mapping properties
		 * 
		 * @param dataMapping
		 */
		public void assertEqualsDataMapping(DataMapping dataMapping)
		{
			// Compare basic 'all sub-proc input mappings' properties
			if (!isScriptMapping)
			{
				assertEquals("ActivityInterface.DataMapperContent",
						(String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
								XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()));
			}
			else
			{
				assertNull(Xpdl2ModelUtil.getOtherAttribute(dataMapping,
						XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId()));
			}
			assertEquals("ProcessToSubProcess.DataMapperContent", (String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId()));
			assertEquals(DirectionType.IN_LITERAL, dataMapping.getDirection());

			// Check From
			if (!isScriptMapping)
			{
				assertNotNull(dataMapping.getActual());
				assertEquals("JavaScript", dataMapping.getActual().getScriptGrammar());
				assertEquals(from, dataMapping.getActual().getText());
			}
			else
			{
				assertNotNull(dataMapping.getActual());
				assertEquals("JavaScript", dataMapping.getActual().getScriptGrammar());
				assertEquals("__SCRIPT__", dataMapping.getActual().getText());

				ScriptInformation scriptInfo = (ScriptInformation) Xpdl2ModelUtil.getOtherElement(dataMapping,
						XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script());
				assertNotNull(scriptInfo);
				assertNotNull(scriptInfo.getExpression());

				assertEquals("JavaScript", scriptInfo.getExpression().getScriptGrammar());

				assertNotNull(scriptInfo.getExpression().getText());
				assertEquals(from.replaceAll("\\s+", ""), scriptInfo.getExpression().getText().replaceAll("\\s+", ""));

			}

			// Check To
			assertEquals(to, dataMapping.getFormal());
		}

	}


}
