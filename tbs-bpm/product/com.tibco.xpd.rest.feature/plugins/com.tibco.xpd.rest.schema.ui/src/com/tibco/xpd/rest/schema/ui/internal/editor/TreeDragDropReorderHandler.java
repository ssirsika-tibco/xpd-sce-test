/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal.editor;

import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * Adds drag and drop support to a StructuredViewer (Tree/Table) to allow single
 * items to be moved within the same control. This is to support reordering of
 * items and reparenting of tree items.
 * 
 * @author nwilson
 * @since 29 Jan 2015
 */
public abstract class TreeDragDropReorderHandler extends ViewerDropAdapter {

    public TreeDragDropReorderHandler(StructuredViewer viewer) {
        super(viewer);
        Transfer[] transferTypes =
                new Transfer[] { LocalTransfer.getInstance() };
        viewer.addDragSupport(DND.DROP_MOVE,
                transferTypes,
                new ViewerDragAdapter(viewer));
        viewer.addDropSupport(DND.DROP_MOVE, transferTypes, this);
    }

    /**
     * @see org.eclipse.jface.viewers.ViewerDropAdapter
     *      #performDrop(java.lang.Object)
     * 
     * @param data
     *            The data to drop.
     * @return true if the drop completed successfully.
     */
    @Override
    public boolean performDrop(Object data) {
        int location = getCurrentLocation();
        Object target = getCurrentTarget();
        Object source = getSelectedItem(data);
        return move(source, target, location);
    }

    /**
     * Moves a source object to a position relative to the target object.
     * Possible locations are defined as constants in ViewerDropAdapter.
     * 
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_ON
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_AFTER
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_BEFORE
     * @param source
     *            The object to move.
     * @param target
     *            The target location.
     * @param location
     *            The position relative to the target.
     * @return true if the source was moved successfully.
     */
    public abstract boolean move(Object source, Object target, int location);

    /**
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#validateDrop(java.lang.Object,
     *      int, org.eclipse.swt.dnd.TransferData)
     * 
     * @param target
     *            The target location for the drop.
     * @param operation
     *            The operation type.
     * @param transferType
     *            The transfer data.
     * @return true if the drop is allowed.
     */
    @Override
    public boolean validateDrop(Object target, int operation,
            TransferData transferType) {
        boolean valid = false;
        Object source = LocalTransfer.getInstance().nativeToJava(transferType);
        Object selected = getSelectedItem(source);
        if (selected != null) {
            valid = isValidTarget(selected, target, getCurrentLocation());
        }
        return valid;
    }

    private Object getSelectedItem(Object selection) {
        Object selected = null;
        if (selection instanceof TreeSelection) {
            TreeSelection treeSelection = (TreeSelection) selection;
            if (treeSelection.size() == 1) {
                selected = treeSelection.getFirstElement();
            }
        }
        return selected;
    }

    /**
     * Validates that the source object can be dropped at the location relative
     * to the target object. Possible locations are defined as constants in
     * ViewerDropAdapter.
     * 
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_ON
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_AFTER
     * @see org.eclipse.jface.viewers.ViewerDropAdapter#LOCATION_BEFORE
     * 
     * @param source
     *            The object to move.
     * @param target
     *            The target location.
     * @param location
     *            The position relative to the target.
     * @return true if the drop is allowed.
     */
    public abstract boolean isValidTarget(Object source, Object target,
            int location);
}
