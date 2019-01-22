/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteNestedTwoPackagesInPackageTest extends
        AbstractCopyPasteTest {

    private Package package1;

    private Package package2;

    private Package package3;

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            package1 =
                    BOMTestUtils.createPackages((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];
            package2 = BOMTestUtils.createPackages(package1, 1)[0];
            package3 = BOMTestUtils.createPackages(package1, 1)[0];
            sourceElements.add(package1);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }

        TestUtil.waitForJobs();
        saveSourceModel();

    }

    @Override
    protected void treatSource() {
    }

    @Override
    protected void treatTarget() {
    }

    /*
     * Compare the original Class with the Copy. Check, that all View attributes
     * are equal, check that the Stereotype with a set Property was copied
     * properly.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#validate()
     */
    @Override
    protected void validateCopies() {

        EObject root2 = targetWorkingCopy.getRootElement();
        EList<EObject> copiedContent = root2.eContents();

        // Ignore first element which is the EAnnotations

        assertEquals("The Number of the copied Elements is incorrect.",
                3,
                copiedContent.size());
        EObject copiedEObject = copiedContent.get(1);

        if (copiedEObject instanceof Package) {
            assertTrue("Copied Element was not a Package.",
                    copiedEObject instanceof Package);
            Package copiedPackage = (Package) copiedEObject;
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    package1,
                    copiedPackage);
            EList<EObject> content = copiedPackage.eContents();
            assertEquals(2, content.size());
            assertTrue(content.get(0) instanceof Package);
            Package nestedCopiedPackage = (Package) content.get(0);
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    package2,
                    nestedCopiedPackage);
            nestedCopiedPackage = (Package) content.get(1);
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    package3,
                    nestedCopiedPackage);
        } else {
            fail("The Nested Package was not copied.");
        }

    }

}
