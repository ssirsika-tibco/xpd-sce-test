package com.tibco.xpd.processwidget.annotations.problems;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;

/**
 * DefaultProblemsAnnotationFigure
 * <p>
 * Places problem marker at top right of hostFigure bounds.
 */
public class DefaultProblemsAnnotationFigure extends
        AbstractProblemsAnnotationFigure {

    public DefaultProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);
    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        Point ret = hostFigure.getBounds().getTopRight();

        Dimension d =
                new Dimension(getImage().getBounds().width, getImage()
                        .getBounds().height);

        ret.translate((int) -(d.width * 0.33f), (int) -(d.height * 0.66f));

        return ret;
    }

}
