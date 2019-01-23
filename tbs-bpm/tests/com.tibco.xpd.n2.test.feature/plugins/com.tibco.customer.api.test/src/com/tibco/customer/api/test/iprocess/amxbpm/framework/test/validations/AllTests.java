/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test.validations;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests class for package
 * "com.tibco.customer.api.test.iprocess.amxbpm.framework.test".
 * 
 * @author sajain
 * @since Apr 30, 2014
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Tests in package com.tibco.customer.api.test.iprocess.amxbpm.framework.test.validations"); //$NON-NLS-1$

        // $JUnit-BEGIN$
        // ********* IPM TO BPM FRAMEWORK VALIDATION TESTS ************
        suite.addTestSuite(IpmBpm_5a_SingleImportInvalidFileTest.class);
        suite.addTestSuite(IpmBpm_5b_MultipleImportInvalidFileTest.class);
        suite.addTestSuite(IpmBpm_5c_SomeProcAlreadyInWorkspaceTest.class);
        suite.addTestSuite(IpmBpm_5d_AllProcAlreadyInWorkspaceTest.class);
        suite.addTestSuite(IpmBpm_5f_DuplicateProcessWithInvalidGUIDsTest.class);
        suite.addTestSuite(IpmBpm_6abc_ReferencedProcessOrInterfaceOrderWarningTest.class);
        suite.addTestSuite(IpmBpm_6d_CompositeTypeNotSupportedWarningTest.class);

        // ********* IPS TO BPM FRAMEWORK VALIDATION TESTS ************
        suite.addTestSuite(IpsBpm_01_SubProcReferencesBrokenTest.class);
        suite.addTestSuite(IpsBpm_02_AllProcessesAlreadyPresentInWorkspaceTest.class);
        suite.addTestSuite(IpsBpm_03_ProcessWithSameNameAndDifferentIdsTest.class);
        suite.addTestSuite(IpsBpm_04_ProcessWithNoIProcessDestinationTest.class);

        // $JUnit-END$

        return suite;
    }
}
