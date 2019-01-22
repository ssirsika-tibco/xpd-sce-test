/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.BusinessProcess;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Util for retrieving port type operation web service operation and to verify
 * whether a given API activity is of Web service implementation type.
 * 
 * @author rsomayaj
 * 
 */
public class WebServiceOperationUtil {

    /**
     * 
     * @param activity
     * @return
     */
    public static WebServiceOperation getWebServiceOperation(Activity activity) {
        TaskType taskType = TaskObjectUtil.getTaskType(activity);
        WebServiceOperation webServiceOperation = null;
        if (activity.getEvent() != null) {

            EventFlowType eventFlowType = EventObjectUtil.getFlowType(activity);
            boolean isWebServiceImplementation = false;
            switch (eventFlowType.getValue()) {
            case EventFlowType.FLOW_START:
                isWebServiceImplementation =
                        isWebServiceImplementationType(activity.getEvent());
                break;
            case EventFlowType.FLOW_INTERMEDIATE:
                isWebServiceImplementation =
                        isWebServiceImplementationType(activity.getEvent());
                break;
            case EventFlowType.FLOW_END:
                isWebServiceImplementation =
                        isWebServiceImplementationType(activity.getEvent());
                break;
            }
            if (isWebServiceImplementation && eventFlowType != null) {
                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(activity);
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil.getTriggerResultMessage(activity);
                switch (eventTriggerType.getValue()) {
                case EventTriggerType.EVENT_MESSAGE_CATCH:
                case EventTriggerType.EVENT_MESSAGE_THROW:
                    webServiceOperation =
                            triggerResultMessage.getWebServiceOperation();
                }
            }

        } else if (taskType != null) {
            switch (taskType.getValue()) {
            case TaskType.SERVICE:
                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();
                // If the service task is a web service implementation
                if (isWebServiceImplementationType(taskService)) {
                    webServiceOperation = taskService.getWebServiceOperation();
                }
                break;
            case TaskType.RECEIVE:
                TaskReceive taskReceive =
                        ((Task) activity.getImplementation()).getTaskReceive();
                if (isWebServiceImplementationType(taskReceive)) {
                    webServiceOperation = taskReceive.getWebServiceOperation();
                }
                break;
            case TaskType.SEND:
                TaskSend taskSend =
                        ((Task) activity.getImplementation()).getTaskSend();
                if (isWebServiceImplementationType(taskSend)) {
                    webServiceOperation = taskSend.getWebServiceOperation();
                }
                break;
            }

        }
        return webServiceOperation;
    }

    /**
     * 
     * @param activity
     * @return
     */
    public static PortTypeOperation getPortTypeOperation(Activity activity) {
        TaskType taskType = TaskObjectUtil.getTaskType(activity);
        PortTypeOperation portTypeOperation = null;
        if (activity.getEvent() != null) {
            EventFlowType eventFlowType = EventObjectUtil.getFlowType(activity);
            boolean isWebServiceImplementation = false;
            if (eventFlowType != null) {
                switch (eventFlowType.getValue()) {
                case EventFlowType.FLOW_START:
                    isWebServiceImplementation =
                            isWebServiceImplementationType(activity.getEvent());
                    break;
                case EventFlowType.FLOW_INTERMEDIATE:
                    isWebServiceImplementation =
                            isWebServiceImplementationType(activity.getEvent());
                    break;
                case EventFlowType.FLOW_END:
                    isWebServiceImplementation =
                            isWebServiceImplementationType(activity.getEvent());
                    break;
                }
                if (isWebServiceImplementation) {
                    TriggerResultMessage triggerResultMessage =
                            EventObjectUtil.getTriggerResultMessage(activity);
                    if (triggerResultMessage != null) {
                        EventTriggerType eventTriggerType =
                                EventObjectUtil.getEventTriggerType(activity);
                        switch (eventTriggerType.getValue()) {
                        case EventTriggerType.EVENT_MESSAGE_CATCH:
                        case EventTriggerType.EVENT_MESSAGE_THROW:
                            portTypeOperation =
                                    getPortTypeOperation(triggerResultMessage);
                        }
                    }
                }
            }
        } else if (taskType != null) {
            switch (taskType.getValue()) {
            case TaskType.SERVICE:
                TaskService taskService =
                        ((Task) activity.getImplementation()).getTaskService();
                // If the service task is a web service implementation
                if (isWebServiceImplementationType(taskService)) {
                    portTypeOperation = getPortTypeOperation(taskService);
                }
                break;
            case TaskType.RECEIVE:
                TaskReceive taskReceive =
                        ((Task) activity.getImplementation()).getTaskReceive();
                if (isWebServiceImplementationType(taskReceive)) {
                    portTypeOperation = getPortTypeOperation(taskReceive);
                }
                break;
            case TaskType.SEND:
                TaskSend taskSend =
                        ((Task) activity.getImplementation()).getTaskSend();
                if (isWebServiceImplementationType(taskSend)) {
                    portTypeOperation = getPortTypeOperation(taskSend);
                }
                break;
            }

        }

        return portTypeOperation;
    }

