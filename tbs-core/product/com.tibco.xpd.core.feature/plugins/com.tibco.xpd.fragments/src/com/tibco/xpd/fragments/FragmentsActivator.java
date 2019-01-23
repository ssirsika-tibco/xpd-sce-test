/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.fragments.internal.FragmentConsts;
import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * The activator class controls the plug-in life cycle
 */
public class FragmentsActivator extends AbstractUIPlugin {
    public static final Object FRAGMENTS_INITIALISE_JOB_FAMILY = new Object();

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.fragments"; //$NON-NLS-1$

    public static final String VIEW_ID = "com.tibco.xpd.fragments.view"; //$NON-NLS-1$

    // The shared instance
    private static FragmentsActivator plugin;

    /** logger instance. */
    private Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    /**
     * The constructor
     */
    public FragmentsActivator() {
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
     * Get the root fragment category for the fragment provider with the given
     * id.
     * 
     * @param providerId
     *            provider id
     * @return fragment root category if found, <code>null</code> otherwise.
     * @throws CoreException
     */
    public IFragmentCategory getRootCategory(String providerId)
            throws CoreException {
        return FragmentsManager.getInstance().getRootCategory(providerId);
    }

    /**
     * 
     * Get the provider bindings associated with the given editor id.
     * 
     * @param editorId
     * 
     * @return provider bindings, or <code>null</code> if not found.
     * 
     * @since 3.3
     */
    public Collection<ProviderBinding> getProviderBindings(String editorId) {
        if (editorId != null) {
            return FragmentsManager.getInstance().getExtensionHelper()
                    .getProviderBindings(editorId);
        }

        return null;
    }

    /**
     * Get the fragments view's label provider.
     * 
     * @return fragments view's {@link ILabelProvider label provider}.
     */
    public ILabelProvider getFragmentsLabelProvider() {
        return FragmentsManager.getInstance().getFragmentsLabelProvider();
    }

    /**
     * Access to eclipse log.
     * 
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static FragmentsActivator getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {
        for (String img : FragmentConsts.IMAGES) {
            ImageDescriptor desc = imageDescriptorFromPlugin(PLUGIN_ID, img);

            if (desc != null) {
                reg.put(img, desc);
            }
        }
    }

}
