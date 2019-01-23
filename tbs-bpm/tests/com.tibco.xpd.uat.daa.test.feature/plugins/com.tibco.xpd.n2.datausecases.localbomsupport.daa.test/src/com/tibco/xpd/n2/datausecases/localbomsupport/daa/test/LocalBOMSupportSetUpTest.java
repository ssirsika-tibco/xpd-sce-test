/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.datausecases.localbomsupport.daa.test;

import com.tibco.xpd.n2.daa.test.junit.SampleAbsolutePathTest;

/**
 * @author kupadhya
 * 
 */
public class LocalBOMSupportSetUpTest extends SampleAbsolutePathTest {

    @Override
    protected String getContextPlugInId() {

        return LocalBomSupportActivator.PLUGIN_ID;
    }
}
