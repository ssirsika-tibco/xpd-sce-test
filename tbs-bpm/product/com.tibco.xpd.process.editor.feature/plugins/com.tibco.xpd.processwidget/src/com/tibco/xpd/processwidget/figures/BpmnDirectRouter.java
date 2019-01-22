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

package com.tibco.xpd.processwidget.figures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.anchors.LineAnchor;

/**
 * @author wzurek
 */
public class BpmnDirectRouter extends AbstractRouter {

    private static final int SELF_CONN_MARGIN = 20;

    Map constraints = new HashMap();

    public Object getConstraint(Connection connection) {
        return constraints.get(connection);
    }

    public void setConstraint(Connection connection, Object constraint) {
        if (constraint == null)
            constraints.remove(connection);
        else
            constraints.put(connection, constraint);
    }

    public void route(Connection conn) {

        PointList p = conn.getPoints();
        p.removeAllPoints();
        List points = (List) getConstraint(conn);

        ConnectionAnchor srcAnchor = conn.getSourceAnchor();
        ConnectionAnchor tgtAnchor = conn.getTargetAnchor();

        IFigure srcFigure = srcAnchor.getOwner();
        IFigure tgtFigure = tgtAnchor.getOwner();

        Point start;
        Point end;
        if (points == null || points.isEmpty()) {
            if (srcFigure == tgtFigure) {
                // the same start and end

                // SID ZOOM - Make sure that SELF_CONN_MARGIN gets scaled too
                // (translateToAbsolute() will scale for zoom level)
                Rectangle relBnds = srcFigure.getBounds();

                // Source anchor reference point (add margin before translate so
                // it gets scaled too).
                Point saRef = new Point(relBnds.x + relBnds.width
                        + SELF_CONN_MARGIN, relBnds.y + (relBnds.height / 2));
                srcFigure.translateToAbsolute(saRef);

                Point taRef = new Point(relBnds.x + (relBnds.width / 2),
                        relBnds.y - SELF_CONN_MARGIN);
                srcFigure.translateToAbsolute(taRef);

                Point startPoint = srcAnchor.getLocation(saRef).getCopy();
                Point endPoint = tgtAnchor.getLocation(taRef).getCopy();

                p.addPoint(startPoint);
                p.addPoint(saRef.x, startPoint.y);
                p.addPoint(saRef.x, taRef.y);
                p.addPoint(endPoint.x, taRef.y);

                p.addPoint(endPoint);

                // Convert all points from absolute to relative to connection.
                for (int i = 0; i < p.size(); i++) {
                    Point tp = p.getPoint(i);
                    conn.translateToRelative(tp);
                    p.setPoint(tp, i);
                }

            } else {

                start = getStartPoint(conn);
                end = getEndPoint(conn);

                p.addPoint(start);
                p.addPoint(end);

                // Convert all points from absolute to relative to connection.
                for (int i = 0; i < p.size(); i++) {
                    Point tp = p.getPoint(i);
                    conn.translateToRelative(tp);
                    p.setPoint(tp, i);
                }
            }
        } else {
            Point fp = ((Bendpoint) points.get(0)).getLocation().getCopy();
            Point lp = ((Bendpoint) points.get(points.size() - 1))
                    .getLocation().getCopy();
            conn.translateToAbsolute(fp);
            conn.translateToAbsolute(lp);
            start = srcAnchor.getLocation(fp).getCopy();
            end = tgtAnchor.getLocation(lp).getCopy();

            conn.translateToRelative(start);
            conn.translateToRelative(end);
            p.addPoint(start);
            for (Iterator iter = points.iterator(); iter.hasNext();) {
                Bendpoint pnt = (Bendpoint) iter.next();

                // This may look strange, but the original ref point we used to
                // get start / end
                // will have gone thru 2 transformations (it's translated to
                // relative to connection in RelativeBendPoint.getLocation()
                // then back to absolute for connection. If we don't go thru the
                // same number of transformations then
                // the bendpoints returned by pnt.getLocation() here may be
                // diofferent from fp/lp we worked out in the first place.
                // So translating to abs according to connection and back again
                // should resolve this.
                // i.e. it should get rid of rounding errors.
                Point newP = pnt.getLocation();
                conn.translateToAbsolute(newP);
                conn.translateToRelative(newP);
                p.addPoint(newP);
            }
            p.addPoint(end);

        }

        conn.setPoints(p);

        return;
    }

    protected Point getStartPoint(Connection conn) {
        Point point = null;
        
        ConnectionAnchor tgtAnchor = conn.getTargetAnchor();
        if (tgtAnchor instanceof LineAnchor) {
            // If other end is a line anchor then its reference point is 50% of
            // line by default.
            //
            // This is no good to us because we would normally base start point
            // on edge of source object on that.
            // (i.e. imagine drawing line from centre of our object to 50% point
            // on line, the point on src object boundary might be top left).
            //
            // LineAnchor getLocation() will then say that it should be closest
            // point on line to src object's start point - so in fact the
            // point chosen on start object might be top-left (pointing at 50%
            // of line BUT the closest point on line might be right of source
            // object and therefore the line is drawn from top-left of source
            // going up diagonally to the right.
            //
            // So instead If other end of connection is LineAnchor we won't use
            // it's reference point, we will get location of source object start
            // point based on the closest point on the line to source objecty ref point.
            
            ConnectionAnchor srcAnchor = conn.getSourceAnchor();
            
            Point ref = srcAnchor.getReferencePoint();
            
            Point tgtLoc = tgtAnchor.getLocation(ref);
            
            point = srcAnchor.getLocation(tgtLoc);
            

        }
        else {
            point = super.getStartPoint(conn);
        }

        return point;
    }

    protected Point getEndPoint(Connection conn) {
        Point point = null;
        
        ConnectionAnchor srcAnchor = conn.getSourceAnchor();
        if (srcAnchor instanceof LineAnchor) {
            // If other end is a line anchor then its reference point is 50% of
            // line by default.
            //
            // This is no good to us because we would normally base start point
            // on edge of source object on that.
            // (i.e. imagine drawing line from centre of our object to 50% point
            // on line, the point on src object boundary might be top left).
            //
            // LineAnchor getLocation() will then say that it should be closest
            // point on line to src object's start point - so in fact the
            // point chosen on start object might be top-left (pointing at 50%
            // of line BUT the closest point on line might be right of source
            // object and therefore the line is drawn from top-left of source
            // going up diagonally to the right.
            //
            // So instead If other end of connection is LineAnchor we won't use
            // it's reference point, we will get location of source object start
            // point based on the closest point on the line to source objecty ref point.
            
            ConnectionAnchor tgtAnchor = conn.getTargetAnchor();
            
            Point ref = tgtAnchor.getReferencePoint();
            
            Point srcLoc = srcAnchor.getLocation(ref);
            
            point = tgtAnchor.getLocation(srcLoc);
            

        }
        else {
            point = super.getEndPoint(conn);
        }

        return point;
    }
}
