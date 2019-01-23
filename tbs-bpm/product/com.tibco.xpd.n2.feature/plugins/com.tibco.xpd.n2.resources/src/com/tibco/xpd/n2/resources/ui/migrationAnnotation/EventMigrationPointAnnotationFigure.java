/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * Migration point annotation figure for Event activities.
 * 
 * @author aallway
 * @since 18 Jun 2012
 */
class EventMigrationPointAnnotationFigure extends
        AbstractMigrationPointAnnotationFigure {

    /**
     * @param diagramObjectFigure
     */
    EventMigrationPointAnnotationFigure(IFigure diagramObjectFigure) {
        super(((ShapeWithDescriptionFigure) diagramObjectFigure).getShape());
    }

    /**
     * @see com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure#calculateLocation(org.eclipse.draw2d.IFigure)
     * 
     * @param diagramObjectFigure
     * @return
     */
    @Override
    protected Point calculateLocation(IFigure hostFigurediagramObjectFigure) {
        Rectangle b = hostFigurediagramObjectFigure.getBounds().getCopy();

        /* Angle around circumference staring at mid-right pos as 0 degrees */
        double angle = 225;

        org.eclipse.swt.graphics.Rectangle imgBnds = getImage().getBounds();

        Point ret =
                XPDLineUtilities.getPointOnLineFromAngle(b.getCenter(),
                        (b.width / 2) + imgBnds.width,
                        angle);

        return ret;
    }

}
