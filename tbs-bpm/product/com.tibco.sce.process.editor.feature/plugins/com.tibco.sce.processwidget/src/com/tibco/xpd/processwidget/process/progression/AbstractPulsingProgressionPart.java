/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processwidget.process.progression;

import java.util.Collections;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;
import com.tibco.xpd.processwidget.neatstuff.PulsingFadeController;
import com.tibco.xpd.processwidget.neatstuff.PulsingFadeController.FadeDownAndCancelCompleteListener;
import com.tibco.xpd.processwidget.progression.BaseFadingProgressionPart;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;
import com.tibco.xpd.resources.ui.XpdColorRegistry;

/**
 * Progression part for process diagram objects.
 * <p>
 * The sub-class merely supplies a figure for highlighting the in-progress state
 * and this class deals with fading it up and down etc.
 * 
 * @author aallway
 * @since 3.3 (25 Jan 2010)
 */
public abstract class AbstractPulsingProgressionPart extends
        BaseFadingProgressionPart implements FadeDownAndCancelCompleteListener {

    private IFadeableFigure inProgressFigure = null;

    private PulsingFadeController inProgressFigurePulsingController = null;

    private Color inProgressFigureColor = XpdColorRegistry.getDefault()
            .getColor(null, 0, 200, 0);

    private int initPulsingAlpha = 50;

    private int finalPulsingAlpha = 255;

    private boolean shouldAnimateInProgressFigure = true;

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public AbstractPulsingProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super(progressionModel, originalGraphicalEditPart,
                progressionFiguresLayer);
    }

    /**
     * @return The figure to further highlight the in-progress status of the
     *         activity.
     */
    protected abstract IFadeableFigure createInProgressFigure(
            IFigure progressionFiguresLayer);

    /**
     * Locate the in progress figure according to the location on the host
     * figure (the figure for the diagram edit part that we are visualising the
     * progression of).
     * 
     * @param inProgressFigure
     * @param hostFigure
     */
    protected abstract void locateInProgressFigure(IFigure inProgressFigure,
            IFigure hostFigure);

    @Override
    protected void initialiseToProgressionState(ProgressionState state,
            IFigure progressionFiguresLayer) {
        super.initialiseToProgressionState(state, progressionFiguresLayer);

        cancelFadeOfInProgressFigure();

        updateInProgressFigureFromState(state, progressionFiguresLayer);

        return;
    }

    @Override
    protected void showStateChange(ProgressionState oldState,
            ProgressionState newState, IFigure progressionFiguresLayer) {
        super.showStateChange(oldState, newState, progressionFiguresLayer);

        updateInProgressFigureFromState(newState, progressionFiguresLayer);

        return;
    }

    @Override
    protected void revertToNonProgressionState(IFigure progressionFiguresLayer) {
        super.revertToNonProgressionState(progressionFiguresLayer);

        cancelFadeOfInProgressFigure();

        return;
    }

    /**
     * @see com.tibco.xpd.processwidget.progression.AbstractProgressionPart#hostFigureMoved(org.eclipse.draw2d.IFigure)
     * 
     * @param figure
     */
    @Override
    protected void hostFigureMoved(IFigure figure) {
        super.hostFigureMoved(figure);

        if (inProgressFigure != null) {
            locateInProgressFigure(inProgressFigure, figure);
        }
        return;
    }

    @Override
    protected void dispose() {
        cancelFadeOfInProgressFigure();

        if (inProgressFigure != null) {
            if (inProgressFigure.getParent() != null) {
                inProgressFigure.getParent().remove(inProgressFigure);
            }
            inProgressFigure = null;
        }
        super.dispose();
        return;
    }

    /**
     * Add / remove the in progress indication figure according to state.
     * 
     * @param state
     * @param progressionControlsLayer
     */
    protected void updateInProgressFigureFromState(ProgressionState state,
            IFigure progressionFiguresLayer) {
        if (ProgressionState.IN_PROGRESS.equals(state)) {
            addTaskInProgressFigure(progressionFiguresLayer);
        } else {
            removeTaskInProgressFigure(progressionFiguresLayer);
        }

        return;
    }

    /**
     * Add the in progress annotation figure if not already added.
     * <p>
     * Otherwise the in progress figure is simply made visible, located and if
     * necessary the pulising fader is switched on/off.
     * 
     * @param progressionControlsLayer
     */
    private void addTaskInProgressFigure(IFigure progressionFiguresLayer) {
        cancelFadeOfInProgressFigure(); /* just in case! */

        if (inProgressFigure == null) {
            inProgressFigure = createInProgressFigure(progressionFiguresLayer);
            if (inProgressFigure == null) {
                // just in case!
                return;
            }

            progressionFiguresLayer.add(inProgressFigure);
        }

        locateInProgressFigure(inProgressFigure, getDiagramObjectEditPart()
                .getFigure());

        inProgressFigure.setForegroundColor(inProgressFigureColor);

        if (shouldAnimateInProgressFigure) {
            startInProgressAnimation();

        } else {
            inProgressFigure
                    .setAlpha(getFigureAlphaForState(ProgressionState.IN_PROGRESS));
            inProgressFigure.setVisible(true);
        }

        return;
    }

    /**
     * 
     */
    private void startInProgressAnimation() {
        if (inProgressFigure != null) {
            inProgressFigure.setAlpha(initPulsingAlpha);
            inProgressFigure.setVisible(true);

            /*
             * Start the pulsing animation.
             */
            long fadePeriod = 400;
            long fadeDownPeriod = 120;

            inProgressFigurePulsingController =
                    new PulsingFadeController(
                            Collections.singletonList(inProgressFigure),
                            (initPulsingAlpha / 255.0f),
                            (finalPulsingAlpha / 255.0f), fadePeriod,
                            fadeDownPeriod, this);
            inProgressFigurePulsingController.start(1);
        }
    }

    /**
     * @param finalPulsingAlpha
     *            the finalPulsingAlpha to set
     */
    public void setFinalPulsingAlpha(int finalPulsingAlpha) {
        this.finalPulsingAlpha = finalPulsingAlpha;
    }

    /**
     * @param initPulsingAlpha
     *            the initPulsingAlpha to set
     */
    public void setInitPulsingAlpha(int initPulsingAlpha) {
        this.initPulsingAlpha = initPulsingAlpha;
    }

    private void removeTaskInProgressFigure(IFigure progressionFiguresLayer) {
        /*
         * If the pulsing animation is running cancel it after fading down.
         */
        if (inProgressFigurePulsingController != null) {
            // inProgressFigurePulsingController.fadeDownAndCancel(100);
            cancelFadeOfInProgressFigure();
        } else {
            if (inProgressFigure != null) {
                inProgressFigure.setVisible(false);
            }
        }
        return;
    }

    /**
     * Cancel any timed fade up / down that my be going on.
     */
    protected void cancelFadeOfInProgressFigure() {
        if (inProgressFigurePulsingController != null) {
            if (inProgressFigurePulsingController.isRunning()) {
                inProgressFigurePulsingController.cancel();
            }
            inProgressFigurePulsingController = null;
        }

        if (inProgressFigure != null) {
            inProgressFigure.setVisible(false);
        }

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.neatstuff.PulsingFadeController.
     * FadeDownAndCancelCompleteListener#fadeDownAndCancelComplete()
     */
    @Override
    public void fadeDownAndCancelCompleteEvent() {
        if (inProgressFigure != null) {
            /*
             * Following checks are to ensure we don't make it invisible if its
             * been restarted!
             */
            if (inProgressFigurePulsingController == null
                    || inProgressFigurePulsingController.isCancelled()) {
                /*
                 * Hide the in progress figure after a fade down and cancel has
                 * completed.
                 */
                inProgressFigure.setVisible(false);
            }
        }
        return;
    }

    /**
     * @param shouldAnimateInProgressFigure
     *            the shouldAnimateInProgressFigure to set
     */
    public void setShouldAnimateInProgressFigure(
            boolean shouldAnimateInProgressFigure) {
        if (this.shouldAnimateInProgressFigure != shouldAnimateInProgressFigure) {
            this.shouldAnimateInProgressFigure = shouldAnimateInProgressFigure;

            if (!shouldAnimateInProgressFigure) {
                if (inProgressFigurePulsingController != null) {
                    if (inProgressFigurePulsingController.isRunning()) {
                        inProgressFigurePulsingController.cancel();
                    }
                    inProgressFigurePulsingController = null;
                }

                if (inProgressFigure != null) {
                    inProgressFigure
                            .setAlpha(getFigureAlphaForState(ProgressionState.IN_PROGRESS));
                }

            } else {
                if (ProgressionState.IN_PROGRESS
                        .equals(getDiagramObjectProgressionModel()
                                .getProgressionState())
                        && inProgressFigure != null) {
                    if (inProgressFigurePulsingController == null) {
                        startInProgressAnimation();
                    }
                }
            }
        }
        return;
    }

    /**
     * @return the shouldAnimateInProgressFigure
     */
    public boolean isShouldAnimateInProgressFigure() {
        return shouldAnimateInProgressFigure;
    }

    /**
     * @return the inProgressFigure
     */
    protected IFadeableFigure getInProgressFigure() {
        return inProgressFigure;
    }
}
