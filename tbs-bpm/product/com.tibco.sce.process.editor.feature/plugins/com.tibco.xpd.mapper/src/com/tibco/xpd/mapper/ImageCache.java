/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author nwilson
 */
public class ImageCache {
    /** The error image location. */
    public static final String ERROR = "icons/error.gif"; //$NON-NLS-1$

    /** The warning image location. */
    public static final String WARNING = "icons/warning.gif"; //$NON-NLS-1$

    /** The close image location. */
    public static final String CLOSE = "icons/close.gif"; //$NON-NLS-1$

    /** The up image location. */
    public static final String UP = "icons/up.gif"; //$NON-NLS-1$

    /** The down image location. */
    public static final String DOWN = "icons/down.gif"; //$NON-NLS-1$

    /** The down image location. */
    public static final String REMOVE = "icons/remove.gif"; //$NON-NLS-1$

    /** Map of image names to images. */
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
                ImageDescriptor id = MapperActivator.imageDescriptorFromPlugin(
                        MapperActivator.PLUGIN_ID, path);
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
        for (Iterator<Image> i = images.values().iterator(); i.hasNext();) {
            Image image = (Image) i.next();
            image.dispose();
            i.remove();
        }
        images = null;
    }
}
