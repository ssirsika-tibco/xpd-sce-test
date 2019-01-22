/**
 * CircumferenceAncor.java
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
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * CircumferenceAncor
 * 
 * <p>
 * Connection anchor whose location is specified as a % portion of the
 * circumference of a circle.
 * </p>
 * 
 */
public class CircumferenceAnchor extends AbstractConnectionAnchor implements
        IFixedLocationAnchor {

    Double portionOfCircumferenceLocation = null;

    /**
     * Create a fixed-position-on-circumference anchor point from a given
     * reference point.
     * <p>
     * You can then ask the this anchor for the portion of circuference that
     * this point on circumference represents.
     * </p>
     * <p>
     * The circle is assumed to be the WIDTH of the given figure's bounding
     * rectangle.
     * </p>
     * 
     * @param owner
     * @param reference
     */
    public CircumferenceAnchor(IFigure owner, Point reference) {
        super(owner);

        portionOfCircumferenceLocation = new Double(
                refPointToCircumferencePortion(reference));

    }

    /**
     * Create a fixed-position-on-circumference anchor point from a given
     * percentage portion of circumference where 0% = EAST, incrementing
     * clockwise so that 75% for instance is NORTH.
     * 
     * @param owner
     * @param portionOfCircumferenceLocation
     *            0.0 to 100.0 % portion of circumference
     */
    public CircumferenceAnchor(IFigure owner,
            Double portionOfCircumferenceLocation) {
        super(owner);

        if (portionOfCircumferenceLocation == null) {
            portionOfCircumferenceLocation = new Double(0);
        }
        else {
            this.portionOfCircumferenceLocation = portionOfCircumferenceLocation;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor#isFixed()
     */
    public boolean isInFixedLocation() {
        // This anchor is always fixed location.
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
     */
    public Point getLocation(Point reference) {
        return portionToCircumferencePoint(portionOfCircumferenceLocation.doubleValue());
    }

    /* (non-Javadoc)
     * @see org.eclipse.draw2d.AbstractConnectionAnchor#getReferencePoint()
     */
    @Override
    public Point getReferencePoint() {
        return portionToCircumferencePoint(portionOfCircumferenceLocation.doubleValue());
    }
    
    /**
     * Return 0-100% portion of owner figure circle from given reference point.
     * 
     * @param reference
     * @return
     */
    private double refPointToCircumferencePortion(Point reference) {
        // Calculate the intersection point on the circumference of circle
        Rectangle b = getOwner().getBounds().getCopy();
        getOwner().translateToAbsolute(b);

        double angle = XPDLineUtilities
                .getAngleOfLine(b.getCenter(), reference);

        // Convert to a % around circumference.
        double portion = 0;

        if (angle != 0) {
            portion = ((angle / 360.0f) * 100.0f);
        }

        return portion;
    }

    /**
     * Given % portion of circumference to a point on circumference of owner
     * figure circle.
     * 
     * @param portion
     * @return
     */
    private Point portionToCircumferencePoint(double portion) {
        // Convert % portion to angle.
        double angle = 360.0f * (portion / 100.0f);
        
        // Calculate point at radius distance along line of given angle from start point.
        Rectangle b = getOwner().getBounds().getCopy();
        getOwner().translateToAbsolute(b);
        
        Point ret = XPDLineUtilities.getPointOnLineFromAngle(b.getCenter(), b.width/2, angle);
        
        return ret;
    }

    /* (non-Javadoc)
     * @see com.tibco.xpd.processwidget.figures.anchors.IFixedLocationAnchor#getAnchorPosAsPortion()
     */
    public Double getAnchorPosAsPortion() {
        return portionOfCircumferenceLocation;
    }

}
