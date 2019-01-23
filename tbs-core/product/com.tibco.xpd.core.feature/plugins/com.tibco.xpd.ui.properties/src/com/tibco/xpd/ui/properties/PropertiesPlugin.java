/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;

/**
 * @author wzurek
 */
public class PropertiesPlugin extends AbstractUIPlugin {

    public static final String COM_ECLIPSE_UI_VIEWS_PROPERTIES_TABBED = "org.eclipse.ui.views.properties.tabbed"; //$NON-NLS-1$

    public static final String PROPERTY_SECTIONS = "propertySections"; //$NON-NLS-1$

    public static final String INDIVIDUAL_SECTION = "propertySection"; //$NON-NLS-1$

    public static final String ID = "com.tibco.xpd.ui.properties"; //$NON-NLS-1$

    private static PropertiesPlugin instance;

    private Logger logger = LoggerFactory.createLogger(ID);

    public static PropertiesPlugin getDefault() {
        return instance;
    }

    /**
     * The Constructor
     */
    public PropertiesPlugin() {
        super();
    }

    public void start(BundleContext context) throws Exception {
        instance = this;
        super.start(context);
    }

    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        instance = null;
    }

    /**
     * This method adds all the c
     * 
     * @return
     */
    public List getPropertySectionConfig() {
        ArrayList toReturn = new ArrayList();
        IExtensionPoint ep = Platform.getExtensionRegistry().getExtensionPoint(
                COM_ECLIPSE_UI_VIEWS_PROPERTIES_TABBED, PROPERTY_SECTIONS);
        IConfigurationElement[] elements = ep.getConfigurationElements();
        for (int i = 0; i < elements.length; i++) {
            IConfigurationElement el = elements[i];
            toReturn.add(el);
        }
        return toReturn;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @param path
     *            the path
     * @return the image descriptor
     */
    private static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    protected void initializeImageRegistry(ImageRegistry reg) {
        String[] imgs = PropertiesPluginConstants.IMAGES;
        for (int i = 0; i < imgs.length; i++) {
            reg.put(imgs[i], getImageDescriptor(imgs[i]));
        }
    }

    /**
     * Access the Eclipse logger.
     * 
     * @return
     */
    public Logger getLogger() {
        return logger;
    }

}
