/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;

import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.figures.EventFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Flow connection anchor for event activities.
 * 
 * @author aallway
 * @since 8 Mar 2012
 */
public class EventActivityAnchor extends AbstractActivityAnchor {
    private EventFigure ownerShapesParentEventFigure;

    /**
     * @param parent
     */
    public EventActivityAnchor(EventFigure ownerShapesParentEventFigure,
            boolean isSource) {
        super(ownerShapesParentEventFigure.getShape(), isSource);
        this.ownerShapesParentEventFigure = ownerShapesParentEventFigure;
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

        IFigure eventFigure = getOwner();
        Rectangle ellispeBounds = eventFigure.getBounds().getCopy();
        eventFigure.translateToAbsolute(ellispeBounds);

        if (PositionConstants.WEST == direction) {
            if (absolute_xORy != west.y) {
                /*
                 * Get first intersection point with the event's outer ellipse
                 * and a line drawn across it at the vertical location
                 * identified by absolute_xORy
                 */
                LineSeg ls =
                        new LineSeg(new Point(bnds.x - 1, absolute_xORy),
                                new Point(bnds.right() + 1, absolute_xORy));

                PointList intersectionsWithEllipse =
                        ls.getLineIntersectionsWithEllipse(ellispeBounds);

                if (intersectionsWithEllipse != null
                        && intersectionsWithEllipse.size() > 0) {

                    Point furthestWest = null;
                    for (int i = 0; i < intersectionsWithEllipse.size(); i++) {
                        if (furthestWest == null) {
                            furthestWest = intersectionsWithEllipse.getPoint(i);
                        } else {
                            Point pt = intersectionsWithEllipse.getPoint(i);
                            if (pt.x < furthestWest.x) {
                                furthestWest = pt;
                            }
                        }
                    }
                    return furthestWest;
                }

            }
            /*
             * Dead level with centre of west side (or couldn't figure it out),
             * just use the centre location we already have.
             */
            return west;

        } else if (PositionConstants.EAST == direction) {
            if (absolute_xORy != east.y) {
                /*
                 * Get first intersection point with the event's outer ellipse
                 * and a line drawn across it at the vertical location
                 * identified by absolute_xORy
                 */
                LineSeg ls =
                        new LineSeg(new Point(bnds.right() + 1, absolute_xORy),
                                new Point(bnds.x - 1, absolute_xORy));

                PointList intersectionsWithEllipse =
                        ls.getLineIntersectionsWithEllipse(ellispeBounds);
                if (intersectionsWithEllipse != null
                        && intersectionsWithEllipse.size() > 0) {

                    Point furthestEast = null;
                    for (int i = 0; i < intersectionsWithEllipse.size(); i++) {
                        if (furthestEast == null) {
                            furthestEast = intersectionsWithEllipse.getPoint(i);
                        } else {
                            Point pt = intersectionsWithEllipse.getPoint(i);
                            if (pt.x > furthestEast.x) {
                                furthestEast = pt;
                            }
                        }
                    }
                    return furthestEast;
                }

            }
            /*
             * Dead level with centre of east side (or couldn't figure it out),
             * just use the centre location we already have.
             */
            return east;

        } else if (PositionConstants.NORTH == direction) {
            if (absolute_xORy != north.x) {
                /*
                 * Get first intersection point with the event's outer ellipse
                 * and a line drawn up thru it at the horizontal location
                 * identified by absolute_xORy
                 */
                LineSeg ls =
                        new LineSeg(new Point(absolute_xORy, bnds.y - 1),
                                new Point(absolute_xORy, bnds.bottom() + 1));

                PointList intersectionsWithEllipse =
                        ls.getLineIntersectionsWithEllipse(ellispeBounds);

                if (intersectionsWithEllipse != null
                        && intersectionsWithEllipse.size() > 0) {
                    Point furthestNorth = null;
                    for (int i = 0; i < intersectionsWithEllipse.size(); i++) {
                        if (furthestNorth == null) {
                            furthestNorth =
                                    intersectionsWithEllipse.getPoint(i);
                        } else {
                            Point pt = intersectionsWithEllipse.getPoint(i);
                            if (pt.y < furthestNorth.y) {
                                furthestNorth = pt;
                            }
                        }
                    }
                    return furthestNorth;
                }

            }
            /*
             * Dead level with centre of north side (or couldn't figure it out),
             * just use the centre location we already have.
             */
            return north;

        } else if (PositionConstants.SOUTH == direction) {
            if (absolute_xORy != south.x) {
                /*
                 * Get first intersection point with the event's outer ellipse
                 * and a line drawn up thru it at the horizontal location
                 * identified by absolute_xORy
                 */
                LineSeg ls =
                        new LineSeg(
                                new Point(absolute_xORy, bnds.bottom() + 1),
                                new Point(absolute_xORy, bnds.y - 1));

                PointList intersectionsWithEllipse =
                        ls.getLineIntersectionsWithEllipse(ellispeBounds);

                if (intersectionsWithEllipse != null
                        && intersectionsWithEllipse.size() > 0) {
                    Point furthestSouth = null;
                    for (int i = 0; i < intersectionsWithEllipse.size(); i++) {
                        if (furthestSouth == null) {
                            furthestSouth =
                                    intersectionsWithEllipse.getPoint(i);
                        } else {
                            Point pt = intersectionsWithEllipse.getPoint(i);
                            if (pt.y > furthestSouth.y) {
                                furthestSouth = pt;
                            }
                        }
                    }
                    return furthestSouth;
                }

            }
            /*
             * Dead level with centre of south side (or couldn't figure it out),
             * just use the centre location we already have.
             */
            return south;
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

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#calculateAnchorDirection(org.eclipse.draw2d.geometry.Rectangle,
     *      org.eclipse.draw2d.geometry.Rectangle)
     * 
     * @param ownerAbsoluteBounds
     * @param refRect
     * @return
     */
    @Override
    protected int calculateAnchorDirection(Rectangle ownerAbsoluteBounds,
            Rectangle refRect) {

        if (ownerShapesParentEventFigure.isAttachedToTask()) {
            /*
             * If this event is attached to task then we need to hard-code the
             * direction to
             * "opposite of the side of the task figure that this event is attached to."
             */
            int openForConnection =
                    calculateEventSidesOpenForConnection(ownerAbsoluteBounds);

            /*
             * As normal flow gives preference to left->right flow, it is better
             * to give attached events preference to up->down flow.
             */
            if ((openForConnection & PositionConstants.SOUTH) != 0) {
                if (refRect.y > ownerAbsoluteBounds.bottom()) {
                    return PositionConstants.SOUTH;
                }
            }

            if ((openForConnection & PositionConstants.NORTH) != 0) {
                if (refRect.bottom() < ownerAbsoluteBounds.y) {
                    return PositionConstants.NORTH;
                }
            }

            if ((openForConnection & PositionConstants.EAST) != 0) {
                if (refRect.x > ownerAbsoluteBounds.right()) {
                    return PositionConstants.EAST;
                }
            }

            if ((openForConnection & PositionConstants.WEST) != 0) {
                if (refRect.right() < ownerAbsoluteBounds.x) {
                    return PositionConstants.WEST;
                }
            }

            /* If all else fails, just use the default. */
        }

        return super.calculateAnchorDirection(ownerAbsoluteBounds, refRect);
    }

    /**
     * @see com.tibco.xpd.processwidget.figures.anchors.AbstractActivityAnchor#calculateAnchorDirection(org.eclipse.draw2d.geometry.Rectangle,
     *      org.eclipse.draw2d.geometry.Point)
     * 
     * @param ownerAbsoluteBounds
     * @param refPoint
     * @return
     */
    @Override
    protected int calculateAnchorDirection(Rectangle ownerAbsoluteBounds,
            Point refPoint) {
        XpdFlowRoutingStyle xpdRouterStyle =
                XPDFigureUtilities.getXpdRouterStyle(getOwner());

        if (ownerShapesParentEventFigure.isAttachedToTask()) {
            /*
             * If this event is attached to task then we need to hard-code the
             * direction to
             * "opposite of the side of the task figure that this event is attached to."
             */
            int openForConnection =
                    calculateEventSidesOpenForConnection(ownerAbsoluteBounds);

            /*
             * As normal flow gives preference to left->right flow, it is better
             * to give attached events preference to up->down flow.
             */
            if ((openForConnection & PositionConstants.SOUTH) != 0) {
                if (refPoint.y > ownerAbsoluteBounds.bottom()) {
                    return PositionConstants.SOUTH;
                }
            }

            if ((openForConnection & PositionConstants.NORTH) != 0) {
                if (refPoint.y < ownerAbsoluteBounds.y) {
                    return PositionConstants.NORTH;
                }
            }

            if ((openForConnection & PositionConstants.EAST) != 0) {
                if (refPoint.x > ownerAbsoluteBounds.right()) {
                    return PositionConstants.EAST;
                }
            }

            if ((openForConnection & PositionConstants.WEST) != 0) {
                if (refPoint.x < ownerAbsoluteBounds.x) {
                    return PositionConstants.WEST;
                }
            }

            /* If all else fails, just use the default. */
        }

        return super.calculateAnchorDirection(ownerAbsoluteBounds, refPoint);
    }

    /**
     * calculate Which sides of the event are 'open for connection' i.e. not
     * overlapping the task.
     * 
     * @param ownerAbsoluteBounds
     * @return Combination of {@link PositionConstants}.NORTH|EAST|SOUTH|WEST
     *         with the possible sides of events that could be used to link to.
     */
    protected int calculateEventSidesOpenForConnection(
            Rectangle ownerAbsoluteBounds) {
        TaskFigure taskFigure =
                ownerShapesParentEventFigure.getAttachedToTaskFigure();
        Rectangle taskBounds = taskFigure.getBounds().getCopy();
        taskFigure.translateToAbsolute(taskBounds);

        /*  */
        int openForConnection = 0;

        if (ownerAbsoluteBounds.right() > taskBounds.right()) {
            openForConnection |= PositionConstants.EAST;
        }

        if (ownerAbsoluteBounds.x < taskBounds.x) {
            openForConnection |= PositionConstants.WEST;
        }

        if (ownerAbsoluteBounds.bottom() > taskBounds.bottom()) {
            openForConnection |= PositionConstants.SOUTH;
        }

        if (ownerAbsoluteBounds.y < taskBounds.y) {
            openForConnection |= PositionConstants.NORTH;
        }
        return openForConnection;
    }
}
