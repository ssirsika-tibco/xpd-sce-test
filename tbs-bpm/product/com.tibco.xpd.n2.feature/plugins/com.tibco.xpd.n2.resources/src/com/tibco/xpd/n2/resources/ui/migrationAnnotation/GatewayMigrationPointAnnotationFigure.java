/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.n2.resources.ui.migrationAnnotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * Migration point annotation figure for Gateway activities.
 * 
 * @author aallway
 * @since 18 Jun 2012
 */
class GatewayMigrationPointAnnotationFigure extends
        AbstractMigrationPointAnnotationFigure {

    /**
     * @param diagramObjectFigure
     */
    GatewayMigrationPointAnnotationFigure(IFigure diagramObjectFigure) {
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
        if (!(diagramObjectFigure instanceof ShapeWithDescriptionFigure)) {
            throw new RuntimeException(
                    "Unexpected hostFigure figure class\n  Expected:" //$NON-NLS-1$
                            + ShapeWithDescriptionFigure.class.getName()
                            + "\n  Got: " + diagramObjectFigure.getClass().getName()); //$NON-NLS-1$
        }

        ShapeWithDescriptionFigure figureWithPolyLineShape =
                (ShapeWithDescriptionFigure) diagramObjectFigure;
        if (figureWithPolyLineShape.getReferenceShapePoints() == null
                || figureWithPolyLineShape.getShape() == null) {
            throw new RuntimeException(
                    "Illegal hostFigure figure state (expected ShaeWithDescriptionFigure with shape points setup."); //$NON-NLS-1$
        }

        // It's ok - host figure is always the same as our stored figure.
        Point plOffset = figureWithPolyLineShape.getReferencePointOffset();
        PointList pl =
                figureWithPolyLineShape.getReferenceShapePoints().getCopy();
        /*
         * Need to physically add in the first point as last point (because
         * XPDLineUtiliti4es.getLinePointFromPortion() does not treat polyline
         * as a 'closed figure'.
         */
        pl.addPoint(pl.getFirstPoint());

        if (plOffset != null && pl != null && pl.size() > 0) {

            Point offset =
                    figureWithPolyLineShape.getBounds().getLocation()
                            .translate(plOffset);
            pl.translate(offset);

            Point location = XPDLineUtilities.getLinePointFromPortion(pl, 0f);

            Dimension d =
                    new Dimension(getImage().getBounds().width, getImage()
                            .getBounds().height);

            location.translate((int) (-d.width * 1.25f),
                    (int) (-d.height * 0.0));

            return location;
        }

        return figureWithPolyLineShape.getBounds().getTopRight();
    }
}
