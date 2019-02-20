/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsB {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.test.validations"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(N2_13_DBSharedResNameRequiredTest.class);
        suite.addTestSuite(N2_16_XPD1675BizSvcCantInvokeReqRespBizProcessTest.class);
        suite.addTestSuite(N2_17_BindingOperationStyleValidationTest.class);
        suite.addTestSuite(N2_19_SystemParticipantSharedResourceTest.class);
        suite.addTestSuite(N2_20_XPD1939ThreeWayCycleViaSpTaskAndImplInterfaceTest.class);
        suite.addTestSuite(N2_21_XPD2064UsingQualifiedNameForEnumAmbiguityTest.class);

        suite.addTestSuite(N2_24_XPD5345PerformerArrayRqlScriptTest.class);
        suite.addTestSuite(N2_25_ExpressionValidationProblemsExistTest.class);
        suite.addTestSuite(N2_26_NonExpressionValidationProblemsExistTest.class);
        suite.addTestSuite(N2_27_ExpressionScopeProviderTest.class);
        suite.addTestSuite(N2_28_LiveValidationProblemsTest.class);
        suite.addTestSuite(N2_29_GlobalDataServiceTaskSingleInstanceCaseRefFieldTest.class);
        suite.addTestSuite(N2_30_EventSubProcessValidationsTest.class);
        suite.addTestSuite(N2_31_AdhocTaskConfigurationTestsTest.class);
        suite.addTestSuite(N2_32_DocumentOperationsServiceTaskTest.class);
        suite.addTestSuite(N2_33_GlobalDataMultilevelGeneralisationTest.class);
        suite.addTestSuite(N2_34_SignalEventsInPageflowTest.class);
        suite.addTestSuite(N2_35_TimeOutScriptsNotSupportedForAdhocActivitiesTest.class);
        suite.addTestSuite(N2_36_EventHandlerValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}
