package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;

/**
 * <b>NonNegativeBendpoint</b>
 * <p>
 * Bendpoint based on relative bendpoint that will never return negative
 * co-ords.
 * <p>
 * Therefore when connections are copied/pasted to top of process etc the
 * bendpoints will not be shown in negative co-ordinate space.
 * 
 * @author aallway
 * 
 */
public class NonNegativeBendpoint extends RelativeBendpoint {

    public NonNegativeBendpoint() {
        super();
    }

    public NonNegativeBendpoint(Connection conn) {
        super(conn);
    }

    @Override
    public Point getLocation() {
        Point pt = super.getLocation();

        if (getConnection().getSourceAnchor() != null
                && getConnection().getTargetAnchor() != null) {
            Point srcRef =
                    getConnection().getSourceAnchor().getReferencePoint()
                            .getCopy();
            getConnection().translateToRelative(srcRef);
            if (pt.x == (srcRef.x - 1) || pt.x == (srcRef.x + 1)) {
                pt.x = srcRef.x;
            }
            if (pt.y == (srcRef.y - 1) || pt.y == (srcRef.y + 1)) {
                pt.y = srcRef.y;
            }

            Point tgtRef =
                    getConnection().getTargetAnchor().getReferencePoint()
                            .getCopy();
            getConnection().translateToRelative(srcRef);
            if (pt.x == (tgtRef.x - 1) || pt.x == (tgtRef.x + 1)) {
                pt.x = tgtRef.x;
            }
            if (pt.y == (tgtRef.y - 1) || pt.y == (tgtRef.y + 1)) {
                pt.y = tgtRef.y;
            }
        }
        
        if (pt.x < 0) {
            pt.x = 0;
        }

        if (pt.y < 0) {
            pt.y = 0;
        }

        if (pt instanceof PrecisionPoint) {
            ((PrecisionPoint) pt).preciseX = pt.x;
            ((PrecisionPoint) pt).preciseY = pt.y;
        }

        return pt;
    }
}
