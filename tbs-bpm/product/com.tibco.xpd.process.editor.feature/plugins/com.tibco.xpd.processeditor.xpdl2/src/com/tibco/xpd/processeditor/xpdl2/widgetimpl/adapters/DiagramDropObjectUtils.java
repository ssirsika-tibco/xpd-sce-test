/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.processeditor.xpdl2.util.SelectObjectOrderDialog;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Drop object utility methods for local Xpdl2 Adapters.
 * 
 * @author aallway
 * 
 */
public class DiagramDropObjectUtils {

    /**
     * Get icon image for the given task type.
     * 
     * @param taskType
     * @return
     */
    public static Image getImageForTaskType(TaskType taskType) {
        String imgId = null;
        switch (taskType.getValue()) {
        case TaskType.EMBEDDED_SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_EMBEDDED_16;
            break;
        case TaskType.EVENT_SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_EVENT_16;
            break;
        case TaskType.MANUAL:
            imgId = ProcessWidgetConstants.IMG_TOOL_MANUALTASK_16;
            break;
        case TaskType.NONE:
            imgId = ProcessWidgetConstants.IMG_TOOL_TASK_16;
            break;
        case TaskType.RECEIVE:
            imgId = ProcessWidgetConstants.IMG_TOOL_RECEIVETASK_16;
            break;
        case TaskType.REFERENCE:
            imgId = ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_16;
            break;
        case TaskType.SCRIPT:
            imgId = ProcessWidgetConstants.IMG_TOOL_SCRIPTTASK_16;
            break;
        case TaskType.SEND:
            imgId = ProcessWidgetConstants.IMG_TOOL_SENDTASK_16;
            break;
        case TaskType.SERVICE:
            imgId = ProcessWidgetConstants.IMG_TOOL_SERVICETASK_16;
            break;
        case TaskType.SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_16;
            break;
        case TaskType.USER:
            imgId = ProcessWidgetConstants.IMG_TOOL_USERTASK_16;
            break;
        case TaskType.DTABLE:
            imgId = ProcessWidgetConstants.IMG_TOOL_DTABLETASK_16;
            break;
        }

        Image img = null;
        if (imgId != null && !XpdResourcesPlugin.isInHeadlessMode()) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(imgId);
        }
        return img;
    }

    /**
     * Get icon image for the given task type.
     * 
     * @param taskType
     * @return
     */
    public static Image getImage24ForTaskType(TaskType taskType) {
        String imgId = null;
        switch (taskType.getValue()) {
        case TaskType.EMBEDDED_SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_EMBEDDED_24;
            break;
        case TaskType.EVENT_SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_EVENT_24;
            break;
        case TaskType.MANUAL:
            imgId = ProcessWidgetConstants.IMG_TOOL_MANUALTASK_24;
            break;
        case TaskType.NONE:
            imgId = ProcessWidgetConstants.IMG_TOOL_TASK_24;
            break;
        case TaskType.RECEIVE:
            imgId = ProcessWidgetConstants.IMG_TOOL_RECEIVETASK_24;
            break;
        case TaskType.REFERENCE:
            imgId = ProcessWidgetConstants.IMG_TOOL_REFERENCETASK_24;
            break;
        case TaskType.SCRIPT:
            imgId = ProcessWidgetConstants.IMG_TOOL_SCRIPTTASK_24;
            break;
        case TaskType.SEND:
            imgId = ProcessWidgetConstants.IMG_TOOL_SENDTASK_24;
            break;
        case TaskType.SERVICE:
            imgId = ProcessWidgetConstants.IMG_TOOL_SERVICETASK_24;
            break;
        case TaskType.SUBPROCESS:
            imgId = ProcessWidgetConstants.IMG_TOOL_SUBPROC_24;
            break;
        case TaskType.USER:
            imgId = ProcessWidgetConstants.IMG_TOOL_USERTASK_24;
            break;
        case TaskType.DTABLE:
            imgId = ProcessWidgetConstants.IMG_TOOL_DTABLETASK_24;
            break;
        }

        Image img = null;
        if (imgId != null) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(imgId);
        }
        return img;
    }

    public static Image getImageForEventType(EventFlowType flowType,
            EventTriggerType trigType) {
        String imgId = null;

        if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_16;
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_MULTIPLE_16;
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_START_CONDITIONAL_16;
                break;
            case EventTriggerType.EVENT_TIMER:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_TIMER_16;
                break;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_16;
                break;
            }

        } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_CANCEL:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_CANCEL_16;
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_END_COMPENSATION_16;
                break;
            case EventTriggerType.EVENT_ERROR:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_ERROR_16;
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_16;
                break;
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_16;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_MULTIPLE_16;
                break;
            case EventTriggerType.EVENT_TERMINATE:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_TERMINATE_16;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_SIGNAL_16;
                break;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_16;
                break;
            }
        } else {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_CANCEL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_16;
                break;
            case EventTriggerType.EVENT_COMPENSATION_CATCH:
                // XPDL21 Handle intermediate compensation throw.
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_16;
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                // XPDL21 Handle intermediate compensation throw.
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_16;
                break;
            case EventTriggerType.EVENT_ERROR:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_16;
                break;
            case EventTriggerType.EVENT_LINK_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_16;
                break;
            case EventTriggerType.EVENT_LINK_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_16;
                break;
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_16;
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_16;
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                // XPDL21 Handle intermediate multiple throw.
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_16;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                // XPDL21 Handle intermediate multiple throw.
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_16;
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_16;
                break;
            case EventTriggerType.EVENT_TIMER:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_TIMER_16;
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_16;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_16;
                break;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_16;
                break;
            }
        }

        Image img = null;
        if (imgId != null) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(imgId);
        }
        return img;
    }

    public static Image getImage24ForEventType(EventFlowType flowType,
            EventTriggerType trigType) {
        String imgId = null;

        if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_MESSAGE_24;
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_MULTIPLE_24;
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_START_CONDITIONAL_24;
                break;
            case EventTriggerType.EVENT_TIMER:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_TIMER_24;
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_SIGNAL_24;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_START_24;
                break;
            }
        } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_CANCEL:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_CANCEL_24;
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_END_COMPENSATION_24;
                break;
            case EventTriggerType.EVENT_ERROR:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_ERROR_24;
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_MESSAGE_24;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_MULTIPLE_24;
                break;
            case EventTriggerType.EVENT_TERMINATE:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_TERMINATE_24;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_SIGNAL_24;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_END_24;
                break;
            }
        } else {
            switch (trigType.getValue()) {
            case EventTriggerType.EVENT_CANCEL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CANCEL_CATCH_24;
                break;
            case EventTriggerType.EVENT_COMPENSATION_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_CATCH_24;
                break;
            case EventTriggerType.EVENT_COMPENSATION_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_COMPENSATION_THROW_24;
                break;
            case EventTriggerType.EVENT_ERROR:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_ERROR_CATCH_24;
                break;
            case EventTriggerType.EVENT_LINK_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_CATCH_24;
                break;
            case EventTriggerType.EVENT_LINK_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_LINK_THROW_24;
                break;
            case EventTriggerType.EVENT_MESSAGE_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_CATCH_24;
                break;
            case EventTriggerType.EVENT_MESSAGE_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MESSAGE_THROW_24;
                break;
            case EventTriggerType.EVENT_MULTIPLE_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_CATCH_24;
                break;
            case EventTriggerType.EVENT_MULTIPLE_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_MULTIPLE_THROW_24;
                break;
            case EventTriggerType.EVENT_CONDITIONAL:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_CONDITIONAL_24;
                break;
            case EventTriggerType.EVENT_TIMER:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_TIMER_24;
                break;
            case EventTriggerType.EVENT_SIGNAL_CATCH:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_CATCH_24;
                break;
            case EventTriggerType.EVENT_SIGNAL_THROW:
                imgId =
                        ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_SIGNAL_THROW_24;
            default:
                imgId = ProcessWidgetConstants.IMG_TOOL_EVENT_INTERMEDIATE_24;
                break;
            }
        }

        Image img = null;
        if (imgId != null) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(imgId);
        }
        return img;
    }

    /**
     * Get icon image for the given gateway type.
     * 
     * @param type
     * @return
     */
    public static Image getImageForGatewayType(GatewayType type) {
        String imgId = null;

        switch (type.getValue()) {
        case GatewayType.COMPLEX:
            imgId = ProcessWidgetConstants.IMG_TOOL_COMPLEX_GATEWAY_16;
            break;
        case GatewayType.INCLUSIVE:
            imgId = ProcessWidgetConstants.IMG_TOOL_OR_GATEWAY_16;
            break;
        case GatewayType.PARALLEL:
            imgId = ProcessWidgetConstants.IMG_TOOL_PARALLEL_GATEWAY_16;
            break;
        case GatewayType.EXLCUSIVE_DATA:
            imgId = ProcessWidgetConstants.IMG_TOOL_XORDATA_GATEWAY_16;
            break;
        case GatewayType.EXCLUSIVE_EVENT:
            imgId = ProcessWidgetConstants.IMG_TOOL_XOREVENT_GATEWAY_16;
            break;
        default:
            imgId = ProcessWidgetConstants.IMG_TOOL_GENERIC_GATEWAY_16;
            break;
        }

        Image img = null;
        if (imgId != null) {
            img =
                    ProcessWidgetPlugin.getDefault().getImageRegistry()
                            .get(imgId);
        }
        return img;
    }

    /**
     * Given a list of new activity objects, create transitions that join them
     * all and return a list of the transitions.
     * 
     * @param activities
     * @return
     */
    public static List<Transition> joinActivitiesWithTransitions(
            List<Activity> activities) {
        List<Transition> transitions = new ArrayList<Transition>();

        ProcessWidgetType processType = null;

        if (activities.size() != 0) {
            Process process = activities.get(0).getProcess();
            if (process != null) {
                processType = TaskObjectUtil.getProcessType(process);
            }
        }

        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE);

        int numActs = activities.size();

        for (int i = 0; i < (numActs - 1); i++) {
            Activity src = activities.get(i);
            Activity tgt = activities.get(i + 1);

            Transition trans =
                    ElementsFactory.createTransition(src,
                            tgt,
                            SequenceFlowType.UNCONTROLLED_LITERAL,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            lineColor.toString());

            transitions.add(trans);
        }

        return transitions;
    }

    /**
     * Create a annotation object for the given task type.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param location
     * @param taskType
     * 
     * @return new annotation object
     */
    public static Artifact createAnnotationObject(Process process,
            String laneId, Point location) {
        Dimension objSize =
                new Dimension(0, ProcessWidgetConstants.NOTE_HEIGHT);

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.NOTE_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.NOTE_LINE);

        Artifact artifact =
                ElementsFactory.createArtifact(location,
                        objSize,
                        ArtifactType.ANNOTATION_LITERAL,
                        laneId,
                        fillColor == null ? null : fillColor.toString(),
                        lineColor.toString());

        return artifact;
    }

    /**
     * Create a annotation object for the given task type.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param location
     * @param taskType
     * 
     * @return new annotation object
     */
    public static Artifact createDataObject(Process process, String laneId,
            Point location) {
        Dimension objSize =
                new Dimension(ProcessWidgetConstants.DATAOBJECT_WIDTH_SIZE,
                        ProcessWidgetConstants.DATAOBJECT_HEIGHT_SIZE);

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.DATAOBJECT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.DATAOBJECT_LINE);

        Artifact artifact =
                ElementsFactory.createArtifact(location,
                        objSize,
                        ArtifactType.DATA_OBJECT_LITERAL,
                        laneId,
                        fillColor.toString(),
                        lineColor.toString());

        return artifact;
    }

    /**
     * Create a task object for the given task type.
     * 
     * @param laneId
     *            Parent lane id or null for activity set.
     * @param location
     * @param taskType
     * @return
     */
    public static Activity createTaskObject(Process process, String laneId,
            Point location, TaskType taskType) {
        // Setup and create new task activity.
        Dimension objSize;

        /*
         * ABPM-911: Saket: An event subprocess should mostly behave like an
         * embedded sub-process.
         */
        if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(taskType)
                || TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskType)) {
            objSize =
                    new Dimension(ProcessWidgetConstants.EMBSUBFLOW_WIDTH_SIZE,
                            ProcessWidgetConstants.EMBSUBFLOW_HEIGHT_SIZE);
        } else if (TaskType.SUBPROCESS_LITERAL.equals(taskType)) {
            objSize =
                    new Dimension(ProcessWidgetConstants.SUBFLOW_WIDTH_SIZE,
                            ProcessWidgetConstants.SUBFLOW_HEIGHT_SIZE);
        } else {
            objSize =
                    new Dimension(ProcessWidgetConstants.TASK_WIDTH_SIZE,
                            ProcessWidgetConstants.TASK_HEIGHT_SIZE);

        }

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                taskType.getGetDefaultFillColorId());
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null,
                                taskType.getGetDefaultLineColorId());

        Activity act =
                ElementsFactory.createTask(location,
                        objSize,
                        laneId,
                        taskType,
                        fillColor.toString(),
                        lineColor.toString());

        return act;
    }

    /**
     * Create a new event object for given event flow type
     * (start/end/intermediate) and event trigger type).
     * 
     * @param laneId
     * @param location
     * @param flowType
     * @param triggerType
     * @return
     */
    public static Activity createEventObject(Process process, String laneId,
            Point location, EventFlowType flowType, EventTriggerType triggerType) {
        // Setup and create new task activity.
        Dimension objSize;
        String fillColorId;
        String lineColorId;

        if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
            objSize =
                    new Dimension(ProcessWidgetConstants.START_EVENT_SIZE,
                            ProcessWidgetConstants.START_EVENT_SIZE);
            fillColorId = ProcessWidgetColors.START_EVENT_FILL;
            lineColorId = ProcessWidgetColors.START_EVENT_LINE;

        } else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
            objSize =
                    new Dimension(ProcessWidgetConstants.END_EVENT_SIZE,
                            ProcessWidgetConstants.END_EVENT_SIZE);
            fillColorId = ProcessWidgetColors.END_EVENT_FILL;
            lineColorId = ProcessWidgetColors.END_EVENT_LINE;

        } else {
            objSize =
                    new Dimension(
                            ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE,
                            ProcessWidgetConstants.INTERMEDIATE_EVENT_SIZE);
            fillColorId = ProcessWidgetColors.INTERMEDIATE_EVENT_FILL;
            lineColorId = ProcessWidgetColors.INTERMEDIATE_EVENT_LINE;
        }

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null, fillColorId);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null, lineColorId);

        Activity act =
                ElementsFactory.createEvent(location,
                        objSize,
                        laneId,
                        flowType,
                        triggerType,
                        fillColor.toString(),
                        lineColor.toString(),
                        null);

        return act;
    }

    /**
     * Create a gateway object for given event flow type
     * (start/end/intermediate) and event trigger type).
     * 
     * @param laneId
     * @param location
     * @param flowType
     * @param triggerType
     * 
     * @return new gateway object.
     */
    public static Activity createGatewayObject(Process process, String laneId,
            Point location, GatewayType type) {
        // Setup and create new task activity.
        Dimension objSize;
        String fillColorId;
        String lineColorId;

        objSize =
                new Dimension(ProcessWidgetConstants.GATEWAY_WIDTH_SIZE,
                        ProcessWidgetConstants.GATEWAY_HEIGHT_SIZE);

        if (GatewayType.PARALLEL_LITERAL.equals(type)) {
            fillColorId = ProcessWidgetColors.PARALLEL_GATEWAY_FILL;
            lineColorId = ProcessWidgetColors.AND_GATEWAY_LINE;

        } else if (GatewayType.COMPLEX_LITERAL.equals(type)) {
            fillColorId = ProcessWidgetColors.COMPLEX_GATEWAY_FILL;
            lineColorId = ProcessWidgetColors.COMPLEX_GATEWAY_LINE;

        } else if (GatewayType.EXLCUSIVE_EVENT_LITERAL.equals(type)) {
            fillColorId = ProcessWidgetColors.EXCLUSIVE_EVENT_GATEWAY_FILL;
            lineColorId = ProcessWidgetColors.XOREVENT_GATEWAY_LINE;

        } else if (GatewayType.INCLUSIVE_LITERAL.equals(type)) {
            fillColorId = ProcessWidgetColors.INCLUSIVE_GATEWAY_FILL;
            lineColorId = ProcessWidgetColors.OR_GATEWAY_LINE;

        } else {
            fillColorId = ProcessWidgetColors.EXCLUSIVE_DATA_GATEWAY_FILL;
            lineColorId = ProcessWidgetColors.XORDATA_GATEWAY_LINE;
        }

        ProcessWidgetType processType = TaskObjectUtil.getProcessType(process);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null, fillColorId);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(processType)
                        .getGraphicalNodeColor(null, lineColorId);

        Activity act =
                ElementsFactory.createGateway(location,
                        objSize,
                        laneId,
                        type,
                        fillColor.toString(),
                        lineColor.toString());

        return act;
    }

    /**
     * @param taskCount
     * @return a list of rectangles suitably spaced for the given number of
     *         tasks.
     */
    public static List<Rectangle> getTaskFeedbackRectangles(int taskCount) {
        List<Rectangle> rects = new ArrayList<Rectangle>();

        for (int i = 0; i < taskCount; i++) {
            Rectangle rc =
                    new Rectangle(
                            i
                                    * (int) (ProcessWidgetConstants.TASK_WIDTH_SIZE * 1.5f),
                            0, ProcessWidgetConstants.TASK_WIDTH_SIZE,
                            ProcessWidgetConstants.TASK_HEIGHT_SIZE);
            rects.add(rc);
        }
        return rects;
    }

    /**
     * Given a list of objects and label provider, popup a dialog allowing user
     * to select order of the objects.
     * 
     * @param parentShell
     * @param objects
     * @param labelProvider
     * @param title
     * 
     * @return List in user selected order or <b>null</b> if user cancelled.
     */
    public static List<Object> selectObjectOrder(Shell parentShell,
            List<Object> objects, ILabelProvider labelProvider, String title) {
        SelectObjectOrderDialog dlg =
                new SelectObjectOrderDialog(parentShell, title, objects,
                        labelProvider);

        if (dlg.open() == SelectObjectOrderDialog.OK) {
            return dlg.getOrderObjects();
        }
        return null;
    }

    /**
     * Check if the list of objects are are parented by the given xpdl2 package.
     * 
     * @param obj1
     * @param pkg
     * @return true if they are
     */
    public static boolean checkFromSamePackage(Collection<Object> objects,
            Package pkg) {
        for (Object o : objects) {
            if (!(o instanceof EObject)
                    || Xpdl2ModelUtil.getPackage((EObject) o) != pkg) {
                return false;
            }
        }
        return true;
    }

}
