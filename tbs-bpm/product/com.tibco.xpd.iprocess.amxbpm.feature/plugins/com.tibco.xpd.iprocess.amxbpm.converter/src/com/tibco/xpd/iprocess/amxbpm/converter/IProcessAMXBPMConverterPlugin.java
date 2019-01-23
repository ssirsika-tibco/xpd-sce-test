package com.tibco.xpd.iprocess.amxbpm.converter;

import java.net.URL;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * 
 * iProcess to AMX BPM Converter Plugin.
 * 
 * @author aprasad
 * @since 09-Apr-2014
 */
public class IProcessAMXBPMConverterPlugin extends Plugin {

    /** The plug-in ID */
    public static final String PLUGIN_ID =
            "com.tibco.xpd.iprocess.amxbpm.converter"; //$NON-NLS-1$

    /** The import transformation xslt */
    private static final String IPM_TO_IPS_XSLT =
            "/xslts/imports/iPM_2_iPS_xpdl.xslt"; //$NON-NLS-1$

    /** The shared instance */
    private static IProcessAMXBPMConverterPlugin plugin;

    /** logger. */
    private Logger logger;

    // XPD-6166
    /**
     * System dependent Line separator
     */
    public static String SYSTEM_LINE_SEPARATOR = System
            .getProperty("line.separator"); //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        super.start(bundleContext);
        plugin = this;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        plugin = null;
        super.stop(bundleContext);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static IProcessAMXBPMConverterPlugin getDefault() {
        return plugin;
    }

    /**
     * Logger for this plug-in.
     * 
     * @return the log
     */
    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.createLogger(PLUGIN_ID);
        }
        return logger;
    }

    /**
     * Returns URLs of the transformation stylesheets from IProcess Manager to
     * IProcess Server.
     * 
     * @return the URLs for the transformation XSLTs.
     */
    public URL[] getXslts() {
        return new URL[] { getIpmToIpsXsltUrl() };
    }

    /**
     * Returns URL of the transformation stylesheet from IProcess Manager to
     * IProcess Server.
     * 
     * @return URL of the transformation stylesheet from IProcess Manager to
     *         IProcess Server.
     */
    public URL getIpmToIpsXsltUrl() {
        return getClass().getResource(IPM_TO_IPS_XSLT);
    }
}
