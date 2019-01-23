/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.bpm.simplified.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author jarciuch
 * @since 15 Jun 2015
 */
public class BpmSimplifiedUiActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.bpm.simplified.ui"; //$NON-NLS-1$

    // The shared instance
    private static BpmSimplifiedUiActivator plugin;

    /**
     * The constructor
     */
    public BpmSimplifiedUiActivator() {
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
    public static BpmSimplifiedUiActivator getDefault() {
        return plugin;
    }

}
