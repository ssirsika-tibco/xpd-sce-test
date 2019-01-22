/* 
 ** 
 **  MODULE:             $RCSfile: XSDElementProcessorTest.java $ 
 **                      $Revision: 1.3 $ 
 **                      $Date: 2005/04/12 15:20:38Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: XSDElementProcessorTest.java $ 
 **    Revision 1.3  2005/04/12 15:20:38Z  jarciuch 
 **    New properties generated for tabs. Generated properties for complex type headers for form app. 
 **    Revision 1.2  2005/03/23 11:56:22Z  jarciuch 
 **    Implemented properties generation for ListApp. 
 **    Revision 1.1  2005/03/17 15:45:52Z  jarciuch 
 **    Initial revision 
 **    Revision 1.2  2005/03/08 13:04:14Z  wzurek 
 **    Revision 1.1  2005/03/04 12:30:54Z  jarciuch 
 **    Initial revision 
 **    Revision 1.0  03-Mar-2005  jarciuch 
 **    Initial revision 
 ** 
 */
package com.tibco.xpd.simulation.preprocess;

import java.io.File;

import com.tibco.xpd.xpdl2.Package;

/**
 * Test class for '' type.
 * 
 * @author jarciuch
 */
public class OneSplitPreprocessWithData1SimulationTest extends XpdlBaseTestCase {

    private static final String PROCESS_NAME = "1"; //$NON-NLS-1$
    private static final String TEST_XPDL_FILE = "test-files/OneSplitWithData1.xpdl"; //$NON-NLS-1$
    private static final String OUT_FILE = "test-files/OneSplitWithData1Out.xpdl"; //$NON-NLS-1$
    private static final String OUT_FILE_2 = "test-files/OOneSplitWithData1Out2.xpdl"; //$NON-NLS-1$
    private Package xpdlPackage;

    protected void setUp() throws Exception {
        super.setUp();
        
        File f = new File(TEST_XPDL_FILE); //$NON-NLS-1$
        String xsdUrl = f.getAbsolutePath();
        xpdlPackage = TestUtil.getXPDLPackage(xsdUrl);
        File outFile = new File(OUT_FILE);
        if (outFile.exists()) {
            outFile.delete();
        }
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
        assertNotNull("Package is not loaded." , xpdlPackage); //$NON-NLS-1$
    }
    
    /**
     * TODO It is not a real test. It is rather a sample use case. Make this test automatic.  
     *
     * @throws IOException
     */
    /*public void testConditionGeneration() throws IOException {
        ProcessManager processManager = new ProcessManagerImpl();
        processManager.generateSimData(xpdlPackage);
        OutputStream out = new FileOutputStream(OUT_FILE); 
        xpdlPackage.eResource().save(out, Collections.EMPTY_MAP);
    }*/
    
    /**
     * TODO It is not a real test. It is rather a sample use case. Make this test automatic.  
     *
     * @throws IOException
     */
    /*public void testConditionGenerationAndRegeneration() throws IOException {
        ProcessManager processManager = new ProcessManagerImpl();
        processManager.generateSimData(xpdlPackage);
        OutputStream out = new FileOutputStream(OUT_FILE); 
        xpdlPackage.eResource().save(out, Collections.EMPTY_MAP);
        
        File f = new File(OUT_FILE); //$NON-NLS-1$
        String xsdUrl = f.getAbsolutePath();
        xpdlPackage = TestUtil.getXPDLPackage(xsdUrl);
        File outFile = new File(OUT_FILE_2);
        if (outFile.exists()) {
            outFile.delete();
        }
        
        processManager.generateSimData(xpdlPackage);
        OutputStream out2 = new FileOutputStream(OUT_FILE_2); 
        xpdlPackage.eResource().save(out2, Collections.EMPTY_MAP);
    }*/
    
    /**
     * TODO It is not a real test. It is rather a sample use case. Make this test automatic.
     */
//    public void testCaseGeneration() {
//        ProcessManager processManager = new ProcessManagerImpl();
//        processManager.generateSimData(xpdlPackage);
//        CaseGenerator cg = processManager.getCaseGenerator(xpdlPackage, PROCESS_NAME, 100); 
//        for (Iterator iter = cg.iterator(); iter.hasNext();) {
//            Case caseInstance = (Case) iter.next();
//            System.out.println(caseInstance);
//        }
//    }
}