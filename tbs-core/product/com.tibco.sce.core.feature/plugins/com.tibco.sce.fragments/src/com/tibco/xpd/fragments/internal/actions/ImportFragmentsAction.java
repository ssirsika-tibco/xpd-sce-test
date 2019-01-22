/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IViewActionDelegate;

import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.projectImport.ImportFragmentsProjectWizard;

/**
 * Import fragments project into workspace action. This will call
 * {@link ImportFragmentsProjectWizard}.
 * 
 * @author njpatel
 * 
 */
public class ImportFragmentsAction extends FragmentActionDelegate implements
        IViewActionDelegate {

    /**
     * Imports fragments project wizard action.
     */
    public ImportFragmentsAction() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action
     * .IAction, org.eclipse.swt.widgets.Event)
     */
    public void runWithEvent(IAction action, Event event) {
        ImportFragmentsProjectWizard wizard = new ImportFragmentsProjectWizard();
        try {
            wizard.setWindowTitle(Messages.ImportFragmentsAction_importFragments_wizard_title);
            WizardDialog dlg = new WizardDialog(getViewPart().getSite()
                    .getShell(), wizard);
            dlg.open();
        } finally {
            wizard.dispose();
            wizard = null;
        }

    }

    @Override
    protected boolean isEnabled(Object sel) {
        // Always enabled in the fragments view if a contributor is active
        return true;
    }
}
