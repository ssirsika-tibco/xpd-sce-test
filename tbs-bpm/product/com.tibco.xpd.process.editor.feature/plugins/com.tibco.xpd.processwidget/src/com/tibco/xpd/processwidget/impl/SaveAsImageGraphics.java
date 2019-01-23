package com.tibco.xpd.processwidget.impl;

/**
 * This class only exists so that HeadFigure can tell that we are performing
 * a save as image rather than a print to screen and undo scaling as necessary. 
 */

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.swt.graphics.GC;

public class SaveAsImageGraphics extends SWTGraphics {

    public SaveAsImageGraphics(GC gc) {
        super(gc);
    }

}
