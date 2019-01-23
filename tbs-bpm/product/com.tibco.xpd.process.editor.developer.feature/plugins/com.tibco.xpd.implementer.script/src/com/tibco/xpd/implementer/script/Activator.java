/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * @author nwilson
 */
public class Activator extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.implementer.script"; //$NON-NLS-1$

    public static final String DEBUG = "debug"; //$NON-NLS-1$

    /** The shared instance. */
    private static Activator plugin;

    /** The image cache. */
    private ImageCache imageCache;

    private Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * 
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
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        imageCache = new ImageCache();
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there is a problem.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        imageCache.dispose();
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
     * @return The image cache.
     */
    public ImageCache getImageCache() {
        return imageCache;
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

}
