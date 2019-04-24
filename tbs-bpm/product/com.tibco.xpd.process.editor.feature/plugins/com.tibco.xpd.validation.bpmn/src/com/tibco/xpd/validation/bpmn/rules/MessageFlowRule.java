/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validate message flows - this has to be done as a package validation rule as
 * message flow are child of package not of the process.
 * 
 * @author aallway
 * @since 3.2
 */
public class MessageFlowRule extends PackageValidationRule {

    private static final String MUST_CONNECT_DIFFERENT_POOLS =
            "bpm.msgFlowMustConnectDifferentPools"; //$NON-NLS-1$

    private static final String INVALID_SOURCE = "bpm.msgFlowInvalidSource"; //$NON-NLS-1$

    private static final String INVALID_TARGET = "bpm.msgFlowInvalidTarget"; //$NON-NLS-1$

    /*
     * Sid ACE-476 Suppress individual Message Flow rules for ACE as they are
     * simply not supported at all (via AceProcessFlowRules).
     */
    private static boolean suppressMessageFlowRulesForAce = true;

    @Override
    public void validate(Package pckg) {
        if (suppressMessageFlowRulesForAce) {
            return;
        }

        for (Process process : pckg.getProcesses()) {
            validateMessageFlows(process);
        }

        return;
    }

    private void validateMessageFlows(Process process) {
        Collection<MessageFlow> msgFlows =
                Xpdl2ModelUtil.getAllMessageFlowsInProc(process);

        Map<String, Pool> poolMap = getPoolMap(process);

        Map<String, Activity> activityMap = getActivityMap(process);

        for (MessageFlow msgFlow : msgFlows) {
            validateMessageFlow(msgFlow, poolMap, activityMap);
        }

        return;
    }

    private void validateMessageFlow(MessageFlow msgFlow,
            Map<String, Pool> poolMap, Map<String, Activity> activityMap) {

        String srcId = msgFlow.getSource();
        String tgtId = msgFlow.getTarget();

        // 
        // Source / target object is either activity or pool
        Object srcObject = poolMap.get(srcId);
        if (srcObject == null) {
            srcObject = activityMap.get(srcId);
        }

        Object tgtObject = poolMap.get(tgtId);
        if (tgtObject == null) {
            tgtObject = activityMap.get(tgtId);
        }

        if (tgtObject != null && srcObject != null) {
            validateDifferentPools(msgFlow, srcObject, tgtObject);

            validateSourceObject(msgFlow, srcObject);

            validateTargetObject(msgFlow, tgtObject);
        }

        return;
    }

    private void validateSourceObject(MessageFlow msgFlow, Object srcObject) {
        // 
        // "Source of Message Flow must be Pool, Message Throwing Event or Task (except Receive)."
        boolean valid = false;

        if (srcObject instanceof Pool) {
            valid = true;

        } else if (srcObject instanceof Activity) {
            Activity activity = (Activity) srcObject;

            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            if (taskType != null) {
                // All task types except Receive are valid
                if (!TaskType.RECEIVE_LITERAL.equals(taskType)) {
                    valid = true;
                }

            } else if (activity.getEvent() != null) {
                // End MEssage event and Throw Message Intermediate are ok.
                if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    valid = true;
                }
            }
        }

        if (!valid) {
            addIssue(INVALID_SOURCE, msgFlow);
        }

        return;
    }

    private void validateTargetObject(MessageFlow msgFlow, Object tgtObject) {
        // 
        // "Target of Message Flow must be Pool, Message Catching Event Event or Task (except Send)"
        boolean valid = false;

        if (tgtObject instanceof Pool) {
            valid = true;

        } else if (tgtObject instanceof Activity) {
            Activity activity = (Activity) tgtObject;

            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            if (taskType != null) {
                // All task types except Send are valid
                if (!TaskType.SEND_LITERAL.equals(taskType)) {
                    valid = true;
                }

            } else if (activity.getEvent() != null) {
                // End MEssage event and Throw Message Intermediate are ok.
                if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    valid = true;
                }
            }
        }

        if (!valid) {
            addIssue(INVALID_TARGET, msgFlow);
        }

        return;

    }

    private void validateDifferentPools(MessageFlow msgFlow, Object srcObject,
            Object tgtObject) {
        Pool srcPool;
        if (srcObject instanceof Pool) {
            srcPool = (Pool) srcObject;
        } else {
            srcPool = Xpdl2ModelUtil.getParentPool((Activity) srcObject);
        }

        Pool tgtPool;
        if (tgtObject instanceof Pool) {
            tgtPool = (Pool) tgtObject;
        } else {
            tgtPool = Xpdl2ModelUtil.getParentPool((Activity) tgtObject);
        }

        if (srcPool == tgtPool) {
            addIssue(MUST_CONNECT_DIFFERENT_POOLS, msgFlow);
        }

        return;
    }

    /**
     * @param process
     * @return Activity Id to Activity object map.
     */
    private Map<String, Activity> getActivityMap(Process process) {
        Map<String, Activity> activityMap = new HashMap<String, Activity>();
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            activityMap.put(activity.getId(), activity);
        }
        return activityMap;
    }

    /**
     * @param process
     * @return Pool Id to Pool object map.
     */
    private Map<String, Pool> getPoolMap(Process process) {
        Map<String, Pool> poolMap = new HashMap<String, Pool>();
        for (Pool pool : Xpdl2ModelUtil.getProcessPools(process)) {
            poolMap.put(pool.getId(), pool);
        }
        return poolMap;
    }

}
