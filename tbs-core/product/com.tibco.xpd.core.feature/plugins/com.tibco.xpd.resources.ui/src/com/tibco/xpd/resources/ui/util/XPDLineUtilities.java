/**
 * XPDLineUtilities.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.resources.ui.util;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Straight;
import org.eclipse.draw2d.geometry.Vector;

/**
 * XPDLineUtilities
 * 
 * Various utility methods for lines.
 */
public class XPDLineUtilities {

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
    private static double mid(double a, double b, double c) {
        double res, min, max;
        min = Math.min(a, b);
        max = Math.max(a, b);
        if (c < min)
            res = min;
        else if (c > max)
            res = max;
        else
            res = c;
        return res;
    }

    /**
     * Get intersection point of two lines.
     * 
     * XPD-5950 Replaced algorithm with standard one from draw2d library as the
     * old algorithm was not working with some lines.
     * 
     * @param v1
     *            Start of line 1.
     * @param v2
     *            End of line 1.
     * @param v3
     *            Start of line 2.
     * @param v4
     *            End of line 2.
     * @return The intersection point, or null if the lines do not intersect.
     */
    public static Point getIntersectionPoint(Point v1, Point v2, Point v3,
            Point v4) {
        Point intersect = null;
        if (!v1.equals(v2) && !v3.equals(v4)) {
            Straight s1 =
                    new Straight(new PrecisionPoint(v1), new PrecisionPoint(v2));
            Straight s2 =
                    new Straight(new PrecisionPoint(v3), new PrecisionPoint(v4));
            Vector intersectVector = s1.getIntersection(s2);
            if (intersectVector != null) {
                intersect = intersectVector.toPoint();
                // Check bounds
                boolean in1 =
                        (v1.x <= intersect.x && v2.x >= intersect.x)
                                || (v2.x <= intersect.x && v1.x >= intersect.x);
                boolean in2 =
                        (v1.y <= intersect.y && v2.y >= intersect.y)
                                || (v2.y <= intersect.y && v1.y >= intersect.y);
                boolean in3 =
                        (v3.x <= intersect.x && v4.x >= intersect.x)
                                || (v4.x <= intersect.x && v3.x >= intersect.x);
                boolean in4 =
                        (v3.y <= intersect.y && v4.y >= intersect.y)
                                || (v4.y <= intersect.y && v3.y >= intersect.y);
                if (!in1 || !in2 || !in3 || !in4) {
                    intersect = null;
                }
            }
        }
        return intersect;
    }

    /**
     * Get the intersection point between centre of the bounds of shape
     * described by polyline and a given reference point.
     * 
     * If a line drawn between the ref Point and centre of bounds of shape
     * intersects one of the shape's lines then the intersection point is
     * returned.
     * 
     * Otherwise null is returned.
     * 
     * @param shape
     * @param startLine
     * @param endLine
     * @return
     */
    public static Point getIntersectionPoint(PointList shape, Point refPoint) {
        Point ret = null;

        Rectangle bnds = shape.getBounds();
        Point centre = bnds.getCenter();

        for (int p = 0; p < (shape.size() - 1) && ret == null; p++) {

            ret =
                    getIntersectionPoint(shape.getPoint(p),
                            shape.getPoint(p + 1),
                            refPoint,
                            centre);

        }

        if (ret == null) {
            // check the line that will close the shape...
            ret =
                    getIntersectionPoint(shape.getPoint(shape.size() - 1),
                            shape.getPoint(0),
                            refPoint,
                            centre);
        }

        return (ret);
    }

