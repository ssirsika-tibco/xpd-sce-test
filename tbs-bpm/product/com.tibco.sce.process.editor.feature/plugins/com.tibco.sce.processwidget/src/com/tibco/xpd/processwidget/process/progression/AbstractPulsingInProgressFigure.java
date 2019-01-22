package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.swt.graphics.Path;

import com.tibco.xpd.processwidget.neatstuff.AbstractBalloonPathFigure;

/**
 * Figure to mark out a diagram object as in progress.
 * <p>
 * The subclass provides a set of {@link Path}'s that are drawn to highlight the
 * in-progress diagram object.
 * 
 * @author aallway
 * @since 3.3 (25 Jan 2010)
 */
public abstract class AbstractPulsingInProgressFigure extends
        AbstractBalloonPathFigure {

    public AbstractPulsingInProgressFigure() {
        super();
    }

}