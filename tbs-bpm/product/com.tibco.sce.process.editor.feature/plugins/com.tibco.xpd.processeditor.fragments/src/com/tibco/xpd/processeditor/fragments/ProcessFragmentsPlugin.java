/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.fragments;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author rsomayaj
 */
public class ProcessFragmentsPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.processeditor.fragments"; //$NON-NLS-1$

    // The shared instance
    private static ProcessFragmentsPlugin plugin;

    /**
     * The constructor
     */
    public ProcessFragmentsPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
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
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static ProcessFragmentsPlugin getDefault() {
        return plugin;
    }

}
