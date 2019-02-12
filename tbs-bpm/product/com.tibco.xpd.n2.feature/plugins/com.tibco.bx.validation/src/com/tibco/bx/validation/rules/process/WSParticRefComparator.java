/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class WSParticRefComparator {

    // copied the following patterns from SDS
    // http://svn.tibco.com/amf/trunk/amf/binding/soap/design/plugins/com.tibco.amf.binding.soap.ui/src/com/tibco/amf/binding/soap/ui/internal/validation/InvalidEndpointURIConstraint.java
    // Pattern for invalid character
    private static final Pattern pattern = Pattern
            .compile("^(/?[\\w\\d\\s:\\$&=\\+,@%\\-_\\.!~\\*\\|'']+)+/?$"); //$NON-NLS-1$

    // Pattern for checking hexadecimal character after % should preceed two
    // hexadecimal character.
    private static final Pattern uriPattern =
            Pattern.compile("[^%]*(%[0-9a-fA-F][0-9a-fA-F][^%]*)*[^%]*"); //$NON-NLS-1$

    /**
     * @param project
     * @param participant
     * @param otherParticipant
     * @return
     */
    public boolean areSamePortTypeAndNameSpace(IProject project,
            Participant participant, Participant otherParticipant) {

        List<ActivityWebServiceReference> webServiceRef =
                getWebServiceReferencesById(project, participant.getId());

        List<ActivityWebServiceReference> otherWebServiceRef =
                getWebServiceReferencesById(project, otherParticipant.getId());

        if (!webServiceRef.isEmpty() && !otherWebServiceRef.isEmpty()) {

            ActivityWebServiceReference ref = webServiceRef.get(0);
            ActivityWebServiceReference otherRef = otherWebServiceRef.get(0);

            if (ref.getWsdlNamespace().equals(otherRef.getWsdlNamespace())) {
                if (notNullAndHasValue(ref.getPortName(),
                        otherRef.getPortName())
                        && ref.getPortName().equals(otherRef.getPortName())) {
                    return true;
                } else if (notNullAndHasValue(ref.getPortTypeName(),
                        otherRef.getPortTypeName())
                        && ref.getPortTypeName()
                                .equals(otherRef.getPortTypeName())) {
                    return true;
                }
            }

        }

        return false;
    }

    /**
     * Check that all uses of this participant (or any other with the same name)
     * are only used for a single port type and transport type.
     * 
     * @param participant
     * @param targetProject
     */
    public ComparatorIssueInfo compareParticipantWebServiceReferences(
            Participant participant, IProject targetProject) {

        /*
         * Rather than raise a whole plethora of problems for when participants
         * are used inconsistently (i.e. a!=b and a!=c and b!=a and b!=c and
         * c!=a and c!=b etc) we will choose the first usage of the participant
         * in a web-service activity with an operation selected as the
         * "nominal port type that this participant is associated with" and then
         * complain only about the first mismatch in it's use.
         * 
         * Therefore we will get only (a!=b and b!=a and c!=a) which is easier
         * for the user to cope with - having fixed the first problem occurrence
         * they will be told about the next.
         */

        ComparatorIssueInfo issueInfo = new ComparatorIssueInfo();

        String participantId = participant.getId();
        ActivityWSParticConfig actWSParticConfig =
                this.new ActivityWSParticConfig();

        List<ActivityWebServiceReference> webServiceReferences =
                getWebServiceReferencesById(targetProject, participantId);

        setNominalActWSParticConfig(participantId,
                actWSParticConfig,
                webServiceReferences);

        if (isValidPortOrPortType(actWSParticConfig)) {

            for (ActivityWebServiceReference ref : webServiceReferences) {

                ActivityWSParticConfig otherActWSParticConfig =
                        this.new ActivityWSParticConfig();

                boolean samePartic =
                        participantId.equals(ref.getParticipantId());

                setOtherActWSParticConfig(ref,
                        otherActWSParticConfig,
                        samePartic);

                /* either name spaces or port types match */
                if (isPortOrPortTypeAndNameSpaceMisMatch(actWSParticConfig,
                        otherActWSParticConfig)) {
                    issueInfo = getPortTypeRelatedIssues(actWSParticConfig,
                            otherActWSParticConfig,
                            samePartic,
                            issueInfo);
                } else {
                    issueInfo = getTransportTypeRelatedIssues(actWSParticConfig,
                            otherActWSParticConfig,
                            samePartic,
                            issueInfo);
                }
                if (null != issueInfo.getIssueId()) {
                    return issueInfo;
                }

                /*
                 * Likewise we'll only check the web-service participant
                 * SharedResource configuration if it's used consistently for
                 * port and transport (otherwise it'll need to mutually
                 * exclusive types of setup).
                 */
                if (samePartic) {
                    issueInfo = checkSharedResourceIssues(participant);
                    if (null != issueInfo.getIssueId()) {
                        return issueInfo;
                    }
                }
            }
        }

        return issueInfo;
    }

    /**
     * @param participant
     * @param invokeType
     * @return
     */
    private ComparatorIssueInfo checkSharedResourceIssues(
            Participant participant) {

        ComparatorIssueInfo issueInfo = new ComparatorIssueInfo();

        ParticipantSharedResource particSharedResource =
                (ParticipantSharedResource) Xpdl2ModelUtil.getOtherElement(
                        participant,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ParticipantSharedResource());
        if (null == particSharedResource
                || null == particSharedResource.getWebService()) {
            /* No sharedResourc e->WebService type at all. */
            issueInfo.setIssueId(
                    WSRefParticErrors.ISSUE_WEBACT_PARTIC_MUSTBE_SHAREDRES_WEBSVC
                            .getValue());
            return issueInfo;

        }

        return issueInfo;
    }

    /**
     * @param actWSParticConfig
     * @param otherActWSParticConfig
     * @param samePartic
     * @param issueInfo
     * @return
     */
    private ComparatorIssueInfo getTransportTypeRelatedIssues(
            ActivityWSParticConfig actWSParticConfig,
            ActivityWSParticConfig otherActWSParticConfig, boolean samePartic,
            ComparatorIssueInfo issueInfo) {

        /*
         * XPD-5005: Transport validation should only be on concrete service
         * call.
         */
        if (actWSParticConfig.getPortName() != null
                && !actWSParticConfig.getPortName().isEmpty()
                && otherActWSParticConfig.getPortName() != null
                && !otherActWSParticConfig.getPortName().isEmpty()) {

            if (!actWSParticConfig.getTransportType()
                    .equals(otherActWSParticConfig.getTransportType())) {
                if (samePartic) {
                    issueInfo.setActivityUri1(
                            actWSParticConfig.getActivityUri());
                    issueInfo.setActivityUri2(
                            otherActWSParticConfig.getActivityUri());
                    issueInfo.setIssueId(
                            WSRefParticErrors.ISSUE_SAME_PARTIC_DIFF_TRANSPORT
                                    .getValue());
                    return issueInfo;
                } else {
                    issueInfo.setActivityUri1(
                            actWSParticConfig.getActivityUri());
                    issueInfo.setActivityUri2(
                            otherActWSParticConfig.getActivityUri());
                    issueInfo.setIssueId(
                            WSRefParticErrors.ISSUE_SAMENAME_PARTICS_DIFF_TRANSPORT
                                    .getValue());
                    return issueInfo;
                }
            }
        }
        return issueInfo;
    }

    /**
     * @param actWSParticConfig
     * @param otherActWSParticConfig
     * @param samePartic
     * @param issueInfo
     * @return
     */
    private ComparatorIssueInfo getPortTypeRelatedIssues(
            ActivityWSParticConfig actWSParticConfig,
            ActivityWSParticConfig otherActWSParticConfig, boolean samePartic,
            ComparatorIssueInfo issueInfo) {
        /* if participant ids are same then check for port types */
        if (samePartic) {
            issueInfo.setActivityUri1(actWSParticConfig.getActivityUri());
            issueInfo.setActivityUri2(otherActWSParticConfig.getActivityUri());
            issueInfo.setIssueId(
                    WSRefParticErrors.ISSUE_SAME_PARTIC_DIFF_PORT.getValue());
            return issueInfo;

        } else {
            /*
             * participant ids are not same that means, participant names are
             * same, so check for port types of same name participants
             * 
             * if it is not same participant, then check if it belongs to the
             * same xpdl and uses the same port type
             * 
             * Participant names matches Participant id is different Both are
             * not pointing to the same port type
             */

            if (actWSParticConfig.getXpdlPath()
                    .equals(otherActWSParticConfig.getXpdlPath())) {
                if (notNullAndHasValue(actWSParticConfig.getPortName(),
                        otherActWSParticConfig.getPortName())) {
                    if (!actWSParticConfig.getPortName()
                            .equals(otherActWSParticConfig.getPortName())) {
                        issueInfo.setActivityUri1(
                                actWSParticConfig.getActivityUri());
                        issueInfo.setActivityUri2(
                                otherActWSParticConfig.getActivityUri());
                        issueInfo.setIssueId(
                                WSRefParticErrors.ISSUE_SAMENAME_PARTICS_DIFF_PORT
                                        .getValue());
                        return issueInfo;
                    }
                }
                if (notNullAndHasValue(actWSParticConfig.getPortTypeName(),
                        otherActWSParticConfig.getPortTypeName())) {
                    if (!actWSParticConfig.getPortTypeName()
                            .equals(otherActWSParticConfig.getPortTypeName())) {
                        issueInfo.setActivityUri1(
                                actWSParticConfig.getActivityUri());
                        issueInfo.setActivityUri2(
                                otherActWSParticConfig.getActivityUri());
                        issueInfo.setIssueId(
                                WSRefParticErrors.ISSUE_SAMENAME_PARTICS_DIFF_PORT
                                        .getValue());
                        return issueInfo;
                    }
                }
            }

        }
        return issueInfo;
    }

    /**
     * @param actWSParticConfig
     * @param otherActWSParticConfig
     * @return
     */
    private boolean isPortOrPortTypeAndNameSpaceMisMatch(
            ActivityWSParticConfig actWSParticConfig,
            ActivityWSParticConfig otherActWSParticConfig) {

        if (notNullAndHasValue(actWSParticConfig.getPortName(),
                otherActWSParticConfig.getPortName())) {
            return !actWSParticConfig.getWsdlNamespace()
                    .equals(otherActWSParticConfig.getWsdlNamespace())
                    || !actWSParticConfig.getPortName()
                            .equals(otherActWSParticConfig.getPortName());
        }

        if (notNullAndHasValue(actWSParticConfig.getPortTypeName(),
                otherActWSParticConfig.getPortTypeName())) {
            return !actWSParticConfig.getWsdlNamespace()
                    .equals(otherActWSParticConfig.getWsdlNamespace())
                    || !actWSParticConfig.getPortTypeName()
                            .equals(otherActWSParticConfig.getPortTypeName());
        }
        return false;
    }

    /**
     * @param ref
     * @param otherActWSParticConfig
     * @param samePartic
     */
    private void setOtherActWSParticConfig(ActivityWebServiceReference ref,
            ActivityWSParticConfig otherActWSParticConfig, boolean samePartic) {
        if (samePartic) {
            otherActWSParticConfig.setSameParticipant(true);
        }

        if (null != ref.getPortName() && ref.getPortName().length() > 0) {
            otherActWSParticConfig.setPortName(ref.getPortName());
        } else {
            otherActWSParticConfig.setPortTypeName(ref.getPortTypeName());
        }

        otherActWSParticConfig.setTransportType(ref.getTransportType());
        otherActWSParticConfig.setWsdlNamespace(ref.getWsdlNamespace());
        otherActWSParticConfig.setActivityUri(ref.getActivityUri());
        otherActWSParticConfig.setXpdlPath(ref.getXpdlPath());
    }

    /**
     * @param actWSParticConfig
     * @return
     */
    private boolean isValidPortOrPortType(
            ActivityWSParticConfig actWSParticConfig) {
        return (null != actWSParticConfig.getPortName()
                && actWSParticConfig.getPortName().length() > 0)
                || (null != actWSParticConfig.getPortTypeName()
                        && actWSParticConfig.getPortTypeName().length() > 0);
    }

    /**
     * @param participantId
     * @param actWSParticConfig
     * @param webServiceReferences
     */
    private void setNominalActWSParticConfig(String participantId,
            ActivityWSParticConfig actWSParticConfig,
            List<ActivityWebServiceReference> webServiceReferences) {

        for (ActivityWebServiceReference ref : webServiceReferences) {
            /*
             * We want to compare all with the first occurence of use of the
             * partic we're checking that has port type set.
             */
            if (participantId.equals(ref.getParticipantId())) {
                if (null != ref.getPortName()
                        && ref.getPortName().length() > 0) {
                    actWSParticConfig.setPortName(ref.getPortName());
                } else {
                    actWSParticConfig.setPortTypeName(ref.getPortTypeName());
                }

                actWSParticConfig.setXpdlPath(ref.getXpdlPath());
                actWSParticConfig.setTransportType(ref.getTransportType());
                actWSParticConfig.setWsdlNamespace(ref.getWsdlNamespace());
                actWSParticConfig.setActivityUri(ref.getActivityUri());
                break;
            }
        }
    }

    /**
     * @param portName1
     * @param portName2
     * @return
     */
    private boolean notNullAndHasValue(String portName1, String portName2) {
        return null != portName1 && portName1.length() > 0 && null != portName2
                && portName2.length() > 0;
    }

    /**
     * @param project
     * @param ignorePackagePath
     * 
     * @return List of web service references in the given project for the given
     *         participant name.
     */
    private List<ActivityWebServiceReference> getWebServiceReferencesById(
            IProject project, String participantId) {
        List<ActivityWebServiceReference> webSvcRefs =
                new ArrayList<ActivityWebServiceReference>();

        Map<String, String> additionalAttributes =
                new HashMap<String, String>();

        additionalAttributes.put(
                ProcessUIUtil.WEBSERVICE_REF_COLUMN_ENDPOINT_PARTICIPANT_ID,
                participantId);

        IndexerItem criteria = new IndexerItemImpl(null,
                ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_TYPE, null,
                additionalAttributes);

        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService().query(
                        ProcessUIUtil.ACTIVITY_WEBSERVICE_REF_INDEX_ID,
                        criteria);

        if (result != null && !result.isEmpty()) {
            for (IndexerItem item : result) {
                ActivityWebServiceReference ref = ActivityWebServiceReference
                        .createActivityWebServiceReference(item);
                if (ref != null) {
                    webSvcRefs.add(ref);
                }
            }
        }

        return webSvcRefs;
    }

    /**
     * @author bharge
     * 
     */
    private class ActivityWSParticConfig {

        private String transportType;

        private String wsdlNamespace;

        private String activityUri;

        private String portName;

        private String portTypeName;

        private boolean sameParticDiffPort;

        private boolean sameNameParticDiffPort;

        private String xpdlPath;

        private boolean sameParticipant;

        /**
         * @return the transportType
         */
        public String getTransportType() {
            return transportType;
        }

        /**
         * @param transportType
         *            the transportType to set
         */
        public void setTransportType(String transportType) {
            this.transportType = transportType;
        }

        /**
         * @return the wsdlNamespace
         */
        public String getWsdlNamespace() {
            return wsdlNamespace;
        }

        /**
         * @param wsdlNamespace
         *            the wsdlNamespace to set
         */
        public void setWsdlNamespace(String wsdlNamespace) {
            this.wsdlNamespace = wsdlNamespace;
        }

        /**
         * @return the activityUri
         */
        public String getActivityUri() {
            return activityUri;
        }

        /**
         * @param activityUri
         *            the activityUri to set
         */
        public void setActivityUri(String activityUri) {
            this.activityUri = activityUri;
        }

        /**
         * @return the portName
         */
        public String getPortName() {
            return portName;
        }

        /**
         * @param portName
         *            the portName to set
         */
        public void setPortName(String portName) {
            this.portName = portName;
        }

        /**
         * @return the portTypeName
         */
        public String getPortTypeName() {
            return portTypeName;
        }

        /**
         * @param portTypeName
         *            the portTypeName to set
         */
        public void setPortTypeName(String portTypeName) {
            this.portTypeName = portTypeName;
        }

        /**
         * @return the sameParticDiffPort
         */
        public boolean isSameParticDiffPort() {
            return sameParticDiffPort;
        }

        /**
         * @param sameParticDiffPort
         *            the sameParticDiffPort to set
         */
        public void setSameParticDiffPort(boolean sameParticDiffPort) {
            this.sameParticDiffPort = sameParticDiffPort;
        }

        /**
         * @return the sameNameParticDiffPort
         */
        public boolean isSameNameParticDiffPort() {
            return sameNameParticDiffPort;
        }

        /**
         * @param sameNameParticDiffPort
         *            the sameNameParticDiffPort to set
         */
        public void setSameNameParticDiffPort(boolean sameNameParticDiffPort) {
            this.sameNameParticDiffPort = sameNameParticDiffPort;
        }

        /**
         * @return the xpdlPath
         */
        public String getXpdlPath() {
            return xpdlPath;
        }

        /**
         * @param xpdlPath
         *            the xpdlPath to set
         */
        public void setXpdlPath(String xpdlPath) {
            this.xpdlPath = xpdlPath;
        }

        /**
         * @return the sameParticipant
         */
        public boolean isSameParticipant() {
            return sameParticipant;
        }

        /**
         * @param sameParticipant
         *            the sameParticipant to set
         */
        public void setSameParticipant(boolean sameParticipant) {
            this.sameParticipant = sameParticipant;
        }

    }

    /**
     * @author bharge
     * 
     */
    protected class ComparatorIssueInfo {
        private String issueId;

        private String activityUri1;

        private String activityUri2;

        /**
         * @return the issueId
         */
        public String getIssueId() {
            return issueId;
        }

        /**
         * @param issueId
         *            the issueId to set
         */
        public void setIssueId(String issueId) {
            this.issueId = issueId;
        }

        /**
         * @return the activityUri1
         */
        public String getActivityUri1() {
            return activityUri1;
        }

        /**
         * @param activityUri1
         *            the activityUri1 to set
         */
        public void setActivityUri1(String activityUri1) {
            this.activityUri1 = activityUri1;
        }

        /**
         * @return the activityUri2
         */
        public String getActivityUri2() {
            return activityUri2;
        }

        /**
         * @param activityUri2
         *            the activityUri2 to set
         */
        public void setActivityUri2(String activityUri2) {
            this.activityUri2 = activityUri2;
        }

    }

}
