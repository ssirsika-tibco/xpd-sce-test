/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.deployexternal;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard;

/**
 * Wizard for deploying module which can be external to workspace. The module
 * must be accessible vie URL.
 * <p>
 * <i>Created: 10 August 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeployExternalModuleWizard extends Wizard implements IDeployWizard {

    private Server selectedServer;

    private final ServerManager serverManager;

    private ModuleUrlPage serverTypePage;

    final private List<URL> modulesUrls;

    /**
     * Constructor.
     */
    public DeployExternalModuleWizard() {
        super();
        setWindowTitle(Messages.DeployExternalModuleWizard_Wizard_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        serverManager = DeployUIActivator.getServerManager();
        modulesUrls = new ArrayList<URL>();
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public void addPages() {
        serverTypePage = new ModuleUrlPage();
        addPage(serverTypePage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        final URI moduleURI = serverTypePage.getModuleURI();

        if (moduleURI != null) {
            modulesUrls.clear();
            IRunnableWithProgress op = new IRunnableWithProgress() {
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException {
                    try {
                        monitor
                                .beginTask(Messages.DeployExternalModuleWizard_Job_shortdesc,
                                        1);
                        modulesUrls.add(moduleURI.toURL());
                        monitor.worked(1);
                    } catch (MalformedURLException e) {
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
            } catch (InvocationTargetException ite) {
                Throwable e = ite.getTargetException();
                Shell s =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getShell();
                String title =
                        Messages.DeployExternalModuleWizard_DeploymentProblem_title2;
                String message =
                        String
                                .format(Messages.DeployExternalModuleWizard_InvalidUrl_message,
                                        serverTypePage.getModuleControlText());
                Status status =
                        new Status(IStatus.ERROR, DeployUIActivator.PLUGIN_ID,
                                0, e.getLocalizedMessage(), e);
                ErrorDialog.openError(s, title, message, status);
                DeployUIActivator.getDefault().getLogger().debug(e, message);
                return false;
            }

            return true;
        }
        
        return false;
    }

    ServerManager getServerManager() {
        return serverManager;
    }

    ModuleUrlPage getServerTypePage() {
        return serverTypePage;
    }

    public Server getServer() {
        return selectedServer;
    }

    public void setServer(Server server) {
        selectedServer = server;
    }

    public List<URL> getModulesUrls() {
        return modulesUrls;
    }

    public Collection<WorkspaceModule> getWorkspaceModules() {
        return null;
    }

}