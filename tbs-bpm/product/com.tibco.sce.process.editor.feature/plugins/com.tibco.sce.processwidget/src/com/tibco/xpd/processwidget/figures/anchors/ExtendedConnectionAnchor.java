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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Extension to ConnectionAnchor that allows to ask for location giving a
 * rectangle as reference
 * 
 * @author wzurek
 */
public interface ExtendedConnectionAnchor extends ConnectionAnchor {
    /**
     * Returns the location where the Connection should be anchored in absolute
     * coordinates. The anchor may use the given reference Rectangle to
     * calculate this location.
     * 
     * @param reference
     *            The reference Rectangle in absolute coordinates
     * @return The anchor's location
     */
    Point getLocation(Rectangle reference);
}
