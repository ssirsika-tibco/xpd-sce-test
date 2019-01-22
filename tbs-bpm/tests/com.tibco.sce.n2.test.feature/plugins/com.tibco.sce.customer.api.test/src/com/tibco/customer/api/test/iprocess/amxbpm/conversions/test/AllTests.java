/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * AllTests class for package
 * "com.tibco.customer.api.test.iprocess.amxbpm.conversions.test".
 * 
 * @author sajain
 * @since Apr 30, 2014
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite(
                        "Tests in package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test"); //$NON-NLS-1$

        // $JUnit-BEGIN$
        // ********* IPM TO BPM CONVERSION TESTS ************
        suite.addTestSuite(IpmBpm01_MoveParticipantToPackageLevelSimpleTest.class);
        suite.addTestSuite(IpmBpm02_MoveParticipantToPackageLevelComplexTest.class);
        suite.addTestSuite(IpmBpm03_TransactionStepsConversionTest.class);
        suite.addTestSuite(IpmBpm04_DynamicSubProcTest.class);
        suite.addTestSuite(IpmBpm05_SetUniqueNameContribution.class);
        suite.addTestSuite(IpmBpm06_EAIStepAuditTrailExpressions.class);
        suite.addTestSuite(IpmBpm07_ArrayToScriptUtilConversionTest.class);
        suite.addTestSuite(IpmBpm08_CustAuditToProcesAuditConversionTest.class);
        suite.addTestSuite(IpmBpm09_iProcessScriptToJavaScriptConversionTest.class);
        suite.addTestSuite(IpmBpm10_RemoveiProcessExtTest.class);
        suite.addTestSuite(IpmBpm11_iProcessToBpmDestinationTest.class);
        suite.addTestSuite(IpmBpm12_EventCanHaveSingleFlowContributionTest.class);
        suite.addTestSuite(IpmBpm13_PublicStepsAndEventsConversionTest.class);
        suite.addTestSuite(IpmBpm14_ProcessAlreadyInWorkSpaceTest.class);
        suite.addTestSuite(IpmBpm15_EAIWithdrawContributionTest.class);
        suite.addTestSuite(IpmBpm16_SpecialCharsContributionTest.class);
        suite.addTestSuite(IpmBpm17_StandaloneScriptContributionTest.class);
        suite.addTestSuite(IpmBpm18_DbTaskSyatemParticContributionTest.class);
        suite.addTestSuite(IpmBpm19_UserTaskAddresseeContributionTest.class);
        suite.addTestSuite(IpmBpm20_SWAndIDXDataContributionTest.class);
        suite.addTestSuite(IpmBpm21_EmailTaskSyatemParticContributionTest.class);
        suite.addTestSuite(IpmBpm22_GraftStepFixMeAnnontationTest.class);
        suite.addTestSuite(IpmBpm23_AllIpmBpmContributionsTest.class);
        suite.addTestSuite(IpmBpm24_SetUniqueParameterLabelContributionTest.class);
        suite.addTestSuite(IpmBpm25_ConditionalDeadlineAndDelayedReleaseFixMeAnnontationTest.class);
        suite.addTestSuite(IpmBpm26_CalctimeMoreAccurateConversionTest.class);
        suite.addTestSuite(IpmBpm27_UserTaskWorkAllocationTest.class);
        suite.addTestSuite(IpmBpm28_GatewayNameOutOfBoundsTest.class);
        suite.addTestSuite(IpmBpm29_EAIJavaStepConversionContributionTest.class);
        suite.addTestSuite(IpmBpm30_DateTimeScriptConversionTest.class);
        suite.addTestSuite(IpmBpm31_FieldsInEMailTaskConfigTest.class);

        // ********* IPS TO BPM CONVERSION TESTS ************
        suite.addTestSuite(IpsBpm01_AllStudioiProcessToBpmContributionTest.class);
        suite.addTestSuite(IpsBpm02_GetProcessNameContributionTest.class);
        suite.addTestSuite(IpsBpm03_MoveParticipantToPackageLevelTest.class);
        suite.addTestSuite(IpsBpm04_RemoveiProcessExtTest.class);
        suite.addTestSuite(IpsBpm05_iProcessToBpmDestinationTest.class);
        suite.addTestSuite(IpsBpm06_DbTaskSyatemParticContributionTest.class);
        suite.addTestSuite(IpsBpm07_EmailTaskSyatemParticContributionTest.class);
        suite.addTestSuite(IpsBpm08_DynamicSubProcStepsTest.class);
        suite.addTestSuite(IpsBpm09_SetUniqueActivityNameTest.class);
        suite.addTestSuite(IpsBpm10_EAIStepAuditTrailExpressionsTest.class);
        suite.addTestSuite(IpsBpm11_EventCanHaveSingleFlowTest.class);
        suite.addTestSuite(IpsBpm12_EAIWithdrawAndDeadlineTest.class);
        suite.addTestSuite(IpsBpm13_UserTaskAddresseeTest.class);
        suite.addTestSuite(IpsBpm14_SW_andIDX_FieldsTest.class);
        suite.addTestSuite(IpsBpm15_DuplicateParameterLabelTest.class);
        suite.addTestSuite(IpsBpm16_PublicStepsAndEventsTest.class);
        suite.addTestSuite(IpsBpm17_UserTaskWorkAllocationTest.class);
        suite.addTestSuite(IpsBpm18_GatewayNameOutOfBoundsTest.class);
        suite.addTestSuite(IpsBpm19_FieldsInEMailTaskConfigTest.class);

        /*
         * General Conversion test, only runs to test the Import/conversion does
         * not fail on exception
         */

        suite.addTestSuite(IpmBpmImportWebServiceStepsTest.class);
        // $JUnit-END$

        return suite;
    }
}
