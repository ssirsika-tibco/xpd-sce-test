/*
 * 
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;

/**
 * Message connection
 * 
 * @author wzurek
 */
public class MessageFlowFigure extends RoundedPolylineConnection implements
        IScaleableConnectionFigure {

    private static final int START_ELLIPSE_SIZE = 7;

    private EllipseDecoration startDecoration = null;

    private PolygonDecoration endDecoration = null;

    class EllipseDecoration extends Ellipse implements RotatableDecoration {

        @Override
        public void setReferencePoint(Point p) {
            // setBounds(new Rectangle(p.x - 2, p.y - 2, 5, 5));
        }

        @Override
        public void setLocation(Point p) {
            if (getLocation().equals(p))
                return;
            Rectangle r = new Rectangle(getBounds());
            r.setLocation(p.x - r.width / 2, p.y - r.height / 2);
            setBounds(r);
        }
    }

    public MessageFlowFigure() {
        super();

        setLineStyle(Graphics.LINE_CUSTOM);
        float dash[] = new float[] { 10, 5 };
        setLineDash(dash);

        startDecoration = new EllipseDecoration();
        startDecoration.setSize(START_ELLIPSE_SIZE, START_ELLIPSE_SIZE);
        setSourceDecoration(startDecoration);

        endDecoration = new PolygonDecoration();
        // pd.setPoints( new PointList(new int[]{}));
        endDecoration.setLineStyle(Graphics.LINE_SOLID);
        endDecoration.setBackgroundColor(ColorConstants.white);
        endDecoration.setFill(true);
        setTargetDecoration(endDecoration);
    }

    @Override
    public void setSourceDecorationScale(double sourceDecorationScale) {
        if (startDecoration != null) {
            startDecoration
                    .setSize((int) Math.ceil(START_ELLIPSE_SIZE
                            * sourceDecorationScale),
                            (int) Math.ceil(START_ELLIPSE_SIZE
                                    * sourceDecorationScale));
        }

    }

    @Override
    public void setTargetDecorationScale(double targetDecorationScale) {
        if (endDecoration != null) {
            // btw the 7 and 3 constants shamelessly ripped out of
            // PolygoDecoration class.
            endDecoration
                    .setScale(ProcessWidgetConstants.POLYGONDECORATION_XSCALE
                            * targetDecorationScale,
                            ProcessWidgetConstants.POLYGONDECORATION_YSCALE
                                    * targetDecorationScale);
        }
    }

}
