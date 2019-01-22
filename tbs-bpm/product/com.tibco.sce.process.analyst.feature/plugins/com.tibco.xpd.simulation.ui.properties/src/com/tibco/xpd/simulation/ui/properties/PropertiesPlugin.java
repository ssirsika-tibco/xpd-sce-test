/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.ui.properties;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class PropertiesPlugin extends AbstractUIPlugin {

    /** The shared instance. */
    private static PropertiesPlugin plugin;

    /**
     * The constructor.
     */
    public PropertiesPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation.
     * @param context The bundle context.
     * @throws Exception if there was a problem starting the bundle.
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped.
     * @param context The bundle context.
     * @throws Exception if there was a problem stopping the bundle.
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     * @return The plugin insatnce.
     */
    public static PropertiesPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(
                "com.tibco.xpd.simulation.ui.properties", path); //$NON-NLS-1$
    }

    /**
     * Returns the currently active workbench window or <code>null</code> if
     * none.
     * 
     * @return the currently active workbench window or <code>null</code>
     */
    public static IWorkbenchWindow getActiveWorkbenchWindow() {
        return getDefault().getWorkbench().getActiveWorkbenchWindow();
    }

    /**
     * Returns the currently active workbench window shell or <code>null</code>
     * if none.
     * 
     * @return the currently active workbench window shell or <code>null</code>
     */
    public static Shell getShell() {
        if (getActiveWorkbenchWindow() != null) {
            return getActiveWorkbenchWindow().getShell();
        }
        return null;
    }
}
