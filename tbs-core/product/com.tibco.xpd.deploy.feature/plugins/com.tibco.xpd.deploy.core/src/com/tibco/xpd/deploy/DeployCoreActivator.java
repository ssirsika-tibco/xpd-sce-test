/*
 * Copyright (c) TIBCO Software Inc 2004, 2006.  All rights reserved.
 */
package com.tibco.xpd.deploy;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * Activator class for supporting plug-in lifecycle.
 * 
 * @author Jan Arciuchiewicz
 */
public class DeployCoreActivator extends Plugin {

    /** The plug-in ID */
    public static final String PLUGIN_ID = "com.tibco.xpd.deploy.core";

    /** The shared instance */
    private static DeployCoreActivator plugin;

    /**
     * The constructor
     */
    public DeployCoreActivator() {
        plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
    public static DeployCoreActivator getDefault() {
        return plugin;
    }

}
