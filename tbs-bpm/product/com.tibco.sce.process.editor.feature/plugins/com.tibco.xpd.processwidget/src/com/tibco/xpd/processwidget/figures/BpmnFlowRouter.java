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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PrecisionPointList;

import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor;
import com.tibco.xpd.processwidget.figures.anchors.AnchorDirectionHelper;
import com.tibco.xpd.processwidget.figures.anchors.ConnectionAnchorDirection;
import com.tibco.xpd.processwidget.figures.anchors.ExtendedConnectionAnchor;
import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;
import com.tibco.xpd.processwidget.figures.anchors.NESWAnchor;

/**
 * @author wzurek
 */
public class BpmnFlowRouter extends AbstractRouter {

    private static final int SELF_CONN_MARGIN = 20;

    Map constraints = new HashMap();

    @Override
    public Object getConstraint(Connection connection) {
        return constraints.get(connection);
    }

    @Override
    public void setConstraint(Connection connection, Object constraint) {
        if (constraint == null)
            constraints.remove(connection);
        else
            constraints.put(connection, constraint);
    }

    @Override
    public void route(Connection conn) {
        /*
         * SID XPD-3811 - Use PrecisionPoints to prevent rounding errors.
         * Otherwise sometimes the anchor-points can be treated differently when
         * simply copied from precision point to normal point (this can cause
         * layout to keep recursing because the point calculated from anchor
         * location rounded differently that here.
         */
        PrecisionPointList precisePoints = new PrecisionPointList();

        PointList connectionPoints = conn.getPoints();
        connectionPoints.removeAllPoints();

        List bendPoints = (List) getConstraint(conn);

        //
        // SID DI:24098 Attempt to solve arrows pointing in wrong direction
        // entering top of gateway / event.
        // Which is basically caused by the fact that we don't take into account
        // the direction
        // line should be exiting / entering the source / target objects (so
        // when we insert
        // a right angle between two nominally diagonal points we don't always
        // choose the direction
        // according to the exit / entry direction.
        //
        ConnectionAnchor srcAnchor = conn.getSourceAnchor();
        ConnectionAnchor tgtAnchor = conn.getTargetAnchor();

        Dimension srcRoutingOffset = null;
        if (srcAnchor instanceof AbstractActivityAnchor) {
            srcRoutingOffset =
                    ((AbstractActivityAnchor) srcAnchor).getRoutingOffset();
        }

        Dimension tgtRoutingOffset = null;
        if (tgtAnchor instanceof AbstractActivityAnchor) {
            tgtRoutingOffset =
                    ((AbstractActivityAnchor) tgtAnchor).getRoutingOffset();
        }

        int srcDirection = PositionConstants.NONE;
        int tgtDirection = PositionConstants.NONE;

        IFigure srcFigure = srcAnchor.getOwner();
        IFigure tgtFigure = tgtAnchor.getOwner();

        XpdFlowRoutingStyle xpdRouterStyle = XpdFlowRoutingStyle.MultiEntryExit;
        if (srcFigure != null) {
            xpdRouterStyle = XPDFigureUtilities.getXpdRouterStyle(srcFigure);
        }

        Point start;
        Point end;
        if (bendPoints == null || bendPoints.isEmpty()) {
            if (srcFigure == tgtFigure) {
                // the same start and end

                // SID ZOOM - Make sure that SELF_CONN_MARGIN gets scaled too
                // (translateToAbsolute() will scale for zoom level)
                Rectangle relBnds = srcFigure.getBounds();

                // Source anchor reference point (add margin before translate so
                // it gets scaled too).
                Point saRef =
                        new Point(relBnds.x + relBnds.width + SELF_CONN_MARGIN,
                                relBnds.y + (relBnds.height / 2));
                srcFigure.translateToAbsolute(saRef);

                Point taRef =
                        new Point(relBnds.x + (relBnds.width / 2), relBnds.y
                                - SELF_CONN_MARGIN);
                srcFigure.translateToAbsolute(taRef);

                Point startPoint = srcAnchor.getLocation(saRef).getCopy();
                Point endPoint = tgtAnchor.getLocation(taRef).getCopy();

                precisePoints.addPoint(startPoint);
                precisePoints.addPoint(saRef.x, startPoint.y);
                precisePoints.addPoint(saRef.x, taRef.y);
                precisePoints.addPoint(endPoint.x, taRef.y);

                precisePoints.addPoint(endPoint);

            } else {

                start = getStartPoint(conn);
                end = getEndPoint(conn, start);

                //
                // SID DI:24098 Attempt to solve arrows pointing in wrong
                // direction entering top of gateway / event.
                // Get the direction of the src of the line.
                precisePoints.addPoint(start);
                precisePoints.addPoint(end);

                if (start.x != end.x && start.y != end.y) {

                    // All we have to do is calculate the directions,
                    // fixPointList will resolve the rest later.
                    if (srcAnchor instanceof LineAnchor) {
                        // For line anchor's get direction from fixed anchor pos
                        // to final destination.
                        srcDirection =
                                ((LineAnchor) srcAnchor)
                                        .getConnectionAnchorDirection(end);

                    } else if (srcAnchor instanceof ConnectionAnchorDirection) {
                        srcDirection =
                                ((ConnectionAnchorDirection) srcAnchor)
                                        .getConnectionAnchorDirection(start);
                    }

                    if (tgtAnchor instanceof LineAnchor) {
                        // For line anchor's get direction from fixed anchor pos
                        // to original start point.
                        tgtDirection =
                                ((LineAnchor) tgtAnchor)
                                        .getConnectionAnchorDirection(start);

                    } else if (tgtAnchor instanceof ConnectionAnchorDirection) {
                        tgtDirection =
                                ((ConnectionAnchorDirection) tgtAnchor)
                                        .getConnectionAnchorDirection(end);
                    }
                }

            }
        } else {
            Point fp = ((Bendpoint) bendPoints.get(0)).getLocation().getCopy();

            Point lp =
                    ((Bendpoint) bendPoints.get(bendPoints.size() - 1))
                            .getLocation().getCopy();

            Point rp = fp.getCopy();
            conn.translateToAbsolute(fp);

            conn.translateToAbsolute(lp);
            start = srcAnchor.getLocation(fp).getCopy();
            end = tgtAnchor.getLocation(lp).getCopy();

            // SID DI:24908 - get src/target line direction before converting
            // start/end to relative.
            // All we have to do is calculate the directions,
            // fixPointList will resolve the rest later.
            if (srcAnchor instanceof LineAnchor) {
                // For line anchor's get direction from fixed anchor pos
                // to initial point on line .
                srcDirection =
                        ((LineAnchor) srcAnchor)
                                .getConnectionAnchorDirection(fp);
            } else if (srcAnchor instanceof ConnectionAnchorDirection) {
                srcDirection =
                        ((ConnectionAnchorDirection) srcAnchor)
                                .getConnectionAnchorDirection(start);
            }

            if (tgtAnchor instanceof LineAnchor) {
                // For line anchor's get direction from fixed anchor pos
                // to last point before end.
                tgtDirection =
                        ((LineAnchor) tgtAnchor)
                                .getConnectionAnchorDirection(lp);
            } else if (tgtAnchor instanceof ConnectionAnchorDirection) {
                tgtDirection =
                        ((ConnectionAnchorDirection) tgtAnchor)
                                .getConnectionAnchorDirection(end);
            }

            precisePoints.addPoint(start);
            for (Iterator iter = bendPoints.iterator(); iter.hasNext();) {
                Bendpoint pnt = (Bendpoint) iter.next();

                Point newP = pnt.getLocation().getCopy();
                conn.translateToAbsolute(newP);
                precisePoints.addPoint(newP);
            }
            precisePoints.addPoint(end);

        }

        // Convert all points from absolute to relative to connection.
        for (int i = 0; i < precisePoints.size(); i++) {
            Point tp = precisePoints.getPoint(i).getCopy();
            conn.translateToRelative(tp);

            connectionPoints.addPoint(tp);
        }

        // SID DI:24908 - pass src/tgt direction for less arbitrary routing of
        // lines.
        fixPointList(connectionPoints,
                srcDirection,
                tgtDirection,
                xpdRouterStyle,
                srcRoutingOffset,
                tgtRoutingOffset);

        conn.setPoints(connectionPoints);

        return;
    }