    /**
     * 
     * @param activity
     * @return TRUE if the xpdExt:ImplementationType is "WebService", for a
     *         given Throw Error Event, it returns TRUE if the request activity
     *         is a xpdExt:ImplementationType is a "WebService"
     */
    public static boolean isWebServiceImplementationType(Activity activity) {
        if (activity != null) {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            if (activity.getEvent() != null) {
                EventFlowType eventFlowType =
                        EventObjectUtil.getFlowType(activity);
                if (eventFlowType != null) {
                    switch (eventFlowType.getValue()) {
                    case EventFlowType.FLOW_START:
                        return isWebServiceImplementationType(activity
                                .getEvent());
                    case EventFlowType.FLOW_INTERMEDIATE:

                        if (EventTriggerType.EVENT_ERROR_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))) {
                            Object thrower =
                                    BpmnCatchableErrorUtil
                                            .getErrorThrower(activity);
                            if (thrower instanceof Activity
                                    && !(thrower.equals(activity))) {
                                return isWebServiceImplementationType((Activity) thrower);
                            }
                        }
                        return isWebServiceImplementationType(activity
                                .getEvent());
                    case EventFlowType.FLOW_END:
                        //
                        if (ThrowErrorEventUtil
                                .isThrowFaultMessageErrorEvent(activity)) {
                            Activity requestActivity =
                                    ThrowErrorEventUtil
                                            .getRequestActivity(activity);
                            return isWebServiceImplementationType(requestActivity);
                        }
                        return isWebServiceImplementationType(activity
                                .getEvent());
                    }
                }
            }
            if (taskType != null) {
                switch (taskType.getValue()) {
                case TaskType.SERVICE:
                    TaskService taskService =
                            ((Task) activity.getImplementation())
                                    .getTaskService();
                    // If the service task is a web service implementation
                    return isWebServiceImplementationType(taskService);
                case TaskType.RECEIVE:
                    TaskReceive taskReceive =
                            ((Task) activity.getImplementation())
                                    .getTaskReceive();
                    return isWebServiceImplementationType(taskReceive);
                case TaskType.SEND:
                    TaskSend taskSend =
                            ((Task) activity.getImplementation()).getTaskSend();
                    return isWebServiceImplementationType(taskSend);
                }

            }
        }
        return false;
    }

    /**
     * @param taskService
     * @return
     */
    private static boolean isWebServiceImplementationType(
            OtherAttributesContainer implementationTypeContainer) {
        Object objImplementationType =
                Xpdl2ModelUtil.getOtherAttribute(implementationTypeContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        return TaskImplementationTypeDefinitions.WEB_SERVICE
                .equals(objImplementationType);
    }

    // XPD-288
    public static BusinessProcess getBusinessProcess(Activity activity) {

        if (TaskType.SEND_LITERAL.equals(TaskObjectUtil.getTaskType(activity))) {
            Object otherElement =
                    Xpdl2ModelUtil.getOtherElement(((Task) activity
                            .getImplementation()).getTaskSend(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessProcess());
            if (otherElement instanceof BusinessProcess) {
                return (BusinessProcess) otherElement;
            }
        }
        return null;
    }

    public static boolean isInvokeBusinessProcessImplementationType(
            OtherAttributesContainer implementationTypeContainer) {
        Object objImplementationType =
                Xpdl2ModelUtil.getOtherAttribute(implementationTypeContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType());
        return ActionUtil.INVOKE_BUSINESS_PROCESS.equals(objImplementationType);
    }

    /**
     * @param activity
     * @return true if the given activity is on ethat invokes a business process
     *         service.
     */
    public static boolean isInvokeBusinessProcessImplementationType(
            Activity activity) {
        String extImplId =
                TaskObjectUtil.getTaskImplementationExtensionId(activity);
        return ActionUtil.INVOKE_BUSINESS_PROCESS.equals(extImplId);
    }

    /**
     * Get the biz process operation name from the given business process.
     * 
     * @param process
     */
    private static String getBusinessProcessOperationName(Process process) {
        for (Activity activity : process.getActivities()) {
            Event event = activity.getEvent();
            if (event instanceof StartEvent) {
                TriggerType trigger = ((StartEvent) event).getTrigger();
                if (TriggerType.MESSAGE_LITERAL.equals(trigger)) {
                    return activity.getName();
                }
            }
        }
        return null;
    }

    /**
     * @param taskService
     */
    private static PortTypeOperation getPortTypeOperation(
            OtherElementsContainer portTypeOpContainer) {
        Object objPortTypeOperation =
                Xpdl2ModelUtil.getOtherElement(portTypeOpContainer,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PortTypeOperation());
        if (objPortTypeOperation instanceof PortTypeOperation) {
            return (PortTypeOperation) objPortTypeOperation;
        }
        return null;
    }
}