    /**
     * Given the coordinates of the endpoints of a line segment, and a point,
     * set res to be the closest point on the segement to the given point.
     * 
     * @param startLine
     * @param endLine
     * @param refPoint
     * 
     * @return Point between startLine and endLine that is closest to refPoint.
     */
    public static Point getLinePointClosestToPoint(Point startLine,
            Point endLine, Point refPoint) {
        Point res = new Point();

        // segment is a point
        if (startLine.y == endLine.y && startLine.x == endLine.x) {
            res.x = startLine.x;
            res.y = startLine.y;
        }
        // segment is horizontal
        else if (startLine.y == endLine.y) {
            res.y = startLine.y;
            res.x = (int) mid(startLine.x, endLine.x, refPoint.x);
        }
        // segment is vertical
        else if (startLine.x == endLine.x) {
            res.x = startLine.x;
            res.y = (int) mid(startLine.y, endLine.y, refPoint.y);
        }
        // Line is angular
        else {
            /*
             * NOTE: Many thanks to Keith T for helping with this bit.
             */
            /*
             * This algorithm works best inverted if dx < dy (closer to vertical
             * than horizontal.
             */
            boolean invert = false;
            if (Math.abs(endLine.x - startLine.x) < Math.abs(endLine.y
                    - startLine.y)) {
                invert = true;
            }

            double sx;
            double sy;
            double ex;
            double ey;
            double rx;
            double ry;

            if (invert) {
                sx = startLine.y;
                sy = startLine.x;
                ex = endLine.y;
                ey = endLine.x;

                rx = refPoint.y;
                ry = refPoint.x;

            } else {
                sx = startLine.x;
                sy = startLine.y;
                ex = endLine.x;
                ey = endLine.y;

                rx = refPoint.x;
                ry = refPoint.y;
            }

            double dx = ex - sx; // Calc delta-x

            double dy = ey - sy; // Calc delta-y

            // Calc c in the equation of the
            // line y = m.x + c or y = (dy/dx).x
            // + c
            double c = sy - dy / dx * sx;

            // Calc d in the equation of the
            // perpendicular y = -x/m + d or y =
            // -(dx/dy).x + d
            double d = ry + dx / dy * rx;

            // Find the x value of the point where the lines cross
            double resX = ((d - c) * dx * dy / (dx * dx + dy * dy));

            // Find the x coordinate of the resultant point
            resX = mid(sx, ex, resX);

            // Find the y coordinate of the resultant point
            double resY = (dy / dx * resX + c);

            if (invert) {
                res.x = (int) Math.round(resY);
                res.y = (int) Math.round(resX);

            } else {
                res.x = (int) Math.round(resX);
                res.y = (int) Math.round(resY);
            }

        }

        return res;
    }

    /**
     * Return the point on one of the given lines described by point list that
     * represents the closest point to the given reference point
     * 
     * @param pts
     * @param refPoint
     * @param returnPoint
     * 
     * @return index into pts for start point of actual line that returnPoint is
     *         on
     */
    public static int getPolylinePointClosestToPoint(PointList pts,
            Point refPoint, Point returnPoint) {
        int startPointRetIndex = 0;
        double minDist = Double.MAX_VALUE;

        // Go thru each line segment
        for (int p = 0; p < (pts.size() - 1); p++) {

            Point pt =
                    XPDLineUtilities
                            .getLinePointClosestToPoint(pts.getPoint(p),
                                    pts.getPoint(p + 1),
                                    refPoint);

            double thisDist =
                    XPDLineUtilities.getComparitiveLineLength(pt, refPoint);

            if (thisDist < minDist) {
                returnPoint.x = pt.x;
                returnPoint.y = pt.y;
                minDist = thisDist;
                startPointRetIndex = p;
            }

        }

        return (startPointRetIndex);
    }

    /**
     * Return the point on one of the given lines described by point list that
     * represents the closest point to the given reference point
     * 
     * @param pts
     * @param refPoint
     * 
     * @return
     */
    public static Point getPolylinePointClosestToPoint(PointList pts,
            Point refPoint) {
        Point closestPoint = new Point(0, 0);

        getPolylinePointClosestToPoint(pts, refPoint, closestPoint);

        return closestPoint;
    }

    /**
     * Get the length of the line described by the 2 points.
     * 
     * NOTE: If you are only doing comparisons of different lines (i.e. 'is this
     * line longer than this') then you should use getComparitiveLineLength()
     * which is more efficient because it does not have to use square-root
     * function to get accurate line length.
     * 
     * @param startLine
     * @param endLine
     * @return
     */
    public static double getLineLength(Point startLine, Point endLine) {

        double xoff = Math.abs(startLine.x - endLine.x);
        double yoff = Math.abs(startLine.y - endLine.y);

        return Math.sqrt((xoff * xoff) + (yoff * yoff));

    }

    /**
     * Get the COMPARITIVE length of the line described by the 2 points.
     * 
     * NOTE: If you wish to get the ACTUAL physical line length then you should
     * use getLineLength().
     * 
     * @param startLine
     * @param endLine
     * 
     * @return A comparitive line length (i.e. not the actual physical line
     *         length but rather a value that can be compared with the return
     *         from this method performed on other lines.
     */
    public static double getComparitiveLineLength(Point startLine, Point endLine) {

        double xoff = Math.abs(startLine.x - endLine.x);
        double yoff = Math.abs(startLine.y - endLine.y);

        return ((xoff * xoff) + (yoff * yoff));

    }

    /**
     * Returns on a point on a sequence of lines that 'percentPortion' distance
     * along the line
     * 
     * @param pts
     * @param percentPortion
     *            0 to 100
     * @return
     */
    public static Point getLinePointFromPortion(PointList pts,
            double percentPortion) {
        if (percentPortion <= 0.0) {
            return (pts.getFirstPoint().getCopy());
        } else if (percentPortion >= 100.0) {
            return (pts.getLastPoint().getCopy());
        }

        // Calculate total line length and length of each portion.
        int totalLength = getLineLength(pts);

        // Calculate where the percent portion lies within the whole line.
        int pixelsFromStart = (int) (totalLength * (percentPortion / 100));

        return getLinePointFromOffset(pts, pixelsFromStart);
    }

