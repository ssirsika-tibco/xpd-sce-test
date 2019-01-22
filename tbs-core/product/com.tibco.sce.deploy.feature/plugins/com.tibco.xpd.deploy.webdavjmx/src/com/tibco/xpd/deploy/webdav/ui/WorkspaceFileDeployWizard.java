/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.ui;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.navigator.DeployResolver;
import com.tibco.xpd.deploy.ui.util.DeployUtil;
import com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard;
import com.tibco.xpd.deploy.webdav.Utils;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

/**
 * Deployment Wizard for the Forms deployment to WebDAV Server.
 * The implmentation is adapted from the work by Jan Arciuchiewicz and
 * Robert Hudson.
 * @author TIBCO Software, Inc.
 */
public class WorkspaceFileDeployWizard extends Wizard implements IDeployWizard {

    /** WebDAV server. */
    private Server mServer;
    /** Paths to the selected modules. */
    private final List<URL> mModulesUrls;
    /** instance of the SelectModulesPage. */
    private SelectModulesPage mSelectModulesPage;

    // Not used now.
    // private SelectWebDAVLocationPage mSelectWebDAVLocationPage;

    /**
     * The constructor.
     */
    public WorkspaceFileDeployWizard() {
        setWindowTitle(Messages.WorkspaceFileDeployWizard_window_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault().getImageRegistry().getDescriptor(
            DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        mModulesUrls = new ArrayList<URL>();
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
        mSelectModulesPage = new SelectModulesPage();
        // mSelectWebDAVLocationPage = new SelectWebDAVLocationPage(mServer);
        addPage(mSelectModulesPage);
        // addPage(mSelectWebDAVLocationPage);
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getModulesUrls()
     */
    public List<URL> getModulesUrls() {
        return mModulesUrls;
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getServer()
     */
    public Server getServer() {
        return mServer;
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getWorkspaceModules()
     */
    public Collection<WorkspaceModule> getWorkspaceModules() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    /** {@inheritDoc}  */
    public final boolean performFinish() {
        final List<IFile> modules = mSelectModulesPage.getSelectedFiles();
        mModulesUrls.clear();
        IRunnableWithProgress op = new IRunnableWithProgress() {
            public void run(IProgressMonitor monitor) throws InvocationTargetException {
                try {
                    int work = modules.size();
                    monitor.beginTask(Messages.WorkspaceFileDeployWizard_deploy_label, work);
                    Utils.clearPathToIFileMap();
                    for (IFile file : modules) {
                        monitor.setTaskName(Messages.WorkspaceFileDeployWizard_preparing_label + file.getName());
                        mModulesUrls.add(file.getLocationURI().toURL());
                        Utils.putIntoPathToIFileMap(file.getLocationURI().toURL().toString(), file);
                        monitor.worked(1);
                    }
                    if (getServer() != null
                            && getServer().getServerType() != null
                            && getServer().getServerType().getId() != null) {
                        // Add dependencies
                        List<IResource> dependencyModules =
                                DeployUtil
                                        .getDependencyModules(new ArrayList<IResource>(
                                                modules),
                                                getServer().getServerType()
                                                        .getId());
                        if (dependencyModules != null
                                && !dependencyModules.isEmpty()) {
                            for (IResource dependencyModule : dependencyModules) {
                                if (dependencyModule != null
                                        && dependencyModule.getLocationURI() != null
                                        && dependencyModule.getLocationURI()
                                                .toURL() != null
                                        && dependencyModule instanceof IFile) {
                                    monitor
                                            .setTaskName(Messages.WorkspaceFileDeployWizard_preparing_label
                                                    + dependencyModule
                                                            .getName());
                                    mModulesUrls.add(dependencyModule
                                            .getLocationURI().toURL());
                                    Utils
                                            .putIntoPathToIFileMap(dependencyModule
                                                    .getLocationURI().toURL()
                                                    .toString(),
                                                    (IFile) dependencyModule);
                                }
                            }
                        }
                    }
                } catch (MalformedURLException mue) {
                    throw new InvocationTargetException(mue);
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
            MessageDialog.openError(getShell(), Messages.WorkspaceFileDeployWizard_exception_msg, realException.getMessage());
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * @see com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#setServer(com.tibco.xpd.deploy.Server)
     */
    public void setServer(Server server) {
        mServer = server;
    }

}
