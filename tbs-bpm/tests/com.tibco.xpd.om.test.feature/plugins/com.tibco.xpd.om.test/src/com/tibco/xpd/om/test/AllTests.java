/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.om.test.cases.OMNamespacesTest;
import com.tibco.xpd.om.test.cases.PropertyTabbedViewShowTabTest;
import com.tibco.xpd.om.test.cases.TestOrgTypedElements;
import com.tibco.xpd.om.test.cases.TestOrganizationsAndEditors;
import com.tibco.xpd.om.test.indexer.OMIndexerTest;

/**
 * 
 * <p>
 * <i>Created: 3 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class AllTests {

    @SuppressWarnings("nls")
    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.om.test");
        // $JUnit-BEGIN$
        suite.addTestSuite(SimpleTest.class);
        suite.addTestSuite(OMNamespacesTest.class);
        suite.addTestSuite(PropertyTabbedViewShowTabTest.class);
        suite.addTestSuite(TestOrganizationsAndEditors.class);
        suite.addTestSuite(TestOrgTypedElements.class);
        suite.addTestSuite(OMIndexerTest.class);
        // $JUnit-END$
        return suite;
    }

}
