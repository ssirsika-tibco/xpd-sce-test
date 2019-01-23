/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.customer.api.test.iprocess.amxbpm.conversions.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Convenience test runner class to run all Customer Api Specific tests.
 * <p>
 * Important : Note that this not added to the test.xml , the purpose of this
 * test is to help run all the tests locally.
 * 
 * @author kthombar
 * 
 */
public class AllCustomerApiTestsTogether {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("All Test for com.tibco.customer.api.test"); //$NON-NLS-1$
        /* General Customer API Tests */
        suite.addTest(com.tibco.customer.api.test.general.AllTests.suite());
        /* IPM/IPS to AMX-BPM Contributions */
        suite.addTest(com.tibco.customer.api.test.iprocess.amxbpm.conversions.test.AllTests
                .suite());
        /* IPM/IPS to AMX-BPM Framework tests */
        suite.addTest(com.tibco.customer.api.test.iprocess.amxbpm.framework.test.AllTests
                .suite());
        /* IPM/IPS to AMX-BPM Framework validation tests */
        suite.addTest(com.tibco.customer.api.test.iprocess.amxbpm.framework.test.validations.AllTests
                .suite());
        /* IPM/IPS to AMX-BPM Process Model tests */
        suite.addTest(com.tibco.customer.api.test.iprocess.amxbpm.process.model.test.AllTests
                .suite());

        return suite;
    }
}
