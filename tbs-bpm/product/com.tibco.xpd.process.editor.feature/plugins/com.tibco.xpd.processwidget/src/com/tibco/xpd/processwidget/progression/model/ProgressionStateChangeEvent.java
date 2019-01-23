/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression.model;

/**
 * {@link ProgressionModel} progression state change event.
 * <p>
 * Passed to a {@link ProgressionModel}
 * {@link IProgressionModelStateChangeListener}'s when the progression state is
 * changed.
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public class ProgressionStateChangeEvent {

    private ProgressionModel progressionModel;

    private ProgressionState oldProgressionState;

    private ProgressionState newProgressionState;

    /**
     * Construct a ProgressionModelStateChangeEvent
     * 
     * @param progressionModel
     * @param oldProgressionState
     * @param newProgressionState
     */
    public ProgressionStateChangeEvent(ProgressionModel progressionModel,
            ProgressionState oldProgressionState,
            ProgressionState newProgressionState) {
        super();
        this.progressionModel = progressionModel;
        this.oldProgressionState = oldProgressionState;
        this.newProgressionState = newProgressionState;
    }

    /**
     * @return the progressionModel
     */
    public ProgressionModel getProgressionModel() {
        return progressionModel;
    }

    /**
     * @return the oldProgressionState
     */
    public ProgressionState getOldProgressionState() {
        return oldProgressionState;
    }

    /**
     * @return the newProgressionState
     */
    public ProgressionState getNewProgressionState() {
        return newProgressionState;
    }

}
