/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Rule to check if there are more than one provider participant(s) referring to
 * same port type with same endpoint url across different xpdls in a given
 * project (and its referenced projects). (see XPD-5009 for more details)
 * 
 * @author bharge
 * @since 25 Jun 2013
 */
public class WSParticEndpointRule extends PackageValidationRule {

    /**
     * 
     * 
     * @author bharge
     * @since 26 Jun 2013
     */
    private class ProviderParticipantInfo {

        private String endpointUrl;

        private Participant participant;

        private ActivityWebServiceReference webServiceRef;

        /**
         * @return the webServiceRef
         */
        public ActivityWebServiceReference getWebServiceRef() {
            return webServiceRef;
        }

        /**
         * @return the participant
         */
        public Participant getParticipant() {
            return participant;
        }

        /**
         * @param participant
         *            the participant to set
         */
        public void setParticipant(Participant participant) {
            this.participant = participant;
        }

        public ProviderParticipantInfo(ActivityWebServiceReference webServiceRef) {
            super();
            this.webServiceRef = webServiceRef;
        }

        /**
         * @return the endpointUrl
         */
        public String getEndpointUrl() {
            return endpointUrl;
        }

        /**
         * @param endpointUrl
         *            the endpointUrl to set
         */
        public void setEndpointUrl(String endpointUrl) {
            this.endpointUrl = endpointUrl;
        }

    }

    /** "Participants used for process as service referring same port type across different xpdl(s) in a given project must specify unique shared resource URI" */
    private static final String SAME_ENDPOINT_URI_ERROR =
            "bx.sameSharedResEndpointUri.error"; //$NON-NLS-1$

    /** "Participants used for process as service referring same port type across different xpdl(s) in a different project must specify unique shared resource URI" */
    private static final String SAME_ENDPOINT_URI_WARNING =
            "bx.sameSharedResEndpointUri.warning"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     * 
     * @param pckg
     */
    @Override
    public void validate(Package pckg) {

        IProject project = WorkingCopyUtil.getProjectFor(pckg);
        IFile xpdlFile = WorkingCopyUtil.getFile(pckg);

        if (null != project && null != xpdlFile) {

            /* get the provider participant info for the given process package */
            Set<ProviderParticipantInfo> providerParticInfoList =
                    getProviderParticInfoForAllPackages(xpdlFile);
            /* get the provider participant info for all other process packages */
            Set<ProviderParticipantInfo> providerParticInfoListForAllOtherPkgs =
                    getProviderParticInfoForAllPackages(null);
            /*
             * iterate thru the list and complain if a conflict in same endpoint
             * url is found
             */
            for (ProviderParticipantInfo thisPkgParticipantInfo : providerParticInfoList) {

                if (null != thisPkgParticipantInfo.getParticipant()) {

                    Set<String> xpdlFilePathAndParticNameSet =
                            new HashSet<String>();
                    StringBuffer linkedResources = new StringBuffer();
                    boolean isError = false;
                    for (ProviderParticipantInfo otherPkgParticInfo : providerParticInfoListForAllOtherPkgs) {

                        /* dont worry if you come across the same xpdl */
                        if (!thisPkgParticipantInfo
                                .getWebServiceRef()
                                .getXpdlPath()
                                .equals(otherPkgParticInfo.getWebServiceRef()
                                        .getXpdlPath())) {

                            boolean endpointUrlMatch =
                                    isEndpointUrlMatch(thisPkgParticipantInfo,
                                            otherPkgParticInfo);
                            if (endpointUrlMatch) {

                                boolean portTypesMatch =
                                        isPortTypesMatch(thisPkgParticipantInfo,
                                                otherPkgParticInfo);
                                boolean namespaceMatch =
                                        isNamespaceMatch(thisPkgParticipantInfo,
                                                otherPkgParticInfo);
                                if (portTypesMatch && namespaceMatch) {

                                    IProject otherProject =
                                            WorkingCopyUtil
                                                    .getProjectFor(otherPkgParticInfo
                                                            .getParticipant());

                                    if (!isError
                                            && otherProject.equals(project)) {
                                        isError = true;
                                    }

                                    xpdlFilePathAndParticNameSet
                                            .add(otherPkgParticInfo
                                                    .getWebServiceRef()
                                                    .getXpdlPath()
                                                    + "/" + otherPkgParticInfo.getWebServiceRef().getParticipantName()); //$NON-NLS-1$

                                    if (linkedResources.length() > 0) {
                                        linkedResources.append(","); //$NON-NLS-1$
                                    }
                                    linkedResources.append(EcoreUtil
                                            .getURI(otherPkgParticInfo
                                                    .getParticipant())
                                            .toString());
                                }
                            }
                        }
                    }

                    /* need to show all conflicting xpdl file names */
                    StringBuffer filePathBuf = new StringBuffer();
                    /*
                     * need to check this size, otherwise since isError by
                     * default is false, shows a warning on every project that
                     * has a processs api participant even if there is no
                     * conflict
                     */
                    if (xpdlFilePathAndParticNameSet.size() > 0) {

                        for (String filePathAndParticName : xpdlFilePathAndParticNameSet) {

                            if (filePathBuf.length() > 0) {

                                filePathBuf.append(","); //$NON-NLS-1$
                            }
                            filePathBuf.append(filePathAndParticName);
                        }
                        List<String> msgs = new ArrayList<String>();
                        msgs.add(thisPkgParticipantInfo.getEndpointUrl());
                        msgs.add(filePathBuf.toString());

                        addIssue(isError ? SAME_ENDPOINT_URI_ERROR
                                : SAME_ENDPOINT_URI_WARNING,
                                thisPkgParticipantInfo.getParticipant(),
                                msgs,
                                Collections
                                        .singletonMap(ValidationActivator.LINKED_RESOURCE,
                                                linkedResources.toString()));
                    }
                }
            }
        }

    }

