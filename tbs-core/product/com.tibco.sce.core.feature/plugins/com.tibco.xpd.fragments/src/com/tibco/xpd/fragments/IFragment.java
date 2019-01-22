/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * A fragment type.
 * 
 * @see IFragmentCategory
 * 
 * @author njpatel
 */
public interface IFragment extends IFragmentElement, IContainedFragmentElement {

    /**
     * Get fragment data (serialized model).
     * 
     * @see #getLocalizedData()
     * 
     * @return fragment data.
     */
    String getData();

    /**
     * Get the image data of this fragment.
     * <p>
     * <b>NOTE:</b> Use {@link #createImage(Device) createImage} to create an
     * image of this fragment. This will load the image more efficiently.
     * </p>
     * 
     * @see #getLocalizedImageData()
     * 
     * @return image data, <code>null</code> if failed to get the image data.
     */
    ImageData getImageData();
    
    /**
     * Get the localized fragment data (serialized model).
     * 
     * @return fragment data.
     */
    String getLocalizedData();

    /**
     * Get the localized image data of this fragment.
     * <p>
     * <b>NOTE:</b> Use {@link #createImage(Device) createImage} to create an
     * image of this fragment. This will load the image more efficiently.
     * </p>
     * 
     * @return image data, <code>null</code> if failed to get the image data.
     */
    ImageData getLocalizedImageData();

    /**
     * Create a localized image of this fragment. If this fragment has no image
     * then a default image will be returned.
     * <p>
     * <b>NOTE:</b> The caller is responsible for disposing this image.
     * </p>
     * 
     * @param device
     *            the device on which to create the image
     * @return fragment image.
     */
    Image createImage(Device device);
  
}
