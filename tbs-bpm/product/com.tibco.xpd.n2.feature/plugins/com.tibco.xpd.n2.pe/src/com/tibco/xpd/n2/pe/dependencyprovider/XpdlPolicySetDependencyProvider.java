/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.pe.dependencyprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.resources.IWorkingCopyDependencyProvider;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapHttpOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Xpdl2 working copies dependency provider to Policy set files.
 * 
 * @author sajain
 * @since Jul 31, 2015
 */
public class XpdlPolicySetDependencyProvider implements
        IWorkingCopyDependencyProvider {

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass()
     * 
     * @return
     */
    public Class<? extends WorkingCopy> getWorkingCopyClass() {

        return Xpdl2WorkingCopyImpl.class;
    }

    /**
     * @see com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getDependencies(com.tibco.xpd.resources.WorkingCopy)
     * 
     * @param wc
     * @return
     */
    public Collection<IResource> getDependencies(WorkingCopy wc) {
        Set<IResource> resources = new HashSet<IResource>();

        /*
         * Check if the working copy exists and is valid.
         */
        if (wc != null && wc.isExist() && !wc.isInvalidFile()) {

            EObject element = wc.getRootElement();

            if (element instanceof Package) {

                /*
                 * Get package.
                 */
                Package pkg = (Package) element;

                /*
                 * Form a list of all participants in the package and the
                 * processes inside.
                 */
                List<Participant> allParticipants =
                        new ArrayList<Participant>();

                allParticipants.addAll(pkg.getParticipants());

                for (Process eachProcess : pkg.getProcesses()) {

                    allParticipants.addAll(eachProcess.getParticipants());
                }

                /*
                 * Traverse through each participant.
                 */
                if (!allParticipants.isEmpty()) {

                    for (Participant eachParticipant : allParticipants) {

                        ParticipantSharedResource particSharedResource =
                                (ParticipantSharedResource) Xpdl2ModelUtil
                                        .getOtherElement(eachParticipant,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ParticipantSharedResource());

                        /*
                         * Check if the participant has a participant shared
                         * resource.
                         */
                        if (null != particSharedResource) {

                            /*
                             * Check for Web service consumer SOAP/HTTP
                             * participants.
                             */
                            if (null != particSharedResource.getWebService()) {

                                WsOutbound wsOutbound =
                                        particSharedResource.getWebService()
                                                .getOutbound();

                                WsSoapSecurity outboundSecurity = null;

                                if (null != wsOutbound) {

                                    if (null != wsOutbound.getSoapHttpBinding()) {

                                        WsSoapHttpOutboundBinding soapHttpBinding =
                                                wsOutbound.getSoapHttpBinding();

                                        outboundSecurity =
                                                soapHttpBinding
                                                        .getOutboundSecurity();

                                    } else if (null != wsOutbound
                                            .getSoapJmsBinding()) {

                                        WsSoapJmsOutboundBinding soapJmsBinding =
                                                wsOutbound.getSoapJmsBinding();

                                        outboundSecurity =
                                                soapJmsBinding
                                                        .getOutboundSecurity();
                                    }
                                }

                                if (null != outboundSecurity
                                        && null != outboundSecurity
                                                .getSecurityPolicy()) {

                                    EList<WsSecurityPolicy> securityPolicy =
                                            outboundSecurity
                                                    .getSecurityPolicy();

                                    for (WsSecurityPolicy wsSecurityPolicy : securityPolicy) {

                                        SecurityPolicy type =
                                                wsSecurityPolicy.getType();

                                        if (Arrays
                                                .asList(SecurityPolicy.CUSTOM)
                                                .contains(type)) {

                                            PolicySetReference ref =
                                                    PolicySetReference
                                                            .getPolicySetReference(wsSecurityPolicy);

                                            if (null != ref.getUri()) {

                                                resources
                                                        .add(getIFileFromURI(ref
                                                                .getUri()
                                                                .toString()));
                                            }
                                        }
                                    }
                                }

                            } else if (null != particSharedResource
                                    .getRestService()) {

                                /*
                                 * Check for REST service participants.
                                 */

                                RestServiceResource restSvcRes =
                                        particSharedResource.getRestService();

                                RestServiceResourceSecurity security = null;

                                EList<RestServiceResourceSecurity> policies =
                                        restSvcRes.getSecurityPolicy();

                                if (!policies.isEmpty()) {

                                    security = policies.get(0);
                                }

                                if (security != null) {

                                    PolicySetReference policySetRef =
                                            PolicySetReference
                                                    .getPolicySetReference(security);

                                    if (policySetRef != null) {

                                        SecurityPolicy type =
                                                security.getPolicyType();

                                        if (Arrays
                                                .asList(SecurityPolicy.CUSTOM)
                                                .contains(type)) {

                                            PolicySetReference ref =
                                                    PolicySetReference
                                                            .getPolicySetReference(security);

                                            if (null != ref.getUri()) {

                                                resources
                                                        .add(getIFileFromURI(ref
                                                                .getUri()
                                                                .toString()));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return resources;
    }

    /**
     * Get/create an IFile from the URI provided.
     * 
     * @param uriString
     * 
     * @return IFile from the URI provided.
     */
    private static IFile getIFileFromURI(String uriString) {

        if (uriString == null || uriString.trim().isEmpty()) {
            return null;
        }

        URI uri = URI.createURI(uriString);

        if (uri != null) {

            uri = uri.trimFragment();

            String platformString = uri.toPlatformString(true);

            if (platformString != null) {

                IResource resource =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(platformString));

                if (resource instanceof IFile) {

                    return (IFile) resource;

                }
            }
        }
        return null;
    }
}
