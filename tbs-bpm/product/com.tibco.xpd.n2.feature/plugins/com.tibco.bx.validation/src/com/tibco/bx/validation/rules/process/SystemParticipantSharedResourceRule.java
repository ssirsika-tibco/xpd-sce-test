/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.n2.resources.util.N2Utils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.EmailResource;
import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check many aspects of web-service participant usage and configuration
 * depending on how it is used.
 * 
 * @author aallway
 * @since 3.3 (22 Jan 2010)
 */
public class SystemParticipantSharedResourceRule extends PackageValidationRule {

    /**
     * "Participants with different shared resource configuration cannot have
     * same name ('%1$s' vs. '%2$s')."
     */
    private static final String ISSUE_DIFFCONFIG_SAMENAME =
            "bx.systemParticWithDiffConfigMustBeDiffName"; //$NON-NLS-1$

    /**
     * "Participants used for process as service must specify unique shared
     * resource URI"
     */
    private static final String ISSUE_SAME_SHARED_RESOURCE_URI =
            "bx.issueSameSharedResourceUri"; //$NON-NLS-1$

    private static final String ISSUE_PARTICIPANTS_WITH_SAME_SHARED_RESOURCE =
            "bx.issueSameSharedResourceforDiffrentParticipants"; //$NON-NLS-1$

    /**
     * "Web service participants with SAML / X509 / UsernameToken security
     * policy type must specify a shared resource instance and issuer name for
     * the policy."
     */
    private static final String ISSUE_WS_PARTIC_MISSING_POLICY_INFO =
            "bx.wsParticMissingPolicyInfo"; //$NON-NLS-1$

    /**
     * REST service participants with 'Basic' security policy type must specify
     * a shared resource instance and issuer name for the policy.
     */
    private static final String ISSUE_REST_PARTIC_MISSING_POLICY_INFO =
            "bx.restParticMissingPolicyInfo"; //$NON-NLS-1$

    /**
     * The referenced custom policy set '%1$s' no longer exists.
     */
    private static final String ISSUE_WS_OR_REST_CUSTOM_POLICY_INVALID_REF =
            "bx.wsOrRestCustomPolicyInvalidRef"; //$NON-NLS-1$

    /**
     * A custom policy set must be selected for participant customer security
     * policy.
     */
    private static final String ISSUE_WS_OR_REST_CUSTOM_POLICY_NOT_SET =
            "bx.wsOrRestCustomPolicyNotSet"; //$NON-NLS-1$

