/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.preferences;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;

import com.tibco.xpd.deploy.Runtime;
import com.tibco.xpd.deploy.ui.components.ViewerAction;
import com.tibco.xpd.deploy.ui.wizards.addruntime.EditRuntimeWizard;

/**
 * Edits selected runtime.
 * <p>
 * <i>Created: 4 December 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
final class EditViewerElementAction extends ViewerAction {

    /**
     * Creates action.
     * 
     * @param viewer
     *            the associated viewer.
     * @param text
     *            the label of action.
     */
    public EditViewerElementAction(StructuredViewer viewer, String text) {
        super(viewer, text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        IStructuredSelection selection = (IStructuredSelection) getViewer()
                .getSelection();
        Runtime runtime = (Runtime) selection.getFirstElement();
        EditRuntimeWizard runtimeWizard = new EditRuntimeWizard(runtime);
        WizardDialog dialog = new WizardDialog(getShell(), runtimeWizard);
        dialog.open();
        if (dialog.getReturnCode() == Window.OK) {
            com.tibco.xpd.deploy.Runtime editedRuntime = runtimeWizard
                    .getEditedRuntime();
            getViewer().refresh();
            if (editedRuntime != null) {
                getViewer().setSelection(
                        new StructuredSelection(editedRuntime), true);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.deploy.ui.components.ViewerAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void selectionChanged(IStructuredSelection selection) {
        setEnabled(selection.size() == 1);
    }

}