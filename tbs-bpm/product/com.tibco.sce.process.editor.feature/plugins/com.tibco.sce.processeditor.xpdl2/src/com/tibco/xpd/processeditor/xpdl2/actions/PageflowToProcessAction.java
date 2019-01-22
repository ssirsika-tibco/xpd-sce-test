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
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Action for convert pageflow process to business process.
 * 
 * @author aallway
 * @since 3.4.2 (20 Sep 2010)
 */
public class PageflowToProcessAction extends BaseSelectionListenerAction {

    private Process selectedProcess = null;

    private EditingDomain editingDomain = null;

    /**
     * @param text
     */
    public PageflowToProcessAction() {
        super(Messages.PageflowToProcessAction_ConvertToBusinessProcess_menu);
        setImageDescriptor(Xpdl2ProcessEditorPlugin
                .getImageDescriptor(ProcessEditorConstants.ICON_PAGEFLOW_TO_PROCESS));

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
            if (Xpdl2ModelUtil
                    .isPageflow((Process) selection.getFirstElement())) {
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
                && Xpdl2ModelUtil.isPageflow(selectedProcess)) {

            /*
             * Create command to convert pageflow process to business process.
             */
            CompoundCommand cmd =
                    new CloseOpenProcessEditorCommand(selectedProcess);
            cmd.setLabel(this.getText());

            /*
             * Unset pageflow flag on process.
             */
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            selectedProcess,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType(),
                            null));

            /*
             * Convert any activity that has default colour for business process
             * to use default colour for business process.
             */
            ProcessWidgetColors bpmnColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BPMN_PROCESS);
            ProcessWidgetColors sourceColours;                    
            if (Xpdl2ModelUtil.isPageflowBusinessService(selectedProcess)){
            	cmd.append(Xpdl2ModelUtil
                         .getSetOtherAttributeCommand(editingDomain,
                                 selectedProcess,
                                 XpdExtensionPackage.eINSTANCE
                                         .getDocumentRoot_PublishAsBusinessService(),
                                 null));
            	cmd.append(Xpdl2ModelUtil
                         .getSetOtherAttributeCommand(editingDomain,
                                 selectedProcess,
                                 XpdExtensionPackage.eINSTANCE
                                         .getDocumentRoot_BusinessServiceCategory(),
                                 null));
            	sourceColours =
                    ProcessWidgetColors
                            .getInstance(ProcessWidgetType.BUSINESS_SERVICE);
            }else{
            	sourceColours = ProcessWidgetColors
                	.getInstance(ProcessWidgetType.PAGEFLOW_PROCESS);
            }

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(selectedProcess);

            for (Activity activity : allActivitiesInProc) {
                cmd.append(new ResetDefaultActivityColourCommand(editingDomain,
                        activity, sourceColours, bpmnColours));
            }

            if (cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
            }
        }
        return;
    }
}
