/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.mock.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All SCE unit tests.
 *
 * @author pwatson
 * @since 21 Mar 2019
 */
@SuppressWarnings("nls")
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.sce.mock.tests");

        // suite.addTestSuite(PERascContributorTest.class);

        return suite;
    }
}
