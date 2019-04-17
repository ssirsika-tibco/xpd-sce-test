/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.sce.tests.cdm.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All BOM->CDM transformation.
 *
 * @author jarciuch
 * @since 11 Apr 2019
 */
@SuppressWarnings("nls")
public class AllBomCdmTransformTests {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test BOM->CDM transformations");

        suite.addTestSuite(SimpleCdmTransformTest.class);
        suite.addTestSuite(StructuredTypesCdmTransformTest.class);
        suite.addTestSuite(ConstraintsCdmTransformTest.class);
        suite.addTestSuite(GlobalDataCdmTransformTest.class);
        suite.addTestSuite(LinksTransformTest.class);

        return suite;
    }
}
