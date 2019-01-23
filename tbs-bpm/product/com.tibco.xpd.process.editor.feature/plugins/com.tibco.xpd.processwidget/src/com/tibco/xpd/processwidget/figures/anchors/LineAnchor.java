/**
 * LineAnchor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * LineAnchor
 * 
 * <p>
 * Connection anchor that can be positioned at 'n'% along a polyline provided by
 * a give figure
 * </p>
 */
public class LineAnchor extends AbstractConnectionAnchor implements
        IFixedLocationAnchor {

    LineAnchorLinesProvider lineProvider;

    IFigure hostFigure;

    Double portionOfLinePosition = null;

    /**
     * Create default line anchor
     * 
     * <li> On getLocation(refPoint) returns point on line closest to refPoint.</li>
     * <li> On getReferencePoint() returns point that is 50% along line.</li>
     * 
     * @param lineProvider
     */
    public LineAnchor(IFigure lineProvider) {
        super((IFigure) lineProvider);

        if (!(lineProvider instanceof LineAnchorLinesProvider)) {
            throw new UnsupportedOperationException(
                    "Attempt to create line anchor with illegal figure"); //$NON-NLS-1$
        }

        this.lineProvider = (LineAnchorLinesProvider) lineProvider;
        this.hostFigure = lineProvider;
    }

    /**
     * Create line anchor fixed at position given by % portion of line
     * 
     * <li>On getLocation(refPoint) always returns point that is
     * portionOfLinePosition along line.</li>
     * <li>On getReferencePoint() returns point that is portionOfLinePosition
     * along line.</li>
     * 
     * @param lineProvider
     * @param portionOfLinePosition
     *            0.0 - 100.0 percentage along line position.
     */
    public LineAnchor(IFigure lineProvider, Double portionOfLinePosition) {
        super((IFigure) lineProvider);

        if (!(lineProvider instanceof LineAnchorLinesProvider)) {
            throw new UnsupportedOperationException(
                    "Attempt to create line anchor with illegal figure"); //$NON-NLS-1$
        }

        this.lineProvider = (LineAnchorLinesProvider) lineProvider;
        this.hostFigure = lineProvider;

        this.portionOfLinePosition = portionOfLinePosition;
    }

    /**
     * Create line anchor fixed at position given by nearest point to refPoint
     * 
     * This is a convenience constructor to save caller the necessity of
     * calculating portion of line before using the above constructor.
     * 
     * <p>
     * In other words, this constructor calculates portion of line that point
     * represents and set up according to that.
     * </p>
     * 
     * <li>On getLocation(refPoint) always returns point that is
     * portionOfLinePosition along line.</li>
     * <li>On getReferencePoint() returns point that is portionOfLinePosition
     * along line.</li>
     * 
     * 
     * @param lineProvider
     * @param refPoint
     */
    public LineAnchor(IFigure lineProvider, Point refPoint) {
        super((IFigure) lineProvider);

        if (!(lineProvider instanceof LineAnchorLinesProvider)) {
            throw new UnsupportedOperationException(
                    "Attempt to create line anchor with illegal figure"); //$NON-NLS-1$
        }

        this.lineProvider = (LineAnchorLinesProvider) lineProvider;
        this.hostFigure = lineProvider;

        PointList pts = this.lineProvider.getLineAnchorLinePoints();

        double portion = XPDLineUtilities
                .getLinePortionFromPoint(pts, refPoint);

        this.portionOfLinePosition = new Double(portion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.AbstractConnectionAnchor#setOwner(org.eclipse.draw2d.IFigure)
     */
    public void setOwner(IFigure owner) {
        if (owner instanceof LineAnchorLinesProvider) {
            super.setOwner(owner);
            lineProvider = (LineAnchorLinesProvider) owner;
            hostFigure = owner;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Location of line anchor given a reference point is closest position to
     * any of the line segments.
     * 
     * If a specific portion of line position has been previously set then it's
     * position is ALWAYS returned.
     * 
     * Otherwise the position of the closest point on host's lines is returned.
     * 
     * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
     */
    public Point getLocation(Point refPoint) {
        PointList pts = this.lineProvider.getLineAnchorLinePoints();

        // If have fixed portion of line set use that.
        Point ret;
        if (portionOfLinePosition != null) {
            ret = XPDLineUtilities.getLinePointFromPortion(pts,
                    portionOfLinePosition.doubleValue());
        } else {
            ret = XPDLineUtilities
                    .getPolylinePointClosestToPoint(pts, refPoint);
        }

        return ret;
    }

    /**
     * Get anchor position according to current set-up.
     * 
     * @return
     */
    private Point getAnchorPosition() {
        double portionOfLine = 50.0; // Default to 50% of line

        if (portionOfLinePosition != null) {
            portionOfLine = portionOfLinePosition.doubleValue();
        }

        PointList pts = this.lineProvider.getLineAnchorLinePoints();

        return XPDLineUtilities.getLinePointFromPortion(pts, portionOfLine);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.AbstractConnectionAnchor#getReferencePoint()
     */
    public Point getReferencePoint() {
        // The reference point for line anchors is 50% of line
        // or specific portion previously set.
        return getAnchorPosition();
    }

    /**
     * Return the percentage portion of line that the anchor is positioned on.
     * 
     * @return null If none set or 0.0 -> 100.0 percentage of line position.
     */
    public Double getPortionOfLinePosition() {
        return portionOfLinePosition;
    }

    /**
     * LineAnchorLinesProvider
     * 
     * Anything that uses line anchors must implement this interface to return
     * the point list that represents the line that can be anchored to.
     * 
     */
    public interface LineAnchorLinesProvider {

        /**
         * Return the point list that represents shape for line anchor
         * 
         * @return PointList in ABSOLUTE co-ordinates.
         */
        PointList getLineAnchorLinePoints();

        /**
         * This method is called by lineanchor to determine the direction in
         * which the line should leave the object being connected to.
         * 
         * This so that line anchor can support BPMN Flow Router's requirment
         * for determining best routing depending on the direction that a line
         * leaves / enters an object.
         * 
         * @param anchorPos
         *            The anchor position on the object
         * @param refPoint
         *            The point for which to return the direction.
         * 
         * @return PositionConstants.NONE / NORTH / SOUTH / EAST / WEST
         */
        int getLineDirectionFromAnchorPoint(Point anchorPos, Point refPoint);

    }

    /**
     * This method allows the caller to determine what direction the line should
     * leave the anchor's host object given a reference point when dealing with
     * Manhattan (horizontal or vertical only) line style.
     * 
     * @param refPoint
     * @return PositionConstants.NONE / NORTH / SOUTH / EAST / WEST
     */
    public int getConnectionAnchorDirection(Point refPoint) {
        // Delegate to line provider...
        // Determine correct direction between the anchor position and
        // the external reference point.
        return lineProvider.getLineDirectionFromAnchorPoint(
                getAnchorPosition(), refPoint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.FixedLocationAnchor#isFixed()
     */
    public boolean isInFixedLocation() {
        // Line anchors are always fixed location.
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor#getAnchorPosAsPortion()
     */
    public Double getAnchorPosAsPortion() {
        if (portionOfLinePosition == null) {
            return new Double (50.0f);
        }
        return portionOfLinePosition;
    }

}
