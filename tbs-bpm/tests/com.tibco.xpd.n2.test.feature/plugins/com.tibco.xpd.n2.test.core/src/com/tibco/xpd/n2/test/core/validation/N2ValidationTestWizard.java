/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.validation;

import java.util.Collection;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.core.createtestwizards.validationtestwizard.CreateValidationTestWizard;
import com.tibco.xpd.n2.test.core.N2TestCorePlugin;

/**
 * @author rsomayaj
 * 
 */
public class N2ValidationTestWizard extends CreateValidationTestWizard {

    /**
     * @param selectedStudioResources
     */
    public N2ValidationTestWizard(Collection<IResource> selectedStudioResources) {
        super(selectedStudioResources);
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.validationtestwizard.CreateValidationTestWizard#getTestSuperClass()
     * 
     * @return
     */
    @Override
    protected Class getTestSuperClass() {
        return AbstractN2BaseValidationTest.class;
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
