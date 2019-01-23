/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.dnd;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

import com.tibco.xpd.fragments.IFragment;

/**
 * Abstract class to be implemented by any <code>EditPartViewer</code> that
 * wishes to support dropping of fragments.
 * 
 * @author njpatel
 * 
 */
public abstract class FragmentTransferDropTargetListener implements
        TransferDropTargetListener {

    private final EditPartViewer viewer;

    private String data;

    private EditPart targetEditPart;

    /**
     * <code>EditPartViewer</code> fragment drop target listener
     * 
     * @param viewer
     *            the viewer that will receive the fragment drop.
     */
    public FragmentTransferDropTargetListener(EditPartViewer viewer) {
        this.viewer = viewer;
    }

    /**
     * Get the edit part viewer.
     * 
     * @return <code>EditPartViewer</code>.
     */
    public EditPartViewer getViewer() {
        return viewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.util.TransferDropTargetListener#getTransfer()
     */
    public Transfer getTransfer() {
        return FragmentLocalSelectionTransfer.getTransfer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.util.TransferDropTargetListener#isEnabled(org.eclipse
     * .swt.dnd.DropTargetEvent)
     */
    public boolean isEnabled(DropTargetEvent event) {
        return FragmentLocalSelectionTransfer.getTransfer().isSupportedType(
                event.currentDataType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.
     * DropTargetEvent)
     */
    public void dragEnter(DropTargetEvent event) {
        data = getTransferData();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.
     * DropTargetEvent)
     */
    public void dragLeave(DropTargetEvent event) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse
     * .swt.dnd.DropTargetEvent)
     */
    public void dragOperationChanged(DropTargetEvent event) {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.
     * DropTargetEvent)
     */
    public void dragOver(DropTargetEvent event) {
        event.detail = DND.DROP_NONE;
        if (data != null) {
            targetEditPart = viewer.findObjectAt(getDropLocation(event));
            if (targetEditPart != null) {
                doDragOver(event, targetEditPart);
            }
        }
    }

    /**
     * Implement the drag over the given target <code>EditPart</code>.
     * 
     * @param event
     *            drop target event
     * @param target
     *            <code>EditPart</code> being dragged over
     */
    protected abstract void doDragOver(DropTargetEvent event,
            final EditPart target);

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.
     * DropTargetEvent)
     */
    public void drop(DropTargetEvent event) {
        if (data != null) {
            doDrop(event, data, targetEditPart);
        }
    }

    /**
     * Implement drop of the fragment on the given target <code>EditPart</code>.
     * 
     * @param event
     *            drop target event
     * @param fragmentData
     *            the serialized fragment model
     * @param targetEditPart
     *            target <code>EditPart</code> of the drop
     */
    protected abstract void doDrop(DropTargetEvent event,
            final String fragmentData, final EditPart targetEditPart);

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd
     * .DropTargetEvent)
     */
    public void dropAccept(DropTargetEvent event) {
        // Nothing to do
    }

    /**
     * Returns the current mouse location, as a {@link Point}. The location is
     * relative to the control's client area.
     * 
     * @return the drop location
     */
    protected Point getDropLocation(DropTargetEvent event) {
        org.eclipse.swt.graphics.Point swt;
        swt = new org.eclipse.swt.graphics.Point(event.x, event.y);
        DropTarget target = (DropTarget) event.widget;
        swt = target.getControl().toControl(swt);
        return new Point(swt.x, swt.y);
    }

    /**
     * Get the fragment serialized model from the fragment transfer.
     * 
     * @return fragment data
     */
    protected String getTransferData() {
        String data = null;

        ISelection selection = FragmentLocalSelectionTransfer.getTransfer()
                .getSelection();

        if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;

            if (sel.size() == 1 && sel.getFirstElement() instanceof IFragment) {
                data = ((IFragment) sel.getFirstElement()).getLocalizedData();
            }
        }

        return data;
    }

}
