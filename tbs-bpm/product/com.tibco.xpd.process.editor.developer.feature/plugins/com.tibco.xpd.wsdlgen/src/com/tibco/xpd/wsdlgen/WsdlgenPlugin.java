package com.tibco.xpd.wsdlgen;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class WsdlgenPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.wsdlgen"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    public static final String GENERATED_SERVICES_FOLDER =
            Messages.WsdlGenBuilder_GeneratedServicesFolderName;

    /**
     * 
     */
    public static final String INTERNAL_JMX_DEBUG = "InternalJmxDebug";

    /**
     * Extended attribute set on a Package to indicate that port types should be
     * generated for process interfaces.
     */
    public static final String ENABLE_WSDLGEN_ON_PROCINTERFACE =
            "EnableWsdlGenForProcInterface";

    /**
     * 
     */
    public static final String BUILDER_ID = "com.tibco.xpd.wsdlgen.wsdlGen"; //$NON-NLS-1$

    // The shared instance
    private static WsdlgenPlugin plugin;

    /**
     * The constructor
     */
    public WsdlgenPlugin() {
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
    public static WsdlgenPlugin getDefault() {
        return plugin;
    }

    public Logger getLogger() {
        return logger;
    }

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = WSDLImageConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
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
