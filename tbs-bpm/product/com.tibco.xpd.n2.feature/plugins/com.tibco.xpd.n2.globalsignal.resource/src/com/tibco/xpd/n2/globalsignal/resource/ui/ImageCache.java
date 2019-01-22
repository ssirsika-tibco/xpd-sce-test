/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;

/**
 * Image cache for Global Signal Definition objects.
 * 
 * @author sajain
 * @since Feb 3, 2015
 */
public class ImageCache {

    /** The GSD file image location. */
    public static final String FILE = "icons/obj16/gsdFile.png"; //$NON-NLS-1$

    /** The Global Signal image location. */
    public static final String GLOBALSIGNAL = "icons/obj16/globalSignal.png"; //$NON-NLS-1$

    /** The Payload Data image location. */
    public static final String PAYLOADDATA = "icons/obj16/payloadData.png"; //$NON-NLS-1$

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
            image = images.get(path);
            if (image == null) {
                ImageDescriptor id =
                        GsdResourcePlugin
                                .imageDescriptorFromPlugin(GsdResourcePlugin.PLUGIN_ID,
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
        }
        images.clear();
    }

}
