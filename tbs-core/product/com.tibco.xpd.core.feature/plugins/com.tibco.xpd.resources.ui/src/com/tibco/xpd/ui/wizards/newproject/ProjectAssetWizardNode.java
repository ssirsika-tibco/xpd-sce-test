/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.wizards.newproject;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.swt.graphics.Point;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetConfigurator2;
import com.tibco.xpd.resources.projectconfig.projectassets.IAssetWizardPage;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Project asset selection wizard node. This will create the wizard based on
 * pages contributed by the selected assets in the new project wizard.
 * 
 * @author njpatel
 */
public class ProjectAssetWizardNode implements IWizardNode {

    private boolean contentCreated = false;

    private AssetWizard wizard = null;

    private final List<ProjectAssetElement> assets;

    /**
     * Project asset wizard node.
     * 
     * @param wizardTitle
     *            title of this wizard
     * @param wizardContainer
     *            the wizard container
     * @param assets
     *            Wizard pages contributed by this assets will be added to this
     *            wizard node.
     */
    public ProjectAssetWizardNode(String wizardTitle,
            IWizardContainer wizardContainer, List<ProjectAssetElement> assets) {
        this(wizardTitle, wizardContainer, assets, null);
    }

    /**
     * Project asset wizard node.
     * 
     * @param wizardTitle
     *            title of this wizard
     * @param wizardContainer
     *            the wizard container
     * @param assets
     *            Wizard pages contributed by this assets will be added to this
     *            wizard node.
     * @param detailsProvider
     *            project details provider that the wizard pages can use to get
     *            the details.
     * 
     * @since 3.2
     */
    public ProjectAssetWizardNode(String wizardTitle,
            IWizardContainer wizardContainer, List<ProjectAssetElement> assets,
            IProjectDetailsProvider detailsProvider) {
        this.assets = assets;

        // Create the wizard
        wizard = new AssetWizardImpl(detailsProvider);
        wizard.setContainer(wizardContainer);

        if (wizardTitle != null) {
            wizard.setWindowTitle(wizardTitle);
        }

        wizard.addPages();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#dispose()
     */
    @Override
    public void dispose() {
        if (wizard != null) {
            wizard.dispose();
            wizard = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getExtent()
     */
    @Override
    public Point getExtent() {
        return new Point(-1, -1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#getWizard()
     */
    @Override
    public IWizard getWizard() {
        return wizard;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizardNode#isContentCreated()
     */
    @Override
    public boolean isContentCreated() {
        return contentCreated;
    }

    /**
     * Implementation of the <code>AssetWizard</code> wizard class. This will
     * create a wizard combining the wizard pages associated with the asset
     * types selected in the new XPD project wizard.
     * 
     * @author njpatel
     * 
     */
    private class AssetWizardImpl extends AssetWizard implements
            IProjectDetailsProvider {

        private final IProjectDetailsProvider detailsProvider;

        private final Logger logger;

        public AssetWizardImpl(IProjectDetailsProvider detailsProvider) {
            this.detailsProvider = detailsProvider;
            this.logger = XpdResourcesUIActivator.getDefault().getLogger();
        }

        @Override
        public void addPages() {

            if (!contentCreated) {
                // Capture all assets that will be creating a special folder
                List<ProjectAssetElement> specialFolderAssets =
                        new ArrayList<ProjectAssetElement>();
                for (ProjectAssetElement asset : assets) {

                    try {
                        // Check if this asset contributes a special folder
                        if (asset.getConfigurator() instanceof AbstractSpecialFolderAssetConfigurator
                                && asset.getConfiguration() instanceof SpecialFolderAssetConfiguration
                                && asset.getWizardPages().length == 0) {
                            specialFolderAssets.add(asset);
                        }
                        addPages(asset);

                        // If there are any extending asset types then add their
                        // pages as well
                        for (ProjectAssetElement extensions : asset
                                .getExtendingAssets()) {
                            addPages(extensions);
                        }

                    } catch (CoreException e) {
                        throw new IllegalArgumentException(
                                String.format(Messages.ProjectAssetWizardNode_0,
                                        asset.getId(),
                                        e.getLocalizedMessage()));
                    }
                }

                // If there are assets that contribute a special folder then add
                // the special folder creation page
                if (!specialFolderAssets.isEmpty()) {
                    addPage(new SpecialFoldersWizardPage("specialFolders", //$NON-NLS-1$
                            specialFolderAssets));
                }

                contentCreated = true;
            }
        }

        /**
         * Add wizard pages from the given asset type.
         * 
         * @param asset
         * @throws CoreException
         */
        private void addPages(ProjectAssetElement asset) throws CoreException {
            IAssetWizardPage[] wizardPages = asset.getWizardPages();

            if (wizardPages != null) {

                // Create config object and add to map
                Object config = asset.getConfiguration();

                // Add and initialise the page
                for (IAssetWizardPage page : wizardPages) {

                    /*
                     * Only add page if it is visible, orset to show
                     * configuration page when invisible (SCF-401 : Show Asset
                     * Configuration page in Project Wizard for hidden assets.)
                     */
                    if (asset.isVisible()
                            || asset.isShowConfigPageWhenInvisible()) {
                        addPage(page);
                    }
                    page.init(config);
                }
            }
        }

        @Override
        public boolean canFinish() {

            // If the pages haven't been added then do so
            addPages();

            return super.canFinish();
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.resources.projectconfig.projectassets.
         * IAssetConfigurationProvider#getAssetConfiguration(java.lang.String)
         */
        @Override
        public Object getAssetConfiguration(String assetId)
                throws CoreException {
            if (assets != null) {
                for (ProjectAssetElement asset : assets) {
                    if (asset.getId().equals(assetId)) {
                        return asset.getConfiguration();
                    }
                }

                // If we got here then no asset was found with the given id
                throw new IllegalArgumentException(
                        String.format("Asset with id '%s' not found.", assetId)); //$NON-NLS-1$
            }

            return null;
        }

        @Override
        public boolean performFinish(IProgressMonitor monitor, IProject project)
                throws CoreException {
            boolean ret = true;

            if (logger.isDebugEnabled()) {
                logger.debug("PROCESSING ASSETS FOR PROJECT: " //$NON-NLS-1$
                        + project.getName());
            }
            if (assets != null && assets.size() > 0) {
                SubProgressMonitor subMonitor =
                        new SubProgressMonitor(monitor, assets.size());

                // Will contain asset IDs to add to the project config
                List<String> assetIds = new ArrayList<String>();

                try {
                    for (ProjectAssetElement asset : assets) {
                        subMonitor.subTask(String
                                .format(Messages.ProjectAssetWizardNode_1,
                                        asset.getName()));

                        processAsset(project, asset);

                        // Update the asset ID list
                        assetIds.add(asset.getId());

                        subMonitor.worked(1);

                        if (subMonitor.isCanceled()) {
                            // User cancelled
                            subMonitor.done();
                            throw new OperationCanceledException();
                        }
                    }

                } finally {
                    // If there is a list of asset Ids then add to the project
                    // config
                    if (!assetIds.isEmpty()) {
                        ProjectConfig config =
                                XpdResourcesPlugin.getDefault()
                                        .getProjectConfig(project);

                        if (config != null) {
                            config.addAssetTypes(new BasicEList<String>(
                                    assetIds));
                        } else {
                            // Failed to get the config
                            throw new CoreException(new Status(IStatus.ERROR,
                                    XpdResourcesUIActivator.ID, IStatus.OK,
                                    Messages.ProjectAssetWizardNode_2, null));
                        }
                    }
                }

                subMonitor.done();
            }

            if (logger.isDebugEnabled()) {
                logger.debug("COMPLETED PROCESSING ASSETS FOR PROJECT: " //$NON-NLS-1$
                        + project.getName());
            }

            return ret;
        }

        /**
         * Process the given asset and all extension of this asset (if any).
         * 
         * @param project
         * @param asset
         * @throws CoreException
         */
        private void processAsset(IProject project, ProjectAssetElement asset)
                throws CoreException {
            long startTime = 0;
            if (logger.isDebugEnabled()) {
                logger.debug(String
                        .format("Start of processing asset '%s'(%s)", asset.getName(), //$NON-NLS-1$
                                asset.getId()));
                startTime = System.currentTimeMillis();
            }

            // Ask wizard pages to update the configuration
            IAssetWizardPage[] wizardPages = asset.getWizardPages();

            if (wizardPages != null) {
                for (IAssetWizardPage page : wizardPages) {
                    page.updateConfiguration();
                }
            }

            // Run the configuration of the asset
            IAssetConfigurator configurator = asset.getConfigurator();

            // If this configuration implements IAssetCofigurator2 then set the
            // configuration provider
            if (configurator instanceof IAssetConfigurator2) {
                ((IAssetConfigurator2) configurator)
                        .setAssetConfigurationProvider(this);
            }

            if (configurator != null) {
                configurator.configure(project, asset.getConfiguration());
            }

            if (logger.isDebugEnabled()) {
                logger.debug(String
                        .format("End of processing asset '%s'(%s) - time taken: %d", //$NON-NLS-1$
                                asset.getName(),
                                asset.getId(),
                                System.currentTimeMillis() - startTime));
            }

            // If this asset has extension then process them
            ProjectAssetElement[] extendingAssets = asset.getExtendingAssets();

            if (extendingAssets.length > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("*** Processing the following extension assets: ***"); //$NON-NLS-1$
                }
                for (ProjectAssetElement ext : extendingAssets) {
                    processAsset(project, ext);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("*** Completed processing of extension assets. ***"); //$NON-NLS-1$
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#
         * getProjectDetails()
         */
        @Override
        public ProjectDetails getProjectDetails() {
            return detailsProvider != null ? detailsProvider
                    .getProjectDetails() : null;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.wizards.newproject.IProjectDetailsProvider#
         * getProjectName()
         */
        @Override
        public String getProjectName() {
            return detailsProvider != null ? detailsProvider.getProjectName()
                    : null;
        }
    }

}
