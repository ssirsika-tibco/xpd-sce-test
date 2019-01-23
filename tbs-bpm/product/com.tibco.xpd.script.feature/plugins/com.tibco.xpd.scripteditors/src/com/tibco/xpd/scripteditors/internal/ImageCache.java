/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/**
 * @author Miguel Torres
 */
public class ImageCache {

    public static final String RQL_IMAGE = "icons/full/obj16/rql.gif"; //$NON-NLS-1$

    /** Map of cached images. */
    private HashMap<String, Image> images = new HashMap<String, Image>();

    /** The attribute image location. */
    public static final String ATTRIBUTE =
            "icons/full/obj16/XSDAttributeDeclaration.gif"; //$NON-NLS-1$

    /** The element image location. */
    public static final String ELEMENT =
            "icons/full/obj16/XSDElementDeclaration.gif"; //$NON-NLS-1$

    /** The aray element image location. */
    public static final String ARRAY =
            "icons/full/obj16/XSDElementArrayDeclaration.gif"; //$NON-NLS-1$

    /** The wsdl part. */
    public static final String PART = "icons/full/obj16/wsdl_part.gif"; //$NON-NLS-1$

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
                        ScriptEditorsPlugin
                                .imageDescriptorFromPlugin(ScriptEditorsPlugin.PLUGIN_ID,
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
