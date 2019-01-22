/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.neatstuff;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.swt.graphics.Image;

/**
 * {@link ImageFigure} whose fading alpha level is setable to make it semi
 * transparent.
 * 
 * 
 * @author aallway
 * @since 15 Jun 2012
 */
public class FadeableImageFigure extends ImageFigure implements IFadeableFigure {

    private int alpha = 255;

    public FadeableImageFigure() {
        super();
    }

    public FadeableImageFigure(Image image, int alignment) {
        super(image, alignment);
    }

    public FadeableImageFigure(Image image) {
        super(image);
    }

    @Override
    public Integer getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            this.alpha = alpha;
            revalidate();
            repaint();
        }

        return;
    }

    @Override
    public void paint(Graphics graphics) {
        int oldAlpha = graphics.getAlpha();

        graphics.setAlpha(alpha);

        super.paint(graphics);

        graphics.setAlpha(oldAlpha);

        return;
    }

}
