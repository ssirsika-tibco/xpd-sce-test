/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.nimbus;

import java.lang.reflect.Field;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * TIBCO Business Studio / Nimbus Control integration plugin.
 * 
 * 
 * @author aallway
 * @since 17 Oct 2012
 */
public class XpdNimbusPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "com.tibco.xpd.nimbus"; //$NON-NLS-1$

    private final Logger logger = LoggerFactory.createLogger(PLUGIN_ID);

    // The shared instance
    private static XpdNimbusPlugin plugin;

    /**
     * The constructor
     */
    public XpdNimbusPlugin() {
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
    public static XpdNimbusPlugin getDefault() {
        return plugin;
    }

    @Override
    protected void initializeImageRegistry(ImageRegistry reg) {

        XpdNimbusImages constClass = new XpdNimbusImages();

        Field[] fields = XpdNimbusImages.class.getFields();
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
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }
}
