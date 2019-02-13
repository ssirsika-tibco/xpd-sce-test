/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.core.test.util.TestUtil;

/**
 * 
 * 
 * @author aallway
 * @since 18 Apr 2011
 */
public class N2TestCreationUtil {

    /**
     * 
     */
    private static final String BPM_PROJECT_DESTINATION = "BPM"; //$NON-NLS-1$

    /**
     * @param testProject
     * @param testPluginId
     */
    public static void configureAsN2Project(IProject testProject,
            String testPluginId) {
        TestUtil.addGlobalDestinationToProject(testPluginId,
                BPM_PROJECT_DESTINATION,
                testProject);

    }

}
