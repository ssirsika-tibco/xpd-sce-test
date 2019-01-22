/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
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
abstract public class AbstractCopyPasteTwoClassesWithGeneralizationTest extends
        AbstractCopyPasteTest {

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
            class1 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];
            addSourceElement(class1);
            class2 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];

            sourceElements.add(class2);

            Generalization generalization =
                    UMLFactory.eINSTANCE.createGeneralization();
            generalization.setGeneral(class1);

            Model mdl = (Model) sourceWorkingCopy.getRootElement();

            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(mdl);

            RecordingCommand cmd = new RecordingCommand(ed) {
                @Override
                protected void doExecute() {
                    UMLFactory f = UMLFactory.eINSTANCE;
                    Generalization g1 = f.createGeneralization();
                    g1.setGeneral(class1);
                    g1.setSpecific(class2);
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

        EList<Generalization> class2Generalizations =
                class2.getGeneralizations();
        assertEquals("The number of Generalizations of the source class was wrong.",
                1,
                class2Generalizations.size());
        Classifier class2General = class2Generalizations.get(0).getGeneral();
        assertTrue("The name of the superclass were wrong", class2General
                .getName().equals("Class1"));

        EList<Generalization> copiedGeneralizations2 =
                ((Class) copiedEObject2).getGeneralizations();
        assertEquals("The number of Generalizations of the target class was wrong.",
                1,
                copiedGeneralizations2.size());
        Classifier copiedGeneral2 = copiedGeneralizations2.get(0).getGeneral();
        assertTrue("The name of the superclass were wrong", copiedGeneral2
                .getName().equals("Class1"));

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
