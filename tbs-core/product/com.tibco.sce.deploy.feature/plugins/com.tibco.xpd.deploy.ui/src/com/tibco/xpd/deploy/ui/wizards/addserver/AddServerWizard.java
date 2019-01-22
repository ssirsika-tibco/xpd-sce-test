/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
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
import org.eclipse.ui.navigator.CommonViewer;

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Repository;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerConfig;
import com.tibco.xpd.deploy.ServerContainer;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.actions.ConnectServerAction;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * The wizard adding a new deployment server.
 * 
 * @author Jan Arciuchiewicz
 */
public class AddServerWizard extends Wizard implements INewWizard {

    private final ServerManager serverManager;

    private final ServerTypePage serverTypePage;

    private ServerParametersPage serverParametersPage;

    private final RuntimeClientPage runtimeClientPage;

    private CommonNavigator navigator;

    private Server createdServer;

    private static final Logger log = DeployUIActivator.getDefault()
            .getLogger();

    private IStructuredSelection selection;

    /**
     * Constructor.
     */
    public AddServerWizard() {
        super();
        setWindowTitle(Messages.AddServerWizard_Wizard_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_SERVER_WIZARD));
        setNeedsProgressMonitor(true);
        serverManager = DeployUIActivator.getServerManager();
        serverTypePage = new ServerTypePage();
        runtimeClientPage = new RuntimeClientPage(false);
        serverParametersPage = new ServerParametersPage();
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        IWorkbenchPart activePart =
                workbench.getActiveWorkbenchWindow().getActivePage()
                        .getActivePart();
        if (activePart instanceof CommonNavigator) {
            navigator = (CommonNavigator) activePart;
        }
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        addPage(serverTypePage);
        addPage(runtimeClientPage);
        addPage(serverParametersPage);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        final String serverName = serverTypePage.getServerName();
        final ServerType serverType = serverTypePage.getServerType();
        final Runtime serverRuntime = runtimeClientPage.getRuntime();

        ServerTypeConfigWizardNode serverTypeConfigWizardNode =
                serverTypePage.getServerTypeWizardNode();

        if (serverTypeConfigWizardNode != null) {
            ServerTypeConfigWizard serverTypeConfigWizard =
                    ((ServerTypeConfigWizard) serverTypeConfigWizardNode
                            .getWizard());
            serverParametersPage =
                    serverTypeConfigWizard.getServerParametersPage();
        }

        final ServerConfig serverConfig =
                serverParametersPage.getServerConfig();
        RepositoryConfigWizardNode node =
                serverParametersPage.getRepositoryWizardNode();
        RepositoryConfig tempRepoConfig = null;
        RepositoryType tempRepoType = null;
        if (node != null) {
            RepositoryConfigWizard repositoryConfigWizard =
                    ((RepositoryConfigWizard) node.getWizard());
            repositoryConfigWizard.transferStateToConfig();
            tempRepoConfig = repositoryConfigWizard.getRepositoryConfig();
            tempRepoType = repositoryConfigWizard.getRepositoryType();
        }
        final RepositoryConfig repoConfig = tempRepoConfig;
        final RepositoryType repoType = tempRepoType;
        // runtime
        EditingDomain ed = serverManager.getEditingDomain();
        DeployFactory factory = DeployFactory.eINSTANCE;
        ServerContainer serverContainer = serverManager.getServerContainer();
        if (!serverContainer.getRuntimes().contains(serverRuntime)) {
            Command cmd =
                    AddCommand.create(ed,
                            serverContainer,
                            DeployPackage.eINSTANCE
                                    .getServerContainer_Runtimes(),
                            serverRuntime);
            if (cmd.canExecute()) {
            }
        }
        // server
        Server server = factory.createServer();
        server.setName(serverName);

        /* runtime */
        server.setRuntime(serverRuntime);

        /* server type */
        ServerType privateServerType = serverType;
        server.setServerType(privateServerType);
        server.setServerConfig(serverConfig);

