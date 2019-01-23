/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import java.util.EventObject;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Project Explorer's Undo/Redo action
 * 
 * @author njpatel
 * 
 */
public class UndoRedoAction extends BaseSelectionListenerAction implements
        CommandStackListener, IOperationHistoryListener {

    public enum Type {
        UNDO(Messages.UndoRedoAction_undo_action), REDO(
                Messages.UndoRedoAction_redo_action);

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private EObject currentEObject = null;

    private EditingDomain editingDomain = null;

    private final Type type;

    /**
     * Redo action
     */
    public UndoRedoAction(Type type) {
        super(type.toString());
        this.type = type;
        setToolTipText(type.toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean result = super.updateSelection(selection);

        if (result) {
            result = false;
            // Only dealing with a single selection
            if (selection != null && selection.size() == 1) {
                EObject eObj = getEObjectFromSelection(selection);

                if (eObj != null) {
                    // If this is a new selection then update editing domain
                    if (currentEObject != eObj) {
                        // If there is an editing domain then dispose it
                        unregisterFromCommandStack();
                        editingDomain = WorkingCopyUtil.getEditingDomain(eObj);
                        if ((editingDomain) != null) {
                            registerForCommandStack();
                            currentEObject = eObj;
                        }
                    }
                } else {
                    currentEObject = null;
                    unregisterFromCommandStack();
                }

                if (editingDomain != null) {
                    if (type.equals(Type.UNDO)) {
                        result = editingDomain.getCommandStack().canUndo();
                    } else {
                        result = editingDomain.getCommandStack().canRedo();
                    }
                }
            }
        }
        if (editingDomain != null) {
            refresh(editingDomain.getCommandStack());
        } else {
            refresh(null);
        }

        return result;
    }

    /**
     * @param selection
     * @return
     */
    protected EObject getEObjectFromSelection(IStructuredSelection selection) {
        EObject eObj = null;

        if (selection.size() == 1) {
            Object selectedObject = selection.getFirstElement();
            if (selectedObject instanceof EObject) {
                eObj = (EObject) selectedObject;
            } else if (selectedObject instanceof IFile) {
                // Get the working copy of the file
                IFile file = (IFile) selectedObject;

                if (file.getProject() != null) {
                    XpdProjectResourceFactory fact =
                            XpdResourcesPlugin.getDefault()
                                    .getXpdProjectResourceFactory(file
                                            .getProject());
                    // Get the Package model of the working copy
                    if (fact != null) {
                        WorkingCopy wc = fact.getWorkingCopy(file);

                        if (wc != null) {
                            eObj = wc.getRootElement();
                        }
                    }
                }
            }
        }
        return eObj;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    public void run() {
        if (editingDomain != null) {
            if (type.equals(Type.UNDO)) {
                if (editingDomain.getCommandStack().canUndo()) {
                    editingDomain.getCommandStack().undo();
                }
            } else {
                if (editingDomain.getCommandStack().canRedo()) {
                    editingDomain.getCommandStack().redo();
                }
            }
            // refresh(editingDomain.getCommandStack());
        }
    }

    /**
     * 
     */
    public void dispose() {
        unregisterFromCommandStack();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.common.command.CommandStackListener#commandStackChanged
     * (java.util.EventObject)
     */
    public void commandStackChanged(EventObject event) {
        CommandStack cs = editingDomain.getCommandStack();
        refresh(cs);
    }

    public void historyNotification(OperationHistoryEvent event) {
        CommandStack cs = editingDomain.getCommandStack();
        refresh(cs);
    }

    /**
     * @param cs
     */
    private void refresh(CommandStack cs) {
        boolean enabled;
        enabled =
                cs != null
                        && (type.equals(Type.UNDO) ? enabled = cs.canUndo()
                                : cs.canRedo());
        if (enabled) {
            setEnabled(true);
            Command cmd =
                    (type.equals(Type.UNDO) ? cs.getUndoCommand() : cs
                            .getRedoCommand());
            setText(type.toString() + " " + (cmd == null ? "" : cmd.getLabel())); //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            setEnabled(false);
            setText(type.toString());
        }
    }

    /**
     * Dispose the editing domain
     */
    private void unregisterFromCommandStack() {
        if (editingDomain != null) {
            CommandStack cs = editingDomain.getCommandStack();
            if (cs instanceof IWorkspaceCommandStack) {
                IOperationHistory oh =
                        ((IWorkspaceCommandStack) cs).getOperationHistory();
                oh.removeOperationHistoryListener(this);
            } else {
                cs.removeCommandStackListener(this);
            }
        }
    }

    /**
     * Dispose the editing domain
     */
    private void registerForCommandStack() {
        if (editingDomain != null) {
            CommandStack cs = editingDomain.getCommandStack();
            if (cs instanceof IWorkspaceCommandStack) {
                IOperationHistory oh =
                        ((IWorkspaceCommandStack) cs).getOperationHistory();
                oh.addOperationHistoryListener(this);
            } else {
                cs.addCommandStackListener(this);
            }
        }
    }

}
