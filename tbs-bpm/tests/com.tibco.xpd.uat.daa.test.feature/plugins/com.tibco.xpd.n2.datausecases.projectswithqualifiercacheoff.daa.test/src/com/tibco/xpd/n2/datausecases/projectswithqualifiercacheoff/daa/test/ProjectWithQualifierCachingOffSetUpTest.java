/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.datausecases.projectswithqualifiercacheoff.daa.test;

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
public class ProjectWithQualifierCachingOffSetUpTest
        extends SampleAbsolutePathTest {

    /*
     * SID ACE-122 - commenting out for now - as may be usefil to preserve the
     * test projects at least from this implementation.
     */

    // /**
    // * @see
    // com.tibco.xpd.n2.daa.test.junit.SetUpTest#aboutToGenerateDAA(org.eclipse.core.resources.IProject)
    // *
    // * @param project
    // */
    // @Override
    // protected void aboutToGenerateDAA(IProject project) {
    // super.aboutToGenerateDAA(project);
    //
    // /* turn off bom caching! */
    // DAAGenPreferences.setCacheBomJars(false);
    // }
    //
    // /**
    // * @see
    // com.tibco.xpd.n2.daa.test.junit.SetUpTest#generateDAADone(org.eclipse.core.resources.IProject)
    // *
    // * @param project
    // */
    // @Override
    // protected void generateDAADone(IProject project) {
    // super.generateDAADone(project);
    //
    // /* turn off bom caching back on! */
    // DAAGenPreferences.setCacheBomJars(false);
    //
    // }

    // /**
    // * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#getContextPlugInId()
    // *
    // * @return
    // */
    // @Override
    // protected String getContextPlugInId() {
    //
    // return Activator.PLUGIN_ID;
    // }
}
