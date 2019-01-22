/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.process.js.parser.util.ScriptParserUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventAdapter.TimerTriggerMode;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * TimerEventRule - validates 1. if a Timer event with a script grammar
 * 'ConstantPeriod' has any value set 2. if java script grammar then checks if
 * it is not empty 3. start timer events are not supported 4. Unspecified and
 * Free Text grammar are not supported
 * 
 * 
 * @author bharge
 * @since 3.3 (1 Mar 2010)
 */
public class TimerEventRule extends ProcessValidationRule {

    private static final String START_EVENT_TIMER_NOT_SUPPORTED =
            "bx.startEventTimerNotSupported"; //$NON-NLS-1$

    private static final String CONSTANTPERIOD_NOT_SET_ISSUE =
            "bx.timerStartEventConstantPeriodNotSet"; //$NON-NLS-1$

    private static final String EMPTY_JAVASCRIPT = "bx.emptyJavaScript"; //$NON-NLS-1$

    private static final String SCRIPT_UNSUPPORTED_ISSUE_ID =
            "bx.scriptUnsupportedTimerEvent"; //$NON-NLS-1$

    private static final String CYCLIC_INTERMEDIATE_EVENT_UNSUPPORTED_ISSUE_ID =
            "bx.cyclicIntermediateTimerEventUnsupported"; //$NON-NLS-1$

    /**
     * Reschedule Timer Script is only supported for timer events on user-task
     * boundary.
     */
    private static final String ISSUE_RESCHEDULE_TIMERSCRIPT_USERTASK_ONLY =
            "bx.rescheduleTimerScriptOnUserTaskOnly"; //$NON-NLS-1$

    /**
     * Reschedule Timer Script must have timeout value specified to be greater
     * than zero micro seconds.
     */
    private static final String ISSUE_RESCHEDULE_TIMERSCRIPT_PERIOD_NOTSET =
            "bx.rescheduleTimerScriptConstantPeriodNotSet"; //$NON-NLS-1$

    /** Unsupported grammar selected for reschedule timer script. */
    private static final String ISSUE_RESCHEDULE_TIMERSCRIPT_UNSUPPORTED_GRAMMAR =
            "bx.rescheduleTimerScriptUnsupportedGrammar"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            Event event = activity.getEvent();
            /* start timer event is currently unsupported */
            if (event instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) event;
                if (TriggerType.TIMER_LITERAL.equals(startEvent.getTrigger())) {
                    addIssue(START_EVENT_TIMER_NOT_SUPPORTED, activity);
                }
            } else if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                if (TriggerType.TIMER_LITERAL.equals(intermediateEvent
                        .getTrigger())) {
                    validateIntermediateTimerEvent(activity, intermediateEvent);
                }
            }

        }
    }

    /**
     * @param activity
     */
    private void validateIntermediateTimerEvent(Activity activity,
            IntermediateEvent intermediateEvent) {
        validateTimerScript(activity,
                getDeadlineExpression(activity),
                SCRIPT_UNSUPPORTED_ISSUE_ID,
                CONSTANTPERIOD_NOT_SET_ISSUE);

        RescheduleTimerScript rescheduleTimerScript =
                EventObjectUtil.getRescheduleTimerScript(activity);

        if (rescheduleTimerScript != null) {
            validateTimerScript(activity,
                    rescheduleTimerScript,
                    ISSUE_RESCHEDULE_TIMERSCRIPT_UNSUPPORTED_GRAMMAR,
                    ISSUE_RESCHEDULE_TIMERSCRIPT_PERIOD_NOTSET);

            /*
             * Validate against use of Reschedule Timer scripts on events not
             * attached to user task boundary.
             */
            Activity taskAttachedTo =
                    EventObjectUtil.getTaskAttachedTo(activity);

            if (taskAttachedTo == null
                    || !TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict(taskAttachedTo))) {
                addIssue(ISSUE_RESCHEDULE_TIMERSCRIPT_USERTASK_ONLY, activity);
            }
        }

        /**
         * XPD:4076 intermediate cyclic event is not supported unless its a
         * boundary event
         */
        if (intermediateEvent.getTarget() == null
                || intermediateEvent.getTarget().isEmpty()) {
            if (EventObjectUtil.getTimerTriggerMode(activity) == TimerTriggerMode.CYCLE) {
                addIssue(CYCLIC_INTERMEDIATE_EVENT_UNSUPPORTED_ISSUE_ID,
                        activity);
            }
        }

    }

    private void validateTimerScript(Activity activity, Expression timerScript,
            String unsupportedGrammarIssue, String emptyPeriodIssue) {

        /* if grammar type is constant period then some value must be set */
        if (null != timerScript
                && EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR
                        .equals(timerScript.getScriptGrammar())) {

            ConstantPeriod constantPeriod = null;

            constantPeriod = getConstantPeriod(activity, timerScript);

            if (constantPeriod == null || !isConstantPeriodSet(constantPeriod)) {
                addIssue(emptyPeriodIssue, activity);
            }

        } else if (null != timerScript
                && ProcessJsConsts.JAVASCRIPT_GRAMMAR.equals(timerScript
                        .getScriptGrammar())) {

            /* if grammar type is java script then it should not be empty */
            String scriptGrammar = timerScript.getScriptGrammar();

            String text = timerScript.getText();

            if (ScriptParserUtil.isEmptyScript(text, scriptGrammar)) {
                addIssue(EMPTY_JAVASCRIPT, activity);
            }

        } else {
            /*
             * we get returned with null deadline expression if the grammar type
             * is not java script or constant period
             */
            addIssue(unsupportedGrammarIssue, activity);
        }

    }

    private boolean isConstantPeriodSet(ConstantPeriod constantPeriod) {
        if (null != constantPeriod) {
            if (constantPeriod.getDays() == null
                    && constantPeriod.getHours() == null
                    && constantPeriod.getMicroSeconds() == null
                    && constantPeriod.getMinutes() == null
                    && constantPeriod.getMonths() == null
                    && constantPeriod.getSeconds() == null
                    && constantPeriod.getWeeks() == null
                    && constantPeriod.getYears() == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the special extension constant period element under deadline duration
     * element.
     * 
     * @return
     */
    private ConstantPeriod getConstantPeriod(Activity activity,
            Expression deadlineDuration) {

        if (deadlineDuration != null) {
            Object cp =
                    deadlineDuration
                            .getMixed()
                            .get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(),
                                    false);
            if (cp instanceof List) {
                List list = (List) cp;
                if (list.size() > 0) {
                    cp = list.get(0);
                }
            }
            if (cp instanceof ConstantPeriod) {
                return (ConstantPeriod) cp;
            }
        }
        return null;

    }

    /**
     * Get the deadline element for activity.
     * 
     * @return
     */
    private Expression getDeadlineExpression(Activity activity) {
        EList list = activity.getDeadline();
        if (list == null || list.size() == 0) {
            return null;
        }

        Deadline deadline = (Deadline) list.get(0);

        Expression deadlineDuration = deadline.getDeadlineDuration();
        if (deadlineDuration == null) {
            return null;
        }

        /*
         * if the script grammar is not constant period or java script then
         * return null for deadline expression
         */
        if (!EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR
                .equals(deadlineDuration.getScriptGrammar())
                && !ProcessJsConsts.JAVASCRIPT_GRAMMAR.equals(deadlineDuration
                        .getScriptGrammar())) {
            return null;
        }

        return deadlineDuration;
    }

}
