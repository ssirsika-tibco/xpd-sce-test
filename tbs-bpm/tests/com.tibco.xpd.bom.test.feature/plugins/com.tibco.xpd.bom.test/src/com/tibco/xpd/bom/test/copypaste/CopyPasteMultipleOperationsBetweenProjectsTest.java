/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.copypaste;

/**
 * Tests the copy of two Packages from one model file to another.
 * 
 * @author rassisi
 * 
 */
public class CopyPasteMultipleOperationsBetweenProjectsTest extends
        AbstractCopyPasteMultipleOperationsTest {

    /**
     * 
     */
    public void testCopyPasteMultipleOperationsBetweenProjects() {
        doTest();
    }

    @Override
    protected void setUp() throws Exception {
        hasTargetProject = true;
        super.setUp();
    }

}
