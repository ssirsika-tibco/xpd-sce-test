/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.tibco.xpd.processwidget.ProcessWidgetColors;

/**
 * @author wzurek
 */
public class PoolHeaderFigure extends HeaderFigure {

    public PoolHeaderFigure(HeaderFigureStyle headerFigureStyle) {
        super(headerFigureStyle);

        setForegroundColor(ColorConstants.gray);
        setBackgroundColor(ColorConstants.tooltipBackground);

    }

    public void setBounds(Rectangle rect) {
        super.setBounds(rect);

        setParentFillBounds(getFillBounds());
    }

    private Rectangle getFillBounds() {
        Rectangle ca = getBounds().getCopy();

        ca.x++;
        ca.y++;
        ca.width -= 1;
        ca.height -= 3;

        return (ca);
    }

    protected void paintFigure(Graphics gr) {
        gr.pushState();
        int aa = gr.getAntialias();
        gr.setAntialias(SWT.ON);

        Rectangle ca = getClientArea().getCopy();

        gr.drawLine(ca.x + ca.width - 1, ca.y, ca.x + ca.width - 1, ca.y
                + ca.height);

        ca = getFillBounds();

        gr.setBackgroundColor(getBackgroundColor());
        gr.setForegroundColor(ProcessWidgetColors
                .getGradientEndColor(gr.getBackgroundColor()));

        Pattern p;

        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            p = XPDFigureUtilities.setBackgroundPattern(gr, ca.x, ca.y, ca.x, ca.y + ca.height);
        } else {
            p = XPDFigureUtilities.setBackgroundPattern(gr, ca.x, ca.y, ca.x + ca.width, ca.y);
        }

        gr.fillRectangle(ca);

        XPDFigureUtilities.resetBackgroundPattern(gr, p);

        gr.setBackgroundColor(getBackgroundColor());
        gr.setForegroundColor(getForegroundColor());

        Image image = getImage();
        if (image != null) {
            super.paintFigure(gr);
        }

        // gr.setForegroundColor(new Color(null, 0,255,255));
        ca.width--;
        ca.height--;
        gr.drawRectangle(ca);

        gr.setAntialias(aa);
        gr.popState();
    }
}
