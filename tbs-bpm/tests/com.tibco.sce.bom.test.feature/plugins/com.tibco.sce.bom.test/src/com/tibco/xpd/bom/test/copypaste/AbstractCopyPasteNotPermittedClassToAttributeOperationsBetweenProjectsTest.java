/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

/**
 * Tests the copy of a class with an applied Stereotype from one model file to
 * another.
 * 
 * @author rassisi
 * 
 */
abstract public class AbstractCopyPasteNotPermittedClassToAttributeOperationsBetweenProjectsTest
        extends AbstractCopyPasteNotPermittedClassToAttributeOperationsTest {

    @Override
    protected void setUp() throws Exception {
        PROJECT_TARGET_NAME = PROJECT_SOURCE_NAME + "_target";
        super.setUp();
    }

}
