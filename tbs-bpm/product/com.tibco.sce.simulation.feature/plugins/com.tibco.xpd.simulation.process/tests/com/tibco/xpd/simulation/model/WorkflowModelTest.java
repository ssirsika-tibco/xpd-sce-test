package com.tibco.xpd.simulation.model;

import com.tibco.xpd.simulation.preprocess.XpdlBaseTestCase;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Tests WorkflowModel class.
 * 
 * @author tstephen
 * 
 */
public class WorkflowModelTest extends XpdlBaseTestCase {

	private static final String TEST_XPDL_FILE 
			= "test-files/SimpleWorkflow.xpdl"; //$NON-NLS-1$

	private static Package xpdlPackage;

	private WorkflowModel model;

	private Process xpdlProcess;

	protected void setUp() throws Exception {
		super.setUp();

		// Read xpdl file into EMF model
//		File f = new File(TEST_XPDL_FILE); //$NON-NLS-1$
//		assertTrue("Cannot find test xpdl file", f.exists()) ; 
//		String xpdlPath = f.getAbsolutePath();
//		// Inexplicably this does not seem to work in this test case 
//		xpdlPackage = TestUtil.getXPDLPackage(xpdlPath);
//
//		xpdlProcess = (WorkflowProcess) xpdlPackage
//						.getWorkflowProcesses().get(0);
//		model = new WorkflowModel(null,
//						"A test simulation model", true, true, xpdlPackage, 
//						xpdlProcess.getId(), null);
	}

	public void test10SetParticipantsCorrectly() {
		
		// TODO What about if same participant exists in both 
		// package and process? 
//		assertEquals("Incorrect Participant Map", 
//				xpdlPackage.getParticipants().size() +  
//				xpdlProcess.getParticipants().size(), 
//				model.getParticipants().size()) ;
	}

	public void test20SetCaseGenerationIntervalTimeCorrectly() {
		//assertEquals("Incorrect case start distribution", 
		//		sim, 
		//		model.getCaseGenerationInterval().getName()) ;
	}

	public void test30SetApplicationExecutionTimesCorrectly() {

	}

	public void test40SetApplicationParticipantMapCorrectly() {

	}

}
