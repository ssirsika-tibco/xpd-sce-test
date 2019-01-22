/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * Payload data drag source listener.
 * 
 * @author sajain
 * @since Feb 10, 2015
 */
public class PayloadDataDragSourceListener extends ViewerDragAdapter {

    public PayloadDataDragSourceListener(StructuredViewer viewer) {
        super(viewer);
    }

    @Override
    public void dragStart(DragSourceEvent event) {
        super.dragStart(event);

        LocalSelectionTransfer.getTransfer()
                .setSelection(viewer.getSelection());
    }

}
