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
 * Abstract action representing moving element down in the viewer.
 * <p>
 * <i>Created: 1 Apr 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerMoveDownAction extends ViewerAction {

    protected IStructuredSelection selection;

    public ViewerMoveDownAction(StructuredViewer viewer) {
        this(viewer, Messages.TableMoveDownAction_downAction_label,
                Messages.TableMoveDownAction_downAction_tooltip);
    }

    public ViewerMoveDownAction(StructuredViewer viewer, String label,
            String tooltip) {
        super(viewer, label, XpdResourcesUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        XpdResourcesUIConstants.IMAGE_DOWN));
        if (tooltip != null) {
            setToolTipText(String
                    .format(
                            Messages.TableMoveDownAction_downAction_withAccelerator_tooltip,
                            tooltip));
        }
        setAccelerator(SWT.CTRL | SWT.ARROW_DOWN);
        selectionChanged((IStructuredSelection) viewer.getSelection());
    }

    @Override
    public void selectionChanged(IStructuredSelection selection) {
        this.selection = selection;
        boolean enable = canMoveDown(selection, getViewer());
        if (enable != isEnabled()) {
            setEnabled(enable);
        }
    }

    /**
     * Checks if the selection can be moved down.
     * 
     * @param selection
     *            the selection in the viewer.
     * @param viewer
     *            the context viewer
     * @return <code>true</code> if selection can be moved down.
     */
    protected boolean canMoveDown(IStructuredSelection selection,
            StructuredViewer viewer) {
        return true;
    }

}
