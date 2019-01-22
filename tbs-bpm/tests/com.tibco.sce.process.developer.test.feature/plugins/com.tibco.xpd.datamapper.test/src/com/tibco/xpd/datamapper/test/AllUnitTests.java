/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit tests for the REST Data Mapper.
 * 
 * @author nwilson
 * @since 22 May 2015
 */
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.rest.datamapper.test"); //$NON-NLS-1$

        suite.addTestSuite(ProcessDataMapperScriptTaskTest.class);
        suite.addTestSuite(RestServiceTaskScriptTest.class);

        return suite;
    }
}
