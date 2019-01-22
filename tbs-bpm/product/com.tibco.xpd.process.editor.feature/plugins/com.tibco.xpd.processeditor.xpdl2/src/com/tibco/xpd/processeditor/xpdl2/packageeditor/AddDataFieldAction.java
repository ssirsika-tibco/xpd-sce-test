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

import com.tibco.xpd.analyst.resources.xpdl2.wizards.newdatafield.NewDataFieldWizard;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.xpdl2.Package;

/**
 * Action on the Package editor to add a new XPDL DataField.
 * 
 * @author aallway
 * @since 3.5 (11 Apr 2010)
 */
public class AddDataFieldAction extends Action {
    private Object defaultSelection;

    /**
     * @param xpdlPackage
     */
    public AddDataFieldAction(Package xpdlPackage) {
        super(
                Messages.PackageEditorFormPage_CreateNewField_button,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor(ProcessEditorConstants.IMG_DATAFIELD_NEW));
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
        NewDataFieldWizard newWizard = new NewDataFieldWizard();
        newWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                defaultSelection));

        WizardDialog dialog =
                new WizardDialog(Display.getCurrent().getActiveShell(),
                        newWizard);
        dialog.open();
    }
}
