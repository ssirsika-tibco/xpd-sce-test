/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.wsdl.Operation;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check if the same co-relation data is mapped to an
 * activity which is configured with a same operation.
 * 
 * Covers Receive task multi-instance parallel loop to see that only Array data
 * co-relation is mapped.
 * 
 * @author rsomayaj
 * @since 3.3 (1 Apr 2010)
 */
public class CorrelationDataMITaskRule extends ProcessValidationRule {

    private static final String RECV_MULTIINSTANCE_CORRELATION_DISALLOWED =
            "bx.recvtaskmultiinstanceparallel.disallowed"; //$NON-NLS-1$

    private static final String WARN_MORE_THAN_ONE_RCV_TASK_ACTIVE =
            "bx.rcvtaskeventscorrelation.behaviourindeterminate"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param activities
     * @param transitions
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        IProject project = WorkingCopyUtil.getProjectFor(process);
        HashMap<Operation, Set<Activity>> operationActivityMap =
                new HashMap<Operation, Set<Activity>>();

        for (Activity activity : allActivitiesInProc) {
            TaskType taskType = TaskObjectUtil.getTaskType(activity);
            // For Multi Instance Parallel Receive tasks - error
            // if co-relation data mapped is not array type then complain
            verifyMultiInstanceParallel(activity, taskType);

            // else if Receive task, or start message, or intermediate message
            // with the same port type, and has the same set of co-relation
            // data, then warn
            if (ReplyActivityUtil.isIncomingRequestActivity(activity)) {
                WsdlServiceKey wsdlServiceKey =
                        ProcessUIUtil.getWsdlServiceKey(activity);
                if (null != wsdlServiceKey) {
                    javax.wsdl.Operation operation =
                            WsdlIndexerUtil.getOperation(project,
                                    wsdlServiceKey,
                                    true,
                                    true);

                    if (null != operation) {
                        Set<Activity> activitySet =
                                operationActivityMap.get(operation);
                        if (activitySet == null) {
                            activitySet = new HashSet<Activity>();
                            operationActivityMap.put(operation, activitySet);
                        }
                        activitySet.add(activity);
                    }
                }
            }
        }

        for (Operation op : operationActivityMap.keySet()) {
            Set<Activity> activitySet = operationActivityMap.get(op);
            for (Activity outerActivity : activitySet) {
                for (Activity innerActivity : activitySet) {
                    if (outerActivity != innerActivity) {
                        if (doesHaveSameCorrelationData(innerActivity,
                                outerActivity)) {

                            List<String> messages = new ArrayList<String>();
                            messages.add(innerActivity.getName());
                            messages.add(outerActivity.getName());
                            addIssue(WARN_MORE_THAN_ONE_RCV_TASK_ACTIVE,
                                    innerActivity,
                                    messages);
                        }
                    }
                }

            }
        }
    }

    /**
     * @param activity1
     * @param activity2
     * @return
     */
    private boolean doesHaveSameCorrelationData(Activity activity1,
            Activity activity2) {

        List<DataField> correlationDataForActivity1 =
                new ArrayList<DataField>(ProcessInterfaceUtil
                        .getAssociatedCorrelationDataForActivity(activity1));
        List<DataField> correlationDataForActivity2 =
                new ArrayList<DataField>(ProcessInterfaceUtil
                        .getAssociatedCorrelationDataForActivity(activity2));

        Collections.sort(correlationDataForActivity1, correlationComparator);
        Collections.sort(correlationDataForActivity2, correlationComparator);

        return correlationDataForActivity1.equals(correlationDataForActivity2);
    }

    Comparator<DataField> correlationComparator = new Comparator<DataField>() {
        public int compare(DataField dataField1, DataField datafield2) {
            return dataField1.getName().compareTo(datafield2.getName());
        }
    };

    /**
     * @param activity
     * @param taskType
     */
    private void verifyMultiInstanceParallel(Activity activity,
            TaskType taskType) {

        if (TaskType.RECEIVE_LITERAL.equals(taskType)) {
            Loop loop = activity.getLoop();
            if (null != loop
                    && LoopType.MULTI_INSTANCE_LITERAL.equals(loop
                            .getLoopType())) {
                LoopMultiInstance loopMultiInstance =
                        loop.getLoopMultiInstance();
                if (null != loopMultiInstance
                        && MIOrderingType.PARALLEL_LITERAL
                                .equals(loopMultiInstance.getMIOrdering())) {
                    // Check for mappings to co-relation data
                    addIssue(RECV_MULTIINSTANCE_CORRELATION_DISALLOWED,
                            activity);
                }
            }
        }
    }
}