    /**
     * @param thisPkgParticipantInfo
     * @param otherPkgParticInfo
     * @return
     */
    private boolean isEndpointUrlMatch(
            ProviderParticipantInfo thisPkgParticipantInfo,
            ProviderParticipantInfo otherPkgParticInfo) {

        if (null != thisPkgParticipantInfo.getEndpointUrl()
                && thisPkgParticipantInfo.getEndpointUrl()
                        .equals(otherPkgParticInfo.getEndpointUrl())) {

            return true;
        }
        return false;

    }

    /**
     * @param thisPkgParticipantInfo
     * @param otherPkgParticInfo
     * @return
     */
    private boolean isNamespaceMatch(
            ProviderParticipantInfo thisPkgParticipantInfo,
            ProviderParticipantInfo otherPkgParticInfo) {

        if (thisPkgParticipantInfo
                .getWebServiceRef()
                .getPortTypeNamespace()
                .equals(otherPkgParticInfo.getWebServiceRef()
                        .getPortTypeNamespace())) {

            return true;
        }
        return false;
    }

    /**
     * @param thisPkgParticipantInfo
     * @param otherPkgParticInfo
     */
    private boolean isPortTypesMatch(
            ProviderParticipantInfo thisPkgParticipantInfo,
            ProviderParticipantInfo otherPkgParticInfo) {

        if (thisPkgParticipantInfo
                .getWebServiceRef()
                .getPortTypeName()
                .equals(otherPkgParticInfo.getWebServiceRef().getPortTypeName())) {

            return true;
        }
        return false;
    }

    /**
     * 
     * @param xpdlFile
     *            - if not null then provider participant info for this package
     *            will be fetched; if null then provider participant info for
     *            all the packages will be fetched
     * @return set of {@link ProviderParticipantInfo}
     */
    private Set<ProviderParticipantInfo> getProviderParticInfoForAllPackages(
            IFile xpdlFile) {

        Set<ProviderParticipantInfo> providerParticInfoSet =
                new HashSet<WSParticEndpointRule.ProviderParticipantInfo>();
        Collection<ActivityWebServiceReference> webServiceReferences =
                ProcessUIUtil.getActivityWebServiceReferencesForApiActivity();

        if (null != xpdlFile) {

            Collection<ActivityWebServiceReference> webServiceReferencesForThisPkg =
                    new ArrayList<ActivityWebServiceReference>();
            for (ActivityWebServiceReference webServiceRef : webServiceReferences) {

                if (xpdlFile.getFullPath().toString()
                        .equals(webServiceRef.getXpdlPath())) {

                    webServiceReferencesForThisPkg.add(webServiceRef);

                }
            }
            providerParticInfoSet =
                    getProviderParticInfo(webServiceReferencesForThisPkg);
        } else {
            providerParticInfoSet = getProviderParticInfo(webServiceReferences);
        }

        return providerParticInfoSet;
    }

    /**
     * @param webServiceReferences
     */
    private Set<ProviderParticipantInfo> getProviderParticInfo(
            Collection<ActivityWebServiceReference> webServiceReferences) {

        Set<ProviderParticipantInfo> providerParticInfoList =
                new HashSet<ProviderParticipantInfo>();

        for (ActivityWebServiceReference webServiceReference : webServiceReferences) {
            if (webServiceReference.getIsApiActivity()) {

                ProviderParticipantInfo providerParticInfo =
                        new ProviderParticipantInfo(webServiceReference);

                URI uri = URI.createURI(webServiceReference.getActivityUri());
                EObject obj = ProcessUIUtil.getEObjectFrom(uri);
                if (obj instanceof Activity) {

                    Activity activity = (Activity) obj;
                    Participant participant =
                            Xpdl2ModelUtil
                                    .getEndPointAliasParticipant(activity);
                    providerParticInfo.setParticipant(participant);

                    ParticipantSharedResource particSharedResource =
                            (ParticipantSharedResource) Xpdl2ModelUtil
                                    .getOtherElement(participant,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());
                    if (null != particSharedResource) {

                        String endpointUrlPath =
                                getParticSharedResEndpointUrlPath(particSharedResource);
                        providerParticInfo.setEndpointUrl(endpointUrlPath);
                    }
                }
                providerParticInfoList.add(providerParticInfo);
            }
        }
        return providerParticInfoList;
    }

    /**
     * 
     * @param participantSharedRes
     * @return participant shared resource endpoint url, <empty string>
     *         otherwise
     */
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

}
