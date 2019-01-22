/**
 * Copyright (c) 2006 TIBCO Software Inc.
 */

package com.tibco.xpd.processwidget.actions;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGrid;

public class XPDSnapToGrid extends SnapToGrid {

    public XPDSnapToGrid (GraphicalEditPart container) {
        super (container);
    }

    public int snapRectangle(Request request, int snapLocations,
            PrecisionRectangle rect, PrecisionRectangle result) {
        int retSnapLocations = snapLocations;
        
        int saveGridX = gridX;
        int saveGridY = gridY;
        Point saveOrigin = origin.getCopy();


        // First off all see if we're resizing (resizing is done when a direction is passed.
        if ((snapLocations & (NORTH | EAST | SOUTH | WEST)) != 0 ) {
            retSnapLocations = super.snapRectangle(request, snapLocations, rect, result);
        }
        else {
            // Ok, what we do now is use the major grid squares (groups of four on even boundary)
            // and see which is closest to a respective grid corner.

            gridX *= 2;
            gridY *= 2;

            // Compare top left with nearest grid point.
            class DeltaAndResult {
                double delta;
                PrecisionRectangle result;
                double x;
                double y;
                
                public DeltaAndResult () {
                    delta = 0;
                    result = new PrecisionRectangle();
                    x = 0;
                    y = 0;
                }
                
                public void setLocation (double x, double y) {
                    this.x = x;
                    this.y = y;
                }
            };
            
            DeltaAndResult[] cornerDeltas = new DeltaAndResult[4];
            
            // Set up list of top left, bottom left, top right, bottom right delats
            cornerDeltas[0] = new DeltaAndResult();
            cornerDeltas[0].setLocation(rect.preciseX, rect.preciseY);                          // Top left.
            cornerDeltas[1] = new DeltaAndResult();
            cornerDeltas[1].setLocation(rect.preciseX, rect.preciseY + rect.preciseHeight);     // Bottom left
            cornerDeltas[2] = new DeltaAndResult();
            cornerDeltas[2].setLocation(rect.preciseX + rect.preciseWidth, rect.preciseY);      // Top right
            cornerDeltas[3] = new DeltaAndResult();
            cornerDeltas[3].setLocation(rect.preciseX + rect.preciseWidth, rect.preciseY + rect.preciseHeight); // Bottom Right
            
            
            for (int i = 0; i < cornerDeltas.length; i++) {
                cornerDeltas[i].delta = getDelta(request, cornerDeltas[i].x, cornerDeltas[i].y, snapLocations, cornerDeltas[i].result);
            }
            
            // Now adjust grid origin so we're working with centres of major squares
            origin.x += saveGridX;
            origin.y += saveGridY;

            // And get close thing to centre point.
            DeltaAndResult centreDelta = new DeltaAndResult();
            centreDelta.setLocation(rect.preciseX + (rect.preciseWidth / 2), rect.preciseY + (rect.preciseHeight / 2));
            centreDelta.delta = getDelta(request, centreDelta.x, centreDelta.y, snapLocations, centreDelta.result);

            // Now see which was closest
            PrecisionRectangle finalResult = centreDelta.result;
            
            double smallestDelta = centreDelta.delta;
            
            for (int i = 0; i < cornerDeltas.length; i++) {
                if (cornerDeltas[i].delta < smallestDelta) {
                    smallestDelta = cornerDeltas[i].delta;
                    finalResult = cornerDeltas[i].result;
                }
            }
            
            result.preciseX += finalResult.preciseX;
            result.preciseY += finalResult.preciseY;
            result.preciseWidth += finalResult.preciseWidth;
            result.preciseWidth += finalResult.preciseHeight;
            result.updateInts();
            
            retSnapLocations = 0;
        }
        
        // Now we have the closest point and result.
        // Now put things back the way they were...
        origin = saveOrigin;
        gridX = saveGridX;
        gridY = saveGridY;
    

        return (retSnapLocations);
    }

    private double getDelta (Request request, double x, double y, int snapLocations, PrecisionRectangle res) {
        PrecisionRectangle rc = new PrecisionRectangle();
        rc.preciseWidth = 1;
        rc.preciseHeight = 1;
        rc.preciseX = x;
        rc.preciseY = y;
        rc.updateInts();
        
        int newSnapLocation = super.snapRectangle(request, snapLocations, rc, res);
        
        return (Math.abs(res.preciseX) + Math.abs(res.preciseY));
    }
    
}
