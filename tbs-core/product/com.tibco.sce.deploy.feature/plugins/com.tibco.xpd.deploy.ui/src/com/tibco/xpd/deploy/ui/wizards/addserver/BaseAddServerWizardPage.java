/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import com.tibco.xpd.deploy.ui.wizards.BaseDeployWizardPage;

/**
 * Base class for all {@see AddServerWizard} wizard pages.
 * <p>
 * <i>Created: 29 August 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public abstract class BaseAddServerWizardPage extends BaseDeployWizardPage {

    protected BaseAddServerWizardPage(String pageName) {
        super(pageName);
    }

    protected AddServerWizard getAddServerWizard() {
        return (AddServerWizard) getWizard();
    }
}
