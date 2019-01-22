/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author nwilson
 */
public class ImageCache {
    /** The WSDL file image location. */
    public static final String FILE = "icons/wsdl_file_obj.gif"; //$NON-NLS-1$

    /** The WSDL service image location. */
    public static final String SERVICE = "icons/service_obj.gif"; //$NON-NLS-1$

    /** The WSDL port image location. */
    public static final String PORT = "icons/port_obj.gif"; //$NON-NLS-1$

    /** The WSDL port type image location. */
    public static final String PORT_TYPE = "icons/porttype_obj.gif"; //$NON-NLS-1$

    /** The WSDL operation image location. */
    public static final String OPERATION = "icons/operation_obj.gif"; //$NON-NLS-1$

    /** The WSDL Wizard image location. */
    public static final String WSDL_WIZ = "icons/new_wsdl_wiz.png"; //$NON-NLS-1$

    /** The WSDL operation binding image location. */
    public static final String OPERATION_BINDING =
            "icons/operationbinding_obj.gif"; //$NON-NLS-1$

    public static final String FAULT = "icons/fault_obj.gif"; //$NON-NLS-1$

    /**  */
    private HashMap<String, Image> images = new HashMap<String, Image>();

    /**
     * @param path
     *            The path of the image to load.
     * @return The image, or null if it could not be found.
     */
    public synchronized Image getImage(String path) {
        Image image = null;
        if (images != null) {
            image = (Image) images.get(path);
            if (image == null) {
                ImageDescriptor id =
                        WsdlUIPlugin
                                .imageDescriptorFromPlugin(WsdlUIPlugin.PLUGIN_ID,
                                        path);
                image = id.createImage();
                images.put(path, image);
            }
        }
        return image;
    }

    /**
     * Disposes of any cached images.
     */
    @SuppressWarnings("unchecked")
    public synchronized void dispose() {
        for (Iterator i = images.values().iterator(); i.hasNext();) {
            Image image = (Image) i.next();
            image.dispose();
        }
        images.clear();
    }
}
