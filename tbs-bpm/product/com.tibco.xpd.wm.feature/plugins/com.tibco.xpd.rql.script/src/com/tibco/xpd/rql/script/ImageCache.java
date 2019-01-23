/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rql.script;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * @author Miguel Torres
 */
public class ImageCache {

    public static final String RQL_IMAGE = "icons/full/obj16/rql.gif"; //$NON-NLS-1$

    public static final String RQL_INTERSECT_IMAGE = "icons/full/obj16/rql-intersect16-n-p.png"; //$NON-NLS-1$

    public static final String RQL_NOT_IMAGE = "icons/full/obj16/rql-not-16-n-p.png"; //$NON-NLS-1$

    public static final String RQL_UNION_IMAGE = "icons/full/obj16/rql-union-16-n-p.png"; //$NON-NLS-1$

    /** Map of cached images. */
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
                        RQLScriptPlugin
                                .imageDescriptorFromPlugin(RQLScriptPlugin.PLUGIN_ID,
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
    public synchronized void dispose() {
        for (Iterator i = images.values().iterator(); i.hasNext();) {
            Image image = (Image) i.next();
            image.dispose();
            i.remove();
        }
        images = null;
    }
}
