/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Project Explorer's Undo action
 * 
 * @author njpatel
 * 
 */
public class UndoAction extends BaseSelectionListenerAction implements
        CommandStackListener {

    /**
     * The id of this action.
     */
    public static final String ID = Xpdl2ResourcesPlugin.PLUGIN_ID
            + ".UndoAction";//$NON-NLS-1$

    private EObject currentSelection = null;

    private EditingDomain editingDomain = null;

    /**
     * Undo action
     */
    public UndoAction() {
        super(Messages.UndoAction_1);
        setToolTipText(Messages.UndoAction_1);
        setId(ID);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     */
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean bRet = super.updateSelection(selection);

        if (bRet) {
            bRet = false;
            // Only deal with one selection
            if (selection != null && selection.size() == 1) {
                Object firstSelection = selection.getFirstElement();
                EObject eObj = null;

                if (firstSelection instanceof EObject) {
                    eObj = (EObject) firstSelection;
                } else if (firstSelection instanceof AbstractAssetGroup) {
                    eObj = (EObject) ((AbstractAssetGroup) firstSelection)
                            .getParent();
                } else if (firstSelection instanceof IFile) {
                    // Get the working copy of the file
                    IFile file = (IFile) firstSelection;
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(file);

                    if (wc != null && wc.isLoaded()) {
                        eObj = wc.getRootElement();
                    }
                }

                if (eObj != null) {

                    if (currentSelection != eObj) {
                        // If there is already an editing domain then dispose it
                        disposeEditingDomain();

                        if ((editingDomain = WorkingCopyUtil
                                .getEditingDomain(eObj)) != null) {
                            editingDomain.getCommandStack()
                                    .addCommandStackListener(this);
                            currentSelection = eObj;
                        }
                    }
                } else {
                    currentSelection = null;
                    disposeEditingDomain();
                }

                if (editingDomain != null) {
                    bRet = editingDomain.getCommandStack().canUndo();
                }
            }
        }

        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    public void run() {
        if (editingDomain != null && editingDomain.getCommandStack().canUndo()) {
            editingDomain.getCommandStack().undo();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CommandStackListener#commandStackChanged(java.util.EventObject)
     */
    public void commandStackChanged(EventObject event) {
        if (editingDomain != null && editingDomain.getCommandStack().canUndo()) {
            setEnabled(true);
            setText(String.format(Messages.UndoAction_2, editingDomain.getCommandStack()
                    .getUndoCommand().getLabel()));
        } else {
            setEnabled(false);
            setText(Messages.UndoAction_3);
        }
    }

    /**
     * Dispose
     */
    public void dispose() {
        disposeEditingDomain();
    }

    /**
     * Dispose the editing domain
     */
    private void disposeEditingDomain() {
        if (editingDomain != null) {
            editingDomain.getCommandStack().removeCommandStackListener(this);
            editingDomain = null;
        }
    }
}
