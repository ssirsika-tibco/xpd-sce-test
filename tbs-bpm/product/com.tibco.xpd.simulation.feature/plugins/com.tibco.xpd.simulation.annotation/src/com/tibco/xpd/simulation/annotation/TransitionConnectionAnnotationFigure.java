/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import java.util.Random;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

/**
 * @author wzurek
 */
public class TransitionConnectionAnnotationFigure extends Figure implements
        FigureListener {

    /** Start angle. */
    private static final int START_ANGLE = 90;

    /** Vertical offset from the connection. */
    private static final int Y_LINE_OFFSET = 17;

    /** X offset. */
    private static final int X_OFFSET = -7;

    /** Y offset. */
    private static final int Y_OFFSET = -7;

    /** Minimum annotation width. */
    private static final int MINIMUM_WIDTH = 15;

    /** Minimum annotation height. */
    private static final int MINIMUM_HEIGHT = 15;

    /** The current percentage. */
    private double percent;

    /**
     * @param base The base figure to annotate.
     */
    public TransitionConnectionAnnotationFigure(IFigure base) {
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));

        setForegroundColor(ColorConstants.darkBlue);
        setBackgroundColor(ColorConstants.lightBlue);

        Rectangle b = base.getBounds().getCopy();
        base.translateToParent(b);
        relocate(base);
        base.addFigureListener(this);
    }

    /**
     * @return The current percentage.
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @param percent The current percentage.
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * @param parent The parent figure.
     */
    private void relocate(IFigure parent) {
        PolylineConnection conn = (PolylineConnection) parent;
        PointList points = conn.getPoints();

        int dest = Y_LINE_OFFSET;

        if (points.size() > 1) {
            Point center;

            Point p1 = points.getPoint(0).getCopy();
            Point p2 = points.getPoint(1).getCopy();

            parent.translateToParent(p1);
            parent.translateToParent(p2);

            int signY = p2.y - p1.y;
            signY = Math.min(signY, 1);
            signY = Math.max(signY, -1);
            int signX = p2.x - p1.x;
            signX = Math.min(signX, 1);
            signX = Math.max(signX, -1);

            if (p2.x == p1.x) {
                center = new Point(p1.x, p1.y + dest * signY);
            } else {
                double a = (p2.y - p1.y) / (p2.x - p1.x);

                double x = Math.sqrt(dest * dest / (a * a + 1)) * signX;
                double y = x * a;

                center = new Point(p1.x + x, p1.y + y);
            }

            // x = ay
            // d = sqrt(x^2+y^2)

            // d^2 = x^2+y^2
            // d^2 = a^2*y^+y^2
            // d^2 = y^2(a^2+1)
            // sqrt(d^2/(a^2+1)) = y;

            center.translate(X_OFFSET, Y_OFFSET);
            setBounds(new Rectangle(center, new Dimension(MINIMUM_WIDTH,
                    MINIMUM_HEIGHT)));
        }
    }

    /**
     * @param gr The graphics context.
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     */
    protected void paintFigure(Graphics gr) {
        Rectangle ca = getClientArea().getCopy();
        ca.width--;
        ca.height--;

        Random rand = new Random();

        gr.pushState();
        int aa = gr.getAntialias();
        Color c = gr.getBackgroundColor();
        gr.setBackgroundColor(ColorConstants.white);
        gr.fillOval(ca);
        gr.setBackgroundColor(c);
        gr.setAntialias(SWT.ON);
        // Temporarily uses random input value.
        gr.fillArc(ca, START_ANGLE, -rand.nextInt(START_ANGLE * 2 * 2));
        gr.drawOval(ca);

        gr.setAntialias(aa);
        gr.popState();
    }

    /**
     * @param source The figure.
     * @see org.eclipse.draw2d.FigureListener#figureMoved(
     *      org.eclipse.draw2d.IFigure)
     */
    public void figureMoved(IFigure source) {
        relocate(source);
    }
}
