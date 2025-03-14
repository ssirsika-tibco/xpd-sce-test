/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.sce.tests.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.ValidationsTestProblemMarkerInfo;
import com.tibco.xpd.n2.test.core.validation.AbstractN2BaseValidationTest;
import com.tibco.xpd.resources.util.ProjectImporter;

/**
 * PSLValidBPMScriptsVariableAssignmentValidationTest
 * <p>
 * PSLValidBPMScriptsVariableAssignmentValidationTest - Test selected validations are correctly raised.
 * </p>
 * <p>
 * Generated by: createBaseTest.javajet
 * </p>
 *
 * 
 * Check the No Validations are raised for Project's with valid PSL 'bpmScripts' variable assignment.
 *
 * @author cbabar
 * @since Apr 18, 2024
 */
public class PSLValidBPMScriptsVariableAssignmentValidationTest extends AbstractN2BaseValidationTest {

	private ProjectImporter	projectImporterProcessProject;

	private ProjectImporter	projectImporterScriptLibrary0;

	private ProjectImporter	projectImporterScriptLibrary1;

	public PSLValidBPMScriptsVariableAssignmentValidationTest() {
		super(true);
	}

	/**
     * PSLValidBPMScriptsVariableAssignmentValidationTest
     * 
     * @throws Exception
     */
    public void testPSLValidBPMScriptsVariableAssignmentValidationTest() throws Exception {
		doTestValidations();

		/*
		 * Project with valid PSL 'bpmScripts' variable assignment - so should have no problems.
		 */
		IProject scriptLib0Project = ResourcesPlugin.getWorkspace().getRoot().getProject("ScriptLibrary_0");

		assertFalse("PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_0" //$NON-NLS-1$
				+ " project should not have any ERROR level problem markers: \n" //$NON-NLS-1$
				+ TestUtil.getErrorProblemMarkerList(scriptLib0Project, true),
				TestUtil.hasErrorProblemMarker(
						scriptLib0Project, // $NON-NLS-1$
						true, "testPSLValidBPMScriptsVariableAssignmentValidationTest"));

		/*
		 * Project with valid PSL 'bpmScripts' variable assignment - so should have no problems.
		 */
		IProject scriptLib1Project = ResourcesPlugin.getWorkspace().getRoot().getProject("ScriptLibrary_1");

		assertFalse("PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_1" //$NON-NLS-1$
				+ " project should not have any ERROR level problem markers: \n" //$NON-NLS-1$
				+ TestUtil.getErrorProblemMarkerList(scriptLib1Project, true),
				TestUtil.hasErrorProblemMarker(scriptLib1Project, // $NON-NLS-1$
						true, "testPSLValidBPMScriptsVariableAssignmentValidationTest"));

		/*
		 * Project with valid PSL 'bpmScripts' variable assignment - so should have no problems.
		 */
		IProject processProject = ResourcesPlugin.getWorkspace().getRoot().getProject("ProcessProject");

		assertFalse("PSLValidBPMScriptsVariableAssignmentValidationTest/ProcessProject" //$NON-NLS-1$
				+ " project should not have any ERROR level problem markers: \n" //$NON-NLS-1$
				+ TestUtil.getErrorProblemMarkerList(processProject, true),
				TestUtil.hasErrorProblemMarker(processProject, // $NON-NLS-1$
						true, "testPSLValidBPMScriptsVariableAssignmentValidationTest"));

        return;
	}

	@Override
    protected ValidationsTestProblemMarkerInfo[] getValidationProblemMarkerInfos() {
		ValidationsTestProblemMarkerInfo[] markerInfos = new ValidationsTestProblemMarkerInfo[]{};
        return markerInfos;
    }

    @Override
    protected String getTestName() {
        return "PSLValidBPMScriptsVariableAssignmentValidationTest"; //$NON-NLS-1$
    }

    @Override
    protected String getTestPlugInId() {
        return "com.tibco.xpd.sce.test"; //$NON-NLS-1$
    }

    @Override
    protected TestResourceInfo[] getTestResources() {
        TestResourceInfo[] testResources = new TestResourceInfo[] {
        };
    
        return testResources;
    }

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#customTestResourceSetup()
	 *
	 */
	@Override
	protected void customTestResourceSetup()
	{

		// Import project which should not have any problem markers after the validation
		projectImporterProcessProject = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
				new String[]{"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ProcessProject/"}, //$NON-NLS-1$
				new String[]{"ProcessProject"}); // $NON-NLS-1$ //$NON-NLS-1$

		assertTrue(
				"Failed to load projects from \"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ProcessProject\"", //$NON-NLS-1$
				projectImporterProcessProject != null);

		// Import project which should not have any problem markers after the validation
		projectImporterScriptLibrary0 = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
				new String[]{"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_0/"}, //$NON-NLS-1$
				new String[]{"ScriptLibrary_0"}); // $NON-NLS-1$ //$NON-NLS-1$

		assertTrue(
				"Failed to load projects from \"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_0\"", //$NON-NLS-1$
				projectImporterScriptLibrary0 != null);

		// Import project which should not have any problem markers after the validation
		projectImporterScriptLibrary1 = TestUtil.importProjectsFromZip("com.tibco.xpd.sce.test", //$NON-NLS-1$
				new String[]{"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_1/"}, //$NON-NLS-1$
				new String[]{"ScriptLibrary_1"}); // $NON-NLS-1$ //$NON-NLS-1$

		assertTrue(
				"Failed to load projects from \"resources/PSLValidBPMScriptsVariableAssignmentValidationTest/ScriptLibrary_1\"", //$NON-NLS-1$
				projectImporterScriptLibrary1 != null);

	}

	/**
	 * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#tearDown()
	 *
	 * @throws Exception
	 */
	@Override
	protected void tearDown() throws Exception
	{
		if (projectImporterProcessProject != null)
		{
			projectImporterProcessProject.performDelete();
		}

		if (projectImporterScriptLibrary1 != null)
		{
			projectImporterScriptLibrary1.performDelete();
		}

		if (projectImporterScriptLibrary0 != null)
		{
			projectImporterScriptLibrary0.performDelete();
		}

		super.tearDown();
	}

}
