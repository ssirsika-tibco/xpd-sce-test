/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.internal.dnd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.TransferData;

/**
 * Drop target listener for <code>LocationSelectionTransfer</code>.
 * 
 * @author njpatel
 * 
 */
public class LocalSelectionDropTargetListener extends DiagramDropTargetListener {

    /**
     * Drop target listener for <code>LocationSelectionTransfer</code>.
     * 
     * @param viewer
     *            edit part viewer.
     */
    public LocalSelectionDropTargetListener(EditPartViewer viewer) {
        super(viewer, LocalSelectionTransfer.getTransfer());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List getObjectsBeingDropped() {
        List<EObject> objs = new ArrayList<EObject>();
        if (getCurrentEvent() != null) {
            TransferData[] transferData = getCurrentEvent().dataTypes;

            if (transferData != null) {
                for (TransferData data : transferData) {
                    if (LocalSelectionTransfer.getTransfer().isSupportedType(
                            data)) {
                        ISelection selection = LocalSelectionTransfer
                                .getTransfer().getSelection();

                        if (selection instanceof IStructuredSelection) {
                            for (Iterator<?> iter = ((IStructuredSelection) selection)
                                    .iterator(); iter.hasNext();) {
                                Object item = iter.next();

                                if (item instanceof EObject
                                        && !objs.contains(item)) {
                                    objs.add((EObject) item);
                                }
                            }
                        }
                    }
                }
            }
        }

        return !objs.isEmpty() ? objs : null;
    }
}
