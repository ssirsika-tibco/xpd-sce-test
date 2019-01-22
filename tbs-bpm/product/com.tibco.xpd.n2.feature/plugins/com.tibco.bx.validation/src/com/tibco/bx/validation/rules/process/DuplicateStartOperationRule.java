/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule ensures that there can only be one process start request activity
 * for a particular WSDL operation (XPD-4332).
 * 
 * 
 * @author agondal
 * @since 14 Jan 2013
 */
public class DuplicateStartOperationRule extends ProcessValidationRule {

    private static final String ISSUE_DUPLIACTE_START_OPERATION =
            "bx.duplicateStartOperation"; //$NON-NLS-1$

    @Override
    public void validate(Process process) {

        /*
         * Check that all the start activities have unique port-type operation
         */

        if (process != null) {

            List<Activity> startActivities = new ArrayList<Activity>();

            /*
             * Add to startActivities if it is a start activity, receive-task
             * with no-inflow or a receive-task/catch message event connected
             * from event-based gateway.
             */

            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {

                if (isStartRequestActivity(activity)) {

                    startActivities.add(activity);
                }
            }

            if (!startActivities.isEmpty()) {

                /*
                 * Get the indexed list of references to web services from
                 * activities in this process.
                 */
                List<ActivityWebServiceReference> webSvcRefs =
                        getIndexedWebServiceReferences(process, true);

                for (Activity activity : startActivities) {

                    checkDuplicateOperation(activity, webSvcRefs);

                }

            }

        }

    }

    /**
     * Check that if the port-type operation for the given activity duplicates
     * another activity's operation
     * 
     * @param activity
     * @param webSvcRefs
     */
    private void checkDuplicateOperation(Activity activity,
            List<ActivityWebServiceReference> webSvcRefs) {
        /*
         * Create a web service reference for the activity so we know we're
         * comparing on an equitable basis
         */
        ActivityWebServiceReference activityWebSvcRef =
                ActivityWebServiceReference
                        .createActivityWebServiceReference(activity);

        if (activityWebSvcRef != null) {

            String portTypeNamespace = activityWebSvcRef.getPortTypeNamespace();
            String portTypeName = activityWebSvcRef.getPortTypeName();
            String portTypeOperation = activityWebSvcRef.getOperation();

            /*
             * Look for other request activities with same port-type namespace,
             * name and operation
             */
            for (ActivityWebServiceReference otherRef : webSvcRefs) {

                if (portTypeNamespace.equals(otherRef.getPortTypeNamespace())
                        && portTypeName.equals(otherRef.getPortTypeName())
                        && portTypeOperationsMatch(portTypeOperation, otherRef)) {

                    Activity otherActivity = otherRef.getActivity();

                    /*
                     * check both activities are not same and are request
                     * activities.
                     */
                    /*
                     * XPD-8105: If any of activity is a start events in an
                     * event sub-process then validation for duplicate start
                     * event should not be thrown.
                     */
                    if (otherActivity != null
                            && !activity.equals(otherActivity)
                            && ReplyActivityUtil
                                    .isIncomingRequestActivity(otherActivity)
                            && !Xpdl2ModelUtil
                                    .isEventSubProcessStartEvent(activity)
                            && !Xpdl2ModelUtil
                                    .isEventSubProcessStartEvent(otherActivity)) {

                        /*
                         * two different activities reference same port type
                         * operation
                         */

                        List<String> messages = new ArrayList<String>();

                        messages.add(activity.getName());

                        if (activity.getProcess()
                                .equals(otherActivity.getProcess())) {

                            messages.add(otherActivity.getName());

                        } else {

                            messages.add(otherActivity.getProcess()
                                    .getPackage().getName()
                                    + "/" //$NON-NLS-1$
                                    + otherActivity.getProcess().getName()
                                    + "/" + otherActivity.getName()); //$NON-NLS-1$

                        }

                        messages.add(portTypeOperation);

                        addIssue(ISSUE_DUPLIACTE_START_OPERATION,
                                activity.getProcess(),
                                messages);
                    }
                }
            }
        }

        return;
    }

