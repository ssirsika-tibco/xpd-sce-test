/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.viewer;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.ProcessDiagramAdapter;
import com.tibco.xpd.processwidget.internal.Messages;
import com.tibco.xpd.processwidget.parts.AssociationEditPart;
import com.tibco.xpd.processwidget.parts.ConnectionLabelEditPart;
import com.tibco.xpd.processwidget.parts.DataObjectEditPart;
import com.tibco.xpd.processwidget.parts.EventEditPart;
import com.tibco.xpd.processwidget.parts.FlowLabelModel;
import com.tibco.xpd.processwidget.parts.GatewayEditPart;
import com.tibco.xpd.processwidget.parts.GroupEditPart;
import com.tibco.xpd.processwidget.parts.LaneEditPart;
import com.tibco.xpd.processwidget.parts.MessageFlowEditPart;
import com.tibco.xpd.processwidget.parts.NoteEditPart;
import com.tibco.xpd.processwidget.parts.PoolEditPart;
import com.tibco.xpd.processwidget.parts.ProcessEditPart;
import com.tibco.xpd.processwidget.parts.SequenceFlowEditPart;
import com.tibco.xpd.processwidget.parts.TaskEditPart;

/**
 * @author wzurek
 */
public class WidgetPartsLabelProvider extends LabelProvider implements
        ILabelProvider {

    /**
     * The Constructor
     */
    public WidgetPartsLabelProvider() {
        super();
    }

    @Override
    public String getText(Object element) {
        if (element instanceof IStructuredSelection
                && ((IStructuredSelection) element).size() == 1) {
            element = ((IStructuredSelection) element).getFirstElement();
        } else if (element instanceof IStructuredSelection
                && ((IStructuredSelection) element).size() > 1) {
            return Messages.WidgetPartsLabelProvider_MultiSel_label;
        }

        if (element instanceof GatewayEditPart) {
            return Messages.WidgetPartsLabelProvider_gateway_label;
        } else if (element instanceof TaskEditPart) {
            TaskEditPart task = (TaskEditPart) element;
            if (task.isNonSubProcTask()) {
                return Messages.WidgetPartsLabelProvider_Task_label;
            } else {
                if (task.isEmbeddedSubProc()) {
                    return Messages.WidgetPartsLabelProvider_EmbeddedSubProc_label;
                } else if (task.isEventSubProc()) {
                    return Messages.WidgetPartsLabelProvider_EventSubProc_label;
                } else {
                    return Messages.WidgetPartsLabelProvider_IndiSubProc_label2;
                }
            }
        } else if (element instanceof EventEditPart) {
            EventEditPart event = (EventEditPart) element;

            switch (event.getEventFlowType()) {
            case EventFlowType.FLOW_START:
                return Messages.WidgetPartsLabelProvider_StartEvent_label;
            case EventFlowType.FLOW_END:
                return Messages.WidgetPartsLabelProvider_EndEvent_label;
            case EventFlowType.FLOW_INTERMEDIATE:
                return Messages.WidgetPartsLabelProvider_InterEvent_label;
            default:
                return Messages.WidgetPartsLabelProvider_Event_label;
            }
        } else if (element instanceof SequenceFlowEditPart) {
            return Messages.WidgetPartsLabelProvider_SeqFlow_label;
        } else if (element instanceof LaneEditPart) {
            LaneEditPart lep = (LaneEditPart) element;
            /* Lanes in Task Libraries are Task Sets */
            if (ProcessWidgetType.TASK_LIBRARY.equals(lep.getModelAdapter()
                    .getProcessType())) {
                return Messages.PaletteFactory_TaskSetTool_menu;
            }
            return Messages.WidgetPartsLabelProvider_Lane_label;
        } else if (element instanceof NoteEditPart) {
            return Messages.WidgetPartsLabelProvider_TextAnnot_label;
        } else if (element instanceof PoolEditPart) {
            return Messages.WidgetPartsLabelProvider_Pool_label;
        } else if (element instanceof AssociationEditPart) {
            return Messages.WidgetPartsLabelProvider_Association_label;
        } else if (element instanceof MessageFlowEditPart) {
            return Messages.WidgetPartsLabelProvider_MsgFlow_label;
        } else if (element instanceof GroupEditPart) {
            return Messages.WidgetPartsLabelProvider_Group_label;
        } else if (element instanceof DataObjectEditPart) {
            return Messages.WidgetPartsLabelProvider_DataObject_label;
        } else if (element instanceof ProcessEditPart) {
            ProcessEditPart pep = (ProcessEditPart) element;
            ProcessDiagramAdapter pda =
                    (ProcessDiagramAdapter) pep.getModelAdapter();
            if (ProcessWidgetType.CASE_SERVICE.equals(pda.getProcessType())) {

                return Messages.WidgetPartsLabelProvider_CaseServicePageflowProcess_label;
            } else if (ProcessWidgetType.BUSINESS_SERVICE.equals(pda
                    .getProcessType())) {

                return Messages.WidgetPartsLabelProvider_BusinessServicePageflowProcess_label;
            }
            if (ProcessWidgetType.PAGEFLOW_PROCESS.equals(pda.getProcessType())) {

                return Messages.WidgetPartsLabelProvider_PageflowProcess_label;
            } else if (ProcessWidgetType.SERVICE_PROCESS.equals(pda
                    .getProcessType())) {

                return Messages.WidgetPartsLabelProvider_ServiceProcess_label;
            } else if (ProcessWidgetType.TASK_LIBRARY.equals(pda
                    .getProcessType())) {

                return Messages.WidgetPartsLabelProvider_TaskLibrary_label;
            } else if (ProcessWidgetType.DECISION_FLOW.equals(pda
                    .getProcessType())) {

                return Messages.WidgetPartsLabelProvider_DecisionFlow_label;
            }
            return Messages.WidgetPartsLabelProvider_Process_label;

        } else if (element instanceof ConnectionLabelEditPart) {
            element = ((ConnectionLabelEditPart) element).getModel();
            return getText(((FlowLabelModel) element).getConnectionEditPart());
        }
        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {
        ImageRegistry ir = ProcessWidgetPlugin.getDefault().getImageRegistry();
        if (element instanceof IStructuredSelection
                && ((IStructuredSelection) element).size() == 1) {
            element = ((IStructuredSelection) element).getFirstElement();
        }

        if (element instanceof GatewayEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_GENERIC_GATEWAY_16);
        } else if (element instanceof TaskEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_TASK_16);
        } else if (element instanceof EventEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_EVENT_START_16);
        } else if (element instanceof SequenceFlowEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_FLOW_16);
        } else if (element instanceof LaneEditPart) {
            LaneEditPart lep = (LaneEditPart) element;
            /* Lanes in Task Libraries are Task Sets */
            if (ProcessWidgetType.TASK_LIBRARY.equals(lep.getModelAdapter()
                    .getProcessType())) {
                return ir.get(ProcessWidgetConstants.IMG_TOOL_TASKSET_16);
            }
            return ir.get(ProcessWidgetConstants.IMG_TOOL_LANE_16);
        } else if (element instanceof NoteEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_NOTE_16);
        } else if (element instanceof PoolEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_POOL_16);
        } else if (element instanceof AssociationEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_ASSOCIATION_16);
        } else if (element instanceof MessageFlowEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_MESSAGE_16);
        } else if (element instanceof GroupEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_GROUP_16);
        } else if (element instanceof DataObjectEditPart) {
            return ir.get(ProcessWidgetConstants.IMG_TOOL_DATA_OBJECT_16);
        } else if (element instanceof ProcessEditPart) {
            ProcessWidgetType type =
                    ((ProcessEditPart) element).getProcessWidgetType();
            if (ProcessWidgetType.CASE_SERVICE.equals(type)) {

                return ir
                        .get(ProcessWidgetConstants.IMG_CASE_SERVICE_PAGEFLOW_PROCESS);
            } else if (ProcessWidgetType.BUSINESS_SERVICE.equals(type)) {
                return ir
                        .get(ProcessWidgetConstants.IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS);
            } else if (ProcessWidgetType.PAGEFLOW_PROCESS.equals(type)) {

                return ir.get(ProcessWidgetConstants.IMG_PAGEFLOW_PROCESS);
            } else if (ProcessWidgetType.SERVICE_PROCESS.equals(type)) {

                return ir.get(ProcessWidgetConstants.IMG_SERVICE_PROCESS);
            } else if (ProcessWidgetType.TASK_LIBRARY.equals(type)) {

                return ir.get(ProcessWidgetConstants.IMG_TASK_LIBRARY);
            } else if (ProcessWidgetType.DECISION_FLOW.equals(type)) {

                return ir.get(ProcessWidgetConstants.IMG_DECISIONS_FLOW);
            } else {

                return ir.get(ProcessWidgetConstants.IMG_PROCESS);
            }
        } else if (element instanceof ConnectionLabelEditPart) {
            element = ((ConnectionLabelEditPart) element).getModel();
            return getImage(((FlowLabelModel) element).getConnectionEditPart());
        }
        return null;
    }
}