    /**
     * @param pts
     * @param pixelsFromStart
     * @return point on line that is pixelsFromStart along the line.
     */
    public static Point getLinePointFromOffset(PointList pts,
            int pixelsFromStart) {
        int totalLength = getLineLength(pts);

        if (pixelsFromStart > totalLength) {
            return pts.getLastPoint().getCopy();
        }

        double[] lineLengths = new double[pts.size() - 1];

        for (int p = 0; p < (pts.size() - 1); p++) {
            double length = getLineLength(pts.getPoint(p), pts.getPoint(p + 1));
            lineLengths[p] = length;
        }

        // Find out how far down which line segment the pixel position lies
        int ll = 0;
        for (ll = 0; ll < lineLengths.length; ll++) {
            if (pixelsFromStart <= lineLengths[ll]) {
                // point falls on this line segment
                break;
            }

            // point doesn't fall on this segment, subtract
            // length of this segment and try next.
            pixelsFromStart -= lineLengths[ll];
        }

        // Ok - we now have the index of the line segment we're interested in
        // get the position of point along this line.
        Point startLine = pts.getPoint(ll);
        Point endLine = pts.getPoint(ll + 1);

        // Calculate portion of line that the pix percent is

        double portion = pixelsFromStart / getLineLength(startLine, endLine);

        double x = (endLine.x - startLine.x) * portion;
        double y = (endLine.y - startLine.y) * portion;
        Point ret =
                new Point(startLine.x + Math.round(x), startLine.y
                        + Math.round(y));

        return ret;
    }

    /**
     * @param pts
     * @return total length of the given line.
     */
    public static int getLineLength(PointList pts) {
        double totalLength = 0;
        for (int p = 0; p < (pts.size() - 1); p++) {
            totalLength += getLineLength(pts.getPoint(p), pts.getPoint(p + 1));
        }

        return (int) totalLength;
    }

    /**
     * Given a point return the percentage distance from the start of the line
     * (0 -> 100) to the point on line that is closest to given location
     * 
     * @param pts
     * @param percentPortion
     * @param retClosest
     *            Optional Return for the point location that represents this
     *            portion.
     * @return
     */
    public static double getLinePortionFromPoint(PointList pts, Point location,
            Point retClosest) {
        double percentPortion = 50; // Default to 50 % if all else fails.

        // Point may not be exactly on line so get nearest point on line first.
        Point closestPoint = null;

        double minDist = Double.MAX_VALUE;
        int lineSegmentIdx = 0;

        // Go thru each line segment
        for (int p = 0; p < (pts.size() - 1); p++) {

            Point pt =
                    XPDLineUtilities
                            .getLinePointClosestToPoint(pts.getPoint(p),
                                    pts.getPoint(p + 1),
                                    location);

            double thisDist = XPDLineUtilities.getLineLength(pt, location);

            if (thisDist < minDist) {
                closestPoint = pt;
                minDist = thisDist;
                lineSegmentIdx = p;
            }

        }

        // Add up the length of line UP TIL the line seg
        double totalLineLength = 0;
        double lenBeforeSegment = 0;
        for (int p = 0; p < (pts.size() - 1); p++) {
            double thisDist =
                    XPDLineUtilities.getLineLength(pts.getPoint(p),
                            pts.getPoint(p + 1));

            totalLineLength += thisDist;

            if (p < lineSegmentIdx) {
                lenBeforeSegment += thisDist;
            }
        }

        // Add the distance between closest point on line segment and start of
        // segment.
        double lenToPoint =
                XPDLineUtilities.getLineLength(pts.getPoint(lineSegmentIdx),
                        closestPoint);

        percentPortion = (lenBeforeSegment + lenToPoint) / totalLineLength;

        percentPortion *= 100; // Change from 0.0 -> 1.0 to 0.0 to 100.0

        if (retClosest != null) {
            retClosest.x = closestPoint.x;
            retClosest.y = closestPoint.y;
        }

        return (percentPortion);
    }

    /**
     * Given a point return the percentage distance from the start of the line
     * (0 -> 100) to the point on line that is closest to given location
     * 
     * @param pts
     * @param percentPortion
     * @return
     */
    public static double getLinePortionFromPoint(PointList pts, Point location) {
        return getLinePortionFromPoint(pts, location, null);
    }

