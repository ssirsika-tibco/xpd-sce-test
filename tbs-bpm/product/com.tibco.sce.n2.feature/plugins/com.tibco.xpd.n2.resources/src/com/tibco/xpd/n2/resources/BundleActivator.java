/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.resources;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * @author kupadhya
 * 
 */
public class BundleActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.n2.resources"; //$NON-NLS-1$

    /*
     * Any field whose value starts with "IMG_" or "ICON_" will be loaded into
     * image registry.
     */
    public static final String ICON_MIGRATION_ANNOTATION =
            "icons/migrationAnnotationIcon.png"; //$NON-NLS-1$

    public static final String ICON_MIGRATION_HIGHLIGHTER_MENU =
            "icons/migrationAnnotationMenuIcon.png"; //$NON-NLS-1$

    public static final String ICON_MIGRATION_ANNOTATION_FADEDOWN =
            "icons/migrationAnnotationFadeDown.png"; //$NON-NLS-1$

    public static final String ICON_MIGRATION_ANNOTATION_FADEUP =
            "icons/migrationAnnotationFadeUp.png"; //$NON-NLS-1$

    // The shared instance
    private static BundleActivator plugin;

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);// new

    /**
     * The constructor
     */
    public BundleActivator() {
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
    public static BundleActivator getDefault() {
        return plugin;
    }

    public Logger getLogger() {
        return logger;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        Field[] fields = this.getClass().getFields();

        for (Field field : fields) {
            String name = field.getName();

            if (name.startsWith("IMG_") || name.startsWith("ICON_")) { //$NON-NLS-1$ //$NON-NLS-2$
                if (field.getType() == String.class
                        && Modifier.isStatic(field.getModifiers())) {
                    String imagePath;
                    try {
                        imagePath = (String) field.get(this);
                        reg.put(imagePath, getImageDescriptor(imagePath));
                    } catch (Exception e) {
                    }
                }

            }
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

    /**
     * @param path
     * @return return image from given path (taken from
     *         {@link QuickFixToolTipConstants})
     */
    public static Image getImage(String path) {
        return getDefault().getImageRegistry().get(path);
    }
}
