package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Command to set the color of the activity/transition to the new default color
 * if it's current colour is old defautl colour.
 * 
 * @author aallway
 * @since 3.4.2 (16 Sep 2010)
 */
public class ResetDefaultActivityColourCommand extends
        AbstractLateExecuteCommand {

    private ProcessWidgetColors targetColourSet;

    private ProcessWidgetColors sourceColourSet;

    /**
     * @param editingDomain
     * @param activity
     * @param sourceColourSet
     * @param targetColourSet
     */
    public ResetDefaultActivityColourCommand(EditingDomain editingDomain,
            Activity activity, ProcessWidgetColors sourceColourSet,
            ProcessWidgetColors targetColourSet) {
        super(editingDomain, activity);

        this.sourceColourSet = sourceColourSet;
        this.targetColourSet = targetColourSet;
    }

    @Override
    protected Command createCommand(EditingDomain editingDomain,
            Object contextObject) {
        Activity activity = (Activity) contextObject;

        CompoundCommand cmd = new CompoundCommand();

        /*
         * Get the ProcessWidgetColorId appropriate to the activity type.
         */
        String fillColorId = null;
        String lineColorId = null;

        TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
        if (taskType != null) {
            fillColorId = taskType.getGetDefaultFillColorId();
            lineColorId = taskType.getGetDefaultLineColorId();
        }

        if (fillColorId == null) {
            EventFlowType eventType = EventObjectUtil.getFlowType(activity);
            if (eventType != null) {
                fillColorId = eventType.getGetDefaultFillColorId();
                lineColorId = eventType.getGetDefaultLineColorId();

            }
        }

        if (fillColorId == null) {
            GatewayType gatewayType =
                    GatewayObjectUtil.getGatewayType(activity);
            if (gatewayType != null) {
                fillColorId = gatewayType.getGetDefaultFillColorId();
                lineColorId = gatewayType.getGetDefaultLineColorId();
            }
        }

        /*
         * Get the bpmn and pageflow process defrault colors for given color
         * ids.
         */
        WidgetRGB bpmnFillColor =
                sourceColourSet.getGraphicalNodeColor(null, fillColorId);
        WidgetRGB bpmnLineColor =
                sourceColourSet.getGraphicalNodeColor(null, lineColorId);

        WidgetRGB pageflowFillColor =
                targetColourSet.getGraphicalNodeColor(null, fillColorId);
        WidgetRGB pageflowLineColor =
                targetColourSet.getGraphicalNodeColor(null, lineColorId);

        /*
         * Set the activity to the default pageflow process color if it's
         * current colour is unset or is set to default for bpmn process (and if
         * it is differnet hat then default pageflow colour to be set).
         */
        if (pageflowFillColor != null && bpmnFillColor != null) {
            String pageflowFillColorStr = pageflowFillColor.toString();
            String bpmnFillColorStr = bpmnFillColor.toString();

            NodeGraphicsInfo nodeGraphicsInfo =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(activity);
            if (nodeGraphicsInfo != null) {
                String currentColor = nodeGraphicsInfo.getFillColor();

                if (currentColor == null
                        || bpmnFillColorStr.equals(currentColor)) {
                    if (!pageflowFillColorStr.equals(currentColor)) {
                        cmd.append(SetCommand.create(getEditingDomain(),
                                nodeGraphicsInfo,
                                Xpdl2Package.eINSTANCE
                                        .getNodeGraphicsInfo_FillColor(),
                                pageflowFillColorStr));
                    }
                }
            }
        }

        if (pageflowLineColor != null && bpmnLineColor != null) {
            String pageflowLineColorStr = pageflowLineColor.toString();
            String bpmnLineColorStr = bpmnLineColor.toString();

            NodeGraphicsInfo nodeGraphicsInfo =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(activity);
            if (nodeGraphicsInfo != null) {
                String currentLineColor = nodeGraphicsInfo.getBorderColor();

                if (currentLineColor == null
                        || bpmnLineColorStr.equals(currentLineColor)) {

                    if (!pageflowLineColorStr.equals(currentLineColor)) {
                        cmd.append(SetCommand.create(getEditingDomain(),
                                nodeGraphicsInfo,
                                Xpdl2Package.eINSTANCE
                                        .getNodeGraphicsInfo_BorderColor(),
                                pageflowLineColorStr));
                    }
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }
}