/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * validation rule complaining activities that are not supported at runtime
 * 
 * Activities that are currently not supported are: (as listed under XPD-1208)
 * Conditional Start Event • Multiple Start Event • Signal Start Event Catch
 * Conditional Intermediate Event • Catch Multiple Intermediate Event • Catch
 * Cancel Intermediate Event • Throw Multiple Intermediate Event • Multiple End
 * Event • Cancel End Event • Event-handlers in Embedded Sub-Processes
 * (XPD-5174)
 * 
 * 
 * @author bharge
 * 
 */
public class UnSupportedActivitiesRule extends ProcessValidationRule {

    private static final String UNSUPPORTED_CONDITIONAL_START_EVENT =
            "bx.unSupportedConditionalStartEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_MULTIPLE_START_EVENT_ISSUE_ID =
            "bx.unSupportedMultipleStartEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_SIGNAL_START_EVENT =
            "bx.unSupportedSignalStartEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_CONDITIONAL_INTERMEDIATE_EVENT =
            "bx.unSupportedConditionalIntermediateEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_CATCH_MULTIPLE_INTERMEDIATE_EVENT =
            "bx.unSupportedCatchMultipleIntermediateEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_THROW_MULTIPLE_INTERMEDIATE_EVENT =
            "bx.unSupportedThrowMultipleIntermediateEvent"; //$NON-NLS-1$

    private static final String UNSUPPORTED_MULTIPLE_END_EVENT =
            "bx.unSupportedMultipleEndEvent"; //$NON-NLS-1$

    /*
     * Sid ACE-476 - Already a rule to cover 'Cancel event no tsupported'
     * elsewhere
     * ("Cancel End event can only be used within Transaction enabled Embedded Sub-Process"
     * )
     */


    private static final String ISSUE_EVENT_HANDLER_TYPE_NOT_SUPPORTED_IN_EMBEDDED_SUB_PROC =
            "bx.eventHandlerTypeNotSupportedInEmbeddedSubProc"; //$NON-NLS-1$

    private static final String UNSUPPORTED_EVENT_HANDLER =
            "bx.eventHandlersMustBeCatchMessageOrSignal"; //$NON-NLS-1$

    private static final String UNSUPPORTED_IMPL_EVENT_HANDLER =
            "bx.implEventHandlersMustBeCatchMessageOrSignal";//$NON-NLS-1$

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
        for (Activity activity : activities) {
            Event event = activity.getEvent();

            /**
             * Conditional Start Event • Multiple Start Event • Signal Start
             * Event
             */

            if (event instanceof StartEvent) {
                if (EventTriggerType.EVENT_CONDITIONAL_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_CONDITIONAL_START_EVENT, activity);
                }
                if (EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_MULTIPLE_START_EVENT_ISSUE_ID,
                            activity);
                }
                /*
                 * ABPM-911: Saket: Should ignore Event sub-process start events
                 * here.
                 */
                if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))
                        && !EventObjectUtil
                                .isEventSubProcessStartEvent(activity)) {
                    addIssue(UNSUPPORTED_SIGNAL_START_EVENT, activity);
                }
            }

            /**
             * Catch Conditional Intermediate Event • Catch Multiple
             * Intermediate Event • Catch Cancel Intermediate Event • Throw
             * Multiple Intermediate Event •
             */

            if (event instanceof IntermediateEvent) {
                if (EventTriggerType.EVENT_CONDITIONAL_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_CONDITIONAL_INTERMEDIATE_EVENT,
                            activity);
                }
                if (EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_CATCH_MULTIPLE_INTERMEDIATE_EVENT,
                            activity);
                }
                if (EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_THROW_MULTIPLE_INTERMEDIATE_EVENT,
                            activity);
                }

                if (Xpdl2ModelUtil.isBusinessProcess(process)) {
                    if (Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                        EventTriggerType eventTriggerType =
                                EventObjectUtil.getEventTriggerType(activity);
                        if (activity.eContainer() instanceof ActivitySet
                                && !EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                        .equals(eventTriggerType)) {
                            /**
                             * Only catch-signal event-handlers are supported in
                             * Embedded sub-processes (XPD-5548).
                             */
                            addIssue(ISSUE_EVENT_HANDLER_TYPE_NOT_SUPPORTED_IN_EMBEDDED_SUB_PROC,
                                    activity);
                        } else if (!EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                .equals(eventTriggerType)
                                && !EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                                        .equals(eventTriggerType)
                                && !EventTriggerType.EVENT_CANCEL_LITERAL
                                        .equals(eventTriggerType)) {

                            Object implementsObj =
                                    Xpdl2ModelUtil
                                            .getOtherAttribute(activity,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Implements());

                            /**
                             * Only catch-message , catch-signal and
                             * catch-cancel type event-handlers are supported in
                             * a business process
                             */

                            /*
                             * XPD-7021: Saket: Need to raise a different
                             * problem marker with same text if the event here
                             * is implemented from a process interface because
                             * if that's the case, then we should not show the
                             * quick fixes that we show for normal
                             * (non-implemented) events.
                             */
                            if (implementsObj != null) {

                                addIssue(UNSUPPORTED_IMPL_EVENT_HANDLER,
                                        activity);

                            } else {

                                addIssue(UNSUPPORTED_EVENT_HANDLER, activity);
                            }
                        }
                    }
                }
            }

            /**
             * • Multiple End Event • Cancel End Event
             * */
            if (event instanceof EndEvent) {
                if (EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(activity))) {
                    addIssue(UNSUPPORTED_MULTIPLE_END_EVENT, activity);
                }

            }

            /**
             * For Reference Task there is a separate ReferenceTaskRule
             * */
        }
    }

}
