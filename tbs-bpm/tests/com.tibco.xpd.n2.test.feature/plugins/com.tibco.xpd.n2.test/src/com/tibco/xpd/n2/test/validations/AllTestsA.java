/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.n2.test.validations;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsA {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.n2.test.validations"); //$NON-NLS-1$
        // $JUnit-BEGIN$

        suite.addTestSuite(N2_02_XPD1939TwoTightCyclesInSubProcessInvokeTest.class);
        suite.addTestSuite(N2_03_XPD1924PageflowEventHandlersRulesTest.class);
        suite.addTestSuite(N2_04_XPD1930MissingRulesTest.class);
        suite.addTestSuite(N2_05_RcvTaskMIParallelTest.class);
        suite.addTestSuite(N2_06_XPD734WebSvcParticipantUseAndConfigTest.class);
        suite.addTestSuite(N2_07_XPD2337SubProcWithMsgStartMustHaveNoneStartTest.class);
        suite.addTestSuite(N2_08_XPD2337CheckValidSubProcWithMsgStartMustHaveNoneStartTest.class);
        suite.addTestSuite(N2_09_XPD1675BizSvcMustCallReqProcessOkTest.class);
        suite.addTestSuite(N2_10_BizServiceValidationTest.class);
        suite.addTestSuite(N2_11_RESTServiceValidationTest.class);
        suite.addTestSuite(N2_12_MustHaveSameParticNameForSamePortTypeTest.class);
       
        // $JUnit-END$
        return suite;
    }
}
