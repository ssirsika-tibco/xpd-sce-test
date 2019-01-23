/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import org.eclipse.swt.widgets.Shell;

/**
 * Sticky tooltip with notification to the tooltip when it is being shown or
 * hidden.
 * <p>
 * Like {@link StickyToolTip}, this will cause diagram editor to keep the
 * tooltip displayed even when the mouse leaves the host figure and enters the
 * tooltip.
 * 
 * @author aallway
 * @since 3.5.10
 */
public interface StickyTooltipWithNotification extends StickyToolTip {

    /**
     * The tooltip is about to be displayed.
     * 
     * @param shell
     *            The shell that the figure is about to be shown in.
     */
    public void aboutToShow(Shell shell);

    /**
     * The tooltip has just been displayed
     */
    public void shown();

    /**
     * The tooltip is about to be hidden
     */
    public void aboutToHide();

    /**
     * The tooltip has just been hidden
     */
    public void hidden();

}
