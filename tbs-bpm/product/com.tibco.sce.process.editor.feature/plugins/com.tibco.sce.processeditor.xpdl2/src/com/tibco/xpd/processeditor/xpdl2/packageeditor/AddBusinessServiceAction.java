/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
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
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessServiceWizard;
import com.tibco.xpd.xpdl2.Package;

/**
 * Action on the Package editor to add a new Business Service.
 * 
 * @author bharge
 * @since 7 Aug 2014
 */
public class AddBusinessServiceAction extends Action {

    private Object defaultSelection;

    /**
     * 
     */
    public AddBusinessServiceAction(Package xpdlPackage) {

        super(
                Messages.PackageEditorFormPage_CreateBusinessService_label,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor(ProcessEditorConstants.IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS_NEW));
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

        NewBusinessServiceWizard bizServWizard = new NewBusinessServiceWizard();
        bizServWizard.init(PlatformUI.getWorkbench(), new StructuredSelection(
                defaultSelection));

        WizardDialog dialog =
                new WizardDialog(Display.getCurrent().getActiveShell(),
                        bizServWizard);
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
