/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
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
abstract public class AbstractCopyPasteMultipleAttributesTest extends
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
            Property prop1 = UMLFactory.eINSTANCE.createProperty();
            prop1.setName("property1");
            Command cmd = AddCommand.create(editingDomain, sourceClass,
                    UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                    prop1);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);
            addSourceElement(prop1);
            Property prop2 = UMLFactory.eINSTANCE.createProperty();
            prop2.setName("property2");
            cmd = AddCommand.create(editingDomain, sourceClass,
                    UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
                    prop2);
            assertTrue(cmd.canExecute());
            editingDomain.getCommandStack().execute(cmd);
            addSourceElement(prop2);
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
                targetClass.getAttributes().size() == 2);
        Property copiedProperty1 = (Property) targetClass.getAllAttributes()
                .toArray()[0];
        assertTrue(copiedProperty1.getName().equals("property1"));
        Property copiedProperty2 = (Property) targetClass.getAllAttributes()
                .toArray()[1];
        assertTrue(copiedProperty2.getName().equals("property2"));

        return;

    }

}
