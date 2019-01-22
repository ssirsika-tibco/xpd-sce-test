/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.bx.validation.rules.mapping.CatchSignalEventMappingRule;
import com.tibco.bx.validation.rules.scripts.BxJsCatchSignalMappingScriptRule;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rules for Signal events other than mapping rules (that are handled by
 * {@link CatchSignalEventMappingRule}) and mapping script rules (that are
 * handled by {@link BxJsCatchSignalMappingScriptRule}).
 * 
 * @author aallway
 * @since 15 May 2012
 */
public class SignalEventRule extends ProcessActivitiesValidationRule {

    private static final String ISSUE_NON_CANCELLING_SIGNAL_NOT_ON_USERTASK =
            "bx.nonCancellingSignalMustbeOnUserTask"; //$NON-NLS-1$

    private static final String ISSUE_NON_CANCELLING_SIGNAL_OUTGOING =
            "bx.cannotProcessActivitiesFromNonCancellingSignal"; //$NON-NLS-1$

    private static final String ISSUE_NON_CANCELLING_SIGNAL_ON_PAGEFLOW =
            "bx.nonCancellingSignalNotSupportOnPageflow"; //$NON-NLS-1$

    private static final String ISSUE_NON_CANCELLING_SIGNAL_ON_BIZSVC =
            "bx.nonCancellingSignalNotSupportOnBizSvc"; //$NON-NLS-1$

    /**
     * Moved from now-removed MustCatchSpecificSignalRule (which was raise error
     * on throw signals too so needed fixing anyway, so moved here to the
     * general signal event validation rule)
     */
    private static final String ISSUE_MUST_CATCH_SPECIFIC_SIGNAL =
            "bx.mustCatchSpecificSignal"; //$NON-NLS-1$

    /** Rescheduling timer events is only supported on user-task boundary. */
    private static final String ISSUE_RESCHEDULE_TIMERS_USERTASK_ONLY =
            "bx.rescheduleTimersOnUserTaskOnly"; //$NON-NLS-1$

    private static final String ISSUE_EVENT_HANDLER_SIGNAL_NAME_MUST_BE_UNIQUE =
            "bx.eventHandlerSignalNameMustBeUnique"; //$NON-NLS-1$    

    private static final String ISSUE_THROW_SIGNAL_EVENT_CANT_INVOKE_SIGNALS_IN_EMBEDDED_SUBPROC =
            "bx.throwSignalEventCantInvokeSignalsInEmbeddedSubProc"; //$NON-NLS-1$ 

