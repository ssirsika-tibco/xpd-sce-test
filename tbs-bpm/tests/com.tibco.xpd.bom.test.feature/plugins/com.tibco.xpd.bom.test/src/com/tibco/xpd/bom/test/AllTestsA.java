/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

import com.tibco.xpd.bom.test.performance.suite.BOMPerfCreateSubClassesTest;
import com.tibco.xpd.bom.test.resource.BOMIndexerTests;
import com.tibco.xpd.bom.test.resource.BOMProjectDependecyTest;
import com.tibco.xpd.bom.test.resource.BOMResourceTest;
import com.tibco.xpd.bom.test.types.PrimitivesUtilTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * 
 */
public class AllTestsA {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.test - set A"); //$NON-NLS-1$

        suite.addTestSuite(BOMPerfCreateSubClassesTest.class);
        suite.addTestSuite(ConceptWorkingCopyTest.class);
        suite.addTestSuite(ProjectRecreateWorkingCopyTest.class);
        suite.addTestSuite(ApplayingStereotypeTest.class);
        suite.addTestSuite(RecreateEditingDomainTest.class);
        suite.addTestSuite(BOMResourceTest.class);
        suite.addTestSuite(BOMIndexerTests.class);
        suite.addTestSuite(BOMProjectDependecyTest.class);
        suite.addTestSuite(PrimitivesUtilTest.class);

        return suite;
    }

}