    /**
     * Fix the given point list to insert necessary extra points for appropriate
     * right angles according to relative src / bendpoint / target locations.
     * 
     * SID DI:24908 - pass src/tgt direction for less arbitrary routing of
     * lines.
     * 
     * @param p
     * @param srcDirection
     * @param tgtDirection
     * @param xpdRouterStyle
     * @param tgtRoutingOffset
     * @param srcRoutingOffset
     */
    private void fixPointList(PointList p, int srcDirection, int tgtDirection,
            XpdFlowRoutingStyle xpdRouterStyle, Dimension srcRoutingOffset,
            Dimension tgtRoutingOffset) {
        int index = 0;
        while ((index + 1) < p.size()) {
            // First sort out the direction of line from source point to target
            // point.
            // i.e. if this is the first point then srcDirection is direction
            // from src anchor
            int tmpSrcDir = PositionConstants.NONE;
            int tmpTgtDir = PositionConstants.NONE;

            if (index == 0) {
                tmpSrcDir = srcDirection;
            }
            if ((index + 1) == (p.size() - 1)) {
                tmpTgtDir = tgtDirection;
            }

            int numpts =
                    insertRouteBetweenPoints(p,
                            index,
                            tmpSrcDir,
                            tmpTgtDir,
                            xpdRouterStyle,
                            srcRoutingOffset,
                            tgtRoutingOffset);

            // Skip to the next point (and passed the extra inserted points.
            index += 1 + numpts;

        }
    }

