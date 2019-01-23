/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.progression.model.IProgressionModelStateChangeListener;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;
import com.tibco.xpd.processwidget.progression.model.ProgressionStateChangeEvent;

/**
 * A progression part links a GEF diagram edit part with the progress model for
 * that diagram object.
 * <p>
 * The progression part is triggered to update visuals and controls when the
 * diagram progression is initialised or terminated AND most importantly when
 * the progression state model for the diagram object changes.
 * <p>
 * Nominally, most of this is handled internally in this abstract class leaving
 * the subclass to decide how to visualise a particilar progression state for a
 * particular diagram edit part.
 * 
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public abstract class AbstractProgressionPart implements
        IProgressionModelStateChangeListener, FigureListener {

    private ProgressionModel progressionModel;

    private GraphicalEditPart originalGraphicalEditPart;

    private IFigure progressionFiguresLayer;

    private boolean enabled = false;

    /**
     * @param progressionModel
     * @param originalGraphicalEditPart
     * @param progressionFiguresLayer
     */
    public AbstractProgressionPart(ProgressionModel progressionModel,
            GraphicalEditPart originalGraphicalEditPart,
            IFigure progressionFiguresLayer) {
        super();
        this.progressionFiguresLayer = progressionFiguresLayer;
        this.progressionModel = progressionModel;
        this.originalGraphicalEditPart = originalGraphicalEditPart;

        progressionModel.addObjectProgressionStateChangeListener(this);
    }

    /**
     * Initialise any progression feedback to the given state.
     * 
     * @param state
     *            Initial progression state.
     * @param progressionFiguresLayer
     *            Layer for any extra annotation figures to support the
     *            progression visuals
     */
    protected abstract void initialiseToProgressionState(
            ProgressionState state, IFigure progressionFiguresLayer);

    /**
     * Perform the display of the given state change.
     * 
     * @param oldState
     *            Old progression state
     * @param newState
     *            New progression state.
     * @param progressionFiguresLayer
     *            Layer for any extra annotation figures to support the
     *            progression visuals
     */
    protected abstract void showStateChange(ProgressionState oldState,
            ProgressionState newState, IFigure progressionFiguresLayer);

    /**
     * Reset the state figure back to how it was before any progression things
     * were performed.
     * 
     * @param progressionFiguresLayer
     *            Layer for any extra annotation figures to support the
     *            progression visuals
     */
    protected abstract void revertToNonProgressionState(
            IFigure progressionFiguresLayer);

    /**
     * Called when host edit part's figure changes the sub-class should locate
     * any progression feedback as required.
     * 
     * @param figure
     */
    protected void hostFigureMoved(IFigure figure) {
        return;
    };

    /**
     * @return the progressionFiguresLayer
     */
    public IFigure getProgressionFiguresLayer() {
        return progressionFiguresLayer;
    }

    /**
     * @return the progressionModel
     */
    public ProgressionModel getDiagramObjectProgressionModel() {
        return progressionModel;
    }

    /**
     * @return the diagram editor edit part.
     */
    public GraphicalEditPart getDiagramObjectEditPart() {
        return originalGraphicalEditPart;
    }

    /**
     * Initialise the figure from the progressModel state.
     */
    void initialiseProgressionVisuals() {
        ProgressionState initState = progressionModel.getProgressionState();

        initialiseToProgressionState(initState, getProgressionFiguresLayer());

        getDiagramObjectEditPart().getFigure().addFigureListener(this);
        return;
    }

    /**
     * Update any visuals to do with a progression state change on this part's
     * progression model.
     * 
     * @param oldState
     * @param newState
     */
    void updateProgressionVisuals(ProgressionState oldState,
            ProgressionState newState) {
        showStateChange(oldState, newState, getProgressionFiguresLayer());

        return;
    }

    /**
     * Reset the figure to its original state.
     */
    void terminateProgressionVisuals() {
        getDiagramObjectEditPart().getFigure().removeFigureListener(this);

        revertToNonProgressionState(getProgressionFiguresLayer());

        return;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     *            the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        return;
    }

    /**
     * Perform any final cleanup as necessary.
     */
    protected void dispose() {
        progressionModel.removeObjectProgressionStateChangeListener(this);
        return;
    }

    /**
     * Listener to ObjectProgressionModel changes for the progression model fo
     * the diagram object for this progression part.
     * 
     * @see com.tibco.xpd.processwidget.progression.model.IProgressionModelStateChangeListener#progressionStateChanged(com.tibco.xpd.processwidget.progression.model.ProgressionStateChangeEvent)
     * 
     * @param progressionStateChangeEvent
     */
    @Override
    public void progressionStateChanged(
            final ProgressionStateChangeEvent progressionStateChangeEvent) {
        if (isEnabled()) {
            Display.getDefault().asyncExec(new Runnable() {

                public void run() {
                    if (isEnabled()) {
                        updateProgressionVisuals(progressionStateChangeEvent
                                .getOldProgressionState(),
                                progressionStateChangeEvent
                                        .getNewProgressionState());
                    }

                    return;
                }

            });
        }
    }

    /**
     * @see org.eclipse.draw2d.FigureListener#figureMoved(org.eclipse.draw2d.IFigure)
     * 
     * @param source
     */
    @Override
    public void figureMoved(IFigure source) {
        /*
         * The diagram edit part's location has changed, move any progression
         * figures as required.
         */
        hostFigureMoved(source);
    }

}
