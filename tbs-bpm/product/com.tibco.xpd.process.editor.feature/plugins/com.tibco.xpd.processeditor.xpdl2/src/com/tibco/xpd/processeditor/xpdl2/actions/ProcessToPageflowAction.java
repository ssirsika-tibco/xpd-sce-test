/**
 * 
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
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action for convert business process to pageflow process.
 * 
 * @author aallway
 * @since 3.4.2 (20 Sep 2010)
 */
public class ProcessToPageflowAction extends BaseSelectionListenerAction {

    private Process selectedProcess = null;

    private EditingDomain editingDomain = null;

    /**
     * @param text
     */
    public ProcessToPageflowAction() {

        super(Messages.ProcessToPageflowAction_ConvertToPageflow_menu);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.ICON_PROCESS_TO_PAGEFLOW));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {

        selectedProcess = null;
        editingDomain = null;

        if (selection.size() == 1
                && selection.getFirstElement() instanceof Process) {

            if (Xpdl2ModelUtil.isBusinessProcess((Process) selection
                    .getFirstElement())) {

                selectedProcess = (Process) selection.getFirstElement();
                editingDomain =
                        WorkingCopyUtil.getEditingDomain(selectedProcess);
            }
        }

        return selectedProcess != null && editingDomain != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (selectedProcess != null
                && Xpdl2ModelUtil.isBusinessProcess(selectedProcess)) {

            /*
             * Create command to convert business process to pageflow process.
             */
            CompoundCommand cmd =
                    new CloseOpenProcessEditorCommand(selectedProcess);
            cmd.setLabel(this.getText());

            /*
             * Set pageflow flag on process.
             */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            selectedProcess,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType(),
                            XpdModelType.PAGE_FLOW));

            /*
             * Convert any activity that has default colour for business process
             * to use default colour for pageflow.
             */
            ProcessWidgetColors bpmnColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BPMN_PROCESS);
            ProcessWidgetColors pageflowColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(selectedProcess);

            for (Activity activity : allActivitiesInProc) {
                cmd.append(new ResetDefaultActivityColourCommand(editingDomain,
                        activity, bpmnColours, pageflowColours));
            }

            if (cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
            }
        }
        return;
    }
}
