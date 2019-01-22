/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal;

/**
 * Constant values used in the fragments plugin.
 * 
 * @author njpatel
 * 
 */
public final class FragmentConsts {

    /**
     * Fragments properties file.
     */
    public static final String FRAGMENTS_PROPERTIES = "fragments.properties"; //$NON-NLS-1$

    /**
     * Fragments file extension.
     */
    public static final String FRAGMENT_FILE_EXT = "frg"; //$NON-NLS-1$

    // Fragment property keys...
    /** Fragment properties key to store the fragment element KEY */
    public static final String KEY = "key"; //$NON-NLS-1$
    /** Fragment properties key to store the fragment element NAME */
    public static final String NAME = "name"; //$NON-NLS-1$
    /** Fragment properties key to store the fragment element DESCRIPTION */
    public static final String DESCRIPTION = "description"; //$NON-NLS-1$
    /** Fragment properties key to store the fragment element SYSTEM status */
    public static final String SYSTEM = "system"; //$NON-NLS-1$

    // IMAGES
    public static final String IMG_CATEGORY = "icons/folder.png"; //$NON-NLS-1$
    public static final String IMG_FRAGMENT = "icons/fragment.png"; //$NON-NLS-1$
    public static final String IMG_LOCK_OVR = "icons/lock_ovr.png"; //$NON-NLS-1$
    public static final String IMG_HORIZ_LAYOUT = "icons/th_horizontal.gif"; //$NON-NLS-1$
    public static final String IMG_VERT_LAYOUT = "icons/th_vertical.gif"; //$NON-NLS-1$
    public static final String IMG_NO_FRAGMENT = "icons/fragmentNoImage.png"; //$NON-NLS-1$

    public static final String IMG_ZOOMIN_ICON = "icons/zoom_in.png"; //$NON-NLS-1$
    public static final String IMG_ZOOMOUT_ICON = "icons/zoom_out.png"; //$NON-NLS-1$

    public static final String[] IMAGES = new String[] { IMG_CATEGORY,
            IMG_FRAGMENT, IMG_LOCK_OVR, IMG_HORIZ_LAYOUT, IMG_VERT_LAYOUT,
            IMG_ZOOMIN_ICON, IMG_ZOOMOUT_ICON };
}
