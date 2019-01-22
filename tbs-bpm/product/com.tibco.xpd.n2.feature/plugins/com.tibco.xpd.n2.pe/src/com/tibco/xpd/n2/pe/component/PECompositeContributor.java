/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.pe.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Output;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.amf.sca.componenttype.ComponentTypeActivator;
import com.tibco.amf.sca.componenttype.CompositeModelBuilder;
import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.composite.Component;
import com.tibco.amf.sca.model.composite.ComponentProperty;
import com.tibco.amf.sca.model.composite.ComponentReference;
import com.tibco.amf.sca.model.composite.ComponentService;
import com.tibco.amf.sca.model.composite.Composite;
import com.tibco.amf.sca.model.composite.CompositeFactory;
import com.tibco.amf.sca.model.composite.PromotedReference;
import com.tibco.amf.sca.model.composite.PromotedService;
import com.tibco.amf.sca.model.composite.Wire;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.amf.sca.model.extensionpoints.PolicySet;
import com.tibco.amf.sca.model.extensionpoints.Property;
import com.tibco.amf.sca.model.extensionpoints.Wsdl11Interface;
import com.tibco.amf.sca.model.externalpolicy.ExternalPolicySetContainerDocument;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.xpd.analyst.resources.xpdl2.utils.SharedResourceUtil;
import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.internal.IChangeRecorder;
import com.tibco.xpd.n2.bpel.builder.BPELOnDemandBuilder;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.daa.Activator;
import com.tibco.xpd.n2.daa.utils.N2PECompositeUtil;
import com.tibco.xpd.n2.daa.utils.N2PENamingUtils;
import com.tibco.xpd.n2.daa.utils.PolicySetUtil;
import com.tibco.xpd.n2.daa.utils.UnprotectedWriteOperation;
import com.tibco.xpd.n2.pe.Messages;
import com.tibco.xpd.n2.pe.PEActivator;
import com.tibco.xpd.n2.pe.util.PEN2Utils;
import com.tibco.xpd.n2.pe.util.SoapBindingUtil;
import com.tibco.xpd.processeditor.xpdl2.util.PolicySetReference;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.SubProgressMonitorEx;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.BpmRuntimeConfiguration;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.RestServiceResourceSecurity;
import com.tibco.xpd.xpdExtension.SecurityPolicy;
import com.tibco.xpd.xpdExtension.WsOutbound;
import com.tibco.xpd.xpdExtension.WsSecurityPolicy;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.XpdlSearchUtil;

/**
 * @author kupadhya
 * 
 */
public class PECompositeContributor extends CompositeContributor {

    /** */
    private static final String PLATFORM_PLUGIN_RES_URI_PREFIX =
            "platform:/plugin/" + PEActivator.PLUGIN_ID //$NON-NLS-1$
                    + "/resources/"; //$NON-NLS-1$

    @Override
    public IStatus contributeToComposite(final IProject project,
            final IContainer stagingArea, final Composite composite,
            final URI compositeLocation, final String timeStamp,
            final boolean replaceWithTS, final IChangeRecorder changeRecorder,
            IProgressMonitor monitor) {

        try {

            List<IResource> xpdlResources =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    N2PENamingUtils.PROCESS_SPECIALFOLDER_KIND,
                                    N2PENamingUtils.XPDL_FILE_EXTENSION,
                                    false);

            monitor.beginTask(Messages.PECompositeContributor_AddRuntimeProcesses_message,
                    xpdlResources.size() + 2);

            cleanPeCompositeErrorMarkers(project);
            copyBpelFiles(stagingArea, project);

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }
            monitor.worked(1);

            TransactionalEditingDomain editingDomain =
                    TransactionUtil.getEditingDomain(composite);
            final ComponentTypeActivator ctActivator =
                    ComponentTypeActivator.getDefault();

            if (xpdlResources != null && !xpdlResources.isEmpty()) {
                final List<Component> createdComponents =
                        new ArrayList<Component>();

                for (final IResource resource : xpdlResources) {
                    if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                    }

                    addBpelForXpdlResource(resource,
                            composite,
                            compositeLocation,
                            editingDomain,
                            ctActivator,
                            createdComponents,
                            SubProgressMonitorEx
                                    .createSubTaskProgressMonitor(monitor, 1));
                }

                UnprotectedWriteOperation promoteComponentsOp =
                        new UnprotectedWriteOperation(editingDomain) {
                            @Override
                            protected Object doExecute() {
                                for (Component component : createdComponents) {
                                    promoteComponent(component,
                                            project,
                                            timeStamp);
                                }
                                return Status.OK_STATUS;
                            }
                        };
                promoteComponentsOp.execute();

