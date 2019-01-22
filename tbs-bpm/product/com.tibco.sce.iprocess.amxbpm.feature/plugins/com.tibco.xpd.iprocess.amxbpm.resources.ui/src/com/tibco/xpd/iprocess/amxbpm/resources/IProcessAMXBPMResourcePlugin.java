package com.tibco.xpd.iprocess.amxbpm.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class IProcessAMXBPMResourcePlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.iprocess.amxbpm.resources.ui"; //$NON-NLS-1$

    public static final String IMG_ERROR_MARKER = "icons/error.png"; //$NON-NLS-1$

    public static final String IMG_WARNING_MARKER = "icons/warning.gif"; //$NON-NLS-1$

    public static final String IMG_INFO_MARKER = "icons/tick.gif"; //$NON-NLS-1$

    /** logger. */
    private Logger log;

    // The shared instance
    private static IProcessAMXBPMResourcePlugin plugin;

    /**
     * The constructor
     */
    public IProcessAMXBPMResourcePlugin() {
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
        log = LoggerFactory.createLogger(PLUGIN_ID);
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
    public static IProcessAMXBPMResourcePlugin getDefault() {
        return plugin;
    }

    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * Logger for this plugin.
     * 
     * @return the log
     */
    public Logger getLogger() {
        return log;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        /* XPD-6288: Initialise Image registry */
        reg.put(IMG_ERROR_MARKER, ImageDescriptor.createFromURL(getBundle()
                .getEntry(IMG_ERROR_MARKER)));

        reg.put(IMG_WARNING_MARKER, ImageDescriptor.createFromURL(getBundle()
                .getEntry(IMG_WARNING_MARKER)));

        reg.put(IMG_INFO_MARKER, ImageDescriptor.createFromURL(getBundle()
                .getEntry(IMG_INFO_MARKER)));

    }
}