        /* repository */
        Repository repo = factory.createRepository();
        repo.setRepositoryType(repoType);
        repo.setRepositoryConfig(repoConfig);
        server.setRepository(repo);

        if (serverTypeConfigWizardNode != null) {
            ServerTypeConfigWizard serverTypeConfigWizard =
                    ((ServerTypeConfigWizard) serverTypeConfigWizardNode
                            .getWizard());
            serverTypeConfigWizard.addConfigToServer(server);
        }

        Command cmd =
                AddCommand.create(ed,
                        serverContainer,
                        DeployPackage.eINSTANCE.getServerContainer_Servers(),
                        server);
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);
        }
        if (navigator != null) {
            navigator.getCommonViewer().refresh();
            navigator.selectReveal(new StructuredSelection(server));
        }
        /* saving server */
        IRunnableWithProgress op = new IRunnableWithProgress() {
            @Override
            public void run(IProgressMonitor monitor)
                    throws InvocationTargetException {
                try {
                    monitor.beginTask(Messages.AddServerWizard_Job_shortdesc
                            + serverName, 3);
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
            StringBuilder sb =
                    new StringBuilder(
                            Messages.AddServerWizard_Exception_message);
            if (realException.getLocalizedMessage() != null) {
                sb.append('\n').append(realException.getLocalizedMessage());
            }
            MessageDialog.openError(getShell(),
                    Messages.AddServerWizard_Excepton_title,
                    sb.toString());
            return false;
        }
        createdServer = server;
        /*
         * SCF-75: Check if the user wants to immediately connect to the server
         * that they just created.
         */
        checkAndConnectServerImmediately();
        return true;
    }

    /**
     * Checks if the user wants to connect immediately to the server they just
     * created. If yes then connects to the server.
     */
    private void checkAndConnectServerImmediately() {
        /*
         * SCF-75: Check if the user wants to immediately connect to the server
         * that they just created.
         */
        if (serverParametersPage.shouldConnectServerImmediately()) {

            /*
             * Calling the 'ConnectServerAction' for 2 reasons , 1. Code
             * reusability, 2. ConnectServerAction has everything that we need
             * to connect the server(i.e. Monitor based job, exception handling
             * etc)
             */
            CommonViewer commonViewer = null;
            if (navigator != null) {
                commonViewer = navigator.getCommonViewer();
            }
            ConnectServerAction connectServerAction =
                    new ConnectServerAction(commonViewer) {
                        /**
                         * @see org.eclipse.ui.actions.BaseSelectionListenerAction#getStructuredSelection()
                         * 
                         * @return
                         */
                        @Override
                        public IStructuredSelection getStructuredSelection() {

                            return selection;
                        }

                        /**
                         * @see com.tibco.xpd.deploy.ui.actions.ConnectServerAction#getServer(org.eclipse.jface.viewers.IStructuredSelection)
                         * 
                         * @param structSel
                         * @return
                         */
                        @Override
                        protected Server getServer(
                                IStructuredSelection structSel) {
                            /*
                             * We need to pass our server explicitly, because
                             * the selection(IStructuredSelection) will be the
                             * ServerContainer as the new server is not yet
                             * created and added to the container.
                             */
                            return createdServer;
                        }
                    };

            connectServerAction.run();
        }
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public ServerTypePage getServerTypePage() {
        return serverTypePage;
    }

    ServerParametersPage getServerParametersPage() {
        return serverParametersPage;
    }

    /**
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     * 
     * @return
     */
    @Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() instanceof ServerTypePage) {
            return false;
        }
        RepositoryConfigWizardNode node =
                (RepositoryConfigWizardNode) getServerParametersPage()
                        .getSelectedNode();
        if (node != null) {
            return node.canFinish() ? super.canFinish() : false;
        }
        return super.canFinish();
    }

    /**
     * @return
     */
    public RuntimeClientPage getRuntimeClientPage() {
        return runtimeClientPage;
    }

    public void setInitialServerType(ServerType serverType, boolean readOnly) {
        serverTypePage.setInitialServerType(serverType, readOnly);
    }

    public Server getCreatedServer() {
        return createdServer;
    }
}