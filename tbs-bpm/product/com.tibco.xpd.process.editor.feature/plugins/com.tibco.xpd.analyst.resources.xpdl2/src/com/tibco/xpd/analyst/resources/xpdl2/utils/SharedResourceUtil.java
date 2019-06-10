/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.WsVirtualBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility for easier access and manipulation of system participant's shared
 * resources.
 * 
 * @author Jan Arciuchiewicz
 */
public class SharedResourceUtil {

    /**
     * Gets system participant's shared resource model or <code>null</code> if
     * it doesn't exist or is not relevant.
     * 
     * @param participant
     *            the participant to get the shared resource model for.
     * @return system participant's shared resource model or <code>null</code>
     *         if it doesn't exist or is not relevant.
     */
    public static ParticipantSharedResource getParticipantSharedResource(
            Participant participant) {
        if (participant != null) {
            ParticipantTypeElem participantType =
                    participant.getParticipantType();
            if (participantType != null
                    && ParticipantType.SYSTEM_LITERAL.equals(participantType
                            .getType())) {
                ParticipantSharedResource sharedResource =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                return sharedResource;
            }
        }
        return null;
    }

    /**
     * Gets web service outbound (service consumer configuration) for a
     * participant or <code>null</code> if participant is not system with web
     * service resource configured as service consumer.
     * 
     * @param participant
     *            the context participant.
     * @return gets web service outbound (consumer configuration) for a
     *         participant.
     */
    public static WsOutbound getWsResourceOutbound(Participant participant) {
        ParticipantSharedResource sharedResource =
                getParticipantSharedResource(participant);
        if (sharedResource != null && sharedResource.getWebService() != null) {
            return sharedResource.getWebService().getOutbound();
        }
        return null;
    }

    /**
     * Gets web service inbound (service provider (or API) configuration) for a
     * participant or <code>null</code> if participant is not system with web
     * service resource configured as service provider.
     * 
     * @param participant
     *            the context participant.
     * @return gets web service inbound (provider configuration) for a
     *         participant.
     */
    public static WsInbound getWsResourceInbound(Participant participant) {
        ParticipantSharedResource sharedResource =
                getParticipantSharedResource(participant);
        if (sharedResource != null && sharedResource.getWebService() != null) {
            return sharedResource.getWebService().getInbound();
        }
        return null;
    }

