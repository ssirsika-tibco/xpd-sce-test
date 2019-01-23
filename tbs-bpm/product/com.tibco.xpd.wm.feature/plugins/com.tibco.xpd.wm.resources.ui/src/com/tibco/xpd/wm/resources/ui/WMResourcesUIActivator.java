/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wm.resources.ui;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.wm.resources.ui.internal.WMImages;

/**
 * The activator class controls the plug-in life cycle
 */
public class WMResourcesUIActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.wm.resources.ui"; //$NON-NLS-1$

    public static final String WM_FILE_EXTENSION = "wm"; //$NON-NLS-1$

    public static final String WM_SPECIAL_FOLDER_KIND = "wm"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    // The shared instance
    private static WMResourcesUIActivator plugin;

    /**
     * The constructor
     */
    public WMResourcesUIActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static WMResourcesUIActivator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = WMImages.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], imageDescriptorFromPlugin(PLUGIN_ID, imgs[i]));
        }
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

    public static ImageDescriptor getImageDescriptor(
            IConfigurationElement configElement, String attrName) {
        String iconPath = configElement.getAttribute(attrName);
        // Get the icon descriptor
        if (iconPath != null) {
            return imageDescriptorFromPlugin(configElement
                    .getNamespaceIdentifier(), iconPath);
        }
        return null;
    }
}
