/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.annotations.problems;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.adapters.BaseProcessAdapter;
import com.tibco.xpd.processwidget.annotations.AbstractImageAnnotationFigure;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipProviderFigure;

/**
 * AbstractProblemsAnnotationFigure
 * 
 */
public abstract class AbstractProblemsAnnotationFigure extends
        AbstractImageAnnotationFigure implements FigureListener,
        AncestorListener {

    public AbstractProblemsAnnotationFigure(IFigure hostFigure, Image img,
            BaseProcessAdapter modelAdapter) {
        super(hostFigure, img);

        QuickFixToolTipProviderFigure quickFixToolTip =
                new QuickFixToolTipProviderFigure(modelAdapter,
                        new ProcessQuickFixToolTipContentProvider());

        setToolTip(quickFixToolTip);

    }

}
