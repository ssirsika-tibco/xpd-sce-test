/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of two Packages, two classes and two PrimitiveTypes from one
 * model file to another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPastePackagesClassesAndPrimitiveTypesTest
        extends AbstractCopyPasteMultipleElementsTest {

    /**
     * Create two Classes, two packages and two PrimitiveTypes. Make sure, that
     * they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {

        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Package_1001);
        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Package_1001);

        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Class_1002);
        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Class_1002);

        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.PrimitiveType_1003);
        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.PrimitiveType_1003);

        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Package_1001);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Package_1001);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Class_1002);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Class_1002);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.PrimitiveType_1003);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.PrimitiveType_1003);

        EObject root = sourceWorkingCopy.getRootElement();
        EList<EObject> content = root.eContents();
        for (EObject object : content) {
            addSourceElement(object);
        }
        TestUtil.waitForJobs();
    }

}
