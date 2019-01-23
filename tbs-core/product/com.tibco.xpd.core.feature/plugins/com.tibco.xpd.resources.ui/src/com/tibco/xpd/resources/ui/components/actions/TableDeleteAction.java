/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;


/**
 * Action to remove the selected items from the table.
 * 
 * @author njpatel
 * 
 */
public abstract class TableDeleteAction extends ViewerDeleteAction {

    /**
     * Action to remove the selected items from the table.
     * 
     * @param viewer
     *            table viewer
     */
    public TableDeleteAction(StructuredViewer viewer) {
        super(viewer);
    }

    /**
     * Action to remove the selected items from the table.
     * 
     * @param viewer
     *            table viewer
     * @param label
     *            action tooltip label
     */
    public TableDeleteAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, tooltip);
    }

    @Override
    public void run() {
        if (selection != null && !selection.isEmpty()) {
            TableViewer viewer = (TableViewer) (getViewer() instanceof TableViewer ? getViewer()
                    : null);
            int itemIndex = 0;
            if (viewer != null) {
                viewer.cancelEditing();
                Object element = selection.getFirstElement();

                int count = viewer.getTable().getItemCount();
                for (int idx = 0; idx < count; idx++) {
                    Object obj = viewer.getElementAt(idx);

                    if (obj != null && obj == element) {
                        itemIndex = idx;
                        break;
                    }
                }

                if (itemIndex >= count - selection.size()) {
                    itemIndex = count - selection.size() - 1;
                }
            }

            deleteRows(selection);

            if (viewer != null) {
                Object element = viewer.getElementAt(itemIndex);
                if (element != null) {
                    viewer.setSelection(new StructuredSelection(element));
                }
            }
        }
    }

    /**
     * Delete the selected rows from the table.
     * 
     * @param selection
     *            selected rows
     */
    protected abstract void deleteRows(IStructuredSelection selection);

}
