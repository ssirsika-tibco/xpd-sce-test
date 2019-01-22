/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class SimulationAnnotationPlugin extends AbstractUIPlugin {

    /** The shared instance. */
    private static SimulationAnnotationPlugin plugin;

    /**
     * The constructor.
     */
    public SimulationAnnotationPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation.
     * 
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem starting the bundle.
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped.
     * 
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem stopping the bundle.
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * 
     * @return The default plugin instance.
     */
    public static SimulationAnnotationPlugin getDefault() {
        return plugin;
    }

}
