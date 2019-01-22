/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.orgdeploymodel.daa.test;

import com.tibco.xpd.n2.daa.test.junit.SampleAbsolutePathTest;

/**
 * @author kupadhya
 * 
 */
public class OrgDeployModelSetUpTest extends SampleAbsolutePathTest {

    @Override
    protected String getContextPlugInId() {
        return Activator.PLUGIN_ID;
    }
}
