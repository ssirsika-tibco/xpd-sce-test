/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author wzurek
 * 
 */
public class CopyPasteAllTests {

    public static Test suite() {
        TestSuite suite =
                new TestSuite("Test for com.tibco.xpd.bom.copypaste.test"); //$NON-NLS-1$

        suite.addTestSuite(CopyPasteClassTest.class);
        suite.addTestSuite(CopyPasteClassWithAttributesAndOperationsBetweenTwoProjectsTest.class);
        suite.addTestSuite(CopyPasteMultipleAttributesTest.class);
        suite.addTestSuite(CopyPasteMultipleAttributesBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteMultipleClassesTest.class);
        suite.addTestSuite(CopyPasteMultipleClassesBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteMultipleOperationsTest.class);
        suite.addTestSuite(CopyPasteMultipleOperationsBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteMultiplePackagesTest.class);
        suite.addTestSuite(CopyPasteMultiplePackagesBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteMultiplePrimitiveTypesTest.class);
        suite.addTestSuite(CopyPasteMultiplePrimitiveTypesBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteNestedClassInPackageTest.class);
        suite.addTestSuite(CopyPasteNestedClassInPackageBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteNestedClassInPackageInPackageTest.class);
        suite.addTestSuite(CopyPasteNestedClassInPackageInPackageBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteNestedPackageInPackageTest.class);
        suite.addTestSuite(CopyPasteNestedPackageInPackageBeweenTwoProjectsTest.class);
        suite.addTestSuite(CopyPasteNestedTwoClassesInPackageInPackageTest.class);
        suite.addTestSuite(CopyPasteNestedTwoClassesInPackageInPackageBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteNestedTwoPackagesInPackageTest.class);
        suite.addTestSuite(CopyPasteNestedTwoPackagesInPackageBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteOverwriteElementsTest.class);
        suite.addTestSuite(CopyPasteOverwriteElementsBetweenTwoProjectsTest.class);
        suite.addTestSuite(CopyPastePackagesClassesAndPrimitiveTypesTest.class);
        suite.addTestSuite(CopyPastePackagesClassesAndPrimitiveTypesBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithGeneralizationTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithGeneralizationBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteTwoPrimitiveTypesWithGeneralizationTest.class);
        suite.addTestSuite(CopyPasteTwoPrimitiveTypesWithGeneralizationBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithAssociationTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithAssociationBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithAggregationTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithAggregationBetweenProjectsTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithCompositionTest.class);
        suite.addTestSuite(CopyPasteTwoClassesWithCompositionBetweenProjectsTest.class);

        // Kapil: The tests below should be un-commented and fixed[corrected]
        // after
        // XPD-5303 is fixed.
        // Some problem with BOM CopyPaste Action. See Jira for details.

        // suite
        // .addTestSuite(
        // CopyPasteNotPermittedPackagesClassesAndPrimitiveTypesFromDifferentModelsTest
        // .class);
        // suite
        // .addTestSuite(CopyPasteNotPermittedClassToAttributeOperationsTest.class
        // );
        // suite
        // .addTestSuite(
        // CopyPasteNotPermittedClassToAttributeOperationsBetweenProjectsTest
        // .class);
        // suite
        // .addTestSuite(CopyPasteNotPermittedClassToClassOperationsTest.class);
        // suite
        // .addTestSuite(
        // CopyPasteNotPermittedClassToClassOperationsBetweenProjectsTest
        // .class);
        // suite
        // .addTestSuite(CopyPasteNotPermittedClassToOperationOperationsTest.class
        // );
        // suite
        // .addTestSuite(
        // CopyPasteNotPermittedClassToOperationOperationsBetweenProjectsTest
        // .class);

        // $JUnit-END$
        return suite;
    }
}
