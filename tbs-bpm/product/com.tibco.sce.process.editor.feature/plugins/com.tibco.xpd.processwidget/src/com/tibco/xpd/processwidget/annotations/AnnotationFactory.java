/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.annotations;

import org.eclipse.draw2d.IFigure;

/**
 * Please note that there is now an abstracft class AnnotationFactoryEx which
 * supports the annotation lifecycle a little better.
 * 
 * @author wzurek
 */
public interface AnnotationFactory {

    void registerListener(AnnotationListener listener);

    void unregisterListener(AnnotationListener listener);

    IFigure createFigure(AnnotationListener listener, Object model,
            IFigure parent);

    void disposeFactory();
}
