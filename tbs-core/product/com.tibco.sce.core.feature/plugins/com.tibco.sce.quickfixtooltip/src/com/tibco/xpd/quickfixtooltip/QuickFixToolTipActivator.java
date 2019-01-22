/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.quickfixtooltip;

import java.lang.reflect.Field;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author aallway
 * @since 3.2
 */
public class QuickFixToolTipActivator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.quickfixtooltip"; //$NON-NLS-1$

    // The shared instance
    private static QuickFixToolTipActivator plugin;

    /**
     * The constructor
     */
    public QuickFixToolTipActivator() {
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
    public static QuickFixToolTipActivator getDefault() {
        return plugin;
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

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        QuickFixToolTipConstants constClass = new QuickFixToolTipConstants();

        Field[] fields = constClass.getClass().getFields();
        if (fields != null) {
            for (Field field : fields) {
                if (field.getName().startsWith("IMG") //$NON-NLS-1$
                        || field.getName().startsWith("ICON")) { //$NON-NLS-1$
                    if (field.getType() == String.class) {
                        String imagePath;
                        try {
                            imagePath = (String) field.get(constClass);
                            reg.put(imagePath, getImageDescriptor(imagePath));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @param path
     * @return return image from given path (taken from
     *         {@link QuickFixToolTipConstants})
     */
    public Image getImage(String path) {
        return getDefault().getImageRegistry().get(path);
    }

}
