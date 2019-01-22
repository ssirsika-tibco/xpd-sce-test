/**
 * TopLeftProblemAnnotationsMarker.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.annotations.problems;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;

/**
 * TopLeftProblemAnnotationsMarker
 *
 */
public class TopLeftProblemsAnnotationsFigure extends
        AbstractProblemsAnnotationFigure {

    public TopLeftProblemsAnnotationsFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img, modelAdapter);
    }

    @Override
    protected Point calculateLocation(IFigure hostFigure) {
        Point ret = hostFigure.getBounds().getTopLeft();

        return ret;
    }

}
