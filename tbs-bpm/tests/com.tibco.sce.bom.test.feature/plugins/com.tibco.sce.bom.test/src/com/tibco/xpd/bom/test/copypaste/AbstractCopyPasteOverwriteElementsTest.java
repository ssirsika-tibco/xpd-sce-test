/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
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
abstract public class AbstractCopyPasteOverwriteElementsTest extends
        AbstractCopyPasteMultipleElementsTest {

    /**
     * Create two Classes. Make sure, that they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            sourceElements
                    .add(BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0]);
            sourceElements
                    .add(BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0]);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected void createTargetElements() {
        try {
            BOMTestUtils.createClasses((Package) targetWorkingCopy
                    .getRootElement(), 1);
            BOMTestUtils.createClasses((Package) targetWorkingCopy
                    .getRootElement(), 1);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected void validateCopies() {
        EObject root2 = targetWorkingCopy.getRootElement();
        EList<EObject> targetContent = root2.eContents();

        // Ignore first element which is the EAnnotations

        assertEquals("The Number of the copied Elements is incorrect.",
                6,
                targetContent.size());
        assertTrue(((Classifier) targetContent.get(1)).getName()
                .equals("Class1"));
        assertTrue(((Classifier) targetContent.get(2)).getName()
                .equals("Class2"));
        assertTrue(((Classifier) targetContent.get(3)).getName()
                .equals("Copy_1_Class1"));
        assertTrue(((Classifier) targetContent.get(4)).getName()
                .equals("Copy_1_Class2"));
    }

}
