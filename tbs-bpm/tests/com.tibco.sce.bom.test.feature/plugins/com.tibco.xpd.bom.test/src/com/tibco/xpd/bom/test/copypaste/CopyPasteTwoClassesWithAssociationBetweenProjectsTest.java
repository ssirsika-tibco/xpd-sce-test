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
public class CopyPasteTwoClassesWithAssociationBetweenProjectsTest extends
        AbstractCopyPasteTwoClassesWithAssociationTest {

    public void testCopyPasteTwoClassesWithAssociationBetweenProjects()
            throws Exception {
        doTest();
    }

    @Override
    protected void setUp() throws Exception {
        hasTargetProject = true;
        super.setUp();
    }

}
