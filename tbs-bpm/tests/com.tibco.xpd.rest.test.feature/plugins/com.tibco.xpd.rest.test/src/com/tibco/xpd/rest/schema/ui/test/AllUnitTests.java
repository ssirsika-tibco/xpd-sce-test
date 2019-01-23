/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All REST Schema unit tests. Note that these are run in headless mode so
 * cannot be used with any UI code.
 * 
 * @author nwilson
 * @since 6 Feb 2015
 */
public class AllUnitTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("REST Service Invocation Unit Tests"); //$NON-NLS-1$

        suite.addTestSuite(ISO8601DateFormatUnitTest.class);
        suite.addTestSuite(JsonSampleImportConvertorUnitTest.class);
        suite.addTestSuite(PrimitiveFilterUnitTest.class);
        suite.addTestSuite(RestServiceDefinitionRuleTest.class);
        suite.addTestSuite(JsonSchemaValidationRuleTest.class);

        return suite;
    }

}
