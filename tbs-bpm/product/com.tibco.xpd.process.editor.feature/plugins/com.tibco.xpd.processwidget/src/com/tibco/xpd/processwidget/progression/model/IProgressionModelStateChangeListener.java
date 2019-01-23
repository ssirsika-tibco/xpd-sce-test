/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processwidget.progression.model;

/**
 * {@link ProgressionState} change listener for use with
 * {@link ProgressionModel}
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public interface IProgressionModelStateChangeListener {

    /**
     * Notification of a ProgressionModel state change happening.
     * 
     * @param progressionStateChangeEvent
     */
    void progressionStateChanged(
            ProgressionStateChangeEvent progressionStateChangeEvent);

}