    /**
     * This variable is a helper for insertRouteBetweenPoints() It stores the
     * target direction into previous (bend) point on transition line chosen by
     * the method. Nominally, source and target objects for transition have exit
     * / entry direcitons for the routing line. However, bend points do not so
     * we have to try and choose the best we can on the fly. One method of doing
     * this is to 'prefer' to exit a bendpoint in the opposite direction than
     * entry (i.e. go straight thru if possible).
     * 
     * SID DI:24908
     */
    private int prevTgtRouteDirection = PositionConstants.NONE;

    /**
     * Insert extra points required between 2 points in order to ensure that
     * manhattan style routing (vert/horiz lines only) is maintained. May return
     * empty list if no points required.
     * 
     * SID DI:24908
     * 
     * @param points
     *            points.
     * @param index
     *            index of first of 2 points to compare.
     * @param srcDirection
     *            direction of line from first point.
     * @param tgtDirection
     *            direction of line into second point.
     * @param finalTgtDirection
     *            direction into final target
     * @param xpdRouterStyle
     *            routing style in use by this process.
     * @param tgtRoutingOffset
     * @param srcRoutingOffset
     * 
     * @return int Number of extra points inserted.
     */
    private int insertRouteBetweenPoints(PointList points, int index,
            int srcDirection, int tgtDirection,
            XpdFlowRoutingStyle xpdRouterStyle, Dimension srcRoutingOffset,
            Dimension tgtRoutingOffset) {

        int numPts = 0; // For return of number of points this method has
        // inserted.

        // If doing first point on line, reset the stored previous target
        // direction.
        if (index == 0) {
            prevTgtRouteDirection = PositionConstants.NONE;
        }

        Point pt1 = points.getPoint(index).getCopy();
        Point pt2 = points.getPoint(index + 1).getCopy();

        // We may need to read onto the next point in some special cases.

        boolean p1IsFirst = (index == 0);

        Point ptAfter = null;
        boolean pt2IsLast = true;

        if ((index + 2) < points.size()) {
            ptAfter = points.getPoint(index + 2).getCopy();
            pt2IsLast = false;
        }

        int pt2SrcToNextDirection = PositionConstants.NONE;
        if (ptAfter != null) {
            pt2SrcToNextDirection =
                    AnchorDirectionHelper.getAnchorDirection(xpdRouterStyle,
                            pt2,
                            ptAfter);
        }

        int localSrcDirection = srcDirection;
        int localTgtDirection = tgtDirection;

        // If we don't have any idea of src line direction then base it on
        // relative position of 2 points.
        if (localSrcDirection == PositionConstants.NONE) {
            localSrcDirection =
                    AnchorDirectionHelper.getAnchorDirection(xpdRouterStyle,
                            pt1,
                            pt2);
        }

        if (localTgtDirection == PositionConstants.NONE) {
            localTgtDirection =
                    AnchorDirectionHelper.getAnchorDirection(xpdRouterStyle,
                            pt2,
                            pt1);
        }

        // If after all else we accidentally end up with anchor directions in
        // opposite directions then set to some sensible default. This can
        // happen when an anchor's reference point works out to be the same as
        // it's location.
        if (oppositeDirections(localSrcDirection, localTgtDirection)) {
            localSrcDirection =
                    AnchorDirectionHelper.getAnchorDirection(xpdRouterStyle,
                            pt1,
                            pt2);
            localTgtDirection =
                    AnchorDirectionHelper.getAnchorDirection(xpdRouterStyle,
                            pt2,
                            pt1);
        }

        // The 1 or 2 extra points we may insert in the point list.
        Point extPt1 = null;
        Point extPt2 = null;

        // First up - if the line is directly horizontal or vertical then
        // there's nothing to do.
        if (pt1.x != pt2.x && pt1.y != pt2.y) {

            // If the original target direction (into pt2) was NONE (i.e. it was
            // a bendpoint) and not end of line
            // then check for some special cases to stop certain wierdies
            // happening...
            if (!pt2IsLast && tgtDirection == PositionConstants.NONE) {

                // Like for instance if entry (target) direction into this
                // bendpoint and the exit of the line
                // in sequence will be the same (i.e. line doubles back on
                // itself.
                if (localTgtDirection == pt2SrcToNextDirection) {
                    if (localTgtDirection == PositionConstants.SOUTH
                            || localTgtDirection == PositionConstants.NORTH) {
                        // Line goes into and out of bottom or into and out of
                        // top...
                        if (pt1.x < pt2.x) {
                            // point before bendpoint is left of bendpoint, make
                            // target direction for bendpoint west
                            localTgtDirection = PositionConstants.WEST;
                        } else {
                            // point before bendpoint is right of bendpoint,
                            // make target direction for bendpoint east
                            localTgtDirection = PositionConstants.EAST;
                        }
                    } else {
                        // Line goes into and out of left or into and out of
                        // right...
                        if (pt1.y < pt2.y) {
                            // point before bendpoint is above bendpoint, make
                            // target direction for bendpoint north
                            localTgtDirection = PositionConstants.NORTH;
                        } else {
                            // point before bendpoint is below bendpoint, make
                            // target direction for bendpoint SOUTH
                            localTgtDirection = PositionConstants.SOUTH;
                        }
                    }
                }
                // Or, if possible make the target direction to bendpoint
                // opposite of it's proposed dest direction.
                else {
                    switch (pt2SrcToNextDirection) {
                    case PositionConstants.NORTH:
                        // Make entry direction to bendpoint south (provided
                        // that is above initial position.
                        if (pt2.y < pt1.y) {
                            localTgtDirection = PositionConstants.SOUTH;
                        }
                        break;

                    case PositionConstants.SOUTH:
                        // Make entry direction to bendpoint north(provided that
                        // is below initial position.
                        if (pt2.y > pt1.y) {
                            localTgtDirection = PositionConstants.NORTH;
                        }
                        break;

                    case PositionConstants.WEST:
                        // Make entry direction to bendpoint EAST (provided that
                        // is left initial position.
                        if (pt2.x < pt1.x) {
                            localTgtDirection = PositionConstants.EAST;
                        }
                        break;

                    case PositionConstants.EAST:
                        // Make entry direction to bendpoint WEST (provided that
                        // is right of initial position.
                        if (pt2.x > pt1.x) {
                            localTgtDirection = PositionConstants.WEST;
                        }
                        break;
                    }

                }

            }

            // Factor in a preference (if possible) to exit bendpoints in the
            // opposite direction that we entered them...
            if (srcDirection == PositionConstants.NONE) {
                // OK, we were not given a source direction so this is likely to
                // be a bendpoint
                // so we can try to do some magic based on the previous target
                // direction (i.e. the direction
                // that we entered the point that in this call is now the
                // source).
                // If possible we will make it opposite so that the line passes
                // straight thru.

                if (prevTgtRouteDirection != PositionConstants.NONE) {
                    // Make sure we have a previous - we WON'T if this is an
                    // unanchored start point.
                    switch (prevTgtRouteDirection) {
                    case PositionConstants.WEST:
                        // Line entered the left hand side of point, exit the
                        // right hand side but only if the target point is right
                        // of source
                        if (pt2.x > pt1.x) {
                            localSrcDirection = PositionConstants.EAST;
                        } else if (pt2.y < pt1.y) {
                            localSrcDirection = PositionConstants.NORTH;
                        } else if (pt2.y > pt1.y) {
                            localSrcDirection = PositionConstants.SOUTH;
                        }
                        break;

                    case PositionConstants.EAST:
                        // Line entered the right hand side of point, exit the
                        // left hand side but only if the target point is left
                        // of source
                        if (pt2.x < pt1.x) {
                            localSrcDirection = PositionConstants.WEST;
                        } else if (pt2.y < pt1.y) {
                            localSrcDirection = PositionConstants.NORTH;
                        } else if (pt2.y > pt1.y) {
                            localSrcDirection = PositionConstants.SOUTH;
                        }

                        break;

                    case PositionConstants.NORTH:
                        // Line entered the top side of point, exit the bottom
                        // side but only if the target point is right of source
                        if (pt2.y > pt1.y) {
                            localSrcDirection = PositionConstants.SOUTH;
                        } else if (pt2.x < pt1.x) {
                            localSrcDirection = PositionConstants.WEST;
                        } else if (pt2.x > pt1.x) {
                            localSrcDirection = PositionConstants.EAST;
                        }
                        break;

                    case PositionConstants.SOUTH:
                        // Line entered the bottom side of point, exit the top
                        // side but only if the target point is left of source
                        if (pt2.y < pt1.y) {
                            localSrcDirection = PositionConstants.NORTH;
                        } else if (pt2.x < pt1.x) {
                            localSrcDirection = PositionConstants.WEST;
                        } else if (pt2.x > pt1.x) {
                            localSrcDirection = PositionConstants.EAST;
                        }
                        break;

                    }
                }
            }

            // If Line should come out from right of object
            if (localSrcDirection == PositionConstants.EAST) {
                // If target direction is into left (dir = WEST) then step down
                // half way along... __|~~
                if (localTgtDirection == PositionConstants.WEST) {
                    int x =
                            pt1.x
                                    + (int) Math
                                            .floor(((float) (pt2.x - pt1.x)) / 2);

                    extPt1 = new Point(x, pt1.y);
                    extPt2 = new Point(x, pt2.y);
                }
                // If target direction is into top or bottom, then just a single
                // right angle is ok... __|
                else if (localTgtDirection == PositionConstants.NORTH
                        || localTgtDirection == PositionConstants.SOUTH) {
                    extPt1 = new Point(pt2.x, pt1.y);
                }
            }
            // If line should come out of left...
            else if (localSrcDirection == PositionConstants.WEST) {
                // If target direction is into right (dir = EAST) then step down
                // half way along... __|~~
                if (localTgtDirection == PositionConstants.EAST) {
                    int x = pt1.x - Math.round((((float) (pt1.x - pt2.x)) / 2));
                    extPt1 = new Point(x, pt1.y);
                    extPt2 = new Point(x, pt2.y);
                }
                // If target direction is into top or bottom, then just a single
                // right angle is ok... __|
                else if (localTgtDirection == PositionConstants.NORTH
                        || localTgtDirection == PositionConstants.SOUTH) {
                    extPt1 = new Point(pt2.x, pt1.y);
                }
            }
            // If line should come out of bottom...
            else if (localSrcDirection == PositionConstants.SOUTH) {
                // If target direction is into top (dir = NORTH) then step down
                // half way along...
                if (localTgtDirection == PositionConstants.NORTH) {
                    int y =
                            pt1.y
                                    + (int) Math
                                            .floor(((float) (pt2.y - pt1.y)) / 2);
                    extPt1 = new Point(pt1.x, y);
                    extPt2 = new Point(pt2.x, y);
                }
                // If target direction is into left or right, then just a single
                // right angle is ok... __|
                else if (localTgtDirection == PositionConstants.EAST
                        || localTgtDirection == PositionConstants.WEST) {
                    extPt1 = new Point(pt1.x, pt2.y);
                }
            }
            // If line should come out of top...
            else if (localSrcDirection == PositionConstants.NORTH) {
                // If target direction is into bottom (dir = SOUTH) then step
                // down half way along...
                if (localTgtDirection == PositionConstants.SOUTH) {
                    int y = pt1.y - Math.round(((float) (pt1.y - pt2.y)) / 2);
                    extPt1 = new Point(pt1.x, y);
                    extPt2 = new Point(pt2.x, y);
                }
                // If target direction is into left or right, then just a single
                // right angle is ok... __|
                else if (localTgtDirection == PositionConstants.EAST
                        || localTgtDirection == PositionConstants.WEST) {
                    extPt1 = new Point(pt1.x, pt2.y);
                }
            }
        }

        /*
         * If ProcessConnectionLayer has setup routing offsets for multiple
         * conneciton target in same zone with same reference point then adjust
         * where we bend line it has calculated.
         */
        if (XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            /*
             * Only apply offsets when final direction is east<->west or
             * north<->south, (i.e. we have a 'Z' shape line rather than an L
             * shape line).
             * 
             * We know this to be the case if we have 2 extra points rather than
             * just one.
             */
            if (extPt1 != null && extPt2 != null) {
                if (p1IsFirst && srcRoutingOffset != null) {
                    extPt1.x += srcRoutingOffset.width;
                    extPt1.y += srcRoutingOffset.height;
                    extPt2.x += srcRoutingOffset.width;
                    extPt2.y += srcRoutingOffset.height;
                }

                if (pt2IsLast && tgtRoutingOffset != null) {
                    extPt1.x += tgtRoutingOffset.width;
                    extPt1.y += tgtRoutingOffset.height;
                    extPt2.x += tgtRoutingOffset.width;
                    extPt2.y += tgtRoutingOffset.height;
                }
            }
        }

        if (extPt1 != null) {
            points.insertPoint(extPt1, index + 1);
            index++;
            numPts++;
        }

        if (extPt2 != null) {
            points.insertPoint(extPt2, index + 1);
            index++;
            numPts++;
        }

        // After all our firtalling, save the target direction for next time
        // into this method.
        prevTgtRouteDirection = localTgtDirection;

        return (numPts);

    }

