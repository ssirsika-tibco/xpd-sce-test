/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.transform.de;

import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class TransformDEActivator extends AbstractUIPlugin {
    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.om.transform.de"; //$NON-NLS-1$

    /**
     * Name attribute used to store the name of the component when generating
     * the DE file. This will be added to the IFiles persisted attributes.
     */
    public static final QualifiedName MODEL_NAME = new QualifiedName(PLUGIN_ID,
            "name"); //$NON-NLS-1$
    /**
     * Version attribute used to store the version of the component when
     * generating the DE file. This will be added to the IFiles persisted
     * attributes.
     */
    public static final QualifiedName MODEL_VERSION = new QualifiedName(
            PLUGIN_ID, "version"); //$NON-NLS-1$

    // The shared instance
    private static TransformDEActivator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public TransformDEActivator() {
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
    public static TransformDEActivator getDefault() {
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
