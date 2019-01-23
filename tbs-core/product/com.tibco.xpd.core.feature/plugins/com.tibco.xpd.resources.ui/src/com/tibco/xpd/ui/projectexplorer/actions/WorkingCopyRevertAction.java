/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Action handler for the <b>Revert</b> global action for <code>IFile</code>
 * that has a <code>WorkingCopy</code>.
 * 
 * @author njpatel
 * 
 */
public class WorkingCopyRevertAction extends BaseSelectionListenerAction {

    private WorkingCopy selectedWc = null;

    /**
     * Action handler for revert of working copy.
     */
    public WorkingCopyRevertAction() {
        super(Messages.WorkingCopyRevertAction_action);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean ret = super.updateSelection(selection);

        if (ret) {
            ret = false;
            selectedWc = null;
            if (selection.size() == 1) {
                if (selection.getFirstElement() instanceof IFile) {
                    IFile file = (IFile) selection.getFirstElement();
                    WorkingCopy wc = XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(file);

                    if (wc != null && wc.isWorkingCopyDirty()) {
                        selectedWc = wc;
                        ret = true;
                    }
                }
            }
        }

        return ret;
    }

    @Override
    public void run() {
        if (selectedWc != null && selectedWc.isWorkingCopyDirty()) {
            // If the working copy is dirty then reload it
            selectedWc.reLoad();
        }
    }

}
