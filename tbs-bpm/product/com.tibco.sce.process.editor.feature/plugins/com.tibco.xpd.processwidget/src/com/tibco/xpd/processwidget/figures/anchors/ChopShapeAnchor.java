/**
 * ChopShapeAnchor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * ChopShapeAnchor
 *
 * Like ChopboxAnchor but for polyline described shapes.
 * 
 * Anchor = intersection point between any line describing the shape 
 * and line drawn between ref point and bounds-centre of shape. 
 * 
 */
public class ChopShapeAnchor extends ChopboxAnchor {
    IFigure                    hostFigure;
    ShapeAnchorLinesProvider    shapeProvider;
    
    
    /**
     * Create chop shape anchor whose shape is defined by polyline figure.
     * 
     * @param hostFigure
     */
    public ChopShapeAnchor (IFigure shapeProvider) {
        super (shapeProvider);

        if (!(shapeProvider instanceof ShapeAnchorLinesProvider)) {
            throw new UnsupportedOperationException(
                    "Attempt to create shape anchor with illegal figure"); //$NON-NLS-1$
        }

        this.shapeProvider = (ShapeAnchorLinesProvider) shapeProvider;
        this.hostFigure = shapeProvider;

    }

    /* (non-Javadoc)
     * @see org.eclipse.draw2d.AbstractConnectionAnchor#setOwner(org.eclipse.draw2d.IFigure)
     */
    public void setOwner(IFigure owner) {
        if (owner instanceof ShapeAnchorLinesProvider) {
            super.setOwner(owner);
            hostFigure = owner;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.draw2d.ChopboxAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
     */
    public Point getLocation(Point reference) {
        
        PointList pts = shapeProvider.getShapeAnchorLinePoints();
        
        Point loc = XPDLineUtilities.getIntersectionPoint(pts, reference);
        
        if (loc == null) {
            // If there is no intersection between centre and reference then get closest point to reference
            loc = XPDLineUtilities.getPolylinePointClosestToPoint(pts, reference);
        }
        
        return loc;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.draw2d.ChopboxAnchor#getReferencePoint()
     */
    public Point getReferencePoint() {
        return super.getReferencePoint();
    }

    /**
     * LineAnchorLinesProvider
     * 
     * Anything that uses line anchors must implement this interface to return
     * the point list that represents the line that can be anchored to.
     * 
     */
    public interface ShapeAnchorLinesProvider {

        /**
         * Return the point list that represents shape for line anchor
         * 
         * @return PointList in ABSOLUTE co-ordinates.
         */
        PointList getShapeAnchorLinePoints();

    }

}
