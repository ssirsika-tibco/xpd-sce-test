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
public class CopyPasteNestedClassInPackageInPackageBetweenProjectsTest extends
        AbstractCopyPasteNestedClassInPackageInPackageTest {

    public void testCopyPasteNestedClassInPackageInPackageBetweenProjects()
            throws Exception {
        doTest();
    }

    @Override
    protected void setUp() throws Exception {
        hasTargetProject = true;
        super.setUp();
    }

}
