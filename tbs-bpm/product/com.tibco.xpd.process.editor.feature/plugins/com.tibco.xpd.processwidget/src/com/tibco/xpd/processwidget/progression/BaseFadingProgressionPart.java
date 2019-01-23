/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression;

import java.util.Collections;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.neatstuff.TimedFadeUpController;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;

/**
 * <p>
 * Base object progression part that will show progression by fading the figure
 * for the diagram editpart up / down depending on its progression state.
 * <p>
 * In order to function correctly the figure for the diagram editpart in
 * question MUSt implement {@link IFadeableFigure}
 * 
 * @author aallway
 * @since 3.3 (2 Nov 2009)
 */
public class BaseFadingProgressionPart extends AbstractProgressionPart {

    /**
     * Time to fade figure up - Millisecs
     */
    public static final int FIGURE_FADEUP_PERIOD = 400;

    /**
     * Time to fade figure down - Millisecs
     */
    public static final int FIGURE_FADEDOWN_PERIOD = 150;

    public static final int DEFAULT_NOT_PROCESSED_STATE_ALPHA = 75;

    public static final int DEFAULT_INPROGRESS_STATE_ALPHA = 255;

    public static final int DEFAULT_COMPLETE_STATE_ALPHA = 190;

    public static final int DEFAULT_OTHER_STATE_ALPHA = 150;

    private TimedFadeUpController animatedFadeController = null;

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public BaseFadingProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);
    }

    @Override
    protected void initialiseToProgressionState(ProgressionState state,
            IFigure progressionFiguresLayer) {
        cancelTimedFade();

        IFigure figure = getDiagramObjectEditPart().getFigure();

        ProgressionState progressionState =
                getDiagramObjectProgressionModel().getProgressionState();

        if (figure instanceof IFadeableFigure) {
            ((IFadeableFigure) figure)
                    .setAlpha(getFigureAlphaForState(progressionState));
        }
        return;
    }

    @Override
    protected void revertToNonProgressionState(IFigure progressionControlsLayer) {
        cancelTimedFade();

        IFigure figure = getDiagramObjectEditPart().getFigure();

        if (figure instanceof IFadeableFigure) {
            ((IFadeableFigure) figure).setAlpha(255);

        }

        return;
    }

    @Override
    protected void showStateChange(ProgressionState oldState,
            ProgressionState newState, IFigure progressionControlsLayer) {
        IFigure figure = getDiagramObjectEditPart().getFigure();

        if (figure instanceof IFadeableFigure) {
            int initAlpha = getFigureAlphaForState(oldState);
            int finalAlpha = getFigureAlphaForState(newState);

            if (finalAlpha != initAlpha) {
                cancelTimedFade();

                if (shouldAnimateStateChange()) {
                    ((IFadeableFigure) figure).setAlpha(initAlpha);

                    long fadePeriod = FIGURE_FADEUP_PERIOD;
                    if (finalAlpha < initAlpha) {
                        fadePeriod = FIGURE_FADEDOWN_PERIOD;
                    }

                    animatedFadeController =
                            new TimedFadeUpController(Collections
                                    .singletonList((IFadeableFigure) figure),
                                    (initAlpha / 255.0f),
                                    (finalAlpha / 255.0f), fadePeriod);
                    animatedFadeController.start(1);

                } else {
                    ((IFadeableFigure) figure).setAlpha(finalAlpha);
                }
            }
        }

        return;
    }

    /**
     * @return true if transition between different states should be animated.
     */
    protected boolean shouldAnimateStateChange() {
        return true;
    }

    /**
     * Get the nominal alpha (transparency) level for the given progression
     * state.
     * 
     * @param progressionState
     * @return
     */
    protected int getFigureAlphaForState(ProgressionState progressionState) {
        if (ProgressionState.NOT_PROCESSED.equals(progressionState)) {
            return DEFAULT_NOT_PROCESSED_STATE_ALPHA;

        } else if (ProgressionState.IN_PROGRESS.equals(progressionState)) {
            return DEFAULT_INPROGRESS_STATE_ALPHA;

        } else if (ProgressionState.COMPLETE.equals(progressionState)) {
            return DEFAULT_COMPLETE_STATE_ALPHA;

        } else if (ProgressionState.OTHER.equals(progressionState)) {
            return DEFAULT_OTHER_STATE_ALPHA;

        }
        return 50;
    }

    /**
     * Cancel any timed fade up / down that my be going on.
     */
    protected void cancelTimedFade() {
        if (animatedFadeController != null) {
            if (animatedFadeController.isRunning()) {
                animatedFadeController.cancel();
            }
            animatedFadeController = null;
        }
        return;
    }
}
