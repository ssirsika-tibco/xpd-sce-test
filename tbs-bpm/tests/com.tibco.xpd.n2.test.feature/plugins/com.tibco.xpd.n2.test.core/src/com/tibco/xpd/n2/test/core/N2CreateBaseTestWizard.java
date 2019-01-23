/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core;

import java.util.Collection;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard;

/**
 * Base resource test for project with BPM destination.
 * 
 * @author aallway
 * @since 18 Apr 2011
 */
public class N2CreateBaseTestWizard extends CreateBaseTestWizard {

    /**
     * @param selectedStudioResources
     */
    public N2CreateBaseTestWizard(Collection<IResource> selectedStudioResources) {
        super(selectedStudioResources);
    }

    @Override
    protected Class getTestSuperClass() {
        return AbstractN2BaseResourceTest.class;
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestWizard#getTestSuperClassPluginId()
     * 
     * @return
     */
    @Override
    protected String getTestSuperClassPluginId() {
        return N2TestCorePlugin.PLUGIN_ID;
    }
}
