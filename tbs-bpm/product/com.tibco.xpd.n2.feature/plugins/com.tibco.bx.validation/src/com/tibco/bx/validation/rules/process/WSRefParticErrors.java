/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

/**
 * @author bharge
 * 
 */
public enum WSRefParticErrors {

    /**
     * "Participants used for web-service activities must be shared resource type Web-Service (see Shared Resource section of participant General properties)."
     */
    ISSUE_WEBACT_PARTIC_MUSTBE_SHAREDRES_WEBSVC(
            "bx.webActParticSharedResMustBeWebService"), //$NON-NLS-1$

    /**
     * "Participants used for send task activities in business service must be shared resource type Web-Service Consumer with Virtualisation binding (see Shared Resource section of participant General properties)."
     */
    ISSUE_SENDTASK_PARTIC_MUSTBE_OUTBOUND_VIRTUAL(
            "bx.sendTaskParticSharedResMustBeConsumerVirtual"), //$NON-NLS-1$

    /**
     * "A single web service participant for a port type must use same transport type ('%1$s' & '%2$s')."
     */
    // ###
    ISSUE_SAME_PARTIC_DIFF_TRANSPORT("bx.sameParticDiffTransportType"), //$NON-NLS-1$

    /**
     * "Same named web service participants for a port type must use same transport type ('%1$s' & '%2$s)."
     */
    // ###
    ISSUE_SAMENAME_PARTICS_DIFF_TRANSPORT("bx.sameNameParticDiffTransportType"), //$NON-NLS-1$

    /**
     * "Participants used for invoking web services must be shared resource type consumer"
     */
    ISSUE_SYS_PARTIC_SHAREDRES_MUST_BE_CONSUMER(
            "bx.sysParticSharedResMustBeConsumer"), //$NON-NLS-1$

    /**
     * "Participants used in outgoing web-service activities must specify a shared resource name (see Shared Resource section of participant General properties)."
     */
    ISSUE_SVCINVOKE_PARTIC_SHAREDRES_MUSTHAVENAME(
            "bx.serviceInvokeParticSharedResMustHaveName"), //$NON-NLS-1$

    /**
     * "Participant shared resource URI and JMS properties are ignored for outgoing web-service activities (see Shared Resource section of participant General properties)."
     */
    ISSUE_SVCINVOKE_PARTIC_SHAREDRESURI_IGNORED(
            "bx.serviceInvokeParticSharedResUriIgnored"), //$NON-NLS-1$

    /** "Participants used for providing web services must be shared resource type provider" */
    ISSUE_SYS_PARTIC_SHAREDRES_MUST_BE_PROVIDER(
            "bx.sysParticSharedResMustBeProvider"), //$NON-NLS-1$

    /**
     * "Participants used for SOAP Over HTTP incoming message request activities must specify a shared resource URI (see Shared Resource section of participant General properties)."
     */
    ISSUE_APIREQUEST_HTTP_PARTIC_MUSTHAVE_SHAREDRES_URI(
            "bx.apiRequestParticHttpMustHaveSharedResUri"), //$NON-NLS-1$

    /**
     * "System participant shared resource endpoint uri does not start with the same prefix as configured in the preference page"
     */

    SHAREDRES_URI_PREFIX_NOT_FROM_PREFSTORE(
            "bx.sharedResURIPrefixNotFromPrefStore"), //$NON-NLS-1$

    /**
     * "Participant shared resource URI for incoming message request activities must start with "
     * /" (see Shared Resource section of participant General properties)."
     */
    ISSUE_APIREQUEST_HTTP_PARTIC_SHAREDRESURI_INVALID(
            "bx.apiRequestParticSharedResUriInvalidFormat"), //$NON-NLS-1$

    /**
     * "A single web service participant must only be used for a single port type ('%1$s' & '%2$s')."
     */
    ISSUE_SAME_PARTIC_DIFF_PORT("bx.sameParticDiffPortType"), //$NON-NLS-1$

    /**
     * "Same named web service participants must only used for a single port type ('%1$s' & '%2$s)."
     */
    // ###
    ISSUE_SAMENAME_PARTICS_DIFF_PORT("bx.sameNameParticDiffPortType"), //$NON-NLS-1$

    /**
     * "Binding details of Consumer System Participant Shared Resource must be compatible with task properties Transport type "
     * */
    ISSUE_SVCINVOKE_PARTIC_BINDING_TYPE_MUSTBE_COMPATIBLE_WITH_TRANSPORT_TYPE(
            "bx.sysParticSharedResBindingTypeMustBeCompatibleWithTransportType"), //$NON-NLS-1$

    /**
     * "Participants used in outgoing jms web-service activities must specify outbound connection factory (see Shared Resource section of participant General properties)."
     */
    ISSUE_WS_PARTIC_MISSING_JMS_OUTBOUND_CONN_FACTORY(
            "bx.wsParticMissingJmsOutboundConnFactory"), //$NON-NLS-1$

    /**
     * "Participants used in outgoing jms web-service activities must specify outbound destination (see Shared Resource section of participant General properties)."
     */
    ISSUE_WS_PARTIC_MISSING_JMS_OUTBOUND_DESTINATION(
            "bx.wsParticMissingJmsOutboundDestination"), //$NON-NLS-1$

    /**
     * "Binding details of Provider System Participant Shared Resource must be compatible with task properties Transport type "
     * */
    ISSUE_SVCPROVIDER_PARTIC_BINDING_TYPE_MISMATCH_WITH_TRANSPORT_TYPE(
            "bx.providerSysParticSharedResBindingTypeMismatchWithTransportType"); //$NON-NLS-1$

    private final String error;

    private WSRefParticErrors(String error) {
        this.error = error;
    }

    public String getValue() {
        return error;
    }
}