                if (monitor.isCanceled()) {
                    return Status.CANCEL_STATUS;
                }
                monitor.worked(1);

            }
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * @param resource
     * @param composite
     * @param compositeLocation
     * @param editingDomain
     * @param ctActivator
     * @param createdComponents
     * @param monitor
     */
    private void addBpelForXpdlResource(final IResource resource,
            final Composite composite, final URI compositeLocation,
            TransactionalEditingDomain editingDomain,
            final ComponentTypeActivator ctActivator,
            final List<Component> createdComponents, IProgressMonitor monitor) {

        try {
            monitor.beginTask(resource.getName(), 1);

            if (resource instanceof IFile && resource.exists()) {

                IFile xpdlFile = (IFile) resource;
                long beforeTimeMillis = System.currentTimeMillis();
                IFile[] bpelFilesList = getBpelFiles(xpdlFile);
                if (bpelFilesList.length > 0) {
                    final String componentName = getComponentName(resource);
                    final CompositeModelBuilder modelBuilder =
                            ComponentTypeActivator.getDefault()
                                    .getModelBuilder();
                    final Implementation impl =
                            ctActivator
                                    .getComponentTypeService()
                                    .createImplementationFromURL(resource.getFullPath()
                                            + getFilePathAppendString(),
                                            compositeLocation);

                    UnprotectedWriteOperation setContentsOp =
                            new UnprotectedWriteOperation(editingDomain) {
                                @Override
                                protected Object doExecute() {
                                    Component generatedComponent =
                                            modelBuilder
                                                    .createComponent(composite,
                                                            impl);
                                    generatedComponent.setName(componentName);
                                    generatedComponent
                                            .setVersion(PEN2Utils
                                                    .getProcessComponentVersionNumber(resource));
                                    return generatedComponent;
                                }
                            };
                    Component component = (Component) setContentsOp.execute();
                    createdComponents.add(component);

                    long afterTimeMillis = System.currentTimeMillis();
                    long timeTaken = afterTimeMillis - beforeTimeMillis;
                    System.err
                            .println("*********The time taken to create a N2PE Component " //$NON-NLS-1$
                                    + resource.getName() + " ******** " //$NON-NLS-1$
                                    + timeTaken + " milliseconds"); //$NON-NLS-1$
                }
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Configures security policies (SAML, User Name Token, X509 and Custom) on
     * component references
     * 
     * @param project
     * @param stagingArea
     * @param composite
     */
    protected void configureSecurityPolicies(IProject project,
            IContainer stagingArea, Composite composite) {

        String samlTemplate =
                PolicySetUtil
                        .getTemplate("SamlCM.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX); //$NON-NLS-1$
        String x505Template =
                PolicySetUtil
                        .getTemplate("X509SB.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX); //$NON-NLS-1$
        String usernameCMTemplate =
                PolicySetUtil
                        .getTemplate("UsernameCM.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX); //$NON-NLS-1$

        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        Map<String, IFile> policyNames = new HashMap<String, IFile>();

        /*
         * Used to save already created policy sets to be reused if the
         * reference is the same.
         */
        HashMap<PolicySetReference, PolicySet> refPolicySetCache =
                new HashMap<PolicySetReference, PolicySet>();
        for (final PromotedReference reference : composite.getReferences()) {

            Participant participant =
                    SoapBindingUtil.getParticipantForReference(project,
                            reference);
            if (participant != null
                    && SharedResourceUtil.isWebServiceConsumer(participant)) {

                IFolder policyFolder =
                        ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$
                Map<String, String> substitutions =
                        new HashMap<String, String>();
                PolicySet securityPolicySet = null;
                ParticipantSharedResource sr =
                        SharedResourceUtil
                                .getParticipantSharedResource(participant);
                if (sr != null && sr.getWebService() != null
                        && sr.getWebService().getOutbound() != null) {

                    WsSecurityPolicy policy =
                            SharedResourceUtil.getSoapOutSecurityPolicy(sr
                                    .getWebService().getOutbound());
                    if (policy != null) {

                        if (SecurityPolicy.SAML_TOKEN.equals(policy.getType())) {
                            /*
                             * SAML Credential Mapping policy added on
                             * reference.
                             */
                            substitutions.put("$$ResourceInstanceApp$$", policy //$NON-NLS-1$
                                    .getGovernanceApplicationName());
                            substitutions
                                    .put("$$IssuerName$$", "urn:www.tibco.com"); //$NON-NLS-1$//$NON-NLS-2$

                            /*
                             * XPD-5331 Removing superfluous "Http" from policy
                             * file name because same policy set will be used
                             * for SoapJms too!
                             */
                            String policyFileName =
                                    "SamlCMSoap_" + participant.getName() + ".policysets"; //$NON-NLS-1$ //$NON-NLS-2$
                            securityPolicySet =
                                    PolicySetUtil
                                            .getCreatePolicySetFromTemplate(policyFolder,
                                                    policyFileName,
                                                    samlTemplate,
                                                    substitutions);
                        } else if (SecurityPolicy.X509_TOKEN.equals(policy
                                .getType())) {

                            substitutions.put("$$ResourceInstanceApp$$", policy //$NON-NLS-1$
                                    .getGovernanceApplicationName());

                            /*
                             * XPD-5331 Removing superfluous "Http" from policy
                             * file name because same policy set will be used
                             * for SoapJms too!
                             */
                            String policyFileName =
                                    "X509SBSoap_" + participant.getName() + ".policysets"; //$NON-NLS-1$ //$NON-NLS-2$
                            securityPolicySet =
                                    PolicySetUtil
                                            .getCreatePolicySetFromTemplate(policyFolder,
                                                    policyFileName,
                                                    x505Template,
                                                    substitutions);
                        } else if (SecurityPolicy.USERNAME_TOKEN.equals(policy
                                .getType())) {

                            substitutions
                                    .put("$$ResourceInstanceApp$$", policy.getGovernanceApplicationName()); //$NON-NLS-1$
                            /*
                             * XPD-5331 Removing superfluous "Http" from policy
                             * file name because same policy set will be used
                             * for SoapJms too!
                             */
                            String policyFileName =
                                    "UsernameCMSoap_" + participant.getName() + ".policysets"; //$NON-NLS-1$ //$NON-NLS-2$
                            securityPolicySet =
                                    PolicySetUtil
                                            .getCreatePolicySetFromTemplate(policyFolder,
                                                    policyFileName,
                                                    usernameCMTemplate,
                                                    substitutions);
                        } else if (SecurityPolicy.CUSTOM.equals(policy
                                .getType())) {

                            WsOutbound wsOutbound =
                                    sr.getWebService().getOutbound();
                            if (wsOutbound != null
                                    && wsOutbound.getBinding() instanceof WsSoapBinding) {

                                WsSoapSecurity soapSecurity =
                                        ((WsSoapBinding) wsOutbound
                                                .getBinding())
                                                .getSoapSecurity();
                                if (soapSecurity != null) {

                                    PolicySetReference ref =
                                            PolicySetReference
                                                    .getPolicySetReference(soapSecurity);
                                    if (ref != null && ref.getFile() != null) {

                                        PolicySet existingPolicySet =
                                                refPolicySetCache.get(ref);
                                        if (existingPolicySet == null) {

                                            String policyFileName =
                                                    getUniquePolicySetName(policyFolder,
                                                            null,
                                                            policyNames,
                                                            ref.getFile());
                                            securityPolicySet =
                                                    getCreatePolicySetFromReference(policyFolder,
                                                            policyFileName,
                                                            ref);
                                            refPolicySetCache.put(ref,
                                                    securityPolicySet);
                                        } else {

                                            securityPolicySet =
                                                    existingPolicySet;
                                        }
                                    }
                                }
                            }
                        }
                        if (securityPolicySet != null) {

                            final PolicySet ps = securityPolicySet;
                            UnprotectedWriteOperation setPolicy =
                                    new UnprotectedWriteOperation(editingDomain) {
                                        @Override
                                        protected Object doExecute() {

                                            reference.getPolicySets().add(ps);
                                            return Status.OK_STATUS;
                                        }
                                    };
                            setPolicy.execute();
                        }
                    }
                }
            }
        }
    }

    /**
     * Configures REST security policies (Basic and Custom) on component
     * references
     * 
     * @param project
     *            the context project.
     * @param stagingArea
     *            staging area where composite and DAA is being build.
     * @param composite
     *            the composite
     */
    protected void configureSecurityRestPolicies(IProject project,
            IContainer stagingArea, Composite composite) {

        String usernameCMTemplate =
                PolicySetUtil
                        .getTemplate("BasicAuthCM.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX); //$NON-NLS-1$

        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        Map<String, IFile> policyNames = new HashMap<String, IFile>();

        /*
         * Used to save already created policy sets to be reused if the
         * reference is the same.
         */
        HashMap<PolicySetReference, PolicySet> refPolicySetCache =
                new HashMap<PolicySetReference, PolicySet>();
        for (final PromotedReference reference : composite.getReferences()) {

            Participant participant =
                    N2PECompositeUtil.getParticipantForReference(project,
                            reference);
            if (participant != null
                    && SharedResourceUtil.isRestConsumer(participant)) {

                IFolder policyFolder =
                        ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$
                Map<String, String> substitutions =
                        new HashMap<String, String>();
                PolicySet securityPolicySet = null;
                RestServiceResource sr =
                        SharedResourceUtil.getRestConsumerResource(participant);
                if (sr != null) {

                    RestServiceResourceSecurity policy = null;
                    if (sr.getSecurityPolicy() != null
                            && !sr.getSecurityPolicy().isEmpty()) {
                        policy = sr.getSecurityPolicy().get(0);
                    }
                    if (policy != null) {
                        if (SecurityPolicy.BASIC.equals(policy.getPolicyType())) {

                            String identityProviderSrName =
                                    getRestPolicyIdentityProvider(policy);
                            substitutions
                                    .put("$$ResourceInstanceApp$$", identityProviderSrName); //$NON-NLS-1$
                            String policyFileName =
                                    "BasicAuthCM_" + participant.getName() + ".policysets"; //$NON-NLS-1$ //$NON-NLS-2$
                            securityPolicySet =
                                    PolicySetUtil
                                            .getCreatePolicySetFromTemplate(policyFolder,
                                                    policyFileName,
                                                    usernameCMTemplate,
                                                    substitutions);
                        } else if (SecurityPolicy.CUSTOM.equals(policy
                                .getPolicyType())) {

                            PolicySetReference ref =
                                    PolicySetReference
                                            .getPolicySetReference(policy);
                            if (ref != null && ref.getFile() != null) {

                                PolicySet existingPolicySet =
                                        refPolicySetCache.get(ref);
                                if (existingPolicySet == null) {

                                    String policyFileName =
                                            getUniquePolicySetName(policyFolder,
                                                    null,
                                                    policyNames,
                                                    ref.getFile());
                                    securityPolicySet =
                                            getCreatePolicySetFromReference(policyFolder,
                                                    policyFileName,
                                                    ref);
                                    refPolicySetCache.put(ref,
                                            securityPolicySet);
                                } else {

                                    securityPolicySet = existingPolicySet;
                                }
                            }
                        }
                        if (securityPolicySet != null) {

                            final PolicySet ps = securityPolicySet;
                            UnprotectedWriteOperation setPolicy =
                                    new UnprotectedWriteOperation(editingDomain) {
                                        @Override
                                        protected Object doExecute() {

                                            reference.getPolicySets().add(ps);
                                            return Status.OK_STATUS;
                                        }
                                    };
                            setPolicy.execute();
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns identity provider resource name for a rest security policy.
     * 
     * @param restPolicy
     *            rest security policy.
     * @return identity provider resource name for a rest security policy, or
     *         'null' if not set.
     */
    private String getRestPolicyIdentityProvider(
            RestServiceResourceSecurity restPolicy) {
        return XpdlSearchUtil.findExtendedAttributeValue(restPolicy,
                "IdentityProvider"); //$NON-NLS-1$
    }

    /**
     * Configures the threading policy on the promoted service in a composite.
     * 
     * Please note that this was done in Project DAA Generator class prior to
     * XPD-6069. XPD-6069 requested to update the invocation time out on the
     * thread policy.
     * 
     * @param project
     * @param stagingArea
     * @param composite
     */
    protected void configureThreadingPolicy(final IProject project,
            final IContainer stagingArea, Composite composite) {

        TransactionalEditingDomain editingDomain =
                TransactionUtil.getEditingDomain(composite);
        /*
         * thread policy for component services (incoming request activities)
         */
        for (final PromotedService promServ : composite.getServices()) {

            final ComponentService componentService = promServ.getPromotion();
            Participant participantForService =
                    SoapBindingUtil.getParticipantForService(project,
                            componentService);
            EObject eContainer = participantForService.eContainer();
            Package procPkg = null;

            if (eContainer instanceof Package) {

                procPkg = (Package) eContainer;
            } else if (eContainer instanceof Process) {

                Process process = (Process) eContainer;
                procPkg = process.getPackage();
            }
            if (null != procPkg) {

                PolicySet threadPolicySet = null;

                boolean requestOnlyService =
                        isRequestOnlyService(componentService);

                if (!requestOnlyService) {
                    /*
                     * XPD-6633: if it is a request-reply service then we want
                     * the threading policy to have time out value configured on
                     * the policy set (if the time out value is not specified,
                     * then the method below returns null. so we need to ensure
                     * to use standard thread policy will be used)
                     */
                    threadPolicySet =
                            getPolicySetForTimeoutConfiguration(procPkg,
                                    stagingArea);
                }

                if (null == threadPolicySet) {

                    threadPolicySet =
                            getStandardThreadPolicySet(procPkg, stagingArea);
                }

                if (null != threadPolicySet) {

                    final PolicySet ps = threadPolicySet;
                    UnprotectedWriteOperation setPolicy =
                            new UnprotectedWriteOperation(editingDomain) {
                                @Override
                                protected Object doExecute() {
                                    componentService.getPolicySets().add(ps);
                                    return Status.OK_STATUS;
                                }
                            };
                    setPolicy.execute();
                }
            }
        }
    }

    /**
     * 
     * @param procPkg
     * @param stagingArea
     * @return the standard policy set
     */
    private PolicySet getStandardThreadPolicySet(Package procPkg,
            IContainer stagingArea) {

        Map<String, String> substitutions = new HashMap<String, String>();
        IFolder policyFolder = ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$
        String threadPolicyTemplate =
                PolicySetUtil
                        .getTemplate("ThreadingPolicy.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX);//$NON-NLS-1$
        String threadPolicyFileName = "ThreadPolicy.policysets"; //$NON-NLS-1$
        PolicySet threadPolicySet =
                PolicySetUtil.getCreatePolicySetFromTemplate(policyFolder,
                        threadPolicyFileName,
                        threadPolicyTemplate,
                        substitutions);
        return threadPolicySet;

    }

    /**
     * If time out value is configured on the process package then returns the
     * thread policy with time out configuration; else returns null
     * 
     * @param procPkg
     * @param stagingArea
     * @return the policy set with time out configuration if time out
     *         configuration is set; returns null if no time out is configured
     */
    private PolicySet getPolicySetForTimeoutConfiguration(Package procPkg,
            IContainer stagingArea) {

        Map<String, String> substitutions = new HashMap<String, String>();

        IFolder policyFolder = ((IFolder) stagingArea).getFolder("resources"); //$NON-NLS-1$

        BpmRuntimeConfiguration bpmRuntimeConfiguration =
                (BpmRuntimeConfiguration) Xpdl2ModelUtil
                        .getOtherElement(procPkg, XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BpmRuntimeConfiguration());
        Integer incomingRequestTimeOut = null;
        /*
         * If the incoming request time out is specified then the ThreadPolicy
         * policy set file, policy set name and policy label will be suffixed
         * with process package name/id. Otherwise existing standard statically
         * defined ThreadingPolicy.policysets file will be referenced by the
         * component service
         */
        if (null != bpmRuntimeConfiguration) {

            incomingRequestTimeOut =
                    bpmRuntimeConfiguration.getIncomingRequestTimeout();
            if (null != incomingRequestTimeOut) {

                String threadPolicyTemplate =
                        PolicySetUtil
                                .getTemplate("ThreadingPolicyWithTimeout.policysets", PLATFORM_PLUGIN_RES_URI_PREFIX);//$NON-NLS-1$
                /*
                 * ThreadingPolicy.policysets file must be created with
                 * ThreadingPolicy_<process-package-name>.policysets
                 */
                String threadPolicyFileName =
                        String.format("ThreadingPolicyWithTimeout_%1$s.policysets", procPkg.getName()); //$NON-NLS-1$
                /*
                 * sca:policySet/Name must be suffixed with
                 * _<process-package-name>
                 */
                substitutions
                        .put("$$PolicySetName$$", String.format("ThreadingPolicy_WRMComponent_%1$s", procPkg.getName())); //$NON-NLS-1$ //$NON-NLS-2$
                /*
                 * systempolicy:threadingPolicy/scaext:label will be suffixed
                 * with the <process-package-name>
                 */
                substitutions
                        .put("$$ThreadingPolicyLabel$$", String.format("Threading Policy %1$s", procPkg.getName())); //$NON-NLS-1$ //$NON-NLS-2$
                /* replace the time out value */
                substitutions
                        .put("$$invocationTimeOut$$", incomingRequestTimeOut.toString()); //$NON-NLS-1$
                PolicySet timeoutThreadPolicySet =
                        PolicySetUtil
                                .getCreatePolicySetFromTemplate(policyFolder,
                                        threadPolicyFileName,
                                        threadPolicyTemplate,
                                        substitutions);
                return timeoutThreadPolicySet;
            }
        }
        return null;
    }

    /**
     * Checks if the given component service has request only operations for its
     * port type
     * 
     * @param compService
     * @return <code>true</code> if the component service has got any request
     *         only operation defined <code>false</code> otherwise
     */
    protected boolean isRequestOnlyService(ComponentService compService) {

        if (compService == null || compService.getInterface() == null) {

            return false;
        }
        PortType portType =
                ((Wsdl11Interface) compService.getInterface()).getPortType();
        if (portType == null) {

            return false;
        }
        List<?> wsdlOperationList = portType.getOperations();
        if (wsdlOperationList == null || wsdlOperationList.isEmpty()) {

            return false;
        }
        for (Object wsdlOpr : wsdlOperationList) {

            Operation wsdlOperation = (Operation) wsdlOpr;
            Output output = wsdlOperation.getOutput();
            if (output != null) {

                return false;
            }
        }
        return true;
    }

    /**
     * Gets unique name for a policy set.
     * 
     * @param destFolder
     * @param destFileName
     * @param policyNames
     * @param src
     * @return
     */
    private String getUniquePolicySetName(IFolder destFolder,
            String destFileName, Map<String, IFile> policyNames, IFile src) {

        String fileName = null;
        if (destFileName != null) {

            fileName = destFileName;
        } else if (src != null) {

            fileName = src.getName();
        }
        if (fileName == null) {

            throw new IllegalArgumentException();
        }
        String result = fileName;
        Path path = new Path(fileName);
        String fileBase = path.removeFileExtension().lastSegment();
        String extension = path.getFileExtension();
        int i = 1;
        while (destFolder.getFile(result).exists()) {

            IFile iFile = policyNames.get(result);
            if (destFolder.getFile(result).equals(iFile)) { // Same source hence
                                                            // same name is OK.
                break;
            }

            result = fileBase + '_' + i + '.' + extension;
        }
        policyNames.put(result, src);
        return result;
    }

    /**
     * Gets source policy set from the process CUSTOM policy reference,
     * validates it, and gets its file in the workspace. Then it copies the
     * source policy file to the staging area's folder (giving the unique file
     * name) and reads and returns the PolicySet reference form this copied
     * file.
     * 
     * @param policyFolder
     *            the folder in the staging area to copy the source policy.
     * @param policyFileName
     *            unique policy file name within policy folder.
     * @param ref
     *            the reference of a custom policy set as stored in an xpdl
     *            file.
     * 
     * @return {@link PolicySet} resolved from a file in the composite's staging
     *         area.
     */
    private PolicySet getCreatePolicySetFromReference(IFolder policyFolder,
            String policyFileName, PolicySetReference ref) {

        try {
            PolicySet srcPolicySet =
                    PolicySetUtil.resolvePolicySetReference(ref);
            if (srcPolicySet == null) {

                throw new IllegalArgumentException(
                        String.format("Policy reference cannot be resolved: %1$s", //$NON-NLS-1$
                                ref));
            }
            if (!policyFolder.exists()) {

                ProjectUtil.createFolder(policyFolder, true, null);
            }
            IFile policyFile = policyFolder.getFile(policyFileName);
            if (!policyFile.exists()) {

                ref.getFile().copy(policyFile.getFullPath(),
                        IResource.FORCE,
                        new NullProgressMonitor());
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(policyFile);
            wc.reLoad();
            EObject root = wc.getRootElement();
            if (root instanceof ExternalPolicySetContainerDocument) {

                EObject eObject =
                        root.eResource().getEObject(URI.createURI(ref.getUri())
                                .fragment());
                if (eObject instanceof PolicySet) {

                    return (PolicySet) eObject;
                }
            }

        } catch (CoreException e) {

            LOG.error(e);
        }
        return null;
    }

    /**
     * 
     * @param xpdlFile
     * @return the BPEL files expected to be generated from the XPDL file
     */
    protected IFile[] getBpelFiles(IFile xpdlFile) {

        IFile[] bpelFiles = BPELN2Utils.getBusinessProcessBpelFiles(xpdlFile);
        return bpelFiles;
    }

    /**
     * clean Pe Composite Error Markers
     * 
     * @param resource
     */
    private void cleanPeCompositeErrorMarkers(IResource resource) {

        try {
            N2PENamingUtils.cleanDaaErrorMarkers(resource,
                    PEN2Utils.PE_COMPOSITE_ERROR_MARKER_ID);
        } catch (CoreException e) {

            LOG.error(e);
        }
    }

    /**
     * copy Bpel Files Into staging area from location they were generated
     * 
     * @param stagingArea
     * @param project
     */
    private void copyBpelFiles(IContainer stagingArea, IProject project) {

        try {
            IFolder bpelOutFolder = getBpelOutFolder(project);
            if (bpelOutFolder != null && bpelOutFolder.exists()
                    && stagingArea != null && stagingArea.exists()) {

                IFolder distFolder =
                        getBpelStagingFolder((IFolder) stagingArea);
                if (!distFolder.exists()) {

                    ProjectUtil.createFolder(distFolder, false, null);
                    if (distFolder.isAccessible()) {

                        distFolder.setDerived(Boolean.TRUE);
                    }
                }
                IPath bpelFilesOut =
                        distFolder.getFullPath()
                                .append(bpelOutFolder.getName());
                distFolder =
                        project.getWorkspace().getRoot()
                                .getFolder(bpelFilesOut);
                if (distFolder.exists()) {

                    distFolder.delete(true, new NullProgressMonitor());
                }
                bpelOutFolder.copy(bpelFilesOut,
                        true,
                        new NullProgressMonitor());
                distFolder.accept(new IResourceVisitor() {
                    public boolean visit(IResource resource)
                            throws CoreException {

                        if (resource instanceof IFile) {

                            if (!"wsdl".equals(resource.getFileExtension())) { //$NON-NLS-1$
                                /*
                                 * hack as we wait for SDS fix to index wsdl
                                 * marked derived
                                 */
                                resource.setDerived(Boolean.TRUE);
                            }
                            /*
                             * XPD-4873: when the composite is created, it gets
                             * the port type from the wsdl and loads the working
                             * copy for the port type. So if port type and port
                             * are in the same wsdl, then port also gets indexed
                             * because there is only one working copy. If port
                             * type and port are in different wsdls, then port
                             * does not get indexed because the working copy for
                             * port type wsdl is loaded but not the working copy
                             * for wsdl having port defined. So the approach we
                             * have decided to fix this issue is to explicitly
                             * load the working copy for all wsdls.
                             */
                            if ("wsdl".equals(resource.getFileExtension())) { //$NON-NLS-1$

                                WorkingCopyUtil.getWorkingCopy(resource);
                            }
                            return false;
                        } else if (resource instanceof IFolder) {

                            resource.setDerived(true);
                            return true;
                        } else {

                            return false;
                        }
                    }
                });
            }
        } catch (CoreException e) {

            LOG.error(e);
            /*
             * addPeCompositeErrorMarker(project,
             * Messages.PECompositeContributor_problemsDuringDaaGeneration);
             */
        }
    }

    /**
     * Add the promoted references, promoted services and the wiring between
     * Process Component Services and References (for same WSDL interface)
     * 
     * @param component
     * @param project
     * @param timeStamp
     */
    protected void promoteComponent(Component component, IProject project,
            String timeStamp) {

        Implementation compImplementation = component.getImplementation();
        if (!(compImplementation instanceof BxServiceImplementation)) {

            return;
        }
        CompositeModelBuilder modelBuilder =
                ComponentTypeActivator.getDefault().getModelBuilder();
        Composite composite = component.getComposite();

        /* promote all services, references and properties. */

        /* SERVICES */
        for (ComponentService cs : component.getServices()) {

            PromotedService promotedService = modelBuilder.promoteService(cs);
            // SoapBindingUtil
            // .configureServiceBindings(promotedService, timeStamp);
        }

        /* REFERENCES */
        List<PromotedReference> promotedReferences = composite.getReferences();
        for (ComponentReference cr : component.getReferences()) {

            PortType crInterface =
                    ((Wsdl11Interface) cr.getInterface()).getPortType();

            Participant crParticipant =
                    SoapBindingUtil.getParticipantForReference(project, cr);

            /*
             * Checking if there is a service on another component with this
             * reference's port type.
             * 
             * Don't try to find the matching service for rest (pass trough)
             * reference.
             */
            ComponentService serviceToWire = null;
            if (!SharedResourceUtil.isRestConsumer(crParticipant)) {
                external: for (Component c : composite.getComponents()) {
                    for (ComponentService cs : c.getServices()) {

                        PortType csInterface =
                                ((Wsdl11Interface) cs.getInterface())
                                        .getPortType();
                        /*
                         * Components with the same interfaces will be auto
                         * wired inside composite. JA: Should we also check if
                         * it's a virtual reference (isVirtual(crParticipant))?
                         */
                        if (crInterface.getQName()
                                .equals(csInterface.getQName())) {
                            /* commented this bit of condition for XPD-1265 */
                            // && !component.equals(c)) {

                            serviceToWire = cs;
                            break external;
                        }
                    }
                }
            }

            if (serviceToWire != null) {
                createWire(cr, serviceToWire);

            } else {

                /*
                 * Sid XPD-7543: used to check this below after going to all the
                 * trouble of finding the promoted reference - just to then
                 * ignore it if wired-by-impl afterwards.
                 */
                if (!cr.isWiredByImpl()) {

                    if (crParticipant == null) {
                        Activator
                                .getDefault()
                                .getLogger()
                                .error(new Exception(),
                                        String.format("PECompositeContributor: Cannot find XPDL participant for component reference: '%s'", //$NON-NLS-1$
                                                cr.getName()));
                        continue;
                    }

                    PromotedReference foundPr = null;
                    for (PromotedReference pr : promotedReferences) {

                        PortType prInterface =
                                ((Wsdl11Interface) pr.getInterface())
                                        .getPortType();

                        /*
                         * Sid XPD-7453: Do quickest check first (no point
                         * hunting down participant if port types don't match)
                         */
                        if (prInterface.getQName()
                                .equals(crInterface.getQName())) {

                            Participant prParticipant =
                                    N2PECompositeUtil
                                            .getParticipantForReference(project,
                                                    pr);

                            if (prParticipant == null) {
                                Activator
                                        .getDefault()
                                        .getLogger()
                                        .error(new Exception(),
                                                String.format("PECompositeContributor: Cannot find XPDL participant for promoted reference: '%s'", //$NON-NLS-1$
                                                        pr.getName()));
                                continue;
                            }

                            if (matchingConsumerParticipants(prParticipant,
                                    crParticipant)) {
                                foundPr = pr;
                                break;

                            }
                        }
                    }

                    if (foundPr == null) {
                        /*
                         * If we did not find an equivalent promotedReference
                         * for the ComponentReference then add one.
                         */
                        PromotedReference promotedReference =
                                modelBuilder.promoteReference(cr);
                    } else {
                        /*
                         * Otherwise we wire the existing one to the Component
                         * reference
                         */
                        foundPr.getPromotions().add(cr);
                    }

                }
            }
        }

        /* PROPERTIES */
        // for (ComponentProperty cp : component.getProperties()) {
        // modelBuilder.promoteProperty(cp);
        // }

        /*
         * TODO there is a problem in
         * com.tibco.amf.sca.componenttype.util.ComponentUtil#createProperty()
         * it missed the simpleValue,so I recreate all ComponentProperties here.
         */
        component.getProperties().clear();
        BxServiceImplementation bxServiceImplementation =
                (BxServiceImplementation) component.getImplementation();
        ComponentType bxServiceType = bxServiceImplementation.getServiceType();
        if (bxServiceType == null) {

            return;
        }
        EList<Property> properties = bxServiceType.getProperties();
        for (Property cp : properties) {

            ComponentProperty compProperty = createComponentProperty(cp);
            component.getProperties().add(compProperty);
            modelBuilder.promoteProperty(compProperty);
        }
    }

    /*
     * TODO Refactor later to
     * CompositeModelBuilder.createWire(ComponentReference reference,
     * ComponentService service) when it appears in AMX build.
     */
    /**
     * 
     * @param reference
     * @param service
     * @return new {@link Wire} with source reference and target service set
     */
    private static Wire createWire(ComponentReference reference,
            ComponentService service) {

        Wire wire = CompositeFactory.eINSTANCE.createWire();
        service.getComponent().getComposite().getWires().add(wire);
        wire.setSource(reference);
        wire.setTarget(service);
        return wire;
    }

    /**
     * 
     * @param property
     * @return new component property with some default values set.
     */
    private static ComponentProperty createComponentProperty(Property property) {

        ComponentProperty compProperty =
                CompositeFactory.eINSTANCE.createComponentProperty();
        compProperty.setName(property.getName());
        compProperty.setType(property.getType());
        compProperty.setMustSupply(property.isMustSupply());
        compProperty.setSimpleValue(property.getSimpleValue());
        return compProperty;
    }

    /**
     * Calls {@link PEN2Utils} to return the component name for the given xpdl
     * file
     * 
     * @param xpdlFile
     * @return the component name for the given xpdl file
     */
    private String getComponentName(IResource xpdlFile) {

        return PEN2Utils.getComponentName(xpdlFile, getFilePathAppendString()
                .substring(1));
    }

    /**
     * Returns the folder where all the bpel files are located
     * 
     * @param project
     * @return
     */
    protected IFolder getBpelOutFolder(IProject project) {

        return BPELN2Utils.getBpelBusinessProcessOutDestFolder(project);
    }

    /**
     * Returns folder under staging folder
     * 
     * @param stagingFolder
     * @return
     */
    protected IFolder getBpelStagingFolder(IFolder stagingFolder) {

        IPath bpelFilesOut =
                stagingFolder.getFullPath()
                        .append(BPELN2Utils.BPEL_ROOT_OUTPUTFOLDER_NAME);

        IFolder distFolder =
                stagingFolder.getProject().getWorkspace().getRoot()
                        .getFolder(bpelFilesOut);
        return distFolder;
    }

    /**
     * 
     * @return
     */
    protected String getFilePathAppendString() {

        return PEN2Utils.PROCESS_FLOW_APPEND;
    }

    /**
     * Checks if both the participants have same name and same outbound shared
     * resource configuration
     * 
     * @param p1
     * @param p2
     * @return
     */
    private static boolean matchingConsumerParticipants(Participant p1,
            Participant p2) {

        /*
         * Sid XPD-7543 - for REST consumer participants we match only on the
         * REST service consumer participant configuration NOT on participant
         * name as well.
         * 
         * Effecitevely the http shared resource name and security config is the
         * unique key for the REST consumer endpoint so we should have only one
         * promoted reference per endpoint.
         */
        RestServiceResource p1RestServiceResource =
                SharedResourceUtil.getRestConsumerResource(p1);
        RestServiceResource p2RestServiceResource =
                SharedResourceUtil.getRestConsumerResource(p2);

        if (p1RestServiceResource != null && p2RestServiceResource != null) {
            /*
             * REST Service consumer participant.
             */
            return SharedResourceUtil
                    .haveSameRESTCOnsumerConfiguration(p1RestServiceResource,
                            p2RestServiceResource);

        } else {
            /*
             * Web Service Participant
             */
            return p1.getName() != null
                    && p1.getName().equals(p2.getName())
                    && SharedResourceUtil
                            .haveSameWsOutbounfiguration(SharedResourceUtil
                                    .getWsResourceOutbound(p1),
                                    SharedResourceUtil
                                            .getWsResourceOutbound(p2));
        }
    }

    /**
     * @see com.tibco.xpd.daa.CompositeContributor#prepareProject(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @return
     */
    @Override
    public IStatus prepareProject(IProject project, IProgressMonitor monitor) {
        /*
         * SID XPD-2613: Perform a create on demand build of the XPDL 2 BPEL
         * files.
         */
        BPELOnDemandBuilder bpelOnDemandBuilder =
                new BPELOnDemandBuilder(project);

        return bpelOnDemandBuilder.buildProject(monitor);
    }
}
