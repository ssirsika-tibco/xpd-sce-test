package com.tibco.n2.ut.resources.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class N2UTResourcesPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.n2.ut.resources"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    // The shared instance
    private static N2UTResourcesPlugin plugin;

    public static final String IMG_TASKUSER_ICON = "icons/task_user_16.png"; //$NON-NLS-1$

    public static final String WORK_ITEM_ATTRIBUTES_GROUP_ICON =
            "icons/WorkItemAttibutesGroup.png"; //$NON-NLS-1$

    /**
     * Images loaded by plug-in and served by registry.
     * */
    public static final String[] IMAGES = new String[] { IMG_TASKUSER_ICON,
            WORK_ITEM_ATTRIBUTES_GROUP_ICON, "icons/WIAttributeDateTime.png",
            "icons/WIAttributeFloat.png", "icons/WIAttributeInt.png",
            "icons/WIAttributeString.png" };

    /**
     * The constructor
     */
    public N2UTResourcesPlugin() {
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
     * Returns the shared instance.
     * 
     * @return the shared instance.
     */
    public static N2UTResourcesPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns logger for the plug-in.
     * 
     * @return plug-in's logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     * 
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }
}
