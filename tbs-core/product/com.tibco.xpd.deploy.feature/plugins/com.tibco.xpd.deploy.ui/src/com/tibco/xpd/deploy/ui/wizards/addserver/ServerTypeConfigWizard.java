/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.wizards.addserver;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.Server;
import com.tibco.xpd.deploy.ServerType;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.deploy.ExtAddWizardSelectionPage;

/**
 * The sub-wizard for adding server type configuration pages associated with
 * server.
 * 
 * @author glewis
 * 
 */
public class ServerTypeConfigWizard extends Wizard {

    private static final String ATTR_ADD_SERVER_WIZARD_PAGE = "serverWizardPage"; //$NON-NLS-1$

    private static final String ATTR_SERVER_TYPE_ID = "serverTypeId"; //$NON-NLS-1$
    private static final String ATTR_PAGE_ID = "pageId"; //$NON-NLS-1$
    private static final String ATTR_AFTER_PAGE = "afterPage"; //$NON-NLS-1$

    private static final String ADD_SERVER_WIZARD_PAGE_EXTENSION = "addServerWizardPage"; //$NON-NLS-1$

    private final IWizard parentWizard;

    private final ServerType serverType;

    private final List<ExtAddWizardSelectionPage> discoveredPages;

    private ServerParametersPage serverParametersPage;

    /**
     * @param parentWizard
     * @param serverType
     */
    public ServerTypeConfigWizard(IWizard parentWizard, ServerType serverType) {
        setWindowTitle(Messages.RepositoryConfigWizard_Wizard_title);
        this.parentWizard = parentWizard;
        this.serverType = serverType;
        discoveredPages = getExtensionServerWizardPages(serverType.getId());
    }

    @Override
    public void addPages() {
        serverParametersPage = new ServerParametersPage();
        addPage(serverParametersPage);

        // add the other server pages
        for (ExtAddWizardSelectionPage page : discoveredPages) {
            addPage(page);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        // outer wizard will be called automatically
        return true;
    }

    @Override
    public boolean canFinish() {
        for (ExtAddWizardSelectionPage page : discoveredPages) {
            if (!page.isPageComplete()) {
                return false;
            }
        }

        RepositoryConfigWizardNode node = (RepositoryConfigWizardNode) serverParametersPage
                .getSelectedNode();
        if (node != null) {
            return node.canFinish() ? super.canFinish() : false;
        }

        return super.canFinish();
    }

    /**
     * Gets any extra pages defined from extension point addServerWizardPage and
     * adds them after the pages currently in addServerWizard.
     * 
     * @param serverTypeId
     * 
     * @return
     */
    private List<ExtAddWizardSelectionPage> getExtensionServerWizardPages(
            String serverTypeId) {
        List<ExtAddWizardSelectionPage> pages = new ArrayList<ExtAddWizardSelectionPage>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements = registry
                .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                        ADD_SERVER_WIZARD_PAGE_EXTENSION);
        for (int i = 0; i < elements.length; i++) {
            String extServerTypeId = getAttribute(elements[i],
                    ATTR_SERVER_TYPE_ID, null);
            if (serverTypeId.equals(extServerTypeId)) {
                ExtAddWizardSelectionPage wizardPage = null;
                try {
                    wizardPage = (ExtAddWizardSelectionPage) elements[i]
                            .createExecutableExtension(ATTR_ADD_SERVER_WIZARD_PAGE);
                    pages.add(wizardPage);
                } catch (CoreException e) {
                    throw new IllegalArgumentException(
                            "Cannot create executable extension for: " //$NON-NLS-1$
                                    + wizardPage, e);
                }
            }
        }
        return pages;
    }

    /**
     * @param configElement
     * @param attrName
     * @param defaultValue
     * @return
     */
    private static String getAttribute(IConfigurationElement configElement,
            String attrName, String defaultValue) {
        String value = configElement.getAttribute(attrName);
        if (value != null) {
            return value;
        }
        if (defaultValue != null) {
            return defaultValue;
        }
        throw new IllegalArgumentException("Missing attirbute: " + attrName); //$NON-NLS-1$
    }

    /**
     * Gets all the extra pages found from extension points
     * 
     * @return
     */
    public List<ExtAddWizardSelectionPage> getDiscoveredPages() {
        return discoveredPages;
    }

    /**
     * @param server
     */
    public void addConfigToServer(Server server) {
        for (ExtAddWizardSelectionPage page : discoveredPages) {
            page.performFinish(server);
        }
    }

    public boolean canFlipToNextPage() {
        return true;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public boolean hasDiscoveredPages() {
        return getDiscoveredPages().size() > 0;
    }

    public ServerParametersPage getServerParametersPage() {
        return serverParametersPage;
    }
}
