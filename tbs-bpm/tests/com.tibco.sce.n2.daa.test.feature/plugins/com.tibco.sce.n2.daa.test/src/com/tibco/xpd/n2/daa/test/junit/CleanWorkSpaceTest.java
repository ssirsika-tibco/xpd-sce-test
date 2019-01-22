/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.util.List;

import junit.framework.TestCase;

import com.tibco.xpd.core.test.util.TestUtil;

/**
 * @author kupadhya
 * 
 */
public class CleanWorkSpaceTest extends TestCase {

    private static final boolean WorkSpaceClean = Boolean.FALSE;

    /**
     * @see junit.framework.TestCase#tearDown()
     * 
     * @throws java.lang.Exception
     */
    @Override
    protected void tearDown() throws Exception {
        if (CleanWorkSpaceTest.WorkSpaceClean) {
            TestUtil.deleteAllWorkpsaceProjects();
        }
    }

    public void testProjectPresence() {
        if (WorkSpaceClean) {
            List<String> projectNames =
                    TestUtil.getAllStudioProjectNamesInWorkSpace(); //$NON-NLS-1$
            assertEquals(0, projectNames.size());
        }
    }

}
