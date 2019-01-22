/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author Miguel Torres
 */
public class EntityDisplayNameRule extends PackageValidationRule {

    public static final String ID_DATAFIELD = "bpmn.entityDataFieldDisplayName"; //$NON-NLS-1$

    public static final String ID_FORMALPARAMETER =
            "bpmn.entityFormalParameterDisplayName"; //$NON-NLS-1$

    public static final String ID_PARTICIPANT =
            "bpmn.entityParticipantDisplayName"; //$NON-NLS-1$

    public static final String ID_TYPEDECLARATION =
            "bpmn.entityTypeDeclarationDisplayName"; //$NON-NLS-1$

    public static final String ID_PROCESSINTERFACE =
            "bpmn.entityProcessInterfaceDisplayName"; //$NON-NLS-1$

    public static final String ID_STARTEVENT =
            "bpmn.entityStartEventDisplayName"; //$NON-NLS-1$

    public static final String ID_INTERMEDIATEEVENT =
            "bpmn.entityIntermediateEventDisplayName"; //$NON-NLS-1$

    public static final String ID_PROCESS = "bpmn.entityProcessDisplayName"; //$NON-NLS-1$

    /**
     * @param pckg
     *            The package to validate.
     * @see com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule#validate(com.tibco.xpd.xpdl2.Package)
     */
    @Override
    public void validate(Package pkg) {
        if (pkg != null) {
            List dataFields = pkg.getDataFields();
            // Validate Package Data Fields
            validateDataFieldsOrParams(dataFields, pkg);

            List<Participant> participants = pkg.getParticipants();
            // Validate Package Participants
            validateParticipants(participants, pkg);

            List<TypeDeclaration> typeDeclarations = pkg.getTypeDeclarations();
            // Validate Type Declarations
            validateTypeDeclarations(typeDeclarations, pkg);

            ProcessInterfaces processInterfaces =
                    ProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                List<ProcessInterface> processInterfaceList =
                        processInterfaces.getProcessInterface();
                // Validate Process Interfaces
                validateProcessInterfaces(processInterfaceList, pkg);
            }

            List<Process> processes = pkg.getProcesses();
            // Validate Processes
            validateProcesses(processes, pkg);
        }
    }

    private void validateDataFieldsOrParams(
            List<ProcessRelevantData> processRelevantDataList, EObject container) {
        if (processRelevantDataList != null && container != null) {
            for (ProcessRelevantData processRelevantData : processRelevantDataList) {
                /** check for duplicate labels */
                String processRelevantDataDisplayName =
                        (String) Xpdl2ModelUtil
                                .getDisplayName(processRelevantData);
                ProcessRelevantData duplicateDisplayFieldOrParam =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayFieldOrParam(container,
                                        processRelevantData,
                                        processRelevantDataDisplayName);
                if (duplicateDisplayFieldOrParam != null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(processRelevantDataDisplayName);
                    messages.add(getContainerName(container));
                    if (processRelevantData instanceof DataField) {
                        addIssue(ID_DATAFIELD, processRelevantData, messages);
                    } else {
                        addIssue(ID_FORMALPARAMETER,
                                processRelevantData,
                                messages);
                    }
                }
            }
        }
    }

    /**
     * @param container
     * @param containerName
     * @return
     */
    private String getContainerName(EObject container) {
        if (container instanceof NamedElement) {
            return Xpdl2ModelUtil
                    .getDisplayNameOrName((NamedElement) container);
        }
        return ""; //$NON-NLS-1$
    }

    private void validateParticipants(List<Participant> participants,
            EObject container) {
        if (participants != null && container != null) {
            for (Participant participant : participants) {
                /** check for duplicate labels */
                String participantDisplayName =
                        (String) Xpdl2ModelUtil.getDisplayName(participant);
                Participant duplicateDisplayParticipant =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayParticipant(container,
                                        participant,
                                        participantDisplayName);
                if (duplicateDisplayParticipant != null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(participantDisplayName);
                    messages.add(getContainerName(container));
                    addIssue(ID_PARTICIPANT, participant, messages);
                }
            }
        }
    }

    private void validateTypeDeclarations(
            List<TypeDeclaration> typeDeclarations, Package pkg) {
        if (typeDeclarations != null && pkg != null) {
            for (TypeDeclaration typeDeclaration : typeDeclarations) {
                /** check for duplicate labels */
                String tdeclarationDisplayName =
                        (String) Xpdl2ModelUtil.getDisplayName(typeDeclaration);
                TypeDeclaration duplicateDisplayTypeDeclaration =
                        Xpdl2ModelUtil.getDuplicateDisplayTypeDeclaration(pkg,
                                typeDeclaration,
                                tdeclarationDisplayName);
                if (duplicateDisplayTypeDeclaration != null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(tdeclarationDisplayName);
                    messages.add(getContainerName(pkg));
                    addIssue(ID_TYPEDECLARATION, typeDeclaration, messages);
                }
            }
        }
    }

    private void validateProcessInterfaces(
            List<ProcessInterface> processInterfaces, Package pkg) {
        if (processInterfaces != null && pkg != null) {
            for (ProcessInterface processInterface : processInterfaces) {
                String processInterfaceDisplayName =
                        (String) Xpdl2ModelUtil
                                .getDisplayName(processInterface);
                ProcessInterface duplicateDisplayProcessInterface =
                        Xpdl2ModelUtil.getDuplicateDisplayProcessInterface(pkg,
                                processInterface,
                                processInterfaceDisplayName);
                if (duplicateDisplayProcessInterface != null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(duplicateDisplayProcessInterface.getName());
                    messages.add(getContainerName(pkg));
                    addIssue(ID_PROCESSINTERFACE, processInterface, messages);
                }
                List formalParameters = processInterface.getFormalParameters();
                // Validate Process Interface Formal Parameters
                validateDataFieldsOrParams(formalParameters, processInterface);

                List<StartMethod> startMethods =
                        processInterface.getStartMethods();
                // Validate Process Interface start Methods
                validateStartMethods(startMethods, processInterface);

                List<IntermediateMethod> intermediateMethods =
                        processInterface.getIntermediateMethods();
                // Validate Process Interface start Methods
                validateIntermediateMethods(intermediateMethods,
                        processInterface);
            }
        }
    }

    private void validateStartMethods(List<StartMethod> startMethods,
            ProcessInterface processInterface) {
        if (startMethods != null && processInterface != null) {
            for (StartMethod startMethod : startMethods) {
                String startMethodDisplayName =
                        (String) Xpdl2ModelUtil.getDisplayName(startMethod);
                StartMethod duplicateDisplayStartMethod =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayStartMethod(processInterface,
                                        startMethod,
                                        startMethodDisplayName);
                if (duplicateDisplayStartMethod != null) {
                    addIssue(ID_STARTEVENT, startMethod);
                }
            }
        }
    }

    private void validateIntermediateMethods(
            List<IntermediateMethod> intermediateMethods,
            ProcessInterface processInterface) {
        if (intermediateMethods != null && processInterface != null) {
            for (IntermediateMethod intermediateMethod : intermediateMethods) {
                String intermediateMethodDisplayName =
                        (String) Xpdl2ModelUtil
                                .getDisplayName(intermediateMethod);
                IntermediateMethod duplicateDisplayIntermediateMethod =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayIntermediateMethod(processInterface,
                                        intermediateMethod,
                                        intermediateMethodDisplayName);
                if (duplicateDisplayIntermediateMethod != null) {
                    addIssue(ID_INTERMEDIATEEVENT, intermediateMethod);
                }
            }
        }
    }

    private void validateProcesses(List<Process> processes, Package pkg) {
        if (processes != null && pkg != null) {
            for (Process process : processes) {
                String processDisplayName =
                        (String) Xpdl2ModelUtil.getDisplayName(process);
                Process duplicateDNProcess =
                        Xpdl2ModelUtil.getDuplicateDisplayNameProcess(pkg,
                                process,
                                processDisplayName);
                if (duplicateDNProcess != null) {
                    List<String> messages = new ArrayList<String>();
                    messages.add(duplicateDNProcess.getName());
                    messages.add(getContainerName(pkg));
                    addIssue(ID_PROCESS, process, messages);
                }
                List parameters = process.getFormalParameters();
                // Validate Process Formal Parameters
                validateDataFieldsOrParams(parameters, process);

                List dataFields = process.getDataFields();
                // Validate Process Data Fields
                validateDataFieldsOrParams(dataFields, process);

                List<Participant> participants = process.getParticipants();
                // Validate Process Participants
                validateParticipants(participants, process);

                // validate local activity data fields
                validateActivityLocalData(process);
            }
        }
    }

    /**
     * @param process
     */
    private void validateActivityLocalData(Process process) {
        Collection<Activity> allActivitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            List localDataFields = activity.getDataFields();
            validateDataFieldsOrParams(localDataFields, activity);
        }
    }

}
