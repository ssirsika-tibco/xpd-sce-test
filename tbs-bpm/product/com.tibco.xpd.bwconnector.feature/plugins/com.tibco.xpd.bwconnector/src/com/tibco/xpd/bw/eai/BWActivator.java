package com.tibco.xpd.bw.eai;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class BWActivator extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "com.tibco.xpd.bwconnector"; //$NON-NLS-1$

    public static final String IMG_JMS_SERVER = "icons/full/obj16/Server_connected.png"; //$NON-NLS-1$
    
    /** The shared instance. */
    private static BWActivator plugin;
    
    public static final String[] IMAGES =
        new String[] {IMG_JMS_SERVER};

    public BWActivator() {
        plugin = this;
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static BWActivator getDefault() {
        return plugin;
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem starting the bundle.
     * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
     * @param context
     *            The bundle context.
     * @throws Exception
     *             if there was a problem stopping the bundle.
     * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
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

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            //reg.put(imgs[i], ImageDescriptor.createFromFile(BWActivator.class, "WebServiceBound.png"));
            
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

}
