/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * Flow connection anchor for gateway activities.
 * 
 * @author aallway
 * @since 8 Mar 2012
 */
public class GatewayActivityAnchor extends AbstractActivityAnchor {

    private static final String ShapeWithDescriptionFigure = null;

    /**
     * @param parent
     */
    public GatewayActivityAnchor(IFigure parent, boolean isSource) {
        super(parent, isSource);
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#getSideIntersectionForDirection(int,
     *      int)
     * 
     * @param absolute_xORy
     * @param direction
     * @return
     */
    @Override
    public Point getSideIntersectionForDirection(int absolute_xORy,
            int direction) {

        Rectangle bnds = getActivityAbsoluteBounds();

        Point west =
                getSideCentreLocationFromDirection(bnds, PositionConstants.WEST);

        Point east =
                getSideCentreLocationFromDirection(bnds, PositionConstants.EAST);

        Point north =
                getSideCentreLocationFromDirection(bnds,
                        PositionConstants.NORTH);

        Point south =
                getSideCentreLocationFromDirection(bnds,
                        PositionConstants.SOUTH);

        if (PositionConstants.WEST == direction) {
            if (absolute_xORy < west.y) {
                /*
                 * Above the mid point, this should connect to the left hand,
                 * upper line and get intersection.
                 */
                return XPDLineUtilities.getIntersectionPoint(west,
                        north,
                        new Point(bnds.x - 1, absolute_xORy),
                        new Point(Integer.MAX_VALUE, absolute_xORy));

            } else if (absolute_xORy > west.y) {
                /*
                 * Below the mid point, this should connect to the left hand,
                 * lower line.
                 */
                return XPDLineUtilities.getIntersectionPoint(west,
                        south,
                        new Point(bnds.x - 1, absolute_xORy),
                        new Point(Integer.MAX_VALUE, absolute_xORy));

            } else {
                /*
                 * Dead level with centre of west side, just use the centre
                 * location we already have.
                 */
                return west;
            }

        } else if (PositionConstants.EAST == direction) {
            if (absolute_xORy < east.y) {
                /*
                 * Above the mid point, this should connect to the right hand,
                 * upper line and get intersection.
                 */
                return XPDLineUtilities.getIntersectionPoint(north,
                        east,
                        new Point(bnds.x - 1, absolute_xORy),
                        new Point(Integer.MAX_VALUE, absolute_xORy));

            } else if (absolute_xORy > east.y) {
                /*
                 * Below the mid point, this should connect to the right hand,
                 * lower line.
                 */
                return XPDLineUtilities.getIntersectionPoint(east,
                        south,
                        new Point(bnds.x - 1, absolute_xORy),
                        new Point(Integer.MAX_VALUE, absolute_xORy));

            } else {
                /*
                 * Dead level with centre of east side, just use the centre
                 * location we already have.
                 */
                return east;
            }

        } else if (PositionConstants.NORTH == direction) {
            if (absolute_xORy < north.x) {
                /*
                 * Left of the mid point, this should connect to the left hand,
                 * upper line and get intersection.
                 */
                return XPDLineUtilities.getIntersectionPoint(west,
                        north,
                        new Point(absolute_xORy, bnds.y - 1),
                        new Point(absolute_xORy, Integer.MAX_VALUE));

            } else if (absolute_xORy > north.x) {
                /*
                 * Right of the mid point, this should connect to the right
                 * hand, upper line.
                 */
                return XPDLineUtilities.getIntersectionPoint(north,
                        east,
                        new Point(absolute_xORy, bnds.y - 1),
                        new Point(absolute_xORy, Integer.MAX_VALUE));

            } else {
                /*
                 * Dead level with centre of north side, just use the centre
                 * location we already have.
                 */
                return north;
            }

        } else if (PositionConstants.SOUTH == direction) {
            if (absolute_xORy < south.x) {
                /*
                 * Left of the mid point, this should connect to the left hand,
                 * lower line and get intersection.
                 */
                return XPDLineUtilities.getIntersectionPoint(west,
                        south,
                        new Point(absolute_xORy, bnds.y - 1),
                        new Point(absolute_xORy, Integer.MAX_VALUE));

            } else if (absolute_xORy > south.x) {
                /*
                 * Right of the mid point, this should connect to the right
                 * hand, lower line.
                 */
                return XPDLineUtilities.getIntersectionPoint(south,
                        east,
                        new Point(absolute_xORy, bnds.y - 1),
                        new Point(absolute_xORy, Integer.MAX_VALUE));

            } else {
                /*
                 * Dead level with centre of south side, just use the centre
                 * location we already have.
                 */
                return south;
            }
        }

        return null;
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#getOriginalMethodLocation(org.eclipse.draw2d.geometry.Point)
     * 
     * @param ref
     * @return
     */
    @Override
    protected Point getOriginalMethodLocation(Point ref) {
        /*
         * Old style is to connect directly to centre - which isn't the same as
         * for square tasks that allow slide along the side of object.
         */
        return super.getSinglePointLocation(ref);
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#getOriginalMethodLocation(org.eclipse.draw2d.geometry.Rectangle)
     * 
     * @param ref
     * @return
     */
    @Override
    protected Point getOriginalMethodLocation(Rectangle ref) {
        /*
         * Old style is to connect directly to centre - which isn't the same as
         * for sqaure tasks that allow slide along the side of object.
         */
        return super.getSinglePointLocation(ref);
    }

}
