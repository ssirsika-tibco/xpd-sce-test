package com.tibco.xpd.xpdl2.resources;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Xpdl2ResourcesActivator extends Plugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID =
            "com.tibco.xpd.analyst.resources.xpdl2"; //$NON-NLS-1$

    /** name of the property on the marker that holds project name. */
    public static final String MARKER_ATT_PROJECTNAME =
            "com.tibco.xpd.xpdl2.resources.ProjectName"; //$NON-NLS-1$

    // The shared instance
    private static Xpdl2ResourcesActivator plugin;

    /**
     * The constructor
     */
    public Xpdl2ResourcesActivator() {
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
    public static Xpdl2ResourcesActivator getDefault() {
        return plugin;
    }

}
