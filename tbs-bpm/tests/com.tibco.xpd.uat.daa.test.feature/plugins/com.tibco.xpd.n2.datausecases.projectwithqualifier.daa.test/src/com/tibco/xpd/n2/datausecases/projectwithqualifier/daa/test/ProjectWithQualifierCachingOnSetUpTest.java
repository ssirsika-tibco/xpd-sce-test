/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.datausecases.projectwithqualifier.daa.test;

import com.tibco.xpd.n2.daa.test.junit.SampleAbsolutePathTest;

/**
 * Set up test for business data related projects that use JUnit specific
 * JUnitMultiProjectDAAGenerationWithProgress to generate the DAA. This is done
 * because we want the timestamp for the generated daas to be the timestamp of
 * gold daa (instead of current timestamp)
 * 
 * @author bharge
 * @since 29 Nov 2013
 */
public class ProjectWithQualifierCachingOnSetUpTest
        extends SampleAbsolutePathTest {
    /*
     * SID ACE-122 - commenting out for now - as may be usefil to preserve the
     * test projects at least from this implementation.
     */
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
