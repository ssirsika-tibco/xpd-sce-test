/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.test;

import com.tibco.xpd.wm.test.om.transform.DynamiOrgTransformationTest;
import com.tibco.xpd.wm.test.om.transform.NestedOrgUnitTest;
import com.tibco.xpd.wm.test.om.transform.OrgDeployModelSchemaTest;
import com.tibco.xpd.wm.test.om.transform.QualifierAttributeValueTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Jan Arciuchiewicz
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.wm.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$

        suite.addTestSuite(DynamiOrgTransformationTest.class);
        suite.addTestSuite(NestedOrgUnitTest.class);
        suite.addTestSuite(QualifierAttributeValueTest.class);
        suite.addTestSuite(OrgDeployModelSchemaTest.class);
        // $JUnit-END$
        return suite;
    }
}
