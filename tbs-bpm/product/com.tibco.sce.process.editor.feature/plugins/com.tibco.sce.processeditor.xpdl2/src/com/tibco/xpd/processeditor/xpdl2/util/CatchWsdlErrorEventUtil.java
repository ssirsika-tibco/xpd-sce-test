/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Helpful utilities for information about wsdl fault caught by catch error
 * event.
 * 
 * @author aprasad
 * @since 28 Aug 2012
 */
public class CatchWsdlErrorEventUtil {

    public static final String TIMEOUT_EXCEPTION = "TimeoutException"; //$NON-NLS-1$

    /**
     * 
     * @param activity
     * @return <code>true</code> if the given activity is a boundary catch event
     *         configured to catch a specific WSDL fault.
     */
    public static boolean isCatchWsdlFaultErrorEvent(Activity activity) {
        if (EventObjectUtil.isAttachedToTask(activity)) {

            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                    .getEventTriggerType(activity))) {

                if (ErrorCatchType.CATCH_SPECIFIC.equals(BpmnCatchableErrorUtil
                        .getCatchType(activity))) {

                    if (getWsdlFaultThrowingActivity(activity) != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Is the given activity of a type that can call out to WSDL operation (And
     * a fault on that operation could be caught by a ctach error event.
     * 
     * @param errorThrower
     * @return true if it is.
     */
    public static boolean isWsdlFaultThrowingActivityType(Activity errorThrower) {
        //
        // We can handle Web Service Send Task, Service Task and Throw Message.
        //
        if (errorThrower.getImplementation() instanceof Task) {
            Task task = (Task) errorThrower.getImplementation();

            if (task.getTaskService() != null) {
                if (ImplementationType.WEB_SERVICE_LITERAL.equals(task
                        .getTaskService().getImplementation())) {
                    return true;
                }
            } else if (task.getTaskSend() != null) {
                if (ImplementationType.WEB_SERVICE_LITERAL.equals(task
                        .getTaskSend().getImplementation())) {
                    return true;
                }
            }

        } else if (errorThrower.getEvent() instanceof IntermediateEvent) {
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(errorThrower))) {
                //
                // ONLY interested in end event if it is effectively a send
                // message
                // (i.e. not a reply to a different error).
                // [Sid XPD-3793: just a tidy up to match the below.]
                //
                if (!ReplyActivityUtil.isReplyActivity(errorThrower)) {
                    return true;
                }
            }

        } else if (errorThrower.getEvent() instanceof EndEvent) {
            //
            // ONLY interested in end event if it is effectively a send message
            // (i.e. not a reply to a different error).
            //
            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(errorThrower))) {
                if (!ReplyActivityUtil.isReplyActivity(errorThrower)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * @param catchErrorEvent
     * 
     * @return Activity that throws the WSDL fault caught by the given catch
     *         error event or <code>null</code> if not found or doesn't throw
     *         wsdl error.
     */
    public static Activity getWsdlFaultThrowingActivity(Activity catchErrorEvent) {
        Object thrower =
                BpmnCatchableErrorUtil.getErrorThrower(catchErrorEvent);
        if (thrower instanceof Activity) {
            if (isWsdlFaultThrowingActivityType((Activity) thrower)) {
                return (Activity) thrower;
            }
        }
        return null;
    }

    /**
     * Returns true if the Task has a SOAP JMS Consumer Participant.
     * 
     * @param errorThrowerTask
     * 
     * @return true if the Task has a SOAP JMS Consumer Participant.
     */
    public static boolean hasSoapJmsConsumerParticipant(
            Activity errorThrowerTask) {

        /* Applicable to only WebService Task */
        boolean applicableWebServiceTask = false;

        if (errorThrowerTask.getImplementation() instanceof Task) {
            Task task = (Task) errorThrowerTask.getImplementation();

            if (task.getTaskService() != null) {

                if (ImplementationType.WEB_SERVICE_LITERAL.equals(task
                        .getTaskService().getImplementation())) {

                    applicableWebServiceTask = true;
                }
            }

        }
        if (applicableWebServiceTask) {

            EObject[] activityPerformers =
                    TaskObjectUtil.getActivityPerformers(errorThrowerTask);

            for (EObject performer : activityPerformers) {

                if (performer instanceof Participant) {

                    Participant participant = (Participant) performer;

                    WsOutbound outbound =
                            SharedResourceUtil
                                    .getWsResourceOutbound(participant);
                    /* Is a Consumer */
                    if (outbound != null) {

                        WsSoapJmsOutboundBinding soapJmsBinding =
                                outbound.getSoapJmsBinding();

                        if (soapJmsBinding != null) {
                            /* Is SOAP JMS Consumer */
                            return true;
                        }
                    }

                }

            }
        }
        return false;
    }

    /**
     * Returns {@link ResultError} for the given Activity if it is an
     * Intermediate Error Event, returns null otherwise.
     * 
     * @param catchErrorEvent
     * @return {@link ResultError} for the given Activity if it is an
     *         Intermediate Error Event, returns null otherwise.
     */
    public static ResultError getCatchEventResultError(Activity catchErrorEvent) {

        if (catchErrorEvent.getEvent() instanceof IntermediateEvent
                && catchErrorEvent.getEvent().getEventTriggerTypeNode() instanceof ResultError) {

            return (ResultError) catchErrorEvent.getEvent()
                    .getEventTriggerTypeNode();
        }
        return null;
    }

    /**
     * Returns true for the Catch Error Event on the Task boundary of a Web
     * Service Task which has a SOAP JMS Consumer Participant with
     * request-response/message-Expiration timeouts specified, set to catch
     * TIMEOUT_EXCEPTION.
     * 
     * 
     * @param catchErrorEventAct
     * @return true if the Catch Error Event is set to catch TIMEOUT_EXCEPTION.
     */
    public static boolean isTimeoutExceptionSelectedForSoapJMSConsumer(
            Activity catchErrorEventAct) {

        ResultError catchEventResultError =
                getCatchEventResultError(catchErrorEventAct);

        Activity wsdlFaultThrowingActivity =
                getWsdlFaultThrowingActivity(catchErrorEventAct);

        if (wsdlFaultThrowingActivity != null
                && CatchWsdlErrorEventUtil
                        .hasSoapJmsConsumerParticipant(wsdlFaultThrowingActivity)
                && catchEventResultError != null) {

            return TIMEOUT_EXCEPTION.equals(catchEventResultError
                    .getErrorCode());

        }
        return false;

    }
}
