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
 * Abstract add action used to remove element(s) from the viewer.
 * <p>
 * <i>Created: 30 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerDeleteAction extends ViewerAction {
    protected IStructuredSelection selection;

    /**
     * Action to remove the selected items from the viewer.
     * 
     * @param viewer
     *            table viewer.
     */
    public ViewerDeleteAction(StructuredViewer viewer) {
        this(viewer, Messages.ViewerDeleteAction_removeAction_label,
                Messages.ViewerDeleteAction_removeAction_tooltip);
    }

    /**
     * Action to remove the selected items from the viewer.
     * 
     * @param viewer
     *            table viewer.
     * @param label
     *            action tooltip label.
     */
    public ViewerDeleteAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, XpdResourcesUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        XpdResourcesUIConstants.IMAGE_DELETE));
        if (tooltip != null) {
            setToolTipText(String
                    .format(
                            Messages.ViewerDeleteAction_removeAction_withAccelerator_tooltip,
                            tooltip));
        }
        setAccelerator(SWT.DEL);
        selectionChanged((IStructuredSelection) viewer.getSelection());
    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        this.selection = selection;
        setEnabled(selection != null && !selection.isEmpty()
                && canDelete(selection));
    }

    /**
     * Check if the selection can be deleted from the viewer. Default
     * implementation returns <code>true</code>, subclasses should override if
     * it needs to check the selection.
     * 
     * @param selection
     *            current selection
     * @return <code>true</code> if delete can occur, <code>false</code>
     *         otherwise
     */
    protected boolean canDelete(IStructuredSelection selection) {
        return true;
    }

}
