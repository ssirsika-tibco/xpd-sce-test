/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.bx.validation.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Validates all SOAP JMS Consumer participants Timeouts.
 * 
 * <p>
 * Validates the following:
 * </p>
 * <li>A warning message will be given if no time out (or zero time out) is
 * specified in any of the invocation time out or message expiration
 * configurations</li>
 * 
 * <li>An error message will be given if message expiration time out is greater
 * than request-response time out. (Always request-response time out must be
 * greater than message expiration time out). This validation will also be shown
 * when no time out is set on message expiration timeout and a timeout is set
 * for request-response.</li>
 * 
 * 
 * @author aprasad
 */
public class SoapJmsTimeoutRule extends ProcessValidationRule {

    /**
     * It is strongly recommended that SOAP JMS Participant definitions specify
     * timeouts (otherwise unresponsive JMS services will block invoking process
     * indefinitely).
     */
    private static final String WARNING_MISSING_TIMEOUT_CONFIGURATIONS =
            "bx.messageConfiguration.timeouts.missing"; //$NON-NLS-1$

    /**
     * The SOAP JMS Participant must specify the request-response timeout value
     * to be greater than the request message expiration timeout.
     */
    private static final String ISSUE_MSG_EXP_VALUE_INVALID =
            "bx.messageConfiguration.message.exp.invalid"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {

        List<Participant> allParticipantsList = new ArrayList<Participant>();

        EList<Participant> processParticipants = process.getParticipants();

        if (processParticipants.size() > 0) {

            allParticipantsList.addAll(processParticipants);
        }

        Package pckg = process.getPackage();
        EList<Participant> packageParticipants = pckg.getParticipants();

        if (packageParticipants.size() > 0) {

            allParticipantsList.addAll(packageParticipants);
        }

        for (Participant participant : allParticipantsList) {

            WsSoapJmsOutboundBinding soapJmsConsumerConfig =
                    getSoapJmsConsumerConfiguration(participant);

            if (soapJmsConsumerConfig != null) {

                boolean isMessageExpirationSet =
                        isMessageExpirationSet(soapJmsConsumerConfig);
                boolean isRequestResponseTimeoutSet =
                        isInvocationTimeoutSet(soapJmsConsumerConfig);

                /* If both are not set then raise Warning */
                if (!isMessageExpirationSet && !isRequestResponseTimeoutSet) {

                    addIssue(WARNING_MISSING_TIMEOUT_CONFIGURATIONS,
                            participant);
                    return;
                }
                int requestResponseTimeout =
                        soapJmsConsumerConfig.getInvocationTimeout();
                int messageExpiration =
                        soapJmsConsumerConfig.getMessageExpiration();
                /*
                 * If both are configured as zero time out then raise Warning
                 */
                if (requestResponseTimeout == 0 && messageExpiration == 0) {

                    addIssue(WARNING_MISSING_TIMEOUT_CONFIGURATIONS,
                            participant);
                    return;
                }
                /*
                 * Message Expiration is not set, but request-response is set
                 * and greater than zero - raise error
                 */
                if (!isMessageExpirationSet) {

                    if (requestResponseTimeout > 0) {

                        /*
                         * Message Expiration should be set when Request
                         * Response Timeout is set, raise Error
                         */
                        addIssue(ISSUE_MSG_EXP_VALUE_INVALID, participant);
                        return;
                    }
                }
                /*
                 * Raise Error when Message Expiration is greater than or equal
                 * to Request Response Timeout or Message Expiration is zero
                 * when request response is set
                 */
                if (messageExpiration >= requestResponseTimeout
                        || messageExpiration == 0) {

                    addIssue(ISSUE_MSG_EXP_VALUE_INVALID, participant);
                    return;
                }
            }
        }
    }

    /**
     * checks if Invocation Timeout is set.
     * 
     * @param soapJmsConsumerConfig
     * @return true if soapJmsConsumerConfig is not null and Invocation Timeout
     *         timeout is set, false otherwise.
     */
    private boolean isInvocationTimeoutSet(
            WsSoapJmsOutboundBinding soapJmsConsumerConfig) {

        if (soapJmsConsumerConfig != null) {

            return soapJmsConsumerConfig.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_InvocationTimeout());
        }

        return false;
    }

    /**
     * Checks if Message Expiration timeout is set.
     * 
     * @param soapJmsConsumerConfig
     * @return true if soapJmsConsumerConfig is not null and Message Expiration
     *         timeout is set, false otherwise.
     */
    private boolean isMessageExpirationSet(
            WsSoapJmsOutboundBinding soapJmsConsumerConfig) {

        if (soapJmsConsumerConfig != null) {

            return soapJmsConsumerConfig.eIsSet(XpdExtensionPackage.eINSTANCE
                    .getWsSoapJmsOutboundBinding_MessageExpiration());
        }

        return false;
    }

    /**
     * Returns {@link WsSoapJmsOutboundBinding} if the assigned participant to
     * the activity is SOAP JMS Consumer.
     * 
     * @param allParticipantsList
     * @param assignedParticipantId
     * @param activity
     * @return Soap JMS Outbound Binding for if this participant is a SOAP JMS
     *         Consumer.
     */
    private WsSoapJmsOutboundBinding getSoapJmsConsumerConfiguration(
            Participant participant) {

        WsOutbound outbound =
                SharedResourceUtil.getWsResourceOutbound(participant);
        if (outbound != null) {

            return outbound.getSoapJmsBinding();
        }

        return null;
    }

}