    /**
     * Checks if the provided outbounds have the same configuration.
     * 
     * @param o1
     *            first of outbound to compare.
     * @param o2
     *            second of outbound to compare.
     * @return <code>true</code> if the both have the same configuration.
     */
    public static boolean haveSameWsOutbounfiguration(WsOutbound o1,
            WsOutbound o2) {
        if (bothNull(o1, o2)) {
            return true;
        } else if (oneIsNull(o1, o2)) {
            return false;
        }

        {// Same VirtualBinding.
            WsVirtualBinding b1 = o1.getVirtualBinding();
            WsVirtualBinding b2 = o2.getVirtualBinding();
            if (oneIsNull(b1, b2)) {
                return false;
            }
        }
        {// Same SoapHttpBinding.
            WsSoapHttpOutboundBinding b1 = o1.getSoapHttpBinding();
            WsSoapHttpOutboundBinding b2 = o2.getSoapHttpBinding();
            if (oneIsNull(b1, b2)) {
                return false;
            } else if (!bothNull(b1, b2)) {
                if (!areEqual(b1.getHttpClientInstanceName(),
                        b1.getHttpClientInstanceName())) {
                    return false;
                }
                WsSecurityPolicy s1 = getSoapHttpOutSecurityPolicy(o1);
                WsSecurityPolicy s2 = getSoapHttpOutSecurityPolicy(o2);
                if (oneIsNull(s1, s2)) {
                    return false;
                } else if (!bothNull(s1, s2)) {
                    if (s1.getType() != s2.getType()) {
                        return false;
                    }
                    if (!areEqual(s1.getGovernanceApplicationName(),
                            s2.getGovernanceApplicationName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean bothNull(Object o1, Object o2) {
        return o1 == null && o2 == null;
    }

    /**
     * @param o1
     * @param o2
     * @return <code>true</code> if noth objects are equal (including treating
     *         null and "" in the case of strings as equal.
     * 
     */
    private static boolean areEqual(Object o1, Object o2) {
        /*
         * XPD-7543: treate null and "" as equal.
         */
        if (bothNull(o1, o2)) {
            return true;
        }

        if (o1 != null && o2 != null) {
            return o1.equals(o2);
        }

        if (o1 instanceof String && ((String) o1).length() == 0 && o2 == null) {
            return true;
        }

        if (o2 instanceof String && ((String) o2).length() == 0 && o1 == null) {
            return true;
        }

        return false;
    }

    private static boolean oneIsNull(Object o1, Object o2) {
        return o1 == null && o2 != null || o1 != null && o2 == null;
    }

    /**
     * Gets first security policy for SOAP/HTTP outbound (consumer) binding.
     * 
     * @param httpSoapBinding
     *            the context service consumer binding.
     * @return first security policy or <code>null</code> if not set.
     */
    public static WsSecurityPolicy getSoapHttpOutSecurityPolicy(
            WsOutbound wsOutbound) {
        if (wsOutbound.getBinding() instanceof WsSoapHttpOutboundBinding) {
            return getSoapOutSecurityPolicy(wsOutbound);
        }
        return null;
    }

    /**
     * Gets first security policy for SOAP outbound (consumer) binding.
     * 
     * @param httpSoapBinding
     *            the context service consumer binding.
     * @return first security policy or <code>null</code> if not set.
     */
    public static WsSecurityPolicy getSoapOutSecurityPolicy(
            WsOutbound wsOutbound) {
        if (wsOutbound.getBinding() instanceof WsSoapBinding) {
            WsSoapSecurity soapSecurity =
                    ((WsSoapBinding) wsOutbound.getBinding()).getSoapSecurity();
            if (soapSecurity != null
                    && !soapSecurity.getSecurityPolicy().isEmpty()) {
                return soapSecurity.getSecurityPolicy().get(0);
            }
        }
        return null;
    }

    /**
     * Returns {@link RestServiceResource} for the system participant or 'null'.
     * 
     * @param participant
     *            the participant to check.
     * @return {@link RestServiceResource} for the system participant or 'null'
     *         if it's not participant for the REST service consumer.
     */
    public static RestServiceResource getRestConsumerResource(
            Participant participant) {
        ParticipantSharedResource sharedResource =
                getParticipantSharedResource(participant);
        if (sharedResource != null && sharedResource.getRestService() != null) {
            return sharedResource.getRestService();
        }
        return null;
    }

    /**
     * Checks if participant is a system participant for rest service consumer.
     * 
     * @param participant
     *            the participant to check.
     * @return if participant is a system participant for rest service consumer.
     */
    public static boolean isRestConsumer(Participant participant) {
        return getRestConsumerResource(participant) != null;
    }

    /**
     * Checks if participant is a system participant for web service consumer.
     * 
     * @param participant
     *            the participant to check.
     * @return if participant is a system participant for web service consumer.
     */
    public static boolean isWebServiceConsumer(Participant participant) {
        return getWsResourceOutbound(participant) != null;
    }

    /**
     * @param p1RestServiceResource
     * @param p2RestServiceResource
     * @return <code>true</code> if the 2 REST Consumer shared resource
     *         configuration are equivalent to each other
     */
    public static boolean haveSameRESTCOnsumerConfiguration(
            RestServiceResource p1RestServiceResource,
            RestServiceResource p2RestServiceResource) {

        if (p1RestServiceResource != null && p2RestServiceResource != null) {

            if (areEqual(p1RestServiceResource.getResourceName(),
                    p2RestServiceResource.getResourceName())) {

                /*
                 * Compare secutiry policies
                 */
                RestServiceResourceSecurity p1SecPolicy = null;
                if (p1RestServiceResource.getSecurityPolicy() != null
                        && !p1RestServiceResource.getSecurityPolicy().isEmpty()) {
                    p1SecPolicy =
                            p1RestServiceResource.getSecurityPolicy().get(0);
                }

                RestServiceResourceSecurity p2SecPolicy = null;
                if (p2RestServiceResource.getSecurityPolicy() != null
                        && !p2RestServiceResource.getSecurityPolicy().isEmpty()) {
                    p2SecPolicy =
                            p2RestServiceResource.getSecurityPolicy().get(0);
                }

                if (bothNull(p1SecPolicy, p2SecPolicy)) {
                    return true;
                }

                if (!oneIsNull(p1SecPolicy, p2SecPolicy)) {
                    SecurityPolicy p1PolicyType = p1SecPolicy.getPolicyType();
                    SecurityPolicy p2PolicyType = p2SecPolicy.getPolicyType();

                    if (bothNull(p1PolicyType, p2PolicyType)) {
                        return true;
                    }

                    if (areEqual(p1PolicyType, p2PolicyType)) {

                        if (p1SecPolicy.getExtendedAttributes().size() == p2SecPolicy
                                .getExtendedAttributes().size()) {

                            for (ExtendedAttribute extAttr : p1SecPolicy
                                    .getExtendedAttributes()) {

                                for (ExtendedAttribute extAttr2 : p2SecPolicy
                                        .getExtendedAttributes()) {
                                    if (areEqual(extAttr2.getName(),
                                            extAttr.getName())) {

                                        if (extAttr.getValue() != null) {
                                            if (!areEqual(extAttr.getValue(),
                                                    extAttr2.getValue())) {
                                                return false;
                                            }

                                        } else if (!EcoreUtil.equals(extAttr2,
                                                extAttr)) {
                                            return false;
                                        }
                                    }
                                }
                            }

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
