/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components.actions;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.ViewerAction;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Abstract add action used to add element to the viewer.
 * <p>
 * <i>Created: 30 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ViewerAddAction extends ViewerAction {

    /**
     * Action to add a new item to the table.
     * 
     * @param viewer
     *            table viewer.
     */
    public ViewerAddAction(StructuredViewer viewer) {
        this(viewer, Messages.ViewerAddAction_addAction_label,
                Messages.ViewerAddAction_addAction_tooltip);
    }

    /**
     * Action to add a new item to the table.
     * 
     * @param viewer
     *            table viewer.
     * @param label
     *            action label.
     * @param tooltip
     *            action tooltip.
     */
    public ViewerAddAction(StructuredViewer viewer, String label, String tooltip) {
        super(viewer, label, XpdResourcesUIActivator.getDefault()
                .getImageRegistry().getDescriptor(
                        XpdResourcesUIConstants.IMAGE_ADD));
        if (tooltip != null) {
            setToolTipText(String.format(
                    Messages.ViewerAddAction_addAction_withAccelerator_tooltip,
                    tooltip));
        }
        setAccelerator(SWT.INSERT);
    }
}
