package com.tibco.xpd.bom.wsdltransform;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    public static final String WSDL_FILE_EXT = "wsdl"; //$NON-NLS-1$

    public static final String EXPORT_FOLDER = Messages.WSDLExportFolderLabel;

    /**
     * TIBEX WSDL extension to indicate whether BOMs will be generated from the
     * WSDL. Set to <code>false</code> if the BOM should not be generated.
     */
    public static final String TIBEX_BDSSUPPORT_ATTR =
            com.tibco.xpd.wsdl.Activator.TIBEX + ":bdsSupport"; //$NON-NLS-1$

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.bom.wsdltransform"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public Activator() {
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
    public static Activator getDefault() {
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

}
