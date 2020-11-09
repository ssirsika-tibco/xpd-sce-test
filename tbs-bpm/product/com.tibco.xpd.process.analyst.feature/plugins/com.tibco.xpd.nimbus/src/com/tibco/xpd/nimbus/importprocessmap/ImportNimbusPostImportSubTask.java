/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.nimbus.importprocessmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.nimbus.XpdNimbusPlugin;
import com.tibco.xpd.nimbus.internal.Messages;
import com.tibco.xpd.nimbus.layoutprocess.ImportNimbusProcessAutoLayout;
import com.tibco.xpd.nimbus.layoutprocess.ImportNimbusProcessAutoLayout.LayoutException;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.importwizard.PostImportTask;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalPackage;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Perform post import operations on processes generated from Nimbus diagrams.
 * 
 * @author aallway
 * @since 31 Oct 2012
 */
public class ImportNimbusPostImportSubTask implements PostImportTask {

    /**
     * 
     * @see com.tibco.xpd.ui.importexport.importwizard.PostImportTask#perform(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IFile)
     * 
     * @param monitor
     * @param outputFile
     * @throws CoreException
     */
    @Override
    public void perform(IProgressMonitor monitor, IFile outputFile)
            throws CoreException {

        try {
            WorkingCopy workingCopy =
                    WorkingCopyUtil.getWorkingCopy(outputFile);

            if (workingCopy != null
                    && workingCopy.getRootElement() instanceof Package) {
                Package pkg = (Package) workingCopy.getRootElement();

                String msg =
                        String.format(Messages.ImportNimbusPostImportSubTask_PerformingPostImprotTasks_message,
                                outputFile.getName());
                monitor.beginTask(msg, pkg.getProcesses().size());

                /*
                 * Don't want to perform post-import on command stack so create
                 * a separate transaction for it.
                 */
                Map<String, Object> attrs = new HashMap<String, Object>();
                attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
                InternalTransaction transaction = null;

                try {
                    transaction =
                            ((InternalTransactionalEditingDomain) workingCopy
                                    .getEditingDomain())
                                    .startTransaction(false, attrs);

                    for (Process process : pkg.getProcesses()) {
                        String subMsg = String.format(" (%1$s)", //$NON-NLS-1$
                                Xpdl2ModelUtil.getDisplayNameOrName(process));
                        monitor.subTask(msg + subMsg);

                        postProcessProcess(process, outputFile);

                        monitor.worked(1);

                        /* Little sleep to allow update of prog monitor. */
                        Thread.sleep(20);

                    }

                } catch (InterruptedException e1) {
                } finally {
                    if (transaction != null) {
                        try {
                            transaction.commit();
                        } catch (RollbackException e) {
                        }
                    }
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * Perform post-import processing on the given process.
     * 
     * @param process
     * @param outputFile
     */
    private void postProcessProcess(Process process, IFile outputFile) {
        /* Auto layout. */
        autoLayout(process);

        /* Fix sub-process package references in other xpdl's */
        fixSubProcessReferences(process);

        /* Sub-process Parameter mapping. */
        addSubProcessParameterMappings(process);

        /*
         * Sid SNA-16. Add project destinations to process in Studio for
         * Designers (BUT not in StudioForAnalyst because that doesn't allow
         * setting / unsetting of destinations AND it makes it consistent with
         * creation of process packages in studio for analysts vs studio for
         * designers).
         */
        if (!XpdResourcesPlugin.isRCP()) {
            addProjectDestinationEnvironments(process, outputFile);
        }

    }

    /**
     * Add project destinations to process
     * 
     * @param process
     * @param outputFile
     */
    private void addProjectDestinationEnvironments(Process process,
            IFile outputFile) {
        IProject project = outputFile.getProject();

        if (project != null) {
            DestinationUtil.addPassedDestinationToProcess(GlobalDestinationUtil
                    .getEnabledGlobalDestinations(project, true), process);
        }
    }

    /**
     * Add sub-process parameter mappings for any sub-process task that has
     * matching data for sub-process parameters.
     * 
     * @param process
     */
    private void addSubProcessParameterMappings(Process process) {

        List<String> localDataNames = new ArrayList<String>();

        for (ProcessRelevantData data : ProcessInterfaceUtil
                .getAllDataDefinedInProcess(process)) {
            localDataNames.add(data.getName());
        }

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {

            if (activity.getImplementation() instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) activity.getImplementation();

                EObject procOrIfc =
                        TaskObjectUtil.getSubProcessOrInterface(activity);

                if (procOrIfc instanceof Process) {
                    Process subProcess = (Process) procOrIfc;

                    /**
                     * Sid ACE-4851 create DataMapper grammar mappings.
                     */
                    if (subProcess.getFormalParameters().size() > 0) {
                        ScriptDataMapper inDataMapper = null;
                        ScriptDataMapper outDataMapper = null;

                        for (FormalParameter parameter : subProcess.getFormalParameters()) {

                            String localDataName = findCaseInsensitive(localDataNames, parameter.getName());

                            if (localDataName != null) {
                                ModeType paramMode = parameter.getMode();

                                if (ModeType.IN_LITERAL.equals(paramMode) || ModeType.INOUT_LITERAL.equals(paramMode)) {
                                    if (inDataMapper == null) {
                                        inDataMapper = XpdExtensionFactory.eINSTANCE.createScriptDataMapper();
                                        inDataMapper.setMappingDirection(DirectionType.IN_LITERAL);
                                        inDataMapper.setMapperContext(
                                                ProcessEditorConstants.DATAMAPPER_CONTEXT_PROCESS_TO_SUBPROCESS);

                                        Xpdl2ModelUtil.addOtherElement(subFlow,
                                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_InputMappings(),
                                                inDataMapper);
                                    }

                                    DataMapping dataMapping = createDataMapping(DirectionType.IN_LITERAL,
                                            parameter.getName(),
                                            localDataName,
                                            ProcessEditorConstants.DATAMAPPER_ACTIVITY_INTERFACE_CONTRIBUTOR_ID,
                                            ProcessEditorConstants.DATAMAPPER_PROCESS_TO_SUBPROCESS_CONTRIBUTOR_ID);

                                    inDataMapper.getDataMappings().add(dataMapping);
                                }

                                if (ModeType.OUT_LITERAL.equals(paramMode)
                                        || ModeType.INOUT_LITERAL.equals(paramMode)) {
                                    if (outDataMapper == null) {
                                        outDataMapper = XpdExtensionFactory.eINSTANCE.createScriptDataMapper();
                                        outDataMapper.setMappingDirection(DirectionType.OUT_LITERAL);
                                        outDataMapper.setMapperContext(
                                                ProcessEditorConstants.DATAMAPPER_CONTEXT_SUBPROCESS_TO_PROCESS);

                                        Xpdl2ModelUtil.addOtherElement(subFlow,
                                                XpdExtensionPackage.eINSTANCE.getDocumentRoot_OutputMappings(),
                                                outDataMapper);
                                    }

                                    DataMapping dataMapping = createDataMapping(DirectionType.OUT_LITERAL,
                                            localDataName,
                                            parameter.getName(),
                                            ProcessEditorConstants.DATAMAPPER_SUBPROCESS_TO_PROCESS_CONTRIBUTOR_ID,
                                            ProcessEditorConstants.DATAMAPPER_ACTIVITY_INTERFACE_CONTRIBUTOR_ID);

                                    outDataMapper.getDataMappings().add(dataMapping);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Sid ACE-4851 create DataMapper grammar mappings.
     * 
     * @param direction
     * @param parameterName
     * @param localDataName
     * @param sourceContributorId
     * @param targetContributorId
     * @return data mapping for the given direction and source/target.
     */
    private DataMapping createDataMapping(DirectionType direction,
            String parameterName, String localDataName, String sourceContributorId, String targetContributorId) {
        DataMapping dataMapping = Xpdl2Factory.eINSTANCE.createDataMapping();

        dataMapping.setDirection(direction);
        dataMapping.setFormal(parameterName);
        Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_SourceContributorId(),
                sourceContributorId);
        Xpdl2ModelUtil.setOtherAttribute(dataMapping,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_TargetContributorId(),
                targetContributorId);

        Expression actual = Xpdl2ModelUtil.createExpression(localDataName);

        dataMapping.setActual(actual);
        return dataMapping;
    }

    /**
     * @param localDataNames
     * @param name
     * @return matching name in list (case insensitive)
     */
    private String findCaseInsensitive(List<String> localDataNames, String name) {
        for (String localDataName : localDataNames) {
            if (localDataName.equalsIgnoreCase(name)) {
                return localDataName;
            }
        }
        return null;
    }

    /**
     * Fix sub-process package references in other xpdl's
     * 
     * @param process
     */
    private void fixSubProcessReferences(Process process) {
        Map<IResource, ExternalPackage> externalPackages =
                new HashMap<IResource, ExternalPackage>();

        Set<String> localSubProcessIds = new HashSet<String>();

        for (Process pkgProcess : process.getPackage().getProcesses()) {
            localSubProcessIds.add(pkgProcess.getId());
        }

        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {
            if (activity.getImplementation() instanceof SubFlow) {
                SubFlow subFlow = (SubFlow) activity.getImplementation();

                /* Check if sub-process is not in the local package. */
                String subProcessId = subFlow.getProcessId();

                if (!localSubProcessIds.contains(subProcessId)) {
                    /*
                     * If the importer hasn't added package id (which currently
                     * it never does) then find and add one if we can.
                     */
                    String packageId = subFlow.getPackageRefId();
                    if (packageId == null || packageId.length() == 0) {

                        List<EObject> procsForId =
                                ProcessUIUtil.getAllProcesses(subProcessId);

                        if (procsForId.size() > 0) {
                            EObject subProcess = procsForId.get(0);

                            IFile extPkgFile =
                                    WorkingCopyUtil.getFile(subProcess);

                            if (extPkgFile != null) {
                                ExternalPackage extPkg =
                                        externalPackages.get(extPkgFile);
                                if (extPkg == null) {
                                    extPkg =
                                            Xpdl2WorkingCopyImpl
                                                    .createExternalPackage(extPkgFile);

                                    process.getPackage().getExternalPackages()
                                            .add(extPkg);
                                    externalPackages.put(extPkgFile, extPkg);
                                }

                                String refId = extPkg.getHref();
                                subFlow.setPackageRefId(refId);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Layout the process.
     * 
     * @param process
     */
    private void autoLayout(Process process) {
        ImportNimbusProcessAutoLayout autoLayout =
                new ImportNimbusProcessAutoLayout(process);

        try {
            autoLayout.layoutProcess();
        } catch (LayoutException e) {
            XpdNimbusPlugin.getDefault().getLogger().error(e);
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.importwizard.ImportSubTask#dispose()
     * 
     */
    @Override
    public void dispose() {

    }

}
