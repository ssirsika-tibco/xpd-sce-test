package com.tibco.bds.designtime.generator.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tibco.bds.designtime.generator.test.da.DAGenerationTest;
import com.tibco.bds.designtime.generator.test.si.SIGenerationTest;
import com.tibco.bds.designtime.generator.test.validations.SubPackageValidationTest;

public class AllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Tests for com.tibco.bds.designtime.generator"); //$NON-NLS-1$
        // $JUnit-BEGIN$
        suite.addTestSuite(ElementTest.class);
        suite.addTestSuite(AnyTest.class);
        suite.addTestSuite(OtherConstructsTest.class);
        suite.addTestSuite(NamespaceURIToJavaPackageMapperTest.class);
        suite.addTestSuite(ModelGenerationTest.class);
        suite.addTestSuite(DAGenerationTest.class);
        suite.addTestSuite(SIGenerationTest.class);
        suite.addTestSuite(XsdImportGenerationTest.class);
        suite.addTestSuite(WsdlImportGenerationTest.class);
        suite.addTestSuite(PerformanceTest.class);
        suite.addTestSuite(DQLValidatorTest.class);
        suite.addTestSuite(SubPackageValidationTest.class);
        // $JUnit-END$
        return suite;
    }
}