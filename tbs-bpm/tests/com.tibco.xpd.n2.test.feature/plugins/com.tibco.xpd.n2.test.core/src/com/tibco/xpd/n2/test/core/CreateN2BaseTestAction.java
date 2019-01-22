/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.IWizard;

import com.tibco.xpd.core.createtestwizards.CreateBaseTestAction;

/**
 * 
 * 
 * @author aallway
 * @since 18 Apr 2011
 */
public class CreateN2BaseTestAction extends CreateBaseTestAction {

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestAction#getTestCreationWizard(java.util.Collection)
     * 
     * @param srcResources
     * @return
     */
    @Override
    protected IWizard getTestCreationWizard(Collection<IResource> srcResources) {
        return new N2CreateBaseTestWizard(srcResources);
    }

}
