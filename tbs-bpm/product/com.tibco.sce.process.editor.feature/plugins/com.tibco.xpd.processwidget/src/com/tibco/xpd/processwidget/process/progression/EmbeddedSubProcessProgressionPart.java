/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;

/**
 * EmbeddedSubProcessProgressionPart
 * <p>
 * Generally, embedded sub-processes are too large and too costly to perform an
 * animated fade up / down like other objects so we just directly fade up /
 * down.
 * 
 * @author aallway
 * @since 3.3 (6 Nov 2009)
 */
public class EmbeddedSubProcessProgressionPart extends TaskProgressionPart {

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public EmbeddedSubProcessProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);

        setShouldAnimateInProgressFigure(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.progression.BaseFadingProgressionPart#
     * shouldAnimateStateChange()
     */
    @Override
    protected boolean shouldAnimateStateChange() {
        return false;
    }

    @Override
    protected void initialiseToProgressionState(ProgressionState state,
            IFigure progressionFiguresLayer) {
        super.initialiseToProgressionState(state, progressionFiguresLayer);

        return;
    }

    @Override
    protected void showStateChange(ProgressionState oldState,
            ProgressionState newState, IFigure progressionFiguresLayer) {
        super.showStateChange(oldState, newState, progressionFiguresLayer);

        return;
    }

}
