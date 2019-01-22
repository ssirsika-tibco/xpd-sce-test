/* 
** 
**  MODULE:             $RCSfile: IntEngAutomaticApplicationTest.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2005-09-06 $ 
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

public class IntEngAutomaticApplicationTest extends XpdlBaseTestCase {

    private static final String SW_INPUT_FIELDS = "swInputFields"; //$NON-NLS-1$
    private static final String SW_OUTPUT_FIELDS = "swOututFields"; //$NON-NLS-1$

    private static final String TEST_FILE_FOLDER = "test-files/"; //$NON-NLS-1$
    private static final String TEST_XPDL_FILE = "AutomaticApplicationOnly.xpdl"; //$NON-NLS-1$
    private static final String OUT_FILE = "AutomaticApplicationOnlyOut.xpdl"; //$NON-NLS-1$
    private static final String PROCESS_NAME = "ClaimsProcess"; //$NON-NLS-1$
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
     * Tests generation of ceses. Check if the num of generated cases is
     */
    /*public void testCaseGeneration() {
        int declaredNoOfCases = 10;
        ProcessManager processManager = new ProcessManagerImpl();
        CaseGenerator cg = processManager.getCaseGenerator(xpdlPackage,
                PROCESS_NAME, declaredNoOfCases); //$NON-NLS-1$
        int noOfCases = 0;
        for (Iterator iter = cg.iterator(); iter.hasNext();) {
            Case caseInstance = (Case) iter.next();
            // System.out.println(caseInstance);
            noOfCases++;
        }
        assertTrue("Num of generated cases != Nom of cases", //$NON-NLS-1$
                noOfCases == declaredNoOfCases);
    }*/

    /**
     * Runs the process with only root activities on InteractionEngine. 
     *
     * @throws MalformedURLException
     */
    /*public void testIntEng() throws MalformedURLException {

        System.out.println("testIntEng START ======"); //$NON-NLS-1$
        String packageName = TEST_XPDL_FILE;
        String processName = PROCESS_NAME;
        
        //setup the repository url
        File f = new File(TEST_FILE_FOLDER + TEST_XPDL_FILE);
        System.setProperty("inteng.default.location", f.getParentFile().toURL() //$NON-NLS-1$
                .toString());

        ProcessManager processManager = new ProcessManagerImpl();
        processManager.generateSimData(xpdlPackage);
        CaseGenerator cg = processManager.getCaseGenerator(xpdlPackage,
                PROCESS_NAME, 10); //$NON-NLS-1$

        // get generated case.
        Case simCase = (Case) cg.iterator().next();
        System.out.println(simCase);

        ResourceLocator resLoc = new URLResources();
        String loc = System.getProperty("inteng.default.location"); //$NON-NLS-1$
        System.out.println("Configuring Int Eng default location: " + loc); //$NON-NLS-1$
        if (loc != null) {
            resLoc = new DefaultLocationResources(loc, resLoc);
        } else {
            resLoc = new DefaultLocationResources(
                    "http://localhost/processes/", resLoc); //$NON-NLS-1$
        }
        resLoc = new FileLocator(resLoc);

        InteractionRepository repository = new InteractionRepositoryImpl();
        InteractionEngine interactionEngine = InteractionEngineFactory
                .getInteractionEngine();
        repository.setResourceLocator(resLoc);
        AutomaticApplication simulationApplication = new AutomaticSimApplicationDispatcher();
        repository.installAutomaticApplication("ScriptApplication", simulationApplication);
        try {
            com.tibco.inteng.Package xpdlPackage = repository
                    .getPackage(packageName);
            Process xpdlProcess = xpdlPackage.getProcess(processName);
            if (xpdlProcess == null) {
                RuntimeException e = new RuntimeException(
                        "Package does not contain process with id = " //$NON-NLS-1$
                                + processName);
                throw e;
            }

            // setting simulation parameters
            List formalParameters = xpdlProcess.getFormalParameters();
            for (Iterator iter = formalParameters.iterator(); iter.hasNext();) {
                XpdlData parameter = (XpdlData) iter.next();
                parameter.setValue(simCase
                        .getCaseParameter(parameter.getName()).getValue());
                System.out.println("Setting parameter = [" //$NON-NLS-1$
                        + parameter.getName()
                        + "] to value= [" //$NON-NLS-1$
                        + simCase.getCaseParameter(parameter.getName())
                                .getValue() + "]"); //$NON-NLS-1$
            }
            ProcessState state = xpdlProcess.newProcessState(formalParameters);

            // invoking the process
            System.out.println("Finished state = " + state.isFinished()); //$NON-NLS-1$
            assertFalse("Before start process should not be finished!", state.isFinished()); //$NON-NLS-1$
            
            System.out.println("Executing process:"); //$NON-NLS-1$
            interactionEngine.executeProcess(state);

            System.out.println("Finished state = " + state.isFinished()); //$NON-NLS-1$
            assertTrue("After execution process should be finished!", state.isFinished()); //$NON-NLS-1$

            if (!state.isFinished()) {
                throw new RuntimeException(
                        "The xpdl process has not finished, The process should not contain any manual activities"); //$NON-NLS-1$
            }
        } catch (XpdlDataFormatException dataEx) {
            RuntimeException e = new RuntimeException(
                    "Passed XML could not be passed as parameter to the process " //$NON-NLS-1$
                            + dataEx.getMessage());
            throw e;
        } catch (IOException ioEx) {
            RuntimeException e = new RuntimeException(
                    "Interaction Object could not be constructed for packageName " //$NON-NLS-1$
                            + packageName + ioEx.getMessage());
            throw e;
        }
    }*/

}
