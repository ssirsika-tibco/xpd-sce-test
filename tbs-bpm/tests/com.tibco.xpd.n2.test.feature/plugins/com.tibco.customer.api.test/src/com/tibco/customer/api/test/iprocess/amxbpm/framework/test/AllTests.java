/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.framework.test;

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
                        "Tests in package com.tibco.customer.api.test.iprocess.amxbpm.framework.test"); //$NON-NLS-1$

        // $JUnit-BEGIN$

        suite.addTestSuite(IpmBpm01_SimpleTest.class);
        suite.addTestSuite(IpmBpm02_BPMFileToConverterTest.class);
        suite.addTestSuite(IpmBpm03_PassSameIProcessXpdlTwiceTest.class);
        suite.addTestSuite(IpmBpm04_SubProcessReferencesMaintedPostSeparationTest.class);
        suite.addTestSuite(IpmBpm05_SameXpdlInOtherProjectTest.class);

        /*
         * this test is deleted and moved to package
         * "com.tibco.customer.api.test.iprocess.amxbpm.conversions.test" by the
         * name "IpmBpm14_ProcessAlreadyInWorkSpaceTest"
         * suite.addTestSuite(IpmBpm06_ProcessAlreadyInWorkSpaceTest.class);
         */

        suite.addTestSuite(IpmBpm07a_SaveIncrementedFileNameTest.class);
        suite.addTestSuite(IpmBpm07b_SaveDefaultFileNameTest.class);
        suite.addTestSuite(IpmBpm07c8_DoNotWriteExistingProcessTest.class);
        suite.addTestSuite(IpmBpm09a_DebugResourceCreationTest.class);
        suite.addTestSuite(IpmBpm09b_DebugResourceCreationTest.class);
        suite.addTestSuite(IpmBpm09c_DebugResourceCreationTest.class);
        suite.addTestSuite(IpmBpm10_LifeCycleListenerTest.class);
        suite.addTestSuite(IpmBpm11_ProcessImplementsInterfaceReferencesCheckTest.class);
        suite.addTestSuite(IpmBpm12_ReusableSubProcInterfaceReferencesCheckTest.class);
        suite.addTestSuite(IpmBpm13_ReferenceBrokenEvenIfXpdlInProjectTest.class);
        suite.addTestSuite(IpmBpm14_XpdlWithNameAlreadyPresentReferenceUpdateCheckTest.class);

        // ********* IPS to AMX-BPM conversion tests
        suite.addTestSuite(IpsBpm01_RegeneratedProcessIfcIdsTest.class);
        suite.addTestSuite(IpsBpm02_SaveIncrementedFileNameTest.class);
        suite.addTestSuite(IpsBpm03_DoNotWriteExistingProcessTest.class);
        suite.addTestSuite(IpsBpm04_ProcessImplementsInterfaceReferencesCheckTest.class);
        suite.addTestSuite(IpsBpm05_ReusableSubProcInterfaceReferencesCheckTest.class);
        suite.addTestSuite(IpsBpm06a_DebugResourceCreationTest.class);
        suite.addTestSuite(IpsBpm06b_DebugResourceCreationTest.class);
        suite.addTestSuite(IpsBpm06c_DebugResourceCreationTest.class);

        // $JUnit-END$

        return suite;
    }
}
