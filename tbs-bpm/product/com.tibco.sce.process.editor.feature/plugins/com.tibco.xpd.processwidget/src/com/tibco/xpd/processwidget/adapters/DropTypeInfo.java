/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processwidget.adapters;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.dnd.DND;

/**
 * Information regarding the drop type allowed by the process widget adapter
 * implementation to return from
 * {@link BaseProcessAdapter#getDropTypeInfo(java.util.List)}.
 * 
 * @author aallway
 * 
 */
public class DropTypeInfo {

    /** Drop not available for this target. */
    public static DropTypeInfo DROP_NONE = new DropTypeInfo(DND.DROP_NONE);

    /** Note that DROP_COPY only affects the drop cursor (adds [+]) */
    public static DropTypeInfo DROP_COPY = new DropTypeInfo(DND.DROP_COPY);

    /** Note that DROP_MOVE only affects the drop cursor (removes [+]) */
    public static DropTypeInfo DROP_MOVE = new DropTypeInfo(DND.DROP_MOVE);

    /** Note that DROP_LINK only affects the drop cursor (shows link cursor)) */
    public static DropTypeInfo DROP_LINK = new DropTypeInfo(DND.DROP_LINK);

    private int dndDropType = DND.DROP_NONE;

    private List<Rectangle> feedbackRectangles = null;

    /**
     * Drop type info.
     * 
     * @param dropType
     *            One of {@link DND}.DROP_xxx
     */
    public DropTypeInfo(int dropType) {
        this.dndDropType = dropType;
    }

    /**
     * Drop type info.
     * 
     * @param dropType
     *            One of {@link DND}.DROP_xxx
     * @param feedbackRectangles
     *            Feedback ghosted rectangles to display (or null if none
     *            required).
     */
    public DropTypeInfo(int dropType, List<Rectangle> feedbackRectangles) {
        this(dropType);
        this.feedbackRectangles = feedbackRectangles;
    }

    /**
     * @return the dropType
     */
    public int getDndDropType() {
        return dndDropType;
    }

    /**
     * @return the feedbackRectangles
     */
    public List<Rectangle> getFeedbackRectangles() {
        return feedbackRectangles;
    }

    /**
     * @param dropType
     *            the dropType to set
     */
    public void setDndDropType(int dropType) {
        this.dndDropType = dropType;
    }

    /**
     * @param feedbackRectangles
     *            the feedbackRectangles to set
     */
    public void setFeedbackRectangles(List<Rectangle> feedbackRectangles) {
        this.feedbackRectangles = feedbackRectangles;
    }

    public void setFeedbackRectangle(Rectangle rect) {
        feedbackRectangles = new ArrayList<Rectangle>();
        feedbackRectangles.add(rect);
    }

}
