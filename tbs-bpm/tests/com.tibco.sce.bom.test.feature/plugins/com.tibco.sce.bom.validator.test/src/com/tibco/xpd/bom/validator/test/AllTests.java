/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.xpd.bom.validator.test.rules.ClassInheritanceRuleTest;
import com.tibco.xpd.bom.validator.test.rules.ClassNameRuleTest;
import com.tibco.xpd.bom.validator.test.rules.PackageNameDuplicateRuleTest;
import com.tibco.xpd.bom.validator.test.rules.PackageNameRuleTest;
import com.tibco.xpd.bom.validator.test.rules.PropertyNameDuplicateRuleTest;
import com.tibco.xpd.bom.validator.test.rules.ProviderExtensionRuleTest;

/**
 * @author wzurek
 * 
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for com.tibco.xpd.bom.test"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        // suite.addTestSuite(AssociationRuleTest.class);
        // suite.addTestSuite(ClassDuplicateNameRuleTest.class);
        suite.addTestSuite(ClassInheritanceRuleTest.class);
        suite.addTestSuite(ClassNameRuleTest.class);
        suite.addTestSuite(PackageNameDuplicateRuleTest.class);
        suite.addTestSuite(PackageNameRuleTest.class);
        suite.addTestSuite(PropertyNameDuplicateRuleTest.class);
        // suite.addTestSuite(IndexerTest.class);
        suite.addTestSuite(ProviderExtensionRuleTest.class);
        // $JUnit-END$
        return suite;
    }

}
