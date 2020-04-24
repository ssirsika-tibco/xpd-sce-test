/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.brm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.osgi.framework.Version;

import com.google.gson.Gson;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.n2.bpel.utils.BPELN2Utils;
import com.tibco.xpd.n2.brm.utils.BRMUtils;
import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.Channels;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.BusinessServicePublishType;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.VisibleForCaseStates;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Sid ACE-3075
 * 
 * Builds the old AMX BPM PFESpecification model (containing BusinessService and CaseService additional configuration
 * information) as a JSON model.
 *
 * @author aallway
 * @since 16 Sep 2019
 */
public class PfeSpecificationJsonBuilder {

    /**
     * Build the PFESpecification JSON model from the project content and return as a string.
     * 
     * The content map is equivalent to old PFESpecification model built in AMX BPM by
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider
     * 
     * @return JSON String or <code>null</code> if there is no content for the model (no biz / case services.
     */
    public String getJson(IProject project) {
        Map<String, Object> Model = new LinkedHashMap<>();

        /* The business service and case service configuration model content */
        Collection<Map<String, Object>> businessServices = new ArrayList<>();
        Collection<Map<String, Object>> caseServices = new ArrayList<>();

        /* The channels model is the same for all processes in the project. */
        Collection<Map<String, Object>> channelsModel = getChannelsModel(project);

        for (Package pkg : BRMUtils.getN2ProcessPackages(project)) {

            /* Get the pageflow folder (will be required for bpel file location proeprties in models */
            IFolder pageflowDestFolder =
                    BPELN2Utils.getBpelPageFlowXpdlDestFolder(project, WorkingCopyUtil.getFile(pkg));

            for (Process process : pkg.getProcesses()) {
                /* Check case service first as it is a specialisation of business service. */
                if (Xpdl2ModelUtil.isCaseService(process)) {
                    caseServices.add(buildCaseServiceModel(process, pageflowDestFolder, channelsModel));

                } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
                    businessServices.add(getBusinessServiceModel(process, pageflowDestFolder, channelsModel));
                }
            }
        }

        if (!businessServices.isEmpty()) {
            Model.put("businessServices", businessServices); //$NON-NLS-1$
        }

        if (!caseServices.isEmpty()) {
            Model.put("caseServices", caseServices); //$NON-NLS-1$
        }

