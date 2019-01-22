package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * Image figure that is printed properly
 * 
 * Sid: This figure also stretches / squashes image to size of figure.
 * 
 * @author wzurek
 */
public class PrintableImageFigure extends ImageFigure {

    public PrintableImageFigure(Image image) {
        super(image);
    }

    public PrintableImageFigure() {
        super();
    }

    protected void paintFigure(Graphics gr) {
        Dimension sz = getSize();

        gr.pushState();
        gr.setInterpolation(SWT.HIGH);
        ImageData id = getImage().getImageData();

        Rectangle r = getClientArea();

        gr.drawImage(getImage(), 0, 0, id.width, id.height, r.x, r.y, sz.width,
                sz.height);

        gr.popState();

        //
        // NOTE: Latest Eclipse / PrinterGraphics seems to handle scaling /
        // transparency OK.
        // So don't need the special code for printer graphics below anymore
        // I've left it here just in case we need to revert.
        //
        /*
         * if (gr instanceof PrinterGraphics) { Dimension bnds = getSize();
         * Image nImg = new Image(null, bnds.width, bnds.height); GC gc = new
         * GC(nImg); SWTGraphics imgGr = new SWTGraphics(gc);
         * imgGr.setBackgroundColor(getParent().getBackgroundColor());
         * imgGr.fillRectangle(0, 0, bnds.width, bnds.height);
         * imgGr.translate(getLocation().getCopy().negate());
         * 
         * super.paintFigure(imgGr); imgGr.dispose(); gc.dispose();
         * 
         * gr.drawImage(nImg, getLocation()); nImg.dispose(); } else {
         * 
         *  }
         */
    }

}
