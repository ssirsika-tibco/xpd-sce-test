/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
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
public class ServerGroupGeneralPropertyPage extends PropertyPage {

    private boolean initialised = false;
    private ServerGroupGeneralControl serverGroupGeneralControl;

    public ServerGroupGeneralPropertyPage() {
        super();
        setTitle(Messages.ServerGroupGeneralPropertyPage_serverGroup_title);
        setDescription(Messages.ServerGroupGeneralPropertyPage_serverGroup_message);
    }

    /**
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        serverGroupGeneralControl = new ServerGroupGeneralControl(parent,
                getServerGroup()) {
            /** {@inheritDoc} */
            @Override
            public void setMessage(String newMessage, int newType) {
                ServerGroupGeneralPropertyPage.this.setMessage(newMessage,
                        newType);
            }

            /** {@inheritDoc} */
            @Override
            protected void setControlComplete(boolean isComplete) {
                ServerGroupGeneralPropertyPage.this.setValid(isComplete);
            }
        };
        PlatformUI.getWorkbench().getHelpSystem().setHelp(
                serverGroupGeneralControl, getTitle());

        return serverGroupGeneralControl;
    }

    void initializePageDefaults() {
        serverGroupGeneralControl.initializeControlDefaults();
        performDefaults();
        initialised = true;

    }

    /**
     * Populates the page with the default values.
     */
    @Override
    protected void performDefaults() {
        serverGroupGeneralControl.initializeControlDefaults();
    }

    @Override
    public boolean performOk() {
        performApply();
        return true;
    }

    @Override
    protected void performApply() {
        try {
            serverGroupGeneralControl.commitToServerGroup();
            /* saving server */
            IRunnableWithProgress op = new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    try {
                        monitor
                                .beginTask(
                                        Messages.ServerGroupGeneralPropertyPage_task_shortdesc
                                                + serverGroupGeneralControl
                                                        .getServerGroupName(),
                                        3);
                        monitor.worked(1);
                        DeployUIActivator.getServerManager()
                                .saveServerContainer();
                        monitor.worked(1);
                    } catch (Exception e) {
                        throw new InvocationTargetException(e);
                    } finally {
                        monitor.done();
                    }
                }
            };
            PlatformUI.getWorkbench().getProgressService().runInUI(
                    PlatformUI.getWorkbench().getProgressService(), op, null);
        } catch (Exception e) {
            // Log only.
            DeployUIActivator.getDefault().getLogger().error(e);
        }
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

    private ServerGroup getServerGroup() {
        return (ServerGroup) (getElement()).getAdapter(ServerGroup.class);
    }

}
