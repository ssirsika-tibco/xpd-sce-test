/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.datausecases.projectswithqualifiercacheoff.daa.test;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.daa.internal.preferences.DAAGenPreferences;
import com.tibco.xpd.n2.daa.test.junit.SampleAbsolutePathTest;

/**
 * 
 * Set up test for business data related projects that use JUnit specific
 * JUnitMultiProjectDAAGenerationWithProgress to generate the DAA. This is done
 * because we want the timestamp for the generated daas to be the timestamp of
 * gold daa (instead of current timestamp)
 * 
 * This one turns off the bom caching!
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class ProjectWithQualifierCachingOffSetUpTest extends
        SampleAbsolutePathTest {

    /**
     * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#aboutToGenerateDAA(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    protected void aboutToGenerateDAA(IProject project) {
        super.aboutToGenerateDAA(project);

        /* turn off bom caching! */
        DAAGenPreferences.setCacheBomJars(false);
    }

    /**
     * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#generateDAADone(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    protected void generateDAADone(IProject project) {
        super.generateDAADone(project);

        /* turn off bom caching back on! */
        DAAGenPreferences.setCacheBomJars(false);

    }

    /**
     * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#getContextPlugInId()
     * 
     * @return
     */
    @Override
    protected String getContextPlugInId() {

        return Activator.PLUGIN_ID;
    }
}
