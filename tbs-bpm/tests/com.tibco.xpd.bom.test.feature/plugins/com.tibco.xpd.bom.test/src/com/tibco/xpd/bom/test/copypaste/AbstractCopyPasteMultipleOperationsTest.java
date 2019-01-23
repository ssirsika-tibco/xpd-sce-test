/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of two Packages from one model file to another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteMultipleOperationsTest extends
        AbstractCopyPasteMultipleElementsTest {

    Class sourceClass;
    Class targetClass;

    /**
     * Create two Classes. Make sure, that they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        try {
            sourceClass = BOMTestUtils.createClasses(
                    (Package) sourceWorkingCopy.getRootElement(), 1)[0];
            Operation operation1 = UMLFactory.eINSTANCE.createOperation();
            operation1.setName("operation1");
            Command cmd = AddCommand.create(editingDomain, sourceClass,
                    UMLPackage.Literals.CLASS__OWNED_OPERATION, operation1);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);
            addSourceElement(operation1);
            Operation operation2 = UMLFactory.eINSTANCE.createOperation();
            operation2.setName("operation2");
            cmd = AddCommand.create(editingDomain, sourceClass,
                    UMLPackage.Literals.CLASS__OWNED_OPERATION, operation2);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);
            addSourceElement(operation2);
        } catch (Exception e) {
            fail("Failed to create the Source Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected void createTargetElements() {
        try {
            targetClass = BOMTestUtils.createClasses(
                    (Package) targetWorkingCopy.getRootElement(), 1)[0];
        } catch (Exception e) {
            fail("Failed to create the Target Class.");
        }
        TestUtil.waitForJobs();
    }

    @Override
    protected EObject getTarget() {
        return targetClass;
    }

    @Override
    protected void validateCopies() {
        BOMTestUtils.validateNamesAndViews(editingDomain, sourceClass,
                targetClass);
        assertTrue(
                "Target Class number of attributes was not correct (should be 2)",
                targetClass.getOperations().size() == 2);
        Operation copiedOperation1 = (Operation) targetClass.getOperations()
                .toArray()[0];
        assertTrue(copiedOperation1.getName().equals("operation1"));
        Operation copiedOperation2 = (Operation) targetClass.getOperations()
                .toArray()[1];
        assertTrue(copiedOperation2.getName().equals("operation2"));
        return;
    }

}
