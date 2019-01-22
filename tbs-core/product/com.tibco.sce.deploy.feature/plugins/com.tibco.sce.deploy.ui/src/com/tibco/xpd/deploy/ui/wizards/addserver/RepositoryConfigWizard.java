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
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import com.tibco.xpd.deploy.ConfigParameter;
import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.RepositoryConfig;
import com.tibco.xpd.deploy.RepositoryType;
import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.internal.Messages;
import com.tibco.xpd.deploy.ui.wizards.RepositoryConfigWizardPage;
import com.tibco.xpd.deploy.ui.wizards.deploy.ExtAddWizardSelectionPage;

/**
 * The sub-wizard for adding repository associated with server.
 * <p>
 * <i>Created: 8 September 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class RepositoryConfigWizard extends Wizard {

    private static final String REPOSITORY_WIZARD_PAGE = "repositoryWizardPage"; //$NON-NLS-1$

    private static final String REPOSITORY_TYPE_ID = "repositoryTypeId"; //$NON-NLS-1$

    private static final String REPOSITORY_CONFIG_WIZARD_PAGE_EXTENSION = "repositoryConfigWizardPage"; //$NON-NLS-1$

    private final RepositoryType repositoryType;

    private final IWizard parentWizard;

    private final RepositoryConfig config;

    private final List<RepositoryConfigWizardPage> discoveredPages;
    
    /**
     * @param repoType
     */
    public RepositoryConfigWizard(IWizard parentWizard, RepositoryType repoType) {
        setWindowTitle(Messages.RepositoryConfigWizard_Wizard_title);
        this.parentWizard = parentWizard;
        this.repositoryType = repoType;
        this.config = createRepositoryConfig(repoType);
        discoveredPages = getExtensionRepositoyWizardPages(repositoryType
                .getId());
    }

    @Override
    public void addPages() {
        for (RepositoryConfigWizardPage page : discoveredPages) {
            page.init(repositoryType, config);
            addPage(page);
        }      
        
        // add any extension pages assiciated with 
        // the Server Type Config Sub Wizard 
        if (parentWizard instanceof ServerTypeConfigWizard){
            List<ExtAddWizardSelectionPage> serverPages = ((ServerTypeConfigWizard)parentWizard).getDiscoveredPages();
            for (ExtAddWizardSelectionPage page : serverPages) {            
                addPage(page);            
            }
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
        return parentWizard.canFinish();
    }

    public void transferStateToConfig() {
        IWizardPage[] pages = getPages();
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] instanceof RepositoryConfigWizardPage){
                ((RepositoryConfigWizardPage) pages[i]).transferStateToConfig();
            }
        }
    }

    public RepositoryConfig getRepositoryConfig() {
        return config;
    }

    public RepositoryType getRepositoryType() {
        return repositoryType;
    }

    private List<RepositoryConfigWizardPage> getExtensionRepositoyWizardPages(
            String repoTypeId) {
        List<RepositoryConfigWizardPage> pages = new ArrayList<RepositoryConfigWizardPage>();
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] elements = registry
                .getConfigurationElementsFor(DeployUIActivator.PLUGIN_ID,
                        REPOSITORY_CONFIG_WIZARD_PAGE_EXTENSION);
        for (int i = 0; i < elements.length; i++) {
            String extRepoType = getAttribute(elements[i], REPOSITORY_TYPE_ID,
                    null);
            if (repoTypeId.equals(extRepoType)) {
                RepositoryConfigWizardPage wizardPage = null;
                try {
                    wizardPage = (RepositoryConfigWizardPage) elements[i]
                            .createExecutableExtension(REPOSITORY_WIZARD_PAGE);
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

    @SuppressWarnings("unchecked")
    private static RepositoryConfig createRepositoryConfig(
            RepositoryType repoType) {
        RepositoryConfig repoConfig = DeployFactory.eINSTANCE
                .createRepositoryConfig();
        List<ConfigParameterInfo> parameterInfos = repoType
                .getRepositoryParameterInfos();
        for (ConfigParameterInfo info : parameterInfos) {
            ConfigParameter parameter = DeployFactory.eINSTANCE
                    .createConfigParameter();
            parameter.setKey(info.getKey());
            parameter.setValue(info.getDefaultValue());
            parameter.setConfigParameterInfo(info);
            repoConfig.getConfigParameters().add(parameter);
        }
        return repoConfig;
    }

    List<RepositoryConfigWizardPage> getDiscoveredPages() {
        return discoveredPages;
    }
}
