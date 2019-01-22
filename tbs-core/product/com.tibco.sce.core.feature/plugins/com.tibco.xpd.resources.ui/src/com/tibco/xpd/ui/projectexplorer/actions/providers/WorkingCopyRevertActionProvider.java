/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.actions.providers;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;

import com.tibco.xpd.ui.projectexplorer.actions.WorkingCopyRevertAction;

/**
 * Action provider for the <b>Revert</b> global action for <code>IFile</code>
 * selections that have a <code>WorkingCopy</code>.
 * 
 * @author njpatel
 * 
 */
public class WorkingCopyRevertActionProvider extends CommonActionProvider {

    private WorkingCopyRevertAction revert;

    /**
     * Revert action provider for a working copy.
     */
    public WorkingCopyRevertActionProvider() {
        revert = new WorkingCopyRevertAction();
    }

    @Override
    public void fillActionBars(IActionBars actionBars) {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();

        revert.selectionChanged(selection);

        actionBars.setGlobalActionHandler(ActionFactory.REVERT.getId(), revert);
    }

}
