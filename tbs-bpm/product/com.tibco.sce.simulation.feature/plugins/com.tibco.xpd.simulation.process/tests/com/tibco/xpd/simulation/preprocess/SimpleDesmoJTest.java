/* 
 ** 
 **  MODULE:             $RCSfile: SimpleDesmoJTest.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-09-21 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess;

import java.io.File;

import com.tibco.xpd.xpdl2.Package;

public class SimpleDesmoJTest extends XpdlBaseTestCase {

    private static final String TEST_FILE_FOLDER = "test-files/"; //$NON-NLS-1$
    private static final String TEST_XPDL_FILE = "TestSimWorkflow.xpdl"; //$NON-NLS-1$
    private static final String OUT_FILE = "TestSimWorkflowOut.xpdl"; //$NON-NLS-1$
    private static final String PROCESS_ID = "1"; //$NON-NLS-1$
    private Package xpdlPackage;

    protected void setUp() throws Exception {
        super.setUp();

        File f = new File(TEST_FILE_FOLDER + TEST_XPDL_FILE); //$NON-NLS-1$
        String xpdlUrl = f.getAbsolutePath();
        xpdlPackage = TestUtil.getXPDLPackage(xpdlUrl);
        File outFile = new File(TEST_FILE_FOLDER + OUT_FILE);
        /*
         * if (outFile.exists()) { outFile.delete(); }
         */
    }

    protected void tearDown() throws Exception {
        if (xpdlPackage != null) {
            xpdlPackage.eResource().unload();
            xpdlPackage = null;
        }
        super.tearDown();
    }

    /**
     * Sample test.
     */
    public void testPackageLoading() {
        assertNotNull("Package is not loaded.", xpdlPackage); //$NON-NLS-1$
    }

    /**
     * Runs the process with only root activities on InteractionEngine.
     * 
     * @throws MalformedURLException
     */
    /*public void testSimulation() throws MalformedURLException {
        System.out.println(" testSimulation START ======"); //$NON-NLS-1$

        String packageName = TEST_XPDL_FILE;
        String processId = PROCESS_ID;
        // setup the repository url
        File f = new File(TEST_FILE_FOLDER + TEST_XPDL_FILE);
        URL repositoryLocation = f.getParentFile().toURL();
        
        //simulation data generation
        System.out.println(" START simulation data generation ======"); //$NON-NLS-1$
        ProcessManager.INSTANCE.generateSimData(xpdlPackage);
        try {
            OutputStream out = new FileOutputStream(f);
            xpdlPackage.eResource().save(out, Collections.EMPTY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
        } 
        System.out.println(" END simulation data generation ======"); //$NON-NLS-1$
        

        // create model and experiment        
        WorkflowModel model = ProcessManager.INSTANCE.createWorkflowModel(processId, packageName,  repositoryLocation);
                
//        WorkflowModel model = new WorkflowModel(null,
//                "Simple Process-Oriented Workflow Model", true, true, //$NON-NLS-1$
//                xpdlPackage,  processName, repositoryLocation); 
        // null as first parameter because it is the main model and has no
        // mastermodel
        Experiment exp = new Experiment("ProcessesWorkflowExperiment"); //$NON-NLS-1$
        // ATTENTION, since the name of the experiment is used in the names
        // of the
        // output files, you have to specify a string that's compatible with
        // the
        // filename constraints of your computer's operating system.
        model.connectToExperiment(exp);

        exp.setShowProgressBar(true); // display a progress bar (or not)
        exp.stop(new SimTime(1500)); // set end of simulation at 1500 time
        // units
        exp.tracePeriod(new SimTime(0.0), new SimTime(100));
        // set the period of the
        // trace
        exp.debugPeriod(new SimTime(0.0), new SimTime(50)); // and debug
        // output

        // start the experiment at simulation time 0.0
        exp.start();

        // --> now the simulation is running until it reaches its end criterion
        // ...
        // ...
        // <-- afterwards, the main thread returns here

        // generate the report (and other output files)
        exp.report();

        // stop all threads still alive and close all output files
        exp.finish();
        
        System.out.println(" testSimulation END ======"); //$NON-NLS-1$

    }*/
}
