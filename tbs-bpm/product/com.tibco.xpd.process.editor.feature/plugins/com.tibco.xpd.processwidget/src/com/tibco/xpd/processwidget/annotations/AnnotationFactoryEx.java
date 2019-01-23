/**
 * AnnotationFactoryEx.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processwidget.annotations;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;

/**
 * AnnotationFactoryEx
 * 
 */
public abstract class AnnotationFactoryEx implements AnnotationFactory {

    /**
     * This will be called in preference to the
     * {@link AnnotationFactory#createFigure(AnnotationListener, Object, IFigure)}
     * method.
     * 
     * @param listener
     * @param model
     * @param parent
     * @return
     */
    public abstract IFigure createFigureEx(AnnotationListener listener,
            EditPart hostEditPart);

    /**
     * The annotation figure is about to be removed from the host figure of the
     * model object that the annotation isassociated with.
     * 
     * @param listener
     * @param hostFigure
     * @param annotationFigure
     */
    public abstract void removingAnnotationFigure(AnnotationListener listener,
            IFigure hostFigure, IFigure annotationFigure);

    /**
     * Process widget will not use createFigure when AnnotationFactoryEx is
     * used
     */
    public IFigure createFigure(AnnotationListener listener, Object model,
            IFigure parent) {
        throw new RuntimeException(
                "old createFigure() method should nto be invoked by process widget."); //$NON-NLS-1$
    }

}
