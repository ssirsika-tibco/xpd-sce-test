/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of two Packages from one model file to another.
 * 
 * @author rassisi
 * 
 */
public class AbstractCopyPasteMultipleClassesTest extends
        AbstractCopyPasteMultipleElementsTest {

    /**
     * Create two Classes. Make sure, that they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {

        BOMTestUtils.createGMFElement(sourceWorkingCopy,
                UMLElementTypes.Class_1002, 10, 100, 50, 50);
        BOMTestUtils.createGMFElement(sourceWorkingCopy,
                UMLElementTypes.Class_1002, 100, 100, 50, 50);
        EObject root = sourceWorkingCopy.getRootElement();
        EList<EObject> content = root.eContents();

        // Ignore first element which is EAnnotation

        Class c1 = (Class) content.get(1);
        addSourceElement(c1);
        Class c2 = (Class) content.get(2);
        addSourceElement(c2);
        TestUtil.waitForJobs();
    }

}
