/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdExtension.Audit;
import com.tibco.xpd.xpdExtension.AuditEvent;
import com.tibco.xpd.xpdExtension.AuditEventType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Check that the (currently) unsupported activity scripts are not defined.
 * <p>
 * <b>Eventually (when scripts fully implemented in N2 process engine then this
 * rule should be loosened to match the following criteria (from Steve
 * Goldberg)...
 * 
 * <pre>
 * Task Script:
 * A task script is a script associated with a task (or event) as part of the task definition rather than as 
 * separate Script task. There are 4 types of scripts:
 * •   Initiated – Executed just before the task begins execution.
 * •   Completed – Executed just after the task completes normal execution.
 * •   Cancel – Executed in the event task execution is cancelled while in progress.
 * •   Timeout – This is a special case of cancel, executed in the event task execution is cancelled by the timeout 
 *       of a timer event on the boundary of the task.
 * 
 * Script execution
 * Which type of task and event can execute which type of script:
 * •   Task – All 4 types of scripts can appear on all tasks. This provides uniformity for the UI, 
 *       but in reality not all tasks are always capable of executing all script types. 
 *       - When a task is serving as the process start event it can only execute the Completed script.
 *       - Some tasks can not execute a Cancel script as their operation is atomic and therefore can not be 
 *           cancelled while in progress - we can tell only tell this for definite for Script Task, Task Type None and Send Task.
 *       - The timeout script on a task will only be executed if there is a timer event attached to task boundary.
 *            
 * •   Start event - Start events can only execute a Completed script.
 * •   End event – End events can only execute an Initiated script
 * •   Intermediate boundary event – Boundary events, like start events, can only execute a Completed script (Compensation does not execute any script).
 * •   Intermediate In-Flow Catch event – Intermediate catch events in the flow can execute Initiated, Completed and Cancel scripts.
 * •   Intermediate Throw event – Intermediate throw events can execute Initiated and Completed scripts.
 * •   Event handler activity – Supports only the complete script.
 * •   End event after non-cancelling boundary signal event – Does not support any script type.
 * 
 * Loops
 * A task may be configured with looping as well as with task scripts. 
 *   In this case, the task scripts will be executed with each task instance. 
 *   Task scripts in this case will need access to the loop index counter in order to really be useful, 
 *   otherwise each script will end up doing exactly the same thing.
 * 
 * Faults
 * Should a task script throw a fault, it will be charged to the associated task
 * </pre>
 * 
 * @author aallway
 * @since 3.3 (14 Jan 2010)
 */
public class ActivityScriptSupportRule extends FlowContainerValidationRule {

    /*
     * Errors...
     */
    public static final String ISSUE_INITIATE_SCRIPT_NOT_SUPPORTED_ON_BOUNDARYEVENT =
            "bx.activityInitiateScriptNotSupportedOnBoundaryEvent"; //$NON-NLS-1$

    public static final String ISSUE_CANCEL_SCRIPT_NOT_SUPPORTED_ON_BOUNDARYEVENT =
            "bx.activityCancelScriptNotSupportedOnBoundaryEvent"; //$NON-NLS-1$

    public static final String ISSUE_CANCEL_SCRIPT_NOT_SUPPORTED_ON_ATOMIC_ACTIVITY =
            "bx.activityCancelScriptNotSupportedOnAtomicActivity"; //$NON-NLS-1$

    public static final String ISSUE_TIMEOUT_SCRIPT_WITH_NO_ATTACHED_EVENT =
            "bx.timeoutScriptWithNoAttachedEvent"; //$NON-NLS-1$

    private static final String ISSUE_TIMEOUT_SCRIPT_NOT_SUPPORTED_FOR_ADHOC_ACTIVITY =
            "bx.timeoutScriptNotSupportedForAdhocActivities"; //$NON-NLS-1$

    public static final String ISSUE_START_TASK_ONLY_SUPPORTS_COMPLETESCRIPT =
            "bx.startTaskOnlySupportsCompleteScript"; //$NON-NLS-1$

    public static final String ISSUE_EVENT_HANDLERS_ONLY_SUPPORT_COMPLETE =
            "bx.evenHandlersSupporOnlyCompleteScript"; //$NON-NLS-1$

    private static final String ISSUE_NO_SCRIPTS_ON_END_EVENT_FROM_NON_CANCELLING_SIGNAL =
            "bx.noScriptSupportOnEndEventAfterNonCancelSignal"; //$NON-NLS-1$

    @Override
    public void validate(FlowContainer container) {
        for (Activity activity : container.getActivities()) {

            Audit audit =
                    (Audit) Xpdl2ModelUtil.getOtherElement(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Audit());
            if (audit != null) {
                if (!checkLimitedSupportForAScripts(activity, audit)) {
                    checkIndividualScriptSupport(activity, audit);
                }
            }

        } /* Next activity */

        return;
    }

    /**
     * Checks for very limited support of activity scripts (cases where we only
     * want to raise one over-arching issue.
     * 
     * @param activity
     * @param audit
     * 
     * @return <code>true</code> if issue raised.
     */
    public boolean checkLimitedSupportForAScripts(Activity activity, Audit audit) {
        for (AuditEvent auditEvent : audit.getAuditEvent()) {
            if (auditEvent.getInformation() != null) {
                String script = auditEvent.getInformation().getText();

                if (script != null && script.trim().length() > 0) {

                    if (isEndEventFromNonCancellingSignal(activity)) {
                        /*
                         * Activity is an end event connected from a
                         * non-cancelling event are not executed Because on
                         * conversion to runtime BPEL, the end event is ignored.
                         */
                        addIssue(ISSUE_NO_SCRIPTS_ON_END_EVENT_FROM_NON_CANCELLING_SIGNAL,
                                activity);
                        return true;
                    }

                    if (!AuditEventType.COMPLETED_LITERAL.equals(auditEvent
                            .getType())) {
                        if (isMainProcessStarterTask(activity)
                                && !isAdhocActivity(activity)) {
                            /*
                             * Task(EXCEPT FOR ADHOC TASKS, because adhoc tasks
                             * can only be invoked by other task/s, hence it can
                             * have initialize/cancel script) used as a start
                             * activity cannot have any script except Complete.
                             */
                            addIssue(ISSUE_START_TASK_ONLY_SUPPORTS_COMPLETESCRIPT,
                                    activity);
                            return true;

                        } else if (Xpdl2ModelUtil
                                .isEventHandlerActivity(activity)) {
                            /*
                             * Event handler activities only support complete
                             * script
                             */
                            addIssue(ISSUE_EVENT_HANDLERS_ONLY_SUPPORT_COMPLETE,
                                    activity);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the Activity is an Adhoc Activity(i.e. has
     *         Adhoc Task Config) ,otherwise return <code>false</code>
     */
    private boolean isAdhocActivity(Activity activity) {

        return Xpdl2ModelUtil.getOtherElement(activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AdHocTaskConfiguration()) != null;
    }

    /**
     * @param activity
     * 
     * @return <code>true</code> if activity is an end event connected from a
     *         non-cancelling event
     */
    private boolean isEndEventFromNonCancellingSignal(Activity activity) {
        if (activity.getEvent() instanceof EndEvent) {
            EList<Transition> incomingTransitions =
                    activity.getIncomingTransitions();

            for (Transition transition : incomingTransitions) {
                String from = transition.getFrom();
                if (from != null) {
                    Activity fromActivity =
                            activity.getFlowContainer().getActivity(from);

                    if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(fromActivity))
                            && EventObjectUtil.isAttachedToTask(fromActivity)) {
                        if (EventObjectUtil.isNonCancellingEvent(fromActivity)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * CHeck for support of each script type individually for activity.
     * 
     * @param activity
     * @param audit
     */
    public void checkIndividualScriptSupport(Activity activity, Audit audit) {
        for (AuditEvent auditEvent : audit.getAuditEvent()) {
            if (auditEvent.getInformation() != null) {
                String script = auditEvent.getInformation().getText();
                if (script != null && script.trim().length() > 0) {
                    if (AuditEventType.INITIATED_LITERAL.equals(auditEvent
                            .getType())) {
                        checkInitiateScriptSupport(activity);

                    } else if (AuditEventType.COMPLETED_LITERAL
                            .equals(auditEvent.getType())) {
                        checkCompleteScriptSupport(activity);

                    } else if (AuditEventType.DEADLINE_EXPIRED_LITERAL
                            .equals(auditEvent.getType())) {
                        checkTimeoutScriptSupport(activity);

                    } else if (AuditEventType.CANCELLED_LITERAL
                            .equals(auditEvent.getType())) {
                        checkCancelScriptSupport(activity);

                    }

                }
            }
        } /* Next script */
    }

    /**
     * @param activity
     * @return true if activity is a task that may start a process.
     */
    private boolean isMainProcessStarterTask(Activity activity) {
        return activity.eContainer() instanceof Process
                && TaskObjectUtil.getTaskTypeStrict(activity) != null
                && activity.getIncomingTransitions().isEmpty();
    }

    /**
     * @param activity
     */
    private void checkInitiateScriptSupport(Activity activity) {
        /*
         * Initiate script is not supported for task boundary events.
         */
        if (activity.getEvent() instanceof IntermediateEvent) {
            if (Xpdl2ModelUtil.isEventAttachedToTask(activity)) {
                addIssue(ISSUE_INITIATE_SCRIPT_NOT_SUPPORTED_ON_BOUNDARYEVENT,
                        activity);

                /* Don't complain about any other initiate script things */
                return;
            }
        }

        return;
    }

    /**
     * @param activity
     */
    private void checkCancelScriptSupport(Activity activity) {
        /*
         * Cancel script is only supported for non-atomic activities (activities
         * do not pause the flow and hence cannot be cancelled).
         */
        if (activity.getEvent() instanceof IntermediateEvent) {

            if (EventObjectUtil.isAttachedToTask(activity)) {
                addIssue(ISSUE_CANCEL_SCRIPT_NOT_SUPPORTED_ON_BOUNDARYEVENT,
                        activity);

                /* Don't complain about any other cancel script things */
                return;

            } else if (!CatchThrow.CATCH.equals(EventObjectUtil
                    .getCatchThrowType(activity))) {
                addIssue(ISSUE_CANCEL_SCRIPT_NOT_SUPPORTED_ON_ATOMIC_ACTIVITY,
                        activity);

                /* Don't complain about any other cancel script things */
                return;
            }
        } else {
            TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);
            if (TaskType.SEND_LITERAL.equals(taskType)
                    || TaskType.SCRIPT_LITERAL.equals(taskType)
                    || TaskType.NONE_LITERAL.equals(taskType)) {
                addIssue(ISSUE_CANCEL_SCRIPT_NOT_SUPPORTED_ON_ATOMIC_ACTIVITY,
                        activity);

            }
        }

        return;
    }

    /**
     * @param activity
     */
    private void checkTimeoutScriptSupport(Activity activity) {

        if (isAdhocActivity(activity)) {
            /*
             * Timeout Scripts are not supported for Ad-hoc Activiites, return
             * from here itself
             */
            addIssue(ISSUE_TIMEOUT_SCRIPT_NOT_SUPPORTED_FOR_ADHOC_ACTIVITY,
                    activity);
            return;
        }

        /*
         * Only valid on a task that has a timer event attached.
         */
        if (TaskObjectUtil.getTaskTypeStrict(activity) != null) {
            boolean hasTimeoutEvent = false;

            Collection<Activity> attachedEvents =
                    TaskObjectUtil.getAttachedEvents(activity);
            for (Activity eventAct : attachedEvents) {
                if (EventTriggerType.EVENT_TIMER_LITERAL.equals(EventObjectUtil
                        .getEventTriggerType(eventAct))) {
                    hasTimeoutEvent = true;
                    break;
                }
            }

            if (!hasTimeoutEvent) {
                addIssue(ISSUE_TIMEOUT_SCRIPT_WITH_NO_ATTACHED_EVENT, activity);
            }
        }

        return;
    }

    /**
     * @param activity
     */
    private void checkCompleteScriptSupport(Activity activity) {
        /*
         * Currently there are no restrictions on complete script (at least not
         * for activities that the script can be defined for via the UI.
         */
        return;
    }

}
