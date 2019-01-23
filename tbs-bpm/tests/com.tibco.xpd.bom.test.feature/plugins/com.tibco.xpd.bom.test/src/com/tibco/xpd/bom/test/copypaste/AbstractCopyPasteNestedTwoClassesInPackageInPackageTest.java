/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteNestedTwoClassesInPackageInPackageTest
        extends AbstractCopyPasteTest {

    private Package package1;

    private Package package2;

    private Class class1;

    private Class class2;

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                    UMLElementTypes.Package_1001);

            // BOMTestUtils.createGMFElement(sourceWorkingCopy,
            // UMLElementTypes.Package_1001);
            EObject root = sourceWorkingCopy.getRootElement();
            EList<EObject> content = root.eContents();

            // Ignore first element which is the EAnnotations

            package1 = (Package) content.get(1);
            addSourceElement(package1);
            package2 = BOMTestUtils.createPackages(package1, 1)[0];
            class1 = BOMTestUtils.createClasses(package2, 1)[0];
            class2 = BOMTestUtils.createClasses(package2, 1)[0];
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
            assertEquals(1, content.size());
            assertTrue(content.get(0) instanceof Package);
            Package nestedCopiedPackage = (Package) content.get(0);
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    package2,
                    nestedCopiedPackage);
            content = nestedCopiedPackage.eContents();
            assertEquals(2, content.size());
            assertTrue(content.get(0) instanceof Class);
            Class nestedCopiedClass = (Class) content.get(0);
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    class1,
                    nestedCopiedClass);
            assertTrue(content.get(1) instanceof Class);
            nestedCopiedClass = (Class) content.get(1);
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    class2,
                    nestedCopiedClass);
            return;
        }

        fail("The Class was not copied.");
    }

}
