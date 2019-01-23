/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.preferences;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;

import com.tibco.xpd.deploy.ui.components.ViewerAction;
import com.tibco.xpd.deploy.ui.wizards.addruntime.AddRuntimeWizard;

/**
 * Creates new server runtime.
 * <p>
 * <i>Created: 4 Dec 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
final class NewViewerElementAction extends ViewerAction {

    public NewViewerElementAction(StructuredViewer viewer, String text) {
        super(viewer, text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        AddRuntimeWizard runtimeWizard = new AddRuntimeWizard();
        WizardDialog dialog = new WizardDialog(getShell(), runtimeWizard);
        dialog.open();
        if (dialog.getReturnCode() == Window.OK) {
            com.tibco.xpd.deploy.Runtime createdRuntime = runtimeWizard
                    .getCreatedRuntime();
            getViewer().refresh();
            if (createdRuntime != null) {
                getViewer().setSelection(
                        new StructuredSelection(createdRuntime), true);
            }
        }
    }

}