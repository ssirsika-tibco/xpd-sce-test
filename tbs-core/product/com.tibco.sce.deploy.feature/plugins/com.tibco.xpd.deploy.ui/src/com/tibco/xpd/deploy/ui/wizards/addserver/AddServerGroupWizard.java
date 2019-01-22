/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerGroup;
import com.tibco.xpd.deploy.ServerGroupType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.resources.logger.Logger;

/**
 * The wizard adding a new deployment server group.
 * 
 * @author Jan Arciuchiewicz, Gary Lewis
 */
public class AddServerGroupWizard extends Wizard implements INewWizard {

    private final ServerManager serverManager;

    private ServerGroupGeneralWizardPage serverGroupTypePage;

    private CommonNavigator navigator;

    private static final Logger log = DeployUIActivator.getDefault()
            .getLogger();

    /**
     * Constructor.
     */
    public AddServerGroupWizard() {
        super();
        setWindowTitle("New Server Group");
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        DeployUIConstants.IMG_SERVER_WIZARD));
        setNeedsProgressMonitor(true);
        serverManager = DeployUIActivator.getServerManager();
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        IWorkbenchPart activePart = workbench.getActiveWorkbenchWindow()
                .getActivePage().getActivePart();
        if (activePart instanceof CommonNavigator) {
            navigator = (CommonNavigator) activePart;
        }
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        serverGroupTypePage = new ServerGroupGeneralWizardPage();
        addPage(serverGroupTypePage);
        // addPage(new CollectiveWizard());

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        final String serverGroupName = serverGroupTypePage.getServerGroupName();
        final ServerGroupType serverGroupType = serverGroupTypePage
                .getServerGroupType();

        DeployFactory factory = DeployFactory.eINSTANCE;
        EditingDomain ed = serverManager.getEditingDomain();
        ServerContainer serverContainer = serverManager.getServerContainer();

        // server group
        ServerGroup serverGroup = factory.createServerGroup();
        serverGroup.setName(serverGroupName);

        /* server group type */
        serverGroup.setServerGroupType(serverGroupType);

        List<Server> associatedServers = serverGroupTypePage
                .getAssociatedServers();

        CompoundCommand command = new CompoundCommand("Add Server Group");
        Command innerCommand;
        innerCommand = AddCommand.create(ed, serverContainer,
                DeployPackage.eINSTANCE.getServerContainer_ServerGroups(),
                serverGroup);
        command.append(innerCommand);
        if (associatedServers.size() > 0) {
            innerCommand = AddCommand.create(ed, serverGroup,
                    DeployPackage.eINSTANCE.getServerGroup_Members(),
                    associatedServers);
            command.append(innerCommand);
        }
        if (command.canExecute()) {
            ed.getCommandStack().execute(command);
        }
        if (navigator != null) {
            navigator.getCommonViewer().refresh();
            navigator.selectReveal(new StructuredSelection(serverGroup));
        }
        /* saving server */
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException {
                try {
                    monitor.beginTask(
                            "Adding Server Group: " + serverGroupName, 3);
                    monitor.worked(1);
                    serverManager.saveServerContainer();
                    monitor.worked(1);
                } catch (Exception e) {
                    throw new InvocationTargetException(e);
                } finally {
                    monitor.done();
                }
            }
        };
        try {
            getContainer().run(true, false, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            Throwable realException = e.getTargetException();
            log.error(e);
            StringBuilder sb = new StringBuilder("The exception was thrown.");
            if (realException.getLocalizedMessage() != null) {
                sb.append('\n').append(realException.getLocalizedMessage());
            }
            MessageDialog.openError(getShell(),
                    "Server Group creation problem.", sb.toString());
            return false;
        }

        return true;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public ServerGroupGeneralWizardPage getServerGroupTypePage() {
        return serverGroupTypePage;
    }
}