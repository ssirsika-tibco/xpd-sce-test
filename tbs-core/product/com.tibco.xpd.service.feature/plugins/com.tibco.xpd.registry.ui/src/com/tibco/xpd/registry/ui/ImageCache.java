/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author nwilson
 */
public class ImageCache {
    /** The ADD image. */
    public static final String ADD = "icons/add.gif"; //$NON-NLS-1$
    /** The REMOVE image. */
    public static final String REMOVE = "icons/remove.gif"; //$NON-NLS-1$
    /** The CLOSE image. */
    public static final String CLOSE = "icons/close.gif"; //$NON-NLS-1$
    /** The BACK image. */
    public static final String BACK = "icons/back.gif"; //$NON-NLS-1$
    /** The SEARCH image. */
    public static final String SEARCH = "icons/search.gif"; //$NON-NLS-1$
    /** The REFRESH image. */
    public static final String REFRESH = "icons/refresh.gif"; //$NON-NLS-1$
    /** The IMPORT image. */
    public static final String IMPORT = "icons/import.gif"; //$NON-NLS-1$
    /** The REGISTRY image. */
    public static final String REGISTRY = "icons/registry.gif"; //$NON-NLS-1$
    /** The ORGANIZATION image. */
    public static final String ORGANIZATION = "icons/organization.gif"; //$NON-NLS-1$
    /** The SERVICE image. */
    public static final String SERVICE = "icons/service.gif"; //$NON-NLS-1$
    /** The ADD_SEARCH image. */
    public static final String ADD_SEARCH = "icons/add_search.gif"; //$NON-NLS-1$
    /** The ADD_REGISTRY image. */
    public static final String ADD_REGISTRY = "icons/add_registry.gif"; //$NON-NLS-1$
    /** The REMOVE_SEARCH image. */
    public static final String REMOVE_SEARCH = "icons/remove_search.gif"; //$NON-NLS-1$
    /** The REMOVE_REGISTRY image. */
    public static final String REMOVE_REGISTRY =
            "icons/remove_registry.gif"; //$NON-NLS-1$

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
                ImageDescriptor id = Activator.imageDescriptorFromPlugin(
                        Activator.PLUGIN_ID, path);
                image = id.createImage();
                images.put(path, image);
            }
        }
        return image;
    }

    /**
     * Disposes of any cached images.
     */
    public synchronized void dispose() {
        for (Iterator<?> i = images.values().iterator(); i.hasNext();) {
            Image image = (Image) i.next();
            image.dispose();
            i.remove();
        }
        images = null;
    }
}
