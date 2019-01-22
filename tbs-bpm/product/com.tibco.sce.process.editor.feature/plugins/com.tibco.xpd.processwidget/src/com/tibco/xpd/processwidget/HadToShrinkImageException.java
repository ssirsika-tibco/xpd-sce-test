/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget;

import org.eclipse.swt.graphics.Image;

/**
 * HadToShrinkImageException
 * <p>
 * Thrown by processWidget.createProcessImage() when the image size had to be
 * shrunk in order to create the image.
 * <p>
 * Operating systems have maximum single graphic image sizes and it is quite
 * easy to overrun these.
 * <p>
 * When this happens the image IS STILL created BUT it is shrunk until it can be
 * created. The objects in the process image are scaled appropriately.
 * 
 * 
 * 
 * @author aallway
 */
public class HadToShrinkImageException extends Exception {

    private int origWidth;

    private int origHeight;

    private int newWidth;

    private int newHeight;

    private double scale;

    private Image shrunkImage;

    public HadToShrinkImageException(Image shrunkImage, double scale,
            int newWidth, int newHeight, int origWidth, int origHeight) {
        super();
        this.shrunkImage = shrunkImage;
        this.scale = scale;
        this.newHeight = newHeight;
        this.newWidth = newWidth;
        this.origHeight = origHeight;
        this.origWidth = origWidth;
    }

    /**
     * @return the origWidth
     */
    public int getOrigWidth() {
        return origWidth;
    }

    /**
     * @return the origHeight
     */
    public int getOrigHeight() {
        return origHeight;
    }

    /**
     * @return the newWidth
     */
    public int getNewWidth() {
        return newWidth;
    }

    /**
     * @return the newHeight
     */
    public int getNewHeight() {
        return newHeight;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @return the shrunkImage
     */
    public Image getShrunkenImage() {
        return shrunkImage;
    }
    
    
}
