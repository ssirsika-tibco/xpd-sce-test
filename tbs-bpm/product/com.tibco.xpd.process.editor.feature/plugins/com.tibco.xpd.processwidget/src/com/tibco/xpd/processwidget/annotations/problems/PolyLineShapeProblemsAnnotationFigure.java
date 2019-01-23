package com.tibco.xpd.processwidget.annotations.problems;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * PolyLineShapeProblemsAnnotationFigure
 * <p>
 * Places marker at a given % proprtion around ShapeWithDescriptionFigure's poly
 * line shape border.
 */
public abstract class PolyLineShapeProblemsAnnotationFigure extends
        AbstractProblemsAnnotationFigure implements PropertyChangeListener {

    private ShapeWithDescriptionFigure figureWithPolyLineShape;

    public PolyLineShapeProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);

    }

    /**
     * @return The percent (0-100) location around poly line shape's border
     *         where the problem marker should be placed.
     */
    protected abstract int getProblemMarkerLocation();

    /**
     * Override default behaviour so that we can listen to the poly line shape
     * figure within the ShapeDescription figure.
     */
    protected void setupFigureMovedListener(IFigure hostFigure) {
        if (!(hostFigure instanceof ShapeWithDescriptionFigure)) {
            throw new RuntimeException(
                    "Unexpected hostFigure figure class\n  Expected:" //$NON-NLS-1$
                            + ShapeWithDescriptionFigure.class.getName()
                            + "\n  Got: " + hostFigure.getClass().getName()); //$NON-NLS-1$
        }

        figureWithPolyLineShape = (ShapeWithDescriptionFigure) hostFigure;
        if (figureWithPolyLineShape.getReferenceShapePoints() == null
                || figureWithPolyLineShape.getShape() == null) {
            throw new RuntimeException(
                    "Illegal hostFigure figure state (expected ShaeWithDescriptionFigure with shape points setup."); //$NON-NLS-1$
        }

        figureWithPolyLineShape.getShape()
                .addPropertyChangeListener(Connection.PROPERTY_POINTS, this);
    }

    @Override
    protected void removeFigureMovedListener(IFigure parent) {
        IFigure shape = figureWithPolyLineShape.getShape();
        if (shape != null) {
            shape.addPropertyChangeListener(Connection.PROPERTY_POINTS, this);
        }
    }

    /**
     * Respond to shape ploy line points changing.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (Connection.PROPERTY_POINTS.equals(evt.getPropertyName())) {
            figureMoved(figureWithPolyLineShape);
        }
    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        // It's ok - host figure is always the same as our stored figure.
        Point plOffset = figureWithPolyLineShape.getReferencePointOffset();
        PointList pl =
                figureWithPolyLineShape.getReferenceShapePoints().getCopy();

        if (plOffset != null && pl != null && pl.size() > 0) {

            Point offset =
                    figureWithPolyLineShape.getBounds().getLocation()
                            .translate(plOffset);
            pl.translate(offset);

            Point location =
                    XPDLineUtilities.getLinePointFromPortion(pl,
                            getProblemMarkerLocation());

            Dimension d =
                    new Dimension(getImage().getBounds().width, getImage()
                            .getBounds().height);

            location.translate(-(d.width / 4), -(d.height / 2));

            return location;
        }

        return figureWithPolyLineShape.getBounds().getTopRight();
    }

}