    /**
     * Return true if the given operations are same
     * 
     * @param portTypeOperation
     * @param otherRef
     * @return
     */
    private boolean portTypeOperationsMatch(String portTypeOperation,
            ActivityWebServiceReference otherRef) {

        if (null != portTypeOperation && null != otherRef.getOperation()) {

            if (portTypeOperation.equals(otherRef.getOperation())) {

                return true;
            }
        }
        return false;
    }

    /**
     * Return true if its a start activity, receive-task with no-inflow or a
     * receive-task/catch message event connected from event-based gateway
     * 
     * @param activity
     * @return
     */
    private boolean isStartRequestActivity(Activity activity) {

        Event event = activity.getEvent();

        if (event != null
                && event.getEventTriggerTypeNode() instanceof TriggerResultMessage) {

            /*
             * XPD-7539 Message Events can be REST sdervce as well as
             * WebService; only want to deal with WebService
             */
            if (EventObjectUtil.isWebServiceRelatedEvent(activity)) {

                TriggerResultMessage trm =
                        (TriggerResultMessage) event.getEventTriggerTypeNode();

                // It's a message event.
                if (event instanceof StartEvent) {
                    // its a start activity
                    return true;

                } else if (event instanceof IntermediateEvent) {
                    /*
                     * Intermediate ,message events can be catch or throw - we
                     * only want catch connected from event-based gateway
                     */

                    if (!CatchThrow.THROW.equals(trm.getCatchThrow())) {

                        if (isConnectedFromEventBasedGateway(activity)) {

                            return true;
                        }
                    }
                }
            }

        } else if (activity.getImplementation() instanceof Task) {

            Task task = (Task) activity.getImplementation();

            if (task.getTaskReceive() != null) {

                // Receive task with no in-flow
                if (activity.getIncomingTransitions().isEmpty()) {
                    return true;
                }

                // Receive task can also be linked from an event based gateway.
                if (isConnectedFromEventBasedGateway(activity)) {

                    return true;

                }
            }
        }

        return false;
    }

    /**
     * Returns true if its connected form an event-based gateway
     * 
     * @param activity
     * @return
     */
    private boolean isConnectedFromEventBasedGateway(Activity activity) {

        for (Transition transition : activity.getIncomingTransitions()) {

            String from = transition.getFrom();

            if (from != null) {

                Activity fromActivity =
                        activity.getFlowContainer().getActivity(from);

                // the gateway itself must not have in-flow
                if (fromActivity.getIncomingTransitions().isEmpty()) {

                    Route route = fromActivity.getRoute();

                    if (route != null) {

                        if (route.getGatewayType()
                                .equals(JoinSplitType.EXCLUSIVE_LITERAL)
                                && route.getExclusiveType()
                                        .equals(ExclusiveType.EVENT)) {

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param process
     * @param apiActivities
     * 
     * @return List of web service reference info.
     */
    private List<ActivityWebServiceReference> getIndexedWebServiceReferences(
            Process process, Boolean apiActivities) {

        List<ActivityWebServiceReference> webSvcRefs =
                new ArrayList<ActivityWebServiceReference>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes
                .put(ProcessUIUtil.WEBSERVICE_REF_COLUMN_IS_API_ACTIVITY,
                        apiActivities.toString());

        IndexerItem criteria =
                new IndexerItemImpl(null,
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                        additionalAttributes);

        Collection<IndexerItem> result =
                XpdResourcesPlugin
                        .getDefault()
                        .getIndexerService()
                        .query(ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_ID,
                                criteria);

        if (result != null && !result.isEmpty()) {
            for (IndexerItem item : result) {
                ActivityWebServiceReference ref =
                        ActivityWebServiceReference
                                .createActivityWebServiceReference(item);
                if (ref != null) {
                    webSvcRefs.add(ref);
                }
            }
        }

        return webSvcRefs;
    }

}
