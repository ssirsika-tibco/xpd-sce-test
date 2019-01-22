/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.daa.test.junit;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.daa.internal.BaseProjectDAAGenerator;
import com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress;

/**
 * MultiProjectDAAGenerationWithProgress for junit purposes. This will return an
 * instance of JUnitProjectDAAGenerator which mainly returns the time stamp of
 * gold DAA to be used as a replacement in junit test generated DAA
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class JUnitMultiProjectDAAGenerationWithProgress extends
        MultiProjectDAAGenerationWithProgress {

    /**
     * @param projects
     * @param preserveDaaAfterGeneration
     */
    public JUnitMultiProjectDAAGenerationWithProgress(List<IProject> projects,
            boolean preserveDaaAfterGeneration) {

        super(projects, preserveDaaAfterGeneration);
    }

    /**
     * @see com.tibco.xpd.n2.daa.internal.wizards.MultiProjectDAAGenerationWithProgress#getProjectDaaGenerator()
     * 
     * @return
     */
    @Override
    protected BaseProjectDAAGenerator getProjectDaaGenerator() {

        return JUnitProjectDAAGenerator.getInstance();
    }
}
