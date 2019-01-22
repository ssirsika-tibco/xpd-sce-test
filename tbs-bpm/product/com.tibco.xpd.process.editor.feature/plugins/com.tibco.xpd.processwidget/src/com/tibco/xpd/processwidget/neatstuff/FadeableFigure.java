/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.neatstuff;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;

/**
 * FadeableFigure
 * 
 * 
 * @author aallway
 * @since 3.3 (7 Sep 2009)
 */
public class FadeableFigure extends Figure implements IFadeableFigure {

    private int alpha = 255;

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
