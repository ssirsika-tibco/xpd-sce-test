/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression.model;

/**
 * Progression state for a diagram model object's {@link ProgressionModel}.
 * 
 * @author aallway
 * @since 3.3 (30 Oct 2009)
 */
public enum ProgressionState {

    NOT_PROCESSED,

    IN_PROGRESS,

    COMPLETE,

    /** Place extra info in {@link ProgressionModel} extended properties */
    OTHER

}
