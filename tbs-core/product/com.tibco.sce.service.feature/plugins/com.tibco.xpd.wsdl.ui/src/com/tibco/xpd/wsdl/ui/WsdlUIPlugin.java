/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.wsdl.Activator;

/**
 * The activator class controls the plug-in life cycle.
 */
public class WsdlUIPlugin extends AbstractUIPlugin {

    /** 
    */
    public static final String TIBEX_PROCESS =
            com.tibco.xpd.wsdl.Activator.TIBEX + ":process"; //$NON-NLS-1$

    /**
     * 
     */
    public static final String TIBEX_CONCRETE_FLAG =
            Activator.TIBEX + ":Concrete"; //$NON-NLS-1$

    /**
     * The Service task tag inserted into the WSDL Definition, so that it
     * identifies this WSDL as one generated from the Service task whose Id is
     * mentioned as its value
     */
    public static final String TIBEX_SERVICE_TASK =
            Activator.TIBEX + ":ServiceTask"; //$NON-NLS-1$

    public static final String TIBEX_XPDL_PLACEHOLDER =
            Activator.TIBEX + ":XPDL"; //$NON-NLS-1$

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.wsdl.ui"; //$NON-NLS-1$

    /**
     * WSDL Special folder kind.
     */
    public static final String WSDL_SPECIALFOLDER_KIND =
            Activator.WSDL_SPECIALFOLDER_KIND;

    /**
     * WSDL File extension
     */
    public static final String WSDL_FILE_EXTENSION =
            Activator.WSDL_FILE_EXTENSION;

    /**
     * XSD File extension
     */
    public static final String XSD_FILE_EXTENSION =
            Activator.XSD_FILE_EXTENSION;

    public static final String WSDL_VALIDATION_NAMESPACE_IGNORE_PREF =
            PLUGIN_ID + ".ignoreNamespacesForValidation"; //$NON-NLS-1$

    /** The shared instance. */
    private static WsdlUIPlugin plugin;

    /** The image cache. */
    private ImageCache imageCache;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor.
     */
    public WsdlUIPlugin() {
        plugin = this;
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem starting the plugin.
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
     *             if there was a problem stopping the plugin.
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        imageCache.dispose();
        plugin = null;
        super.stop(context);
    }

    /**
     * @return The image cache.
     */
    public ImageCache getImageCache() {
        return imageCache;
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static WsdlUIPlugin getDefault() {
        return plugin;
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
