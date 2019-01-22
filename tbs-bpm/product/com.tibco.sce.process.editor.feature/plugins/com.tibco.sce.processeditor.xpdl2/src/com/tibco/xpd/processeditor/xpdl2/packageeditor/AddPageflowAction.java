/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewPageflowProcessWizard;
import com.tibco.xpd.xpdl2.Package;

/**
 * Action on the Package editor to add a new XPDL Pageflow Process.
 * 
 * @author aallway
 * @since 3.5 (11 Apr 2010)
 */
public class AddPageflowAction extends Action {
    private Object defaultSelection;

    /**
     * @param xpdlPackage
     */
    public AddPageflowAction(Package xpdlPackage) {
        super(
                Messages.PackageEditorFormPage_CreatePageflow_label,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor(ProcessEditorConstants.IMG_PAGEFLOW_PROCESS_NEW));
        defaultSelection = xpdlPackage;
    }

    /**
     * @see org.eclipse.jface.action.Action#getImageDescriptor()
     * 
     * @return
     */
    @Override
    public ImageDescriptor getImageDescriptor() {
        return super.getImageDescriptor();
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {
        NewPageflowProcessWizard newProcessWizard =
                new NewPageflowProcessWizard();
        newProcessWizard.init(PlatformUI.getWorkbench(),
                new StructuredSelection(defaultSelection));

        WizardDialog dialog =
                new WizardDialog(Display.getCurrent().getActiveShell(),
                        newProcessWizard);
        dialog.open();
    }

    /**
     * @return the defaultSelection
     */
    public Object getDefaultSelection() {
        return defaultSelection;
    }

    /**
     * @param defaultSelection
     *            the defaultSelection to set
     */
    public void setDefaultSelection(Object defaultSelection) {
        this.defaultSelection = defaultSelection;
    }
}
