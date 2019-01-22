/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.ResetDefaultActivityColourCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action to convert service process to business process.
 * 
 * @author bharge
 * @since 16 Feb 2015
 */
public class ServiceProcessToBusinessProcessAction extends
        BaseSelectionListenerAction {

    private Process selectedProcess = null;

    private EditingDomain editingDomain = null;

    /**
     * @param text
     */
    public ServiceProcessToBusinessProcessAction() {

        super(
                Messages.ServiceProcessToBusinessProcessAction_ConvertToBusinessProcess_menu);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.ICON_PAGEFLOW_TO_PROCESS));
    }

    /**
     * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param selection
     * @return
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {

        selectedProcess = null;
        editingDomain = null;

        if (selection.size() == 1
                && selection.getFirstElement() instanceof Process) {

            if (Xpdl2ModelUtil.isServiceProcess((Process) selection
                    .getFirstElement())) {

                selectedProcess = (Process) selection.getFirstElement();
                editingDomain =
                        WorkingCopyUtil.getEditingDomain(selectedProcess);
            }
        }

        return selectedProcess != null && editingDomain != null;

    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     * 
     */
    @Override
    public void run() {

        if (selectedProcess != null
                && Xpdl2ModelUtil.isServiceProcess(selectedProcess)) {

            /*
             * Create command to convert service process to business process.
             */
            CompoundCommand cmd =
                    new CloseOpenProcessEditorCommand(selectedProcess);
            cmd.setLabel(this.getText());

            /*
             * Set business process flag on process (null makes it business
             * process).
             */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            selectedProcess,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType(),
                            null));
            /*
             * Remove Service Process Configuration with deployment target as
             * process run-time
             */
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(editingDomain,
                    selectedProcess,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ServiceProcessConfiguration(),
                    null));

            /*
             * Convert any activity that has default colour for service process
             * to use default colour for business process.
             */
            ProcessWidgetColors serviceProcessColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.SERVICE_PROCESS);
            ProcessWidgetColors businessProcessColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BPMN_PROCESS);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(selectedProcess);

            for (Activity activity : allActivitiesInProc) {

                cmd.append(new ResetDefaultActivityColourCommand(editingDomain,
                        activity, serviceProcessColours, businessProcessColours));
            }

            if (cmd.canExecute()) {

                editingDomain.getCommandStack().execute(cmd);
            }
        }
        return;

    }
}
