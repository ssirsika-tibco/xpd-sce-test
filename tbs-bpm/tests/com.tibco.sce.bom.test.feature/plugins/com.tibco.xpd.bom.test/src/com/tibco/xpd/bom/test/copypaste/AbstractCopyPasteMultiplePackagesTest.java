/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.test.util.BOMTestUtils;
import com.tibco.xpd.core.test.util.TestUtil;

/**
 * Tests the copy of two Packages from one model file to another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteMultiplePackagesTest extends
        AbstractCopyPasteMultipleElementsTest {

    /**
     * Create two Classes. Make sure, that they have all view elements.
     * 
     * @see com.tibco.xpd.bom.test.copypaste.AbstractCopyPasteTest#createSourceElements()
     */
    @Override
    protected void createSourceElements() {
        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Package_1001);
        BOMTestUtils.creatGMFElementByMouse(sourceWorkingCopy,
                UMLElementTypes.Package_1001);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Package_1001);
        // BOMTestUtils.createGMFElement(sourceWorkingCopy,
        // UMLElementTypes.Package_1001);
        EObject root = sourceWorkingCopy.getRootElement();
        EList<EObject> content = root.eContents();

        // Ignore first element which is the EAnnotations

        Package c1 = (Package) content.get(1);
        addSourceElement(c1);
        Package c2 = (Package) content.get(2);
        addSourceElement(c2);
        TestUtil.waitForJobs();
    }

}
