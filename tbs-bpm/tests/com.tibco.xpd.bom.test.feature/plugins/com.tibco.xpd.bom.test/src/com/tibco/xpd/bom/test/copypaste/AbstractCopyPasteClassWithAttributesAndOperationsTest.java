/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteClassWithAttributesAndOperationsTest
        extends AbstractCopyPasteTest {

    private Class c1;

    /**
     * Create one Class. Make sure, that it has all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            c1 =
                    BOMTestUtils.createClasses((Package) sourceWorkingCopy
                            .getRootElement(), 1)[0];

            // ---------- add 2 properties -------------------------------------

            Property prop1 = UMLFactory.eINSTANCE.createProperty();
            prop1.setName("property1");
            Command cmd =
                    AddCommand
                            .create(editingDomain,
                                    c1,
                                    UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                                    prop1);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);

            Property prop2 = UMLFactory.eINSTANCE.createProperty();
            prop2.setName("property2");
            cmd =
                    AddCommand
                            .create(editingDomain,
                                    c1,
                                    UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                                    prop2);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);

            // ---------- add 2 operations -------------------------------------

            Operation operation1 = UMLFactory.eINSTANCE.createOperation();
            operation1.setName("operation1");
            cmd =
                    AddCommand.create(editingDomain,
                            c1,
                            UMLPackage.Literals.CLASS__OWNED_OPERATION,
                            operation1);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);

            Operation operation2 = UMLFactory.eINSTANCE.createOperation();
            operation2.setName("operation2");
            cmd =
                    AddCommand.create(editingDomain,
                            c1,
                            UMLPackage.Literals.CLASS__OWNED_OPERATION,
                            operation2);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);

            sourceElements.add(c1);

        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
        TestUtil.waitForJobs();
        saveSourceModel();
        c1 = (Class) sourceElements.get(0);
    }

    @Override
    protected void treatSource() {
    }

    @Override
    protected void treatTarget() {
    }

    @Override
    protected void validateCopies() {
        EObject root2 = targetWorkingCopy.getRootElement();
        EList<EObject> copiedContent = root2.eContents();

        // Ignore first element which is the EAnnotations

        assertEquals("The Number of the copied Elements is incorrect.",
                3,
                copiedContent.size());
        EObject copiedEObject = copiedContent.get(1);
        if (copiedEObject instanceof Class) {
            Class c1Copy = (Class) copiedEObject;
            BOMTestUtils.validateNamesAndViews(editingDomain, c1, c1Copy);
            Property copiedProperty1 =
                    (Property) c1Copy.getAllAttributes().toArray()[0];
            assertTrue(copiedProperty1.getName().equals("property1"));
            Property copiedProperty2 =
                    (Property) c1Copy.getAllAttributes().toArray()[1];
            assertTrue(copiedProperty2.getName().equals("property2"));

            Operation copiedOperation1 =
                    (Operation) c1Copy.getAllOperations().toArray()[0];
            assertTrue(copiedOperation1.getName().equals("operation1"));
            Operation copiedOperation2 =
                    (Operation) c1Copy.getAllOperations().toArray()[1];
            assertTrue(copiedOperation2.getName().equals("operation2"));
            return;
        }

        fail("The Class was not copied.");

    }

    @Override
    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

}
