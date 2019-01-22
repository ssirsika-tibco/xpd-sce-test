/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.rest.daa.test;

import com.tibco.xpd.n2.daa.test.junit.SampleAbsolutePathTest;

/**
 * Set up test for business data related projects that use JUnit specific
 * JUnitMultiProjectDAAGenerationWithProgress to generate the DAA. This is done
 * because we want the timestamp for the generated daas to be the timestamp of
 * gold daa (instead of current timestamp)
 *
 * @author jarciuch
 * @since 24 Jun 2015
 */
public class RestSetUpTest extends SampleAbsolutePathTest {

    /**
     * @see com.tibco.xpd.n2.daa.test.junit.SetUpTest#getContextPlugInId()
     * 
     * @return
     */
    @Override
    protected String getContextPlugInId() {

        return RestDAATestActivator.PLUGIN_ID;
    }
}
