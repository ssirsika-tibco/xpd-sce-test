/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.process.model.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests class for package
 * "com.tibco.customer.api.test.iprocess.amxbpm.process.model.test".
 * 
 * @author sajain
 * @since Apr 30, 2014
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Tests in package com.tibco.customer.api.test.iprocess.amxbpm.process.model.test"); //$NON-NLS-1$

        // $JUnit-BEGIN$

        suite.addTestSuite(IpmBpm01_EAIConversionTest.class);
        suite.addTestSuite(IpmBpm02_StepObjectConversionTest.class);
        suite.addTestSuite(IpmBpm03_ComplexStepObjConversionTest.class);
        suite.addTestSuite(IpmBpm04_SubProcConversionTest.class);
        suite.addTestSuite(IpmBpm05_EventStepConversionTest.class);
        suite.addTestSuite(IpmBpm06_ActivitiesConversion01.class);
        suite.addTestSuite(IpmBpm07_ActivitiesConversion02.class);
        suite.addTestSuite(IpmBpm08_ActivitiesConversion03.class);
        suite.addTestSuite(IpmBpm09_ComplexRouterTest.class);
        suite.addTestSuite(IpmBpm10_ConditionalObjectTest.class);
        suite.addTestSuite(IpmBpm11_DynamicSubProcCalTest.class);
        suite.addTestSuite(IpmBpm12_EAIDBTest.class);
        suite.addTestSuite(IpmBpm13_EAIMailTest.class);
        suite.addTestSuite(IpmBpm14_EventStepTest.class);
        suite.addTestSuite(IpmBpm15_GraftStepTest.class);
        suite.addTestSuite(IpmBpm16_LineStylesAndAnnotationsTest.class);
        suite.addTestSuite(IpmBpm17_MainProcTest.class);
        suite.addTestSuite(IpmBpm18_SubProcCalTest.class);
        suite.addTestSuite(IpmBpm19_EAIScriptTest.class);
        suite.addTestSuite(IpmBpm20_StandaloneScriptTest.class);
        suite.addTestSuite(IpmBpm21_StepObjectTest.class);
        suite.addTestSuite(IpmBpm22_WaitStepTest.class);
        suite.addTestSuite(IpmBpm23_EAIJavaConversionTest.class);
        suite.addTestSuite(IpmBpm24_EAICOMConversionTest.class);

        // $JUnit-END$

        return suite;
    }
}
