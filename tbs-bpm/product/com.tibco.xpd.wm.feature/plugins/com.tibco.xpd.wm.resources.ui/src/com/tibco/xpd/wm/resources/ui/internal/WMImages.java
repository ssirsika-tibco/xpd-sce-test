/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.wm.resources.ui.internal;

/**
 * Shared images and image descriptors which can be obtained from plug-in image
 * registry.
 * 
 * For example,
 * 
 * <pre>
 *     Image i = Activator.getDefault().getImageRegistry().
 *              get(WMImages.IMAGE_CONSTANT);
 *     [or]
 *      
 *     ImageDescriptor id = Activator.getDefault().getImageRegistry().
 *              getDescriptor(WMImages.IMAGE_CONSTANT);
 * </pre>
 * <p>
 * <i>Created: 21 August 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class WMImages {

    private WMImages() {
    }

    /** Presentation icon. */
    public static final String IMG_PRESENTATION_ICON = "icons/obj16/Presentation.gif"; //$NON-NLS-1$

    /**
     * Images loaded by plug-in and served by registry.
     * 
     * @see DeployUIActivator#initializeImageRegistry(org.eclipse.jface.resource.ImageRegistry)
     */
    public static final String[] IMAGES = new String[] { IMG_PRESENTATION_ICON };

}
