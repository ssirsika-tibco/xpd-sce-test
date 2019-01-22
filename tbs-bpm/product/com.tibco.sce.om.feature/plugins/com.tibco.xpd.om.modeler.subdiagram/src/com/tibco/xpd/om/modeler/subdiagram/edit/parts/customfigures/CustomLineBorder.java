/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Insets;

/**
 * Custom line border that can be set as solid line or dashed line. Use
 * {@link #makeDashed()} or {@link #makeSolid()} to change line style.
 * 
 * @author njpatel
 */
public class CustomLineBorder extends LineBorder {

    private static final int[] DASH_LINE = new int[] { 6, 4 };

    private boolean isDash;

    /**
     * 
     */
    public CustomLineBorder() {
        super();
    }

    public void makeDashed() {
        isDash = true;
    }

    public void makeSolid() {
        isDash = false;
    }

    /**
     * @see org.eclipse.draw2d.LineBorder#paint(org.eclipse.draw2d.IFigure,
     *      org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
     * 
     * @param figure
     * @param graphics
     * @param insets
     */
    @Override
    public void paint(IFigure figure, Graphics graphics, Insets insets) {
        if (isDash) {
            setStyle(Graphics.LINE_CUSTOM);
            graphics.setLineDash(DASH_LINE);
        } else {
            setStyle(Graphics.LINE_SOLID);
        }
        super.paint(figure, graphics, insets);
    }
}
