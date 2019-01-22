/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processwidget.figures;

/**
 * Shape with description figure specifically for events.
 * 
 * @author aallway
 * @since 15 Mar 2012
 */
public class EventFigure extends ShapeWithDescriptionFigure {

    private TaskFigure attachedToTaskFigure = null;

    /**
     * @param attachedToTaskFigure
     *            the attachedToTaskFigure to set
     */
    public void setAttachedToTaskFigure(TaskFigure attachedToTaskFigure) {
        this.attachedToTaskFigure = attachedToTaskFigure;
    }

    /**
     * @return the attachedToTaskFigure
     */
    public TaskFigure getAttachedToTaskFigure() {
        return attachedToTaskFigure;
    }

    public boolean isAttachedToTask() {
        return attachedToTaskFigure != null;
    }

}
