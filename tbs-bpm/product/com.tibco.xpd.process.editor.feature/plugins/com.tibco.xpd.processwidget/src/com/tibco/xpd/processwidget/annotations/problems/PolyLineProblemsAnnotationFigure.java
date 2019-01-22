package com.tibco.xpd.processwidget.annotations.problems;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.figures.RoundedPolylineConnection;
import com.tibco.xpd.processwidget.figures.XPDLineUtilities;

/**
 * PolyLineProblemsAnnotationFigure
 * <p>
 * Places marker at 50% along line length for host figure that's a polyline
 * connection.
 */
public class PolyLineProblemsAnnotationFigure extends
        AbstractProblemsAnnotationFigure {

    public PolyLineProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);

    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        if (!(hostFigure instanceof PolylineConnection)) {
            throw new RuntimeException(
                    "Unexpected hostFigure figure class\n  Expected:" //$NON-NLS-1$
                            + RoundedPolylineConnection.class.getName()
                            + "\n  Got: " + hostFigure.getClass().getName()); //$NON-NLS-1$
        }

        PolylineConnection polylineFigure = (PolylineConnection) hostFigure;

        Point ret =
                XPDLineUtilities.getLinePointFromPortion(polylineFigure
                        .getPoints(), 50);
        

        org.eclipse.swt.graphics.Rectangle imgBnds = getImage().getBounds();

        Dimension d = new Dimension(imgBnds.width, imgBnds.height);
        ret.translate((d.width/4), -(d.height/4)*3);

        return ret;

    }

}
