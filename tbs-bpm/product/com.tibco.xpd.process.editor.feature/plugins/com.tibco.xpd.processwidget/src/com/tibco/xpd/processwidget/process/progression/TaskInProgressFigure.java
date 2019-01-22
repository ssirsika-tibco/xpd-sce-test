/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

import com.tibco.xpd.processwidget.figures.TaskFigure;

/**
 * Progression in-progress state highlight figure for task activity
 * 
 * @author aallway
 * @since 3.3 (27 Jan 2010)
 */
public class TaskInProgressFigure extends AbstractPulsingInProgressFigure {

    private static final int ARC_SIZE = TaskFigure.CORNER_ARC + 4;

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

        int right = b.x + b.width;
        int bottom = b.y + b.height;

        int horzArcExtendLength = (b.width / 2) - ARC_SIZE - 10;
        if (horzArcExtendLength < 2) {
            horzArcExtendLength = 2;
        }

        int vertArcExtendLength = (b.height / 2) - ARC_SIZE - 10;
        if (vertArcExtendLength < 2) {
            vertArcExtendLength = 2;
        }

        /*
         * Top left corner
         */
        paths[0] = new Path(null);
        paths[0].moveTo(b.x, b.y + ARC_SIZE + vertArcExtendLength);
        paths[0].lineTo(b.x, b.y + ARC_SIZE);
        paths[0].addArc(b.x, b.y, ARC_SIZE, ARC_SIZE, 180, -90);
        paths[0].lineTo(b.x + ARC_SIZE + horzArcExtendLength, b.y);

        /*
         * Top right corner
         */
        paths[1] = new Path(null);
        paths[1].moveTo(right - ARC_SIZE - horzArcExtendLength, b.y);
        paths[1].lineTo(right - ARC_SIZE, b.y);
        paths[1].addArc(right - ARC_SIZE, b.y, ARC_SIZE, ARC_SIZE, 90, -90);
        paths[1].lineTo(right, b.y + ARC_SIZE + vertArcExtendLength);

        /*
         * Bottom rightcorner
         */
        paths[2] = new Path(null);
        paths[2].moveTo(right, bottom - ARC_SIZE - vertArcExtendLength);
        paths[2].lineTo(right, bottom - ARC_SIZE);
        paths[2].addArc(right - ARC_SIZE,
                bottom - ARC_SIZE,
                ARC_SIZE,
                ARC_SIZE,
                0,
                -90);
        paths[2].lineTo(right - ARC_SIZE - horzArcExtendLength, bottom);

        /*
         * Bottom left corner
         */
        paths[3] = new Path(null);
        paths[3].moveTo(b.x + ARC_SIZE + horzArcExtendLength, bottom);
        paths[3].lineTo(b.x + ARC_SIZE, bottom);
        paths[3].addArc(b.x, bottom - ARC_SIZE, ARC_SIZE, ARC_SIZE, 270, -90);
        paths[3].lineTo(b.x, bottom - ARC_SIZE - vertArcExtendLength);

        return paths;
    }

}
