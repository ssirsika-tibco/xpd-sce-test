package com.tibco.xpd.rasc.ui;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class RascUiActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.rasc.ui"; //$NON-NLS-1$

    /**
     * Main image for the export wizard.
     */
    public static final String RASC_EXPORT_WIZARD_IMAGE =
            "RASC_EXPORT_WIZARD_IMAGE"; //$NON-NLS-1$

    // The shared instance
    private static RascUiActivator plugin;

    private Logger logger =
            LoggerFactory.createLogger(RascUiActivator.PLUGIN_ID);

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     *
     * @param reg
     */
    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        reg.put(RASC_EXPORT_WIZARD_IMAGE,
                imageDescriptorFromPlugin(PLUGIN_ID,
                        "icons/wizban/rasc_wiz.png")); //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
    public static RascUiActivator getDefault() {
        return plugin;
    }

    /**
     * @return The logger instance.
     */
    public static Logger getLogger() {
        return getDefault().logger;
    }

}
