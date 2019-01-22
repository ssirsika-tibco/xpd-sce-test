/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.deploy.manager.ServerManager;
import com.tibco.xpd.deploy.manager.ServerManagerImpl;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class DeployUIActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.deploy.ui"; //$NON-NLS-1$

    // The shared instance
    private static DeployUIActivator plugin;

    private final AutoDeployManager autoDeployManager;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public DeployUIActivator() {
        plugin = this;
        autoDeployManager = new AutoDeployManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        ResourcesPlugin.getWorkspace().addResourceChangeListener(
                autoDeployManager, IResourceChangeEvent.POST_BUILD);

        // Set the default value of the status of the deploy result dialog - set
        // to show by default
        getPreferenceStore().setDefault(
                DeployUIConstants.SHOW_DEPLOY_RESULT_DIALOG, true);
        getPreferenceStore().setDefault(
                DeployUIConstants.SHOW_DELETE_NODE_DIALOG, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(
                autoDeployManager);
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static DeployUIActivator getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Obtains shared instance of ServerManager.
     * 
     * @return the shared instance of server manager.
     */
    public static ServerManager getServerManager() {
        return ServerManagerImpl.getInstance();
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = DeployUIConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Update the preference store with whether to show the deployment result
     * dialog.
     * 
     * @param show
     *            <code>true</code> to show the dialog, <code>false</code>
     *            otherwise.
     */
    public void setShowDeployResultDialog(boolean show) {
        getPreferenceStore().setValue(
                DeployUIConstants.SHOW_DEPLOY_RESULT_DIALOG, show);
    }

    /**
     * Check whether the deployment result dialog should be shown.
     * 
     * @return <code>true</code> to show the dialog, <code>false</code> to
     *         not show the dialog.
     */
    public boolean showDeployResultDialog() {
        return getPreferenceStore().getBoolean(
                DeployUIConstants.SHOW_DEPLOY_RESULT_DIALOG);
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return the plug-in logger.
     */
    public Logger getLogger() {
        return logger;
    }

}