    private static final String ISSUE_THROW_SIGNAL_CANT_START_PARENT_EVENT_SUBPROCESS =
            "bx.throwSignalCannotStartAncestorEventSubProcess"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     */
    @Override
    protected void validate(Activity activity) {
        EventTriggerType eventTriggerType =
                EventObjectUtil.getEventTriggerType(activity);

        boolean isLocalSignalEvent =
                EventObjectUtil.isLocalSignalEvent(activity);

        if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                .equals(eventTriggerType)) {

            if (isLocalSignalEvent) {

                validateLocalCatchSignal(activity);
            }
        }
    }

    /**
     * Validate activities that are catch signal events.
     * 
     * @param catchSignalActivity
     */
    private void validateLocalCatchSignal(Activity catchSignalActivity) {
        String signalName = EventObjectUtil.getSignalName(catchSignalActivity);

        Process process = catchSignalActivity.getProcess();
        if (signalName == null || signalName.length() == 0) {
            addIssue(ISSUE_MUST_CATCH_SPECIFIC_SIGNAL, catchSignalActivity);

        } else if (EventObjectUtil
                .isEventSubProcessStartEvent(catchSignalActivity)) {
            /*
             * Validate against a signal event sub-process being triggered by
             * ANY descendent signal throw (else it could go around forever!
             */
            if (catchSignalActivity.getFlowContainer() instanceof ActivitySet) {
                ActivitySet activitySet =
                        (ActivitySet) catchSignalActivity.getFlowContainer();

                Activity embSubProcActivityForActSet =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(process,
                                activitySet.getId());

                if (embSubProcActivityForActSet != null) {
                    Collection<Activity> allDescendentActivities =
                            Xpdl2ModelUtil
                                    .getAllActivitiesInEmbeddedSubProc(embSubProcActivityForActSet);

                    for (Activity descendentActivity : allDescendentActivities) {
                        if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(descendentActivity))) {

                            if (signalName.equals(EventObjectUtil
                                    .getSignalName(descendentActivity))) {
                                /*
                                 * Signal thrower starts the signal event
                                 * sub-process it was started from!
                                 */
                                addIssue(ISSUE_THROW_SIGNAL_CANT_START_PARENT_EVENT_SUBPROCESS,
                                        descendentActivity);
                            }
                        }
                    }
                }
            }

        } else if (Xpdl2ModelUtil.isEventHandlerActivity(catchSignalActivity)) {
            /*
             * Validate catch-signal event-handler must have a
             * unique-signal-name within whole process scope
             */
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            boolean duplicateFound = false;

            for (Activity activity : activities) {
                if (activity != catchSignalActivity
                        && EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))) {

                    if (signalName.equals(EventObjectUtil
                            .getSignalName(activity))) {
                        addIssue(ISSUE_EVENT_HANDLER_SIGNAL_NAME_MUST_BE_UNIQUE,
                                catchSignalActivity);
                        duplicateFound = true;
                        break;
                    }
                }
            }

            /*
             * Valdiate that catch signal in embedded sub-proc is not thrown
             * from outside of the embedded sub-process. The only valid throwers
             * for a given catch are in same scope or child embedded
             * sub-processes.
             * 
             * So if catch signal is at process level ALL throwers are in it's
             * scope. So only need to check catches in embedded sub-processes.
             */
            if (!duplicateFound
                    && catchSignalActivity.eContainer() instanceof ActivitySet) {
                Activity embSubprocActivity =
                        Xpdl2ModelUtil
                                .getEmbSubProcActivityForActSet(process,
                                        ((ActivitySet) catchSignalActivity
                                                .eContainer()).getId());

                if (embSubprocActivity != null) {
                    /*
                     * Get a small set of signal throwers that ARE in valid
                     * scope of this signal.
                     */
                    Set<Activity> validThrowSignalsInScopeOfCatch =
                            new HashSet<Activity>();

                    Collection<Activity> allActivitiesInValidScope =
                            Xpdl2ModelUtil
                                    .getAllActivitiesInEmbeddedSubProc(embSubprocActivity);

                    for (Activity actInValidScope : allActivitiesInValidScope) {
                        if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(actInValidScope))
                                && signalName.equals(EventObjectUtil
                                        .getSignalName(actInValidScope))) {
                            validThrowSignalsInScopeOfCatch
                                    .add(actInValidScope);
                        }
                    }

                    /*
                     * Then look for any throw signal for this signal that isn't
                     * one of the ones in the valid scope.
                     */
                    for (Activity activity : activities) {
                        if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                                .equals(EventObjectUtil
                                        .getEventTriggerType(activity))
                                && signalName.equals(EventObjectUtil
                                        .getSignalName(activity))) {

                            if (!validThrowSignalsInScopeOfCatch
                                    .contains(activity)) {
                                addIssue(ISSUE_THROW_SIGNAL_EVENT_CANT_INVOKE_SIGNALS_IN_EMBEDDED_SUBPROC,
                                        activity);
                            }
                        }
                    }
                }
            }

        }

        if (EventObjectUtil.isNonCancellingEvent(catchSignalActivity)) {
            /*
             * Non-cancelling signals are not supported in pageflow/business
             * service processes.
             */
            if (Xpdl2ModelUtil.isPageflow(process)
                    || ProcessInterfaceUtil
                            .isPageflowEngineServiceProcess(process)) {

                addIssue(ISSUE_NON_CANCELLING_SIGNAL_ON_PAGEFLOW,
                        catchSignalActivity);

            } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {

                addIssue(ISSUE_NON_CANCELLING_SIGNAL_ON_BIZSVC,
                        catchSignalActivity);

            } else {
                /*
                 * Non-cancelling catch signal events are only supported on user
                 * task boundary.
                 */

                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(catchSignalActivity);

                if (taskAttachedTo == null
                        || !TaskType.USER_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(taskAttachedTo))) {
                    addIssue(ISSUE_NON_CANCELLING_SIGNAL_NOT_ON_USERTASK,
                            catchSignalActivity);
                }

                /*
                 * Outgoing flow is not supported on non-cacenlling signal
                 * except for end-event type none.
                 */
                if (catchSignalActivity.getFlowContainer() != null) {
                    FlowContainer flowContainer =
                            catchSignalActivity.getFlowContainer();

                    EList<Transition> outgoingTransitions =
                            catchSignalActivity.getOutgoingTransitions();

                    for (Transition transition : outgoingTransitions) {
                        Activity targetActivity =
                                flowContainer.getActivity(transition.getTo());

                        if (targetActivity != null) {
                            if (!(targetActivity.getEvent() instanceof EndEvent)
                                    || !EventTriggerType.EVENT_NONE_LITERAL
                                            .equals(EventObjectUtil
                                                    .getEventTriggerType(targetActivity))) {
                                addIssue(ISSUE_NON_CANCELLING_SIGNAL_OUTGOING,
                                        catchSignalActivity);
                            }
                        }
                    }
                }
            }
        }

        /*
         * Validate against setting to reschedule timer events except on user
         * task boundary.
         */
        SignalData signalData =
                EventObjectUtil
                        .getSignalDataExtensionElement(catchSignalActivity);

        if (signalData != null) {
            if (signalData.getRescheduleTimers() != null) {

                Activity taskAttachedTo =
                        EventObjectUtil.getTaskAttachedTo(catchSignalActivity);

                if (taskAttachedTo == null
                        || !TaskType.USER_LITERAL.equals(TaskObjectUtil
                                .getTaskTypeStrict(taskAttachedTo))) {
                    addIssue(ISSUE_RESCHEDULE_TIMERS_USERTASK_ONLY,
                            catchSignalActivity);
                }
            }
        }
    }
}
