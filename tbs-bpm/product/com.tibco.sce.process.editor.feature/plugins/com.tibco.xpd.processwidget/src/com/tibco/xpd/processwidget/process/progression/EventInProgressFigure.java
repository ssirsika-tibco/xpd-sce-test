/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

/**
 * Progression in-progress state highlight figure for event activity
 * 
 * @author aallway
 * @since 3.3 (27 Jan 2010)
 */
public class EventInProgressFigure extends AbstractPulsingInProgressFigure {

    private static final int ARC_DEGREES = 60;

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.process.progression.
     * AbstractActivityInProgressFigure#createPaths()
     */
    @Override
    protected Path[] createPaths() {
        Path[] paths = new Path[4];

        Rectangle b = getBounds().getCopy();

        b.shrink(2, 2);
        int arcSize = (int) (b.width * 1f);
        int arcOffset = (90 - ARC_DEGREES) / 2;

        int right = b.x + b.width;
        int bottom = b.y + b.height;

        int horzArcExtendLength = (int) (b.width * 0.15f);
        if (horzArcExtendLength < 2) {
            horzArcExtendLength = 2;
        }

        int vertArcExtendLength = (int) (b.height * 0.15f);
        if (vertArcExtendLength < 2) {
            vertArcExtendLength = 2;
        }

        /*
         * Top left corner
         */
        paths[0] = new Path(null);
        paths[0].addArc(b.x,
                b.y,
                arcSize,
                arcSize,
                180 - arcOffset,
                -ARC_DEGREES);

        /*
         * Top right corner
         */
        paths[1] = new Path(null);
        paths[1].addArc(right - arcSize,
                b.y,
                arcSize,
                arcSize,
                90 - arcOffset,
                -ARC_DEGREES);

        /*
         * Bottom rightcorner
         */
        paths[2] = new Path(null);
        paths[2].addArc(right - arcSize,
                bottom - arcSize,
                arcSize,
                arcSize,
                360 - arcOffset,
                -ARC_DEGREES);

        /*
         * Bottom left corner
         */
        paths[3] = new Path(null);
        paths[3].addArc(b.x,
                bottom - arcSize,
                arcSize,
                arcSize,
                270 - arcOffset,
                -ARC_DEGREES);

        return paths;
    }

}
