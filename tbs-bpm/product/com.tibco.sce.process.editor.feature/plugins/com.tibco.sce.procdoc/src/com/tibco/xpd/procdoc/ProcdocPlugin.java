package com.tibco.xpd.procdoc;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The main plugin class to be used in the desktop.
 */
public class ProcdocPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.tibco.xpd.procdoc"; //$NON-NLS-1$

    // The shared instance.
    private static ProcdocPlugin plugin;

    /** logger instance. */
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor.
     */
    public ProcdocPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static ProcdocPlugin getDefault() {
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

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
}
