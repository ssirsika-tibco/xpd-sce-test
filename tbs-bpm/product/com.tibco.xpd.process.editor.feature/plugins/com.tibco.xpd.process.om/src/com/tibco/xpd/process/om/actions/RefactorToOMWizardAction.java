/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.process.om.Activator;
import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.wizards.RefactorXPDLToOMWizard;

/**
 * Refactors Process Models to Organisation Model.
 * <p>
 * <i>Created: 16 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class RefactorToOMWizardAction extends BaseSelectionListenerAction {
    /**
     * The constructor
     */
    public RefactorToOMWizardAction() {
        super(Messages.RefactorToOMWizardAction_refactorToOMWizard_menu);
        setImageDescriptor(Activator
                .getImageDescriptor(Activator.IMG_ORG_MODEL));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        final IStructuredSelection selection = getStructuredSelection();
        Display display = Display.getDefault();
        display.asyncExec(new Runnable() {
            public void run() {
                IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
                        .getWorkbenchWindows();
                Shell shell = windows[0].getShell();
                RefactorXPDLToOMWizard wizard = new RefactorXPDLToOMWizard();
                wizard.init(PlatformUI.getWorkbench(), selection);
                WizardDialog dialog = new WizardDialog(shell, wizard);
                dialog.open();
            }
        });
    }
}
