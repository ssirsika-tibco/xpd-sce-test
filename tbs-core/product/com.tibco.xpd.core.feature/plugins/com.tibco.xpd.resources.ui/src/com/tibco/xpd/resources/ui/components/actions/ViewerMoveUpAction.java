/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.ViewerAction;

/**
 * Abstract action representing moving element up in the viewer.
 * <p>
 * <i>Created: 1 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public abstract class ViewerMoveUpAction extends ViewerAction {

    protected IStructuredSelection selection;

    /**
     * Action to move element up the table.
     * 
     * @param viewer
     *            table viewer
     */
    public ViewerMoveUpAction(StructuredViewer viewer) {
        this(
                viewer,
                com.tibco.xpd.resources.ui.internal.Messages.TableMoveUpAction_upAction_label,
                com.tibco.xpd.resources.ui.internal.Messages.TableMoveUpAction_upAction_tooltip);
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
    public ViewerMoveUpAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, XpdResourcesUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        XpdResourcesUIConstants.IMAGE_UP));

        if (tooltip != null) {
            setToolTipText(String
                    .format(
                            com.tibco.xpd.resources.ui.internal.Messages.TableMoveUpAction_upAction_withAccelerator_tooltip,
                            tooltip));
        }
        setAccelerator(SWT.CTRL | SWT.ARROW_UP);
        selectionChanged((IStructuredSelection) viewer.getSelection());
    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        this.selection = selection;
        boolean enable = canMoveUp(selection, getViewer());
        if (enable != isEnabled()) {
            setEnabled(enable);
        }
    }

    /**
     * Checks if the selection can be moved up.
     * 
     * @param selection
     *            the selection in the viewer.
     * @param viewer
     *            the context viewer
     * @return <code>true</code> if selection can be moved up.
     */
    protected boolean canMoveUp(IStructuredSelection selection,
            StructuredViewer viewer) {
        return true;
    }

}