        if (!Model.isEmpty()) {
            String json = new Gson().toJson(Model);
            return json;
        }
        return null;
    }

    /**
     * Create the model map for the given business service process
     * 
     * @param process
     * @param pageflowDestFolder
     * @param channelsModel
     * 
     * @return The model map for the given business service process
     */
    private Map<String, Object> getBusinessServiceModel(Process process, IFolder pageflowDestFolder,
            Collection<Map<String, Object>> channelsModel) {
        Map<String, Object> bizSvcModel = new LinkedHashMap<>();

        /* Add the parts of the model common to both business and case service processes. */
        bizSvcModel.putAll(getCommonServiceElements(process, pageflowDestFolder, channelsModel));

        /* Add the specific parts for business service. */
        String category = getBusinessServiceCategory(process);
        if (category != null) {
            bizSvcModel.put("category", category); //$NON-NLS-1$
        }

        String publishType = getBusinessServiceTargetDevice(process);
        if (publishType != null) {
            bizSvcModel.put("publishType", publishType); //$NON-NLS-1$
        }

        /* Sid CBPM-3600 include whether the business service has parameters or not. */
        bizSvcModel.put("hasFormalParameters", process.getFormalParameters().size() > 0); //$NON-NLS-1$

        return bizSvcModel;
    }

    /**
     * Create the model map for the given case service process
     * 
     * @param process
     * @param pageflowDestFolder
     * @param channelsModel
     * 
     * @return The model map for the given business service process
     */
    private Map<String, Object> buildCaseServiceModel(Process process, IFolder pageflowDestFolder,
            Collection<Map<String, Object>> channelsModel) {
        Map<String, Object> caseSvcModel = new LinkedHashMap<>();

        /* Add the parts of the model common to both business and case service processes. */
        caseSvcModel.putAll(getCommonServiceElements(process, pageflowDestFolder, channelsModel));

        /* Add the specific parts for business service. */
        caseSvcModel.put("category", "_$_TIBCO_CASE_SERVICE_$_"); //$NON-NLS-1$ //$NON-NLS-2$

        caseSvcModel.put("caseActionName", Xpdl2ModelUtil.getDisplayNameOrName(process)); //$NON-NLS-1$
        
        caseSvcModel.put("caseRefParamName", getCaseServiceCaseRefParam(process)); //$NON-NLS-1$
        
        Class caseClass = getCaseServiceCaseClass(process);

        caseSvcModel.put("caseClassName", //$NON-NLS-1$
                String.format("%s.%s", caseClass.getPackage().getName(), caseClass.getName())); //$NON-NLS-1$
        
        IProject bomProject = WorkingCopyUtil.getProjectFor(caseClass);
        String bomProjectVersionStr = ProjectUtil.getProjectVersion(bomProject);
        if (null != bomProjectVersionStr) {
            Version version = Version.parseVersion(bomProjectVersionStr);
            int majorVersion = version.getMajor();
            caseSvcModel.put("caseMajorVersion", "" + majorVersion); //$NON-NLS-1$//$NON-NLS-2$
        }

        Collection<Map<String, Object>> stateAvailabilityModel = getCaseStateAvailabilityModel(process, caseClass);

        if (stateAvailabilityModel != null && !stateAvailabilityModel.isEmpty()) {
            caseSvcModel.put("caseStates", stateAvailabilityModel); //$NON-NLS-1$
        }

        /* Sid ACE-3600 include state attribute name. */
        Property caseStateAttrib = ProcessUIUtil.getCaseClassCaseState(caseClass);
        caseSvcModel.put("caseStatePropertyName", caseStateAttrib.getName()); //$NON-NLS-1$

        return caseSvcModel;
    }

    /**
     * Gets the common case / business service model elements as a model map.
     * 
     * category, processFileName, processName, channels, privileges
     * 
     * @param process
     * @param pageflowDestFolder
     * @param channelsModel
     * @param bizSvcModel
     */
    private Map<String, Object> getCommonServiceElements(Process process, IFolder pageflowDestFolder,
            Collection<Map<String, Object>> channelsModel) {
        Map<String, Object> commonSvcModel = new LinkedHashMap<>();

        commonSvcModel.put("processFileName", getProcessRascBpelLocation(pageflowDestFolder, process)); //$NON-NLS-1$

        commonSvcModel.put("processName", process.getName()); //$NON-NLS-1$

        /* Sid ACE-3399: Add process label so that runtime can access it. */
        commonSvcModel.put("processLabel", Xpdl2ModelUtil.getDisplayNameOrName(process)); //$NON-NLS-1$

        if (channelsModel != null && !channelsModel.isEmpty()) {
            commonSvcModel.put("channels", channelsModel); //$NON-NLS-1$
        }

        Collection<Map<String, Object>> privilegesModel = getPrivilegesModel(process);
        if (privilegesModel != null && !privilegesModel.isEmpty()) {
            commonSvcModel.put("privileges", privilegesModel); //$NON-NLS-1$
        }

        return commonSvcModel;
    }

    /**
     * Get the model map for the channels enabled in the given project
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.getPageFlowChannelIds())
     * 
     * @param project
     * @return the model map for the channels enabled in the given project
     */
    private Collection<Map<String, Object>> getChannelsModel(IProject project) {
        Collection<Map<String, Object>> channelsModel = new ArrayList<>();

        Channels channels = PresentationManager.getInstance().getChannels(project);
        for (Channel channel : channels.getChannels()) {

            for (TypeAssociation typeAssociation : channel.getTypeAssociations()) {

                if (typeAssociation.getChannelType().getImplementation().getId()
                        .equals(PresentationManager.IMPLEMANTATION_PULL)) {

                    Map<String, Object> channelModel = new LinkedHashMap<String, Object>();
                    channelModel.put("channel", typeAssociation.getDerivedId()); //$NON-NLS-1$

                    channelsModel.add(channelModel);
                }
            }
        }
        return channelsModel;

    }

    /**
     * Get the model map for the privileges selected for the given case / business service
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.addBusinessServicesModel())
     * 
     * @param project
     * @return the model map for the privileges selected for the given case / business service
     */
    private Collection<Map<String, Object>> getPrivilegesModel(Process process) {
        Collection<Map<String, Object>> privilegesModel = new ArrayList<>();

        RequiredAccessPrivileges requiredAccessPrivileges =
                (RequiredAccessPrivileges) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_RequiredAccessPrivileges());

        if (null != requiredAccessPrivileges) {

            EList<ExternalReference> privilegeReference = requiredAccessPrivileges.getPrivilegeReference();

            for (ExternalReference externalReference : privilegeReference) {
                Map<String, Object> privilegeModel = new LinkedHashMap<String, Object>();
                privilegeModel.put("privilege", externalReference.getXref()); //$NON-NLS-1$

                privilegesModel.add(privilegeModel);
            }
        }
        return privilegesModel;

    }



    /**
     * Returns the business service category extended attribute from process.
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.getBusinessServiceCategory())
     * 
     * @return the business service category extended attribute from process.
     */
    private String getBusinessServiceCategory(Process process) {

        Object bsCategory = Xpdl2ModelUtil.getOtherAttribute(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_BusinessServiceCategory());
        return (String) bsCategory;
    }

    /**
     * Retrieve the design time configuration of the target device for business service.
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.getBusinessServiceTargetDevice())
     * 
     * @return Retrieve the design time configuration of the target device for business service.
     */
    private String getBusinessServiceTargetDevice(Process process) {
        BusinessServicePublishType pt = (BusinessServicePublishType) Xpdl2ModelUtil.getOtherAttribute(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_BusinessServicePublishType());

        if (pt != null) {
            return pt.getLiteral();
        }

        return "Desktop"; //$NON-NLS-1$
    }

    /**
     * Get the RASC-relative BPEL file path for the given process
     * 
     * @param pageflowDestFolder
     * @param process
     * @return The RASC-relative BPEL file path for the given process
     */
    private String getProcessRascBpelLocation(IFolder pageflowDestFolder, Process process) {
        /* The path in the RASC mirrors the Process/.processOut/.... path used as a build location of the BPEL. */
        IFile bpelFile = pageflowDestFolder.getFile(process.getName() + N2PEConstants.BPEL_EXTENSION);

        String buildPath = bpelFile.getFullPath().toString();

        String rascPath = buildPath.substring(buildPath.indexOf(BPELN2Utils.BPEL_ROOT_OUTPUTFOLDER_NAME));

        return rascPath;
    }

    /**
     * Retrieve the name of the case reference parameter in the given case service process
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.addCaseServicesModel())
     * 
     * @return the name of the case reference parameter in the given case service process
     */
    private String getCaseServiceCaseRefParam(Process process) {
        Collection<FormalParameter> formalParameters = ProcessInterfaceUtil.getAllFormalParameters(process);

        for (FormalParameter formalParameter : formalParameters) {

            if (formalParameter.getDataType() instanceof RecordType) {

                if (ModeType.IN_LITERAL.equals(formalParameter.getMode())) {
                    return formalParameter.getName();
                }
            }
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Retrieve the BOM case class that the given case service process is related to
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.addCaseServicesModel())
     * 
     * @return the BOM case class that the given case service process is related to
     */
    private Class getCaseServiceCaseClass(Process process) {
        CaseService xpdlCaseServiceModel = (CaseService) Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_CaseService());

        ExternalReference reference = xpdlCaseServiceModel.getCaseClassType();

        Class umlClass =
                (Class) ProcessUIUtil.getReferencedClassifier(reference,
                WorkingCopyUtil.getProjectFor(process));

        return umlClass;
    }

    /**
     * Get the model map for the available case states selected for the given case service
     * 
     * (This method adapted from AMX BPM
     * com.tibco.xpd.wm.pageflow.component.NewPFCImplementationTypeProvider.addCaseServicesModel())
     * 
     * @param project
     * @return the model map for the available case states selected for the given case service
     */
    private Collection<Map<String, Object>> getCaseStateAvailabilityModel(Process process, Class caseClass) {
        Collection<Map<String, Object>> caseStatesModel = new ArrayList<>();

        CaseService xpdlCaseServiceModel = (CaseService) Xpdl2ModelUtil.getOtherElement(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_CaseService());

        VisibleForCaseStates visibleForCaseStates = xpdlCaseServiceModel.getVisibleForCaseStates();

        if (null != xpdlCaseServiceModel && null != visibleForCaseStates) {
            /*
             * The 'no state set' configuration.
             */
            if (visibleForCaseStates.isVisibleForUnsetCaseState()) {
                Map<String, Object> caseStateModel = new LinkedHashMap<String, Object>();
                caseStateModel.put("caseState", "$UNSET$"); //$NON-NLS-1$ //$NON-NLS-2$

                caseStatesModel.add(caseStateModel);
            }

            /*
             * The individual specific state selection configuration.
             */
            EList<ExternalReference> caseStates = visibleForCaseStates.getCaseState();

            if (!caseStates.isEmpty()) {
                Property caseStateAttrib = ProcessUIUtil.getCaseClassCaseState(caseClass);

                Enumeration caseStateEnum = (Enumeration) caseStateAttrib.getType();

                EList<EnumerationLiteral> caseStateLiterals = caseStateEnum.getOwnedLiterals();

                for (EnumerationLiteral enumerationLiteral : caseStateLiterals) {
                    ExternalReference bomEnumRef =
                            ProcessUIUtil.getExternalRefForEnumLit(xpdlCaseServiceModel, enumerationLiteral);

                    for (ExternalReference xpdlExtRef : caseStates) {
                        if (EcoreUtil.equals(bomEnumRef, xpdlExtRef)) {
                            Map<String, Object> caseStateModel = new LinkedHashMap<String, Object>();
                            caseStateModel.put("caseState", enumerationLiteral.getName()); //$NON-NLS-1$

                            caseStatesModel.add(caseStateModel);
                        }
                    }
                }
            }

        }
        return caseStatesModel;

    }
}
