/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;

/**
 * Basic progression part factory for diagrams.
 * <p>
 * If this is not overridden then NO progression is shown (a default No-Op
 * progression part is allocated - which is ideal for diagram edit parts that
 * need no progression annotation
 * 
 * 
 * @author aallway
 * @since 3.3 (2 Nov 2009)
 */
public abstract class AbstractProgressionPartFactory {

    /**
     * Create an appropriate {@link AbstractProgressionPart} for the given
     * progression model and process diagram object edit part.
     * 
     * @param progressionModel
     * @param originalGraphicalEditPart
     * 
     * @return Progression Edit Part.
     */
    public AbstractProgressionPart createObjectProgressionPart(
            ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {

        return new NoOpProgressionEditPart(progressionModel,
                originalGraphicalEditPart, progressionFiguresLayer);
    }

    /**
     * NoOpProgressionEditPart
     * <p>
     * Default no-operation (does nothing on change to progression state)
     * progresison part.
     * 
     * @author aallway
     * @since 3.3 (3 Nov 2009)
     */
    public static class NoOpProgressionEditPart extends AbstractProgressionPart {

        public NoOpProgressionEditPart(ProgressionModel progressionModel,
                GraphicalEditPart originalGraphicalEditPart,
                IFigure progressionFiguresLayer) {
            super(progressionModel, originalGraphicalEditPart,
                    progressionFiguresLayer);
        }

        @Override
        protected void initialiseToProgressionState(ProgressionState state,
                IFigure progressionFiguresLayer) {
            return;
        }

        @Override
        protected void revertToNonProgressionState(
                IFigure progressionFiguresLayer) {
            return;
        }

        @Override
        protected void showStateChange(ProgressionState oldState,
                ProgressionState newState, IFigure progressionFiguresLayer) {
            return;
        }

    }

}
