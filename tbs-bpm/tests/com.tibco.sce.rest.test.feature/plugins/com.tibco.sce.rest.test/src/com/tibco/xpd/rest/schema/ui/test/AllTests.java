/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.rest.schema.validation.test.Json_01_SchemaTypeAndPropertyValidationTest;
import com.tibco.xpd.rest.schema.validation.test.RestPathValidationTest;

/**
 * All REST Schema UI tests.
 * 
 * @author nwilson
 * @since 6 Feb 2015
 */
public class AllTests {
    public static Test suite() {
        TestSuite suite =
                new TestSuite("REST Service Invocation Functional Tests"); //$NON-NLS-1$
        suite.addTestSuite(JsonSchemaEditorTest.class);
        suite.addTestSuite(RsdEditorTest.class);

        /*
         * JSON Validation test
         */
        suite.addTestSuite(Json_01_SchemaTypeAndPropertyValidationTest.class);
        suite.addTestSuite(RestPathValidationTest.class);
        return suite;
    }

}
