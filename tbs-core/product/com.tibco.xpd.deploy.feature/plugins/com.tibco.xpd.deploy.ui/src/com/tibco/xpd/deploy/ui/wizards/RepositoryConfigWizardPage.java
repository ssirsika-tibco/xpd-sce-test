/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;

/**
 * Represent WizardPage responsible for capturing repository's configuration
 * parameters. Page can update configuration immediately synchronising state of
 * the control or use {@link #transferStateToConfig()} method to update
 * configuration. It is guaranteed that {@link #transferStateToConfig()} method
 * will be invoked before {@link Wizard#performFinish()}.
 * <p>
 * <i>Created: 8 September 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public interface RepositoryConfigWizardPage extends IWizardPage {

    /**
     * This method will be invoked and will provide repository type and shared
     * configuration object to the page.
     * 
     * @param type
     *            the type of repository.
     * @param config
     *            the reference to the repository configuration.
     */
    void init(RepositoryType type, RepositoryConfig config);

    /**
     * Updates the state of the repository configuration.
     */
    void transferStateToConfig();
}
