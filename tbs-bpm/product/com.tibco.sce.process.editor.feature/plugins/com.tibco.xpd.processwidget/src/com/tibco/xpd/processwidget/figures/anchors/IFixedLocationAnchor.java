/**
 * FixedLocationAnchor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.figures.anchors;

/**
 * FixedLocationAnchor
 * 
 * <p>
 * Interface for connection anchors that support fixed-locations.
 * </p>
 * <p>
 * Nominally anchors work out the best position to connect to an object
 * according to a given reference point. Anchors that support fixed location
 * have some way of specifying a non-default anchor location (i.e. they allow
 * the user to place the anchor at a given position on/in an object
 * </p>
 * <p>
 * The process widget Connection End Point Edit Policy will show a different
 * anchor graphic for fixed location anchors allowing the user to see which
 * anchors are 'default' and which have fixed locations.
 * </p>
 */
public interface IFixedLocationAnchor {

    /**
     * Return whether the anchor currently has a fixed location.
     * 
     * @return
     */
    boolean isInFixedLocation();

    /**
     * Get the anchor position expressed as a portion of the owner object (for
     * instance, on a line this might be % of distance along the line)
     * 
     * @return
     */
    Double getAnchorPosAsPortion();
}
