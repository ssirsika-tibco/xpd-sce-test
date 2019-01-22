/**
 * FixedOrientationAnchor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.AnchorListener;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * FixedOrientationAnchor
 * 
 * <p>
 * Anchor that either:
 * <li>Delegates positioning to any other anchor but only allows direction of
 * line from anchor a given orientation.</li>
 * 
 * <li>Sets position of anchor at the given absolute location along the
 * opposite access to the given orientation</li>
 * </p>
 */
public class FixedOrientationAnchor extends AbstractConnectionAnchor implements
        ConnectionAnchorDirection, IFixedLocationAnchor {

    private ConnectionAnchor delegateAnchor = null;

    private int orientation;

    public static final int DEFAULT_OPPOSITEAXIS = -1;

    private int oppositeAxisOffset = DEFAULT_OPPOSITEAXIS;

    /**
     * Anchor specifically designed to provide an anchor point on the visible
     * parent figure of a figure that has been hidden because the parent's
     * contents have been made invisible (i.e. it has been closed).
     * 
     * @param owner
     * @param delegateAnchor
     *            Anchor to delegate location'ing in given orientation.
     * @param orientation
     *            Either of PositionConstants.VERTICAL or
     *            PositionConstants.HORIZONTAL
     * 
     */
    public FixedOrientationAnchor(IFigure owner,
            ConnectionAnchor delegateAnchor, int orientation) {
        super(owner);

        this.delegateAnchor = delegateAnchor;
        this.orientation = orientation;
    }

    /**
     * Anchor point that always returns line-from anchor direction in given axis
     * orientation and fixes the opposite axis position as the given value.
     * 
     * @param owner
     * @param oppositeAxisOffset
     *            Absolute position of anchor in opposite axis to given
     *            orientation. OR DEFAULT_OPPOSITEAXIS to position anchor
     *            according to position of opposite end of conenction's
     *            reference point
     * @param orientation
     *            Either of PositionConstants.VERTICAL or
     *            PositionConstants.HORIZONTAL
     * 
     */
    public FixedOrientationAnchor(IFigure owner, int oppositeAxisOffset,
            int orientation) {
        super(owner);

        this.delegateAnchor = null;
        this.orientation = orientation;
        this.oppositeAxisOffset = oppositeAxisOffset;

    }

    /**
     * Anchor point that always returns line-from anchor direction in given axis
     * orientation and fixes the opposite axis position according to position of
     * opposite end of conenction's reference point
     * 
     * @param owner
     * @param orientation
     *            Either of PositionConstants.VERTICAL or
     *            PositionConstants.HORIZONTAL
     * 
     */
    public FixedOrientationAnchor(IFigure owner, int orientation) {
        this(owner, DEFAULT_OPPOSITEAXIS, orientation);
    }

    /**
     * Return the opposite axis to orientation position of anchor.
     * 
     * @return the oppositeAxisOffset or DEFAULT_OPPOSITEAXIS if anchor
     *         positioning is delegated to other anchor or no opposite axis
     *         position is set.
     */
    public int getOppositeAxisOffset() {
        return oppositeAxisOffset;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public void setDelegate(ConnectionAnchor delegate) {
        this.delegateAnchor = delegate;
    }

    /**
     * @param listener
     * @see org.eclipse.draw2d.ConnectionAnchor#addAnchorListener(org.eclipse.draw2d.AnchorListener)
     */
    public void addAnchorListener(AnchorListener listener) {
        if (delegateAnchor != null) {
            delegateAnchor.addAnchorListener(listener);
        }
    }

    /**
     * @param reference
     * @return
     * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
     */
    public Point getLocation(Point reference) {
        Point ret = null;
        int direction = getConnectionAnchorDirection(reference);

        if (delegateAnchor != null) {
            // If delegating normal stuff to another anchor then use it.

            Rectangle bnds = getOwner().getBounds().getCopy();
            getOwner().translateToAbsolute(bnds);

            Point hostCentre = bnds.getCenter();

            Point newRef = reference.getCopy();

            if (direction == PositionConstants.NORTH) {
                // Force NORTH location by giving ref as just above top middle.
                newRef = new Point(hostCentre.x, bnds.y - 1);

            } else if (direction == PositionConstants.SOUTH) {
                // Force SOUTH location by giving ref as just below bottom
                // middle.
                newRef = new Point(hostCentre.x, bnds.bottom() + 1);

            } else if (direction == PositionConstants.WEST) {
                // Force WEST location by giving ref as just left of left
                // middle.
                newRef = new Point(bnds.x - 1, hostCentre.y);

            } else if (direction == PositionConstants.EAST) {
                // Force EAST location by giving ref as just right of right
                // middle.
                newRef = new Point(bnds.right() + 1, hostCentre.y);

            }

            ret = delegateAnchor.getLocation(newRef);

        } else {
            // Else if anchor is fixed direction for given pos along opposite
            // axis.
            ret = getReferencePoint();

            if (oppositeAxisOffset == DEFAULT_OPPOSITEAXIS) {
                // If no absolute pos on opposite axis is given then default to
                // same opposite axis position as passed reference point.
                if (orientation == PositionConstants.HORIZONTAL) {
                    ret.y = reference.y;
                } else {
                    ret.x = reference.x;
                }

            }

            Rectangle bnds = getOwner().getBounds().getCopy();
            getOwner().translateToAbsolute(bnds);

            if (direction == PositionConstants.NORTH) {
                ret.y = bnds.y;

            } else if (direction == PositionConstants.SOUTH) {
                ret.y = bnds.bottom();

            } else if (direction == PositionConstants.WEST) {
                ret.x = bnds.x;

            } else if (direction == PositionConstants.EAST) {
                ret.x = bnds.right();

            }

        }
        return ret;
    }

    /**
     * @return
     * @see org.eclipse.draw2d.ConnectionAnchor#getOwner()
     */
    public IFigure getOwner() {
        if (delegateAnchor != null) {
            return delegateAnchor.getOwner();
        }

        return super.getOwner();
    }

    /**
     * @return
     * @see org.eclipse.draw2d.ConnectionAnchor#getReferencePoint()
     */
    public Point getReferencePoint() {
        // If delegating normal stuff to another anchor then use it.
        if (delegateAnchor != null) {
            return delegateAnchor.getReferencePoint();
        }

        // Else if anchor is fixed direction for given pos along opposite axis.
        Rectangle bnds = getOwner().getBounds();
        Point cntr = bnds.getCenter().getCopy();

        if (oppositeAxisOffset != DEFAULT_OPPOSITEAXIS) {
            if (orientation == PositionConstants.HORIZONTAL) {
                cntr.y = Math.min(oppositeAxisOffset, bnds.bottom() - 5);
            } else {
                cntr.x = Math.min(oppositeAxisOffset, bnds.right() - 5);
            }
        }

        getOwner().translateToAbsolute(cntr);

        return cntr;
    }

    /**
     * @param listener
     * @see org.eclipse.draw2d.ConnectionAnchor#removeAnchorListener(org.eclipse.draw2d.AnchorListener)
     */
    public void removeAnchorListener(AnchorListener listener) {
        if (delegateAnchor != null) {
            delegateAnchor.removeAnchorListener(listener);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.ConnectionAnchorDirection#getConnectionAnchorDirection(org.eclipse.draw2d.geometry.Point)
     */
    public int getConnectionAnchorDirection(Point refPt) {
        int direction = PositionConstants.NONE;

        Point hostRef = getReferencePoint();

        if (orientation == PositionConstants.HORIZONTAL) {
            if (refPt.x < hostRef.x) {
                direction = PositionConstants.WEST;
            } else {
                direction = PositionConstants.EAST;
            }
        } else {
            if (refPt.y < hostRef.y) {
                direction = PositionConstants.NORTH;
            } else {
                direction = PositionConstants.SOUTH;
            }
        }

        return direction;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor#isFixed()
     */
    public boolean isInFixedLocation() {
        // Anchor is fixed position no specific offset is in  use.
        return oppositeAxisOffset != DEFAULT_OPPOSITEAXIS;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor#getAnchorPosAsPortion()
     */
    public Double getAnchorPosAsPortion() {
        return new Double(oppositeAxisOffset);
    }

}
