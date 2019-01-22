package com.tibco.xpd.implementer.nativeservices.java;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class JavaActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.implementer.nativeservices.java"; //$NON-NLS-1$

    // The shared instance
    private static JavaActivator plugin;
    
    /** logger. */
    private Logger log;

    /**
     * The constructor
     */
    public JavaActivator() {
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);

        log = LoggerFactory.createLogger(PLUGIN_ID);
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

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] images = JavaConstants.IMAGES;

        for (String img : images) {
            reg.put(img, getImageDescriptor(img));
        }
    }

    /*
     * (non-Javadoc)
     * 
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
    public static JavaActivator getDefault() {
        return plugin;
    }
    
    
    /**
     * Logger for this plugin.
     * 
     * @return the log
     */
    public Logger getLogger() {
        return log;
    }

}
