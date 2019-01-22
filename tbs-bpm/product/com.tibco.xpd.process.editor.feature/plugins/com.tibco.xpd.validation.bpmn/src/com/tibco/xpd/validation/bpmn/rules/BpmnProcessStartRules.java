/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.GatewayObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.GatewayType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BpmnProcessStartRules
 * 
 * 
 * @author aallway
 * @since 3.3 (23 Nov 2009)
 */
public class BpmnProcessStartRules extends ProcessValidationRule {

    /**
     * Process must have at least one activity.
     */
    private static final String ISSUE_PROCESS_MUST_HAVE_ACTIVITY =
            "bpmn.processMustHaveActivity"; //$NON-NLS-1$

    /**
     * Embedded/Event Sub-Process must have at least one activity.
     */
    private static final String ISSUE_EMBEDDED_OR_EVENT_SUB_PROCESS_MUST_HAVE_ACTIVITY =
            "bpmn.embeddedOrEventSubProcessMustHaveActivity"; //$NON-NLS-1$

    /**
     * Process can only start with a Start Event, Task or Exclusive Event-Based
     * Gateway.
     */
    private static final String ISSUE_BAD_PROCESS_START_ACTIVITY =
            "bpmn.processMustStartWith"; //$NON-NLS-1$

    /**
     * Embedded sub-process can only start with a Start Event, Task or Exclusive
     * Event-Based Gateway.
     */
    private static final String ISSUE_BAD_EMBEDDED_SUBPROCESS_START_ACTIVITY =
            "bpmn.embeddedSubProcessMustStartWith"; //$NON-NLS-1$

    @Override
    /*
     * XPD-4892: Saket: When activity is an embedded sub-process,
     * FlowContainerValidationRules are executed on it because the embedded
     * sub-process is an activity that internally references an ActivitySet and
     * an ActivitySet implements FlowContainer. The problem marker is added to
     * the activity that references the ActivitySet. Now when the activity type
     * gets changed to something else, it’s ActivitySet will be removed but it
     * will still exist. So the marker doesn’t get removed because (a) the
     * ‘host’ of the maker – the activity – still exists and (b) the
     * FlowContainerValidationRules no longer run for it because there is no
     * FlowContainer. Hence we make BpmnProcessStartRules extend
     * ProcessValidationRule (instead of FlowContainerValidationRule) thereby
     * replacing validate(FlowContainer) with validate(Process) and making it
     * call validateFlowContainer(FlowContainer).
     */
    public void validate(Process process) {
        validateFlowContainer(process);
        EList<ActivitySet> activitySets = process.getActivitySets();
        for (ActivitySet currentActivitySet : activitySets) {
            validateFlowContainer(currentActivitySet);
        }
    }

    public void validateFlowContainer(FlowContainer container) {

        EList<Activity> activities = container.getActivities();
        if (!activities.isEmpty()) {
            for (Activity act : activities) {
                if (isStartActivity(act)) {
                    validStartActivity(act);
                }
            }

        } else {
            if (container instanceof ActivitySet) {
                Activity embAct =
                        Xpdl2ModelUtil
                                .getEmbSubProcActivityForActSet(((ActivitySet) container)
                                        .getProcess(),
                                        ((ActivitySet) container).getId());

                addIssue(ISSUE_EMBEDDED_OR_EVENT_SUB_PROCESS_MUST_HAVE_ACTIVITY,
                        embAct != null ? embAct : container);
            } else {
                addIssue(ISSUE_PROCESS_MUST_HAVE_ACTIVITY, container);
            }
        }

        return;
    }

    /**
     * Validate whether the activity (with no incoming and not attachedf to
     * task) is a valid start activity type.
     * 
     * @param act
     */
    private void validStartActivity(Activity act) {
        boolean isValid = false;

        if (act.getEvent() instanceof StartEvent) {
            isValid = true;
        } else if (act.getRoute() != null) {
            if (GatewayType.EXLCUSIVE_EVENT_LITERAL.equals(GatewayObjectUtil
                    .getGatewayType(act))) {
                isValid = true;
            }
        } else if (TaskObjectUtil.getTaskTypeStrict(act) != null) {
            isValid = true;
        } else if (EventTriggerType.EVENT_LINK_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(act))) {
            // XPD-857: If link intermediate catch event - shouldn't complain
            // about it being invalid start of process
            isValid = true;
        } else if (Xpdl2ModelUtil.isEventHandlerActivity(act)) {
            isValid = true;
        }

        if (!isValid) {
            if (act.getFlowContainer() instanceof ActivitySet) {
                addIssue(ISSUE_BAD_EMBEDDED_SUBPROCESS_START_ACTIVITY, act);
            } else {
                addIssue(ISSUE_BAD_PROCESS_START_ACTIVITY, act);
            }
        }

        return;
    }

    /**
     * @param act
     * @return true if activity is a potential start activity (no incoming trans
     *         and not an event attached to task boundary).
     */
    private boolean isStartActivity(Activity act) {
        EList<Transition> incoming = act.getIncomingTransitions();
        if (incoming == null || incoming.size() == 0) {
            if (!EventObjectUtil.isAttachedToTask(act)) {
                return true;
            }
        }

        return false;
    }

}
