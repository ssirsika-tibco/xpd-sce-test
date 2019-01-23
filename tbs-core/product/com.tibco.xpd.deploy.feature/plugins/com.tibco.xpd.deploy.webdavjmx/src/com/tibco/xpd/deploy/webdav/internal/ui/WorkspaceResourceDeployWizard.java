/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.internal.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.WorkspaceModule;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard;
import com.tibco.xpd.deploy.webdav.WebDAVPlugin;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;

/**
 * Deployment Wizard for the Forms deployment to WebDAV Server. The
 * implmentation is adapted from the work by Jan Arciuchiewicz and Robert
 * Hudson.
 * 
 * @author TIBCO Software, Inc.
 */
public class WorkspaceResourceDeployWizard extends Wizard implements
        IDeployWizard {

    /** WebDAV server. */
    private Server server;

    /** Paths to the selected modules. */
    private List<URL> modulesUrls;

    /** WebDAV resources selection page. */
    private WorkspaceResourceSelectionWizardPage resourceSelectionPage;

    private final Logger LOG = WebDAVPlugin.getDefault().getLogger();

    /**
     * The constructor.
     */
    public WorkspaceResourceDeployWizard() {
        setWindowTitle(Messages.WorkspaceFileDeployWizard_window_title);
        setDefaultPageImageDescriptor(DeployUIActivator.getDefault()
                .getImageRegistry()
                .getDescriptor(DeployUIConstants.IMG_DEPLOY_MODULE_WIZARD));
        setNeedsProgressMonitor(true);
        modulesUrls = new ArrayList<URL>();
    }

    /**
     * Adding the page(s) to the wizard.
     */
    @Override
    public final void addPages() {
        IStructuredSelection initialSelection = StructuredSelection.EMPTY;
        resourceSelectionPage =
                new WorkspaceResourceSelectionWizardPage(initialSelection);
        addPage(resourceSelectionPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getModulesUrls()
     */
    public List<URL> getModulesUrls() {
        return Collections.unmodifiableList(modulesUrls);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getServer()
     */
    public Server getServer() {
        return server;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#getWorkspaceModules
     * ()
     */
    public Collection<WorkspaceModule> getWorkspaceModules() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    /** {@inheritDoc} */
    @Override
    public final boolean performFinish() {
        modulesUrls = new ArrayList<URL>();
        final List<IResource> resourceModules =
                resourceSelectionPage.getSelectedDAVResources();
        // trace resources to deploy
        for (IResource resource : resourceModules) {
            LOG.debug("Resource: " + resource); //$NON-NLS-1$
        }

        try {
            for (IResource resource : resourceModules) {
                modulesUrls.add(resource.getLocationURI().toURL());
            }
        } catch (MalformedURLException e) {
            LOG.error(e);
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.deploy.ui.wizards.deploy.IDeployWizard#setServer(com.tibco
     * .xpd.deploy.Server)
     */
    public void setServer(Server server) {
        this.server = server;
    }
}
