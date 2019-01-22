/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.navigator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.dnd.DropCommandManager;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Drag and Drop adapter for the project explorer that will process dropping of
 * BOM elements.
 * 
 * @author njpatel
 * 
 */
public class BOMDropAdapterAssistant extends CommonDropAdapterAssistant {

    private IUndoableOperation cmd;
    private IOperationHistory opHistory;

    /**
     * BOM drop adapter for the project explorer.
     */
    public BOMDropAdapterAssistant() {
        opHistory = OperationHistoryFactory.getOperationHistory();
    }

    @Override
    public IStatus handleDrop(CommonDropAdapter dropAdapter,
            DropTargetEvent dropTargetEvent, Object target) {
        IStatus status = Status.OK_STATUS;

        if (cmd != null) {
            try {
                opHistory.execute(cmd, null, null);
            } catch (ExecutionException e) {
                status = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                        Messages.BOMDropAdapterAssistant_dropError_message, e);
            }
        }

        return status;
    }

    @Override
    public IStatus validateDrop(Object target, int operation,
            TransferData transferType) {
        IStatus status = Status.CANCEL_STATUS;
        cmd = null;

        // Only allow copy for now
        if (operation == DND.DROP_COPY) {
            if (LocalSelectionTransfer.getTransfer().isSupportedType(
                    transferType)) {
                final IStructuredSelection selection = (IStructuredSelection) LocalSelectionTransfer
                        .getTransfer().getSelection();

                if (!selection.isEmpty() && target instanceof EObject) {
                    WorkingCopy wc = WorkingCopyUtil
                            .getWorkingCopyFor((EObject) target);

                    if (wc != null
                            && wc.getEditingDomain() instanceof TransactionalEditingDomain) {
                        TransactionalEditingDomain ed = (TransactionalEditingDomain) wc
                                .getEditingDomain();

                        // Check if the selection can be dragged
                        if (canDrag((EObject) target, selection)) {
                            if (operation == DND.DROP_MOVE
                                    || operation == DND.DROP_NONE) {
                                DropObjectsRequest req = new DropObjectsRequest();
                                req.setObjects(selection.toList());
                                cmd = DropCommandManager
                                        .createMoveObjectsCommand(
                                                (EObject) target, ed, null, req);
                            } else if (operation == DND.DROP_COPY) {
                                DropObjectsRequest request = new DropObjectsRequest();
                                request.setObjects(selection.toList());
                                // Copy objects
                                cmd = DropCommandManager
                                        .createCopyObjectsCommand(
                                                (EObject) target, null, ed,
                                                request);
                            }

                            if (cmd != null && cmd.canExecute()) {
                                status = Status.OK_STATUS;
                            }
                        }
                    }
                }
            }
        }
        return status;
    }

    /**
     * Check if the selection can be dragged. It cannot be dragged if the
     * selection is empty, or the selection contains the target, or the
     * selection contains children of the target, or the selection contains a
     * <code>Model</code>. Also, for a <code>Package</code> target only
     * <code>PackageableElement</code>s can be included in the selection; for a
     * <code>Class</code> target only {@link Property properties} can be
     * included in the selection.
     * 
     * @param target
     *            drop target
     * @param selection
     *            objects being dragged
     * @return <code>true</code> if the drag is valid, <code>false</code>
     *         otherwise.
     */
    private boolean canDrag(EObject target, IStructuredSelection selection) {
        boolean canDrag = false;

        if (target != null && selection != null && !selection.isEmpty()) {
            // Selection cannot contain target
            canDrag = !selection.toList().contains(target);

            if (canDrag) {
                // Selection cannot contain children of target or cannot contain
                // model
                for (Object obj : selection.toArray()) {
                    canDrag = !(obj instanceof Model)
                            && (obj instanceof EObject && ((EObject) obj)
                                    .eContainer() != target);

                    if (!canDrag) {
                        break;
                    }
                }

                /*
                 * If target is Package then only PackageableElements can be
                 * included in selection, and if target is Class then only
                 * Properties can be included.
                 */
                if (canDrag) {
                    for (Object obj : selection.toArray()) {
                        canDrag = (target instanceof Package && obj instanceof PackageableElement)
                                || (target instanceof Class && obj instanceof Property);

                        if (!canDrag) {
                            break;
                        }
                    }
                }
            }
        }

        return canDrag;
    }
}
