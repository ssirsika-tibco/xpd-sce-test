/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addruntime;

import com.tibco.xpd.deploy.ui.wizards.BaseDeployWizardPage;

/**
 * Base action for new Runtime wizard pages.
 * <p>
 * <i>Created: 28 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class BaseAddRuntimeWizardPage extends BaseDeployWizardPage {

    protected BaseAddRuntimeWizardPage(String pageName) {
        super(pageName);
    }

    protected AddRuntimeWizard getAddRuntimeWizard() {
        return (AddRuntimeWizard) getWizard();
    }
}
