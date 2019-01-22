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

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.XPDFigureUtilities;

/**
 * Anchor that allows conection only to the middle of the each side of the owner
 * figure bounds
 * 
 * SID DI:24908 Now implements ConnectionAnchorDirection interface.
 * 
 * @author wzurek
 */
public class NESWAnchor extends ChopboxAnchor implements
        ConnectionAnchorDirection {
    public NESWAnchor(IFigure owner) {
        super(owner);
    }

    /**
     * Return the direction in which the line leaving / entering an anchor point
     * should go. SID DI:24908
     */
    @Override
    public int getConnectionAnchorDirection(Point anchorOrReferencePoint) {
        // Gateways are fairly simple, the anchors can only be mid point on each
        // side
        // So took the code previously located in getLocation() to calculate
        // side
        // getLocation() can then use this method then return the point which is
        // equivalent
        // to that side.
        Rectangle r = getBox().getCopy();

        // SID DI:24879 - Event/Gateway anchor points were 'missing' object
        // edges
        // seems to have been caused by the following 'fudge-factoring'...
        // r.translate(-1, -1);
        // r.resize(3, 3);

        getOwner().translateToAbsolute(r);

        int direction =
                AnchorDirectionHelper.getAnchorDirection(XPDFigureUtilities
                        .getXpdRouterStyle(getOwner()),
                        r,
                        anchorOrReferencePoint);

        return (direction);
    }

    @Override
    public Point getReferencePoint() {
        /*
         * Have to do get reference point and get Location using same order of
         * translation (i.e. translate rect to absolute before getting centre)
         * Otherwise roudning errors caused by zoom can give different results.
         */
        Rectangle rct = getBox().getCopy();
        getOwner().translateToAbsolute(rct);

        /*
         * SID ROuting Improvements: Use Math.round() when calculating the
         * centre because that matches what the alignment guides do.
         */
        /*
         * Sid Actually, the bendpoint stuff seems to use the opposite :( so use
         * floor() instead
         */
        return new Point(Math.floor(rct.x + ((float) rct.width / 2)),
                Math.floor(rct.y + ((float) rct.height / 2)));
    }

    @Override
    public Point getLocation(Point reference) {
        Point ret = null;

        Rectangle rct = getBox().getCopy();

        getOwner().translateToAbsolute(rct);

        // SID DI:24879 - Event/Gateway anchor points were 'missing' object
        // edges
        // seems to have been caused by the following 'fudge-factoring'...
        // r.translate(-1, -1);
        // r.resize(3, 3);

        // SID DI:24089 Gateways are fairly simple, the anchors can only be mid
        // point on each side
        // So find out the direction and we can find out the location.
        int direction = getConnectionAnchorDirection(reference);

        /*
         * SID ROuting Improvements: Use Math.round() when calculating the
         * centre because that matches what the alignment guides do.
         */

        switch (direction) {
        case PositionConstants.NORTH:
            ret =
                    new Point((Math.floor(rct.x + ((float) rct.width / 2))),
                            rct.y);
            break;
        case PositionConstants.SOUTH:
            ret =
                    new Point((Math.floor(rct.x + ((float) rct.width / 2))),
                            (rct.y + rct.height));
            break;
        case PositionConstants.EAST:
            ret =
                    new Point((rct.x + rct.width), (Math.floor(rct.y
                            + ((float) rct.height / 2))));
            break;
        case PositionConstants.WEST:
            ret =
                    new Point(rct.x, (Math.floor(rct.y
                            + ((float) rct.height / 2))));
            break;
        default:
            // If all else fails then return the centre position...
            double centerX = rct.x + Math.floor(0.5f * rct.width);
            double centerY = rct.y + Math.floor(0.5f * rct.height);

            ret = new Point((int) centerX, (int) centerY);
            break;
        }

        return (ret);
    }
}
