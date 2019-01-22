package com.tibco.xpd.processwidget.figures.anchors;

/**
 * AnchorDirectionHelper 
 * SID DI:24908
 * 
 * Helper class for deciding the best anchor point direction (i.e. side of object)
 * by comparing a reference point away from centre of object bounding rectangle.
 * 
 * (i.e. imagine drawing diagonal lines from centre of object bounding
 * 			to each corner, if reference point positioned in between the centre to top right angle and the
 * 			centre to bottom right angle then the direction is EAST and so on.
 * 
 * @author aallway
 *
 */

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;

public class AnchorDirectionHelper {

    /**
     * Returns the appropriate direction (and therefore side of object) that a
     * line going to the given reference point should enter / exit an object.
     * 
     * (i.e. imagine drawing diagonal lines from centre of object bounding to
     * each corner, if reference point positioned in between the centre to top
     * right angle and the centre to bottom right angle then the direction is
     * EAST and so on.
     * 
     * @param xpdRouterStyle
     *            The routing style in use
     * @param objRect
     *            Bounding rectangle for object
     * @param refPoint
     *            Reference point away from centre of object.
     * @return int PositionConstants
     */
    public static int getAnchorDirection(XpdFlowRoutingStyle xpdRouterStyle,
            Rectangle objRect, Point refPoint) {

        if (XpdFlowRoutingStyle.SingleEntryExit.equals(xpdRouterStyle)) {
            return singleOrMultiEntryGetAnchorDirection(objRect, refPoint);

        } else if (XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            return singleOrMultiEntryGetAnchorDirection(objRect, refPoint);
        }

        return originalGetAnchorDirection(objRect, refPoint);
    }

    /**
     * @param objRect
     * @param refPoint
     * @param direction
     * @return
     */
    private static int originalGetAnchorDirection(Rectangle objRect,
            Point refPoint) {
        int direction = PositionConstants.NONE;

        Point centre =
                new Point(objRect.x + (objRect.width / 2), objRect.y
                        + (objRect.height / 2));

        // Get the angle to each corner of rectangle
        double trAng = getAngle(centre, objRect.getTopRight());
        double brAng = getAngle(centre, objRect.getBottomRight());
        double blAng = getAngle(centre, objRect.getBottomLeft());
        double tlAng = getAngle(centre, objRect.getTopLeft());

        // Get angle to reference point...
        double refAng = getAngle(centre, refPoint);

        // Calculate direction by checking which sector the reference point
        // angle lies in
        if ((refAng >= trAng && refAng < 0) || // Between angle to top right
                                               // corner and direct right
                (refAng >= 0 && refAng <= brAng)) { // Between direct right
                                                    // and
                                                    // angle to bottom right
                                                    // corner
            direction = PositionConstants.EAST;
        } else if (refAng >= brAng && refAng <= blAng) { // Between angle to
                                                         // bottom right
                                                         // corner
                                                         // and angle to
                                                         // bottom
                                                         // left angle.
            direction = PositionConstants.SOUTH;
        } else if ((refAng >= blAng && refAng <= 180) || // Between angle to
                                                         // bottom left and
                                                         // direct left
                (refAng >= -180 && refAng <= tlAng)) { // Between (almost)
                                                       // direct left and
                                                       // angle
                                                       // to top left corner
            direction = PositionConstants.WEST;
        } else if (refAng >= tlAng && refAng <= trAng) { // Between angle to
                                                         // top
                                                         // left corner and
                                                         // angle to top
                                                         // right
                                                         // corner
            direction = PositionConstants.NORTH;
        }

        if (direction == PositionConstants.NONE) {
            // should NOT ever get here!!!
            direction = PositionConstants.SOUTH;
        }
        return direction;
    }

    /**
     * Get the default direction of line (NORTH/EAST/SOUTH/WEST) according to
     * the relative position of the 2 points (i.e. if pt2.x is right of pt1.x
     * return EAST).
     * 
     * SID DI:24908
     * 
     * @param xpdRouterStyle
     *            The routing style in use
     * @param pt1
     * @param pt2
     * @return int PositionConstant.NORTH/EAST/SOUTH/WEST
     */
    public static int getAnchorDirection(XpdFlowRoutingStyle xpdRouterStyle,
            Point pt1, Point pt2) {

        if (XpdFlowRoutingStyle.SingleEntryExit.equals(xpdRouterStyle)) {
            return singleEntryGetAnchorDirection(pt1, pt2);

        } else if (XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            return singleEntryGetAnchorDirection(pt1, pt2);
        }

        return originalGetAnchorDirection(pt1, pt2);

    }

