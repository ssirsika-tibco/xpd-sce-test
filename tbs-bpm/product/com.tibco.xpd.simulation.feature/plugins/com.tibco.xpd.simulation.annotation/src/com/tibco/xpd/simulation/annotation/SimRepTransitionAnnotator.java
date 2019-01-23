/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;

import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.provider.SimRepItemProviderAdapterFactory;

/**
 * @author nwilson
 */
public class SimRepTransitionAnnotator implements INotifyChangedListener {
    /** The annotation figure. */
    private TransitionConnectionAnnotationFigure figure;

    /**
     * @param fromSimActivity From activity.
     * @param toSimActivity To activity.
     * @param factory Item provider adapter factory.
     * @param parent Parent figure.
     */
    public SimRepTransitionAnnotator(SimRepActivity fromSimActivity,
            SimRepActivity toSimActivity,
            SimRepItemProviderAdapterFactory factory, IFigure parent) {
        figure = new TransitionConnectionAnnotationFigure(parent);
    }

    /**
     * Disposes of any resources.
     */
    public void dispose() {
    }

    /**
     * @return The figure.
     */
    public IFigure getFigure() {
        return figure;
    }

    /**
     * @param notification The change notification.
     * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(
     *      org.eclipse.emf.common.notify.Notification)
     */
    public void notifyChanged(Notification notification) {
    }
}