    /**
     * Return the angle of the line described by 2 points.
     * 
     * 0 degrees = a line going horizontally EAST (left to right). 90 degrees =
     * a line going vertically SOUTH and so on.
     * 
     * @param a
     * @param b
     * @return 360 degree angle (starting EAST going clockwise.
     */
    public static double getAngleOfLine(Point a, Point b) {
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        double angle = 0.0d;

        if (dx == 0.0) {
            if (dy == 0.0)
                angle = 0.0;
            else if (dy > 0.0)
                angle = Math.PI / 2.0;
            else
                angle = (Math.PI * 3.0) / 2.0;
        } else if (dy == 0.0) {
            if (dx > 0.0)
                angle = 0.0;
            else
                angle = Math.PI;
        } else {
            if (dx < 0.0)
                angle = Math.atan(dy / dx) + Math.PI;
            else if (dy < 0.0)
                angle = Math.atan(dy / dx) + (2 * Math.PI);
            else
                angle = Math.atan(dy / dx);
        }

        // Finally convert from radians to 360 degrees.
        return (angle * 180) / Math.PI;
    }

    /**
     * Given the start of point of a line, an 360 degree angle and a line length
     * (i.e. radius), return the x,y co-ords of the point at that distance and
     * angle from start.
     * 
     * @param start
     *            Start point of line.
     * @param length
     *            Distance to travel along line.
     * @param angle360
     *            Angle expressed as 0-359.99999 degrees (0 = EAST incrementing
     *            clockwise).
     * 
     * @return
     */
    public static Point getPointOnLineFromAngle(Point start, double length,
            double angle360) {

        // Convert angle from 360 degrees to radians
        double angRad = (angle360 / 360.0f) * (2 * Math.PI);

        double x = length * Math.cos(angRad);
        double y = length * Math.sin(angRad);

        return new Point(start.x + Math.round(x), start.y + Math.round(y));
    }

    /**
     * Given the centre and radius of a circle calculate the closest point on
     * circumference to the given reference point.
     * 
     * @param centre
     * @param radius
     * @param refPoint
     * @return
     */
    public static Point getClosestPointOnCircumference(Point centre,
            double radius, Point refPoint) {

        // calculate delta-x and delta-y
        double dx = centre.x - refPoint.x;
        double dy = centre.y - refPoint.y;

        // Calculate Angle(in radians) from centre & point on circumference
        double angleToRefPoint = Math.atan2(dy, dx) + Math.PI;

        // Calculate point on circumference given Angle(in radians) and centre
        int x =
                (int) Math.round(centre.x
                        + (radius * Math.cos(angleToRefPoint)));

        int y =
                (int) Math.round(centre.y
                        + (radius * Math.sin(angleToRefPoint)));

        return new Point(x, y);

    }

    /**
     * Scale the given polyline/polygon KEEPING the centre the same as original.
     * 
     * @param pts
     * @param factor
     * 
     * @return New PointList with scaled points.
     */
    public static PointList scaleCentredPolyline(PointList pts, double factor) {
        PointList scaledPts = new PointList(pts.size());

        Point centre = pts.getBounds().getCenter();

        Point pt = new Point();

        int numPts = pts.size();

        for (int p = 0; p < numPts; p++) {
            pts.getPoint(pt, p);

            if (!pt.equals(centre)) {
                // Get the angle of line drawn between centre and this point.
                double angle = getAngleOfLine(centre, pt);

                // Get the line length between centre and point.
                double lineLen = getLineLength(centre, pt);

                // Scale the distance by given factor.
                lineLen *= factor;

                // Now calculate the point that lies along the line of given
                // angle at the scale distance.
                Point newp = getPointOnLineFromAngle(centre, lineLen, angle);

                scaledPts.addPoint(newp.x, newp.y);
            }
        }

        return scaledPts;
    }

    /**
     * Check whether polygon described by given point list contains point.
     * 
     * @param pts
     * @param x
     * @param y
     * @return
     */
    public static boolean polygonContainsPoint(PointList pts, int x, int y) {
        if (!pts.getBounds().contains(x, y))
            return false;

        boolean isOdd = false;
        int[] pointsxy = pts.toIntArray();
        int n = pointsxy.length;
        if (n > 3) { // If there are at least 2 Points (4 ints)
            int x1, y1;
            int x0 = pointsxy[n - 2];
            int y0 = pointsxy[n - 1];

            for (int i = 0; i < n; x0 = x1, y0 = y1) {
                x1 = pointsxy[i++];
                y1 = pointsxy[i++];

                if (y0 <= y && y < y1 && crossProduct(x1, y1, x0, y0, x, y) > 0)
                    isOdd = !isOdd;
                if (y1 <= y && y < y0 && crossProduct(x0, y0, x1, y1, x, y) > 0)
                    isOdd = !isOdd;
            }
            if (isOdd)
                return true;
        }

        return false;
    }

    private static int crossProduct(int ax, int ay, int bx, int by, int cx,
            int cy) {
        return (ax - cx) * (by - cy) - (ay - cy) * (bx - cx);
    }

}
