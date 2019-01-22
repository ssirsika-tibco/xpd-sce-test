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

package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Flow conneciton anchor for task activities
 * 
 * 
 * @author aallway
 * @since 12 Mar 2012
 */
public class TaskActivityAnchor extends AbstractActivityAnchor implements
        ExtendedConnectionAnchor, ConnectionAnchorDirection {

    /**
     * @param parent
     */
    public TaskActivityAnchor(IFigure parent, boolean isSource) {
        super(parent, isSource);
    }

    @Override
    public Point getSideIntersectionForDirection(int absolute_xORy,
            int direction) {
        if (PositionConstants.WEST == direction) {
            return new Point(getActivityAbsoluteBounds().x, absolute_xORy);
        } else if (PositionConstants.EAST == direction) {
            return new Point(getActivityAbsoluteBounds().right(), absolute_xORy);
        } else if (PositionConstants.NORTH == direction) {
            return new Point(absolute_xORy, getActivityAbsoluteBounds().y);
        } else if (PositionConstants.SOUTH == direction) {
            return new Point(absolute_xORy, getActivityAbsoluteBounds()
                    .bottom());
        }
        return new Point(0, 0);
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#getOriginalMethodLocation(org.eclipse.draw2d.geometry.Point)
     * 
     * @param ref
     * @return
     */
    @Override
    protected Point getOriginalMethodLocation(Point ref) {
        return originalGetLocation(ref);
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#getOriginalMethodLocation(org.eclipse.draw2d.geometry.Rectangle)
     * 
     * @param ref
     * @return
     */
    @Override
    protected Point getOriginalMethodLocation(Rectangle ref) {
        return originalGetLocation(ref);
    }

    /**
     * Original (pre v3.6.0) method - allows anchor location to 'slide' along
     * nearest side to the mid point in th norizontal or vertcal overlap between
     * the task and the target reference.
     * 
     * @param ref
     * @return location
     */
    private Point originalGetLocation(Point ref) {
        Rectangle bnds = getActivityAbsoluteBounds();

        // We will only allow anchor to be within 20% or 10 pixels of corners
        // (whichever is least)
        int minX = bnds.x + getXMargin();
        int maxX = (bnds.x + bnds.width) - getXMargin();
        int minY = bnds.y + getYMargin();
        int maxY = (bnds.y + bnds.height) - getYMargin();

        // Get the default direction of line into/from anchor according to the
        // reference point
        int direction = originalGetDirection(ref);

        setLastCalculatedDirection(direction);
        setUsingOldSlideAlongSideMethod(true);

        // x and y location of anchor point (to be assigned).
        int x = 0;
        int y = 0;

        // Check the direction and set the location of anchor point
        // accordingly...
        switch (direction) {
        case PositionConstants.NORTH:
            // Set directly below reference point.
            y = bnds.y;
            x = Math.max(ref.x, minX); // Don't allow to go beyond 20% of
                                       // corner...
            x = Math.min(x, maxX); // Don't allow to go beyond 20% of corner...
            break;

        case PositionConstants.SOUTH:
            // Set directly below reference point.
            y = bnds.y + bnds.height;
            x = Math.max(ref.x, minX); // Don't allow to go beyond 20% of
                                       // corner...
            x = Math.min(x, maxX); // Don't allow to go beyond 20% of corner...
            break;

        case PositionConstants.WEST:
            // Set directly beside reference point.
            x = bnds.x;
            y = Math.max(ref.y, minY); // Don't allow to go beyond 20% of
                                       // corner...
            y = Math.min(y, maxY); // Don't allow to go beyond 20% of corner...
            break;

        case PositionConstants.EAST:
            // Set directly beside reference point.
            x = bnds.x + bnds.width;
            y = Math.max(ref.y, minY); // Don't allow to go beyond 20% of
                                       // corner...
            y = Math.min(y, maxY); // Don't allow to go beyond 20% of corner...
            break;

        default:
            // Default to centre of object just in case...
            x = bnds.x + (bnds.width / 2);
            y = bnds.y + (bnds.height / 2);

        }

        // That's it, return what we've come up with.
        Point location = (new Point(x, y));
        return location;
    }

    /**
     * Original (pre v3.6.0) method - allows anchor location to 'slide' along
     * nearest side to the mid point in th norizontal or vertcal overlap between
     * the task and the target reference.
     * 
     */
    private Point originalGetLocation(Rectangle ref) {
        Rectangle rct = getActivityBounds();
        getOwner().translateToAbsolute(rct);
        setUsingOldSlideAlongSideMethod(true);

        Point result;

        if (rct.intersects(ref)) {
            result = getLocation(ref.getCenter());
            setLastCalculatedDirection(PositionConstants.NONE);
        } else {
            int dir1 = getPosition(ref.getTopLeft(), rct.getBottomRight());
            int dir2 = getPosition(ref.getBottomRight(), rct.getTopLeft());

            // SID DI:24908 - We never link directly to corner anymore (we only
            // allow start / end of line within
            // 10 pixels (or 20% whichever is smallest) so now we have to decide
            // which
            // of the 2 possible sides we are going to choose.
            if (dir1 != dir2) {
                // The 2 rectangles are in-line either horizontally or
                // vertically
                // This should give us the correct direction.
                int dir = dir1 & dir2;

                setLastCalculatedDirection(dir);

                switch (dir) {
                case PositionConstants.NORTH:
                    result = new Point(getCalculateHorizontal(rct, ref), rct.y);
                    break;
                case PositionConstants.SOUTH:
                    result =
                            new Point(getCalculateHorizontal(rct, ref), rct.y
                                    + rct.height);
                    break;
                case PositionConstants.WEST:
                    result = new Point(rct.x, getCalculateVertical(rct, ref));
                    break;
                case PositionConstants.EAST:
                    result =
                            new Point(rct.x + rct.width,
                                    getCalculateVertical(rct, ref));
                    break;
                default:
                    // not possible
                    result = new Point(getCalculateHorizontal(rct, ref), rct.y);
                }
            } else {
                // The two rectangles are not in-line horizontally or
                // vertically,
                // work out the correct direction dependent on the relative
                // position of the 2 closest corners.
                setLastCalculatedDirection(dir1);
                result = getCorner(rct, ref, dir1);
            }

        }

        return result;
    }

    //
    // SID DI:24908 - We never link directly to corner anymore (we only allow
    // start / end of line within
    // 10 pixels (or 20% whichever is smallest) so now we have to decide which
    // of the 2 possible sides we are going to choose.
    private Point getCorner(Rectangle thisActRect, Rectangle refRect,
            int directionFromActToRef) {

        // So what we'll do is say
        // "where is the nearest ref corner in relation to our nearest corner"
        Point srcPt;
        Point tgtPt;

        switch (directionFromActToRef) {
        case PositionConstants.NORTH_EAST:
            srcPt = thisActRect.getTopRight();
            tgtPt = refRect.getBottomLeft();
            break;

        case PositionConstants.NORTH_WEST:
            srcPt = thisActRect.getTopLeft();
            tgtPt = refRect.getBottomRight();
            break;

        case PositionConstants.SOUTH_EAST:
            srcPt = thisActRect.getBottomRight();
            tgtPt = refRect.getTopLeft();
            break;

        case PositionConstants.SOUTH_WEST:
            srcPt = thisActRect.getBottomLeft();
            tgtPt = refRect.getTopRight();
            break;

        default:
            // not possible
            throw new IllegalArgumentException();
        }

        // Get the position of the target corner in relation to the source
        // corner
        int direction =
                AnchorDirectionHelper.getAnchorDirection(XPDFigureUtilities
                        .getXpdRouterStyle(getOwner()), srcPt, tgtPt);

        Point result;

        switch (direction) {
        case PositionConstants.NORTH:
        case PositionConstants.SOUTH:
            if ((directionFromActToRef & PositionConstants.EAST) == PositionConstants.EAST) {
                // Ref rect was right of us, so we've got right hand point, so
                // subtract margin...
                result = new Point(srcPt.x - getXMargin(), srcPt.y);
            } else {
                result = new Point(srcPt.x + getXMargin(), srcPt.y);
            }

            break;

        case PositionConstants.WEST:
        case PositionConstants.EAST:
            if ((directionFromActToRef & PositionConstants.SOUTH) == PositionConstants.SOUTH) {
                // Ref rect was below us, so we've go bottom point, so subtract
                // margin...
                result = new Point(srcPt.x, srcPt.y - getYMargin());
            } else {
                result = new Point(srcPt.x, srcPt.y + getYMargin());
            }
            break;

        default:
            result = thisActRect.getCenter().getCopy();
        }

        return (result);
    }

    /**
     * Returns the position of p2 from p1.
     */
    private int getPosition(Point p1, Point p2) {
        int result = PositionConstants.NONE;
        if (p1.x > p2.x) {
            result |= PositionConstants.EAST;
        } else if (p1.x < p2.x) {
            result |= PositionConstants.WEST;
        }
        if (p1.y > p2.y) {
            result |= PositionConstants.SOUTH;
        } else if (p1.y < p2.y) {
            result |= PositionConstants.NORTH;
        }
        return result;
    }

}
