/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.packageeditor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewServiceProcessWizard;
import com.tibco.xpd.xpdl2.Package;

/**
 * Action on the Package editor to add a new Service Process.
 * 
 * @author bharge
 * @since 9 Feb 2015
 */
public class AddServiceProcessAction extends Action {

    private Object defaultSelection;

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

    public AddServiceProcessAction(Package xpdlPackage) {

        super(
                Messages.PackageEditorFormPage_CreateServiceProcess_label,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor(ProcessEditorConstants.IMG_SERVICE_PROCESS_NEW));
        defaultSelection = xpdlPackage;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        NewServiceProcessWizard serviceProcessWizard =
                new NewServiceProcessWizard();
        serviceProcessWizard.init(PlatformUI.getWorkbench(),
                new StructuredSelection(defaultSelection));

        WizardDialog dialog =
                new WizardDialog(Display.getCurrent().getActiveShell(),
                        serviceProcessWizard);
        dialog.open();
    }
}
