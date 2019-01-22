/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Abstract edit action used to edit element (usually externally using wizard or
 * other dialog) form the viewer.
 * <p>
 * <i>Created: 30 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerEditAction extends ViewerAction {

    protected IStructuredSelection selection;

    /**
     * Action to add a new item to the table.
     * 
     * @param viewer
     *            table viewer
     */
    public ViewerEditAction(StructuredViewer viewer) {
        this(viewer, Messages.ViewerEditAction_editAction_label,
                Messages.ViewerEditAction_editAction_tooltip);
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
    public ViewerEditAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, XpdResourcesUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        XpdResourcesUIConstants.IMAGE_EDIT));
        if (tooltip != null) {
            setToolTipText(String
                    .format(
                            Messages.ViewerEditAction_editAction_withAccelerator_tooltip,
                            tooltip));
        }
        setAccelerator(SWT.F2);
        selectionChanged((IStructuredSelection) viewer.getSelection());
    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        this.selection = selection;
        setEnabled(selection != null && !selection.isEmpty()
                && canEdit(selection));
    }

    /**
     * Check if the selection can be edited. Default implementation returns
     * <code>true</code>, subclasses should override if it needs to check the
     * selection.
     * 
     * @param selection
     *            current selection
     * @return <code>true</code> if edit can occur, <code>false</code> otherwise
     */
    protected boolean canEdit(IStructuredSelection selection) {
        return true;
    }
}
