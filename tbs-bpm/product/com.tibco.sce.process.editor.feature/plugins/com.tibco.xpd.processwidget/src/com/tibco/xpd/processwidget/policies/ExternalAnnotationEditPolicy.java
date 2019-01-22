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

package com.tibco.xpd.processwidget.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationFactoryEx;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.resources.logger.Logger;

/**
 * @author wzurek
 */
public class ExternalAnnotationEditPolicy extends GraphicalEditPolicy implements
        AnnotationListener {

    private IFigure annotation;

    private final AnnotationFactory factory;

    private final Object layer;

    private static final Logger LOG =
            ProcessWidgetPlugin.getDefault().getLogger();

    public ExternalAnnotationEditPolicy(AnnotationFactory factory, Object layer) {
        this.factory = factory;
        this.layer = layer;
    }

    @Override
    public void activate() {
        super.activate();

        try {
            factory.registerListener(this);
        } catch (Exception e) {
            LOG
                    .error(e,
                            "Caught exception from annotation factory.registerListener()"); //$NON-NLS-1$
        }
    }

    @Override
    public void deactivate() {
        try {
            factory.unregisterListener(this);
        } catch (Exception e) {
            LOG
                    .error(e,
                            "Caught exception from annotation factory.unregisterListener()"); //$NON-NLS-1$
        }
        super.deactivate();
    }

    public void createAnnotations() {
        if (factory instanceof AnnotationFactoryEx) {
            try {
                annotation =
                        ((AnnotationFactoryEx) factory).createFigureEx(this,
                                getHost());
            } catch (Exception e) {
                LOG
                        .error(e,
                                "Caught exception from annotation factory.createFigureEx()"); //$NON-NLS-1$
            }

        } else {
            try {
                annotation =
                        factory.createFigure(this,
                                getHost().getModel(),
                                getHostFigure());
            } catch (Exception e) {
                LOG
                        .error(e,
                                "Caught exception from annotation factory.createFigure()"); //$NON-NLS-1$
            }
        }

        if (annotation != null) {
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    // System.out
                    //                            .println(".createAnnotations(): Adding: " + annotation.hashCode() + "  For:  " + getHostFigure()); //$NON-NLS-1$ //$NON-NLS-2$
                    try {
                        getLayer(layer).add(annotation);
                    } catch (Exception e) {
                        LOG
                                .error(e,
                                        "Caught exception from adding annotation to annotation layer"); //$NON-NLS-1$
                    }
                }
            });
        }
    }

    @Override
    protected IFigure getLayer(Object layer) {
        return super.getLayer(layer);
    }

    public void removeAnnotations() {
        if (annotation != null) {
            Display.getDefault().syncExec(new Runnable() {
                public void run() {
                    if (factory instanceof AnnotationFactoryEx) {
                        try {
                            ((AnnotationFactoryEx) factory)
                                    .removingAnnotationFigure(ExternalAnnotationEditPolicy.this,
                                            getHostFigure(),
                                            annotation);
                        } catch (Exception e) {
                            LOG
                                    .error(e,
                                            "Caught exception from annotation factory.removingAnnotationFigure()"); //$NON-NLS-1$
                        }
                    }

                    if (annotation != null) {
                        IFigure parent = annotation.getParent();
                        if (parent != null) {
                            // System.out
                            //                                    .println(".removeAnnotations(): Removing: " + annotation.hashCode() + "  from:  " + getHostFigure()); //$NON-NLS-1$ //$NON-NLS-2$
                            parent.remove(annotation);
                        }
                    }
                }
            });
        }
        annotation = null;
    }

    public Object getModelObject() {
        Object model = null;
        if (getHost() != null) {
            model = getHost().getModel();
        }
        return model;
    }

    public EditPart getHostEditPart() {
        return getHost();
    }
}
