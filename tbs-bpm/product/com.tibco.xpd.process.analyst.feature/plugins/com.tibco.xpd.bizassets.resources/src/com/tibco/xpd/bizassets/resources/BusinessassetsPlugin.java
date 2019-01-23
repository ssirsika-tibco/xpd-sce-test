package com.tibco.xpd.bizassets.resources;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class BusinessassetsPlugin extends AbstractUIPlugin {

    public static final String ID = "com.tibco.xpd.bizassets.resources"; //$NON-NLS-1$

    // The shared instance.
    private static BusinessassetsPlugin plugin;

    /**
     * The constructor.
     */
    public BusinessassetsPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * This method is called when the plug-in is stopped
     */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static BusinessassetsPlugin getDefault() {
        return plugin;
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
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = BusinessAssetsConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }
}
