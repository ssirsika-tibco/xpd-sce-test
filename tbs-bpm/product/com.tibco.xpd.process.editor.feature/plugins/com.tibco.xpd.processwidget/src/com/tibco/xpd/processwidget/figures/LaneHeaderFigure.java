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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;

import com.tibco.xpd.processwidget.ProcessWidgetColors;

/**
 * @author wzurek
 */
public class LaneHeaderFigure extends HeaderFigure {

    private int laneAlpha = 10;

    private final int roundSize = 10;

    public LaneHeaderFigure(HeaderFigureStyle headerFigureStyle) {
        super(headerFigureStyle);

        setCloseable(true);
    }

    @Override
    public void setBounds(Rectangle rect) {
        super.setBounds(rect);

        setParentFillBounds(getFillBounds());
    }

    private Rectangle getFillBounds() {
        Rectangle ca = getBounds().getCopy();

        ca.x++;
        ca.y++;
        ca.width -= 3;
        ca.height -= 1;

        return (ca);
    }

    @Override
    protected void paintFigure(Graphics gr) {
        Image image = getImage();

        gr.pushState();
        int aa = gr.getAntialias();
        gr.setAntialias(SWT.ON);

        Rectangle ca = getClientArea().getCopy();

        // If lane bacground alpha not transparent then do a fill.
        if (laneAlpha != 0) {
            // Don't colour lane content for printing.
            // (p.s. if you do try and setAlpha on a printer device then SWT
            // switches to
            // using a gdip object which works in a different co-ordinate space
            // and
            // things go in completely the wrong place!!!).
            if (!(gr instanceof PrinterGraphics)) {
                gr.pushState();
                int currAlpha = gr.getAlpha();
                gr.setAlpha(laneAlpha);

                gr.fillRectangle(ca);

                gr.setAlpha(currAlpha);
                gr.popState();
            }
        }

        ca = getFillBounds();

        gr.setBackgroundColor(getBackgroundColor());

        gr.setForegroundColor(ProcessWidgetColors.getGradientEndColor(gr
                .getBackgroundColor()));

        Pattern p;
        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            p =
                    XPDFigureUtilities.setBackgroundPattern(gr,
                            ca.x,
                            ca.y,
                            ca.x,
                            ca.y + ca.height);
        } else {
            p =
                    XPDFigureUtilities.setBackgroundPattern(gr,
                            ca.x,
                            ca.y,
                            ca.x + ca.width,
                            ca.y);
        }

        gr.fillRoundRectangle(ca, roundSize, roundSize);

        XPDFigureUtilities.resetBackgroundPattern(gr, p);

        gr.setBackgroundColor(getBackgroundColor());
        gr.setForegroundColor(getForegroundColor());

        if (image != null) {
            super.paintFigure(gr);
        }

        ca.width--;
        ca.height--;
        if (!XPDFigureUtilities.isInTaskLibraryAlternateView(this)) {
            gr.drawRoundRectangle(ca, roundSize, roundSize);
        }

        gr.setAntialias(aa);
        gr.popState();
    }

    /**
     * Set alpha blend for content lane (0 = completely transparent, 25 =
     * completely opaque)
     * 
     * @param laneAlpha
     */
    public void setLaneAlpha(int laneAlpha) {
        this.laneAlpha = laneAlpha;
    }

}
