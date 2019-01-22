/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.osgi.framework.Version;

import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.amx.model.service.BxServiceImplementation;
import com.tibco.n2.pfe.BusinessService;
import com.tibco.n2.pfe.CaseService;
import com.tibco.n2.pfe.PFESpecification;
import com.tibco.n2.pfe.PfeFactory;
import com.tibco.n2.pfe.PublishType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.n2.pe.component.PEImplementationTypeProvider;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.BusinessServicePublishType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.RestServiceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author kupadhya
 * 
 */
public class NewPFCImplementationTypeProvider extends
        PEImplementationTypeProvider {

    @Override
    protected IFile[] getBpelFiles(IFile xpdlFile) {
        IFile[] bpelFiles = BPELN2Utils.getPageFlowBpelFiles(xpdlFile);
        return bpelFiles;
    }

    @Override
    protected String getFilePathAppendString() {
        return PFEUtil.PAGE_FLOW_APPEND;
    }

    @Override
    protected BxServiceImplementation newImplementation() {
        return PfeFactory.eINSTANCE.createPFESpecification();

    }

    /**
     * {@inheritDoc}
     */
    protected void postCreateImplementation(BxServiceImplementation impl,
            String path, URI compositeLocation) {

        if (impl instanceof PFESpecification) {

            PFESpecification pfeSpecification = (PFESpecification) impl;
            EList<com.tibco.bx.core.model.Process> bxProcesses =
                    pfeSpecification.getServiceModel().getProcesses();

            /* Add deployment model for Business services */
            for (Process xpdlBusinessService : getBusinessServices(pfeSpecification
                    .getServiceModel().getModuleName())) {

                addBusinessServicesModel(pfeSpecification,
                        bxProcesses,
                        xpdlBusinessService);
            }

            /* ABPM-882: Add deployment model for case services */
            Collection<Process> caseServices =
                    getCaseServices(pfeSpecification.getServiceModel()
                            .getModuleName());
            for (Process xpdlCaseService : caseServices) {

                addCaseServicesModel(pfeSpecification,
                        bxProcesses,
                        xpdlCaseService);
            }

            /* Add REST services as business service elements with REST category */
            for (Process restService : getRestServices(pfeSpecification
                    .getServiceModel().getModuleName())) {

                addRestServicesModel(pfeSpecification, bxProcesses, restService);
            }
        }

    }

    /**
     * Adds Business Service model
     * 
     * @param pfeSpecification
     * @param bxProcesses
     * @param xpdlBusinessService
     */
    private void addBusinessServicesModel(PFESpecification pfeSpecification,
            EList<com.tibco.bx.core.model.Process> bxProcesses,
            Process xpdlBusinessService) {

        BusinessService businessService =
                PfeFactory.eINSTANCE.createBusinessService();
        businessService
                .setCategory(getBusinessServiceCategory(xpdlBusinessService));
        businessService.getChannels()
                .addAll(getPageFlowChannelIds(xpdlBusinessService));

        /*
         * Sid CBPM-8227 - Add target device config
         */
        businessService
                .setPublishType(getBusinessServiceTargetDevice(xpdlBusinessService));

        // Find referenced bx process by name.
        for (com.tibco.bx.core.model.Process p : bxProcesses) {

            if (xpdlBusinessService.getName().equals(p.getProcessName())) {

                businessService.setProcessName(p.getProcessName());
                businessService.setProcessFileName(p.getProcessFileName());
            }
        }
        /* ABPM-882: Add privileges to business service. */
        RequiredAccessPrivileges requiredAccessPrivileges =
                (RequiredAccessPrivileges) Xpdl2ModelUtil
                        .getOtherElement(xpdlBusinessService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RequiredAccessPrivileges());
        if (null != requiredAccessPrivileges) {

            EList<ExternalReference> privilegeReference =
                    requiredAccessPrivileges.getPrivilegeReference();
            for (ExternalReference externalReference : privilegeReference) {

                businessService.getPrivileges()
                        .add(externalReference.getXref());
            }
        }
        pfeSpecification.getBusinessServices().add(businessService);
    }

    /**
     * Adds Case Service Model
     * 
     * @param pfeSpecification
     * @param bxProcesses
     * @param xpdlCaseService
     */
    private void addCaseServicesModel(PFESpecification pfeSpecification,
            EList<com.tibco.bx.core.model.Process> bxProcesses,
            Process xpdlCaseService) {

        /* create a case service */
        CaseService caseService = PfeFactory.eINSTANCE.createCaseService();
        caseService.setCategory("_$_TIBCO_CASE_SERVICE_$_"); //$NON-NLS-1$
        caseService.getChannels()
                .addAll(getPageFlowChannelIds(xpdlCaseService));

        for (com.tibco.bx.core.model.Process p : bxProcesses) {

            if (xpdlCaseService.getName().equals(p.getProcessName())) {

                caseService.setProcessName(p.getProcessName());
                caseService.setProcessFileName(p.getProcessFileName());
                /* set the process label */
                String processLabel =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute(xpdlCaseService,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName());
                caseService.setCaseActionName(processLabel);
            }
        }

        /* set the case ref param name */
        List<FormalParameter> formalParameters =
                ProcessInterfaceUtil.getAllFormalParameters(xpdlCaseService);
        for (FormalParameter formalParameter : formalParameters) {

            if (formalParameter.getDataType() instanceof RecordType) {

                if (ModeType.IN_LITERAL.equals(formalParameter.getMode())) {

                    caseService.setCaseRefParamName(formalParameter.getName());
                }
            }
        }

        /* set the case class name */
        com.tibco.xpd.xpdExtension.CaseService xpdlCaseServiceModel =
                (com.tibco.xpd.xpdExtension.CaseService) Xpdl2ModelUtil
                        .getOtherElement(xpdlCaseService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CaseService());

        ExternalReference reference = xpdlCaseServiceModel.getCaseClassType();

        org.eclipse.uml2.uml.Class umlClass =
                (Class) ProcessUIUtil.getReferencedClassifier(reference,
                        WorkingCopyUtil.getProjectFor(xpdlCaseService));

        String umlPkgName = umlClass.getPackage().getName();
        String caseClassName =
                String.format("%1$s.%2$s", umlPkgName, umlClass.getName()); //$NON-NLS-1$
        caseService.setCaseClassName(caseClassName);

        /* set the major version on case service from the case class project */
        IProject bomProject = WorkingCopyUtil.getProjectFor(umlClass);
        String bomProjectVersionStr = ProjectUtil.getProjectVersion(bomProject);
        if (null != bomProjectVersionStr) {

            Version version = Version.parseVersion(bomProjectVersionStr);
            int majorVersion = version.getMajor();
            caseService.setMajorVersion("" + majorVersion); //$NON-NLS-1$
        }

        /* set the case states */
        VisibleForCaseStates visibleForCaseStates =
                xpdlCaseServiceModel.getVisibleForCaseStates();
        if (null != visibleForCaseStates) {

            /*
             * when specific states is selected and 'No Case State Set' is
             * selected in the studio UI
             */
            if (visibleForCaseStates.isVisibleForUnsetCaseState()) {

                caseService.getCaseStates().add("$UNSET$"); //$NON-NLS-1$
            }
            EList<ExternalReference> caseStates =
                    visibleForCaseStates.getCaseState();
            if (!caseStates.isEmpty()) {

                /* specific case states are selected */
                Property caseStateAttrib =
                        ProcessUIUtil.getCaseClassCaseState(umlClass);
                Enumeration caseStateEnum =
                        (Enumeration) caseStateAttrib.getType();
                EList<EnumerationLiteral> caseStateLiterals =
                        caseStateEnum.getOwnedLiterals();
                for (EnumerationLiteral enumerationLiteral : caseStateLiterals) {

                    ExternalReference bomEnumRef =
                            ProcessUIUtil
                                    .getExternalRefForEnumLit(xpdlCaseServiceModel,
                                            enumerationLiteral);
                    for (ExternalReference xpdlExtRef : caseStates) {

                        if (EcoreUtil.equals(bomEnumRef, xpdlExtRef)) {

                            caseService.getCaseStates()
                                    .add(enumerationLiteral.getName());
                        }
                    }
                }
            }
        }

        /* set the privileges */
        RequiredAccessPrivileges requiredAccessPrivileges =
                (RequiredAccessPrivileges) Xpdl2ModelUtil
                        .getOtherElement(xpdlCaseService,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RequiredAccessPrivileges());
        if (null != requiredAccessPrivileges) {

            EList<ExternalReference> privilegeReference =
                    requiredAccessPrivileges.getPrivilegeReference();
            for (ExternalReference externalReference : privilegeReference) {

                caseService.getPrivileges().add(externalReference.getXref());
            }
        }
        pfeSpecification.getCaseServices().add(caseService);
    }

    /**
     * Adds the model for rest services
     * 
     * @param pfeSpecification
     * @param bxProcesses
     * @param restService
     */
    private void addRestServicesModel(PFESpecification pfeSpecification,
            EList<com.tibco.bx.core.model.Process> bxProcesses,
            Process restService) {

        BusinessService businessService =
                PfeFactory.eINSTANCE.createBusinessService();
        businessService.setCategory("_$_TIBCO_REST_SERVICE_$_"); //$NON-NLS-1$

        // Find referenced bx process by name.
        for (com.tibco.bx.core.model.Process p : bxProcesses) {
            if (restService.getName().equals(p.getProcessName())) {
                businessService.setProcessName(p.getProcessName());
                businessService.setProcessFileName(p.getProcessFileName());
            }
        }
        pfeSpecification.getBusinessServices().add(businessService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Implementation createImplementation(String path,
            URI compositeLocation) {

        Implementation impl =
                super.createImplementation(path, compositeLocation);
        postCreateImplementation((BxServiceImplementation) impl,
                path,
                compositeLocation);
        return impl;
    }

    /**
     * Gets channel (TypeAssociation) ids' for the containing project.
     */
    private static List<String> getPageFlowChannelIds(Process pageflow) {

        List<String> channelIds = new ArrayList<String>();
        IFile file = WorkingCopyUtil.getFile(pageflow);
        if (file != null) {

            Channels channels =
                    PresentationManager.getInstance()
                            .getChannels(file.getProject());
            for (Channel channel : channels.getChannels()) {

                for (TypeAssociation typeAssociation : channel
                        .getTypeAssociations()) {

                    if (typeAssociation.getChannelType().getImplementation()
                            .getId()
                            .equals(PresentationManager.IMPLEMANTATION_PULL)) {

                        channelIds.add(typeAssociation.getDerivedId());
                    }
                }
            }
        }
        return channelIds;
    }

    /**
     * @param path
     * @return
     */
    private Collection<Process> getCaseServices(String path) {

        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(path));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null) {

            EObject root = wc.getRootElement();
            if (root instanceof Package) {

                Package xpdlPackage = (Package) root;
                return BRMUtils.getCaseServices(Collections
                        .singleton(xpdlPackage));
            }
        }
        return Collections.emptySet();
    }

    private static Collection<Process> getBusinessServices(String path) {

        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(path));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null) {

            EObject root = wc.getRootElement();
            if (root instanceof Package) {

                Package xpdlPackage = (Package) root;
                return BRMUtils.getBusinessServices(Collections
                        .singleton(xpdlPackage));
            }
        }
        return Collections.emptySet();
    }

    private static Collection<Process> getRestServices(String path) {

        IFile xpdlFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(path));
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(xpdlFile);
        if (wc != null) {

            EObject root = wc.getRootElement();
            List<Process> restServices = new ArrayList<Process>();

            if (root instanceof Package) {

                Package xpdlPackage = (Package) root;
                for (Process process : xpdlPackage.getProcesses()) {

                    restServices.addAll(RestServiceUtil
                            .getRestServices(process));
                }
                if (!restServices.isEmpty()) {

                    return restServices;
                }
            }
        }
        return Collections.emptySet();
    }

    /**
     * Returns business service category extended attribute from process.
     */
    private static String getBusinessServiceCategory(Process process) {

        Object bsCategory =
                Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_BusinessServiceCategory());
        return (String) bsCategory;
    }

    /**
     * Sid XPD-8227
     * 
     * @return Retrieve the design time configuration of the target device for
     *         business service.
     */
    private static PublishType getBusinessServiceTargetDevice(Process process) {
        BusinessServicePublishType pt =
                (BusinessServicePublishType) Xpdl2ModelUtil
                        .getOtherAttribute(process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessServicePublishType());

        if (pt != null && BusinessServicePublishType.MOBILE.equals(pt)) {
            return PublishType.MOBILE;
        }

        return PublishType.DESKTOP;
    }

}
