/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Path;

/**
 * In progress figure for connection figures.
 * 
 * @author aallway
 * @since 25 Jan 2011
 */
public class ConnectionInProgressFigure extends AbstractPulsingInProgressFigure {

    private PointList points;

    /**
     * @param points
     */
    public ConnectionInProgressFigure(PointList points) {
        super();
        this.points = points;
    }

    /**
     * @param points
     *            the points to set
     */
    public void setPoints(PointList points) {
        this.points = points;
        disposePaths();
        invalidate();
    }

    /**
     * @see com.tibco.xpd.processwidget.process.progression.AbstractPulsingInProgressFigure#createPaths()
     * 
     * @return
     */
    @Override
    protected Path[] createPaths() {
        int numPoints = points.size();

        if (numPoints < 2) {
            return new Path[0];
        }

        Path[] paths = new Path[numPoints - 1];

        for (int i = 0, pathIdx = 0; i < (numPoints - 1); i++, pathIdx++) {
            Point point = points.getPoint(i);
            Point next = points.getPoint(i + 1);

            paths[pathIdx] = new Path(null);

            paths[pathIdx].moveTo(point.x, point.y);

            paths[pathIdx].lineTo(next.x, next.y);

        }

        return paths;
    }
}
