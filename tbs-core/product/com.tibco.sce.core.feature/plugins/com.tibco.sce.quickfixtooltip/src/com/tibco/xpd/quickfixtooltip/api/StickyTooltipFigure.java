/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.quickfixtooltip.api;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FocusEvent;
import org.eclipse.draw2d.FocusListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * A {@link StickyTooltipWithNotification} figure implementation that handles
 * selection state display and allows you to provide the content of the tooltip
 * as a figure.
 * <p>
 * This takes advantage of the {@link StickyToolTipHelper} that can be used by
 * any GEF editor as the domain event dispatcher (and must be if this tooltip is
 * to be sticky).
 * 
 * @author aallway
 * @since 3.5.10
 */
public class StickyTooltipFigure extends Figure implements
        StickyTooltipWithNotification {

    private int selectionBorderSize = 3;

    private int horizontalMargin = selectionBorderSize + 1;

    private int verticalMargin = selectionBorderSize + 1;

    private Dimension contentPreferredSize = new Dimension(10, 10);

    private IFigure contentFigure;

    private boolean paintMouseRollover = false;

    private boolean isSelected = false;

    private Shell popupShell = null;

    /**
     * Create a StickyTooltipFigure with the given figure as it's content.
     * 
     * @param contentFigure
     */
    public StickyTooltipFigure(IFigure contentFigure) {
        this();
        setContentFigure(contentFigure);

    }

    /**
     * Sid SNA-20. Allow construction prior to creation of tooltip content.
     * 
     * Create a StickyTooltipFigure the {@link #setContentFigure(IFigure)}
     * method should be called to add visual figure to display.
     * 
     * @param contentFigure
     */
    public StickyTooltipFigure() {
        super();
        SelectionStatusMouseListener selectionMouseListener =
                new SelectionStatusMouseListener();

        this.addMouseMotionListener(selectionMouseListener);
        this.addMouseListener(selectionMouseListener);
        this.addFocusListener(selectionMouseListener);
        this.setFocusTraversable(true);
        this.setRequestFocusEnabled(true);

    }

    /**
     * @param contentFigure
     */
    public void setContentFigure(IFigure contentFigure) {
        if (this.contentFigure != null) {
            this.remove(this.contentFigure);
        }

        this.contentFigure = contentFigure;
        this.add(contentFigure);
    }

    /**
     * @return the contentFigure
     */
    public IFigure getContentFigure() {
        return contentFigure;
    }

    /**
     * @see com.tibco.xpd.quickfixtooltip.api.StickyTooltipWithNotification#aboutToShow(org.eclipse.swt.widgets.Shell)
     * 
     * @param shell
     */
    @Override
    public void aboutToShow(Shell shell) {
        setBackgroundColor(ColorConstants.tooltipBackground);
        setForegroundColor(ColorConstants.tooltipForeground);
        this.setOpaque(true);

        popupShell = shell;
        if (this.contentFigure != null) {
            updateContentFigure(contentFigure);
        }
    }

    /**
     * Gives the sub-class chance to update the content figure - normally called
     * just before the tooltip control is layed out then displayed.
     * 
     * @param contentFigure
     */
    protected void updateContentFigure(IFigure contentFigure) {
        return;
    }

    /**
     * @see com.tibco.xpd.quickfixtooltip.api.StickyTooltipWithNotification#shown()
     * 
     */
    @Override
    public void shown() {
    }

    /**
     * @see com.tibco.xpd.quickfixtooltip.api.StickyTooltipWithNotification#aboutToHide()
     * 
     */
    @Override
    public void aboutToHide() {
    }

    /**
     * @see com.tibco.xpd.quickfixtooltip.api.StickyTooltipWithNotification#hidden()
     * 
     */
    @Override
    public void hidden() {
        popupShell = null;
        setSelected(false);
    }

    /**
     * 
     * @see org.eclipse.draw2d.Figure#getPreferredSize(int, int)
     * 
     * @param wHint
     * @param hHint
     * @return
     */
    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        if (contentFigure != null) {
            contentPreferredSize = contentFigure.getPreferredSize(wHint, hHint);
        } else {
            contentPreferredSize = new Dimension(20, 10);
        }

        Dimension size = contentPreferredSize.getCopy();
        size.width += horizontalMargin * 2;
        size.height += verticalMargin * 2;
        return size;
    }

    /**
     * @see org.eclipse.draw2d.Figure#layout()
     * 
     */
    @Override
    protected void layout() {
        Rectangle bnds = getBounds().getCopy();

        bnds.x = horizontalMargin;
        bnds.y = verticalMargin;

        bnds.width -= horizontalMargin * 2;
        bnds.height -= verticalMargin * 2;

        if (contentFigure != null) {
            contentFigure.setBounds(bnds);
        }
    }

    /**
     * @see org.eclipse.draw2d.Figure#useLocalCoordinates()
     * 
     * @return
     */
    @Override
    protected boolean useLocalCoordinates() {
        return true;
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
     * 
     * @param graphics
     */
    @Override
    protected void paintFigure(Graphics gr) {
        try {
            gr.pushState();

            Rectangle b = getBounds();

            if (isSelected) {
                gr.setLineWidth(selectionBorderSize);
                gr.setForegroundColor(ColorConstants.lightGray);

                gr.drawRectangle(b.x + (selectionBorderSize / 2), b.y
                        + (selectionBorderSize / 2), b.width
                        - selectionBorderSize, b.height - selectionBorderSize);
            }

            if (paintMouseRollover) {
                gr.setLineWidth(1);
                gr.setLineStyle(SWT.LINE_DOT);
                gr.setForegroundColor(ColorConstants.lightGray);

                gr.drawRectangle(b.x + selectionBorderSize, b.y
                        + selectionBorderSize, b.width
                        - (selectionBorderSize * 2) - 1, b.height
                        - (selectionBorderSize * 2) - 1);
            }

        } finally {
            gr.popState();
        }
    }

    /**
     * @param paintMouseRollover
     *            set to paint figure as mouse rolled over state
     */
    public void setPaintMouseRollover(boolean paintMouseRollover) {
        if (paintMouseRollover != this.paintMouseRollover) {
            this.paintMouseRollover = paintMouseRollover;
            repaint();
        }
    }

    /**
     * @param isSelected
     *            the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        if (isSelected != this.isSelected) {
            this.isSelected = isSelected;
            repaint();
        }
    }

    /**
     * Handle a drag notification.
     * 
     * @param me
     */
    public void handleDrag(MouseEvent me, Dimension mouseOffsetInPopup) {
        if (popupShell != null && !popupShell.isDisposed()
                && popupShell.getDisplay() != null) {

            Point mouseLocation = popupShell.getDisplay().getCursorLocation();

            Point popupLocation =
                    new Point(mouseLocation.x - mouseOffsetInPopup.width,
                            mouseLocation.y - mouseOffsetInPopup.height);

            popupShell.setLocation(popupLocation);
        }
    }

    /**
     * @return the popupShell that contains this tooltip (only set between
     *         {@link #aboutToShow(Shell)} {@link #hidden()})
     */
    public Shell getPopupShell() {
        return popupShell;
    }

    /**
     * Listen to mouse-rollover
     * 
     * 
     * @author aallway
     * @since 13 Jul 2012
     */
    private class SelectionStatusMouseListener implements MouseMotionListener,
            FocusListener, MouseListener {

        private Dimension mousePressOffsetInPopup = new Dimension(0, 0);

        /**
         * @see org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mousePressed(MouseEvent me) {
            StickyTooltipFigure.this.requestFocus();

            if (popupShell != null && !popupShell.isDisposed()
                    && popupShell.getDisplay() != null) {

                Point popupLocation = popupShell.getLocation();
                Point mouseLoc = popupShell.toDisplay(me.x, me.y);

                mousePressOffsetInPopup =
                        new Dimension(mouseLoc.x - popupLocation.x, mouseLoc.y
                                - popupLocation.y);
            }
        }

        /**
         * @see org.eclipse.draw2d.FocusListener#focusGained(org.eclipse.draw2d.FocusEvent)
         * 
         * @param fe
         */
        @Override
        public void focusGained(FocusEvent fe) {
            setSelected(true);
        }

        /**
         * @see org.eclipse.draw2d.FocusListener#focusLost(org.eclipse.draw2d.FocusEvent)
         * 
         * @param fe
         */
        @Override
        public void focusLost(FocusEvent fe) {
            /*
             * If losing focus to a child then don't unset selection state.
             */
            if (fe.gainer != null) {
                IFigure parent = fe.gainer.getParent();

                while (parent != null) {
                    if (parent == StickyTooltipFigure.this) {
                        return;
                    }
                    parent = parent.getParent();
                }
            }

            /* Focus lost to something else, unset selection */
            setSelected(false);
        }

        /**
         * @see org.eclipse.draw2d.MouseMotionListener#mouseEntered(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseEntered(MouseEvent me) {
            setPaintMouseRollover(true);
        }

        /**
         * @see org.eclipse.draw2d.MouseMotionListener#mouseExited(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseExited(MouseEvent me) {
            setPaintMouseRollover(false);
        }

        /**
         * @see org.eclipse.draw2d.MouseMotionListener#mouseDragged(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseDragged(MouseEvent me) {
            handleDrag(me, mousePressOffsetInPopup);
        }

        /**
         * @see org.eclipse.draw2d.MouseMotionListener#mouseHover(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseHover(MouseEvent me) {
        }

        /**
         * @see org.eclipse.draw2d.MouseMotionListener#mouseMoved(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseMoved(MouseEvent me) {
        }

        /**
         * @see org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseReleased(MouseEvent me) {
        }

        /**
         * @see org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d.MouseEvent)
         * 
         * @param me
         */
        @Override
        public void mouseDoubleClicked(MouseEvent me) {

        }

    }

}
