/**
 */
package com.tibco.xpd.rsd.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.rsd.provider.RsdItemProviderAdapterFactory;

/**
 * This is the central singleton for the Rsd editor plugin.
 * 
 * @generated NOT
 */
public final class RsdUIPlugin extends EMFPlugin {

    public static final String PLUGIN_ID = "com.tibco.xpd.rsd.ui"; //$NON-NLS-1$

    /**
     * File extension.
     */
    public static final String RSD_EXTENSION = "rsd"; //$NON-NLS-1$

    /**
     * Delay (in milliseconds) used for text field changes to be propagated to
     * the model.
     */
    public static int OBSERVED_TEXT_DELAY = 400;

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    public static final RsdUIPlugin INSTANCE = new RsdUIPlugin();

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    private static Implementation plugin;

    /**
     * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public RsdUIPlugin() {
        super(new ResourceLocator[] {});
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the singleton instance.
     * @generated
     */
    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the singleton instance.
     * @generated
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    public static Logger getLogger() {
        return getPlugin().logger;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public static class Implementation extends EclipseUIPlugin {

        private Logger logger = LoggerFactory
                .createLogger(RsdUIPlugin.PLUGIN_ID);

        private ComposedAdapterFactory adapterFactory;

        /**
         * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }

        /**
         * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
         * 
         * @param context
         * @throws Exception
         */
        @Override
        public void start(BundleContext context) throws Exception {
            super.start(context);
            adapterFactory = createAdapterFactory();

        }

        /**
         * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
         * 
         * @param context
         * @throws Exception
         */
        @Override
        public void stop(BundleContext context) throws Exception {
            if (adapterFactory != null) {
                adapterFactory.dispose();
                adapterFactory = null;
            }
            super.stop(context);
        }

        protected ComposedAdapterFactory createAdapterFactory() {
            List<AdapterFactory> factories = new ArrayList<>();
            fillItemProviderFactories(factories);
            return new ComposedAdapterFactory(factories);
        }

        protected void fillItemProviderFactories(List<AdapterFactory> factories) {
            factories.add(new RsdItemProviderAdapterFactory());
            factories.add(new ResourceItemProviderAdapterFactory());
            factories.add(new ReflectiveItemProviderAdapterFactory());
        }

        public AdapterFactory getItemProvidersAdapterFactory() {
            return adapterFactory;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void initializeImageRegistry(ImageRegistry reg) {
            for (RsdImage image : RsdImage.values()) {
                reg.put(image.getPath(),
                        imageDescriptorFromPlugin(PLUGIN_ID, image.getPath()));
            }
        }

        /**
         * @param key
         *            The image key.
         * @return The image, or null if not found.
         * 
         * @see RsdImage#getImage(RsdImage)
         */
        /* package */Image getImage(RsdImage key) {
            return getImageRegistry().get(key.getPath());
        }

        /**
         * 
         * @param key
         *            The image key.
         * @return the image descriptor, or null if not found.
         * 
         * @see RsdImage#getImageDescriptor(RsdImage)
         */
        /* package */ImageDescriptor getImageDescriptor(RsdImage key) {
            return getImageRegistry().getDescriptor(key.getPath());
        }
    }

}
