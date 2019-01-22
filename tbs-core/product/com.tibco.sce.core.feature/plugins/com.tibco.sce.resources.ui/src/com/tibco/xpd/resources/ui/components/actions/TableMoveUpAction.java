/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;


/**
 * Action to move the selected element up the table.
 * 
 * @author njpatel
 * 
 */
public abstract class TableMoveUpAction extends ViewerMoveUpAction {

    /**
     * Action to move element up the table.
     * 
     * @param viewer
     *            table viewer
     */
    public TableMoveUpAction(StructuredViewer viewer) {
        super(viewer);
    }

    /**
     * Action to move element up the table.
     * 
     * @param viewer
     *            table viewer
     * @param label
     *            action label
     * @param tooltip
     *            action tooltip
     */
    public TableMoveUpAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, tooltip);
    }

    @Override
    public void run() {
        if (selection != null && selection.size() == 1) {
            if (getViewer() instanceof ColumnViewer) {
                ((ColumnViewer) getViewer()).cancelEditing();
            }
            Object element = selection.getFirstElement();
            moveUp(element);
            getViewer().setSelection(new StructuredSelection(element), true);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canMoveUp(IStructuredSelection selection,
            StructuredViewer viewer) {
        TableViewer tableViewer = (TableViewer) (getViewer() instanceof TableViewer ? getViewer()
                : null);
        boolean canMoveUp = false;

        // Can not move up the first element in the table
        if (tableViewer != null && selection != null && selection.size() == 1) {
            Object element = selection.getFirstElement();
            canMoveUp = tableViewer.getElementAt(0) != element;
        }
        return canMoveUp;
    }

    protected abstract void moveUp(Object element);

}
