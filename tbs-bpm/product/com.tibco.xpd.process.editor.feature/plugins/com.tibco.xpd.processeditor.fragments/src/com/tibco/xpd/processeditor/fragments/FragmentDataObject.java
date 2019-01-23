/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.fragments;

import org.eclipse.swt.graphics.ImageData;

/**
 * @author rsomayaj
 * 
 */
public class FragmentDataObject {

    private String fragmentData;

    private ImageData fragmentImageData;
    
    public FragmentDataObject() {
    }
    
    public FragmentDataObject(String fragmentData, ImageData imgData){
        this.fragmentData = fragmentData;
        fragmentImageData = imgData;
        
    }

    public String getFragmentData() {
        return fragmentData;
    }

    public void setFragmentData(String fragmentData) {
        this.fragmentData = fragmentData;
    }

    public ImageData getFragmentImageData() {
        return fragmentImageData;
    }

    public void setFragmentImageData(ImageData fragmentImage) {
        this.fragmentImageData = fragmentImage;
    }

}
