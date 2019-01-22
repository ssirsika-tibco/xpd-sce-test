/**
 * TopLeftProblemAnnotationsMarker.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.annotations.problems;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;

/**
 * TopLeftProblemAnnotationsMarker
 * 
 */
public class TextAnnotProblemsAnnotationsFigure extends
        AbstractProblemsAnnotationFigure {

    private Dimension imgSize;

    public TextAnnotProblemsAnnotationsFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);

    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        Rectangle bnds = getImage().getBounds();
        Dimension imgSize = new Dimension(bnds.width, bnds.height);

        Point ret = hostFigure.getBounds().getTopLeft();
        ret.x -= (int) (imgSize.width * 0.75f);
        ret.y -= (int) (imgSize.height * 0.75f);

        return ret;
    }

}
