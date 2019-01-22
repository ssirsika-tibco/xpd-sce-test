/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.simulation.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Jan Arciuchiewicz
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.simulation.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(SimpleSimulationTest.class);
        suite.addTestSuite(CommercialPropertyV3SimulationTest.class);
        // $JUnit-END$
        return suite;
    }

}
