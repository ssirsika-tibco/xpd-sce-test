/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * Migration point annotation figure for Task activities.
 * 
 * @author aallway
 * @since 18 Jun 2012
 */
class TaskMigrationPointAnnotationFigure extends
        AbstractMigrationPointAnnotationFigure {

    /**
     * @param diagramObjectFigure
     */
    TaskMigrationPointAnnotationFigure(IFigure diagramObjectFigure) {
        super(diagramObjectFigure);
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure#calculateLocation(org.eclipse.draw2d.IFigure)
     * 
     * @param diagramObjectFigure
     * @return
     */
    @Override
    protected Point calculateLocation(IFigure diagramObjectFigure) {
        Dimension sz = this.getSize();

        Point topLeft = diagramObjectFigure.getBounds().getTopLeft();

        return new Point(topLeft.x - (int) (sz.width * 0.6), topLeft.y
                - (int) (sz.height * 0.6));
    }

}
