/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */

package com.tibco.xpd.processwidget.policies.clickOrDragGadgetPolicy;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Polyline;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;

class GadgetDragLine extends Polyline implements IFadeableFigure {
    private int alpha = 255;

    private int minimumAlpha = 0;

    GadgetDragLine() {
        setForegroundColor(ColorConstants.darkBlue);
        setLineStyle(Graphics.LINE_DASH);
        setLineWidth(1);
    }

    /**
     * @return the alpha
     */
    @Override
    public Integer getAlpha() {
        return alpha;
    }

    /**
     * @param alpha
     *            the alpha to set
     */
    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            if (alpha >= minimumAlpha) {
                this.alpha = alpha;
            } else {
                this.alpha = minimumAlpha;
            }
            revalidate();
            repaint();
        }
    }

    @Override
    public void paintFigure(Graphics graphics) {
        int oldA = graphics.getAlpha();
        int oldAA = graphics.getAntialias();
        graphics.setAlpha(alpha);
        graphics.setAntialias(SWT.ON);
        super.paintFigure(graphics);
        graphics.setAntialias(oldAA);
        graphics.setAlpha(oldA);
    }

    /**
     * @return the minimumAlpha
     */
    public int getMinimumAlpha() {
        return minimumAlpha;
    }

    /**
     * @param minimumAlpha
     *            the minimumAlpha to set
     */
    public void setMinimumAlpha(int minimumAlpha) {
        this.minimumAlpha = minimumAlpha;

    }
}