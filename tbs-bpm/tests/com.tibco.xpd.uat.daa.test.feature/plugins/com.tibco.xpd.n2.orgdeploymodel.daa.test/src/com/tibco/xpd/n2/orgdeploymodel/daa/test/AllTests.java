/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.orgdeploymodel.daa.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.n2.daa.test.junit.CleanWorkSpaceTest;

/**
 * @author kupadhya
 * 
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Test for com.tibco.xpd.n2.orgdeploymodel.daa.test.junit"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        System.setProperty("bpm.cleandaastaging", "false"); //$NON-NLS-1$ //$NON-NLS-2$
        suite.addTestSuite(OrgDeployModelSetUpTest.class);
        suite.addTestSuite(OrgDeployModelDaaContentsTest.class);
        suite.addTestSuite(CleanWorkSpaceTest.class);
        // $JUnit-END$
        return suite;
    }
}
