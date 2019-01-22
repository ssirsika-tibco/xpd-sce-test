package com.tibco.xpd.implementer.nativeservices.decisions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the Decisions Native Serivces plug-in life cycle
 * 
 * @author mtorres
 * @since 3.6
 */
public class NativeServicesDecisionPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID =
            "com.tibco.xpd.implementer.nativeservices.decisions"; //$NON-NLS-1$

    private Logger logger;

    // The shared instance
    private static NativeServicesDecisionPlugin plugin;

    /**
     * The constructor
     */
    public NativeServicesDecisionPlugin() {
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
    public static NativeServicesDecisionPlugin getDefault() {
        return plugin;
    }

    public synchronized Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse
     * .jface.resource.ImageRegistry)
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (int x = 0; x < IMAGES.length; x++) {
            reg.put(IMAGES[x], getImageDescriptor(IMAGES[x]));
        }
    }

    public static final String IMG_DECISIONFLOW = "icons/DecisionFlow.png"; //$NON-NLS-1$

    private static String[] IMAGES = { IMG_DECISIONFLOW };
}
