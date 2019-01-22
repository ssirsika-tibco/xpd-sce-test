package com.tibco.xpd.om.resources;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class OMResourcesActivator extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.om.resources"; //$NON-NLS-1$

    // the ID of the OM resource indexer
    public static final String OM_INDEXER_ID = "com.tibco.xpd.om.resources.indexing.omIndexer"; //$NON-NLS-1$

    // OM file extension
    public static final String OM_FILE_EXTENSION = OMUtil.OM_FILE_EXTENSION;
    
    // Special folder kind
    public static final String OM_SPECIAL_FOLDER_KIND = OMUtil.OM_SPECIAL_FOLDER_KIND;

    // The shared instance
    private static OMResourcesActivator plugin;
    
    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * Parameter key that can be used to set the default values of an element
     * being created using the <code>CreateElementRequest</code>.
     */
    public static final class CREATE_ELEMENT_REQUEST_PARAM {

        /**
         * Set default name of the element being created using the
         * <code>CreateElementRequest</code>.
         */
        public static final String NAME = "default_name"; //$NON-NLS-1$
    }

    /**
     * The constructor
     */
    public OMResourcesActivator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
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
     * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
    public static OMResourcesActivator getDefault() {
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
