/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ui.components.ServerGroupGeneralControl;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * Page used to select type of the server.
 * <p>
 * <i>Created: 29 Aug 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ServerGroupGeneralWizardPage extends WizardPage {

    private boolean initialised = false;
    private ServerGroupGeneralControl serverGroupGeneralControl;

    public ServerGroupGeneralWizardPage() {
        super("ServerTypePage"); //$NON-NLS-1$
        setTitle(Messages.ServerGroupGeneralWizardPage_title);
        setDescription(Messages.ServerGroupGeneralWizardPage_message);
    }

    public void createControl(Composite parent) {
        serverGroupGeneralControl = new ServerGroupGeneralControl(parent) {
            /** {@inheritDoc} */
            @Override
            public void setMessage(String newMessage, int newType) {
                ServerGroupGeneralWizardPage.this.setMessage(newMessage, newType);
            }

            /** {@inheritDoc} */
            @Override
            protected void setControlComplete(boolean isComplete) {
                ServerGroupGeneralWizardPage.this.setPageComplete(isComplete);
            }
        };
        PlatformUI.getWorkbench().getHelpSystem().setHelp(
                serverGroupGeneralControl, getTitle());

        setControl(serverGroupGeneralControl);
    }

    void initializePageDefaults() {
        serverGroupGeneralControl.initializeControlDefaults();
        initialised = true;

    }

    @Override
    public void setVisible(boolean visible) {
        if (visible == true && !initialised) {
            initializePageDefaults();
        }
        super.setVisible(visible);
    }

    @Override
    public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp(getTitle());
    }

    /**
     * @return
     */
    public String getServerGroupName() {
        return serverGroupGeneralControl.getServerGroupName();
    }

    /**
     * @return
     */
    public ServerGroupType getServerGroupType() {
        return serverGroupGeneralControl.getServerGroupType();
    }

    /**
     * @return
     */
    public List<Server> getAssociatedServers() {
        return serverGroupGeneralControl.getAssociatedServers();
    }

}
