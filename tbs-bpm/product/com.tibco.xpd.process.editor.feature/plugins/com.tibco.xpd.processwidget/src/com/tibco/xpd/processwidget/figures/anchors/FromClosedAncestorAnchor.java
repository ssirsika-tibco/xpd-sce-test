/**
 * FromClosedAncestortAnchor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

import java.util.ArrayList;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.LaneFigure;
import com.tibco.xpd.processwidget.figures.PoolFigure;
import com.tibco.xpd.processwidget.figures.TaskFigure;

/**
 * FromClosedAncestortAnchor
 * 
 * Anchor specifically designed to provide an anchor point on the visible parent
 * figure of a figure that has been hidden because the parent's contents have
 * been made invisible (i.e. it has been closed).
 * 
 */
public class FromClosedAncestorAnchor extends AbstractConnectionAnchor
        implements ConnectionAnchorDirection {

    ConnectionAnchor delegateAnchor;

    int orientation;

    private class PointAndDirection {
        Point point;

        int direction;
    }

    /**
     * Anchor specifically designed to provide an anchor point on the visible
     * parent figure of a figure that has been hidden because the parent's
     * contents have been made invisible (i.e. it has been closed).
     * 
     * @param owner
     * @param closeableAncestorClass
     *            IFigure class to be counted as parent of owner.
     * @param delegateAnchor
     *            Anchor to delgate get position of nomainal anchor when
     *            object's ancestor not closed.
     * @param orientation
     *            Either of PositionConstants.VERTICAL or
     *            PositionConstants.HORIZONTAL
     * 
     */
    public FromClosedAncestorAnchor(IFigure owner,
            ConnectionAnchor delegateAnchor, int orientation) {
        super(owner);

        this.orientation = orientation;
        this.delegateAnchor = delegateAnchor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry
     * .Point)
     */
    public Point getLocation(Point reference) {
        PointAndDirection pt = getLocationAndDirection(reference);
        return pt.point;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.figures.anchors.ConnectionAnchorDirection
     * #getConnectionAnchorDirection(org.eclipse.draw2d.geometry.Point)
     */
    public int getConnectionAnchorDirection(Point reference) {
        PointAndDirection pt = getLocationAndDirection(reference);
        return pt.direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.AbstractConnectionAnchor#getReferencePoint()
     */
    @Override
    public Point getReferencePoint() {
        // Get reference point that is guaranteed to be within visible parent
        // figure. Otherwise when routing asks for a reference point for object
        // in closed parent, the host object can be outside it's parents
        // bounds (i.e. object can be at y=400 in visible ancestor BUT visible
        // ancestor is only 20 pixels high. That would mean that our anchor
        // preferred direction calcs would be all wrong.

        Point ref = super.getReferencePoint().getCopy();
        if (ref != null) {
            IFigure visibleAncestor = getCloseableAncestor();

            if (visibleAncestor != null) {
                Rectangle bnds = visibleAncestor.getBounds().getCopy();

                visibleAncestor.translateToAbsolute(bnds);

                ref.x = mid(bnds.x, ref.x, bnds.right());
                ref.y = mid(bnds.y, ref.y, bnds.bottom());
            }

        }

        return ref;
    }

    /**
     * @return the delegateAnchor
     */
    public ConnectionAnchor getDelegateAnchor() {
        return delegateAnchor;
    }

    /**
     * @return Get nearest visible closeable ancestor to redirect the anchor to.
     */
    private IFigure getCloseableAncestor() {

        IFigure nearestVisibleAncestor = null;

        IFigure ancestor = getOwner().getParent();

        while (ancestor != null) {
            boolean isClosableAncestorAndClosed = false;

            if (ancestor instanceof PoolFigure) {
                isClosableAncestorAndClosed =
                        ((PoolFigure) ancestor).isClosed();
            } else if (ancestor instanceof LaneFigure) {
                isClosableAncestorAndClosed =
                        ((LaneFigure) ancestor).isClosed();
            } else if (ancestor instanceof TaskFigure) {
                isClosableAncestorAndClosed =
                        !((TaskFigure) ancestor).isContentsVisible();
            }

            // If this is a closeable ancestor AND it is closed then override
            // the last set nearest because that won't be visible.
            if (isClosableAncestorAndClosed) {
                nearestVisibleAncestor = ancestor;
            }

            ancestor = ancestor.getParent();
        }

        return nearestVisibleAncestor;
    }

    private PointAndDirection getLocationAndDirection(Point refPt) {
        PointAndDirection pt = new PointAndDirection();

        Point hostCentre = delegateAnchor.getLocation(refPt);

        // Default centre of host
        pt.point = hostCentre;
        pt.direction = PositionConstants.NONE;

        IFigure parent = getCloseableAncestor();

        if (parent != null) {

            Rectangle bnds = parent.getBounds().getCopy();
            parent.translateToAbsolute(bnds);

            hostCentre.x = mid(bnds.x, hostCentre.x, bnds.right());
            hostCentre.y = mid(bnds.y, hostCentre.y, bnds.bottom());

            if (orientation == PositionConstants.HORIZONTAL) {
                if (refPt.x < hostCentre.x) {
                    pt.direction = PositionConstants.WEST;
                } else {
                    pt.direction = PositionConstants.EAST;
                }
            } else {
                if (refPt.y < hostCentre.y) {
                    pt.direction = PositionConstants.NORTH;
                } else {
                    pt.direction = PositionConstants.SOUTH;
                }
            }

            if (pt.direction == PositionConstants.NORTH) {
                // Reference is above.
                pt.point =
                        new Point(mid(bnds.x, hostCentre.x, bnds.right()),
                                bnds.y);

            } else if (pt.direction == PositionConstants.SOUTH) {
                // Reference is below.
                pt.point =
                        new Point(mid(bnds.x, hostCentre.x, bnds.right()), bnds
                                .bottom());

            } else if (pt.direction == PositionConstants.WEST) {
                // Reference is left.
                pt.point =
                        new Point(bnds.x, mid(bnds.y, hostCentre.y, bnds
                                .bottom()));

            } else if (pt.direction == PositionConstants.EAST) {
                // Reference is right.
                pt.point =
                        new Point(bnds.right(), mid(bnds.y, hostCentre.y, bnds
                                .bottom()));

            }

        }
        return pt;
    }

    /**
     * Given three ints, return the one with the middle value. I.e., it is not
     * the single largest or the single smallest.
     * 
     * @param a
     * @param b
     * @param c
     * 
     * @return a, b or c depedning on which has the middle value.
     */
    private static int mid(int a, int b, int c) {
        if (a <= b) {
            if (b <= c)
                return b;
            else if (c <= a)
                return a;
            else
                return c;
        } else {
            if (b >= c)
                return b;
            else if (c >= a)
                return a;
            else
                return c;
        }
    }

}
