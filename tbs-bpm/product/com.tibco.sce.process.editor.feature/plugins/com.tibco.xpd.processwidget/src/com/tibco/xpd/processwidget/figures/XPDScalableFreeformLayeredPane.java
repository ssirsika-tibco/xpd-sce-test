package com.tibco.xpd.processwidget.figures;

/**
 * GEF / Draw2d has a problem with scaling in that GEF tends to scale from diagram logical co-ordinates
 * to scaled screen co-ordinates and then back again.
 * 
 * Unfortunately Draw2d's objects like Rectangle and Point use integer co-ordinates and 
 * either do multiply against floating point scale value then cast back to int or
 * (in the case of Point, use Math.floor() which is effectively the same thing).
 * 
 * So if we take x-co-ord value of 10 as example and use scale factor of 1.25 then...
 * 
 *  Scale up:      10 * 1.25 = 12.5 (cast back to 12).
 *  Scale down:    12 / 1.25 = 9.6 (cast back to 9).
 *  
 * We started with 10 now we have 9 :o(
 * 
 * We can't replace all our (and GEF's) usages of Point and Rectangle and Dimension
 *   (the ones we really care about)
 * BUT all these objects are scaled via the GEF ScalableFreeformLayeredPane class.
 * 
 * So this is an attempt to fix things by not calling Point / Rectangle's scaling routines
 * and trying to better here ourselves.
 * 
 * Yes I know it's horrible,m because when another Translatable class object comes along it won't
 * work - but tough! maybe it'll work for the ones we have.
 *  
 */

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;

public class XPDScalableFreeformLayeredPane extends ScalableFreeformLayeredPane {

    public XPDScalableFreeformLayeredPane() {
        super();
    }

    private void translate(Translatable t, double factor) {
        /*
         * Sid XPD-3811. Used to do p.x = Math.round(p.preciseX) etc in this
         * method.
         * 
         * This was different with normal treatment of precisX -> X performed by
         * the Point class itself when it synch's the int x's with float x's as
         * that does a floor). That could cause problems when comparing points
         * that had been translated via this method with the original.
         * 
         * So now we will try to ensure all things are equal by making sure we
         * use the EXACT equivalent of the PrecisionPoint/Rect/Dimension's
         * updateInts() method.
         */

        if (t instanceof PrecisionPoint) {
            PrecisionPoint p = (PrecisionPoint) t;

            p.preciseX = p.preciseX * factor;
            p.preciseY = p.preciseY * factor;

            p.updateInts();

        } else if (t instanceof Point) {
            Point p = (Point) t;

            PrecisionPoint pp = new PrecisionPoint(p);

            pp.preciseX = pp.preciseX * factor;
            pp.preciseY = pp.preciseY * factor;

            pp.updateInts();
            p.x = pp.x;
            p.y = pp.y;

        } else if (t instanceof PrecisionRectangle) {
            PrecisionRectangle r = (PrecisionRectangle) t;
            r.preciseX = r.preciseX * factor;
            r.preciseY = r.preciseY * factor;
            r.preciseWidth = r.preciseWidth * factor;
            r.preciseHeight = r.preciseHeight * factor;

            r.updateInts();

        } else if (t instanceof Rectangle) {
            Rectangle r = (Rectangle) t;

            PrecisionRectangle pr = new PrecisionRectangle(r);
            pr.preciseX *= factor;
            pr.preciseY *= factor;
            pr.preciseWidth *= factor;
            pr.preciseHeight *= factor;

            pr.updateInts();

            r.x = pr.x;
            r.y = pr.y;
            r.width = pr.width;
            r.height = pr.height;

        } else if (t instanceof PrecisionDimension) {
            PrecisionDimension d = (PrecisionDimension) t;
            d.preciseWidth = d.preciseWidth * factor;
            d.preciseHeight = d.preciseHeight * factor;

            d.updateInts();

        } else if (t instanceof Dimension) {
            Dimension d = (Dimension) t;

            PrecisionDimension pd = new PrecisionDimension(d);
            pd.width *= factor;
            pd.height *= factor;
            pd.updateInts();

            d.width = pd.width;
            d.height = pd.height;

        } else {
            t.performScale(factor);
        }
    }

    @Override
    public void translateToParent(Translatable t) {
        double factor = super.getScale();

        translate(t, factor);

    }

    @Override
    public void translateFromParent(Translatable t) {
        double factor = (1d / super.getScale());

        translate(t, factor);
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintClientArea(Graphics)
     */
    @Override
    protected void paintClientArea(Graphics graphics) {
        if (getChildren().isEmpty())
            return;
        if (getScale() == 1.0) {
            super.paintClientArea(graphics);
        } else {
            ScaledGraphics g = new XpdScaledGraphics(graphics);
            boolean optimizeClip =
                    getBorder() == null || getBorder().isOpaque();
            if (!optimizeClip)
                g.clipRect(getBounds().getCropped(getInsets()));
            g.scale(getScale());
            g.pushState();
            paintChildren(g);
            g.dispose();
            graphics.restoreState();
        }
    }

}
