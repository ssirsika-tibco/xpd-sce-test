/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.wizards.deploy;

import org.eclipse.jface.wizard.IWizard;

import com.tibco.xpd.deploy.Server;

/**
 * Interface that tells DeployModuleAction that all work is done in perform
 * finish of wizard.
 * 
 * @author Jan Arciuchiewicz
 */
public interface ISimpleDeployWizard extends IWizard {

    /**
     * Sets the context server for the wizard.
     */
    void setServer(Server server);

    /**
     * Gets the context server.
     * 
     * @return the context server.
     */
    Server getServer();

}
