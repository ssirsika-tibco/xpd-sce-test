/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.validation;

import java.util.List;

import org.eclipse.core.resources.IResource;

import com.tibco.xpd.core.createtestwizards.validationtestwizard.CreateValidationTestAction;
import com.tibco.xpd.core.createtestwizards.validationtestwizard.CreateValidationTestWizard;

/**
 * @author rsomayaj
 * 
 */
public class CreateN2ValidationTestAction extends CreateValidationTestAction {

    /**
     * @see com.tibco.xpd.core.createtestwizards.validationtestwizard.CreateValidationTestAction#getValidationTestWizard(java.util.List)
     * 
     * @param sourceResources
     * @return
     */
    @Override
    protected CreateValidationTestWizard getValidationTestWizard(
            List<IResource> sourceResources) {
        return new N2ValidationTestWizard(sourceResources);
    }
}
