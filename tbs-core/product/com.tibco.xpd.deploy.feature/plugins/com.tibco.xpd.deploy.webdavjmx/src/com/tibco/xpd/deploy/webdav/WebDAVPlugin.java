/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * Bundle activator for the WebDAV plug-in.
 * 
 * @author TIBCO Software, Inc.
 */
public class WebDAVPlugin extends AbstractUIPlugin {// AbstractFormUIPlugin {

    /** Special folder kind for WebDAV deployable resources. */
    public static final String WEBDAV_DEPLOYABLE_KIND = "webdavDeployable"; //$NON-NLS-1$

    public static final String PLUGIN_ID = "com.tibco.xpd.deploy.webdavjmx"; //$NON-NLS-1$

    /** The shared instance. */
    private static WebDAVPlugin plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public WebDAVPlugin() {
        plugin = this;
    }

    /**
     * Returns the shared singleton instance.
     * 
     * @return the shared instance.
     */
    public static WebDAVPlugin getDefault() {
        return plugin;
    }

    /** {@inheritDoc} */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /** {@inheritDoc} */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
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
     * Returns logger for the plug-in.
     * 
     * @return the plug-in logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns a formatted message with parameter replacements.
     * 
     * @param msg
     *            The message string.
     * @param args
     *            The message arguments. Length must match the number of
     *            distinct parameter markers in <code>msg</code>.
     * @return Formatted message with parameter markers in <code>msg</code>
     *         replaced by <code>args</code> values.
     * @deprecated {@link String#format(String, Object...)} should be used
     *             instead.
     */
    @Deprecated
    public final String format(String msg, Object... args) {
        return args.length > 0 ? NLS.bind(msg, args) : msg;
    }
}