    /**
     * original method
     * 
     * @param pt1
     * @param pt2
     * @return
     */
    private static int originalGetAnchorDirection(Point pt1, Point pt2) {
        int direction = PositionConstants.NONE;

        int dx = pt2.x - pt1.x;
        int dy = pt2.y - pt1.y;

        if (dx > 0) { // it's off to the right somewhere
            if (dy > 0) { // It's down somewhere
                if (dx > dy) { // It's further right than down
                    direction = PositionConstants.EAST;
                } else { // It's further down than right
                    direction = PositionConstants.SOUTH;
                }
            } else { // It's up somewhere.
                if (dx > -dy) { // It's further right than up
                    direction = PositionConstants.EAST;
                } else { // It's further up than right
                    direction = PositionConstants.NORTH;
                }
            }
        } else { // It's off to the left somewhere
            if (dy > 0) { // It's down somewhere
                if (-dx > dy) { // It's further left than down
                    direction = PositionConstants.WEST;
                } else { // It's further down than left
                    direction = PositionConstants.SOUTH;
                }
            } else { // It's up somewhere.
                if (-dx > -dy) { // It's further leftthan up
                    direction = PositionConstants.WEST;
                } else { // It's further up than right
                    direction = PositionConstants.NORTH;
                }
            }
        }

        return direction;
    }

    /**
     * Get angle of line between 2 points - as if pt1 is centre point Returns...
     * Direct right = 0.0, going anti clockwise to -179.99999999999999 (almost
     * direct left) going clockwise to 180.0 (direct left).
     * 
     * @param centre
     *            Centre reference point for angle.
     * @param pt2
     *            point to calc angle to.
     * 
     * @return double angle to line (see description above.
     */
    private static double getAngle(Point centre, Point pt2) {
        // Zero base the x and y (i.e. 2nd point relative to first)
        int x = pt2.x - centre.x;
        int y = pt2.y - centre.y;

        double rad = Math.atan2(y, x);

        return (Math.toDegrees(rad));
    }

    /**
     * Return direction favouring left->right layout.
     * 
     * @param objRect
     * @param refPoint
     * @param direction
     * @return
     */
    private static int singleOrMultiEntryGetAnchorDirection(Rectangle objRect,
            Point refPoint) {

        /*
         * Decide on trhe best side allowing for a margin between the objects
         * (to leave room for arrow heads etc)
         */
        if (refPoint.x >= (objRect.right() + 16)) {
            /* Ref is fully right of right edge of source + a margin. */
            return PositionConstants.EAST;
        } else if (refPoint.x <= (objRect.x - 16)) {
            /* Ref is fully left of left edge of source. */
            return PositionConstants.WEST;
        } else if (refPoint.y >= (objRect.bottom() + 16)) {
            /* Ref is fully under of bottom edge of source. */
            return PositionConstants.SOUTH;
        } else if (refPoint.y <= (objRect.y - 16)) {
            /* Ref is fully above of topedge of source. */
            return PositionConstants.NORTH;
        }

        /*
         * Reference point is within margin vertically and horizontally - so do
         * it again without the margin.
         */
        if (refPoint.x >= objRect.right()) {
            /* Ref is fully right of right edge of source + a margin. */
            return PositionConstants.EAST;
        } else if (refPoint.x <= objRect.x) {
            /* Ref is fully left of left edge of source. */
            return PositionConstants.WEST;
        } else if (refPoint.y >= objRect.bottom()) {
            /* Ref is fully under of bottom edge of source. */
            return PositionConstants.SOUTH;
        } else if (refPoint.y <= objRect.y) {
            /* Ref is fully above of topedge of source. */
            return PositionConstants.NORTH;
        }

        return PositionConstants.NONE;

    }

    /**
     * @param pt1
     * @param pt2
     * @return
     */
    private static int singleEntryGetAnchorDirection(Point pt1, Point pt2) {
        /* Return direction favouring left->right layout. */
        if (pt2.x >= pt1.x) {
            return PositionConstants.EAST;
        } else if (pt2.x <= pt1.x) {
            return PositionConstants.WEST;

        } else if (pt2.y >= pt1.y) {
            return PositionConstants.SOUTH;
        } else if (pt2.y <= pt1.y) {
            return PositionConstants.NORTH;
        }

        return PositionConstants.NONE;
    }

}
