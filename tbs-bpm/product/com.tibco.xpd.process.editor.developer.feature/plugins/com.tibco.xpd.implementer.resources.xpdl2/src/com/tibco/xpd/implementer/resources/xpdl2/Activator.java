/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID =
            "com.tibco.xpd.implementer.resources.xpdl2"; //$NON-NLS-1$

    /** The shared instance. */
    private static Activator plugin;

    /** The logger for this plugin. */
    private Logger logger;

    /**
     * The constructor.
     */
    public Activator() {
        plugin = this;
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there is a problem.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        logger = LoggerFactory.createLogger(PLUGIN_ID);
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there is a problem.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * @return The logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse
     * .jface.resource.ImageRegistry)
     */
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] images = ImageConstants.IMAGES;

        for (int x = 0; x < images.length; x++) {
            reg.put(images[x], getImageDescriptor(images[x]));
        }
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
