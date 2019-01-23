/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Mar 2010)
 */
public class GatewayInProgressFigure extends AbstractPulsingInProgressFigure {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.process.progression.
     * AbstractActivityInProgressFigure#createPaths()
     */
    @Override
    protected Path[] createPaths() {

        Path[] paths = new Path[4];

        Rectangle b = getBounds().getCopy();
        b.shrink(2, 2);
        Point right = b.getRight();
        Point left = b.getLeft();
        Point top = b.getTop();
        Point bottom = b.getBottom();

        PointList pts;
        Point p1;
        Point p2;
        Point p3;

        /* Top */
        paths[0] = new Path(null);

        pts = new PointList();
        p2 = top;
        pts.addPoint(left);
        pts.addPoint(p2);

        p1 = XPDLineUtilities.getLinePointFromPortion(pts, 65f);

        pts = new PointList();
        pts.addPoint(p2);
        pts.addPoint(right);

        p3 = XPDLineUtilities.getLinePointFromPortion(pts, 35f);

        paths[0].moveTo(p1.x, p1.y);
        paths[0].lineTo(p2.x, p2.y);
        paths[0].lineTo(p3.x, p3.y);

        /* Right */
        paths[1] = new Path(null);

        pts = new PointList();
        p2 = right;
        pts.addPoint(top);
        pts.addPoint(p2);

        p1 = XPDLineUtilities.getLinePointFromPortion(pts, 65f);

        pts = new PointList();
        pts.addPoint(p2);
        pts.addPoint(bottom);

        p3 = XPDLineUtilities.getLinePointFromPortion(pts, 35f);

        paths[1].moveTo(p1.x, p1.y);
        paths[1].lineTo(p2.x, p2.y);
        paths[1].lineTo(p3.x, p3.y);

        /* Bottom */
        paths[2] = new Path(null);

        pts = new PointList();
        p2 = bottom;
        pts.addPoint(right);
        pts.addPoint(p2);

        p1 = XPDLineUtilities.getLinePointFromPortion(pts, 65f);

        pts = new PointList();
        pts.addPoint(p2);
        pts.addPoint(left);

        p3 = XPDLineUtilities.getLinePointFromPortion(pts, 35f);

        paths[2].moveTo(p1.x, p1.y);
        paths[2].lineTo(p2.x, p2.y);
        paths[2].lineTo(p3.x, p3.y);

        /* Left */
        paths[3] = new Path(null);

        pts = new PointList();
        p2 = left;
        pts.addPoint(bottom);
        pts.addPoint(p2);

        p1 = XPDLineUtilities.getLinePointFromPortion(pts, 65f);

        pts = new PointList();
        pts.addPoint(p2);
        pts.addPoint(top);

        p3 = XPDLineUtilities.getLinePointFromPortion(pts, 35f);

        paths[3].moveTo(p1.x, p1.y);
        paths[3].lineTo(p2.x, p2.y);
        paths[3].lineTo(p3.x, p3.y);

        return paths;
    }
}
