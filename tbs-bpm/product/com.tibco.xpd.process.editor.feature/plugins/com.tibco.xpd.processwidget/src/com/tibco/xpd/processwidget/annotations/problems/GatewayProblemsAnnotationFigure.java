package com.tibco.xpd.processwidget.annotations.problems;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;

/**
 * GatewayProblemsAnnotationFigure
 * <p>
 * Places problem marker at 2 o'clock position of gateway.
 */
public class GatewayProblemsAnnotationFigure extends
        PolyLineShapeProblemsAnnotationFigure {

    public GatewayProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);
    }

    @Override
    protected int getProblemMarkerLocation() {
        return 13;
    }

}
