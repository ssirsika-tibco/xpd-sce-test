/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.Collection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Event subprocess start event validation rules.
 * <p>
 * 1. Only catch-message / catch-signal event sub-processes are supported in
 * Business Processes.
 * <p>
 * 2. Only catch-signal / type-none event sub-processes are supported in
 * Pageflows/Business services/Case actions.
 * <p>
 * 3. Interrupting Event Sub-processes are not supported in Pageflows, Business
 * Services and Case Actions.
 * <p>
 * Note: XPD-6909: Saket: We validate against this for pageflows because it can
 * cause deadlocks between throw-interrupting signal on a parallel path to a
 * web-service call that is in progress and waiting for reply (ask PE team for
 * more details)
 * <p>
 * 4. A catch signal start event in an event sub-process must have a unique
 * signal name within whole process scope.
 * <p>
 * 
 * @author sajain
 * @since Aug 14, 2014
 */
public class EventSubProcessStartEventRule extends
        ProcessActivitiesValidationRule {

    /**
     * Only catch-message / catch-signal event sub-processes are supported in
     * Business Processes.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_BUSINESS_PROC =
            "bx.eventSubProcStartEventTriggerTypeRestrictionInBusinessProc"; //$NON-NLS-1$

    /**
     * Only catch-signal / type-none event sub-processes are supported in
     * Pageflows.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_PAGEFLOW =
            "bx.eventSubProcStartEventTriggerTypeRestrictionInPageflow"; //$NON-NLS-1$

    /**
     * Only catch-signal / type-none event sub-processes are supported in
     * Business Services.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_BUSINESS_SERVICE =
            "bx.eventSubProcStartEventTriggerTypeRestrictionInBusinessService"; //$NON-NLS-1$

    /**
     * Only catch-signal / type-none event sub-processes are supported in Case
     * Actions.
     */
    private static final String ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_CASE_ACTION =
            "bx.eventSubProcStartEventTriggerTypeRestrictionInCaseAction"; //$NON-NLS-1$

    /**
     * Interrupting Event Sub-processes are not supported in Pageflows, Business
     * Services and Case Actions.
     * <p>
     * Note: XPD-6909: Saket: We validate against this for pageflows because it
     * can cause deadlocks between throw-interrupting signal on a parallel path
     * to a web-service call that is in progress and waiting for reply (ask PE
     * team for more details)
     */
    private static final String ISSUE_INTERRUPTING_ESP_NOT_SUPPORTED_IN_PAGEFLOW =
            "bx.interruptingEventSubProcessNotSupportedInPageflow"; //$NON-NLS-1$

    /**
     * A catch signal start event in an event sub-process must have a unique
     * signal name within whole process scope.
     */
    private static final String ISSUE_ESP_SIGNAL_START_EVENT_SIGNAL_NAME_MUST_BE_UNIQUE =
            "bx.eventSubProcessStartEventSignalNameMustBeUnique"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {

        /*
         * Check if the activity is a start event activity and is contained
         * inside an event sub process.
         */
        if (EventObjectUtil.isEventSubProcessStartEvent(activity)) {

            /*
             * Validate catch-signal start event in an event sub-process must
             * have a unique-signal-name within whole process scope.
             */
            /*
             * XPD-7075: Show this validation only for Local Signal Events.
             */
            if (EventObjectUtil.isLocalSignalEvent(activity)
                    && !isSignalNameUniqueInProcessScope(activity)) {

                addIssue(ISSUE_ESP_SIGNAL_START_EVENT_SIGNAL_NAME_MUST_BE_UNIQUE,
                        activity);
            }

            /*
             * Check if the process wherein the activity is placed is a business
             * process.
             */
            Process process = activity.getProcess();
            if (Xpdl2ModelUtil.isBusinessProcess(process)
                    || ProcessInterfaceUtil
                            .isProcessEngineServiceProcess(process)) {

                /*
                 * Check if the specified activity is NOT a message catch or
                 * signal catch triggered event.
                 */
                if (!isMessageCatchOrSignalCatchEvent(activity)) {

                    /*
                     * If it is not, then add an issue.
                     */
                    addIssue(ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_BUSINESS_PROC,
                            activity);
                }
            }
            /*
             * Check if the process wherein the activity is placed is a
             * pageflow.
             */
            else if (Xpdl2ModelUtil.isPageflow(process)
                    || ProcessInterfaceUtil
                            .isPageflowEngineServiceProcess(process)) {

                /*
                 * Check if the specified activity is NOT a message catch or
                 * signal catch triggered event.
                 */
                if (!isTypeNoneOrSignalCatchEvent(activity)) {

                    /*
                     * If it is not, then add an issue.
                     */

                    if (Xpdl2ModelUtil.isCaseService(process)) {

                        addIssue(ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_CASE_ACTION,
                                activity);

                    } else if (Xpdl2ModelUtil
                            .isPageflowBusinessService(process)) {

                        addIssue(ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_BUSINESS_SERVICE,
                                activity);

                    } else {

                        addIssue(ISSUE_EVENT_SUB_PROCESS_START_EVENT_TRIGGER_TYPE_PAGEFLOW,
                                activity);

                    }
                }

                /*
                 * Check if the specified activity is an interrupting event
                 * sub-process start event.
                 */
                if (!EventObjectUtil
                        .isNonInterruptingEventSubProcessStartEvent(activity)) {

                    /*
                     * If it is, then add an issue.
                     */
                    addIssue(ISSUE_INTERRUPTING_ESP_NOT_SUPPORTED_IN_PAGEFLOW,
                            activity);

                }
            }
        }
    }

    /**
     * Return <code>true</code> if the signal name of the specified event
     * sub-process start event is unique in the entire scope of it's process,
     * <code>false</code> otherwise.
     * 
     * @param activity
     *            Event sub-process start event activity.
     * 
     * @return <code>true</code> if the signal name of the specified event
     *         sub-process start event is unique in the entire scope of it's
     *         process, <code>false</code> otherwise.
     */
    private boolean isSignalNameUniqueInProcessScope(Activity activity) {

        /*
         * Check if it's a catch signal event.
         */
        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(activity))) {

            /*
             * Get signal name.
             */
            String signalName = EventObjectUtil.getSignalName(activity);

            /*
             * Get all activities in the process.
             */
            Collection<Activity> activities =
                    Xpdl2ModelUtil
                            .getAllActivitiesInProc(activity.getProcess());

            /*
             * Go through all activities.
             */
            for (Activity eachActivity : activities) {

                /*
                 * Check if there's any other catch signal event.
                 */
                if (eachActivity != activity
                        && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(eachActivity))) {

                    /*
                     * Check if it's signal name is same as that of the
                     * currently being processed catch signal start event of an
                     * event sub-process.
                     */
                    if (signalName.equals(EventObjectUtil
                            .getSignalName(eachActivity))) {

                        /*
                         * Return false is it is so.
                         */
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Return <code>true</code> if the specified activity is a message catch or
     * signal catch triggered event, <code>false</code> otherwise.
     * 
     * @param act
     * @return <code>true</code> if the specified activity is a message catch or
     *         signal catch triggered event, <code>false</code> otherwise.
     */
    private boolean isMessageCatchOrSignalCatchEvent(Activity act) {

        return EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                .equals(EventObjectUtil.getEventTriggerType(act))
                || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(act));
    }

    /**
     * Return <code>true</code> if the specified activity is a type none or
     * signal catch triggered event, <code>false</code> otherwise.
     * 
     * @param act
     * @return <code>true</code> if the specified activity is a message catch or
     *         signal catch triggered event, <code>false</code> otherwise.
     */
    private boolean isTypeNoneOrSignalCatchEvent(Activity act) {

        return EventTriggerType.EVENT_NONE_LITERAL.equals(EventObjectUtil
                .getEventTriggerType(act))
                || EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(act));
    }
}
