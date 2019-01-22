/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.resources.ui.components;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

/**
 * Keeps track of mouse over cell's and updates the tool tip text ready for next
 * hover
 * <p>
 * If the tooltip is already displayed then its text is reset immediately. On
 * mouse scroll or key press, cell location is recalculated and the tooltip is
 * reset accordingly.
 * 
 * 
 * @author aallway
 * @since 3 Jul 2012
 */
public class ViewerCellSpecificTooltipHandler implements MouseMoveListener,
        MouseWheelListener, KeyListener, MouseTrackListener {

    private ColumnViewer columnViewer;

    private boolean isActive = false;

    public ViewerCellSpecificTooltipHandler(ColumnViewer columnViewer) {
        this.columnViewer = columnViewer;
    }

    /**
     * Get the tooltip for the cell.
     * 
     * @param cell
     * @return tooltip text
     */
    protected String getTooltipForCell(ViewerCell cell) {
        CellLabelProvider labelProvider =
                columnViewer.getLabelProvider(cell.getColumnIndex());
        if (labelProvider != null) {
            return labelProvider.getToolTipText(cell.getElement());
        }
        return null;
    }

    /**
     * Call to activate the tooltip provision (adds appropriate listeners to
     * table control)
     * 
     * @param control
     */
    public void activate() {
        Control control = columnViewer.getControl();
        if (control != null && !control.isDisposed()) {
            if (!isActive) {
                isActive = true;
                control.addMouseMoveListener(this);
                control.addMouseTrackListener(this);
                control.addMouseWheelListener(this);
                control.addKeyListener(this);
            }
        }

    }

    /**
     * Call to deactivate the tooltip provision (removes listeners from table
     * control)
     */
    public void deactivate() {
        Control control = columnViewer.getControl();
        if (control != null && !control.isDisposed() && isActive) {
            isActive = false;
            control.removeMouseMoveListener(this);
            control.removeMouseTrackListener(this);
            control.removeMouseWheelListener(this);
            control.removeKeyListener(this);
        }
    }

    /**
     * Sets the tooltip text from the given mouse location. If already hovering
     * then the tooltip text is set straight away (which brings up the tooltip
     * itself. If not already hoverring then the new text is saved for when the
     * hover event actually happens.
     * 
     * @param mouseLocation
     */
    private void setTooltipFromMouseLocation(Point mouseLocation) {
        String tooltip = null;
        ViewerCell cell = columnViewer.getCell(mouseLocation);

        if (cell != null) {
            tooltip = getTooltipForCell(cell);
        }

        if (tooltip != null) {
            columnViewer.getControl().setToolTipText(tooltip);
        } else {
            /*
             * Bring down the current tooltip. Using "" means that tooltip is
             * pre-loaded. When setting control.setTooltipText("") is called for
             * first time then the system has a built in delay. So when
             * responding to mouseHover events there has already been a delay
             * before that. So presetting to "" basically means that the tootip
             * will appear immediately.
             */
            columnViewer.getControl().setToolTipText(""); //$NON-NLS-1$
        }

    }

    /**
     * Tear-down the current tooltip and create an asynch job that then
     * re-instantiates tooltip after normal hover delay.
     */
    public void resetTooltipAfterScroll() {
        /*
         * Tear down the tooltip completely (set to null so that on reset
         * tooltip from current location after scroll, then there will be a
         * standard hover delay). This is because, nominally mouse-hover event
         * won't happen until after mouse has moved again.
         */
        columnViewer.getControl().setToolTipText(null);

        /*
         * Have to asynch exec this to ensure that the table has chance to
         * scroll if necessary.
         */
        columnViewer.getControl().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                if (columnViewer.getControl() != null
                        && !columnViewer.getControl().isDisposed()) {
                    Point mouseLocation =
                            columnViewer.getControl().toControl(columnViewer
                                    .getControl().getDisplay()
                                    .getCursorLocation());

                    setTooltipFromMouseLocation(mouseLocation);
                }

            }
        });
    }

    /**
     * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
     * 
     * @param e
     */
    @Override
    public void mouseHover(MouseEvent e) {
        setTooltipFromMouseLocation(new Point(e.x, e.y));
    }

    /**
     * @see org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse.swt.events.MouseEvent)
     * 
     * @param e
     */
    @Override
    public void mouseEnter(MouseEvent e) {
        /*
         * Using "" means that tooltip is pre-loaded. When setting
         * control.setTooltipText("") is called for first time then the system
         * has a built in delay. So when responding to mouseHover events there
         * has already been a delay before that. So presetting to "" basically
         * means that the tootip will appear immediately.
         */
        columnViewer.getControl().setToolTipText(""); //$NON-NLS-1$

    }

    /**
     * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
     * 
     * @param e
     */
    @Override
    public void mouseExit(MouseEvent e) {
        columnViewer.getControl().setToolTipText(null);
    }

    @Override
    public void mouseMove(MouseEvent e) {
        /*
         * Tear down the current tooltip (set to empty string to keep it
         * 'primed'). Will be re-shown on next mouse-hover.
         */
        columnViewer.getControl().setToolTipText(""); //$NON-NLS-1$
    }

    @Override
    public void keyPressed(KeyEvent e) {
        /* table could have potentially scrolled. */
        resetTooltipAfterScroll();
    }

    @Override
    public void mouseScrolled(MouseEvent e) {
        resetTooltipAfterScroll();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}