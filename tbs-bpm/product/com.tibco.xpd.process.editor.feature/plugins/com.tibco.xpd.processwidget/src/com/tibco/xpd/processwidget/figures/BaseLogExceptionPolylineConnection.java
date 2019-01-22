/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.PolylineConnection;

import com.tibco.xpd.processwidget.ProcessWidgetPlugin;

/**
 * This class exists purely to catch (and log) exceptions tht are thrown during
 * a firePropertyChange() event.
 * <p>
 * By doing this, it can prevent things that are outside of our control that are
 * listening to figure events from messing things up badly. Often the figure
 * events are fired as a result of a model change - if an exception is thrown
 * during command execution it often seriously messes up the model AND the
 * diagram graphics etc.
 * 
 * @author aallway
 * @since 3.3 (8 Apr 2010)
 */
public class BaseLogExceptionPolylineConnection extends PolylineConnection {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#firePropertyChange(java.lang.String,
     * boolean, boolean)
     */
    @Override
    protected void firePropertyChange(String property, boolean old,
            boolean current) {
        try {
            super.firePropertyChange(property, old, current);
        } catch (Exception e) {
            ProcessWidgetPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Figure caught exception during firePropertyChange()"); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#firePropertyChange(java.lang.String,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    protected void firePropertyChange(String property, Object old,
            Object current) {
        try {
            super.firePropertyChange(property, old, current);
        } catch (Exception e) {
            ProcessWidgetPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Figure caught exception during firePropertyChange()"); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#firePropertyChange(java.lang.String, int,
     * int)
     */
    @Override
    protected void firePropertyChange(String property, int old, int current) {
        try {
            super.firePropertyChange(property, old, current);
        } catch (Exception e) {
            ProcessWidgetPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Figure caught exception during firePropertyChange()"); //$NON-NLS-1$
        }
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s[%d]: From(%s) To(%s)", //$NON-NLS-1$
                this.getClass().getSimpleName(),
                hashCode(),
                (getSourceAnchor() != null ? getSourceAnchor().getOwner()
                        : "null"), //$NON-NLS-1$
                (getTargetAnchor() != null ? getTargetAnchor().getOwner()
                        : "null")); //$NON-NLS-1$
    }

}