    /**
     * System participant shared resource endpoint uri does not start with the
     * same prefix as configured in the preference page
     */
    private static final String SHAREDRES_URI_PREFIX_NOT_FROM_PREFSTORE =
            "bx.sharedResURIPrefixNotFromPrefStore"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com
     * .tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pckg) {
        /*
         * Get all participants in the same project as package that has changed.
         */
        IProject targetProject = WorkingCopyUtil.getProjectFor(pckg);
        if (targetProject != null) {

            /*
             * Get all participants in the package in question.
             */
            Collection<Participant> pkgParticipants =
                    N2Utils.getPackageParticipants(pckg);
            if (pkgParticipants.size() > 0) {

                /*
                 * Get the indexer items so we can quickly compare names
                 * (without loading stuff without reason.
                 */
                List<IndexerItem> sortedProjectPartics =
                        N2Utils.getProjectParticipants(targetProject);

                for (Participant participant : pkgParticipants) {
                    checkParticipants(pckg,
                            participant,
                            sortedProjectPartics,
                            targetProject);
                }
            }
        }

        return;
    }

    /**
     * Compare the system participant with all other participants.
     * 
     * @param participant
     * @param sortedProjectPartics
     * @param targetProject
     */
    private void checkParticipants(Package pckg, Participant participant,
            List<IndexerItem> sortedProjectPartics, IProject targetProject) {

        if (ParticipantType.SYSTEM_LITERAL
                .equals(N2Utils.getParticipantType(participant))) {
            String particName = participant.getName();
            if (particName != null) {
                /*
                 * Check that all participants with the same name as this one
                 * have the same configuration.
                 */
                for (IndexerItem ii : sortedProjectPartics) {
                    String iiName = ii.get(Xpdl2ResourcesPlugin.ATTRIBUTE_NAME);

                    URI uri = URI.createURI(ii.getURI());

                    if (uri != null) {
                        EObject o = ProcessUIUtil.getEObjectFrom(uri);
                        if (o instanceof Participant) {
                            Participant otherPartic = (Participant) o;

                            /*
                             * don't care if other partic is not a system one.
                             */
                            if (ParticipantType.SYSTEM_LITERAL.equals(
                                    N2Utils.getParticipantType(otherPartic))) {
                                /*
                                 * Don't care if it's exactly the same
                                 * participant.
                                 */
                                if (!otherPartic.equals(participant)) {

                                    if (particName.equals(iiName)) {
                                        /*
                                         * Check that all participants with the
                                         * same name as this one have the same
                                         * configuration.
                                         */
                                        compareSystemParticipantsWithSameName(
                                                participant,
                                                otherPartic);
                                    }

                                    /*
                                     * Check SharedResource URI is not the same
                                     * for different process API participants
                                     */
                                    checkProcessApiUrisAreUnique(targetProject,
                                            participant,
                                            otherPartic);
                                }
                                /**
                                 * check if the shared resource uri prefix is
                                 * referring the prefix configured on the
                                 * preference page - [bharti on 05/July/2012]
                                 * moved this check to WSParicRefComparator
                                 * getProviderRelatedIssues()
                                 */
                            }
                        }
                    }
                }

            }
        }
        return;
    }

    private String getParticSharedResEndpointUrlPath(
            ParticipantSharedResource participantSharedRes) {
        String ret = ""; //$NON-NLS-1$

        if (null != participantSharedRes) {
            WsResource wsResource = participantSharedRes.getWebService();
            if (null != wsResource) {
                WsInbound wsInbound = wsResource.getInbound();
                if (null != wsInbound) {
                    EList<WsSoapHttpInboundBinding> soapHttpInBinding =
                            wsInbound.getSoapHttpBinding();

                    for (WsSoapHttpInboundBinding wsSoapHttpInboundBinding : soapHttpInBinding) {
                        ret = wsSoapHttpInboundBinding.getEndpointUrlPath();
                    }

                }
                if (null == ret) {
                    ret = ""; //$NON-NLS-1$
                }
            }
        }
        return ret;
    }

    /**
     * @param project
     * @param participant
     * @param otherPartic
     */
    private void checkProcessApiUrisAreUnique(IProject project,
            Participant participant, Participant otherPartic) {

        if (ProcessUIUtil.isProcessApiParticipant(project, participant.getId())
                && ProcessUIUtil.isProcessApiParticipant(project,
                        otherPartic.getId())) {

            boolean samePortTypes = new WSParticRefComparator()
                    .areSamePortTypeAndNameSpace(project,
                            participant,
                            otherPartic);

            if (!samePortTypes) {

                ParticipantSharedResource particSharedRes =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                ParticipantSharedResource otherParticSharedRes =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(otherPartic,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());

                String endpointUrlPath =
                        getParticSharedResEndpointUrlPath(particSharedRes);
                String otherEndpointUrlPath =
                        getParticSharedResEndpointUrlPath(otherParticSharedRes);
                if (endpointUrlPath.length() > 0
                        && endpointUrlPath.equals(otherEndpointUrlPath)) {
                    addIssue(ISSUE_SAME_SHARED_RESOURCE_URI, participant);
                }
            } else {

                /*
                 * SID XPD-4885 - found unrelated issue with the implementation
                 * of the problem
                 * "Identical participant shared resource configurations must use same participant name ('%1$s' and '%2$s')."
                 * 
                 * The issue was being raised regardless of whether the two
                 * names were identical or not!
                 */
                if (!participant.getName().equals(otherPartic.getName())) {
                    addIssue(ISSUE_PARTICIPANTS_WITH_SAME_SHARED_RESOURCE,
                            participant,
                            Arrays.asList(new String[] { participant.getName(),
                                    otherPartic.getName() }));
                }

            }

        }
        return;
    }

    /**
     * Gets 2 strings to be used as parameters for problem text. The strings are
     * the 'paths' to the given activities in the form...
     * <p>
     * [xpdl file/]process/activity
     * <p>
     * The xpdl file is included IF the activities resource is different from
     * the localResource.
     * 
     * @param localResource
     * @param activityUri1
     * @param activityUri2
     * 
     * @return activity paths for given activities
     */
    private List<String> getActivityPaths(IResource localResource,
            String activityUri1, String activityUri2) {
        List<String> paths = new ArrayList<String>();
        paths.add(getActivityPath(localResource, activityUri1));
        paths.add(getActivityPath(localResource, activityUri2));

        return paths;
    }

    /**
     * Gets 2 strings to be used as parameters for problem text. The strings are
     * the 'paths' to the given activities in the form...
     * <p>
     * [xpdl file/]process/activity
     * <p>
     * The xpdl file is included IF the activities resource is different from
     * the localResource.
     * 
     * @param localResource
     * @param activityUri
     * @return activity path for given activity
     */
    private String getActivityPath(IResource localResource,
            String activityUri) {
        String path;
        String actName = null;
        String procName = null;
        String otherResName = null;

        if (activityUri != null && activityUri.length() > 0) {

            URI uri = URI.createURI(activityUri);
            if (uri != null) {
                ResourceSet resourceSet = XpdResourcesPlugin.getDefault()
                        .getEditingDomain().getResourceSet();

                EObject activity = resourceSet.getEObject(uri, true);
                if (activity instanceof Activity) {
                    actName = Xpdl2ModelUtil
                            .getDisplayNameOrName((Activity) activity);
                    procName = Xpdl2ModelUtil.getDisplayNameOrName(
                            ((Activity) activity).getProcess());

                    otherResName = null;
                    if (localResource != null) {
                        IFile otherRes = WorkingCopyUtil.getFile(activity);
                        if (otherRes != null) {
                            if (!localResource.equals(otherRes)) {
                                otherResName = otherRes.getName();
                            }
                        }
                    }
                }
            }
        }

        if (actName == null || actName.trim().length() == 0) {
            actName = "???"; //$NON-NLS-1$
        }

        if (procName == null || procName.trim().length() == 0) {
            procName = "???"; //$NON-NLS-1$
        }

        if (otherResName != null && otherResName.length() > 0) {
            path = otherResName + "/" + procName + "/" + actName; //$NON-NLS-1$ //$NON-NLS-2$
        } else {
            path = procName + "/" + actName; //$NON-NLS-1$
        }
        return path;
    }

    /**
     * Compare two system participants that have the same name (raise an issue
     * of shared resource config is different)
     * 
     * @param participant
     * @param otherPartic
     */
    private void compareSystemParticipantsWithSameName(Participant participant,
            Participant otherPartic) {
        boolean sameNameDiffConfig = false;

        if (!participant.equals(otherPartic)) {
            ParticipantSharedResource particSharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil.getOtherElement(
                            participant,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantSharedResource());

            ParticipantSharedResource otherParticSharedResource =
                    (ParticipantSharedResource) Xpdl2ModelUtil.getOtherElement(
                            otherPartic,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ParticipantSharedResource());

            /* one is web service type and other is not */
            if (null != particSharedResource
                    && null != otherParticSharedResource) {

                boolean sameType = checkSharedResType(particSharedResource,
                        otherParticSharedResource);

                if (sameType) {
                    /*
                     * both are same type then - (by same type we mean that both
                     * are jdbc, email or web service participants. now find the
                     * differences in the participant shared resource
                     * configuration within their areas)
                     * 
                     * case 1: check for differences in web service type.
                     * 
                     * case 2: check for differences in jdbc type.
                     * 
                     * case 3: check for differences in email type
                     */
                    if (null != particSharedResource.getWebService()
                            && null != otherParticSharedResource
                                    .getWebService()) {
                        sameNameDiffConfig =
                                checkForWebServiceDiff(particSharedResource,
                                        otherParticSharedResource);
                    } else if (null != particSharedResource.getJdbc()
                            && null != otherParticSharedResource.getJdbc()) {
                        sameNameDiffConfig =
                                checkForJdbcDiff(particSharedResource,
                                        otherParticSharedResource);
                    } else if (null != particSharedResource.getEmail()
                            && null != otherParticSharedResource.getEmail()) {
                        sameNameDiffConfig =
                                checkForEmailDiff(particSharedResource,
                                        otherParticSharedResource);

                    } else if (null != particSharedResource.getRestService()) {
                        /*
                         * Sid XPD-7543: REST Service participants can have the
                         * same name even with different configuration because
                         * the source of truth for the endpoint is the actual
                         * conbfiguration.
                         */
                    }
                } else {
                    sameNameDiffConfig = true;
                }

            }
        }

        if (sameNameDiffConfig) {
            IFile particFile = WorkingCopyUtil.getFile(participant);
            IFile otherFile = WorkingCopyUtil.getFile(otherPartic);

            if (particFile != null && otherFile != null) {

                ArrayList<String> msgParams = new ArrayList<String>();
                msgParams.add(Xpdl2ModelUtil.getDisplayNameOrName(participant));
                msgParams.add(getParticipantPath(otherPartic));

                if (!particFile.equals(otherFile)) {
                    /*
                     * When participants are in a different file need to set it
                     * as a linked resource so that files are temporarily
                     * dependent for validations
                     */
                    String resURI = URI.createPlatformResourceURI(
                            otherFile.getFullPath().toPortableString(),
                            true).toString();

                    addIssue(ISSUE_DIFFCONFIG_SAMENAME,
                            participant,
                            msgParams,
                            Collections.singletonMap(
                                    ValidationActivator.LINKED_RESOURCE,
                                    resURI));

                } else {
                    /*
                     * Participant in same resource does not need to be flagged
                     * as a LINKED_RESOURCE in marker.
                     */
                    addIssue(ISSUE_DIFFCONFIG_SAMENAME, participant, msgParams);

                }
            }
        }

        return;
    }

    /**
     * @param particSharedResource
     * @param otherParticSharedResource
     * @return
     */
    private boolean checkSharedResType(
            ParticipantSharedResource particSharedResource,
            ParticipantSharedResource otherParticSharedResource) {
        if (null != particSharedResource.getWebService()
                && null != otherParticSharedResource.getWebService()) {
            return true;
        }
        if (null != particSharedResource.getJdbc()
                && null != otherParticSharedResource.getJdbc()) {
            return true;
        }
        if (null != particSharedResource.getEmail()
                && null != otherParticSharedResource.getEmail()) {
            return true;
        }

        if (null != particSharedResource.getRestService()
                && null != otherParticSharedResource.getRestService()) {
            return true;
        }
        return false;
    }

    /**
     * @param emailResource
     * @param otherEmailResource
     * @return
     */
    private boolean checkForEmailDiff(
            ParticipantSharedResource particSharedResource,
            ParticipantSharedResource otherParticSharedResource) {
        EmailResource emailResource = particSharedResource.getEmail();
        EmailResource otherEmailResource = otherParticSharedResource.getEmail();

        if (null != emailResource && null != otherEmailResource) {
            if (null != emailResource.getInstanceName()
                    && null != otherEmailResource.getInstanceName()
                    && !emailResource.getInstanceName()
                            .equals(otherEmailResource.getInstanceName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param jdbcResource
     * @param otherJdbcResource
     * @return
     */
    private boolean checkForJdbcDiff(
            ParticipantSharedResource particSharedResource,
            ParticipantSharedResource otherParticSharedResource) {
        JdbcResource jdbcResource = particSharedResource.getJdbc();
        JdbcResource otherJdbcResource = otherParticSharedResource.getJdbc();

        if (null != jdbcResource && null != otherJdbcResource) {
            if (null != jdbcResource.getInstanceName()
                    && null != otherJdbcResource.getInstanceName()
                    && !jdbcResource.getInstanceName()
                            .equals(otherJdbcResource.getInstanceName())) {
                return true;
            }
            if (null != jdbcResource.getJdbcProfileName()
                    && null != otherJdbcResource.getJdbcProfileName()
                    && !jdbcResource.getJdbcProfileName()
                            .equals(otherJdbcResource.getJdbcProfileName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param particSharedResource
     * @param otherParticSharedResource
     * @return
     */
    private boolean checkForWebServiceDiff(
            ParticipantSharedResource particSharedResource,
            ParticipantSharedResource otherParticSharedResource) {

        /* check for inbound differences */
        boolean sameNameDiffConfig = false;

        WsResource wsResource = particSharedResource.getWebService();
        WsResource otherWsResource = otherParticSharedResource.getWebService();

        if (isOneObjectNull(wsResource.getInbound(),
                otherWsResource.getInbound())) {
            return true;
        }

        if (isOneObjectNull(wsResource.getOutbound(),
                otherWsResource.getOutbound())) {
            return true;
        }

        if (null != wsResource.getInbound()
                && null != otherWsResource.getInbound()) {
            sameNameDiffConfig = inboundWebServiceDiff(wsResource.getInbound(),
                    otherWsResource.getInbound());
        }
        /* check for outbound differences */
        if (!sameNameDiffConfig) {
            if (null != wsResource.getOutbound()
                    && null != otherWsResource.getOutbound()) {
                sameNameDiffConfig =
                        outboundWebServiceDiff(wsResource.getOutbound(),
                                otherWsResource.getOutbound());
            }
        }

        return sameNameDiffConfig;
    }

    /**
     * @param wsOutbound
     * @param otherWsOutbound
     * @return
     */
    private boolean outboundWebServiceDiff(WsOutbound wsOutbound,
            WsOutbound otherWsOutbound) {
        /*
         * SoapHttpBinding - name, http client instance name, outbound security
         * - security policy - governance application name, type VirtualBinding
         */

        /* check for http binding */
        WsSoapHttpOutboundBinding soapHttpBinding =
                wsOutbound.getSoapHttpBinding();
        WsSoapHttpOutboundBinding othersoapHttpBinding =
                otherWsOutbound.getSoapHttpBinding();
        if (soapHttpBinding == null && othersoapHttpBinding != null
                || soapHttpBinding != null && othersoapHttpBinding == null) {
            return true;
        }
        if (null != soapHttpBinding && null != othersoapHttpBinding) {
            // ignoring binding name as there is no UI
            /*
             * if (!soapHttpBinding.getName().equals(othersoapHttpBinding
             * .getName())) { return true; }
             */
            if (isDiffNullSafe(soapHttpBinding.getHttpClientInstanceName(),
                    othersoapHttpBinding.getHttpClientInstanceName())) {
                return true;
            }
            WsSoapSecurity security = soapHttpBinding.getOutboundSecurity();
            WsSoapSecurity otherSecurity =
                    othersoapHttpBinding.getOutboundSecurity();
            EList<WsSecurityPolicy> securityPolicy = null;
            int policySize = 0;
            if (security != null) {
                securityPolicy = security.getSecurityPolicy();
                policySize = securityPolicy.size();
            }
            EList<WsSecurityPolicy> otherSecurityPolicy = null;
            int otherPolicySize = 0;
            if (otherSecurity != null) {
                otherSecurityPolicy = otherSecurity.getSecurityPolicy();
                otherPolicySize = otherSecurityPolicy.size();
            }
            if (policySize != otherPolicySize) {
                return true;
            }
            for (int j = 0; j < policySize; j++) {
                WsSecurityPolicy wsSecurityPolicy = securityPolicy.get(j);
                WsSecurityPolicy otherWsSecurityPolicy =
                        otherSecurityPolicy.get(j);
                if (wsSecurityPolicy != null && otherWsSecurityPolicy != null) {
                    if (isDiffNullSafe(
                            wsSecurityPolicy.getGovernanceApplicationName(),
                            otherWsSecurityPolicy
                                    .getGovernanceApplicationName())) {
                        return true;
                    }
                    if (isDiffNullSafe(wsSecurityPolicy.getType(),
                            otherWsSecurityPolicy.getType())) {
                        return true;
                    }
                }
            }
        }
        /* check for jms binding */
        WsSoapJmsOutboundBinding soapJmsBinding =
                wsOutbound.getSoapJmsBinding();
        WsSoapJmsOutboundBinding otherSoapJmsBinding =
                otherWsOutbound.getSoapJmsBinding();
        if (soapJmsBinding == null && otherSoapJmsBinding != null
                || soapJmsBinding != null && otherSoapJmsBinding == null) {
            return true;
        }
        if (null != soapJmsBinding && null != otherSoapJmsBinding) {

            if (isDiffNullSafe(soapJmsBinding.getOutboundConnectionFactory(),
                    otherSoapJmsBinding.getOutboundConnectionFactory())
                    || isDiffNullSafe(soapJmsBinding.getInboundDestination(),
                            otherSoapJmsBinding.getInboundDestination())
                    || isDiffNullSafe(soapJmsBinding.getOutboundDestination(),
                            otherSoapJmsBinding.getOutboundDestination())
                    || isDiffJmsMessageConfiguration(soapJmsBinding,
                            otherSoapJmsBinding)) {

                return true;
            }
        }

        // should ignore name attribute as this is ignored by the user
        /*
         * if (null != soapJmsBinding && null != soapJmsBinding.getName() &&
         * null != otherSoapJmsBinding && null != otherSoapJmsBinding.getName()
         * && !soapJmsBinding.getName().equals(otherSoapJmsBinding .getName()))
         * { return true; }
         */

        /* check for virtual binding */
        // should ignore name attribute as this is ignored by the user
        /*
         * WsVirtualBinding virtualBinding = wsOutbound.getVirtualBinding();
         * WsVirtualBinding otherVirtualBinding =
         * otherWsOutbound.getVirtualBinding(); if (null != virtualBinding &&
         * null != otherVirtualBinding) { if
         * (!virtualBinding.getName().equals(otherVirtualBinding.getName())) {
         * return true; } }
         */

        return false;
    }

    /**
     * @param soapJmsBinding
     * @param otherSoapJmsBinding
     * @return <code>true</code> if the time out configuration is different on
     *         both outbound bindings
     */
    private boolean isDiffJmsMessageConfiguration(
            WsSoapJmsOutboundBinding soapJmsBinding,
            WsSoapJmsOutboundBinding otherSoapJmsBinding) {

        /* if time out is set on one participant and not on the other */
        if (isDiffNullSafe(soapJmsBinding.getInvocationTimeout(),
                otherSoapJmsBinding.getInvocationTimeout())) {
            return true;
        }

        if (isDiffNullSafe(soapJmsBinding.getMessageExpiration(),
                otherSoapJmsBinding.getMessageExpiration())) {
            return true;
        }

        if (isDiffNullSafe(soapJmsBinding.getDeliveryMode(),
                otherSoapJmsBinding.getDeliveryMode())) {
            return true;
        }

        if (isDiffNullSafe(soapJmsBinding.getPriority(),
                otherSoapJmsBinding.getPriority())) {
            return true;
        }

        return false;
    }

    /**
     * Returns 'true' if objects are not equal. Checks for 'null' first.
     */
    private static boolean isDiffNullSafe(Object s1, Object s2) {
        return (s1 == null && s2 != null) || (s1 != null && !s1.equals(s2));
    }

    /**
     * Returns 'true' if one of the object is null and other is not null.
     */
    private static boolean isOneObjectNull(Object o1, Object o2) {
        if ((null == o1 && null != o2) || null != o1 && null == o2) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param wsResource
     * @param otherWsResource
     * @param sameNameDiffConfig
     * @return
     */
    private boolean inboundWebServiceDiff(WsInbound wsInbound,
            WsInbound otherWsInbound) {
        /*
         * Name, binding style, soap version, endpoint url path, http connector
         * instance name
         */
        EList<WsSoapHttpInboundBinding> soapHttpBinding =
                wsInbound.getSoapHttpBinding();
        EList<WsSoapHttpInboundBinding> otherSoapHttpBinding =
                otherWsInbound.getSoapHttpBinding();

        if (null != soapHttpBinding && null != otherSoapHttpBinding) {
            int size = soapHttpBinding.size();
            int otherSize = otherSoapHttpBinding.size();
            if (size != otherSize) {
                return true;
            }
            for (int i = 0; i < soapHttpBinding.size(); i++) {
                WsSoapHttpInboundBinding binding = soapHttpBinding.get(i);
                WsSoapHttpInboundBinding otherBinding =
                        otherSoapHttpBinding.get(i);
                // should ignore name attribute as this is not visible to the
                // user
                /*
                 * String name = binding.getName(); String otherName =
                 * otherBinding.getName(); if (null != name && null !=
                 * otherName) { if (!name.equals(otherName)) { return true; } }
                 */
                if (isDiffNullSafe(binding.getBindingStyle(),
                        otherBinding.getBindingStyle())) {
                    return true;
                }
                if (isDiffNullSafe(binding.getSoapVersion(),
                        otherBinding.getSoapVersion())) {
                    return true;
                }
                if (isDiffNullSafe(binding.getEndpointUrlPath(),
                        otherBinding.getEndpointUrlPath())) {
                    return true;
                }
                if (isDiffNullSafe(binding.getHttpConnectorInstanceName(),
                        otherBinding.getHttpConnectorInstanceName())) {
                    return true;
                }
                WsSoapSecurity inboundSecurity = binding.getInboundSecurity();
                WsSoapSecurity otherInboundSecurity =
                        otherBinding.getInboundSecurity();
                if (inboundSecurity != null && otherInboundSecurity != null) {
                    EList<WsSecurityPolicy> securityPolicy =
                            inboundSecurity.getSecurityPolicy();
                    EList<WsSecurityPolicy> otherSecurityPolicy =
                            otherInboundSecurity.getSecurityPolicy();
                    if (securityPolicy.size() != otherSecurityPolicy.size()) {
                        return true;
                    }
                    for (int j = 0; j < securityPolicy.size(); j++) {
                        WsSecurityPolicy wsSecurityPolicy =
                                securityPolicy.get(j);
                        WsSecurityPolicy otherWsSecurityPolicy =
                                otherSecurityPolicy.get(j);
                        if (isDiffNullSafe(
                                wsSecurityPolicy.getGovernanceApplicationName(),
                                otherWsSecurityPolicy
                                        .getGovernanceApplicationName())) {
                            return true;
                        }
                        if (isDiffNullSafe(wsSecurityPolicy.getType(),
                                otherWsSecurityPolicy.getType())) {
                            return true;
                        }
                    }
                } else if (inboundSecurity != otherInboundSecurity) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * @param participant
     * @return user-friendly path to participant.
     */
    private String getParticipantPath(Participant participant) {
        String path = null;
        if (participant.eContainer() instanceof Process) {
            Process process = (Process) participant.eContainer();

            IFile xpdl = WorkingCopyUtil.getFile(participant);
            if (xpdl != null) {
                path = xpdl.getName() + "/" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayNameOrName(process) + "/" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayNameOrName(participant);
            }

        } else if (participant.eContainer() instanceof Package) {

            IFile xpdl = WorkingCopyUtil.getFile(participant);
            if (xpdl != null) {
                path = xpdl.getName() + "/" //$NON-NLS-1$
                        + Xpdl2ModelUtil.getDisplayNameOrName(participant);
            }
        }

        if (path == null) {
            path = ""; //$NON-NLS-1$
        }
        return path;
    }

}
