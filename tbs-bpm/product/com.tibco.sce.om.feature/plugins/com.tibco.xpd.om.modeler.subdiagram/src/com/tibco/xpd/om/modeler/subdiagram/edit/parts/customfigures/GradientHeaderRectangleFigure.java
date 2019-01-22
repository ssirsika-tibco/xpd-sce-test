/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * A custom Figure that extends RectangleFigure. Fills its gradient with
 * the two colors passed to the constructor.
 * 
 * @author rgreen
 *
 */
public class GradientHeaderRectangleFigure extends RectangleFigure implements IGradientFigure {

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
            graphics.setForegroundColor(DiagramColorConstants.diagramBlue);
            graphics.setBackgroundColor(ColorConstants.white);

        }

        graphics.fillGradient(getBounds(), false);

        // outlineShape(graphics);
    }

    public void setGradientStart(Color gradColor1) {
        this.gradColor1 = gradColor1;
        this.invalidate();
    }

    public void setGradientEnd(Color gradColor2) {
        this.gradColor2 = gradColor2;
        this.invalidate();
    }

}