    @Override
    protected Point getStartPoint(Connection conn) {
        Point point;
        ConnectionAnchor a = conn.getSourceAnchor();
        if (a instanceof ExtendedConnectionAnchor
                && conn.getTargetAnchor() instanceof ExtendedConnectionAnchor) {

            // Activity to Activity.

            IFigure target = conn.getTargetAnchor().getOwner();
            if (target != null) {
                Rectangle bnds = target.getBounds().getCopy();
                target.translateToAbsolute(bnds);
                point = ((ExtendedConnectionAnchor) a).getLocation(bnds);
            } else {
                point = super.getStartPoint(conn);
            }

        }
        // Different behaviour depending on target.
        else if (conn.getTargetAnchor() instanceof NESWAnchor) {

            // SID DI:24908 = Gateway/Event to Gateway/Event
            // To gateway or event with fixed anchor point positions.
            // So base source location on centgre of destination
            // (used to base source anchor position on destination anchor
            // position
            // but don't know the dest anchor position until have the source
            // anchor pos
            // so got a bit chicken and egg and very confused)!.
            Point ref = conn.getTargetAnchor().getReferencePoint();
            point = a.getLocation(ref);
        } else if (conn.getTargetAnchor() instanceof ExtendedConnectionAnchor) {
            // To activity (or some other thing whose anchor point slides along
            // the side.
            Point ref = conn.getSourceAnchor().getReferencePoint();
            ref = conn.getTargetAnchor().getLocation(ref);
            point = a.getLocation(ref);
        }

        else {
            point = super.getStartPoint(conn);
        }

        return point;
    }

