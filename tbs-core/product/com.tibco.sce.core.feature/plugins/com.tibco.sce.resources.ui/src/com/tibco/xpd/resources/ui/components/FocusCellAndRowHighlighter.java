/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.FocusCellHighlighter;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerRow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Focus cell highlighter for a table viewer to highlight the whole row when
 * selected.
 * 
 * @author njpatel
 * 
 */
public class FocusCellAndRowHighlighter extends FocusCellHighlighter {

    /**
     * @param viewer
     *            the viewer
     */
    public FocusCellAndRowHighlighter(ColumnViewer viewer) {
        super(viewer);
        /*
         * XPD-8378 - Adding SWT.EraseItem listener cause problems with checkbox
         * columns display in the tables on non-windows platforms.
         * 
         */
        if ("win32".equals(Platform.getOS())) {//$NON-NLS-1$
            hookListener(viewer);
        }
    }

    /**
     * Mark the cell that has focus.
     * 
     * @param event
     * @param cell
     */
    private void markFocusedCell(Event event, ViewerCell cell) {
        GC gc = event.gc;

        Rectangle rect = event.getBounds();
        gc.drawFocus(rect.x, rect.y, rect.width, rect.height);

        event.detail &= ~SWT.SELECTED;
    }

    /**
     * Hook an event listener to the viewer to mark the cell and row that has
     * focus.
     * 
     * @param viewer
     */
    private void hookListener(final ColumnViewer viewer) {

        Listener listener = new Listener() {

            @Override
            public void handleEvent(Event event) {
                if ((event.detail & SWT.SELECTED) > 0) {
                    ViewerCell focusCell = getFocusCell();
                    if (focusCell != null) {
                        ViewerRow row = focusCell.getViewerRow();

                        Assert.isNotNull(row,
                                "Internal structure invalid. Item without associated row is not possible."); //$NON-NLS-1$

                        ViewerCell cell = row.getCell(event.index);
                        if (cell.equals(focusCell)) {
                            markFocusedCell(event, cell);
                        }
                    }
                }
            }

        };
        viewer.getControl().addListener(SWT.EraseItem, listener);
    }

    @Override
    protected void focusCellChanged(ViewerCell newCell, ViewerCell oldCell) {
        super.focusCellChanged(newCell, oldCell);
        // Redraw new area
        if (newCell != null) {
            Rectangle rect = newCell.getBounds();
            int x = newCell.getColumnIndex() == 0 ? 0 : rect.x;
            int width = newCell.getColumnIndex() == 0 ? rect.x + rect.width
                    : rect.width;
            newCell.getControl().redraw(x, rect.y, width, rect.height, true);
        }

        if (oldCell != null) {
            Rectangle rect = oldCell.getBounds();
            int x = oldCell.getColumnIndex() == 0 ? 0 : rect.x;
            int width = oldCell.getColumnIndex() == 0 ? rect.x + rect.width
                    : rect.width;
            oldCell.getControl().redraw(x, rect.y, width, rect.height, true);
        }
    }
}