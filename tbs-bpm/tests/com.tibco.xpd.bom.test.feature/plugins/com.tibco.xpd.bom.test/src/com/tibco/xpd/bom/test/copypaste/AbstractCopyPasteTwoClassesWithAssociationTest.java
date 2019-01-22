/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.wc.gmf.AbstractGMFWorkingCopy;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
public class AbstractCopyPasteTwoClassesWithAssociationTest extends
        AbstractCopyPasteTest {

    private Class class1;

    private Class class2;

    private Association association;

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {

        try {
            class1 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];
            addSourceElement(class1);
            class2 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];

            sourceElements.add(class2);

            association =
                    BOMTestUtils.createAssociation((Package) sourceWorkingCopy
                            .getRootElement(), class1, class2);

        } catch (Exception e) {
            fail("Failed to create the Class.");
        }

        TestUtil.waitForJobs();
        saveSourceModel();

    }

    /**
     * Apply a Stereotype to the source Class which is derived from a Profile
     * which is loaded from a file.
     * 
     */
    @Override
    protected void treatSource() {
    }

    /**
     * Apply a Profile to the target model.
     * 
     */
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
                5,
                copiedContent.size());
        EObject copiedEObject1 = copiedContent.get(1);
        if (copiedEObject1 instanceof Class) {
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    (Classifier) class1,
                    (Classifier) copiedEObject1);
        } else {
            fail("The Class was not copied.");
        }
        EObject copiedEObject2 = copiedContent.get(2);
        if (copiedEObject2 instanceof Class) {
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    (Classifier) class2,
                    (Classifier) copiedEObject2);
        } else {
            fail("The Class was not copied.");
        }

        Class copiedClass1 = (Class) copiedEObject1;
        EList<Property> attributes = copiedClass1.getOwnedAttributes();
        assertEquals("The number of attributes of the target class is wrong",
                1,
                attributes.size());
        Association copiedAssociation = attributes.get(0).getAssociation();

        // validate if all three elements has related notation elements
        // Note: this will fail if the editor is not open
        BOMTestUtils.validateView(copiedEObject1,
                copiedEObject2,
                copiedAssociation);

        // validate if we have two nodes and one edge
        Diagram diag =
                ((AbstractGMFWorkingCopy) targetWorkingCopy).getDiagrams()
                        .get(0);

        // Ignore first element which is the Badge

        assertEquals(3, diag.getChildren().size());
        assertEquals(1, diag.getEdges().size());

    }

    @Override
    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

    @Override
    protected EObject getTarget() {
        return super.getTarget();
    }

}
