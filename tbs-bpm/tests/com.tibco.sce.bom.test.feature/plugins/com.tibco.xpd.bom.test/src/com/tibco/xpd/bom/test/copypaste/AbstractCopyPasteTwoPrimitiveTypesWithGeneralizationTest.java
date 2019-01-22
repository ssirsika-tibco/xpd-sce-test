/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteTwoPrimitiveTypesWithGeneralizationTest
        extends AbstractCopyPasteTest {

    private PrimitiveType type1;

    private PrimitiveType type2;

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {

        try {
            type1 =
                    BOMTestUtils
                            .createPrimitiveTypes((Package) sourceWorkingCopy
                                    .getRootElement(), 1)[0];
            addSourceElement(type1);
            type2 =
                    BOMTestUtils
                            .createPrimitiveTypes((Package) sourceWorkingCopy
                                    .getRootElement(), 1)[0];

            sourceElements.add(type2);

            Generalization generalization =
                    UMLFactory.eINSTANCE.createGeneralization();
            generalization.setGeneral(type1);
            Model mdl = (Model) sourceWorkingCopy.getRootElement();
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(mdl);

            RecordingCommand cmd = new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    UMLFactory f = UMLFactory.eINSTANCE;
                    Generalization g1 = f.createGeneralization();
                    g1.setGeneral(type1);
                    g1.setSpecific(type2);
                    // TestUtil.waitForValidatior();
                }
            };
            ed.getCommandStack().execute(cmd);

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
                4,
                copiedContent.size());
        EObject copiedEObject1 = copiedContent.get(1);
        if (copiedEObject1 instanceof PrimitiveType) {
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    (Classifier) type1,
                    (Classifier) copiedEObject1);
        } else {
            fail("The PrimitiveType was not copied.");
        }
        EObject copiedEObject2 = copiedContent.get(2);
        if (copiedEObject2 instanceof PrimitiveType) {
            BOMTestUtils.validateNamesAndViews(editingDomain,
                    (Classifier) type2,
                    (Classifier) copiedEObject2);
        } else {
            fail("The PrimitiveType was not copied.");
        }

        EList<Generalization> type2Generalizations = type2.getGeneralizations();
        assertEquals("The number of Generalizations of the source PrimitiveType was wrong.",
                2,
                type2Generalizations.size());
        // the second is our own
        Classifier type2General = type2Generalizations.get(1).getGeneral();
        assertTrue("The name of the superclass of the source PrimitiveType were wrong",
                type2General.getName().equals("PrimitiveType1"));

        EList<Generalization> copiedGeneralizations2 =
                ((PrimitiveType) copiedEObject2).getGeneralizations();
        assertEquals("The number of Generalizations of the target PrimitiveType was wrong.",
                2,
                copiedGeneralizations2.size());
        // the second is our own
        Classifier copiedGeneral2 = copiedGeneralizations2.get(1).getGeneral();
        assertTrue("The name of the superclass of the target class were wrong ("
                + copiedGeneral2.getName() + ")",
                copiedGeneral2.getName().equals("PrimitiveType1"));

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
