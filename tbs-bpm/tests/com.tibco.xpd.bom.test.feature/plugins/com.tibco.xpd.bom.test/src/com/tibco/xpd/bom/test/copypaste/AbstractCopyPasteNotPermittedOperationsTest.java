/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

import org.eclipse.emf.ecore.EObject;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteNotPermittedOperationsTest extends
        AbstractCopyPasteTest {

    @Override
    protected void treatSource() {
    }

    @Override
    protected void treatTarget() {
    }

    @Override
    protected void validateCopies() {
        // do nothing because the test is only to check the
        // paste operation
    }

    @Override
    protected EObject getCopyContext() {
        return sourceElements.get(0);
    }

}
