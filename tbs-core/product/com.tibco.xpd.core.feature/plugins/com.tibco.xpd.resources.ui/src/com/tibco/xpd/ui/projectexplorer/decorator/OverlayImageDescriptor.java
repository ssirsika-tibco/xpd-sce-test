/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.ui.projectexplorer.decorator;

import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * @author wzurek
 * 
 */
public class OverlayImageDescriptor extends CompositeImageDescriptor {

    private final ImageData baseImage;

    private final ImageData errorOverlay;

    /**
     * 
     */
    public OverlayImageDescriptor(ImageData baseImage, ImageData errorOverlay) {
        this.baseImage = baseImage;
        this.errorOverlay = errorOverlay;
    }

    @Override
    protected void drawCompositeImage(int width, int height) {
        drawImage(baseImage, 0, 0);
        Point s = getSize();
        drawImage(errorOverlay, 0, s.y - errorOverlay.height);
    }

    @Override
    protected Point getSize() {
        return new Point(baseImage.width, baseImage.height);
    }

}
