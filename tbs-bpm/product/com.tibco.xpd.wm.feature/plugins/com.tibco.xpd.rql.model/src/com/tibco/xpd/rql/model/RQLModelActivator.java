/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.model;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLModelActivator extends Plugin {
    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rql.model"; //$NON-NLS-1$

    // The shared instance
    private static RQLModelActivator plugin;

    /**
     * The constructor
     */
    public RQLModelActivator() {
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
    public static RQLModelActivator getDefault() {
        return plugin;
    }
}
