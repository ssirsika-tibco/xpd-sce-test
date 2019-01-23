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

import com.tibco.xpd.analyst.resources.xpdl2.wizards.processinterfaces.NewServiceProcessInterfaceWizard;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * 
 * Action on the Package editor to add a new Service Process Interface.
 * 
 * @author bharge
 * @since 9 Feb 2015
 */
public class AddServiceProcessIfcAction extends Action {

    private Object defaultSelection;

    /**
     * @param defaultSel
     */
    public AddServiceProcessIfcAction(Object defaultSel) {

        super(
                Messages.PackageEditorFormPage_CreateServiceProcIfc_label,
                Xpdl2ProcessEditorPlugin
                        .getImageDescriptor(ProcessEditorConstants.IMG_SERVICE_PROCESS_IFC_NEW));
        this.defaultSelection = defaultSel;
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

        NewServiceProcessInterfaceWizard newServiceProcessInterfaceWizard =
                new NewServiceProcessInterfaceWizard();
        newServiceProcessInterfaceWizard.init(PlatformUI.getWorkbench(),
                new StructuredSelection(defaultSelection));

        WizardDialog dialog =
                new WizardDialog(Display.getCurrent().getActiveShell(),
                        newServiceProcessInterfaceWizard);
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
