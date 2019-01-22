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
public class CopyPasteTwoClassesWithGeneralizationBetweenProjectsTest extends
        AbstractCopyPasteTwoClassesWithGeneralizationTest {

    /**
     * 
     */
    public void testCopyPasteTwoClassesWithGeneralizationBetweenProjects() {
        doTest();
    }

    @Override
    protected void setUp() throws Exception {
        hasTargetProject = true;
        super.setUp();
    }

}
