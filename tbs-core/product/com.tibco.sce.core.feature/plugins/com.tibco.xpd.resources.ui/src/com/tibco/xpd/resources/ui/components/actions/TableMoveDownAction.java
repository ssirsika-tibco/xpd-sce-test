package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;

public abstract class TableMoveDownAction extends ViewerMoveDownAction {

    public TableMoveDownAction(StructuredViewer viewer) {
        super(viewer);
    }

    public TableMoveDownAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, tooltip);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean canMoveDown(IStructuredSelection selection,
            StructuredViewer viewer) {
        TableViewer tableViewer =
                (TableViewer) (getViewer() instanceof TableViewer ? getViewer()
                        : null);
        boolean canMoveDown = false;

        // Can not move up the first element in the table
        if (tableViewer != null && selection != null && selection.size() == 1) {
            Object element = selection.getFirstElement();
            int count = tableViewer.getTable().getItemCount();
            canMoveDown = tableViewer.getElementAt(count - 1) != element;
        }
        return canMoveDown;
    }

    @Override
    public void run() {
        if (selection != null && selection.size() == 1) {
            if (getViewer() instanceof ColumnViewer) {
                ((ColumnViewer) getViewer()).cancelEditing();
            }
            Object element = selection.getFirstElement();
            moveDown(element);
            getViewer().setSelection(new StructuredSelection(element), true);
        }
    }

    protected abstract void moveDown(Object element);

}
