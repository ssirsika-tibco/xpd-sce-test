/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.uc5.daa.test;

import com.tibco.xpd.n2.daa.test.junit.DAAContentsTest;

/**
 * @author kupadhya
 * 
 */
public class UC5DaaContentsTest extends DAAContentsTest {
    private static final String FILE_QUALIFIER = "UC5"; //$NON-NLS-1$

    @Override
    protected String getFileQualifier() {
        return UC5DaaContentsTest.FILE_QUALIFIER;
    }
}
