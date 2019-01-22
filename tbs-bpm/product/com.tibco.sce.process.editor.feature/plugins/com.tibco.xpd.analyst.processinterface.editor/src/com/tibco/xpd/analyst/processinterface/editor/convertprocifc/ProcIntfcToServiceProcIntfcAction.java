/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.analyst.processinterface.editor.convertprocifc;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.processinterface.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action to convert process interface to service process interface.
 * 
 * @author bharge
 * @since 18 Feb 2015
 */
public class ProcIntfcToServiceProcIntfcAction extends
        BaseSelectionListenerAction {

    private ProcessInterface selectedProcessInterface = null;

    private EditingDomain editingDomain = null;

    /**
     * @param text
     */
    public ProcIntfcToServiceProcIntfcAction() {

        super(
                Messages.ProcIntfcToServiceProcIntfcAction_ConvertToServiceProcessInterface_menu);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.ICON_PROC_INTFC_TO_SERVICE_PROC_INTFC));
    }

    /**
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param selection
     * @return
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {

        selectedProcessInterface = null;
        editingDomain = null;

        if (selection.size() == 1
                && selection.getFirstElement() instanceof ProcessInterface) {

            if (Xpdl2ModelUtil.isProcessInterface((ProcessInterface) selection
                    .getFirstElement())) {

                selectedProcessInterface =
                        (ProcessInterface) selection.getFirstElement();
                editingDomain =
                        WorkingCopyUtil
                                .getEditingDomain(selectedProcessInterface);
            }
        }

        return selectedProcessInterface != null && editingDomain != null;
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        if (null != selectedProcessInterface
                && Xpdl2ModelUtil.isProcessInterface(selectedProcessInterface)) {
            /*
             * Create command to convert process interface to service process
             * interface.
             */
            /*
             * XPD-7285: Saket: Need to make sure that we close and re-open the
             * process interface editor in case it was already open. We need to
             * do this so that the content on the editor is refreshed
             * appropriately for the end user.
             */
            CompoundCommand cmd =
                    new CloseOpenProcessInterfaceEditorCommand(
                            selectedProcessInterface);
            cmd.setLabel(this.getText());

            /*
             * Set service process interface flag on selected process interface.
             */
            cmd.append(SetCommand.create(editingDomain,
                    selectedProcessInterface,
                    XpdExtensionPackage.eINSTANCE
                            .getProcessInterface_XpdInterfaceType(),
                    XpdInterfaceType.SERVICE_PROCESS_INTERFACE));

            /*
             * Add Service Process Configuration with deployment target as
             * process run-time
             */
            ServiceProcessConfiguration serviceProcessConfiguration =
                    XpdExtensionFactory.eINSTANCE
                            .createServiceProcessConfiguration();
            serviceProcessConfiguration.setDeployToPageflowRuntime(false);
            serviceProcessConfiguration.setDeployToProcessRuntime(true);

            cmd.append(SetCommand.create(editingDomain,
                    selectedProcessInterface,
                    XpdExtensionPackage.eINSTANCE
                            .getProcessInterface_ServiceProcessConfiguration(),
                    serviceProcessConfiguration));

            if (cmd.canExecute()) {

                editingDomain.getCommandStack().execute(cmd);
                this.selectionChanged(getStructuredSelection());
            }
        }
    }
}
