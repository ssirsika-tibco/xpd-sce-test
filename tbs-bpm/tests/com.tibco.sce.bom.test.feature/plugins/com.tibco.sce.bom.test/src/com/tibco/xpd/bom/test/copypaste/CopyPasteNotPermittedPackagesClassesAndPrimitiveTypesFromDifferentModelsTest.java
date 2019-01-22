/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of two Packages from one model file to another.
 * 
 * @author rassisi
 * 
 */
public class CopyPasteNotPermittedPackagesClassesAndPrimitiveTypesFromDifferentModelsTest
        extends AbstractCopyPasteMultipleElementsTest {

    public void testCopyPasteNotPermittedPackagesClassesAndPrimitiveTypesFromDifferentModels() {
        doTestPasteShouldFail();
    }

    /**
     * Create two Classes. Make sure, that they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            sourceElements.add(BOMTestUtils.createPackages(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPackages(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createClasses(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createClasses(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPrimitiveTypes(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPrimitiveTypes(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0]);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected void createTargetElements() {
        try {
            sourceElements.add(BOMTestUtils.createPackages(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPackages(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createClasses(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createClasses(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPrimitiveTypes(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
            sourceElements.add(BOMTestUtils.createPrimitiveTypes(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0]);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected void setUp() throws Exception {
        PROJECT_TARGET_NAME = PROJECT_SOURCE_NAME + "_target";
        super.setUp();
    }

    @Override
    protected void validateCopies() {
        // do nothing because the test is about the paste command
    }
}