    protected Point getEndPoint(Connection conn, Point startAnchorPos) {
        //
        // SID DI:24908 - Start and end points are (sometimes) dependent on the
        // direction
        // (i.e. the end point depends on the location direction of the original
        // start point)
        //
        // This method used to have special cases for Activity to Activity and
        // so on.
        // It then in some cases tried to get the start anchor location an it
        // would come out different
        // than getStartPoint()'s calculation so things would get confused.
        //
        // So to ensure we are talking on even grounds, we now make sure we base
        // the end point on the start point.

        Point point = conn.getTargetAnchor().getLocation(startAnchorPos);

        return point;
    }

    private boolean oppositeDirections(int srcDirection, int tgtDirection) {
        boolean ret = false;
        if (srcDirection == PositionConstants.NORTH
                && tgtDirection == PositionConstants.NORTH) {
            ret = true;
        } else if (srcDirection == PositionConstants.SOUTH
                && tgtDirection == PositionConstants.SOUTH) {
            ret = true;
        } else if (srcDirection == PositionConstants.WEST
                && tgtDirection == PositionConstants.WEST) {
            ret = true;
        } else if (srcDirection == PositionConstants.EAST
                && tgtDirection == PositionConstants.EAST) {
            ret = true;
        }

        return ret;
    }

}
