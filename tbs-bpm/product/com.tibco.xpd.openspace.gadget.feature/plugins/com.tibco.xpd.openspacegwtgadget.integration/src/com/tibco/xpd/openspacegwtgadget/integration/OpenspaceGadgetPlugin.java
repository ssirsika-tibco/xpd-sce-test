/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.openspacegwtgadget.integration;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author aallway
 * @since 7 Dec 2012
 */
public class OpenspaceGadgetPlugin extends AbstractUIPlugin {

    /** The plug-in ID */
    public static final String PLUGIN_ID =
            "com.tibco.xpd.openspacegwtgadget.integration"; //$NON-NLS-1$

    /** The source plug-in daaResources folder */
    public static final String PLUGIN_DAA_RESOURCES_FOLDER = "daaResources"; //$NON-NLS-1$

    /**
     * Main menu / toolbar icon for Openspace Gadget Development options.
     */
    public static final String IMG_OPENSPACEGADGET_POPUP =
            "icons/openspaceGadgetPopup.png"; //$NON-NLS-1$

    /**
     * Openspace sample wizard title image
     */
    public static final String IMG_OPENSPACE_SAMPLE_WIZARD =
            "icons/openspaceSampleWizard.png"; //$NON-NLS-1$

    /**
     * Export Openspace Gadget Development DAA icon (menus etc).
     */
    public static final String IMG_EXPORT_OPENSPACE_DAA = "icons/daa_16x16.png"; //$NON-NLS-1$

    /**
     * Export Openspace Gadget Development DAA wizard banner image
     */
    public static final String IMG_EXPORT_OPENSPACE_DAA_WIZARD =
            "icons/daa_wiz.png"; //$NON-NLS-1$

    // The shared instance
    private static OpenspaceGadgetPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public OpenspaceGadgetPlugin() {
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
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static OpenspaceGadgetPlugin getDefault() {
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
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}
