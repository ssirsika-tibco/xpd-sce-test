/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.n2.daa;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.n2.resources.BundleActivator;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.n2.daa"; //$NON-NLS-1$

    public static final String N2PE_PLUGIN_FEATURE_FILE_EXTENSION = "jar"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    public static final Logger LOG = BundleActivator.getDefault().getLogger();

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
     * BundleContext )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
     * BundleContext )
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
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

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
     * Returns the standard display to be used. The method first checks, if the
     * thread calling this method has an associated display. If so, this display
     * is returned. Otherwise the method returns the default display.
     */
    public static Display getStandardDisplay() {
        Display display = Display.getCurrent();
        if (display == null) {
            display = Display.getDefault();
        }
        return display;
    }

    /** */
    private static final String PLATFORM_PLUGIN_RES_URI_PREFIX =
            "platform:/plugin/" + Activator.PLUGIN_ID //$NON-NLS-1$
                    + "/resources/"; //$NON-NLS-1$

    public static final String PREPARE_BEFORE_UNDEPLOY_POLCICYSET =
            "PrepareBeforeUndeploy.policysets";


}
