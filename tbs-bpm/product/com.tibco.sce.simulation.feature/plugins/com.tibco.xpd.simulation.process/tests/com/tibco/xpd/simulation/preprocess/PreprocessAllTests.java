/* 
** 
**  MODULE:             $RCSfile: PreprocessAllTests.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2006-01-31 $ 
** 
**  DESCRIPTION:           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: $ 
** 
*/
package com.tibco.xpd.simulation.preprocess;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PreprocessAllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(
                "Test for com.tibco.xpd.simulation.preprocess"); //$NON-NLS-1$
        //$JUnit-BEGIN$
        //suite.addTestSuite(OneSplitPreprocessSimulationTest.class);
        suite.addTestSuite(OneSplitPreprocessWithData1SimulationTest.class);
        //$JUnit-END$
        return suite;
    }

}
