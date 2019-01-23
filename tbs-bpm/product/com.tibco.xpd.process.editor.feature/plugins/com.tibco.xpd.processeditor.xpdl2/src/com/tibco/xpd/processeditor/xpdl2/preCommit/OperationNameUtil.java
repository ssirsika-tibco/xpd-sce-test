/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class OperationNameUtil {

    /**
     * 
     */
    private static final String UN_NAMED_RECEIVE_TASK = "UnNamedReceiveTask"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UN_NAMED_INTERMEDIATE_EVENT =
            "UnNamedIntermediateEvent"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UN_NAMED_START_EVENT = "UnNamedStartEvent"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UN_NAMED_START_IFC_EVENT =
            "UnNamedStartInterfaceEvent"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String UN_NAMED_INTERMEDIATE_IFC_EVENT =
            "UnNamedIntermediateInterfaceEvent"; //$NON-NLS-1$

    /**
     * 
     */
    private static Map<String, HashMap<String, String>> procMap =
            new HashMap<String, HashMap<String, String>>();

    /**
     * 
     */
    private static Map<String, HashMap<String, String>> procIfcMap =
            new HashMap<String, HashMap<String, String>>();

    private static int index = 0;

    public static String getSubstitutedName(InterfaceMethod interfaceMethod) {
        ProcessInterface processInterface =
                ProcessInterfaceUtil.getProcessInterface(interfaceMethod);
        if (processInterface != null) {
            HashMap<String, String> interfaceIdNameMap =
                    procIfcMap.get(processInterface.getId());
            if (interfaceIdNameMap == null) {
                interfaceIdNameMap = new HashMap<String, String>();
                procIfcMap.put(processInterface.getId(), interfaceIdNameMap);
            }

            for (StartMethod startMethod : processInterface.getStartMethods()) {
                if (TriggerType.MESSAGE_LITERAL
                        .equals(startMethod.getTrigger())
                        && startMethod.getName() == null
                        || (startMethod.getName() != null && "".equals(startMethod.getName()))) { //$NON-NLS-1$
                    index = 0;
                    while (interfaceIdNameMap
                            .containsValue(UN_NAMED_START_IFC_EVENT + index)) {
                        index++;
                    }
                    interfaceIdNameMap.put(startMethod.getId(),
                            UN_NAMED_START_IFC_EVENT + index);
                }
            }

            for (IntermediateMethod intermediateMethod : processInterface
                    .getIntermediateMethods()) {
                if (TriggerType.MESSAGE_LITERAL.equals(interfaceMethod
                        .getTrigger())
                        && intermediateMethod.getName() == null
                        || (intermediateMethod.getName() != null && "".equals(intermediateMethod.getName()))) { //$NON-NLS-1$
                    index = 0;
                    while (interfaceIdNameMap
                            .containsValue(UN_NAMED_INTERMEDIATE_IFC_EVENT
                                    + index)) {
                        index++;
                    }
                    interfaceIdNameMap.put(intermediateMethod.getId(),
                            UN_NAMED_INTERMEDIATE_IFC_EVENT + index);
                }
            }
            if (interfaceMethod.getName() == null
                    || (interfaceMethod.getName() != null && "".equals(interfaceMethod.getName()))) { //$NON-NLS-1$
                return interfaceIdNameMap.get(interfaceMethod.getId());
            }
        }
        return interfaceMethod.getName();
    }

    public static String getSubstitutedName(Activity activity) {
        Process process = activity.getProcess();
        if (process != null) {
            HashMap<String, String> activityIdNameMap =
                    procMap.get(process.getId());
            if (activityIdNameMap == null) {
                activityIdNameMap = new HashMap<String, String>();
                procMap.put(process.getId(), activityIdNameMap);
            }
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            Collection<Activity> startMessageEvents = new ArrayList<Activity>();
            Collection<Activity> intermediateMessageEvents =
                    new ArrayList<Activity>();
            Collection<Activity> rcvTasks = new ArrayList<Activity>();
            for (Activity act : allActivitiesInProc) {
                TaskType taskType = TaskObjectUtil.getTaskType(act);
                if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
                    rcvTasks.add(act);
                } else {
                    EventTriggerType eventTriggerType =
                            EventObjectUtil.getEventTriggerType(act);
                    if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                            .equals(eventTriggerType)) {
                        EventFlowType flowType =
                                EventObjectUtil.getFlowType(act);
                        if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                            startMessageEvents.add(act);
                        } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                .equals(flowType)) {
                            intermediateMessageEvents.add(act);
                        }
                    }
                }
            }
            for (Activity rcvTaskAct : rcvTasks) {
                if (rcvTaskAct.getName() == null
                        || (rcvTaskAct.getName() != null && "".equals(rcvTaskAct.getName()))) { //$NON-NLS-1$
                    index = 0;
                    while (activityIdNameMap
                            .containsValue(UN_NAMED_RECEIVE_TASK + index)) {
                        index++;
                    }
                    activityIdNameMap.put(rcvTaskAct.getId(),
                            UN_NAMED_RECEIVE_TASK + index);

                }
            }
            for (Activity intermediateEventAct : intermediateMessageEvents) {
                if (intermediateEventAct.getName() == null
                        || (intermediateEventAct.getName() != null && "".equals(intermediateEventAct.getName()))) { //$NON-NLS-1$
                    index = 0;
                    while (activityIdNameMap
                            .containsValue(UN_NAMED_INTERMEDIATE_EVENT + index)) {
                        index++;
                    }
                    activityIdNameMap.put(intermediateEventAct.getId(),
                            UN_NAMED_INTERMEDIATE_EVENT + index);

                }
            }
            for (Activity startEventAct : startMessageEvents) {
                if (startEventAct.getName() == null
                        || (startEventAct.getName() != null && "".equals(startEventAct.getName()))) { //$NON-NLS-1$
                    index = 0;
                    while (!activityIdNameMap
                            .containsKey(startEventAct.getId())
                            && activityIdNameMap
                                    .containsValue(UN_NAMED_START_EVENT + index)) {
                        index++;
                    }
                    if (!activityIdNameMap.containsKey(startEventAct.getId())) {
                        activityIdNameMap.put(startEventAct.getId(),
                                UN_NAMED_START_EVENT + index);
                    }

                }
            }

            if (activity.getName() == null
                    || (activity.getName() != null && "".equals(activity.getName()))) { //$NON-NLS-1$
                return activityIdNameMap.get(activity.getId());
            }
        }
        return activity.getName();
    }
}
