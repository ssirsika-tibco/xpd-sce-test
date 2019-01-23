/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Class;
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
abstract public class AbstractCopyPasteNotPermittedClassToClassOperationsTest
        extends AbstractCopyPasteNotPermittedOperationsTest {

    private Class c1;
    private Class c2;

    @Override
    protected void createSourceElements() {
        try {
            c1 = BOMTestUtils.createClasses((Package) sourceWorkingCopy
                    .getRootElement(), 1)[0];
            sourceElements.add(c1);
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }

        sourceElements.add(c1);
        TestUtil.waitForJobs();
        saveSourceModel();
        c1 = (Class) sourceElements.get(0);
    }

    @Override
    protected void createTargetElements() {
        try {
            c2 = BOMTestUtils.createClasses((Package) targetWorkingCopy
                    .getRootElement(), 1)[0];
        } catch (Exception e) {
            fail("Failed to create the Class.");
        }
    }

    @Override
    protected EObject getTarget() {
        return c2;
    }

}
