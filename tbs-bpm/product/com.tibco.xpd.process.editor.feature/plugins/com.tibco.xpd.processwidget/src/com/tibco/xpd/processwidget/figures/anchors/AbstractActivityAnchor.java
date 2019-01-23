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

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.handles.HandleBounds;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.figures.ProcessConnectionLayer;
import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Connection anchor for flow (sequence/message) connections to/from activities
 * (task, event, gateway)
 * 
 * 
 * @author aallway
 * @since 9 Mar 2012 (Major update to support different routing styles)
 */
public abstract class AbstractActivityAnchor extends AbstractConnectionAnchor
        implements ExtendedConnectionAnchor, ConnectionAnchorDirection {

    private int lastCalculatedDirection = -1;

    private boolean usingOldSlideAlongSideMethod = false;

    private Point lastReturnedLocation = null;

    private Point presetMultiEntryAnchorLocation = null;

    private Dimension routingOffset = null;

    private boolean isSource;

    /**
     * Whether to revert to allowing connection anchor to be placed at any
     * position (most appropriate for the reference point) along the side of the
     * activity - this generally looks better for large objects such as embeded
     * sub-process.
     */
    public static boolean ALLOW_NON_CENTRE_FOR_LARGE_OBJECTS = true;

    /**
     * How wide an object can be before we revert to the old method of allowing
     * connection to slide anywhere along the side of activity (if
     * {@link #ALLOW_NON_CENTRE_FOR_LARGE_OBJECTS} is true )
     */
    public static int LARGE_OBJECT_WIDTH_THRESHOLD =
            ProcessWidgetConstants.TASK_WIDTH_SIZE * 3;

    /**
     * How high an object can be before we revert to the old method of allowing
     * connection to slide anywhere along the side of activity (if
     * {@link #ALLOW_NON_CENTRE_FOR_LARGE_OBJECTS} is true )
     */
    public static int LARGE_OBJECT_HEIGHT_THRESHOLD =
            ProcessWidgetConstants.TASK_HEIGHT_SIZE * 3;

    public AbstractActivityAnchor(IFigure parent, boolean isSource) {
        super(parent);
        this.isSource = isSource;
    }

    /**
     * @param ref
     * @return The location for anchor using the original pre-v3.6.0 method -
     *         when the {@link XpdFlowRoutingStyle#UncenteredOnTasks} style is
     *         selected.
     */
    protected abstract Point getOriginalMethodLocation(Point ref);

    /**
     * @param ref
     * @return The location for anchor using the original pre-v3.6.0 method -
     *         when the {@link XpdFlowRoutingStyle#UncenteredOnTasks} style is
     *         selected.
     */
    protected abstract Point getOriginalMethodLocation(Rectangle ref);

    /**
     * @param ref
     * @return The location for anchor when the
     *         {@link XpdFlowRoutingStyle#SingleEntryExit} style is selected.
     */
    public Point getSinglePointLocation(Point ref) {
        lastReturnedLocation = getCenterOfBestSideLocation(ref);
        return lastReturnedLocation;
    }

    /**
     * This method relies on {@link ProcessConnectionLayer} to have previously
     * preset the anchor position sorted along the edge of our owner object
     * 
     * @param ref
     * @return The location for anchor when the
     *         {@link XpdFlowRoutingStyle#MultiEntryExit} style is selected.
     */
    protected final Point getMultiPointLocation(Point ref) {
        lastReturnedLocation = presetMultiEntryAnchorLocation;

        /*
         * If we haven't been preset with a location then default to the single
         * entry/exit point (centre of side) anchor location.
         * 
         * This can happen, when we are called outside of
         * ProcesConnectionLayer.validate() which normally presets our location
         * after sorting the anchors on one side of an activity.
         */
        if (lastReturnedLocation == null) {
            lastReturnedLocation = getSinglePointLocation(ref);
        }
        return lastReturnedLocation;
    }

    /**
     * Given horizontal or vertical location (absolute_xORy) anywhere along the
     * side of the owner object indicated by the direction, then calculate the
     * actual point location where that should intersect the oactivity bounds.
     * 
     * @param absolute_xORy
     *            When side is NORTH or SOUTH then the is the x coordinate along
     *            that side. When side is WEST or EAST then it is the vertical y
     *            location along that side.
     * @param direction
     *            {@link PositionConstants}.NORTH|SOUTH|EAST|WEST
     * @return
     */
    public abstract Point getSideIntersectionForDirection(int absolute_xORy,
            int direction);

    @Override
    public Point getReferencePoint() {
        if (getOwner() == null) {
            return null;
        }

        Rectangle bnds = getActivityAbsoluteBounds();

        /* Get absolute centre. */
        Point centre = getSideCentreLocationFromDirection(bnds, 0);

        return centre;
    }

    @Override
    public Point getLocation(Point ref) {
        XpdFlowRoutingStyle xpdRouterStyle =
                XPDFigureUtilities.getXpdRouterStyle(getOwner());

        if (XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            return getMultiPointLocation(ref);
        }

        lastCalculatedDirection = -1;

        if (XpdFlowRoutingStyle.SingleEntryExit.equals(xpdRouterStyle)) {
            return getSinglePointLocation(ref);
        }

        lastReturnedLocation = getOriginalMethodLocation(ref);
        return lastReturnedLocation;
    }

    /**
     * Get anchor location given a rectangle as external reference point.
     */
    @Override
    public Point getLocation(Rectangle ref) {
        XpdFlowRoutingStyle xpdRouterStyle =
                XPDFigureUtilities.getXpdRouterStyle(getOwner());

        if (XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            return getMultiPointLocation(ref);
        }

        lastCalculatedDirection = -1;

        if (XpdFlowRoutingStyle.SingleEntryExit.equals(xpdRouterStyle)) {
            return getSinglePointLocation(ref);
        }

        lastReturnedLocation = getOriginalMethodLocation(ref);
        return lastReturnedLocation;
    }

    /**
     * @param ref
     * @return The location for anchor when the
     *         {@link XpdFlowRoutingStyle#SingleEntryExit} style is selected.
     */
    public Point getSinglePointLocation(Rectangle ref) {
        return getCenterOfBestSideLocation(ref);
    }

    /**
     * This method relies on {@link ProcessConnectionLayer} to have previously
     * preset the anchor position sorted along the edge of our owner object
     * 
     * @param ref
     * @return The location for anchor when the
     *         {@link XpdFlowRoutingStyle#MultiEntryExit} style is selected.
     */
    protected Point getMultiPointLocation(Rectangle ref) {
        /*
         * If we haven't been preset with a location then default to the single
         * entry/exit point (centre of side) anchor location.
         * 
         * This can happen, when we are called outside of
         * ProcesConnectionLayer.validate() which normally presets our location
         * after sorting the anchors on one side of an activity.
         */
        lastReturnedLocation = presetMultiEntryAnchorLocation;
        // System.out.println(String
        //                    .format("ActivityAnchor[%d].getLocation(%s): %s", //$NON-NLS-1$
        // hashCode(),
        // ref,
        // lastReturnedLocation));
        if (lastReturnedLocation == null) {
            lastReturnedLocation = getCenterOfBestSideLocation(ref);
        }

        return lastReturnedLocation;
    }

    /**
     * SID DI:24908 - now implements route helping interface
     * ConnectionAnchorDirection
     */
    @Override
    public int getConnectionAnchorDirection(Point anchorOrReferencePoint) {
        XpdFlowRoutingStyle xpdRouterStyle =
                XPDFigureUtilities.getXpdRouterStyle(getOwner());

        if (XpdFlowRoutingStyle.SingleEntryExit.equals(xpdRouterStyle)
                || XpdFlowRoutingStyle.MultiEntryExit.equals(xpdRouterStyle)) {
            if (lastCalculatedDirection == -1) {
                System.err
                        .println(this.getClass().getName()
                                + "getConnectionAnchorDirection(): Last connection direction not set. This shoudl not happen"); //$NON-NLS-1$
                return PositionConstants.NONE;
            } else {
                return lastCalculatedDirection;
            }
        }

        return originalGetDirection(anchorOrReferencePoint);
    }

    /**
     * Method for single entry / exit point location.
     * 
     * @param ref
     * @return
     */
    public Point getCenterOfBestSideLocation(Point ref) {

        Rectangle bnds = getActivityAbsoluteBounds();

        // Get the default direction of line into/from anchor according to the
        // reference point
        int direction = calculateAnchorDirection(bnds, ref);
        lastCalculatedDirection = direction;
        usingOldSlideAlongSideMethod = false;

        Point location;

        // Check the direction and set the location of anchor point
        // accordingly...
        location = getSideCentreLocationFromDirection(bnds, direction);

        /*
         * If the behaviour switch that says use the old locating method for
         * larger objects is on and the side that we are suggesting is larger
         * than a given threshold then revert to old method.
         */
        if (ALLOW_NON_CENTRE_FOR_LARGE_OBJECTS) {
            Rectangle relativeBounds = getActivityBounds();

            if ((direction == PositionConstants.NORTH || direction == PositionConstants.SOUTH)
                    && relativeBounds.width > AbstractActivityAnchor.LARGE_OBJECT_WIDTH_THRESHOLD) {
                /* Reverting to old "allow slide along side" method */
                usingOldSlideAlongSideMethod = true;

                int minX = bnds.x + getXMargin();
                int maxX = (bnds.x + bnds.width) - getXMargin();

                /* Don't allow to go beyond 20 % of corners. */
                location.x = Math.max(ref.x, minX);
                location.x = Math.min(location.x, maxX);

            } else if ((direction == PositionConstants.WEST || direction == PositionConstants.EAST)
                    && relativeBounds.height > AbstractActivityAnchor.LARGE_OBJECT_HEIGHT_THRESHOLD) {
                /* Reverting to old "allow slide along side" method */
                usingOldSlideAlongSideMethod = true;

                int minY = bnds.y + getYMargin();
                int maxY = (bnds.y + bnds.height) - getYMargin();

                /* Don't allow to go beyond 20 % of corners. */
                location.y = Math.max(ref.y, minY);
                location.y = Math.min(location.y, maxY);
            }
        }

        lastReturnedLocation = location;
        return location;
    }

    /**
     * This method compensates for the vaugeries of processing by
     * {@link SnapToGeometry} for teh location of center of objects. By using
     * this method you should be able to be sure that the location matches that
     * used by thing sliek {@link AbstractActivityAnchor}
     * 
     * @param bnds
     * @param direction
     *            {@link PositionConstants}.NORTH|EAST|SOUTH|WEST for required
     *            center side location or 0 for absolteu centre,
     * @return Centre of side location for given line direction.
     */
    public Point getSideCentreLocationFromDirection(Rectangle bnds,
            int direction) {
        Point location;

        int width = bnds.width;
        int height = bnds.height;

        int centreX = (int) Math.floor(bnds.x + ((float) width / 2));
        int centreY = (int) Math.floor(bnds.y + ((float) height / 2));

        switch (direction) {
        case PositionConstants.NORTH:
            location = new Point(centreX, bnds.y);
            break;

        case PositionConstants.SOUTH:
            location = new Point(centreX, bnds.y + height);
            break;

        case PositionConstants.WEST:
            location = new Point(bnds.x, centreY);
            break;

        case PositionConstants.EAST:
            location = new Point(bnds.x + width, centreY);
            break;
        default:
            location = new Point(centreX, centreY);
            break;
        }
        return location;
    }

    /**
     * Method for single entry / exit point location.
     * 
     * @param ref
     * @return
     */
    protected Point getCenterOfBestSideLocation(Rectangle ref) {
        Rectangle bnds = getActivityAbsoluteBounds();

        // Get the default direction of line into/from anchor according to the
        // reference point, Direction is the direction of the point from US.
        int direction = calculateAnchorDirection(bnds, ref);
        lastCalculatedDirection = direction;
        usingOldSlideAlongSideMethod = false;

        Point location = getSideCentreLocationFromDirection(bnds, direction);

        /*
         * If the behaviour switch that says use the old locating method for
         * larger objects is on and the side that we are suggesting is larger
         * than a given threshold then revert to old method.
         */
        if (ALLOW_NON_CENTRE_FOR_LARGE_OBJECTS) {
            if ((direction == PositionConstants.NORTH || direction == PositionConstants.SOUTH)
                    && getActivityBounds().width > AbstractActivityAnchor.LARGE_OBJECT_WIDTH_THRESHOLD) {
                /* Reverting to old "allow slide along side" method */
                usingOldSlideAlongSideMethod = true;

                /*
                 * If the reference object is less than the threshold width then
                 * we will be mapping to it's centre to treat accordingly.
                 */
                if (ref.width < AbstractActivityAnchor.LARGE_OBJECT_WIDTH_THRESHOLD) {
                    int minX = bnds.x + getXMargin();
                    int maxX = (bnds.x + bnds.width) - getXMargin();

                    /* Don't allow to go beyond 20 % of corners. */
                    location.x =
                            Math.max(getSideCentreLocationFromDirection(ref, 0).x,
                                    minX);
                    location.x = Math.min(location.x, maxX);

                } else {
                    location.x = getCalculateHorizontal(bnds, ref);

                }

            } else if ((direction == PositionConstants.WEST || direction == PositionConstants.EAST)
                    && getActivityBounds().height > AbstractActivityAnchor.LARGE_OBJECT_HEIGHT_THRESHOLD) {
                /* Reverting to old "allow slide along side" method */
                usingOldSlideAlongSideMethod = true;

                /*
                 * If the reference object is less than the threshold height
                 * then we will be mapping to it's centre to treat accordingly.
                 */
                if (ref.height < AbstractActivityAnchor.LARGE_OBJECT_WIDTH_THRESHOLD) {
                    int minY = bnds.y + getYMargin();
                    int maxY = (bnds.y + bnds.height) - getYMargin();

                    /* Don't allow to go beyond 20 % of corners. */
                    location.y =
                            Math.max(getSideCentreLocationFromDirection(ref, 0).y,
                                    minY);
                    location.y = Math.min(location.y, maxY);

                } else {
                    location.y = getCalculateVertical(bnds, ref);
                }
            }
        }

        lastReturnedLocation = location;
        return location;
    }

    /**
     * @param ownerAbsoluteBounds
     * @param refRect
     * @return Most appropriate direction for anchor (i.e. the side that the
     *         connection using the anchor will enter/leave the owner figure.
     */
    protected int calculateAnchorDirection(Rectangle ownerAbsoluteBounds,
            Rectangle refRect) {
        return getAnchorDirectionForToRects(ownerAbsoluteBounds, refRect);
    }

    /**
     * Get best anchor direction given a source and target reference rectangle.
     * 
     * @param objRect
     * @param ref
     * @return anchor direction {@link PositionConstants}.NORTH|EAST|SOUTH|WEST
     */
    protected int getAnchorDirectionForToRects(Rectangle objRect, Rectangle ref) {
        if (ref.x > objRect.right()) {
            /* Ref is fully right of right edge of source. */
            return PositionConstants.EAST;
        } else if (ref.right() < objRect.x) {
            /* Ref is fully left of left edge of source. */
            return PositionConstants.WEST;
        } else if (ref.y > objRect.bottom()) {
            /* Ref is fully under of bottom edge of source. */
            return PositionConstants.SOUTH;
        } else if (ref.bottom() < objRect.y) {
            /* Ref is fully above of top edge of source. */
            return PositionConstants.NORTH;
        }

        return PositionConstants.NONE;
    }

    /**
     * @param ownerAbsoluteBounds
     * @param refPoint
     * @return Most appropriate direction for anchor (i.e. the side that the
     *         connection using the anchor will enter/leave the owner figure.
     */
    protected int calculateAnchorDirection(Rectangle ownerAbsoluteBounds,
            Point refPoint) {
        return AnchorDirectionHelper.getAnchorDirection(XPDFigureUtilities
                .getXpdRouterStyle(getOwner()), ownerAbsoluteBounds, refPoint);
    }

    /**
     * @return the lastCalculatedDirection
     */
    public final int getLastCalculatedDirection() {
        return lastCalculatedDirection;
    }

    /**
     * @return the usingOldSlideAlongSideMethod
     */
    public final boolean isUsingOldSlideAlongSideMethod() {
        return usingOldSlideAlongSideMethod;
    }

    /**
     * @return the lastReturnedLocation
     */
    public final Point getLastReturnedLocation() {
        return lastReturnedLocation;
    }

    /**
     * In {@link XpdFlowRoutingStyle#MultiEntryExit} routing style,
     * {@link ProcessConnectionLayer} needs to analyse and sort the connection
     * anchors that appear on one side of an activity.
     * <p>
     * This method allows {@link ProcessConnectionLayer} to preset the locaiton
     * of the anchr so that when {@link #getLocation(Point)} etc are called we
     * can simply return the value already set upon this anchor.
     * 
     * @param presetMultiEntryAnchorLocation
     * @param nthAnchor
     *            This is the 'n'th anchor (1-based)
     * @param beforeMidPoint
     *            Before or after the midpoint.
     */
    public final void presetAnchorLocationForMultiEntry(
            Point presetMultiEntryAnchorLocation) {
        this.presetMultiEntryAnchorLocation = presetMultiEntryAnchorLocation;
    }

    /**
     * @return the presetMultiEntryAnchorLocation
     */
    public Point getPresetMultiEntryAnchorLocation() {
        return presetMultiEntryAnchorLocation;
    }

    /**
     * @param lastCalculatedDirection
     *            the lastCalculatedDirection to set
     */
    protected final void setLastCalculatedDirection(int lastCalculatedDirection) {
        this.lastCalculatedDirection = lastCalculatedDirection;
    }

    /**
     * @param usingOldSlideAlongSideMethod
     *            the usingOldSlideAlongSideMethod to set
     */
    protected final void setUsingOldSlideAlongSideMethod(
            boolean usingOldSlideAlongSideMethod) {
        this.usingOldSlideAlongSideMethod = usingOldSlideAlongSideMethod;
    }

    /**
     * @param lastReturnedLocation
     *            the lastReturnedLocation to set
     */
    protected final void setLastReturnedLocation(Point lastReturnedLocation) {
        this.lastReturnedLocation = lastReturnedLocation;
    }

    /**
     * @return activity bounds translated to absolute coords.
     */
    public Rectangle getActivityAbsoluteBounds() {
        // Get activity bounding rectangle
        Rectangle bnds = getActivityBounds();
        getOwner().translateToAbsolute(bnds);
        return bnds;

    }

    /**
     * @param ref
     * @return Get the nominal direction (side of activity) of the anchor.
     */
    protected int originalGetDirection(Point ref) {
        int direction = PositionConstants.NONE;

        Rectangle bnds = getActivityBounds();
        getOwner().translateToAbsolute(bnds);

        direction = calculateAnchorDirection(bnds, ref);

        return (direction);
    }

    /**
     * SID DI:24908 - return horizontal margin from corner Returns in absolute
     * (post scaled) size
     */
    protected int getXMargin() {
        Rectangle rct = getActivityBounds();

        int margin = Math.min((rct.width / 5), 6);

        double scale = XPDFigureUtilities.getFigureScale(getOwner());

        return ((int) (margin * scale));
    }

    /**
     * SID DI:24908 - return vertical margin from corner Returns in absolute
     * (post scaled) size
     */
    protected int getYMargin() {
        Rectangle rct = getActivityBounds();

        int margin = Math.min((rct.height / 5), 6);

        double scale = XPDFigureUtilities.getFigureScale(getOwner());

        return ((int) (margin * scale));
    }

    protected Rectangle getActivityBounds() {
        IFigure fig = getOwner();
        if (fig instanceof HandleBounds) {
            return ((HandleBounds) fig).getHandleBounds().getCopy();
        }

        return fig.getBounds().getCopy();
    }

    // SID DI:24908 - We never link directly to corner anymore (we only allow
    // start / end of line within
    // 10 pixels (or 20% whichever is smallest)
    protected int getCalculateHorizontal(Rectangle rct, Rectangle ref) {
        int x1 = Math.max(rct.x, ref.x);
        int x2 = Math.min(rct.x + rct.width, ref.x + ref.width);

        // Position is half way along intersection.
        int x = x1 + ((x2 - x1) / 2);

        // We will only allow anchor to be within 20% or 10 pixels of corners
        // (whichever is least)
        int margin = getXMargin();

        if (x < (rct.x + margin)) {
            x = rct.x + margin;
        } else if (x > ((rct.x + rct.width) - margin)) {
            x = ((rct.x + rct.width) - margin);
        }

        return x;
    }

    // SID DI:24908 - We never link directly to corner anymore (we only allow
    // start / end of line within
    // 10 pixels (or 20% whichever is smallest)
    protected int getCalculateVertical(Rectangle rct, Rectangle ref) {

        int y1 = Math.max(rct.y, ref.y);
        int y2 = Math.min(rct.y + rct.height, ref.y + ref.height);
        int y = y1 + (y2 - y1) / 2;

        // We will only allow anchor to be within 20% or 10 pixels of corners
        // (whichever is least)
        int margin = getYMargin();

        if (y < (rct.y + margin)) {
            y = rct.y + margin;
        } else if (y > ((rct.y + rct.height) - margin)) {
            y = ((rct.y + rct.height) - margin);
        }

        return y;
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        if (getOwner() != null) {
            return String
                    .format("%s[%d]: Owner(%s) LastReturnedLocation(%s) LastCalculatedDirection(%d)", //$NON-NLS-1$
                            this.getClass().getSimpleName(),
                            hashCode(),
                            getOwner(),
                            lastReturnedLocation,
                            lastCalculatedDirection);
        }
        return super.toString();
    }

    /**
     * @param point
     */
    public void setRoutingOffset(Dimension routingOffset) {
        this.routingOffset = routingOffset;
    }

    /**
     * @return the routingOffset
     */
    public Dimension getRoutingOffset() {
        return routingOffset;
    }

    /**
     * @return <code>true</code> if this is a connection source anchor
     */
    public boolean isSource() {
        return isSource;
    }
}
