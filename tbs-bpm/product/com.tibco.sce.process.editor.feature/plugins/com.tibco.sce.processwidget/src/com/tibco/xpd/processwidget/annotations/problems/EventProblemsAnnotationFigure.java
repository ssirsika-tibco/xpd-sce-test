package com.tibco.xpd.processwidget.annotations.problems;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.figures.ShapeWithDescriptionFigure;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * EventProblemsAnnotationFigure
 * <p>
 * Plavces problem maker at 2 o'clock position of event figure.
 */
public class EventProblemsAnnotationFigure extends
        AbstractProblemsAnnotationFigure {

    public EventProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);
    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        if (!(hostFigure instanceof ShapeWithDescriptionFigure)
                || !(((ShapeWithDescriptionFigure) hostFigure).getShape() instanceof Ellipse)) {
            throw new RuntimeException(
                    "Unexpected figure for Event object class type (excepted: " //$NON-NLS-1$
                            + ShapeWithDescriptionFigure.class.getName()
                            + "  configured with an Ellipse class shape."); //$NON-NLS-1$
        }

        Shape shape =
                (Shape) ((ShapeWithDescriptionFigure) hostFigure).getShape();

        /* Angle around circumference staring at mid-right pos as 0 degrees */
        double angle = 315;

        Rectangle b = shape.getBounds().getCopy();

        org.eclipse.swt.graphics.Rectangle imgBnds = getImage().getBounds();

        Point ret =
                XPDLineUtilities.getPointOnLineFromAngle(b.getCenter(),
                        (b.width / 2),
                        angle);

        ret.translate(-(int) (imgBnds.width * 0.15),
                -(int) (imgBnds.height * 0.66));

        return ret;
    }
}
