/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Collection;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This rule lists the activities that are not supported in a Service Process
 * 
 * <p>
 * The following activities are not supported
 * 
 * <li>User Task</li>
 * 
 * <li>Inbound Message Events and Receive Tasks (i.e. Incoming Request
 * Activities)</li>
 * 
 * <li>Manual Adhoc Activity</li>
 * 
 * <li>Non boundary timers</li>
 * 
 * <li>Global signal catch</li>
 * 
 * <li>Global Data signal catch</li>
 * 
 * </p>
 * 
 * @author bharge
 * @since 12 Feb 2015
 */
public class UnsupportedActivitiesInServiceProcessRule extends
        ProcessValidationRule {

    /**
     * User Task activities are not supported in Service Processes.
     */
    private static final String UNSUPPORTED_USERTASK_ACTIVITY_TYPE_ISSUE_ID =
            "bx.unsupportedUserTaskActivityTypeInServiceProcess"; //$NON-NLS-1$

    /**
     * Correlating activities are not supported in Service Processes.
     */
    private static final String UNSUPPORTED_CORRELATING_ACTIVITY_TYPE_ISSUE_ID =
            "bx.unsupportedCorrelatingActivityTypeInServiceProcess"; //$NON-NLS-1$

    /**
     * Manual Ad hoc activities are not supported in Service Processes.
     */
    private static final String UNSUPPORTED_MANUAL_ADHOC_ACTIVITY_TYPE_ISSUE_ID =
            "bx.unsupportedManualAdhocActivityTypeInServiceProcess"; //$NON-NLS-1$

    /**
     * Sid ACE-2947 - Expanded restriction to task-boundary timer events as well as just in-flow ones..
     * 
     * Timer events are not supported in Service Processes.
     */
    private static final String UNSUPPORTED_TIMER_EVENTS_TYPE_ISSUE_ID =
            "bx.unsupportedTimerEventInServiceProcess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (Xpdl2ModelUtil.isServiceProcess(process)) {

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            for (Activity activity : activities) {

                TaskType taskTypeStrict =
                        TaskObjectUtil.getTaskTypeStrict(activity);
                /* User Tasks are not supported */
                if (TaskType.USER_LITERAL.equals(taskTypeStrict)) {

                    addIssue(UNSUPPORTED_USERTASK_ACTIVITY_TYPE_ISSUE_ID,
                            activity);

                }
                /*
                 * Correlating request activities (i.e. message start event,
                 * catch intermediate message event, receive tasks) are not
                 * supported
                 */
                if (Xpdl2ModelUtil.isCorrelatingActivity(activity)) {
                    addIssue(UNSUPPORTED_CORRELATING_ACTIVITY_TYPE_ISSUE_ID,
                            activity);
                }

                /* Manual Adhoc Activities are not supported */
                Object adHocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adHocConfig instanceof AdHocTaskConfigurationType) {

                    AdHocTaskConfigurationType adhocConfigurationType =
                            (AdHocTaskConfigurationType) adHocConfig;
                    AdHocExecutionTypeType adHocExecutionType =
                            adhocConfigurationType.getAdHocExecutionType();

                    if (AdHocExecutionTypeType.MANUAL
                            .equals(adHocExecutionType)) {

                        addIssue(UNSUPPORTED_MANUAL_ADHOC_ACTIVITY_TYPE_ISSUE_ID,
                                activity);
                    }
                }
                /* Non boundary timers are not supported */
                EventTriggerType eventType =
                        EventObjectUtil.getEventTriggerType(activity);
                if (EventTriggerType.EVENT_TIMER_LITERAL.equals(eventType)) {
                    /*
                     * Sid ACE-2947 - Expanded restriction to task-boundary timer events as well as just in-flow ones..
                     */
                    addIssue(UNSUPPORTED_TIMER_EVENTS_TYPE_ISSUE_ID, activity);
                }
            }
        }
    }
}
