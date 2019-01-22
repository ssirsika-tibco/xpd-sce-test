/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * A custom Figure that extends RectangleFigure. Fills its gradient with
 * the two colors passed to the constructor.
 * 
 * @author rgreen
 *
 */
public class GradientHeaderRectangleFigure extends RectangleFigure {

    private RGB gradRGB1 = null;
    private RGB gradRGB2 = null;
    private Color gradColor1 = null;
    private Color gradColor2 = null;

    public GradientHeaderRectangleFigure() {

    }

    /**
     * @param grad1
     * @param grad2
     */
    public GradientHeaderRectangleFigure(RGB grad1, RGB grad2) {

        gradRGB1 = grad1;
        gradRGB2 = grad2;

    }

    public GradientHeaderRectangleFigure(Color grad1, Color grad2) {

        gradColor1 = grad1;
        gradColor2 = grad2;

    }

    @Override
    public void paintFigure(Graphics graphics) {

        if ((gradColor1 != null) && (gradColor2 != null)) {
            // Set to something so we don't crash
            graphics.setForegroundColor(gradColor1);
            graphics.setBackgroundColor(gradColor2);
        } else {
            // TODO: Set to preferences colors
            graphics.setForegroundColor(ColorConstants.lightGray);
            graphics.setBackgroundColor(ColorConstants.white);

        }

        graphics.fillGradient(getBounds(), false);

        // outlineShape(graphics);
    }

}
