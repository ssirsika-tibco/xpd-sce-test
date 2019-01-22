/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;


/**
 * Action to add a new item to the table.
 * 
 * @author njpatel
 * 
 */
public abstract class TableAddAction extends ViewerAddAction {

    /**
     * Action to add a new item to the table.
     * 
     * @param viewer
     *            table viewer
     */
    public TableAddAction(StructuredViewer viewer) {
        super(viewer);
    }

    /**
     * Action to add a new item to the table.
     * 
     * @param viewer
     *            table viewer
     * @param label
     *            action label
     * @param tooltip
     *            action tooltip
     */
    public TableAddAction(StructuredViewer viewer, String label, String tooltip) {
        super(viewer, label, tooltip);
    }

    @Override
    public void run() {
        ColumnViewer viewer = (ColumnViewer) (getViewer() instanceof ColumnViewer ? getViewer()
                : null);

        if (viewer != null) {
            viewer.cancelEditing();
        }

        Object obj = addRow(getViewer());

        if (obj != null) {
            if (viewer != null) {
                // Edit the first column of the new element
                viewer.editElement(obj, 0);
            } else {
                getViewer().setSelection(new StructuredSelection(obj), true);
            }
        }
    }

    /**
     * Add a row to the table
     * 
     * @param viewer
     *            table viewer
     * @return Object added to the table, <code>null</code> if none added.
     */
    protected abstract Object addRow(StructuredViewer viewer);

}
